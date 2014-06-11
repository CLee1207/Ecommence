/**
 * Created by zodiake on 2014/6/10.
 */
//123
$(function () {
    $('a').click(function (event) {
        var src = $(this);
        var id = src.attr('data-id');
        $.ajax({
            method: 'put',
            url: '/sellerCenter/items/' + id,
            success: function (data) {
                if (data.type == 'success') {
                    src.text('编辑');
                    src.attr('href','/sellerCenter/fabricEdit?id='+id);
                }else{
                    alert(data.content);
                }
            }
        });
    });
});
