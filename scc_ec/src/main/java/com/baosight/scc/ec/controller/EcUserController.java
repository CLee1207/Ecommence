package com.baosight.scc.ec.controller;

import com.baosight.scc.ec.model.*;
import com.baosight.scc.ec.security.UserContext;
import com.baosight.scc.ec.service.EcUserService;
import com.baosight.scc.ec.service.FavouriteItemsService;
import com.baosight.scc.ec.service.FavouriteShopService;
import com.baosight.scc.ec.service.ItemService;
import com.baosight.scc.ec.web.EcGrid;
import com.baosight.scc.ec.web.ItemJSON;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Locale;

/**
 * 用户控制器
 *
 * @author sam
 */
@Controller
@RequestMapping("/userCenter")
public class EcUserController extends AbstractController {
    @Autowired
    private UserContext userContext;
    @Autowired
    private FavouriteItemsService favouriteItemsService;
    @Autowired
    private FavouriteShopService favouriteShopService;
    @Autowired
    private ItemService itemService;
    @Autowired
    private MessageSource messageSource;
    @Autowired
    private EcUserService userService;

    final Logger logger = LoggerFactory.getLogger(EcUserController.class);

    private final static String FAVOURITE_LIST = "favouriteList";//收藏的商品
    private final static String FAVOURITE_SHOP_LIST = "favouriteShopList";//收藏的店铺

    @RequestMapping(value = "/favourites", method = RequestMethod.GET)
    public String listFavourites(Model uiModel,
                                 @RequestParam(value = "page", required = false, defaultValue = "1") Integer page,
                                 @RequestParam(value = "size", required = false, defaultValue = "15") Integer size) {
        Pageable pageable = createPageRequest(page, size);
        EcUser user = userContext.getCurrentUser();
        EcGrid<FavouriteItems> grid = createGrid(favouriteItemsService.findByUser(user, pageable));
        for (FavouriteItems item : grid.getEcList()) {
            if (item.getItem() instanceof Fabric)
                item.getItem().setUrl(Fabric.class.getName());
            else
                item.getItem().setUrl(Material.class.getName());
        }
        uiModel.addAttribute("grid", grid);

        return FAVOURITE_LIST;
    }

    @RequestMapping(value = "/favourites/item/{id}", method = RequestMethod.POST,produces = "application/json")
    @ResponseBody
    public Message addFavourites(@PathVariable("id")String id,Locale locale){
        EcUser user=userContext.getCurrentUser();
        Item item=new Item();
        item.setId(id);
        if(favouriteItemsService.countByItemAndUser(item,user)){
            return new Message("fail",messageSource.getMessage("favourite_duplicate",new Object[]{},locale));
        }
        else {
            favouriteItemsService.addFavourite(user, item);
            return new Message("success",messageSource.getMessage("favourite_success",new Object[]{},locale));
        }
    }

    @RequestMapping(value = "/favourites/item/{id}", method = RequestMethod.DELETE,produces = "application/json")
    @ResponseBody
    public Message deleteFavourites(@PathVariable("id")String id,Locale locale){
        EcUser user=userContext.getCurrentUser();
        Item item=new Item();
        item.setId(id);
        if(favouriteItemsService.countByItemAndUser(item,user)){
            favouriteItemsService.deleteFavourite(item, user);
            return new Message("success",messageSource.getMessage("favourite_delete_success",new Object[]{},locale));
        }
        else {
            return new Message("fail",messageSource.getMessage("favourite_no_exist",new Object[]{},locale));
        }
    }

    @RequestMapping(value = "/favourite/shops", method = RequestMethod.GET)
    public String listFavouriteShop(Model uiModel,
                                    @RequestParam(value = "page", required = false, defaultValue = "1") Integer page,
                                    @RequestParam(value = "size", required = false, defaultValue = "15") Integer size) {
        Pageable pageable = createPageRequest(page, size);
        EcUser u = userContext.getCurrentUser();
        Page<FavouriteShops> shops = favouriteShopService.findByUser(u, pageable);
        EcGrid<FavouriteShops> grid = createGrid(shops);
        uiModel.addAttribute("grid",grid);
        return FAVOURITE_SHOP_LIST;
    }

    @RequestMapping(value = "/favourite/shops/{id}", method = RequestMethod.GET,produces = "application/json")
    @ResponseBody
    public String addShopFavourites(@PathVariable("id")String id){
        return null;
    }

    @RequestMapping(value="/favourite/shops/{id}/hottestItems",method = RequestMethod.GET,produces = "application/json")
    @ResponseBody
    public List<ItemJSON> listHottestItems(@PathVariable("id")String id){
        EcUser u=new EcUser();
        u.setId(id);
        return itemService.findTop4ByCreatedByOrderByBidCount(u);
    }

}
