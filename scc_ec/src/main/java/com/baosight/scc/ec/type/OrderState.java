package com.baosight.scc.ec.type;

/**
 * Created by Charles on 2014/6/9.
 */
public enum OrderState {
     WAIT_GOODS_SEND,  //等待卖家发货
    BUYER_CANCEL,   //买家取消交易
    SELLER_CANCEL,  //卖家取消交易
    GOODS_DELIVER,  //已发货
    GOODS_RECEIVE,  //已确认收货
    BUYER_APPRAISE, //买家已评价
    SELLER_APPRAISE,    //卖家已评价
    FINISH  //交易结束
}
