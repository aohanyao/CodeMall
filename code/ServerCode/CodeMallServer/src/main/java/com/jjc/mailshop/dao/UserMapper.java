package com.jjc.mailshop.dao;

import com.jjc.mailshop.pojo.User;
import org.apache.ibatis.annotations.Param;

public interface UserMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(User record);

    int insertSelective(User record);

    User selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(User record);

    int updateByPrimaryKey(User record);

    int checkUserExit(String username);

    //记得加上Param注解
    User selectLogin(@Param("username") String username, @Param("password") String password);

    //检测用户名是否存在
    int checkEmail(String email);

    //查询用户忘记密码的问题
    String selectUserQuestion(String username);

    //根据用户名 问题 答案 查询用户
    User selectUserQuestionAndAnswer(@Param("username") String username,
                                     @Param("question") String question,
                                     @Param("answer") String answer);

    //根据用户名 密码 查询这个用户
    int selectForgetPasswordUser(@Param("username") String username,
                                 @Param("password") String oldPass);

    //修改密码
    int updatePassword(@Param("username") String username,
                       @Param("password") String oldPass);
}