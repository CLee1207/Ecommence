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
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
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
    private final static String BUYER_PROFILE = "buyer_profile";//买家信息维护
    private final static String SELLER_PROFILE = "seller_profile";//卖家信息维护
    /*
        list all favourite items
     */
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

    /*
        add a favourite item
     */
    @RequestMapping(value = "/favourites/item/{id}", method = RequestMethod.POST, produces = "application/json")
    @ResponseBody
    public Message addFavourites(@PathVariable("id") String id, Locale locale) {
        EcUser user = userContext.getCurrentUser();
        Item item = new Item();
        item.setId(id);
        if (favouriteItemsService.countByItemAndUser(item, user)) {
            return new Message("fail", messageSource.getMessage("favourite_duplicate", new Object[]{}, locale));
        } else {
            favouriteItemsService.addFavourite(user, item);
            return new Message("success", messageSource.getMessage("favourite_success", new Object[]{}, locale));
        }
    }

    /*
        delete a favourite item
     */
    @RequestMapping(value = "/favourites/item/{id}", method = RequestMethod.DELETE, produces = "application/json")
    @ResponseBody
    public Message deleteFavourites(@PathVariable("id") String id, Locale locale) {
        EcUser user = userContext.getCurrentUser();
        Item item = new Item();
        item.setId(id);
        if (favouriteItemsService.countByItemAndUser(item, user)) {
            favouriteItemsService.deleteFavourite(item, user);
            return new Message("success", messageSource.getMessage("favourite_delete_success", new Object[]{}, locale));
        } else {
            return new Message("fail", messageSource.getMessage("favourite_no_exist", new Object[]{}, locale));
        }
    }

    /*
        list all favourite shops
     */
    @RequestMapping(value = "/favourite/shops", method = RequestMethod.GET)
    public String listFavouriteShop(Model uiModel,
                                    @RequestParam(value = "page", required = false, defaultValue = "1") Integer page,
                                    @RequestParam(value = "size", required = false, defaultValue = "15") Integer size) {
        Pageable pageable = createPageRequest(page, size);
        EcUser u = userContext.getCurrentUser();
        Page<FavouriteShops> shops = favouriteShopService.findByUser(u, pageable);
        EcGrid<FavouriteShops> grid = createGrid(shops);
        uiModel.addAttribute("grid", grid);
        return FAVOURITE_SHOP_LIST;
    }

    /*
        add a favourite shop
     */
    @RequestMapping(value = "/favourite/shops/{id}", method = RequestMethod.POST, produces = "application/json")
    @ResponseBody
    public Message addFavouriteShop(@PathVariable("id") String id, Locale locale) {
        Shop shop = new Shop();
        shop.setId(id);
        EcUser user = userContext.getCurrentUser();
        if (favouriteShopService.countByUserAndShop(user, shop)) {
            return new Message("fail", messageSource.getMessage("favourite_duplicate", new Object[]{}, locale));
        } else {
            favouriteShopService.save(user, shop);
            return new Message("fail", messageSource.getMessage("favourite_success", new Object[]{}, locale));
        }
    }

    /*
        delete a favourite shop
     */
    @RequestMapping(value = "/favourites/shops/{id}", method = RequestMethod.DELETE, produces = "application/json")
    @ResponseBody
    public Message deleteShopFavourites(@PathVariable("id") String id, Locale locale) {
        EcUser user = userContext.getCurrentUser();
        Shop shop = new Shop();
        shop.setId(id);
        FavouriteShops favouriteShops=new FavouriteShops(user,shop);
        favouriteShopService.delete(favouriteShops);
        return new Message("success", messageSource.getMessage("favourite_delete_success", new Object[]{}, locale));
    }

    /*
        ajax requesting to show hottest items in a shop
     */
    @RequestMapping(value = "/favourite/shops/{id}/hottestItems", method = RequestMethod.GET, produces = "application/json")
    @ResponseBody
    public List<ItemJSON> listHottestItems(@PathVariable("id") String id) {
        EcUser u = new EcUser();
        u.setId(id);
        return itemService.findTop4ByCreatedByOrderByBidCount(u);
    }

    /**
     * 买家信息维护
    */
    @RequestMapping(value="/profile",method = RequestMethod.GET)
    public String modifyUserInfo(Model uiModel){
        EcUser currentUser=userContext.getCurrentUser();
        uiModel.addAttribute("user",currentUser);
        return BUYER_PROFILE;
    }

    @RequestMapping(value="/profile",params = "form",method = RequestMethod.POST)
    public String saveUserInfo(Model uiModel,@Valid @ModelAttribute("user")EcUser user,BindingResult result,Locale locale,HttpServletRequest request){
        if(result.hasErrors()){
            uiModel.addAttribute("message", new Message("error", messageSource.getMessage("profile.required", new Object[]{}, locale)));
            uiModel.addAttribute("user", user);
            return BUYER_PROFILE;
        }
        else {
            EcUser u = userContext.getCurrentUser();
            u.setName("");
            userService.save(u);
            return BUYER_PROFILE;
        }
    }

    /**
     * 卖家信息维护
     */
    @RequestMapping(value="/company",method = RequestMethod.GET)
    public String modifyComInfo(Model uiModel){
        EcUser currentUser=userContext.getCurrentUser();
        uiModel.addAttribute("user",currentUser);
        return SELLER_PROFILE;
    }

    @RequestMapping(value="/company",params = "form",method = RequestMethod.POST)
    public String saveComInfo(Model uiModel,@Valid @ModelAttribute("user")EcUser user,BindingResult result,Locale locale,HttpServletRequest request){
        if(result.hasErrors()){
            uiModel.addAttribute("message", new Message("error", messageSource.getMessage("profile.required", new Object[]{}, locale)));
            uiModel.addAttribute("user", user);
            return SELLER_PROFILE;
        }
        else {
            EcUser u = userContext.getCurrentUser();
            u.setName("");
            userService.save(u);
            return SELLER_PROFILE;
        }
    }
}
