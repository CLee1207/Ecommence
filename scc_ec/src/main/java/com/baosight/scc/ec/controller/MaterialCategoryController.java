package com.baosight.scc.ec.controller;

import com.baosight.scc.ec.model.MaterialCategory;
import com.baosight.scc.ec.service.MaterialCategoryService;
import com.baosight.scc.ec.web.MaterialCategoryJSON;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.List;

@Controller
public class MaterialCategoryController extends AbstractController {
    final Logger logger = LoggerFactory.getLogger(MaterialCategoryController.class);

    @Autowired
    private MaterialCategoryService materialCategoryService;

    /**
     * 查询辅料二级分类
     *
     * @param id 一级分类id
     * @return
     */
    @RequestMapping(value = "/materialCategory/{id}/secondCategory", method = RequestMethod.GET, produces = "application/json")
    @ResponseBody
    public List<MaterialCategoryJSON> getSecondCategoryByPid(@PathVariable("id") String id) {
        List<MaterialCategory> list = new ArrayList<MaterialCategory>();
        MaterialCategory category = new MaterialCategory();
        category.setId(id);
        return materialCategoryService.findByParentCategory(category);
    }
}

