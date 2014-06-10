package com.baosight.scc.ec.controller;

import com.baosight.scc.ec.model.EcUser;
import com.baosight.scc.ec.model.Item;
import com.baosight.scc.ec.model.Message;
import com.baosight.scc.ec.model.SellerComment;
import com.baosight.scc.ec.security.UserContext;
import com.baosight.scc.ec.service.EcUserService;
import com.baosight.scc.ec.service.ItemService;
import com.baosight.scc.ec.service.SellerCommentService;
import com.baosight.scc.ec.utils.UrlUtil;
import com.baosight.scc.ec.web.EcGrid;
import com.google.common.collect.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.Locale;

/**
 * Created by sam on 2014/5/29.
 */
public class SellerCommentController {

    @Autowired
    private ItemService itemService;
    @Autowired
    private EcUserService ecUserService;
    @Autowired
    private SellerCommentService sellerCommentService;
    @Autowired
    private UserContext userContext;
    @Autowired
    private MessageSource messageSource;

    private final static String EDIT_SELLERCOMMENT = "sellerComment.edit";
    private final static String SENDLIST_SELLERCOMMENT = "sendComments"; //卖家发出的评论

    private final static String REDIRECT = "commentOk";


    @InitBinder
    public void initBinder(WebDataBinder binder) {
        binder.setDisallowedFields("id");
    }

    /*
    创建表单，让用户输入
     */
    @RequestMapping(value="/sellerCenter/sellerComment/{id}", params = "form", method = RequestMethod.GET)
    public String createForm(Model uiModel,@PathVariable("id") String id){
        SellerComment sellerComment = new SellerComment();
        Item item = this.itemService.findById(id);
        uiModel.addAttribute("item",item);
        uiModel.addAttribute("sellerComment",sellerComment);
        return EDIT_SELLERCOMMENT;
    }

    /**
     *处理用户新建的表单数据
     */
    @RequestMapping(value="/sellerCenter/sellerComment/{id}",params = "form",method = RequestMethod.POST)
    public String save(@Valid @ModelAttribute("sellerComment") SellerComment sellerComment,
                       RedirectAttributes redirectAttributes,Locale locale,
                       @PathVariable("id") String id,
                       HttpServletRequest request,BindingResult result,Model uiModel){
        if(result.hasErrors()){
            uiModel.addAttribute("sellerComment",sellerComment);
            return EDIT_SELLERCOMMENT;
        }
        sellerComment.setUser(userContext.getCurrentUser());

        SellerComment source = this.sellerCommentService.save(sellerComment);

        redirectAttributes.addFlashAttribute("message", new Message("success", messageSource.getMessage("save_success", new Object[]{}, locale)));
        return REDIRECT + UrlUtil.encodeUrlPathSegment(source.getId(), request) + "?edit";
    }

    /*
    用户发出的评价
     */
    @RequestMapping(value="/sellerCenter/sellerComments",method = RequestMethod.GET)
    public String sellerSendComments(Model uiModel,
                                     @RequestParam("type") Integer type,
                                     @RequestParam(value = "page",required = false,defaultValue = "1") Integer page,
                                     @RequestParam(value = "size",required = false,defaultValue = "15") Integer size,
                                     @RequestParam(value = "content",required = false) String content){
        PageRequest pageRequest = null;
        //定义排序字段
        Sort sort = new Sort(Sort.Direction.DESC, "createdTime");
        //spring 封装page
        if (page != null)
            pageRequest = new PageRequest(page - 1, size, sort);
        else
            pageRequest = new PageRequest(0, size, sort);

        //获取当前用户信息
        EcUser user = userContext.getCurrentUser();
        Page<SellerComment> sellerCommentPage = this.sellerCommentService.findSellerSendComments(user,type,content,pageRequest);
        EcGrid<SellerComment> grid = new EcGrid<SellerComment>();
        grid.setCurrentPage(sellerCommentPage.getNumber() + 1);
        grid.setEcList(Lists.newArrayList(sellerCommentPage));
        grid.setTotalPages(sellerCommentPage.getTotalPages());
        grid.setTotalRecords(sellerCommentPage.getTotalElements());
        uiModel.addAttribute("grid",grid);
        return SENDLIST_SELLERCOMMENT;
    }
}
