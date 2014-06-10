package com.baosight.scc.ec.service.jpa;

import com.baosight.scc.ec.model.*;
import com.baosight.scc.ec.repository.OrderItemRepository;
import com.baosight.scc.ec.security.UserContext;
import com.baosight.scc.ec.service.AddressService;
import com.baosight.scc.ec.service.OrderAddressService;
import com.baosight.scc.ec.service.OrderItemService;
import com.baosight.scc.ec.type.OrderState;
import com.baosight.scc.ec.utils.GuidUtil;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by Charles on 2014/5/16.
 */
@Service
@Transactional
public class OrderItemServiceImpl implements OrderItemService {
    @Autowired
    private OrderItemRepository or;

    @Autowired
    private UserContext userContext;
    @Autowired
    private AddressService addressService;
    @Autowired
    private OrderAddressService orderAddressService;
    @Override
    @Transactional(readOnly = true)
    public OrderItem findById(String id) {
        return or.findOne(id);
    }

    @Override
    public OrderItem save(OrderItem item) {
        return or.save(item);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<OrderItem> findByBuyer(EcUser buyer,Pageable pageable) {
        return or.findByBuyer(buyer,pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<OrderItem> findBySeller(EcUser seller,Pageable pageable) {
        return or.findBySeller(seller, pageable);
    }

    /**
     * 订单提交
     * @param cartLineMap
     * @param addressId
     * @param orderTitle
     * @return
     */
    public Map createOrder(Map<String,List<CartLine>> cartLineMap,String addressId,String orderTitle){
        DateTime dt = new DateTime();
        SimpleDateFormat sdf = new SimpleDateFormat("yyMMddHHmmssSSSS");

        for (Map.Entry<String,List<CartLine>> entry: cartLineMap.entrySet()){
            String orderItemId = GuidUtil.newGuid();
            OrderItem orderItem = new OrderItem();
            orderItem.setId(orderItemId);
            String orderNo = sdf.format(dt.toDate())+Math.round(Math.random()*900+100);
            String sellerId = entry.getKey();
            List<CartLine> cartLines = entry.getValue();
            List<OrderLine> orderLines = new ArrayList<OrderLine>();
            Address address = addressService.findById(addressId);
            OrderAddress orderAddress = new OrderAddress(address,orderTitle);
            double orderSum = 0;
            orderAddressService.save(orderAddress);
            EcUser buyer = userContext.getCurrentUser();
            EcUser seller = new EcUser();
            seller.setId(sellerId);
            for (int i = cartLines.size()-1; i >= 0; i --){
                if(cartLines.get(i).getIsCheck() == 0){
                    OrderLine orderLine = new OrderLine(cartLines.get(i));
                    orderLine.setOrderItem(orderItem);
                    orderLines.add(orderLine);
                    orderSum += orderLine.getSum();
                    cartLines.remove(i);
                }
            }
            if(cartLines.isEmpty()){
                cartLineMap.remove(sellerId);
            }
            orderItem.setBuyer(buyer);
            orderItem.setLines(orderLines);
            orderItem.setOrderAddress(orderAddress);
            orderItem.setSeller(seller);
            orderItem.setOrderNo(orderNo);
            orderItem.setSummary(orderSum);
            orderItem.setStatus(OrderState.WAIT_GOODS_SEND.toString());
            save(orderItem);
        }
        return  cartLineMap;
    }

    /**
     * 更新订单状态
     * @param orderId   订单id
      * @param status   更新状态
     * @return
     */
    public String updateStatus(String orderId,String status){
        OrderItem orderItemOld = findById(orderId);
        //更新状态前校验是否已更新
        if(OrderState.valueOf(status).ordinal() <= OrderState.valueOf(orderItemOld.getStatus()).ordinal()){
            return "statusError";
        }else{
            orderItemOld.setStatus(status);
            DateTime dateTime = new DateTime();
            Calendar calendar = dateTime.toCalendar(Locale.SIMPLIFIED_CHINESE);
            //如果是取消交易、发货、确认收货等操作，记录相应时间
            if(status.equals(OrderState.) || status == 2){
                orderItemOld.setCancelTime(calendar);
            }else if(status == 3){
                orderItemOld.setDeliverTime(calendar);
            }else if(status == 4){
                orderItemOld.setReceiveTime(calendar);
            }
            return "success";
            }
//        OrderItem orderItemOld = findById(orderId);
//        //更新状态前校验是否已更新
//        if(status <= orderItemOld.getStatus()){
//            return "statusError";
//        }else{
//            orderItemOld.setStatus(status);
//            DateTime dateTime = new DateTime();
//            Calendar calendar = dateTime.toCalendar(Locale.SIMPLIFIED_CHINESE);
//            //如果是取消交易、发货、确认收货等操作，记录相应时间
//            if(status == 1 || status == 2){
//                orderItemOld.setCancelTime(calendar);
//            }else if(status == 3){
//                orderItemOld.setDeliverTime(calendar);
//            }else if(status == 4){
//                orderItemOld.setReceiveTime(calendar);
//            }
//            return "success";
//        }
        return null;
    }

    @Override
    @Transactional(readOnly = true)
    public Page<OrderItem> findByBuyerAndStatusNotLike(EcUser buyer, String status, Pageable pageable) {
        return or.findByBuyerAndStatusNotLike(buyer, status, pageable);
    }
}
