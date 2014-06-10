package com.baosight.scc.ec.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import com.baosight.scc.ec.model.*;
import com.baosight.scc.ec.service.*;
import com.baosight.scc.ec.type.CommentType;
import com.baosight.scc.ec.utils.UrlUtil;
import com.baosight.scc.ec.web.EcGrid;
import com.google.common.collect.Lists;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import com.baosight.scc.ec.security.UserContext;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

/**
 * 买家评价管理
 * @author sam
 *
 */
@Controller
public class CommentController {

	@Autowired
	private ItemService itemService;
    @Autowired
    private EcUserService ecUserService;
	@Autowired
    private UserContext userContext;
	@Autowired
	private CommentService commentService;
    @Autowired
    private MessageSource messageSource;

    private Comment comment;

    final Logger logger = LoggerFactory.getLogger(CommentController.class);
	
	private final static String COMMENT_BUYER_EDIT = "buyer_edit"; //买家评价编辑页面

    private final static  String REDIRECT = "commentOk"; //评价成功

    @InitBinder
    public void initBinder(WebDataBinder binder) {
        binder.setDisallowedFields("id");
    }
	
    /**
     * 创建评价form表单，让用户输入信息
     * @param uiModel
     * @param id 产品id
     * @return
     * @author sam
     */
    @RequestMapping(value="/buyerCenter/comment/{id}",params = "form",method = RequestMethod.GET)
    public String createForm(Model uiModel,@PathVariable("id") String id){
        //根据产品id，获取产品信息
        Item item = this.itemService.findById(id);
        //获取卖家信息
        EcUser user = this.ecUserService.findById(item.getCreatedBy().getId());
        //创建一个空对象，来接收页面数据
        Comment comment = new Comment();
        //设置数值，用于页面取数
        uiModel.addAttribute("item",item);
        uiModel.addAttribute("comment",comment);
        uiModel.addAttribute("user",user);
        return COMMENT_BUYER_EDIT;
    }

    /*
    处理买家新建评论form表单数据
     */
    @RequestMapping(value="/buyerCenter/comment/{id}",params = "form",method = RequestMethod.POST)
    public String save(@Valid @ModelAttribute("comment") Comment comment,
                       @PathVariable("id") String id,
                       RedirectAttributes redirectAttributes, Locale locale,
                       HttpServletRequest request, BindingResult result, Model uiModel){
        if(result.hasErrors()){
            uiModel.addAttribute("comment",comment);
            return COMMENT_BUYER_EDIT;
        }
        comment.setUser(userContext.getCurrentUser());

        Comment source = this.commentService.save(comment);

    //    redirectAttributes.addFlashAttribute("message", new Message("success", messageSource.getMessage("save_success", new Object[]{}, locale)));
     //   return REDIRECT + UrlUtil.encodeUrlPathSegment(source.getId(), request) + "?edit";
        return REDIRECT;
    }

    @RequestMapping(value = "test",method = RequestMethod.GET)
    public String test(){
        EcUser user = userContext.getCurrentUser();
        this.commentService.findCommentsDimensionRates(null);
        return REDIRECT;
    }
}
