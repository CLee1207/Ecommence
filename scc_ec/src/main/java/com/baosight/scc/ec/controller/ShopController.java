package com.baosight.scc.ec.controller;

import com.baosight.scc.ec.model.*;
import com.baosight.scc.ec.security.UserContext;
import com.baosight.scc.ec.service.*;
import com.baosight.scc.ec.service.jpa.EcUserServiceImpl;
import com.baosight.scc.ec.web.EcGrid;
import com.google.common.collect.Lists;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
/**
 * 店铺首页
 * @author sam
 *
 */
public class ShopController extends AbstractController{

	@Autowired
	private MaterialService materialService;
	@Autowired
	private FabricService fabricService;
	@Autowired
	private OrderLineService orderLineService;
	@Autowired
	private EcUserService ecUserService;
	@Autowired
	private FabricCategoryService fabricCategoryService;
	@Autowired
	private MaterialCategoryService materialCategoryService;
    @Autowired
    private UserContext userContext;
    @Autowired
    private FavouriteItemsService favouriteItemsService;
    @Autowired
    private  ItemService itemService;
    @Autowired
    private CommentService commentService;
    @Autowired
    private  SellerCommentService sellerCommentService;
    @Autowired
    private DimensionRateService dimensionRateService;
    @Autowired
    private SellerCreditService sellerCreditService;
	
	final Logger logger = LoggerFactory.getLogger(ShopController.class);
	
	private final static String PROVIDER_MATERIAL = "providerMaterials"; //辅料供应商页面
	private final static String PROVIDER_FABRIC = "providerFabrics"; //面料供应商页面
	private final static String PROVIDER_DETAIL = "providerDetail"; //服务商详情页面
    private final static String PROVIDER_ITEMS = "providerIndex";  //供应商页面
    private final static String BUYER_COMMENTS = "buyer_comments"; //买家评价列表
    private final static String SELLER_COMMENTS = "seller_comments"; //卖家评价列表
    private final static String SELLER_CREDIT = "sellerCredit"; //店铺动态评分
	
	

	/**
	 * 辅料供应商页面，根据产品名称、二级分类id搜索产品
	 * @author sam
	 */
	@RequestMapping(value="/shopCenter/materials/{id}",method=RequestMethod.GET)
	public String searchMaterials(Model uiModel,@RequestParam(value="proName",required=false) String proName,
									@PathVariable("id") String id,
									@RequestParam(value="page",required=false,defaultValue="1") Integer page,
									@RequestParam(value = "size", required = false,defaultValue = "15") Integer size,
									@RequestParam(value="secondCategory",required=false) String secondCategory){
		
		PageRequest pageRequest = null;
		//指定排序字段
		Sort sort = new Sort(Sort.Direction.DESC, "createdTime");
		if (page != null)
			pageRequest = new PageRequest(page - 1, size, sort);
		else
		    pageRequest = new PageRequest(0, size, sort);
		//封装查询结果到grid对象中，便于在页面取数
		EcGrid<Material> grid = new EcGrid<Material>();
		//调用服务接口
		Page<Material> mpage = this.materialService.searhItems(id, proName,secondCategory, pageRequest);
		grid.setCurrentPage(mpage.getNumber() + 1);
		grid.setEcList(Lists.newArrayList(mpage));
		grid.setTotalPages(mpage.getTotalPages());
		grid.setTotalRecords(mpage.getTotalElements());
		//辅料一级分类
		List<MaterialCategory> firstCategoryList = this.materialCategoryService.getMaterialFirstCategorys();
		//服务商信息
		EcUser user = this.ecUserService.findById(id);
		//设置返回页面数据
		uiModel.addAttribute("grid", grid);
		uiModel.addAttribute("firstCategoryList", firstCategoryList);
		uiModel.addAttribute("user", user);
		//返回页面
		return PROVIDER_MATERIAL;
	}
	
