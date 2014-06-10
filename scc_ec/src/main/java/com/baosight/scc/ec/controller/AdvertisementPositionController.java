package com.baosight.scc.ec.controller;

import com.baosight.scc.ec.model.AdvertisementPosition;
import com.baosight.scc.ec.service.AdvertisementPositionService;
import com.baosight.scc.ec.web.EcGrid;
import com.google.common.collect.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Charles on 2014/6/3.
 */
@Controller
@RequestMapping("/informationCenter")
public class AdvertisementPositionController extends AbstractController {
    @Autowired
    private AdvertisementPositionService advertisementPositionService;

    public final static String ADP_LIST = "adp_list";

    /**
     * 查看广告栏位列表
     * @param page
     * @param uiModel
     * @return
     */
    @RequestMapping(value = "/advertisementPosition", method = RequestMethod.GET)
    public String findAll(@RequestParam(value = "page", required = false,defaultValue = "1") Integer page,@RequestParam(value = "size", required = false,defaultValue = "15") Integer size,
                          Model uiModel){
        Sort sort = new Sort(Sort.Direction.DESC, "createdTime");
        PageRequest pageRequest = null;
        if (page != null)
            pageRequest = new PageRequest(page - 1, size, sort);
        else
            pageRequest = new PageRequest(0, size, sort);
        EcGrid<AdvertisementPosition> grid = new EcGrid<AdvertisementPosition>();
        Page<AdvertisementPosition> advertisementPositionPage = advertisementPositionService.findByIsValid(0,pageRequest);
        grid.setCurrentPage(advertisementPositionPage.getNumber() + 1);
        grid.setEcList(Lists.newArrayList(advertisementPositionPage));
        grid.setTotalPages(advertisementPositionPage.getTotalPages());
        grid.setTotalRecords(advertisementPositionPage.getTotalElements());

        uiModel.addAttribute("grid", grid);
        return ADP_LIST;
    }

    /**
     * 广告栏位增加
     * @param request
     * @return
     */
    @RequestMapping(value = "/advertisementPosition", params = "add", method = RequestMethod.POST,produces = "application/json")
    @ResponseBody
    public Map save(HttpServletRequest request) {
        String id = request.getParameter("id");
        String positionNo = request.getParameter("positionNo");
        String name = request.getParameter("name");
        String description = request.getParameter("description");
        Map<String,String> result = new HashMap<String, String>();
        AdvertisementPosition advertisementPosition = new AdvertisementPosition();
        if(id != null && id != ""){
            advertisementPosition = advertisementPositionService.findById(id);
            advertisementPosition.setPositionNo(positionNo);
            advertisementPosition.setName(name);
            advertisementPosition.setDescription(description);
            advertisementPositionService.update(advertisementPosition);
        }else{
            advertisementPosition.setPositionNo(positionNo);
            advertisementPosition.setName(name);
            advertisementPosition.setDescription(description);
            advertisementPositionService.save(advertisementPosition);
        }


        result.put("result","success");
        return result;
    }

    /**
     * 广告栏位查看
     * @param request
     * @return
     */
    @RequestMapping(value = "/advertisementPosition", params = "view", method = RequestMethod.POST,produces = "application/json")
    @ResponseBody
    public Map view(HttpServletRequest request) {
        String id = request.getParameter("id");
        Map<String,String> result = new HashMap<String, String>();
        AdvertisementPosition advertisementPosition = new AdvertisementPosition();
        if(id == null || id == ""){
            result.put("result","error");
        }else{
            advertisementPosition = advertisementPositionService.findById(id);
            result.put("positionNo",advertisementPosition.getPositionNo());
            result.put("name",advertisementPosition.getName());
            result.put("description",advertisementPosition.getDescription());
            result.put("result","success");
        }
        return result;
    }

    /**
     * 广告栏位删除
     * @param request
     * @return
     */
    @RequestMapping(value = "/advertisementPosition", params = "delete", method = RequestMethod.POST,produces = "application/json")
    @ResponseBody
    public Map delete(HttpServletRequest request) {
        String id = request.getParameter("id");
        Map<String,String> result = new HashMap<String, String>();
        AdvertisementPosition advertisementPosition = new AdvertisementPosition();
        if(id == null || id == ""){
            result.put("result","error");
        }else{
            advertisementPosition = advertisementPositionService.findById(id);
            advertisementPosition.setIsValid(1);
            advertisementPositionService.update(advertisementPosition);
            result.put("result","success");
        }
        return result;
    }
}
