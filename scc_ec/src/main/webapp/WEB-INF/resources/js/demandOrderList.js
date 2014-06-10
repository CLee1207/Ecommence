/**
 * Created by zodiake on 2014/5/23.
 */
$(function(){
    $(".addCart").click(function(){
        var itemId =$(this).attr("data-id");
        var sellerId =$(this).attr("data-spid");
        var quantity = $("#quantity").val();
        $.ajax({
            type: "POST",
            url: "/orderCenter/cartLine?add",
            data:{
                id:itemId,
                sellerId:sellerId,
                type:"F",
                quantity:quantity
            },
            success:function(data){
                alert(data.result);
                console.log(data);
            }
        });
    });
});
