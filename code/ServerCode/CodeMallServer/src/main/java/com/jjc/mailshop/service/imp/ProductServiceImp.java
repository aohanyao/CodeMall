package com.jjc.mailshop.service.imp;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.jjc.mailshop.common.ServerResponse;
import com.jjc.mailshop.dao.ProductMapper;
import com.jjc.mailshop.pojo.Product;
import com.jjc.mailshop.service.IProductService;
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
    public ServerResponse<PageInfo> getProductList(Integer pageSize, Integer pageIndex) {
        //设置开始页数
        PageHelper.startPage(pageIndex, pageSize);
        List<Product> products = productMapper.selectProduct();

        PageInfo<List<Product>> pageInfo = new PageInfo(products);
        //返回数据
        return ServerResponse.createBySuccess("查询成功", pageInfo);
    }
}
