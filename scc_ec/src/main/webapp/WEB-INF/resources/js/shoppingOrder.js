/**
 * Created by Charles on 2014/5/29.
 */
$(function(){
    $(".optStatus").click(function(){
        var orderId =$(this).attr("data-id");
        var status =$(this).attr("data-status");
        $.ajax({
            type: "POST",
            url: "/orderCenter/order?updateStatus",
            data:{
                id:orderId,
                status:status
            },
            success:function(data){
                console.log(data);
            }
        });
    });

});
