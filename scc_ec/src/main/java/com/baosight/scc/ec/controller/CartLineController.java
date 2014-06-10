package com.baosight.scc.ec.controller;

import com.baosight.scc.ec.model.*;
import com.baosight.scc.ec.security.UserContext;
import com.baosight.scc.ec.service.CartLineService;
import com.baosight.scc.ec.service.FabricService;
import com.baosight.scc.ec.service.MaterialService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import javax.servlet.http.HttpServletRequest;
import java.util.*;

/**
 * Created by Charles on 2014/5/16.
 * 购物车，所有购物车的商品保存在session中
 */
@Controller
@RequestMapping(value = "/orderCenter")
public class CartLineController {
    @Autowired
    private MaterialService materialService;
    @Autowired
    private FabricService fabricService;
    @Autowired
    private UserContext userContext;
    //购物车页面
    private final static String CART_LIST = "order_shoppingCart";

    @InitBinder
    public void initBinder(WebDataBinder binder) {
        binder.setDisallowedFields("id");
    }
    /*
      @author Charles
      用户添加商品到购物车，前台AJax提交请求，即向session中存入一个商品Map对象
      @param:request 前端请求商品的id和类型
      @return success:商品添加成功；error:商品添加失败
      @exception
   */
    @RequestMapping(value = "/cartLine", params = "add", method = RequestMethod.POST,produces = "application/json")
    @ResponseBody
    public Map addItem(HttpServletRequest request){
        Map resultMap = new HashMap();
        try {
            String sessionId = request.getRequestedSessionId();
            EcUser buyer = userContext.getCurrentUser();
            boolean flag =true;
            String itemId = request.getParameter("id");
            String itemType = request.getParameter("type");
            String sellerId = request.getParameter("sellerId");
            int quantity = Integer.parseInt(request.getParameter("quantity"));
            Map<String,List<CartLine>> cartLineMap = (Map<String,List<CartLine>>)request.getSession().getAttribute("cart_"+sessionId);
            List<CartLine> cartLineList = new ArrayList<CartLine>();
            if(cartLineMap == null || cartLineMap.size() == 0){
                //如果session里的购物车为空则创建一个新的购物车
                cartLineMap = new HashMap<String, List<CartLine>>();
            }
            if(itemId == "" || sellerId == "" || itemType == ""){
                resultMap.put("result", "error");
                flag = false;
            }
            //判断买家是否购买了自己的商品
            if(buyer.getId().equals(sellerId)){
                resultMap.put("result","wrongBuyer");
                //测试时flag=true
                //flag = false;
                flag = true;
            }
            //根据itemId查询商品信息和供应商信息存入到cartLine中
            //由于供应商信息存储在Material和Fabric表中，所以需要根据商品类型来查询订单所需信息
            //session中具体要存储的数据为Map<供应商id,Map<商品id,商品>>
            //判断购物车中是否已经存在此商品，如果存在则数量累加，否则新增商品到购物车
            if(flag){
                CartLine cartLine = new CartLine();
                cartLine.setItemId(itemId);
                cartLine.setQuantity(quantity);
                if(cartLineMap.get(sellerId) != null){
                    cartLineList = cartLineMap.get(sellerId);
                    int index = -1;
                    for (int i = 0;i < cartLineList.size();i ++){
                        CartLine cl = cartLineList.get(i);
                        if(cartLine.compare(cl)){
                            index = i;
                        }
                    }
                    if(index > -1){
                        CartLine cartLineOld = cartLineList.get(index);
                        Map<Double,Double> priceRanges = cartLineOld.getPriceRange();
                        List<Double> prices = new ArrayList<Double>();
                        double price = 0.0;
                        int quantityNew = cartLineOld.getQuantity()+quantity;
                        for(Map.Entry<Double,Double> entry : priceRanges.entrySet()){
                            prices.add(entry.getKey());
                        }
                        Collections.sort(prices);
                        for (int i = 0 ; i < prices.size() ; i ++){
                            if(prices.size() == 1 || quantityNew < priceRanges.get(prices.get(i))){
                                price = priceRanges.get(prices.get(0));
                            }else if(quantityNew < prices.get(i)){
                                price = priceRanges.get(prices.get(i-1));
                                break;
                            }else{
                                price = priceRanges.get(prices.get(prices.size()-1));
                            }
                        }
                        cartLineOld.setPrice(price);
                        cartLineOld.setQuantity(quantityNew);
                        cartLineOld.setSummary(quantityNew*price);
                        cartLineList.remove(index);
                        cartLineList.add(cartLineOld);
                        resultMap.put("quantityNew", quantityNew);
                        resultMap.put("priceNew", price);
                        resultMap.put("summaryNew", quantityNew*price);
                    }else{
                        //购物车中没有此商品时，新增一件该商品
                        EcUser supplier = new EcUser();
                        if(itemType.equals("M")){
                            Material material =  materialService.getMaterialInfo(itemId);
                            supplier = material.getCreatedBy();
                            cartLine = new CartLine(material);
                        }else if(itemType.equals("F")){
                            Fabric fabric = fabricService.findById(itemId);
                            supplier = fabric.getCreatedBy();
                            cartLine = new CartLine(fabric);
                        }
                        Map<Double,Double> priceRanges = cartLine.getPriceRange();
                        List<Double> prices = new ArrayList<Double>();
                        double price = 0.0;
                        for(Map.Entry<Double,Double> entry : priceRanges.entrySet()){
                            prices.add(entry.getKey());
                        }
                        Collections.sort(prices);
                        for (int i = 0 ; i < prices.size() ; i ++){
                            if(prices.size() == 1 || quantity < priceRanges.get(prices.get(i))){
                                price = priceRanges.get(prices.get(0));
                            }else if(quantity < prices.get(i)){
                                price = priceRanges.get(prices.get(i-1));
                                break;
                            }else{
                                price = priceRanges.get(prices.get(prices.size()-1));
                            }
                        }
                        cartLine.setQuantity(quantity);
                        cartLine.setPrice(price);
                        cartLine.setSummary(price*quantity);
                        cartLineList.add(cartLine);
                    }
                    cartLineMap.put(sellerId,cartLineList);
                }else{
                    //购物车中没有此商品时，新增一件该商品
                    EcUser supplier = new EcUser();
                    if(itemType.equals("M")){
                        Material material =  materialService.getMaterialInfo(itemId);
                        supplier = material.getCreatedBy();
                        cartLine = new CartLine(material);
                    }else if(itemType.equals("F")){
                        Fabric fabric = fabricService.findById(itemId);
                        supplier = fabric.getCreatedBy();
                        cartLine = new CartLine(fabric);
                    }
                    Map<Double,Double> priceRanges = cartLine.getPriceRange();
                    List<Double> prices = new ArrayList<Double>();
                    double price = 0.0;
                    for(Map.Entry<Double,Double> entry : priceRanges.entrySet()){
                        prices.add(entry.getKey());
                    }
                    Collections.sort(prices);
                    for (int i = 0 ; i < prices.size() ; i ++){
                        if(prices.size() == 1 || quantity < priceRanges.get(prices.get(i))){
                            price = priceRanges.get(prices.get(0));
                        }else if(quantity < prices.get(i)){
                            price = priceRanges.get(prices.get(i-1));
                            break;
                        }else{
                            price = priceRanges.get(prices.get(prices.size()-1));
                        }
                    }
                    cartLine.setQuantity(quantity);
                    cartLine.setPrice(price);
                    cartLine.setSummary(price*quantity);
                    cartLineList.add(cartLine);
                    cartLineMap.put(sellerId,cartLineList);
                }
                request.getSession().setAttribute("cart_" + sessionId, cartLineMap);
                resultMap.put("result", "success");
            }
        }catch (Exception e){
            e.printStackTrace();
            resultMap.put("result", "error");
        }
        return resultMap;
    }

