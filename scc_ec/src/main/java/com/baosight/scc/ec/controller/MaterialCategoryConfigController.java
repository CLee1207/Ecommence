package com.baosight.scc.ec.controller;

import com.baosight.scc.ec.model.MaterialCategory;
import com.baosight.scc.ec.model.Message;
import com.baosight.scc.ec.service.MaterialCategoryService;
import com.baosight.scc.ec.web.EcGrid;
import com.baosight.scc.ec.web.MaterialCategoryJSON;
import com.google.common.collect.Lists;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

@Controller
@RequestMapping("/systemConfig")
public class MaterialCategoryConfigController extends AbstractController {
    final Logger logger = LoggerFactory.getLogger(MaterialCategoryConfigController.class);

    @Autowired
    private MaterialCategoryService materialCategoryService;

    @Autowired
    private MessageSource messageSource;

    public final static String FIRST_LIST = "firstMaterial_list";
    public final static String FIRST_ADD = "firstMaterial_add";
    public final static String FIRST_EDIT = "firstMaterial_edit";
    public final static String FIRST_REDIRECT_LIST = "redirect:/systemConfig/materialFirstCategory";

    public final static String SECOND_LIST = "secondMaterial_list";
    public final static String SECOND_ADD = "secondMaterial_add";
    public final static String SECOND_EDIT = "secondMaterial_edit";
    public final static String SECOND_REDIRECT_LIST = "redirect:/systemConfig/materialSecondCategory";

    /**
     * 查看一级分类列表
     * @param page
     * @param uiModel
     * @return
     */
    @RequestMapping(value = "/materialFirstCategory", method = RequestMethod.GET)
    public String findFirstCategory(@RequestParam(value = "page", required = false,defaultValue = "1") Integer page,@RequestParam(value = "size", required = false,defaultValue = "15") Integer size, Model uiModel){
        Sort sort = new Sort(Sort.Direction.DESC, "createdTime");
        PageRequest pageRequest = null;
        if (page != null)
            pageRequest = new PageRequest(page - 1, size, sort);
        else
            pageRequest = new PageRequest(0, size, sort);
        EcGrid<MaterialCategory> grid = new EcGrid<MaterialCategory>();
        Page<MaterialCategory> fabricCategoryPage = materialCategoryService.findAllFirstCategoryByPage(pageRequest);
        grid.setCurrentPage(fabricCategoryPage.getNumber() + 1);
        grid.setEcList(Lists.newArrayList(fabricCategoryPage));
        grid.setTotalPages(fabricCategoryPage.getTotalPages());
        grid.setTotalRecords(fabricCategoryPage.getTotalElements());

        uiModel.addAttribute("grid", grid);
        return FIRST_LIST;
    }

    /**
     * 创建form让用户输入
     */
    @RequestMapping(value = "/materialFirstCategory", params = "form", method = RequestMethod.GET)
    public String createFirstCategoryForm(Model uiModel) {
        MaterialCategory firstCategory = new MaterialCategory();
        List<MaterialCategory> materialCategories = materialCategoryService.findAllFirstCategory();
        uiModel.addAttribute("firstCategory",firstCategory);
        uiModel.addAttribute("materialCategories",materialCategories);
        return FIRST_ADD;
    }

    /**
     * 创建form让用户编辑
     */
    @RequestMapping(value = "/materialFirstCategory/edit/{id}", method = RequestMethod.GET)
    public String editFirstCategoryForm(@PathVariable("id") String id,Model uiModel) {
        MaterialCategory firstCategory = materialCategoryService.findById(id);
        List<MaterialCategory> materialCategories = materialCategoryService.findAllFirstCategory();
        uiModel.addAttribute("firstCategory",firstCategory);
        uiModel.addAttribute("materialCategories",materialCategories);
        return FIRST_EDIT;
    }

    /**
     * 提交广告信息表单,根据提交信息中的id是否有值来进行insert和update操作
     * @param uiModel
     * @param materialCategory
     * @param result
     * @param locale
     * @return
     */
    @RequestMapping(value="/materialFirstCategory",params = "form",method = RequestMethod.POST)
    public String saveFirstCategory(Model uiModel,@Valid @ModelAttribute("firstCategory")MaterialCategory materialCategory,BindingResult result,Locale locale,HttpServletRequest request){
        if(result.hasErrors()){
            uiModel.addAttribute("message", new Message("error", messageSource.getMessage("firstCategory.required", new Object[]{}, locale)));
            uiModel.addAttribute("firstCategory", materialCategory);
            return FIRST_ADD;
        }
        else {
            String id = request.getParameter("id");
            if(id == "" || id == null){
                materialCategoryService.save(materialCategory);
            }else {
                materialCategory.setId(id);
                materialCategoryService.update(materialCategory);
            }
            return FIRST_REDIRECT_LIST;
        }
    }

