package com.jjc.mailshop.controller.universal

import com.jjc.mailshop.common.CheckUserPermissionUtil
import com.jjc.mailshop.common.Conts
import com.jjc.mailshop.common.ServerResponse
import com.jjc.mailshop.service.IFileService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.ResponseBody
import org.springframework.web.multipart.MultipartFile
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpSession

/**
 * Created by Administrator on 2017/6/13 0013.
 * <p>通用文件控制器
 */
@RequestMapping(value = "/universal/file/")
@Controller
class FileController {
    @Autowired
    val iFileService: IFileService? = null

    /***
     * 上传文件
     */
    @RequestMapping(value = "uploadFile.json")
    @ResponseBody
    fun uploadFile(session: HttpSession,
                   @RequestParam(value = "upload_file", required = false) file: MultipartFile,
                   request: HttpServletRequest): ServerResponse<String> {
        //检查登录与权限
//        val permission = CheckUserPermissionUtil.checkAdminLoginAndPermission(session)
//        if (permission != null) {
//            return ServerResponse.createByErrorMessage(permission.msg)
//        }
        //获取到文件路径
        var realPath = request.session.servletContext.getRealPath(Conts.UPLOAD_FILE)
        //开始上传图片
        return iFileService!!.uploadImage(file, realPath)
    }
}