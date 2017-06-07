package com.jjc.mailshop.service.imp;

import com.jjc.mailshop.dao.ProductMapper;
import com.jjc.mailshop.service.IProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by jjc on 2017/6/7.
 */
@Service(value = "iProductService")
public class ProductServiceImp implements IProductService {
    @Autowired
    ProductMapper productMapper;
}
