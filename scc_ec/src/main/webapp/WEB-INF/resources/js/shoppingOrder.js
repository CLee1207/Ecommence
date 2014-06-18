/**
 * Created by Charles on 2014/5/29.
 */
$(function(){
    $(".optStatus").click(function(){
        var orderId =$(this).attr("data-id");
        var status =$(this).attr("data-status");
        $.ajax({
            type: "POST",
            url: "/orderCenter/order/updateStatus",
            data:{
                id:orderId,
                status:status
            },
            success:function(data){
                $(this).remove();
                var state = "";
                if(status == "BUYER_CANCEL"){
                    state = "已取消";
                }else if(status == "GOODS_RECEIVE"){
                    state = "已确认收货";
                }else if(status == "GOODS_DELIVER"){
                    state = "已发货";
                }else if(status == "SELLER_CANCEL"){
                    state = "BUYER_CANCEL";
                }
                $(".orderState_"+orderId).html(state);
            }
        });
    });

});