    /*
      @author Charles
      查看购物车
      @param:
      @param:
      @return 跳转到订单购物车商品确认页面
      @exception
   */
    @RequestMapping(value = "/shopCart",method = RequestMethod.GET)
    public String showCart(HttpServletRequest request,
                           RedirectAttributes redirectAttributes,
                           Model uiModel){
        try {
            String sessionId = request.getRequestedSessionId();
            Map<String,List<CartLine>> cartLineMap = (Map<String,List<CartLine>>)request.getSession().getAttribute("cart_"+sessionId);
            if(cartLineMap == null || cartLineMap.size() == 0){
                //如果session里的购物车为空则提示
//                redirectAttributes.addFlashAttribute("message", "购物车中没有商品，请添加商品到购物车");
//                return "";
                return CART_LIST;
            }
            //更新购物车中重新放回的商品check状态为0
            for (Map.Entry<String,List<CartLine>> entry: cartLineMap.entrySet()){
                List<CartLine> cartLines = entry.getValue();
                for (int i = 0; i < cartLines.size(); i ++){
                    if(cartLines.get(i).getIsCheck() == 1){
                        cartLines.get(i).setIsCheck(0);
                    }
                }
            }
            uiModel.addAttribute("cartLineList",cartLineMap);
             return CART_LIST;
        }catch (Exception e){
            e.printStackTrace();
        }
        return CART_LIST;
    }

