package com.baosight.scc.ec.controller;

import com.baosight.scc.ec.model.*;
import com.baosight.scc.ec.security.UserContext;
import com.baosight.scc.ec.service.*;
import com.baosight.scc.ec.type.FabricMainUseType;
import com.baosight.scc.ec.type.ItemState;
import com.baosight.scc.ec.web.EcGrid;
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
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Created by ThinkPad on 2014/5/6.
 */
@Controller
public class FabricController extends AbstractController{
    @Autowired
    private FabricService fabricService;
    @Autowired
    private UserContext userContext;
    @Autowired
    private MessageSource messageSource;
    @Autowired
    private FabricMainUseTypeService useTypeService;
    @Autowired
    private FabricCategoryService categoryService;
    @Autowired
    private FabricSourceService sourceService;
    @Autowired
    private FavouriteItemsService favouriteItemsService;

    final Logger logger = LoggerFactory.getLogger(FabricController.class);

    //列表页面
    //跳转列表
    private final static String REDIRECT_LIST = "redirect:/sellerCenter/fabrics";
    //详细页面
    private final static String FABRIC_VIEW = "fabric_detail";
    //无权限
    private final static String FIVE_HUNDRED = "fiveHundred";
    //面料交易记录列表页面
    private final static String FABRIC_ORDERS = "fabric_orders";
    //面料交易评价列表页面
    private final static String FABRIC_COMMENTS = "fabric_comments";

    @RequestMapping(value = "/fabric/{id}", method = RequestMethod.GET)
    public String view(@PathVariable("id") String id, Model uiModel) {
        Fabric fabric = fabricService.findById(id);
        uiModel.addAttribute("fabric", fabric);
        return FABRIC_VIEW;
    }


    /*
        通过主键获取到登入用户希望修改的面料信息
        @param:id 面料主键
     */
    public Fabric editForm(String id) {
        Fabric fabric = fabricService.findByIdAndUser(id, userContext.getCurrentUser(),true);
        return fabric;
    }

    /*
     *   创建空表单，让用户进行填写
     */
    public Fabric createForm() {
        Fabric fabric = new Fabric();
        return fabric;
    }

    /*
     *	判断该会话中的面料是否为空
     *  @param:fabric 整个flow中，登入用户希望操作的面料
     */
    public boolean checkUserFabric(Fabric fabric) {
        return fabric == null;
    }

    /*
     *   保存用户提交的面料数据
     *  @param:fabric 整个flow中，对应的fabric对象
     */
    public void saveFabric(Fabric fabric) {
        fabricService.save(fabric);
    }

    public void standardSave(Fabric fabric){
        //build map
        fabric.setRanges(buildRanges(fabric.getKeys(), fabric.getValues()));

        //set createdby
        EcUser user = userContext.getCurrentUser();
        fabric.setCreatedBy(user);

        //save
        saveFabric(fabric);
    }

    public void updateFabric(Fabric fabric){
        //build map
        fabric.setRanges(buildRanges(fabric.getKeys(), fabric.getValues()));

        EcUser user = userContext.getCurrentUser();
        fabric.setCreatedBy(user);
        fabric.setState(ItemState.审核中);
        
        fabricService.update(fabric);
    }

    public void tempUpdateFabric(Fabric fabric){
        //build map
        fabric.setRanges(buildRanges(fabric.getKeys(), fabric.getValues()));

        EcUser user = userContext.getCurrentUser();
        fabric.setCreatedBy(user);
        fabric.setState(ItemState.草稿);

        fabricService.update(fabric);
    }

    /*
       暂存
     */
    public void tempSaveFabric(Fabric fabric){
        fabric.setRanges(buildRanges(fabric.getKeys(),fabric.getValues()));

        EcUser user = userContext.getCurrentUser();
        fabric.setCreatedBy(user);

        fabric.setState(ItemState.草稿);

        fabricService.save(fabric);
    }


    /*
        初始化面料分类
     */
    public List<FabricCategory> initFabricCategory(){
        return categoryService.findAllSecondCategory();
    }

    /*
        初始化原料成分
     */
    public List<FabricSource> initSource(){
        return sourceService.findAllFirstCategory();
    }

