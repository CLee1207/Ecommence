package com.baosight.scc.ec.controller;

import com.baosight.scc.ec.model.InformationCategory;
import com.baosight.scc.ec.service.InformationCategoryService;
import com.baosight.scc.ec.web.EcGrid;
import com.google.common.collect.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Charles on 2014/6/5.
 */
@Controller
@RequestMapping("/informationCenter")
public class InformationCategoryController extends AbstractController{
    @Autowired
    private InformationCategoryService informationCategoryService;

    public final static String INFO_CATE_LIST = "info_cate_list";

    /**
     * 查看资讯分类列表
     * @param page
     * @param uiModel
     * @return
     */
    @RequestMapping(value = "/informationCategory", method = RequestMethod.GET)
    public String findAll(@RequestParam(value = "page", required = false,defaultValue = "1") Integer page,@RequestParam(value = "size", required = false,defaultValue = "15") Integer size,
                          Model uiModel){
        Sort sort = new Sort(Sort.Direction.DESC, "createdTime");
        PageRequest pageRequest = null;
        if (page != null)
            pageRequest = new PageRequest(page - 1, size, sort);
        else
            pageRequest = new PageRequest(0, size, sort);
        EcGrid<InformationCategory> grid = new EcGrid<InformationCategory>();
        Page<InformationCategory> informationCategoryPage = informationCategoryService.findByIsValid(0,pageRequest);
        grid.setCurrentPage(informationCategoryPage.getNumber() + 1);
        grid.setEcList(Lists.newArrayList(informationCategoryPage));
        grid.setTotalPages(informationCategoryPage.getTotalPages());
        grid.setTotalRecords(informationCategoryPage.getTotalElements());

        uiModel.addAttribute("grid", grid);
        return INFO_CATE_LIST;
    }

    /**
     * 资讯分类增加
     * @param request
     * @return
     */
    @RequestMapping(value = "/informationCategory", params = "add", method = RequestMethod.POST,produces = "application/json")
    @ResponseBody
    public Map save(HttpServletRequest request) {
        String id = request.getParameter("id");
        String categoryName = request.getParameter("categoryName");
        Map<String,String> result = new HashMap<String, String>();
        InformationCategory informationCategory = new InformationCategory();
        informationCategory.setCategoryName(categoryName);
        if(id != null && id != ""){
            informationCategoryService.update(informationCategory,id);
        }else{
            informationCategoryService.save(informationCategory);
        }
        result.put("result","success");
        return result;
    }

    /**
     * 资讯分类查看
     * @param request
     * @return
     */
    @RequestMapping(value = "/informationCategory", params = "view", method = RequestMethod.POST,produces = "application/json")
    @ResponseBody
    public Map view(HttpServletRequest request) {
        String id = request.getParameter("id");
        Map<String,String> result = new HashMap<String, String>();
        InformationCategory informationCategory = new InformationCategory();
        if(id == null || id == ""){
            result.put("result","error");
        }else{
            informationCategory = informationCategoryService.findById(id);
            result.put("categoryName",informationCategory.getCategoryName());
            result.put("result","success");
        }
        return result;
    }

    /**
     * 资讯分类删除
     * @param request
     * @return
     */
    @RequestMapping(value = "/informationCategory", params = "delete", method = RequestMethod.POST,produces = "application/json")
    @ResponseBody
    public Map delete(HttpServletRequest request) {
        String id = request.getParameter("id");
        Map<String,String> result = new HashMap<String, String>();
        if(id == null || id == ""){
            result.put("result","error");
        }else{
            informationCategoryService.delete(id);
            result.put("result","success");
        }
        return result;
    }
}