    /*
     @author Charles
     用户删除购物车里商品，前台AJax提交请求，更新session中商品Map对象
     @param:request 商品id
     @return success:商品删除成功；error:商品添加删除失败
     @exception
  */
    @RequestMapping(value = "/cartLine", params = "delete", method = RequestMethod.POST,produces = "application/json")
    @ResponseBody
    public Map deleteItem(HttpServletRequest request){
        Map resultMap = new HashMap();
        try {
            String sessionId = request.getRequestedSessionId();
            CartLine cartLine = new CartLine();
            String itemId = request.getParameter("id");
            String sellerId = request.getParameter("sellerId");
            Map<String,List<CartLine>> cartLineMap = (Map<String,List<CartLine>>)request.getSession().getAttribute("cart_"+sessionId);
            if(cartLineMap == null || cartLineMap.size() == 0){
                //如果session里的购物车为空则提示
                resultMap.put("result", "emptyCart");
            }
            List<CartLine> cartLines = cartLineMap.get(sellerId);
            cartLine.setItemId(itemId);
            if(cartLineMap.get(sellerId) != null){
                cartLines = cartLineMap.get(sellerId);
                int index = -1;
                for (int i = 0;i < cartLines.size();i ++){
                    CartLine cl = cartLines.get(i);
                    if(cartLine.compare(cl)){
                        index = i;
                    }
                }
                if(index > -1){
                    cartLines.remove(index);
                }
                if(cartLines.size() == 0){
                    cartLineMap.remove(sellerId);
                    resultMap.put("cartLine", "empty");
                }else{
                    cartLineMap.put(sellerId,cartLines);
                }

            }
            request.getSession().setAttribute("cart_" + sessionId, cartLineMap);
            resultMap.put("result", "success");
        }catch (Exception e){
            e.printStackTrace();
            resultMap.put("result", "error");
        }
        return resultMap;
    }

    /*
     @author Charles
     用户在购物车车中手动输入数量，前台AJax提交请求，即向session中存入一个商品Map对象
     @param:request 前端请求商品的id和类型
     @return success:商品添加成功；error:商品添加失败
     @exception
  */
    @RequestMapping(value = "/cartLine", params = "refresh", method = RequestMethod.POST,produces = "application/json")
    @ResponseBody
    public Map refreshItem(HttpServletRequest request){
        Map resultMap = new HashMap();
        try {
            String sessionId = request.getRequestedSessionId();
            CartLine cartLine = new CartLine();
            String itemId = request.getParameter("id");
            String sellerId = request.getParameter("sellerId");
            int quantity = Integer.parseInt(request.getParameter("quantity"));
            Map<String,List<CartLine>> cartLineMap = (Map<String,List<CartLine>>)request.getSession().getAttribute("cart_"+sessionId);
            List<CartLine> cartLineList = new ArrayList<CartLine>();
            if(cartLineMap == null || cartLineMap.size() == 0){
                //如果session里的购物车为空则创建一个新的购物车
                cartLineMap = new HashMap<String, List<CartLine>>();
            }
            if(itemId == "" || sellerId == ""){
                resultMap.put("result", "error");
            }
            cartLine.setItemId(itemId);
            cartLine.setQuantity(quantity);
            if(cartLineMap.get(sellerId) != null){
                cartLineList = cartLineMap.get(sellerId);
                int index = -1;
                for (int i = 0;i < cartLineList.size();i ++){
                    CartLine cl = cartLineList.get(i);
                    if(cartLine.compare(cl)){
                        index = i;
                    }
                }
                if(index > -1){
                    CartLine cartLineOld = cartLineList.get(index);
                    Map<Double,Double> priceRanges = cartLineOld.getPriceRange();
                    List<Double> prices = new ArrayList<Double>();
                    double price = 0.0;
                    for(Map.Entry<Double,Double> entry : priceRanges.entrySet()){
                        prices.add(entry.getKey());
                    }
                    Collections.sort(prices);
                    for (int i = 0 ; i < prices.size() ; i ++){
                        if(prices.size() == 1 || quantity < priceRanges.get(prices.get(i))){
                            price = priceRanges.get(prices.get(0));
                        }else if(quantity < prices.get(i)){
                            price = priceRanges.get(prices.get(i-1));
                            break;
                        }else{
                            price = priceRanges.get(prices.get(prices.size()-1));
                        }
                    }
                    cartLineOld.setPrice(price);
                    cartLineOld.setQuantity(quantity);
                    cartLineOld.setSummary(quantity*price);
                    cartLineList.remove(index);
                    cartLineList.add(cartLineOld);
                    resultMap.put("quantityNew", quantity);
                    resultMap.put("priceNew", price);
                    resultMap.put("summaryNew", quantity*price);
                }
                cartLineMap.put(sellerId,cartLineList);
            }
            request.getSession().setAttribute("cart_" + sessionId, cartLineMap);
            resultMap.put("result", "success");
        }catch (Exception e){
            e.printStackTrace();
            resultMap.put("result", "error");
        }
        return resultMap;
    }

