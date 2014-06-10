package com.baosight.scc.ec.controller;

import com.baosight.scc.ec.model.DemandOrder;
import com.baosight.scc.ec.model.EcUser;
import com.baosight.scc.ec.model.Message;
import com.baosight.scc.ec.security.UserContext;
import com.baosight.scc.ec.service.DemandOrderService;
import com.baosight.scc.ec.type.DemandOrderState;
import com.baosight.scc.ec.utils.UrlUtil;
import com.baosight.scc.ec.web.EcGrid;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.Locale;

/**
 * Created by zodiake on 2014/5/22.
 */
@Controller
public class DemandOrderController extends AbstractController {
    @Autowired
    private DemandOrderService service;

    @Autowired
    private MessageSource messageSource;

    @Autowired
    private UserContext userContext;

    private Logger logger = LoggerFactory.getLogger(DemandOrderController.class);

    public final static String EDIT_DEMANDORDER = "demandOrder.edit";
    public final static String VIEW = "demandOrder.view";
    public final static String RELEASEDLIST = "demandOrder.releasedList";
    public final static String REDIRECT = "redirect:/buyerCenter/demandOrder/";
    public final static String FORBIDDEN = "redirect:/forbidden";

    /*
        获取当前用户提交的求购单列表
        @param:page current page
        @Param:size current page items number
        @param:uiModel
        @param:state demandOrder state
     */
    @RequestMapping(value = "/buyerCenter/demandOrders", method = RequestMethod.GET)
    public String viewDemandOrder(@RequestParam(value = "page", required = false, defaultValue = "1") Integer page,
                                  @RequestParam(value = "size", required = false, defaultValue = "15") Integer size,
                                  @RequestParam(value = "type", required = false, defaultValue = "5") int state,
                                  Model uiModel) {

        DemandOrderState demandOrderState;
        if (state >= 1 && state <= 5)
            demandOrderState = DemandOrderState.values()[state];
        else
            demandOrderState = DemandOrderState.全部;

        EcUser currentUser = userContext.getCurrentUser();
        Sort sort = new Sort(Sort.Direction.DESC, "createdTime");

        PageRequest pageRequest = createPageRequest(page, size, sort);
        EcGrid<DemandOrder> grids;
        if (state == 5)
            grids = createGrid(service.findByUser(currentUser, pageRequest));
        else
            grids = createGrid(service.findByUserAndState(currentUser, demandOrderState, pageRequest));

        if (logger.isDebugEnabled())
            logger.debug(grids.getEcList().size() + "");
        uiModel.addAttribute("grids", grids);
        return RELEASEDLIST;
    }

    /*
        查看demandOrder
        @param id demandOrder pk
        @param uiModel
     */
    @RequestMapping(value = "/demandOrder/{id}", method = RequestMethod.GET)
    public String view(@PathVariable("id") String id, Model uiModel) {
        DemandOrder demandOrder = service.findOne(id);
        uiModel.addAttribute("demandOrder", demandOrder);
        return VIEW;
    }

    /*
        创建form让用户输入
        @param uiModel
     */
    @RequestMapping(value = "/buyerCenter/demandOrder", params = "form", method = RequestMethod.GET)
    public String createForm(Model uiModel) {
        DemandOrder demandOrder = new DemandOrder();
        uiModel.addAttribute("demandOrder", demandOrder);
        return EDIT_DEMANDORDER;
    }

    /*
        处理用户新建的表单数据
        @param demandOrder submit form data
        @param redirectArrributes
        @param locale
        @param request
        @param uiModel
     */
    @RequestMapping(value = "/buyerCenter/demandOrder", params = "form", method = RequestMethod.POST)
    public String save(@Valid @ModelAttribute("demandOrder") DemandOrder demandOrder,BindingResult result,
                       RedirectAttributes redirectAttributes, Locale locale,
                       HttpServletRequest request,  Model uiModel) {
        if (result.hasErrors()) {
            uiModel.addAttribute("demandOrder", demandOrder);
            return EDIT_DEMANDORDER;
        }
        demandOrder.setCreatedBy(userContext.getCurrentUser());
        DemandOrder source = service.save(demandOrder);
        redirectAttributes.addFlashAttribute("message", new Message("success", messageSource.getMessage("save_success", new Object[]{}, locale)));
        return REDIRECT + UrlUtil.encodeUrlPathSegment(source.getId(), request) + "?edit";
    }

    /*
        获取用户需要更新的demandOrder
        将地址分装成 demanorderaddress
        @param id demandOrder pk
     */
    @RequestMapping(value = "/buyerCenter/demandOrder/{id}", params = "edit", method = RequestMethod.GET)
    public String getEditForm(@PathVariable("id") String id, Model uiModel) {
        EcUser user = userContext.getCurrentUser();
        DemandOrder demandOrder = service.findByIdAndUser(id, user);
        if (demandOrder != null) {
            uiModel.addAttribute("demandOrder", demandOrder);
            return EDIT_DEMANDORDER;
        } else {
            return FORBIDDEN;
        }
    }

    /*
        处理用户跟新数据
        @param id demandOrder pk
        @param uiModel
        @param request
        @param locale
        @param result
        @param redirectAttributes
     */
    @RequestMapping(value = "/buyerCenter/demandOrder/{id}", params = "edit", method = RequestMethod.POST)
    public String updateForm(@PathVariable("id") String id, Model uiModel, HttpServletRequest request, Locale locale,
                             @Valid @ModelAttribute("demandOrder") DemandOrder demandOrder,
                             BindingResult result, RedirectAttributes redirectAttributes) {
        if (result.hasErrors()) {
            uiModel.addAttribute("demandOrder", demandOrder);
            return EDIT_DEMANDORDER;
        }
        EcUser u = userContext.getCurrentUser();
        demandOrder.setId(id);
        demandOrder.setCreatedBy(u);

        DemandOrder order = service.update(demandOrder);
        redirectAttributes.addFlashAttribute("message", new Message("success", messageSource.getMessage("save_success", new Object[]{}, locale)));
        return REDIRECT + UrlUtil.encodeUrlPathSegment(order.getId(), request) + "?edit";
    }
}
