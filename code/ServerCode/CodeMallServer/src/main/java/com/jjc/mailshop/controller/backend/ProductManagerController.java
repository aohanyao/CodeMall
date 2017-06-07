package com.jjc.mailshop.controller.backend;

import com.jjc.mailshop.service.IProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created by jjc on 2017/6/7.
 * <p>产品管理控制器</p>
 */
@RequestMapping(value = "/manager/product/")
@Controller
public class ProductManagerController {
    @Autowired
    IProductService iProductService;
}