    public List<FabricSource> initDetailSource(){
        return sourceService.findAllSecondCategory();
    }
    /*
     *  初始化选择分类时的主要使用方式
     */
    public List<FabricMainUseType> initMainUserType() {
        return useTypeService.findAll();
    }

    /*

     */
    public List<FabricCategory> initCategory(){
        return categoryService.findAllSecondCategory();
    }

    /*

     */
    public String successMessage(){
        return "暂存成功";
    }

    /**
     * 面料交易记录列表
     * @param uiModel
     * @param page
     * @param id 面料id
     * @return
     */
    @RequestMapping(value="/fabric/{id}/orders",method=RequestMethod.GET)
    public String showFabricOrdersByFid(Model uiModel,
							    		@RequestParam(value = "page", required = false,defaultValue="1") Integer page,
							    		@RequestParam(value = "size", required = false,defaultValue = "15") Integer size,
										@PathVariable("id") String id){
    	PageRequest pageRequest = null;
		//定义排序字段
		Sort sort = new Sort(Sort.Direction.DESC, "createdTime");
		//spring 封装page
		if (page != null)
			pageRequest = new PageRequest(page - 1, size, sort);
		else
		    pageRequest = new PageRequest(0, size, sort);
		//获取交易记录数据
		Page<OrderLine> fabricPage = this.fabricService.showFabricOrdersByFid(id, pageRequest);
		EcGrid<OrderLine> grid = new EcGrid<OrderLine>();
		grid.setCurrentPage(fabricPage.getNumber() + 1);
		grid.setEcList(Lists.newArrayList(fabricPage));
		grid.setTotalPages(fabricPage.getTotalPages());
		grid.setTotalRecords(fabricPage.getTotalElements());
		//传递数据到页面
		uiModel.addAttribute("grid", grid);
    	return this.FABRIC_ORDERS;
    }

    /**
     * 根据面料id，查询面料交易评价记录
     * @param uiModel
     * @param page
     * @param id 面料id
     * @return
     */
    @RequestMapping(value="/fabric/{id}/comments",method=RequestMethod.GET)
    public String showFabricCommentsByFid(Model uiModel,
    										@RequestParam(value="page",required=false,defaultValue="1") Integer page,
    										@RequestParam(value="flag",required=false) Integer flag,
    										@RequestParam(value = "size", required = false,defaultValue = "15") Integer size,
    										@PathVariable("id") String id){
    	PageRequest pageRequest = null;
		//定义排序字段
		Sort sort = new Sort(Sort.Direction.DESC, "createdTime");
		//spring 封装page
		if (page != null)
			pageRequest = new PageRequest(page - 1, size, sort);
		else
		    pageRequest = new PageRequest(0, size, sort);

		//调用服务层接口
		Page<Comment> fabricPage = this.fabricService.showFabricCommentsByFid(id, flag, pageRequest);

		EcGrid<Comment> grid = new EcGrid<Comment>();
		grid.setCurrentPage(fabricPage.getNumber() + 1);
		grid.setEcList(Lists.newArrayList(fabricPage));
		grid.setTotalPages(fabricPage.getTotalPages());
		grid.setTotalRecords(fabricPage.getTotalElements());

		uiModel.addAttribute("grid", grid);

    	return FABRIC_COMMENTS;
    }

    @RequestMapping(value="/fabric/favourite/{id}",method =RequestMethod.POST,produces = "application/json")
    @ResponseBody
    public String addFavourite(@PathVariable("id")String id){
        EcUser currentUser=userContext.getCurrentUser();
        if(currentUser!=null) {
            Fabric fabric = new Fabric();
            fabric.setId(id);
            favouriteItemsService.addFavourite(currentUser,fabric);
            return "success";
        }else{
            return "fail";
        }
    }

    @RequestMapping(value = "/fabric/favourite/{id}",method = RequestMethod.DELETE,produces = "application/json")
    @ResponseBody
    public String delFavourite(@PathVariable("id")String id){
        EcUser currentUser=userContext.getCurrentUser();
        if(currentUser!=null) {
            Fabric fabric = new Fabric();
            fabric.setId(id);
            favouriteItemsService.removeFavourite(currentUser,fabric);
            return "success";
        }else{
            return "fail";
        }
    }
}
