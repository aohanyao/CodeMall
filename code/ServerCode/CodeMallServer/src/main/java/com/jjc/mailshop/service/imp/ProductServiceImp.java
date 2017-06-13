package com.jjc.mailshop.service.imp;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.jjc.mailshop.common.ServerResponse;
import com.jjc.mailshop.dao.ProductMapper;
import com.jjc.mailshop.pojo.Product;
import com.jjc.mailshop.service.IProductService;
import org.apache.commons.collections.ListUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by jjc on 2017/6/7.
 */
@Service(value = "iProductService")
public class ProductServiceImp implements IProductService {
    @Autowired
    ProductMapper productMapper;

    @Override
    public ServerResponse<Product> getProductDetail(Integer productId) {
        Product product = productMapper.selectByPrimaryKey(productId);
        //不存在
        if (product == null) {
            return ServerResponse.createByErrorMessage("找不到该产品");
        }
        //已经下架的是否返回
//        if (product.getStatus() == null || product.getStatus() == 0) {
//            return ServerResponse.createByErrorMessage("改产品已下架");
//        }

        //返回
        return ServerResponse.createBySuccess("查询成功", product);
    }

    @Override
    public ServerResponse<String> setSaleStatus(Integer productId, Integer status) {
        //创建数据对象
        Product product = new Product();
        product.setId(productId);
        product.setStatus(status);

        //调用mapper进行修改 受影响行数大于0
        if (productMapper.updateByPrimaryKeySelective(product) > 0) {
            return ServerResponse.createBySuccessMessage("操作成功");
        }

        //操作失败
        return ServerResponse.createByErrorMessage("操作失败");
    }

    @Override
    public ServerResponse<String> updateProduct(Product product) {
        //判断受影响行数大于0
        if (productMapper.updateByPrimaryKeySelective(product) > 0) {
            return ServerResponse.createBySuccessMessage("修改成功");
        }
        //修改失败
        return ServerResponse.createByErrorMessage("修改失败");
    }

    @Override
    public ServerResponse<String> addProduct(Product product) {
        //判断受影响行数大于0
        if (productMapper.insert(product) > 0) {
            return ServerResponse.createBySuccessMessage("新增成功");
        }
        //修改失败
        return ServerResponse.createByErrorMessage("新增失败");
    }

    @Override
    public ServerResponse<PageInfo<Product>> getProductList(int pageIndex, int pageSize) {
        //查询数据
        PageHelper.startPage(pageIndex, pageSize);
        List<Product> allProduct = productMapper.getAllProduct();
        PageInfo<Product> pageInfo = new PageInfo<>(allProduct);
        pageInfo.setList(allProduct);
        //返回数据
        return ServerResponse.createBySuccess("查询成功", pageInfo);
    }

    @Override
    public ServerResponse<PageInfo<Product>> searchProductList(String productName, String productId, int pageIndex, int pageSize) {
        //判断名字是否为空  拼接字符串
        if (StringUtils.isNotBlank(productName)) {
            productName = new StringBuilder("%").append(productName).append("%").toString();
        } else {
            productName = null;
        }
        if (StringUtils.isBlank(productId)) {
            productId = null;
        }
        //开始分页
        PageHelper.startPage(pageIndex, pageSize);
        //获取到数据
        List<Product> products = productMapper.searchProductByNameOrId(productId, productName);
        //装箱数据
        PageInfo<Product> pageInfo = new PageInfo<>(products);

        if (products.size() == 0) {
            return ServerResponse.createBySuccess("没有找到相关数据", pageInfo);
        }
        //返回成功数据
        return ServerResponse.createBySuccess("搜索成功", pageInfo);

    }
}
