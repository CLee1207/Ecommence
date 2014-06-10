package com.baosight.scc.ec.controller;

import com.baosight.scc.ec.model.*;
import com.baosight.scc.ec.security.UserContext;
import com.baosight.scc.ec.service.OrderItemService;
import com.baosight.scc.ec.web.EcGrid;
import com.google.common.collect.Lists;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

/**
 * Created by Charles on 2014/5/15.
 * 订单
 */
@Controller
@RequestMapping(value = "/orderCenter")
public class OrderController {

    @Autowired
    private OrderItemService orderItemService;
    @Autowired
    private UserContext userContext;

    //购物车页面
    private final static String CART_LIST = "order_shoppingCart";
    //列表页面
    private final static String BUYER_ORDER_LIST = "order_buyerOrderList";
    //列表页面
    private final static String SELLER_ORDER_LIST = "order_sellerOrderList";
    //订单确认页面
    private final static String ORDER_CHECKOUT = "order_checkOut";
    //订单提交成功
    private final static String SUBMIT_OK = "redirect:/orderCenter/orderSubmitSuccess";
    //订单提交失败
    private final static String SUBMIT_FAIL = "redirect:/orderCenter/orderSubmitFail";
    //订单提交成功
    private final static String OK_PAGE = "order_orderSubmitSuccess";
    //订单提交失败
    private final static String FAIL_PAGE = "order_orderSubmitFail";

    //买家查看订单详情
    private final static String BUYER_ORDER_VIEW = "order_buyerViewOrderDetail";

    //卖家查看订单详情
    private final static String SELLER_ORDER_VIEW = "order_sellerViewOrderDetail";

    @InitBinder
    public void initBinder(WebDataBinder binder) {
        binder.setDisallowedFields("id");
    }

    /**
     * 进入订单确认页面
     * @param request
     * @param uiModel
     * @return
     */
    @RequestMapping(value = "/checkOut", method = RequestMethod.GET)
    public String checkOutOrder(HttpServletRequest request,Model uiModel){
        String sessionId = request.getRequestedSessionId();
        Map<String,List<CartLine>> cartLineMap = (Map<String,List<CartLine>>)request.getSession().getAttribute("cart_"+sessionId);
        if(cartLineMap == null || cartLineMap.size() == 0){
            //如果session里的购物车为空则提示
//                redirectAttributes.addFlashAttribute("message", "购物车中没有商品，请添加商品到购物车");
//                return "";
            return CART_LIST;
        }
        EcUser user = userContext.getCurrentUser();

        Address address = user.getDefaultAddress();
        uiModel.addAttribute("address",address);
        uiModel.addAttribute("cartLineList",cartLineMap);
        return ORDER_CHECKOUT;
    }

    /**
     * 提交订单
     * @param request
     * @return
     */
    @RequestMapping(value = "/submitOrder", method = RequestMethod.POST)
    public String submitOrder(HttpServletRequest request){
        String sessionId = request.getRequestedSessionId();
        String addressId = request.getParameter("addressId");
        String orderTitle = request.getParameter("title");
        Map<String,List<CartLine>> cartLineMap = (Map<String,List<CartLine>>)request.getSession().getAttribute("cart_"+sessionId);
        if(cartLineMap == null || cartLineMap.size() == 0){
            //如果session里的购物车为空则提示
//                redirectAttributes.addFlashAttribute("message", "购物车中没有商品，请添加商品到购物车");
//                return "";
            return CART_LIST;
        }
        cartLineMap = orderItemService.createOrder(cartLineMap,addressId,orderTitle);
        request.getSession().setAttribute("cart_"+sessionId,cartLineMap);
        return SUBMIT_OK;
    }

    /**
     * 订单处理成功，跳转到成功提示页面
     * @param request
     * @return
     */
    @RequestMapping(value = "/orderSubmitSuccess", method = RequestMethod.GET)
    public String orderSubmitSuccess(HttpServletRequest request){
        return OK_PAGE;
    }

    /**
     * 订单提交失败，跳转到错误提示页面
     * @param request
     * @return
     */
    @RequestMapping(value = "/orderSubmitFail", method = RequestMethod.GET)
    public String orderSubmitFail(HttpServletRequest request){
        return FAIL_PAGE;
    }

