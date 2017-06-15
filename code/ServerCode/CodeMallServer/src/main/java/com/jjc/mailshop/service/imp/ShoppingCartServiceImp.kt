package com.jjc.mailshop.service.imp

import com.github.pagehelper.PageHelper
import com.jjc.mailshop.common.ServerResponse
import com.jjc.mailshop.dao.CartMapper
import com.jjc.mailshop.dao.ProductMapper
import com.jjc.mailshop.pojo.Cart
import com.jjc.mailshop.service.IShoppingCartService
import com.jjc.mailshop.vo.CartProductVo
import com.jjc.mailshop.vo.CartProductVoList
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

/**
 * Created by Administrator on 2017/6/14 0014.
 */
@Service(value = "iShoppingCartService")
class ShoppingCartServiceImp : IShoppingCartService {

    @Autowired
    var mCartMapper: CartMapper? = null

    @Autowired
    var mProductMapper: ProductMapper? = null


    override fun getShoppingCartList(userId: Int, pageIndex: Int, pageSize: Int): ServerResponse<CartProductVoList> {
        //查询出购物车的数据
        PageHelper.startPage<Cart>(pageIndex, pageSize)
        val mCarts = mCartMapper!!.selectByUserId(userId)


        val cartProductVoList: CartProductVoList = CartProductVoList()

        //根据购物车中的id，查询产品详情
        cartProductVoList.cartProductVoList = mCarts.map {
            //查询产品
            val product = mProductMapper!!.selectByPrimaryKey(it.productId)
            //转换数据
            val cv: CartProductVo = CartProductVo()
            cv.id = it.id
            cv.userId = it.userId
            cv.quantity = it.quantity
            product.detail = ""
            cv.product = product
            //还有一些数据没有存放
            cv
        }

        return ServerResponse.createBySuccess("查询成功", cartProductVoList)

    }

    override fun addShoppingCard(userId: Int, productId: Int, count: Int): ServerResponse<String> {
        //判断产品是否存在
        mProductMapper!!.selectByPrimaryKey(productId) ?: return ServerResponse.createByErrorMessage("该产品不存在")
        //判断是否已经下架了？？？？

        //判断原先是否存在了该商品
        val exitCart = mCartMapper!!.selectByProductId(productId, userId)

        //没有查到相关的记录  新增
        if (exitCart == null) {
            //创建数据对象
            val cart: Cart = Cart()
            cart.checked = 0
            cart.productId = productId
            cart.userId = userId
            cart.quantity = count

            //插入数据
            if (mCartMapper!!.insertSelective(cart) > 0) {
                return ServerResponse.createBySuccess("新增成功")
            }
        } else {
            //调用修改方法进行修改
            if (updateShoppingCard(userId, productId, exitCart.quantity + count).isSuccess) {
                return ServerResponse.createBySuccess("修改成功")
            }
        }

        return ServerResponse.createByErrorMessage("新增失败")

    }

    override fun updateShoppingCard(userId: Int, productId: Int, count: Int): ServerResponse<CartProductVoList> {

        //判断产品是否存在
        mProductMapper!!.selectByPrimaryKey(productId) ?: return ServerResponse.createByErrorMessage("该产品不存在")
        //判断是否已经下架了？？？？

        //判断原先是否存在了该商品
        val exitCart = mCartMapper!!.selectByProductId(productId, userId)
        exitCart?.quantity = count

        //查到相关的记录  修改
        if (exitCart != null && mCartMapper!!.updateByPrimaryKeySelective(exitCart) > 0) {
            //调用修改方法 进行修改
            return ServerResponse.createBySuccess("修改成功", null)
        } else {
            //新增记录
            if (addShoppingCard(userId, productId, count).isSuccess) {
                return ServerResponse.createBySuccess("新增成功", null)
            }
        }

        return ServerResponse.createByErrorMessage("修改失败")
    }

    override fun removeShoppingCard(userId: Int,  cartId: Array<Int>): ServerResponse<CartProductVoList> {
        //循环遍历删除
        cartId.forEach {
            mCartMapper!!.deleteByPrimaryKey(it)
        }

        return ServerResponse.createBySuccess("删除成功", null)
    }

    override fun checkShoppingCard(userId: Int, cartId: Int): ServerResponse<CartProductVoList> {
        //??或许应该直接进行修改？？？

        //查询该记录
        val mCartInfo = mCartMapper!!.selectByPrimaryKey(cartId) ?: return ServerResponse.createByErrorMessage("该记录不存在")

        mCartInfo.checked = 1

        if (mCartMapper!!.updateByPrimaryKeySelective(mCartInfo) > 0) {
            return ServerResponse.createBySuccess("选中成功", null)
        }
        return ServerResponse.createByErrorMessage("选中失败")
    }

    override fun unCheckShoppingCard(userId: Int, cartId: Int): ServerResponse<CartProductVoList> {
        //??或许应该直接进行修改？？？

        //查询该记录
        val mCartInfo = mCartMapper!!.selectByPrimaryKey(cartId) ?: return ServerResponse.createByErrorMessage("该记录不存在")

        mCartInfo.checked = 0

        if (mCartMapper!!.updateByPrimaryKeySelective(mCartInfo) > 0) {
            return ServerResponse.createBySuccess("取消选中成功", null)
        }
        return ServerResponse.createByErrorMessage("取消选中失败")
    }

    override fun getShoppingCardCount(userId: Int): ServerResponse<CartProductVoList> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun unCheckAllShoppingCard(userId: Int): ServerResponse<CartProductVoList> {
        if (mCartMapper!!.updateAllChecked(0, userId) > 0) {
            return ServerResponse.createBySuccess("取消选中成功", null)
        }
        return ServerResponse.createByErrorMessage("取消选中失败")
    }

    override fun checkAllShoppingCard(userId: Int): ServerResponse<CartProductVoList> {
        if (mCartMapper!!.updateAllChecked(1, userId) > 0) {
            return ServerResponse.createBySuccess("选中成功", null)
        }
        return ServerResponse.createByErrorMessage("选中失败")
    }
}