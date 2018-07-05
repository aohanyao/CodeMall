package com.jjc.mailshop.service.imp

import com.github.pagehelper.PageHelper
import com.github.pagehelper.PageInfo
import com.jjc.mailshop.common.ServerResponse
import com.jjc.mailshop.dao.ShippingMapper
import com.jjc.mailshop.pojo.Shipping
import com.jjc.mailshop.service.IAddressService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

/**
 * Created by Administrator on 2017/6/15 0015.
 */
@Service(value = "iAddressService")
class AddressServiceImp : IAddressService {

    @Autowired
    var mShippingMapper: ShippingMapper? = null


    override fun createAddress(shipping: Shipping): ServerResponse<Shipping> {
        //调用接口插入数据
        if (mShippingMapper!!.insertSelective(shipping) > 0) {
            //返回当前数据
            return ServerResponse.createBySuccess("新增成功", shipping)
        }

        return ServerResponse.createByErrorMessage("新增失败")
    }

    override fun updateAddress(shipping: Shipping, userId: Int): ServerResponse<Shipping> {
        //查询地址详情
        val addressDetails = getAddressDetails(shipping.id, userId)
        //不成功
        if (!addressDetails.isSuccess) {
            return ServerResponse.createByErrorMessage(addressDetails.message)
        }

        //调用 修改数据
        if (mShippingMapper!!.updateByPrimaryKeySelective(shipping) > 0) {
            return ServerResponse.createBySuccessMessage("修改成功")
        }

        return ServerResponse.createByErrorMessage("修改失败")
    }

    override fun deleteAddress(shippingId: Int, userId: Int): ServerResponse<String> {
        //查询地址详情
        val addressDetails = getAddressDetails(shippingId, userId)
        //不成功
        if (!addressDetails.isSuccess) {
            return ServerResponse.createByErrorMessage(addressDetails.message)
        }

        //调用 删除数据
        if (mShippingMapper!!.deleteByPrimaryKey(shippingId) > 0) {
            return ServerResponse.createBySuccessMessage("删除成功")
        }

        return ServerResponse.createByErrorMessage("删除失败")
    }

    override fun getAddressDetails(shippingId: Int, userId: Int): ServerResponse<Shipping> {
        //查询数据
        val addressDetails = mShippingMapper!!.selectByPrimaryKey(shippingId) ?: return ServerResponse.createByErrorMessage("找不到该地址")
        //判断是不是当前用户的
        if (addressDetails.userId != userId) {
            return ServerResponse.createByErrorMessage("查询不到该地址")
        }
        return ServerResponse.createBySuccess("查询成功", addressDetails)
    }

    override fun getAddressList(userId: String, pageIndex: Int, pageSize: Int): ServerResponse<PageInfo<Shipping>> {
        //设置分页
        PageHelper.startPage<Shipping>(pageIndex, pageSize)
        //调用 查询数据
        val pageInfo: PageInfo<Shipping> = PageInfo(mShippingMapper!!.selectByUserId(userId))

        //返回数据
        return ServerResponse.createBySuccess("查询成功", pageInfo)
    }
}