    /**
     * 作为买家，查看我的订单列表
     * @param page
     * @param uiModel
     * @return
     */
    @RequestMapping(value = "/buyerOrderList", method = RequestMethod.GET)
    public String findBuyerOrderList(@RequestParam(value = "page", required = false,defaultValue = "1") Integer page, Model uiModel){
        EcUser user = userContext.getCurrentUser();
        Sort sort = new Sort(Sort.Direction.DESC, "createdTime");
        PageRequest pageRequest = null;
        if (page != null)
            pageRequest = new PageRequest(page - 1, 15, sort);
        else
            pageRequest = new PageRequest(0, 15, sort);
        EcGrid<OrderItem> grid = new EcGrid<OrderItem>();
        Page<OrderItem> orderItemPage = orderItemService.findByBuyer(user,pageRequest);
        grid.setCurrentPage(orderItemPage.getNumber() + 1);
        grid.setEcList(Lists.newArrayList(orderItemPage));
        grid.setTotalPages(orderItemPage.getTotalPages());
        grid.setTotalRecords(orderItemPage.getTotalElements());

        uiModel.addAttribute("grid", grid);
        return BUYER_ORDER_LIST;
    }

    /**
     * 作为买家，查看我的订单列表
     * @param id
     * @param uiModel
     * @return
     */
    @RequestMapping(value = "/buyerViewOrder/{id}", method = RequestMethod.GET)
    public String buyerViewOrder(@PathVariable("id") String id, Model uiModel){
        OrderItem orderItem = orderItemService.findById(id);
        uiModel.addAttribute("orderItem", orderItem);
        return BUYER_ORDER_VIEW;
    }

    /**
     * 作为买家，查看我的订单列表
     * @param id
     * @param uiModel
     * @return
     */
    @RequestMapping(value = "/sellerViewOrder/{id}", method = RequestMethod.GET)
    public String sellerViewOrder(@PathVariable("id") String id, Model uiModel){
        OrderItem orderItem = orderItemService.findById(id);
        uiModel.addAttribute("orderItem", orderItem);
        return SELLER_ORDER_VIEW;
    }

    /**
     * 作为买家，查看我的订单列表,过滤已取消的订单
     * @param page
     * @param uiModel
     * @return
     */
    @RequestMapping(value = "/buyerOrderListByStatus", method = RequestMethod.GET)
    public String findByBuyerAndStatusGreaterThan(@RequestParam(value = "page", required = false,defaultValue = "1") Integer page, @RequestParam(value = "status",required = false,defaultValue = "0")String status,Model uiModel){
        EcUser user = userContext.getCurrentUser();
        Sort sort = new Sort(Sort.Direction.DESC, "createdTime");
        PageRequest pageRequest = null;
        if (page != null)
            pageRequest = new PageRequest(page - 1, 15, sort);
        else
            pageRequest = new PageRequest(0, 15, sort);
        EcGrid<OrderItem> grid = new EcGrid<OrderItem>();
        Page<OrderItem> orderItemPage = orderItemService.findByBuyerAndStatusNotLike(user,status,pageRequest);
        grid.setCurrentPage(orderItemPage.getNumber() + 1);
        grid.setEcList(Lists.newArrayList(orderItemPage));
        grid.setTotalPages(orderItemPage.getTotalPages());
        grid.setTotalRecords(orderItemPage.getTotalElements());

        uiModel.addAttribute("grid", grid);
        return BUYER_ORDER_LIST;
    }

    /**
     * 作为卖家，查看我的订单列表
     * @param page
     * @param uiModel
     * @return
     */
    @RequestMapping(value = "/sellerOrderList", method = RequestMethod.GET)
    public String findSellerOrderList(@RequestParam(value = "page", required = false,defaultValue = "1") Integer page, Model uiModel){
        EcUser user = userContext.getCurrentUser();
        Sort sort = new Sort(Sort.Direction.DESC, "createdTime");
        PageRequest pageRequest = null;
        if (page != null)
            pageRequest = new PageRequest(page - 1, 15, sort);
        else
            pageRequest = new PageRequest(0, 15, sort);
        EcGrid<OrderItem> grid = new EcGrid<OrderItem>();
        Page<OrderItem> orderItemPage = orderItemService.findBySeller(user,pageRequest);
        grid.setCurrentPage(orderItemPage.getNumber() + 1);
        grid.setEcList(Lists.newArrayList(orderItemPage));
        grid.setTotalPages(orderItemPage.getTotalPages());
        grid.setTotalRecords(orderItemPage.getTotalElements());

        uiModel.addAttribute("grid", grid);
        return SELLER_ORDER_LIST;
    }

    /**
     * 更新订单状态，前端Ajax请求
     * @param request
     * @return
     */
    @RequestMapping(value = "/order", params = "updateStatus", method = RequestMethod.POST,produces = "application/json")
    @ResponseBody
    public Map updateStatus(HttpServletRequest request){
        String orderId = request.getParameter("id");
        String status = request.getParameter("status");
        Map resultMap = new HashMap();
        String result = orderItemService.updateStatus(orderId,status);
        resultMap.put("result",result);
        return resultMap;
    }
}