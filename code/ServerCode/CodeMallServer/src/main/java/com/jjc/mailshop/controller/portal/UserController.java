package com.jjc.mailshop.controller.portal;

import com.jjc.mailshop.common.Conts;
import com.jjc.mailshop.common.ServerResponse;
import com.jjc.mailshop.common.TokenCache;
import com.jjc.mailshop.pojo.User;
import com.jjc.mailshop.service.IUserService;
import com.sun.istack.internal.NotNull;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;
import java.util.UUID;

/**
 * Created by jjc on 2017/5/20.
 * <p>用户控制器</p>
 */
@Controller
@RequestMapping(value = "/user/")
public class UserController {
    @Autowired
    IUserService iUserService;

    /**
     * 登陆接口
     *
     * @return
     */
    @RequestMapping(value = "login.json", method = RequestMethod.POST)
    @ResponseBody
    public ServerResponse<User> login(String username, String password, HttpSession session) {

        ServerResponse<User> serverResponse = iUserService.login(username, password);
        if (serverResponse.isSuccess()) {
            session.setAttribute(Conts.CURRENT_USER, serverResponse.getData());
        }

        return serverResponse;
    }

    /**
     * 用户注册接口
     *
     * @param user 用户对象参数
     * @return
     */
    @RequestMapping(value = "register.json", method = RequestMethod.POST)
    @ResponseBody
    public ServerResponse<String> register(User user) {
        //非空判断？？？

        //注册用户
        return iUserService.register(user);
    }


    /**
     * 校验相关的值
     *
     * @param str  需要校验的值
     * @param type 类型 email 邮箱  username 用户名
     * @return
     */
    @RequestMapping(value = "validType.json", method = RequestMethod.POST)
    @ResponseBody
    public ServerResponse<String> validType(String str, String type) {
        //非空判断
        if (StringUtils.isEmpty(type) || StringUtils.isEmpty(str)) {
            return ServerResponse.createByErrorMessage("参数名或参数为空");
        }
        switch (type) {
            case Conts.EMAIL:
                //邮箱
                return iUserService.checkEmail(str);
            case Conts.USERNAME:
                //用户名
                return iUserService.checkUsername(str);

        }
        return ServerResponse.createByErrorMessage("参数非法");
    }

    /**
     * 根据用户名获取用户的问题
     *
     * @param username
     * @return
     */
    @RequestMapping(value = "getUserQuestion.json", method = RequestMethod.POST)
    @ResponseBody
    public ServerResponse<String> getUserQuestion(String username) {
        return iUserService.getUserQuestion(username);
    }


    /**
     * 登出
     *
     * @param session
     * @return
     */
    @RequestMapping(value = "logout.json", method = RequestMethod.POST)
    @ResponseBody
    public ServerResponse<String> logout(HttpSession session) {
        //清除session的用户信息
        session.removeAttribute(Conts.CURRENT_USER);
        //返回提示
        return ServerResponse.createBySuccessMessage("登出成功");
    }


    /**
     * 获取已经登陆的用户信息
     *
     * @param session
     * @return
     */
    @RequestMapping(value = "getLoginUserInfo.json", method = RequestMethod.POST)
    @ResponseBody
    public ServerResponse<User> getLoginUserInfo(HttpSession session) {
        //从session中获取信息
        User user = (User) session.getAttribute(Conts.CURRENT_USER);
        //判断为空
        if (user == null) {
            return ServerResponse.createByErrorMessage("获取用户信息失败，用户未登录或已超时");
        }
        //返回用户信息
        return ServerResponse.createBySuccess("获取用户信息成功", user);
    }

    /**
     * 验证用户的问题与答案
     *
     * @param username 用户名
     * @param question 问题
     * @param answer   答案
     * @return
     */
    @RequestMapping(value = "validUserQuestionAndAnswer.json", method = RequestMethod.POST)
    @ResponseBody
    public ServerResponse<String> validUserQuestionAndAnswer(String username, String question, String answer) {

        //查询用户
        ServerResponse<User> userServerResponse = iUserService.validUserQuestionAndAnswer(username, question, answer);
        //判断是否查询到用户
        if (userServerResponse.isSuccess()) {
            //获取token
            String token = UUID.randomUUID().toString();
            //存储token
            TokenCache.setKey(TokenCache.TOKEN_PREFIX + username, token);
            //返回token
            return ServerResponse.createBySuccess("验证成功", token);
        }
        //返回错误
        return ServerResponse.createByErrorMessage(userServerResponse.getMsg());
    }

    /**
     * 忘记密码
     *
     * @param username 用户名
     * @param oldPass  旧密码
     * @param newPass  新密码
     * @param token    token
     * @return
     */
    @RequestMapping(value = "forgetPassword.json", method = RequestMethod.POST)
    @ResponseBody
    public ServerResponse<String> forgetPassword(@NotNull String username,
                                                 @NotNull String oldPass,
                                                 @NotNull String newPass,
                                                 @NotNull String token) {
        //获取缓存中的token
        String cacheToken = TokenCache.getKey(TokenCache.TOKEN_PREFIX + username);
        //两个token不相等
        if (!StringUtils.equals(cacheToken, token)) {
            return ServerResponse.createByErrorMessage("token不存在或已失效");
        }


        //调用Service 进行更改

        return null;
    }
}
