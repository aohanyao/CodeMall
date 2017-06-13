package com.jjc.mailshop.service.imp

import com.jjc.mailshop.common.ServerResponse
import com.jjc.mailshop.service.IFileService
import org.springframework.stereotype.Service
import org.springframework.web.multipart.MultipartFile
import java.io.File
import java.util.*

/**
 * Created by Administrator on 2017/6/13 0013.
 */
@Service
class FileServiceImp : IFileService {


    override fun uploadImage(file: MultipartFile, realFilePath: String): ServerResponse<String> {
        //1.先存放到本地
        //查看文件夹是否存在
        var mFile = File(realFilePath)
        if (!mFile.exists()) {
            //可写
            mFile.setWritable(true)
            //创建文件夹
            mFile.mkdir()
        }
        //  获取拓展名
        val fileName = file.originalFilename
        //  获取拓展名
        val mExtName = fileName.subSequence(fileName.indexOfLast { it == '.' } + 1, fileName.length)
        //创建文件
        val targetFile = File("$realFilePath/${UUID.randomUUID()}.$mExtName")
        //写入文件
        try {
            file.transferTo(targetFile)
        } catch(e: Exception) {
            e.printStackTrace()
            return ServerResponse.createByErrorMessage("文件上传失败")
        }
        //2.上传到七牛


        //3.删除本地
        //4.返回结果

        return ServerResponse.createBySuccess("上传成功", targetFile.path)
    }
}