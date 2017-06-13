package com.jjc.mailshop.service.imp

import com.jjc.mailshop.common.ServerResponse
import com.jjc.mailshop.service.IFileService
import org.springframework.stereotype.Service
import org.springframework.web.multipart.MultipartFile
import java.io.File
import java.util.*
import com.qiniu.common.QiniuException
import com.qiniu.storage.model.DefaultPutRet
import com.google.gson.Gson
import com.qiniu.util.Auth
import com.qiniu.storage.UploadManager
import com.qiniu.common.Zone
import com.qiniu.storage.Configuration


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

        //构造一个带指定Zone对象的配置类
        val cfg = Configuration(Zone.zone2())
        //...其他参数参考类注释
        val uploadManager = UploadManager(cfg)
        //...生成上传凭证，然后准备上传
        val accessKey = "4pEFhEobGofwwHN2wDlk_z7X-DyHdqli7bBnH2nj"
        val secretKey = "bXRxAo7Tar2yqvi1tTbAA4pONPZZbRUhiOFUdlMW"
        //目标名称
        val bucket = "mal-shop-image"
        //如果是Windows情况下，格式是 D:\\qiniu\\test.png
        val localFilePath = targetFile.path
        //默认不指定key的情况下，以文件内容的hash值作为文件名
        val key: String? = null
        val auth = Auth.create(accessKey, secretKey)
        val upToken = auth.uploadToken(bucket)
        try {
            val response = uploadManager.put(localFilePath, key, upToken)
            //解析上传成功的结果
            val putRet = Gson().fromJson(response.bodyString(), DefaultPutRet::class.java)

            //3.删除本地
            targetFile.delete()
            //返回结果
            return ServerResponse.createBySuccess("上传成功", "http://orfgrfzku.bkt.clouddn.com/${putRet.key}")
        } catch (ex: QiniuException) {
            val r = ex.response
            //返回错误
            return ServerResponse.createBySuccessMessage(r.toString())

        }

    }
}