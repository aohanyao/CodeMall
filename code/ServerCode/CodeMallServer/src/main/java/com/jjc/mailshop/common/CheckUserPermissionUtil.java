package com.jjc.mailshop.common;

import com.jjc.mailshop.pojo.User;

import javax.servlet.http.HttpSession;

/**
 * Created by Administrator on 2017/6/8 0008.
 * <p>检查用户权限的工具类</p>
 */
public class CheckUserPermissionUtil {
    /**
     * 检查登录与用户权限
     *
     * @param session
     * @return
     */
    public static ServerResponse checkLoginAndPermission(HttpSession session) {
        //判断有没有登陆
        User mUser = (User) session.getAttribute(Conts.CURRENT_USER);
        if (mUser == null) {
            return ServerResponse.createByErrorMessage("用户未登录");
        }
        //判断用户是不是管理员
        if (mUser.getRole() != Conts.Role.ROLE_ADMIN) {
            return ServerResponse.createByErrorMessage("无权限操作");
        }
        return null;
    }
}
