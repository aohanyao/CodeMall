package com.jjc.mailshop.service;

import com.jjc.mailshop.common.ServerResponse;
import com.jjc.mailshop.pojo.User;
import com.sun.istack.internal.NotNull;

import java.util.List;

/**
 * Created by jjc on 2017/5/22.
 * <p>用户服务的接口</p>
 */
public interface IUserService {
    /**
     * 用户登陆
     *
     * @param username
     * @param password
     * @return
     */
    ServerResponse<User> login(String username, String password);

    /**
     * 查询所有的用户
     *
     * @return
     */
    ServerResponse<List<User>> selectAllUser();

    /**
     * 用户注册
     *
     * @param user
     * @return
     */
    ServerResponse<String> register(User user);

    /**
     * 检测邮箱是否存在
     *
     * @param email
     * @return
     */
    ServerResponse<String> checkEmail(String email);

    /**
     * 检测用户名是否存在
     *
     * @param username
     * @return
     */
    ServerResponse<String> checkUsername(String username);

    /**
     * 获取用户忘记密码的问题
     *
     * @param username
     * @return
     */
    ServerResponse<String> getUserQuestion(String username);


    /**
     * 验证改用户的问题和答案是否一致
     *
     * @param username
     * @param question
     * @param answer
     * @return
     */
    ServerResponse<User> validUserQuestionAndAnswer(String username,
                                                    String question,
                                                    String answer);

    /**
     * 忘记密码
     *
     * @param username 用户名
     * @param oldPass  旧密码
     * @param newPass  新密码
     * @return
     */
    ServerResponse<String> forgetPassword(@NotNull String username,
                                          @NotNull String oldPass,
                                          @NotNull String newPass);

    /**
     * 管理员登陆
     * @param username
     * @param password
     * @return
     */
     ServerResponse<User> loginAdmin(String username, String password);
}