    /***********************************************二级分类维护*****************************************
     * 查看二级分类列表
     * @param page
     * @param uiModel
     * @return
     */
    @RequestMapping(value = "/materialSecondCategory", method = RequestMethod.GET)
    public String findSecondCategory(@RequestParam(value = "page", required = false,defaultValue = "1") Integer page,@RequestParam(value = "size", required = false,defaultValue = "15") Integer size, Model uiModel){
        Sort sort = new Sort(Sort.Direction.DESC, "createdTime");
        PageRequest pageRequest = null;
        if (page != null)
            pageRequest = new PageRequest(page - 1, size, sort);
        else
            pageRequest = new PageRequest(0, size, sort);
        EcGrid<MaterialCategory> grid = new EcGrid<MaterialCategory>();
        Page<MaterialCategory> fabricCategoryPage = materialCategoryService.findAllSecondCategoryByPage(pageRequest);
        grid.setCurrentPage(fabricCategoryPage.getNumber() + 1);
        grid.setEcList(Lists.newArrayList(fabricCategoryPage));
        grid.setTotalPages(fabricCategoryPage.getTotalPages());
        grid.setTotalRecords(fabricCategoryPage.getTotalElements());

        uiModel.addAttribute("grid", grid);
        return SECOND_LIST;
    }

    /**
     * 创建form让用户输入
     */
    @RequestMapping(value = "/materialSecondCategory", params = "form", method = RequestMethod.GET)
    public String createSecondCategoryForm(Model uiModel) {
        MaterialCategory secondCategory = new MaterialCategory();
        List<MaterialCategory> firstCategoryList = materialCategoryService.findFirstCategoryByIsValid(0);
        List<MaterialCategory> secondCategoryList = materialCategoryService.findAllSecondCategory();
        uiModel.addAttribute("secondCategory",secondCategory);
        uiModel.addAttribute("firstCategoryList",firstCategoryList);
        uiModel.addAttribute("secondCategoryList",secondCategoryList);
        return SECOND_ADD;
    }

    /**
     * 创建form让用户编辑
     */
    @RequestMapping(value = "/materialSecondCategory/edit/{id}", method = RequestMethod.GET)
    public String editSecondCategoryForm(@PathVariable("id") String id,Model uiModel) {
        MaterialCategory secondCategory = materialCategoryService.findById(id);
        List<MaterialCategory> firstCategoryList = materialCategoryService.findFirstCategoryByIsValid(0);
        List<MaterialCategory> secondCategoryList = materialCategoryService.findAllSecondCategory();
        uiModel.addAttribute("secondCategory",secondCategory);
        uiModel.addAttribute("firstCategoryList",firstCategoryList);
        uiModel.addAttribute("secondCategoryList",secondCategoryList);
        return SECOND_EDIT;
    }

    /**
     * 提交广告信息表单,根据提交信息中的id是否有值来进行insert和update操作
     * @param uiModel
     * @param secondCategory
     * @param result
     * @param locale
     * @return
     */
    @RequestMapping(value="/materialSecondCategory",params = "form",method = RequestMethod.POST)
    public String saveSecondCategory(Model uiModel,@Valid @ModelAttribute("secondCategory")MaterialCategory secondCategory,BindingResult result,Locale locale,HttpServletRequest request){
        if(result.hasErrors()){
            uiModel.addAttribute("message", new Message("error", messageSource.getMessage("secondCategory.required", new Object[]{}, locale)));
            uiModel.addAttribute("secondCategory", secondCategory);
            return SECOND_ADD;
        }
        else {
            String id = request.getParameter("id");

            if(id == "" || id == null){
                materialCategoryService.save(secondCategory);
            }else {
                secondCategory.setId(id);
                materialCategoryService.update(secondCategory);
            }
            return SECOND_REDIRECT_LIST;
        }
    }
}

