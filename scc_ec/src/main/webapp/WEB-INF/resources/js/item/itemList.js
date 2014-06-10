/**
 * Created by zodiake on 2014/6/10.
 */
//123
$(function () {
    $('a.down').click(function (event) {
        var src=$(this);
        var id = src.attr('data-id');
        $.ajax({
            method: 'put',
            url: '/sellerCenter/Item/' + id,
            success:function(){
               this.parent().prev().text('下架');
            }
        });
    });
});
