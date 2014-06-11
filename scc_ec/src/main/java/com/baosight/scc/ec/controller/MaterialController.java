package com.baosight.scc.ec.controller;


import com.baosight.scc.ec.model.*;
import com.baosight.scc.ec.security.UserContext;
import com.baosight.scc.ec.service.*;
import com.baosight.scc.ec.type.CommentType;
import com.baosight.scc.ec.type.ItemState;
import com.baosight.scc.ec.type.MaterialState;
import com.baosight.scc.ec.web.EcGrid;
import com.baosight.scc.ec.web.MaterialCategoryJSON;
import com.google.common.collect.Lists;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 辅料控制器
 *
 * @author sam
 */
@Controller
@RequestMapping(value = "/sellerCenter")
public class MaterialController extends AbstractController {
    @Autowired
    private MaterialService materialService;

    @Autowired
    private MaterialCategoryService categoryService;

    @Autowired
    private UserContext userContext;

    @Autowired
    private FavouriteItemsService favouriteItemsService;
    @Autowired
    private  MaterialCategoryService materialCategoryService;
    @Autowired
    private FabricCategoryService fabricCategoryService;
    @Autowired
    private EcUserService ecUserService;

    final Logger logger = LoggerFactory.getLogger(MaterialController.class);


    private final static String MATERIAL_DETAIL = "materialDetail"; //辅料详情页面
    private final static String MATERIAL_COMMENTS = "materialCommentList"; //辅料交易评价记录页面
    private final static String MATERIAL_ORDERS = "materialOrderList"; //辅料交易记录页面
    private final static String MATERIAL_LIST = "material_list"; //辅料交易记录页面

    @InitBinder
    public void initBinder(WebDataBinder binder) {
        binder.setDisallowedFields("id");
    }

    //面料详情页面
    @RequestMapping(value = "/material/{id}", method = RequestMethod.GET)
    public String getMaterial(Model uiModel, @PathVariable("id") String id) {
        Material mt = null;
        //辅料信息
        mt = this.materialService.getMaterialInfo(id);
        //供应商信息
        EcUser user = this.ecUserService.findById(mt.getCreatedBy().getId());
        //辅料一级分类
        List<MaterialCategory> materialCategories = this.materialCategoryService.findAllFirstCategory();
        //面料一级分类
        List<FabricCategory> fabricCategories = this.fabricCategoryService.findAllFirstCategory();
        if (mt == null) {
            mt = new Material();
        }
        uiModel.addAttribute("mt", mt);
        uiModel.addAttribute("user",user);
        uiModel.addAttribute("materialCategories",materialCategories);
        uiModel.addAttribute("fabricCategories",fabricCategories);
        return MATERIAL_DETAIL;
    }