	/**
	 * 面料供应商页面，根据面料产品名称、二级分类id，搜索产品列表分页
	 * @param uiModel
	 * @param proName 产品名称
	 * @param id 供应商id
	 * @param page
	 * @param secondCategory 二级分类id
	 * @return
	 * @author sam
	 */
	@RequestMapping(value="/shopCenter/fabrics/{id}",method=RequestMethod.GET)
	public String searchFabrics(Model uiModel,@RequestParam(value="proName",required=false) String proName,
									@PathVariable("id") String id,
									@RequestParam(value="page",required=false,defaultValue="1") Integer page,
									@RequestParam(value = "size", required = false,defaultValue = "15") Integer size,
									@RequestParam(value="secondCategory",required=false) String secondCategory){
		PageRequest pageRequest = null;
		//指定排序字段
		Sort sort = new Sort(Sort.Direction.DESC, "createdTime");
		if (page != null)
			pageRequest = new PageRequest(page - 1, size, sort);
		else
		    pageRequest = new PageRequest(0, size, sort);
		//调用服务层接口
		Page<Fabric> fabricPage = this.fabricService.searchItems(id, proName, secondCategory, pageRequest);
		//封装查询结果到grid对象中，便于在页面取数
		EcGrid<Fabric> grid = new EcGrid<Fabric>();
		grid.setCurrentPage(fabricPage.getNumber() + 1);
		grid.setEcList(Lists.newArrayList(fabricPage));
		grid.setTotalPages(fabricPage.getTotalPages());
		grid.setTotalRecords(fabricPage.getTotalElements());
		//面料一级分类
		List<FabricCategory> firstCategory = this.fabricCategoryService.findAllFirstCategory();
		//服务商信息
		EcUser user = this.ecUserService.findById(id);
		uiModel.addAttribute("grid", grid);
		uiModel.addAttribute("firstCategory", firstCategory);
		uiModel.addAttribute("user", user);
		return PROVIDER_FABRIC;
	}

    /*
    供应商主页
     */
    @RequestMapping(value="/shopCenter/provider/{id}",method = RequestMethod.GET)
    public String searchProviderItems(Model uiModel,@RequestParam(value = "secondCategoryId",required = false) String secondCategoryId,
                                      @PathVariable("id") String id,
                                      @RequestParam(value = "size",required = false,defaultValue = "15") Integer size,
                                      @RequestParam(value = "page",required = false,defaultValue = "1") Integer  page,
                                      @RequestParam(value = "categoryType",required = false,defaultValue = "0") Integer categoryType){
        PageRequest pageRequest = null;
        //指定排序字段
        Sort sort = new Sort(Sort.Direction.DESC, "createdTime");
        if (page != null)
            pageRequest = new PageRequest(page - 1, size, sort);
        else
            pageRequest = new PageRequest(0, size, sort);

        Page<Item> itemPage = this.itemService.findByUserIdAndCategoryIdAndType(id,secondCategoryId,categoryType,pageRequest);
        //供应商信息
        EcUser user = this.ecUserService.findById(id);
        //面料一级分类
        List<FabricCategory> fabricCategories = this.fabricCategoryService.findAllFirstCategory();
        //辅料一级分类
        List<MaterialCategory> materialCategories = this.materialCategoryService.getMaterialFirstCategorys();

        EcGrid<Item> grid = new EcGrid<Item>();
        grid.setTotalPages(itemPage.getTotalPages());
        grid.setTotalRecords(itemPage.getTotalElements());
        grid.setEcList(Lists.newArrayList(itemPage));
        grid.setCurrentPage(itemPage.getNumber() + 1);
        uiModel.addAttribute("grid",grid);
        uiModel.addAttribute("fabricCategories",fabricCategories);
        uiModel.addAttribute("materialCategories",materialCategories);
        return PROVIDER_ITEMS;
    }

	/**
	 * 服务商信息
	 * @param uiModel
	 * @param id
	 * @return
	 */
	@RequestMapping(value="/provider/{id}/")
	public String showProviderInfo(Model uiModel,@PathVariable("id") String id){
		EcUser user  = this.ecUserService.findById(id);
		uiModel.addAttribute("user", user);
		return PROVIDER_DETAIL;
	}

