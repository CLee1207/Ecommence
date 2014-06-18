/**
 * Created by Charles on 2014/5/27.
 */
$(function () {
    $(".addCart").click(function () {
        var itemId = $(this).attr("data-id");
        $.ajax({
            type: "POST",
            url: "/orderCenter/cartLine/add",
            data: {
                id: itemId
            },
            success: function (data) {
                $(".cartQuantity").html(data.cartQuantity);
            }
        });
    });

    $(".increase").click(function () {
        var itemId = $(this).attr("data-id");
        var sellerId = $(this).attr("seller-id");
        var quantity = 1;
        var quantityIpt = $("#" + itemId + "_quantity"), summarySpan = $("#" + itemId + "_summary"), priceSpan = $("#" + itemId + "_price");
        $.ajax({
            type: "POST",
            url: "/orderCenter/cartLine/add",
            data: {
                id: itemId,
                sellerId: sellerId,
                quantity: quantity
            },
            success: function (data) {
                var quantityNew = data.quantityNew, priceNew = data.priceNew, summaryNew = data.summaryNew;
                quantityIpt.val(quantityNew);
                priceSpan.html(priceNew);
                summarySpan.html(summaryNew);
                $(".cartQuantity").html(data.cartQuantity);
            }
        });
    });

    $(".reduce").click(function () {
        var itemId = $(this).attr("data-id");
        var sellerId = $(this).attr("seller-id");
        var quantityIpt = $("#" + itemId + "_quantity"), summarySpan = $("#" + itemId + "_summary"), priceSpan = $("#" + itemId + "_price");
        var quantity = -1;
        if (quantityIpt.val() < 2) {
            return false;
        }
        $.ajax({
            type: "POST",
            url: "/orderCenter/cartLine/add",
            data: {
                id: itemId,
                sellerId: sellerId,
                quantity: quantity
            },
            success: function (data) {
                var quantityNew = data.quantityNew, priceNew = data.priceNew, summaryNew = data.summaryNew;
                quantityIpt.val(quantityNew);
                priceSpan.html(priceNew);
                summarySpan.html(summaryNew);
                $(".cartQuantity").html(data.cartQuantity);
            }
        });
    });

    $(".quantity").change(function () {
        var itemId = $(this).attr("data-id");
        var sellerId = $(this).attr("seller-id");
        var quantity = $(this).val(), summarySpan = $("#" + itemId + "_summary"), priceSpan = $("#" + itemId + "_price");
        var rule = /^[0-9]*[0-9][0-9]*$/;
        if (!rule.test(quantity)) {
            alert("输入的数量必须为整数");
            document.execCommand("undo");
            return false;
        }
        if (quantity > 2147483647) {
            alert("输入的数量超过最大值");
            document.execCommand("undo");
            return false;
        }
        $.ajax({
            type: "POST",
            url: "/orderCenter/cartLine/refresh",
            data: {
                id: itemId,
                sellerId: sellerId,
                quantity: quantity
            },
            success: function (data) {
                var quantityNew = data.quantityNew, priceNew = data.priceNew, summaryNew = data.summaryNew;
                $(this).val(quantityNew);
                priceSpan.html(priceNew);
                summarySpan.html(summaryNew);
                $(".cartQuantity").html(data.cartQuantity);
            }
        });
    });

    $(".delete").click(function () {
        var itemId = $(this).attr("data-id");
        var sellerId = $(this).attr("seller-id");
        if (confirm("是否从购物车中删除?!")) {
            $.ajax({
                type: "POST",
                url: "/orderCenter/cartLine/delete",
                data: {
                    id: itemId,
                    sellerId: sellerId
                },
                success: function (data) {
                    if (data.result == "success") {
                        $(".dataTable_" + itemId).remove();
                        $(".cartQuantity").html(data.cartQuantity);
                        alert("删除成功");
                    }
                    if (data.cartLine == "empty") {
                        $(".data_" + itemId).remove();
                    }
                }
            });
        } else {
            return false;
        }
    });

    $(".reAddCart").click(function () {
        var itemId = $(this).attr("data-id");
        var sellerId = $(this).attr("data-spid");
        var price = $(this).attr("data-price"),summaryPrice=$("#summaryPrice").val();
        var GoodsCount = $("#GoodsCount").val()-1;
        $.ajax({
            type: "POST",
            url: "/orderCenter/cartLine/reAdd",
            data: {
                id: itemId,
                sellerId: sellerId
            },
            success: function (data) {
                $(".dataTable_"+itemId).remove();
                $(".summaryPrice").html(summaryPrice-price);
                $("#summaryPrice").val(summaryPrice-price);
                $("#GoodsCount").val(GoodsCount);
            }
        });
    });

    $('.changeTitle').click(function () {
        var orderTitle = $(".orderTitle").val();
        $(".showOrderTitle").html(orderTitle);
        $("#title").val(orderTitle);
    });

    $('.needInvoice').click(function () {
        var isChecked = $(this).attr("checked");
        if(isChecked == "checked"){
            $(this).removeAttr("checked");
            $("#needInvoice").val("0");
        }else{
            $(this).attr("checked","checked");
            $("#needInvoice").val("1");
        }
    });

    $('.defaultAddress').click(function () {
        var addressId = $("#addressId").val();
        if(addressId == null || addressId == ""){
            return false;
        }else{
            $.ajax({
                url: "/buyerCenter/defaultAddress",
                type:'post',
                data:{
                    addressId:addressId
                },
                success:function(data){
                    alert('设置成功!');
                }
            });
        }
    });

    $(".drop-menu").mouseenter(function () {
        var viewAddress = $("#viewAddress");
        if(viewAddress.val() == 0){
            $.ajax({
                method: "GET",
                url: "/buyerCenter/address",
                success: function (data) {
                    viewAddress.val(1);
                    $(".addressList").html(data);
                }
            });
        }else{
            return;
        }
    });

    $(".orderImmediately").click(function(){
        $("#orderDiv").css({display:''});
    });

    $(".quantityHandle").change(function () {
        var itemId = $(this).attr("data-id");
        var sellerId = $(this).attr("seller-id");
        var quantity = $(this).val(), summarySpan = $("#" + itemId + "_summary"), priceSpan = $("#" + itemId + "_price");
        var rule = /^[0-9]*[0-9][0-9]*$/;
        if (!rule.test(quantity)) {
            alert("输入的数量必须为整数");
            document.execCommand("undo");
            return false;
        }
        if (quantity > 2147483647) {
            alert("输入的数量超过最大值");
            document.execCommand("undo");
            return false;
        }
    });

    $(".immediatelyToCart").click(function () {
        var itemId = $(this).attr("data-id");
        $.ajax({
            type: "GET",
            url: "/orderCenter/immediatelyToCart",
            success: function (data) {
                $(".cartQuantity").html(data.cartQuantity);
                $(".dataTable_"+itemId).remove();
            }
        });
    });
});

function selectAddress(addressId){
    var receiveName = $(".receiveName_"+addressId).html();
    var addressDetail = $(".addressDetail_"+addressId).html();
    var street = $(".street_"+addressId).html();
    $(".dropAddress").html(addressDetail+street);
    $(".showOrderTitle").html(receiveName);
    $("#addressId").val(addressId);
    $("#title").val(receiveName);
    $(".orderTitle").val(receiveName);
}

function preSubmit(){
    var GoodsCount = $("#GoodsCount").val();
    if(GoodsCount<1){
        alert("订单中没有找到商品，请重新购买!");
        return false;
    }else{
        $("#orderForm").submit();
    }
}