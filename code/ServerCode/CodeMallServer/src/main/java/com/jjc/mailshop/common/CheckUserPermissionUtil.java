package com.jjc.mailshop.common;

import com.jjc.mailshop.pojo.User;

import javax.servlet.http.HttpSession;

/**
 * Created by Administrator on 2017/6/8 0008.
 * <p>检查用户权限的工具类</p>
 */
public class CheckUserPermissionUtil {
    /**
     * 检查前台用户登陆
     *
     * @param session
     * @return
     */
<<<<<<< HEAD
    public static ServerResponse<User> checkUserLoginAndPermission(HttpSession session) {
=======
    public static ServerResponse checkLoginAndPermission(HttpSession session) {
>>>>>>> 1a2dc5b53bd735a441d30ea042b86e28da53cdd4
        //判断有没有登陆
        User mUser = (User) session.getAttribute(Conts.CURRENT_USER);
        if (mUser == null) {
            return ServerResponse.createByErrorMessage("用户未登录");
        }else{

            return ServerResponse.createBySuccess("用户已登录",mUser);
        }
    }

    /**
     * 检查后台管理员登录与用户权限
     *
     * @param session
     * @return
     */
    public static ServerResponse<String> checkAdminLoginAndPermission(HttpSession session) {
        //判断有没有登陆
        User mUser = (User) session.getAttribute(Conts.CURRENT_ADMIN_USER);
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
