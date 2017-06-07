package com.jjc.mailshop.controller.backend;

import com.jjc.mailshop.common.Conts;
import com.jjc.mailshop.common.ServerResponse;
import com.jjc.mailshop.pojo.User;
import com.jjc.mailshop.service.IUserService;
import com.jjc.mailshop.service.imp.UserServiceImp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;

/**
 * Created by Administrator on 2017/6/6 0006.
 */
@Controller
@RequestMapping(value = "/manger/user")
public class UserMangerController {
    @Autowired
    IUserService iUserService;


    @RequestMapping(value = "login.json", method = RequestMethod.POST)
    @ResponseBody
    public ServerResponse<User> login(String username, String password, HttpSession session) {
        //调用服务进行登陆
        ServerResponse<User> response = iUserService.loginAdmin(username, password);
        //登陆成功
        if (response.isSuccess()) {
            //存放用户到session
            session.setAttribute(Conts.CURRENT_USER, response.getData());
        }
        //返回结果
        return response;
    }
}
