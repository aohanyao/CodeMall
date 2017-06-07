package com.jjc.mailshop.service;

import com.jjc.mailshop.common.ServerResponse;
import com.jjc.mailshop.pojo.Category;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpSession;
import java.util.List;

/**
 * Created by jjc on 2017/6/3.
 * <p>产品相关服务接口</p>
 */
public interface ICategoryService {
    /**
     * 获取品类子节点
     *
     * @param parentId 品类ID
     * @return
     */
    ServerResponse<List<Category>> getCategoryByParentId(String parentId);

    /**
     * 增加产品品类
     *
     * @param parentId
     * @param categoryName
     * @return
     */
    ServerResponse<String> addCategory(String parentId, String categoryName);

    /**
     * 修改品类
     *
     * @param categoryId
     * @param categoryName
     * @return
     */
    ServerResponse<String> updateCategory(String categoryId, String categoryName);
}