    //面料交易评价列表
    @RequestMapping(value = "/material/{id}/comments", method = RequestMethod.GET)
    public String showMaterialComments(Model uiModel,
                                       @RequestParam(value = "page", required = false,defaultValue="1") Integer page,
                                       @RequestParam(value = "size", required = false,defaultValue = "15") Integer size,
                                       @PathVariable("id") String id,
                                       @RequestParam(value = "type", required = false) Integer type) {
        try {
            if (null != id) {
                //根据辅料id查询辅料信息
                Material m = this.materialService.getMaterialInfo(id);
                PageRequest pageRequest = null;
                Sort sort = new Sort(Sort.Direction.DESC, "createdTime");
                if (page != null)
                    pageRequest = new PageRequest(page - 1, size, sort);
                else
                    pageRequest = new PageRequest(0, size, sort);
                //选择好、中、差评
                CommentType ctype = null;
                if (type != null) {
                   ctype = CommentType.values()[type];
                }
                //封装数据到页面显示
                EcGrid<Comment> cgrid = new EcGrid<Comment>();
                Page<Comment> commentPage = this.materialService.showMaterialComments(m, pageRequest, ctype);
                cgrid.setCurrentPage(commentPage.getNumber() + 1);
                cgrid.setEcList(Lists.newArrayList(commentPage));
                cgrid.setTotalPages(commentPage.getTotalPages());
                cgrid.setTotalRecords(commentPage.getTotalElements());
                uiModel.addAttribute("materialComments", cgrid);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return MATERIAL_COMMENTS;
    }

    /*
     * 辅料交易记录
     */
    @RequestMapping(value = "material/{id}/orders", method = RequestMethod.GET)
    public String showMaterialOrders(Model uiModel,
                                     @RequestParam(value = "page", required = false,defaultValue = "1") Integer page,
                                     @RequestParam(value = "size", required = false,defaultValue = "15") Integer size,
                                     @PathVariable("id") String id) {
        try {
            //辅料信息
            Material material = this.materialService.getMaterialInfo(id);

            PageRequest pageRequest = null;
            //定义排序字段
            Sort sort = new Sort(Sort.Direction.DESC, "createdTime");
            //spring 封装page
            if (page != null)
                pageRequest = new PageRequest(page - 1, size, sort);
            else
                pageRequest = new PageRequest(0, size, sort);
            //获取交易数据
            Page<OrderLine> materialPage = this.materialService.showMaterialOrders(material, pageRequest);
            //将交易数据封装到一个页面对象mgrid上
            EcGrid<OrderLine> mgrid = new EcGrid<OrderLine>();
            mgrid.setCurrentPage(materialPage.getNumber() + 1);
            mgrid.setEcList(Lists.newArrayList(materialPage));
            mgrid.setTotalPages(materialPage.getTotalPages());
            mgrid.setTotalRecords(materialPage.getTotalElements());

            uiModel.addAttribute("mgrid", mgrid);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return MATERIAL_ORDERS;
    }

    @RequestMapping(value = "/materials", method = RequestMethod.GET)
    public String list(Model uiModel,
                       @RequestParam(value="size",required = false,defaultValue = "15") Integer size,
                       @RequestParam(value = "page", required = false,defaultValue = "1") Integer page,
                       @RequestParam(value = "type", required = false,defaultValue = "4") Integer type) {
        MaterialState state=MaterialState.values()[type];

        EcUser user = userContext.getCurrentUser();
        Sort sort = new Sort(Sort.Direction.DESC, "createdTime");
        Pageable pageable=createPageRequest(page,size,sort);
        EcGrid<Material> materialGrid;
        if(type==4)
            materialGrid=createGrid(materialService.findByCreatedBy(user,pageable));
        else
            materialGrid=createGrid(materialService.findByCreatedByAndState(user, state, pageable));

        uiModel.addAttribute("materialGrid",materialGrid);
        return MATERIAL_LIST;
    }

    /*
        create form for material create
     */
    public Material createForm() {
        return new Material();
    }

    /*
        init material first category
     */
    public List<MaterialCategory> initFirstCategory() {
        return categoryService.getMaterialFirstCategorys();
    }

    /*
        init material second category
     */
    public List<MaterialCategory> initSecondCategory() {
        return categoryService.findAllSecondCategory();
    }

    @RequestMapping(value = "/materialCategory/{id}",method = RequestMethod.GET,produces = "application/json")
    @ResponseBody
    public List<MaterialCategoryJSON> findSecondCategoryByParentId(@PathVariable("id") String id) {
        MaterialCategory materialCategory=new MaterialCategory();
        materialCategory.setId(id);
        return categoryService.findByParentCategory(materialCategory);
    }

    /*
        保存辅料
     */
    public void saveMaterial(Material material) {
        material.setRanges(buildRanges(material.getKeys(), material.getValues()));
        EcUser user = userContext.getCurrentUser();
        material.setCreatedBy(user);
        material.setState(ItemState.审核中);
        materialService.save(material);
    }

    /*
        暂存
     */
    public void tempSaveMaterial(Material material) {
        material.setRanges(buildRanges(material.getKeys(), material.getValues()));
        EcUser user = userContext.getCurrentUser();
        material.setCreatedBy(user);
        material.setState(ItemState.草稿);
        materialService.save(material);
    }

    /*
    @param:id
    get materi by id
 */
public Material editForm(String id){
    return materialService.findOne(id);
}

/*
    @param: detached material
 */
public void updateMaterial(Material material){
    //build map
    material.setRanges(buildRanges(material.getKeys(), material.getValues()));

    EcUser user = userContext.getCurrentUser();
    material.setCreatedBy(user);
    material.setState(ItemState.审核中);

    materialService.update(material);
}

/*
    @param:material detached material
    temp update material
 */
public void tempUpdateMaterial(Material material){
    //build map
    material.setRanges(buildRanges(material.getKeys(), material.getValues()));

    EcUser user = userContext.getCurrentUser();
    material.setCreatedBy(user);
    material.setState(ItemState.草稿);

    materialService.update(material);
}

    public String successMessage(){
        return "暂存成功";
    }


    @RequestMapping(value="/material/favourite/{id}",method =RequestMethod.POST,produces = "application/json")
    @ResponseBody
    public String addFavourite(@PathVariable("id")String id){
        EcUser currentUser=userContext.getCurrentUser();
        if(currentUser!=null) {
            Material material = new Material();
            material.setId(id);
            favouriteItemsService.addFavourite(currentUser, material);
            return "success";
        }else{
            return "fail";
        }
    }

    @RequestMapping(value = "/material/favourite/{id}",method = RequestMethod.DELETE,produces = "application/json")
    @ResponseBody
    public String delFavourite(@PathVariable("id")String id){
        EcUser currentUser=userContext.getCurrentUser();
        if(currentUser!=null) {
            Material material = new Material();
            material.setId(id);
            favouriteItemsService.removeFavourite(currentUser,material);
            return "success";
        }else{
            return "fail";
        }
    }
}
