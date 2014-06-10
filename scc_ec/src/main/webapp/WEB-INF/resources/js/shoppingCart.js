/**
 * Created by Charles on 2014/5/27.
 */
$(function () {
    $(".addCart").click(function () {
        var itemId = $(this).attr("data-id");
        var sellerId = $(this).attr("data-spid");
        var quantity = $("#quantity").val();
        $.ajax({
            type: "POST",
            url: "/orderCenter/cartLine?add",
            data: {
                id: itemId,
                sellerId: sellerId,
                type: "F",
                quantity: quantity
            },
            success: function (data) {
                console.log(data);
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
            url: "/orderCenter/cartLine?add",
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
            url: "/orderCenter/cartLine?add",
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
            }
        });
    });

    $(".quantity").blur(function () {
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
            url: "/orderCenter/cartLine?refresh",
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
            }
        });
    });

    $(".delete").click(function () {
        var itemId = $(this).attr("data-id");
        var sellerId = $(this).attr("seller-id");
        if (confirm("是否从购物车中删除?!")) {
            $.ajax({
                type: "POST",
                url: "/orderCenter/cartLine?delete",
                data: {
                    id: itemId,
                    sellerId: sellerId
                },
                success: function (data) {
                    if (data.result == "success") {
                        $(".dataTable_" + itemId).remove();
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
        $.ajax({
            type: "POST",
            url: "/orderCenter/cartLine?reAdd",
            data: {
                id: itemId,
                sellerId: sellerId
            },
            success: function (data) {
                console.log(data);
            }
        });
    });

    $('#address').click(function () {
        $.ajax({
            url: "/buyerCenter/defaultAddress",
            type:'post',
            data:{
                addressId:'2'
            },
            success:function(data){
                alert('ok');
            }
        });
    });
});
