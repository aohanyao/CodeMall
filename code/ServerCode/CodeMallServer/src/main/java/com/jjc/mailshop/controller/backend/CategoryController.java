package com.jjc.mailshop.controller.backend;

import com.jjc.mailshop.common.CheckUserPermissionUtil;
import com.jjc.mailshop.common.Conts;
import com.jjc.mailshop.common.ServerResponse;
import com.jjc.mailshop.pojo.Category;
import com.jjc.mailshop.pojo.User;
import com.jjc.mailshop.service.ICategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;
import java.util.List;

/**
 * Created by jjc on 2017/6/3.
 * <p>后台产品相关的控制器</p>
 */
@Controller
@RequestMapping(value = "/manage/category/")
public class CategoryController {
    @Autowired
    ICategoryService iCategoryService;

    /**
     * 根据父ID 获取产品类别
     *
     * @param session
     * @param parentId
     * @return
     */
    @RequestMapping(value = "getCategory.json", method = RequestMethod.POST)
    @ResponseBody
    public ServerResponse<List<Category>> getCategory(HttpSession session
            , @RequestParam(value = "parentId", defaultValue = "0") String parentId) {
        //判断有没有登陆
        User mUser = (User) session.getAttribute(Conts.CURRENT_USER);
        if (mUser == null) {
            return ServerResponse.createByErrorMessage("用户未登录");
        }
        //判断用户是不是管理员
        if (mUser.getRole() != Conts.Role.ROLE_ADMIN) {
            return ServerResponse.createByErrorMessage("无权限操作");

        }
        return iCategoryService.getCategoryByParentId(parentId);
    }

    /**
     * 新增产品类别
     *
     * @param parentId     父类ID
     * @param categoryName 名称
     * @param session
     * @return
     */
    @RequestMapping(value = "addCategory.json", method = RequestMethod.POST)
    @ResponseBody
    public ServerResponse<String> addCategory(@RequestParam(value = "parentId", defaultValue = "0") String parentId,
                                              String categoryName, HttpSession session) {
        //检查权限
        ServerResponse<String> mPermission = CheckUserPermissionUtil.checkLoginAndPermission(session);
        if (mPermission != null) return mPermission;

        //调用服务层增加产品类别
        return iCategoryService.addCategory(parentId, categoryName);
    }


    /**
     * 修改品类
     *
     * @param categoryId   品类ID
     * @param categoryName 品类名称
     * @param session
     * @return
     */
    @RequestMapping(value = "updateCategory.json", method = RequestMethod.POST)
    @ResponseBody
    public ServerResponse<String> updateCategory(String categoryId, String categoryName, HttpSession session) {
        //检查权限
        ServerResponse<String> mPermission = CheckUserPermissionUtil.checkLoginAndPermission(session);
        if (mPermission != null) return mPermission;

        //调用服务层进行修改
        return iCategoryService.updateCategory(categoryId, categoryName);
    }

}
