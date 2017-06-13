package com.jjc.mailshop.controller.portal

import com.github.pagehelper.PageInfo
import com.jjc.mailshop.common.ServerResponse
import com.jjc.mailshop.pojo.Product
import com.jjc.mailshop.service.IProductService
import com.sun.istack.internal.NotNull
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestMethod
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.ResponseBody

/**
 * Created by Administrator on 2017/6/13 0013.
 * <p>前台的产品控制器
 */
@Controller
@RequestMapping(value = "/product/")
class ProductController {
    @Autowired
    var iProductService: IProductService? = null

    /**
     *@param  categoryId 品类ID
     *@param keyword 关键字
     *@param pageIndex(default=1)
     *@param pageSize(default=10)
     *@param orderBy(default="")：排序参数：例如price_desc，price_asc
     */
    @RequestMapping(value = "getProductList.json")
    @ResponseBody
    fun getProductList(@RequestParam(value = "categoryId", defaultValue = "") categoryId: String,
                       @RequestParam(value = "keyword", defaultValue = "") keyword: String,
                       @RequestParam(value = "orderBy", defaultValue = "") orderBy: String,
                       @RequestParam(value = "pageIndex", defaultValue = "1") pageIndex: Int,
                       @RequestParam(value = "pageSize", defaultValue = "10") pageSize: Int): ServerResponse<PageInfo<Product>>? {

        //调用接口获取数据
        return iProductService!!.searchProductByCategoryIdOrLikeName(categoryId, keyword, orderBy, pageIndex, pageSize)
    }

    /**
     * 获取产品详情

     * @param productId 产品ID
     * *
     * @return
     */
    @RequestMapping(value = "getProductDetail.json", method = arrayOf(RequestMethod.POST))
    @ResponseBody
    fun getProductDetail(@NotNull productId: Int?): ServerResponse<Product> {
        //获取详情
        var productDetail = iProductService!!.getProductDetail(productId)
        //已经下架了
        if (productDetail.data.status == 0) {
            return ServerResponse.createByErrorMessage("该产品已下架")
        }
        return productDetail
    }
}