    /*
   来自买家评价列表
    */
    @RequestMapping(value="/shopCenter/comments/{id}/fromBuyer")
    public String listCommentsFromBuyer(Model uiModel,
                                        @RequestParam(value="type",required = false,defaultValue = "0") Integer type,
                                        @RequestParam(value="content",required = false,defaultValue = "aaa") String content,
                                        @PathVariable("id") String id,
                                        @RequestParam(value = "page",required = false,defaultValue = "1") Integer page,
                                        @RequestParam(value = "size",required = false,defaultValue = "15") Integer size){
        PageRequest pageRequest = null;
        //定义排序字段
        Sort sort = new Sort(Sort.Direction.DESC, "createdTime");
        //spring 封装page
        if (page != null)
            pageRequest = new PageRequest(page - 1, size, sort);
        else
            pageRequest = new PageRequest(0, size, sort);
        EcUser user = this.ecUserService.findById("id");
        Page<Comment> commentPage = this.commentService.findCommentsFromBuyer(user,type,content,pageRequest);
        EcGrid<Comment> grid = new EcGrid<Comment>();
        grid.setCurrentPage(commentPage.getNumber() + 1);
        grid.setEcList(Lists.newArrayList(commentPage));
        grid.setTotalRecords(commentPage.getTotalElements());
        grid.setTotalPages(commentPage.getTotalPages());
        uiModel.addAttribute("grid",grid);
        return BUYER_COMMENTS;
    }

    /*
    来自卖家的评价
     */
    @RequestMapping(value = "/shopCenter/comments/{id}/fromSeller",method = RequestMethod.GET)
    public String listCommentsFromSeller(Model uiModel,
                                         @RequestParam(value = "type",required = false,defaultValue = "0") Integer type,
                                         @RequestParam(value = "content",required = false,defaultValue = "aa") String content,
                                         @PathVariable("id") String id,
                                         @RequestParam(value = "page",required = false,defaultValue = "1") Integer page,
                                         @RequestParam(value = "size",required = false,defaultValue = "15") Integer size){
        PageRequest pageRequest = null;
        //定义排序字段
        Sort sort = new Sort(Sort.Direction.DESC, "createdTime");
        //spring 封装page
        if (page != null)
            pageRequest = new PageRequest(page - 1, size, sort);
        else
            pageRequest = new PageRequest(0, size, sort);
        EcUser user = this.ecUserService.findById("id");
        Page<SellerComment> sellerCommentPage = this.sellerCommentService.findSellerCommentsFromSeller(user,type,content,pageRequest);
        EcGrid<SellerComment> grid = new EcGrid<SellerComment>();
        grid.setCurrentPage(sellerCommentPage.getNumber()+1);
        grid.setTotalPages(sellerCommentPage.getTotalPages());
        grid.setEcList(Lists.newArrayList(sellerCommentPage));
        grid.setTotalRecords(sellerCommentPage.getTotalElements());
        uiModel.addAttribute("grid",grid);
        return SELLER_COMMENTS;
    }

    /*
    店铺动态评分
     */
    @RequestMapping(value = "/shopCenter/shopRateScore/{id}",method = RequestMethod.GET)
    public String shopRateScore(Model uiModel,@PathVariable("id") String id){
        EcUser user = this.ecUserService.findById(id);
        DimensionRate dimensionRate = this.dimensionRateService.findBySeller(user);
        List<SellerCredit> sellerCredits = this.sellerCreditService.findByUser(user);
        SellerCredit sellerCredit = this.sellerCreditService.findTypeTotal(user);
        uiModel.addAttribute("dimensionRate",dimensionRate);
        uiModel.addAttribute("sellerCredits",sellerCredits);
        uiModel.addAttribute("sellerCredit",sellerCredit);
        return SELLER_CREDIT;
    }
}
