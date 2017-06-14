package com.jjc.mailshop.vo

import com.jjc.mailshop.pojo.Product

/**
 * Created by Administrator on 2017/6/14 0014.
 */
class CartProductVo {

    /**
     * id : 1
     * userId : 13
     * productId : 1
     * quantity : 1
     * productName : iphone7
     * productSubtitle : 双十一促销
     * productMainImage : mainimage.jpg
     * productPrice : 7199.22
     * productStatus : 1
     * productTotalPrice : 7199.22
     * productStock : 86
     * productChecked : 1
     * limitQuantity : LIMIT_NUM_SUCCESS
     */

    var id: Int = 0
    var userId: Int = 0
    var quantity: Int = 0
    var productTotalPrice: Double = 0.toDouble()
    var productChecked: Int = 0
    var product: Product? = null
    var limitQuantity: String? = null
}
