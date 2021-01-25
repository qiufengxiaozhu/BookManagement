package com.java.book.common;

import com.java.book.entity.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * <p>
 * 描述: shiro相关
 * </p>
 *
 * @author 邱高强
 * @since 2020-12-20 17:30
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ActiveUser {

    private User user;

    private List<String> roles;

    private List<String> permissions;

}
