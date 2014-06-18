/**
 * Created by Charles on 2014/6/4.
 */
$(function(){
    /**
     * 保存广告栏位
     * return id
     */
    $(".saveAdp").click(function(){
        var id =$("#id").val();
        var positionNo = $("#positionNo").val();
        var name = $("#name").val();
        var description = $("#description").val();
        if(positionNo == null || positionNo == "" || name == null || name == ""){
            console.log("positionNo and name should be input at the same time!");
            return false;
        }else{
            $.ajax({
                type: "POST",
                url: "/admin/advertisementPosition?add",
                data:{
                    id:id,
                    positionNo:positionNo,
                    name:name,
                    description:description
                },
                success:function(data){
                    $("#id").val(data.id);
                    console.log(data);
                    window.location.href="/admin/advertisementPosition";
                }
            });
        }
    });
    /**
     * 查看广告栏位，提供修改的数据
     */
    $(".viewAdp").click(function(){
        var id =$(this).attr("data-id");
        if(id == null || id == ""){
            console.log("doesn't find id in this page!");
            return false;
        }else{
            $.ajax({
                type: "POST",
                url: "/admin/advertisementPosition?view",
                data:{
                    id:id
                },
                success:function(data){
                    if(data.result=="success"){
                        $("#id").val(id);
                        $("#positionNo").val(data.positionNo);
                        $("#name").val(data.name);
                        $("#description").val(data.description);
                    }else if(data.result=="error"){
                        console.log("can't find data!");
                        return false;
                    }
                }
            });
        }
    });

    /**
     * 删除广告栏位
     */
    $(".deleteAdp").click(function(){
        var id =$(this).attr("data-id");
        if(id == null || id == ""){
            console.log("doesn't find id in this page!");
            return false;
        }else{
            $.ajax({
                type: "POST",
                url: "/admin/advertisementPosition?delete",
                data:{
                    id:id
                },
                success:function(data){
                    if(data.result=="success"){
                        $(".tr-"+id).remove();
                        console.log("delete ok!");
                    }
                }
            });
        }
    });

    /**
     * 取消按钮，清空输入框内容
     */
    $(".cancelAdp").click(function(){
        $("#id").val("");
        $("#positionNo").val("");
        $("#name").val("");
        $("#description").val("");
    });

    /************************************************广告信息设置************************************
     * 查看广告信息
     */
    $(".viewAd").click(function(){
        var id =$(this).attr("data-id");
        if(id == null || id == ""){
            console.log("doesn't find id in this page!");
            return false;
        }else{
            window.location.href =  "/admin/advertisement/"+id;
        }
    });
    /**
     * 编辑广告
     */
    $(".editAd").click(function(){
        var id =$(this).attr("data-id");
        if(id == null || id == ""){
            console.log("doesn't find id in this page!");
            return false;
        }else{
            window.location.href =  "/admin/advertisement/edit/"+id;
        }
    });

    /**
     * 删除广告
     */
    $(".deleteAd").click(function(){
        var id =$(this).attr("data-id");
        if(id == null || id == ""){
            console.log("doesn't find id in this page!");
            return false;
        }else{
            $.ajax({
                type: "POST",
                url: "/admin/advertisement?delete",
                data:{
                    id:id
                },
                success:function(data){
                    if(data.result=="success"){
                        $(".tr-"+id).remove();
                        console.log("delete ok!");
                    }
                }
            });
        }
    });

    /***************************************************资讯分类设置*************************************************
     * 资讯分类保存
     * return id
     */
    $(".saveInfoCate").click(function(){
        var id =$("#id").val();
        var categoryName = $("#categoryName").val();
        if(categoryName == null || categoryName == ""){
            console.log("name should be input at the same time!");
            return false;
        }else{
            $.ajax({
                type: "POST",
                url: "/admin/informationCategory?add",
                data:{
                    id:id,
                    categoryName:categoryName
                },
                success:function(data){
                    $("#id").val(data.id);
                    console.log(data);
                    window.location.href="/admin/informationCategory";
                }
            });
        }
    });
    /**
     * 查看资讯分类，提供修改的数据
     */
    $(".editInfoCate").click(function(){
        var id =$(this).attr("data-id");
        if(id == null || id == ""){
            console.log("doesn't find id in this page!");
            return false;
        }else{
            $.ajax({
                type: "POST",
                url: "/admin/informationCategory?view",
                data:{
                    id:id
                },
                success:function(data){
                    if(data.result=="success"){
                        $("#id").val(id);
                        $("#categoryName").val(data.categoryName);
                    }else if(data.result=="error"){
                        console.log("can't find data!");
                        return false;
                    }
                }
            });
        }
    });

    /**
     * 删除资讯分类
     */
    $(".deleteInfoCate").click(function(){
        var id =$(this).attr("data-id");
        if(id == null || id == ""){
            console.log("doesn't find id in this page!");
            return false;
        }else{
            $.ajax({
                type: "POST",
                url: "/admin/informationCategory?delete",
                data:{
                    id:id
                },
                success:function(data){
                    if(data.result=="success"){
                        $(".tr-"+id).remove();
                        console.log("delete ok!");
                    }
                }
            });
        }
    });

    /**
     * 取消按钮，清空输入框内容
     */
    $(".cancelInfoCate").click(function(){
        $("#id").val("");
        $("#categoryName").val("");
    });

    /************************************************资讯内容设置************************************
     * 查看资讯详情
     */
    $(".viewInfo").click(function(){
        var id =$(this).attr("data-id");
        if(id == null || id == ""){
            console.log("doesn't find id in this page!");
            return false;
        }else{
            window.location.href =  "/admin/information/"+id;
        }
    });
    /**
     * 编辑广告
     */
    $(".editInfo").click(function(){
        var id =$(this).attr("data-id");
        if(id == null || id == ""){
            console.log("doesn't find id in this page!");
            return false;
        }else{
            window.location.href =  "/admin/information/edit/"+id;
        }
    });

    /**
     * 删除广告
     */
    $(".deleteInfo").click(function(){
        var id =$(this).attr("data-id");
        if(id == null || id == ""){
            console.log("doesn't find id in this page!");
            return false;
        }else{
            $.ajax({
                type: "POST",
                url: "/admin/information?delete",
                data:{
                    id:id
                },
                success:function(data){
                    if(data.result=="success"){
                        $(".tr-"+id).remove();
                        console.log("delete ok!");
                    }
                }
            });
        }
    });

    $("#cancelBtn").click(function(){
        window.history.back();
    });
});
