/**
 * Created by zodiake on 2014/6/9.
 */
$(function () {
    $('a#add').click(function () {
        $.ajax({
            method: 'post',
            url: '/userCenter/favourites/item/1',
            success: function () {
                alert(22);
            }
        });
    });
    $('a#remove').click(function () {
        $.ajax({
            method: 'delete',
            url: '/userCenter/favourites/item/2',
            success: function () {
                alert(22);
            }
        });
    });
    $('a#addShop').click(function () {
        $.ajax({
            method: 'post',
            url: '/userCenter/favourites/shops/1',
            success: function () {
                alert(22);
            }
        });
    });
    $('a#removeShop').click(function () {
        $.ajax({
            method: 'delete',
            url: '/userCenter/favourites/shops/2',
            success: function () {
                alert(22);
            }
        });
    });
    $('dl.commodity-list a').hover(function () {
        var id = $(this).attr('data-id');
        var source = $(this);
        if (source.siblings('div').size() == 0) {
            $.ajax({
                method: 'get',
                url: '/fabricCategory/' + id + '/secondCategory',
                success: function (data) {
                    var container = $('<div></div>');
                    source.after(container);
                    for (var i = 0; i < data.length; i++) {
                        container.append('<a data-id="' + data[i].id + '">' + data[i].name + '</a>');
                    }
                }
            });
        }
    });
})
