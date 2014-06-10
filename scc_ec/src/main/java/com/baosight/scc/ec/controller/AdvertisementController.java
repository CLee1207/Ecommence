package com.baosight.scc.ec.controller;

import com.baosight.scc.ec.model.Advertisement;
import com.baosight.scc.ec.model.AdvertisementPosition;
import com.baosight.scc.ec.model.Message;
import com.baosight.scc.ec.service.AdvertisementPositionService;
import com.baosight.scc.ec.service.AdvertisementService;
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
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

/**
 * Created by Charles on 2014/6/3.
 */
@Controller
@RequestMapping("/informationCenter")
public class AdvertisementController extends AbstractController {
    @Autowired
    private AdvertisementService advertisementService;
    @Autowired
    private AdvertisementPositionService advertisementPositionService;
    @Autowired
    private MessageSource messageSource;

    public final static String AD_CREATE = "ad_create";
    public final static String AD_EDIT = "ad_edit";
    public final static String AD_VIEW = "ad_view";
    public final static String AD_LIST = "ad_list";
    public final static String REDIRECT_LIST = "redirect:/informationCenter/advertisement";

    /**
     * 查看广告栏位列表
     * @param page
     * @param uiModel
     * @return
     */
    @RequestMapping(value = "/advertisement", method = RequestMethod.GET)
    public String findAll(@RequestParam(value = "page", required = false,defaultValue = "1") Integer page,@RequestParam(value = "size", required = false,defaultValue = "15") Integer size, Model uiModel){
        Sort sort = new Sort(Sort.Direction.DESC, "createdTime");
        PageRequest pageRequest = null;
        if (page != null)
            pageRequest = new PageRequest(page - 1, size, sort);
        else
            pageRequest = new PageRequest(0, size, sort);
        EcGrid<Advertisement> grid = new EcGrid<Advertisement>();
        Page<Advertisement> advertisementPage = advertisementService.findByIsValid(0,pageRequest);
        grid.setCurrentPage(advertisementPage.getNumber() + 1);
        grid.setEcList(Lists.newArrayList(advertisementPage));
        grid.setTotalPages(advertisementPage.getTotalPages());
        grid.setTotalRecords(advertisementPage.getTotalElements());

        uiModel.addAttribute("grid", grid);
        return AD_LIST;
    }

    /**
     * 创建form让用户输入
     */
    @RequestMapping(value = "/advertisement", params = "form", method = RequestMethod.GET)
    public String createForm(Model uiModel) {
        Advertisement advertisement = new Advertisement();
        List<AdvertisementPosition> advertisementPositionList = advertisementPositionService.findByIsValid(0);
        uiModel.addAttribute("advertisement",advertisement);
        uiModel.addAttribute("advertisementPositionList",advertisementPositionList);
        return AD_CREATE;
    }

    /**
     * 创建form让用户编辑
     */
    @RequestMapping(value = "/advertisement/edit/{id}", method = RequestMethod.GET)
    public String editForm(@PathVariable("id") String id,Model uiModel) {
        Advertisement advertisement = advertisementService.findById(id);
        List<AdvertisementPosition> advertisementPositionList = advertisementPositionService.findByIsValid(0);
        uiModel.addAttribute("advertisement",advertisement);
        uiModel.addAttribute("advertisementPositionList",advertisementPositionList);
        return AD_EDIT;
    }

    /**
     * 提交广告信息表单,根据提交信息中的id是否有值来进行insert和update操作
     * @param uiModel
     * @param advertisement
     * @param result
     * @param locale
     * @return
     */
    @RequestMapping(value="/advertisement",params = "form",method = RequestMethod.POST)
    public String save(Model uiModel,@Valid @ModelAttribute("advertisement")Advertisement advertisement,BindingResult result,Locale locale,HttpServletRequest request){
        if(result.hasErrors()){
            uiModel.addAttribute("message", new Message("error", messageSource.getMessage("fabric.required", new Object[]{}, locale)));
            uiModel.addAttribute("advertisement", advertisement);
            return AD_CREATE;
        }
        else {
            String id = request.getParameter("id");
            String position_id = request.getParameter("position_id");
            AdvertisementPosition advertisementPosition = new AdvertisementPosition();
            advertisementPosition.setId(position_id);
            advertisement.setAdvertisementPosition(advertisementPosition);
            if(id == "" || id == null){
                advertisementService.save(advertisement);
            }else {
                advertisement.setIsValid(0);
                advertisementService.update(advertisement,id);
            }
            return REDIRECT_LIST;
        }
    }

    /**
     * 查看广告信息详情
     * @param id
     * @param uiModel
     * @return
     */
    @RequestMapping(value = "/advertisement/{id}", method = RequestMethod.GET)
    public String view(@PathVariable("id") String id, Model uiModel) {
        Advertisement advertisement = advertisementService.findById(id);
        uiModel.addAttribute("advertisement", advertisement);
        return AD_VIEW;
    }

    /**
     * 广告删除
     * @param request
     * @return
     */
    @RequestMapping(value = "/advertisement", params = "delete", method = RequestMethod.POST,produces = "application/json")
    @ResponseBody
    public Map delete(HttpServletRequest request) {
        String id = request.getParameter("id");
        Map<String,String> result = new HashMap<String, String>();
        Advertisement advertisement = new Advertisement();
        if(id == null || id == ""){
            result.put("result","error");
        }else{
            advertisement.setIsValid(1);
            advertisementService.update(advertisement,id);
            result.put("result","success");
        }
        return result;
    }
}
