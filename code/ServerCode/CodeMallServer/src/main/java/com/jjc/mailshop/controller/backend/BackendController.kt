package com.jjc.mailshop.controller.backend

import com.jjc.mailshop.common.CheckUserPermissionUtil
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.ResponseBody
import org.springframework.web.servlet.view.RedirectView
import javax.servlet.http.HttpServletResponse
import javax.servlet.http.HttpSession

/**
 * Created by Administrator on 2017/6/13 0013.
 */
@Controller
@RequestMapping("/backend/")
class BackendController {
    @RequestMapping("index.json")
    @ResponseBody
    fun checkIndex(session: HttpSession, response: HttpServletResponse): RedirectView {
        //检查登录与权限
        val permission = CheckUserPermissionUtil.checkLoginAndPermission(session)

        if (permission == null) {
            return RedirectView("production/index.html")
        } else {
            return RedirectView("production/login.html")
        }
    }
}