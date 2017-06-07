package com.jjc.mailshop.service.imp;

import com.jjc.mailshop.common.Conts;
import com.jjc.mailshop.common.ServerResponse;
import com.jjc.mailshop.dao.UserMapper;
import com.jjc.mailshop.pojo.User;
import com.jjc.mailshop.service.IUserService;
import com.jjc.mailshop.util.MD5Util;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by jjc on 2017/5/22.
 * <p>用户服务的实现</p>
 */
@Service("iUserService")
public class UserServiceImp implements IUserService {

    @Autowired
    UserMapper userMapper;

    @Override
    public ServerResponse<User> login(String username, String password) {
        //检查用户是否存在
        if (userMapper.checkUserExit(username) == 0) {
            return ServerResponse.createByErrorMessage("用户不存在");
        }
        //登陆
        User user = userMapper.selectLogin(username, MD5Util.MD5EncodeUtf8(password));
        if (user == null) {
            return ServerResponse.createByErrorMessage("用户名或密码错误");
        }
        //清空密码
        user.setPassword("");
        //返回数据
        return ServerResponse.createBySuccess("登陆成功", user);
    }

    @Override
    public ServerResponse<List<User>> selectAllUser() {

        return null;
    }


    @Override
    public ServerResponse<String> register(User user) {
        //先检测邮箱是否存在
        ServerResponse<String> checkEmail = checkEmail(user.getEmail());
        if (!checkEmail.isSuccess()) {
            return checkEmail;
        }
        //检测用户名是否存在
        ServerResponse<String> checkUsername = checkUsername(user.getUsername());
        if (!checkUsername.isSuccess()) {
            return checkEmail;
        }

        //加密密码
        user.setPassword(MD5Util.MD5EncodeUtf8(user.getPassword()));

        //插入用户到数据库
        if (userMapper.insert(user) == 0) {
            return ServerResponse.createByErrorMessage("注册失败");
        }

        //返回注册成功
        return ServerResponse.createBySuccessMessage("注册成功");
    }


    @Override
    public ServerResponse<String> checkEmail(String email) {
        if (userMapper.checkEmail(email) > 0) {
            return ServerResponse.createByErrorMessage("邮箱已存在");
        }
        return ServerResponse.createBySuccessMessage("验证成功");
    }

    @Override
    public ServerResponse<String> checkUsername(String username) {
        if (userMapper.checkUserExit(username) > 0) {
            return ServerResponse.createByErrorMessage("用户已存在");
        }
        return ServerResponse.createBySuccessMessage("验证成功");
    }

    @Override
    public ServerResponse<String> getUserQuestion(String username) {
        //检测用户名是否存在
        ServerResponse<String> checkUsername = checkUsername(username);
        //不存在
        if (checkUsername.isSuccess()) {
            return checkUsername;
        }
        //查找问题
        String question = userMapper.selectUserQuestion(username);
        if (StringUtils.isEmpty(question)) {
            return ServerResponse.createByErrorMessage("该用户未设置问题");
        }
        //返回问题
        return ServerResponse.createBySuccess("查询问题成功", question);
    }

    @Override
    public ServerResponse<User> validUserQuestionAndAnswer(String username, String question, String answer) {
        //查找用户
        User user = userMapper.selectUserQuestionAndAnswer(username, question, answer);
        //判断是否为空
        if (user == null) {
            return ServerResponse.createByErrorMessage("验证失败");
        }
        //返回用户
        return ServerResponse.createBySuccess("验证成功", user);
    }

    @Override
    public ServerResponse<String> forgetPassword(String username, String oldPass, String newPass) {
        //查询用户是否存在
        ServerResponse<String> checkUsername = checkUsername(username);
        //不存在
        if (checkUsername.isSuccess()) {
            return checkUsername;
        }
        //根据用户名和密码查询用户
        if (userMapper.selectForgetPasswordUser(username, oldPass) == 0) {
            return ServerResponse.createByErrorMessage("用户名或密码错误");
        }
        //修改密码
        return null;
    }

    //---------------------admin
    @Override
    public ServerResponse<User> loginAdmin(String username, String password) {
        //登陆
        ServerResponse<User> response = login(username, password);
        //判断是否登陆成功
        if (response.isSuccess()) {
            //设置为管理员
            response.getData().setRole(Conts.Role.ROLE_ADMIN);
        }
        return response;
    }
    //---------------------admin
}
