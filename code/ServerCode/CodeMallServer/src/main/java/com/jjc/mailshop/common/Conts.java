package com.jjc.mailshop.common;

/**
 * Created by jjc on 2017/5/23.
 */
public class Conts {
    /**
     * 存储用户的key
     */
    public static final String CURRENT_USER="current_user";
    /**
     * 邮箱
     */
    public static final String EMAIL="email";
    /**
     * 用户名
     */
    public static final String USERNAME="username";
    public static final String UPLOAD_FILE="upload_file";
    public interface Role{
        int ROLE_CUSTOMER = 0; //普通用户
        int ROLE_ADMIN = 1;//管理员
    }
}
