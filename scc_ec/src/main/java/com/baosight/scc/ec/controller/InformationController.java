package com.baosight.scc.ec.controller;

import com.baosight.scc.ec.model.Information;
import com.baosight.scc.ec.model.InformationCategory;
import com.baosight.scc.ec.model.Message;
import com.baosight.scc.ec.service.InformationCategoryService;
import com.baosight.scc.ec.service.InformationService;
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
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

/**
 * Created by Charles on 2014/6/5.
 */
@Controller
@RequestMapping("/informationCenter")
public class InformationController extends AbstractController{
    @Autowired
    private InformationService informationService;
    @Autowired
    private InformationCategoryService informationCategoryService;
    @Autowired
    private MessageSource messageSource;

    public final static String INFO_LIST = "info_list";
    public final static String INFO_CREATE = "info_create";
    public final static String INFO_EDIT = "info_edit";
    public final static String INFO_VIEW = "info_view";
    public final static String REDIRECT_LIST = "redirect:/informationCenter/information";
    /**
     * 查看资讯分类列表
     * @param page
     * @param uiModel
     * @return
     */
    @RequestMapping(value = "/information", method = RequestMethod.GET)
    public String findAll(@RequestParam(value = "page", required = false,defaultValue = "1") Integer page,@RequestParam(value = "size", required = false,defaultValue = "15") Integer size,
                          Model uiModel){
        Sort sort = new Sort(Sort.Direction.DESC, "createdTime");
        PageRequest pageRequest = null;
        if (page != null)
            pageRequest = new PageRequest(page - 1, size, sort);
        else
            pageRequest = new PageRequest(0, size, sort);
        EcGrid<Information> grid = new EcGrid<Information>();
        Page<Information> informationPage = informationService.findByIsValid(0,pageRequest);
        grid.setCurrentPage(informationPage.getNumber() + 1);
        grid.setEcList(Lists.newArrayList(informationPage));
        grid.setTotalPages(informationPage.getTotalPages());
        grid.setTotalRecords(informationPage.getTotalElements());

        uiModel.addAttribute("grid", grid);
        return INFO_LIST;
    }

    /**
     * 创建form让用户输入
     */
    @RequestMapping(value = "/information", params = "form", method = RequestMethod.GET)
    public String createForm(Model uiModel) {
        Information information = new Information();
        List<InformationCategory> informationCategoryList = informationCategoryService.findByIsValid(0);
        uiModel.addAttribute("information",information);
        uiModel.addAttribute("informationCategoryList",informationCategoryList);
        return INFO_CREATE;
    }

    /**
     * 创建form让用户编辑
     */
    @RequestMapping(value = "/information/edit/{id}", method = RequestMethod.GET)
    public String editForm(@PathVariable("id") String id,Model uiModel) {
        Information information = informationService.findById(id);
        List<InformationCategory> informationCategoryList = informationCategoryService.findByIsValid(0);
        uiModel.addAttribute("information",information);
        uiModel.addAttribute("informationCategoryList",informationCategoryList);
        return INFO_EDIT;
    }

    /**
     * 提交广告信息表单,根据提交信息中的id是否有值来进行insert和update操作
     * @param uiModel
     * @param information
     * @param result
     * @param locale
     * @return
     */
    @RequestMapping(value="/information",params = "form",method = RequestMethod.POST)
    public String save(Model uiModel,@Valid @ModelAttribute("advertisement")Information information,BindingResult result,Locale locale,HttpServletRequest request){
        if(result.hasErrors()){
            uiModel.addAttribute("message", new Message("error", messageSource.getMessage("fabric.required", new Object[]{}, locale)));
            uiModel.addAttribute("information", information);
            return INFO_CREATE;
        }
        else {
            String id = request.getParameter("id");
            String category_id = request.getParameter("category_id");
            InformationCategory informationCategory = new InformationCategory();
            informationCategory.setId(category_id);
            information.setInformationCategory(informationCategory);
            if(id == "" || id == null){
                informationService.save(information);
            }else {
                information.setIsValid(0);
                informationService.update(information,id);
            }
            return REDIRECT_LIST;
        }
    }

    /**
     * 查看资讯信息详情
     * @param id
     * @param uiModel
     * @return
     */
    @RequestMapping(value = "/information/{id}", method = RequestMethod.GET)
    public String view(@PathVariable("id") String id, Model uiModel) {
        Information information = informationService.findById(id);
        uiModel.addAttribute("information", information);
        return INFO_VIEW;
    }

    /**
     * 资讯删除
     * @param request
     * @return
     */
    @RequestMapping(value = "/information", params = "delete", method = RequestMethod.POST,produces = "application/json")
    @ResponseBody
    public Map delete(HttpServletRequest request) {
        String id = request.getParameter("id");
        Map<String,String> result = new HashMap<String, String>();
        if(id == null || id == ""){
            result.put("result","error");
        }else{
            informationService.delete(id);
            result.put("result","success");
        }
        return result;
    }
}
