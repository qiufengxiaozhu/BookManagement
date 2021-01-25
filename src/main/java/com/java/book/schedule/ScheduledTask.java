package com.java.book.schedule;

import cn.hutool.core.date.DateUnit;
import cn.hutool.core.date.DateUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.java.book.common.Constant;
import com.java.book.entity.Borrow;
import com.java.book.entity.Ticket;
import com.java.book.service.BorrowService;
import com.java.book.service.TicketService;
import com.java.book.util.DateUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.math.BigDecimal;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Logger;

/**
 * <p>
 * 描述: 定时任务
 * </p>
 *
 * @author 邱高强
 * @since 2020-09-25 09:58
 */
@Api(tags = "定时任务")
@Component
public class ScheduledTask {

    Logger log = Logger.getLogger(String.valueOf(ScheduledTask.class));

    @Autowired
    private BorrowService borrowService;

    @Autowired
    private TicketService ticketService;

    /**
     * 【定时判断读者借阅记录是否逾期】
     * 每天凌晨1点执行定时  cron = "0 0 1 * * ?"
     * 为了测试方便，设置为每分钟启动一次
     * 七子表达式，最后一位不要写出来
     */
    @ApiOperation(value = "定时更新或新增罚单纪录")
    @Scheduled(cron = "0 0/1 * * * ?")
    public void updateBorrowTask() throws ParseException {

        // 是否启用定时任务1
        if ( !Constant.START_TIME_TASK_ONE ) {
            return;
        }

        log.warning(" ⏰ --- 定时任务：updateBorrowTask 启动 --- ⏰ ");

        QueryWrapper<Borrow> queryWrapper = new QueryWrapper<>();
        // 只查询修改前未逾期的数据
        queryWrapper.eq("flag", Constant.BORROW_IS_NOT_OVERDUE);
        // 只查询修改前未归档的数据
        queryWrapper.isNull("return_time");
        List<Borrow> borrowList = borrowService.list(queryWrapper);

        // 借阅记录--接收修改后已逾期、未归档的数据
        List<Borrow> borrows = new ArrayList<>();

        for ( Borrow borrow : borrowList ) {
            // 判断是否逾期  预期时间小于今天，则为已逾期
            if ( DateUtils.compare(borrow.getExpectTime(), DateUtil.parse(DateUtil.today() + " 23:59:59")) ) {
                //  小于，已逾期
                borrow.setFlag(1);
                borrow.setCreateTime(new Date());
                borrow.setUpdateTime(new Date());
                borrows.add(borrow);
            }
        }

        if ( borrows.size() > 0 ) {

            // ⏰批量更新【定时更新读者借阅记录是否逾期】
            if ( borrowService.updateBatchById(borrows) ) {
                log.warning(" ⏰ 【定时更新读者借阅记录是否逾期】 -- 成功！");
            } else {
                log.warning(" ⏰ 【定时更新读者借阅记录是否逾期】 -- 失败！");
            }

        } else {
            log.warning(" ⏰ 【定时更新读者借阅记录是否逾期】 -- 没有记录已逾期！");
        }

        log.warning(" ⏰ --- 定时任务： updateBorrowTask 结束 --- ⏰ ");
    }

    /**
     * 【定时更新或新增罚单纪录】
     * 每天凌晨1点执行定时  cron = "0 0 1 * * ?"
     * 为了测试方便，设置为每分钟启动一次
     * 七子表达式，最后一位不要写出来
     */
    @ApiOperation(value = "定时更新或新增罚单纪录")
    @Scheduled(cron = "0 0/1 * * * ?")
    public void addOrUpdateTicketTask() {

        // 是否启用定时任务2
        if ( !Constant.START_TIME_TASK_TWO ) {
            return;
        }

        log.warning(" ⏰ --- 定时任务： addOrUpdateTicketTask 启动 --- ⏰ ");

        QueryWrapper<Borrow> queryWrapper = new QueryWrapper<>();
        // 只查询修改前已逾期的数据
        queryWrapper.eq("flag", Constant.BORROW_IS_OVERDUE);
        // 只查询修改前未归档的数据
        queryWrapper.isNull("return_time");
        List<Borrow> borrowList = borrowService.list(queryWrapper);

        if ( borrowList.size() > 0 ) {

            // 接收罚单记录--新增
            List<Ticket> addTicketList = new ArrayList<>();
            // 接收罚单记录--更新
            List<Ticket> updateTicketList = new ArrayList<>();

            for ( Borrow borrow : borrowList) {
                // 判断是应该更新还是新增
                QueryWrapper<Ticket> queryWrapper2 = new QueryWrapper<>();
                queryWrapper2.eq("id", borrow.getFkTicketid());

                Ticket ticket = new Ticket();
                Ticket one = ticketService.getOne(queryWrapper2);
                if ( !StringUtils.isEmpty(one) ) {
                    ticket = one;
                }
                // 计算超期天数
                long betweenDay = DateUtil.between(borrow.getExpectTime(), DateUtil.parse(DateUtil.today() + " 23:59:59"), DateUnit.DAY);
                int days = Math.toIntExact(betweenDay) > 0 ? Math.toIntExact(betweenDay) : 1;
                ticket.setOverDate(days);
                // 计算超期费用
                ticket.setTicketFee(BigDecimal.valueOf(days * Double.parseDouble(String.valueOf(Constant.OVER_DATE_FEE))));
                ticket.setUpdateTime(new Date());

                if ( StringUtils.isEmpty(one) ) {

                    // 给对应的借阅记录赋值
                    borrow.setFkTicketid(borrow.getId());
                    borrowService.updateById(borrow);

                    // 给罚单id赋值
                    ticket.setId(borrow.getId());
                    ticket.setBooknum(borrow.getBooknum());
                    ticket.setLoginname(borrow.getLoginname());
                    ticket.setOverFee(Constant.OVER_DATE_FEE);

                    addTicketList.add(ticket);
                } else {
                    updateTicketList.add(ticket);
                }
            }

            // ⏰批量新增【定时更新或新增罚单纪录】
            if ( addTicketList.size() > 0 && ticketService.saveBatch(addTicketList) ) {
                log.warning(" ⏰ 【定时新增罚单纪录】 -- 成功！");
            } else {
                log.warning(" ⏰ 【定时新增罚单纪录】 -- 没有新的新增记录！");
            }
            if ( updateTicketList.size() > 0 && ticketService.updateBatchById(updateTicketList) ) {
                log.warning(" ⏰ 【定时更新罚单纪录】 -- 成功！");
            } else {
                log.warning(" ⏰ 【定时更新罚单纪录】 -- 没有新的更新记录！");
            }
        } else {
            log.warning(" ⏰ 【定时更新或新增罚单纪录】 -- 没有记录需要新增或更新！");
        }

        log.warning(" ⏰ --- 定时任务： addOrUpdateTicketTask 结束 --- ⏰ ");
    }
}
