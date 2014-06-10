/**
 * Created by zodiake on 2014/6/9.
 */
$(function () {
    $('a#add').click(function () {
        $.ajax({
            method: 'post',
            url:'/userCenter/favourites/item/1',
            success:function(){
                alert(22);
            }
        });
    });
    $('a#remove').click(function () {
        $.ajax({
            method: 'delete',
            url:'/userCenter/favourites/item/2',
            success:function(){
                alert(22);
            }
        });
    });
})