    /*
     @author Charles
     将订单确认页面的商品放回购物车，前台AJax提交请求，即向session中存入一个商品Map对象
     @param:request 前端请求商品的id和类型
     @return success:商品添加成功；error:商品添加失败
     @exception
  */
    @RequestMapping(value = "/cartLine", params = "reAdd", method = RequestMethod.POST,produces = "application/json")
    @ResponseBody
    public Map reAdd(HttpServletRequest request){
        Map resultMap = new HashMap();
        try {
            String sessionId = request.getRequestedSessionId();
            CartLine cartLine = new CartLine();
            String itemId = request.getParameter("id");
            String sellerId = request.getParameter("sellerId");
            Map<String,List<CartLine>> cartLineMap = (Map<String,List<CartLine>>)request.getSession().getAttribute("cart_"+sessionId);
            List<CartLine> cartLineList = new ArrayList<CartLine>();
            if(cartLineMap == null || cartLineMap.size() == 0){
                //如果session里的购物车为空则创建一个新的购物车
                cartLineMap = new HashMap<String, List<CartLine>>();
            }
            if(itemId == "" || sellerId == ""){
                resultMap.put("result", "error");
            }
            cartLine.setItemId(itemId);
            if(cartLineMap.get(sellerId) != null){
                cartLineList = cartLineMap.get(sellerId);
                int index = -1;
                for (int i = 0;i < cartLineList.size();i ++){
                    CartLine cl = cartLineList.get(i);
                    if(cartLine.compare(cl)){
                        index = i;
                    }
                }
                if(index > -1){
                    CartLine cartLineOld = cartLineList.get(index);
                    cartLineOld.setIsCheck(1);
                    cartLineList.set(index,cartLineOld);
                }
                cartLineMap.put(sellerId,cartLineList);
            }
            request.getSession().setAttribute("cart_" + sessionId, cartLineMap);
            resultMap.put("result", "success");
        }catch (Exception e){
            e.printStackTrace();
            resultMap.put("result", "error");
        }
        return resultMap;
    }

    /*
     @author Charles
     页面头上现实购物车商品时的Ajax请求
     @param:request
     @return success,error
     @exception
  */
    @RequestMapping(value = "/viewCart", method = RequestMethod.POST,produces = "application/json")
    @ResponseBody
    public Map viewCart(HttpServletRequest request){
        Map resultMap = new HashMap();
        String sessionId = request.getRequestedSessionId();
        Map<String,List<CartLine>> cartLineMap = (Map<String,List<CartLine>>)request.getSession().getAttribute("cart_"+sessionId);
        if(cartLineMap == null || cartLineMap.size() == 0){
            //如果session里的购物车为空则提示
//                redirectAttributes.addFlashAttribute("message", "购物车中没有商品，请添加商品到购物车");
//                return "";
            resultMap.put("result","error");
            return resultMap;
        }
        //更新购物车中重新放回的商品check状态为0
        for (Map.Entry<String,List<CartLine>> entry: cartLineMap.entrySet()){
            List<CartLine> cartLines = entry.getValue();
            for (int i = 0; i < cartLines.size(); i ++){
                if(cartLines.get(i).getIsCheck() == 1){
                    cartLines.get(i).setIsCheck(0);
                }
            }
        }
        resultMap.put("result","success");
        resultMap.put("cartLineList",cartLineMap);
        return resultMap;
    }
}