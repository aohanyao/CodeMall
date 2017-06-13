package com.jjc.mailshop.service

import com.jjc.mailshop.common.ServerResponse
import org.springframework.web.multipart.MultipartFile

/**
 * Created by Administrator on 2017/6/13 0013.
 * <p>文件相关服务</p>
 */
interface IFileService {
    /**
     * 上传图片
     * @param file 图片
     * @param realFilePath 文件夹路径
     */
    fun uploadImage(file: MultipartFile, realFilePath: String): ServerResponse<String>
}