package com.baosight.scc.ec.controller;

import com.baosight.scc.ec.model.FabricSource;
import com.baosight.scc.ec.model.Message;
import com.baosight.scc.ec.service.FabricSourceService;
import com.baosight.scc.ec.web.EcGrid;
import com.google.common.collect.Lists;
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
import java.util.List;
import java.util.Locale;

/**
 * Created by Charles on 2014/6/9.
 */
@Controller
@RequestMapping("/systemConfig")
public class FabricSourceController extends AbstractController{
    @Autowired
    private FabricSourceService fabricSourceService;

    @Autowired
    private MessageSource messageSource;

    public final static String FIRST_LIST = "firstSource_list";
    public final static String FIRST_ADD = "firstSource_add";
    public final static String FIRST_EDIT = "firstSource_edit";
    public final static String FIRST_REDIRECT_LIST = "redirect:/systemConfig/firstSource";

    public final static String SECOND_LIST = "secondSource_list";
    public final static String SECOND_ADD = "secondSource_add";
    public final static String SECOND_EDIT = "secondSource_edit";
    public final static String SECOND_REDIRECT_LIST = "redirect:/systemConfig/secondSource";

    /**
     * 查看一级分类列表
     * @param page
     * @param uiModel
     * @return
     */
    @RequestMapping(value = "/firstSource", method = RequestMethod.GET)
    public String findFirstCategory(@RequestParam(value = "page", required = false,defaultValue = "1") Integer page,@RequestParam(value = "size", required = false,defaultValue = "15") Integer size, Model uiModel){
        Sort sort = new Sort(Sort.Direction.DESC, "createdTime");
        PageRequest pageRequest = null;
        if (page != null)
            pageRequest = new PageRequest(page - 1, size, sort);
        else
            pageRequest = new PageRequest(0, size, sort);
        EcGrid<FabricSource> grid = new EcGrid<FabricSource>();
        Page<FabricSource> fabricCategoryPage = fabricSourceService.findAllFirstCategoryByPage(pageRequest);
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
    @RequestMapping(value = "/firstSource", params = "form", method = RequestMethod.GET)
    public String createFirstCategoryForm(Model uiModel) {
        FabricSource firstCategory = new FabricSource();
        List<FabricSource> fabricSources = fabricSourceService.findAllFirstCategory();
        uiModel.addAttribute("firstCategory",firstCategory);
        uiModel.addAttribute("fabricCategories",fabricSources);
        return FIRST_ADD;
    }

    /**
     * 创建form让用户编辑
     */
    @RequestMapping(value = "/firstSource/edit/{id}", method = RequestMethod.GET)
    public String editFirstCategoryForm(@PathVariable("id") String id,Model uiModel) {
        FabricSource fabricSource = fabricSourceService.findById(id);
        List<FabricSource> fabricSources = fabricSourceService.findAllFirstCategory();
        uiModel.addAttribute("firstCategory",fabricSource);
        uiModel.addAttribute("fabricCategories",fabricSources);
        return FIRST_EDIT;
    }

    /**
     * 提交广告信息表单,根据提交信息中的id是否有值来进行insert和update操作
     * @param uiModel
     * @param firstCategory
     * @param result
     * @param locale
     * @return
     */
    @RequestMapping(value="/firstSource",params = "form",method = RequestMethod.POST)
    public String saveFirstCategory(Model uiModel,@Valid @ModelAttribute("firstCategory")FabricSource firstCategory,BindingResult result,Locale locale,HttpServletRequest request){
        if(result.hasErrors()){
            uiModel.addAttribute("message", new Message("error", messageSource.getMessage("firstCategory.required", new Object[]{}, locale)));
            uiModel.addAttribute("firstCategory", firstCategory);
            return FIRST_ADD;
        }
        else {
            String id = request.getParameter("id");
            if(id == "" || id == null){
                fabricSourceService.save(firstCategory);
            }else {
                firstCategory.setId(id);
                fabricSourceService.update(firstCategory);
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
    @RequestMapping(value = "/secondSource", method = RequestMethod.GET)
    public String findSecondCategory(@RequestParam(value = "page", required = false,defaultValue = "1") Integer page,@RequestParam(value = "size", required = false,defaultValue = "15") Integer size, Model uiModel){
        Sort sort = new Sort(Sort.Direction.DESC, "createdTime");
        PageRequest pageRequest = null;
        if (page != null)
            pageRequest = new PageRequest(page - 1, size, sort);
        else
            pageRequest = new PageRequest(0, size, sort);
        EcGrid<FabricSource> grid = new EcGrid<FabricSource>();
        Page<FabricSource> fabricCategoryPage = fabricSourceService.findAllSecondCategoryByPage(pageRequest);
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
    @RequestMapping(value = "/secondSource", params = "form", method = RequestMethod.GET)
    public String createSecondCategoryForm(Model uiModel) {
        FabricSource secondCategory = new FabricSource();
        List<FabricSource> firstCategoryList = fabricSourceService.findFirstCategoryByIsValid(0);
        List<FabricSource> secondCategoryList = fabricSourceService.findAllSecondCategory();
        uiModel.addAttribute("secondCategory",secondCategory);
        uiModel.addAttribute("firstCategoryList",firstCategoryList);
        uiModel.addAttribute("secondCategoryList",secondCategoryList);
        return SECOND_ADD;
    }

    /**
     * 创建form让用户编辑
     */
    @RequestMapping(value = "/secondSource/edit/{id}", method = RequestMethod.GET)
    public String editSecondCategoryForm(@PathVariable("id") String id,Model uiModel) {
        FabricSource secondCategory = fabricSourceService.findById(id);
        List<FabricSource> firstCategoryList = fabricSourceService.findFirstCategoryByIsValid(0);
        List<FabricSource> secondCategoryList = fabricSourceService.findAllSecondCategory();
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
    @RequestMapping(value="/secondSource",params = "form",method = RequestMethod.POST)
    public String saveSecondCategory(Model uiModel,@Valid @ModelAttribute("secondCategory")FabricSource secondCategory,BindingResult result,Locale locale,HttpServletRequest request){
        if(result.hasErrors()){
            uiModel.addAttribute("message", new Message("error", messageSource.getMessage("secondCategory.required", new Object[]{}, locale)));
            uiModel.addAttribute("secondCategory", secondCategory);
            return SECOND_ADD;
        }
        else {
            String id = request.getParameter("id");

            if(id == "" || id == null){
                fabricSourceService.save(secondCategory);
            }else {
                secondCategory.setId(id);
                fabricSourceService.update(secondCategory);
            }
            return SECOND_REDIRECT_LIST;
        }
    }

}
