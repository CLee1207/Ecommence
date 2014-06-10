package com.baosight.scc.ec.controller;

import com.baosight.scc.ec.model.*;
import com.baosight.scc.ec.security.UserContext;
import com.baosight.scc.ec.service.*;
import com.baosight.scc.ec.type.SampleOrderState;
import com.baosight.scc.ec.utils.UrlUtil;
import com.baosight.scc.ec.web.EcGrid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.naming.Binding;
import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Locale;

/**
 * Created by zodiake on 2014/5/28.
 */
@Controller
public class SampleOrderController extends AbstractController {
    @Autowired
    private ItemService itemService;
    @Autowired
    private AddressService addressService;
    @Autowired
    private SampleOrderService sampleOrderService;
    @Autowired
    private UserContext userContext;
    @Autowired
    private MessageSource messageSource;

    //create form page
    private final String CREATE = "sampleOrder_create";
    private final String VIEW = "sampleOrder_view";
    private final String UPDATE = "sampleOrder_create";
    private final String REDIRECT_UPDATE = "redirect:/buyerCenter/sampleOrder/";
    private final String SELLER_VIEW_LIST = "sampleOrder_seller_list";
    private final String BUYER_VIEW_LIST = "sampleOrder_buyer_list";

    private final Logger logger = LoggerFactory.getLogger(SampleOrderController.class);

    /*
        get create form
     */
    @RequestMapping(value = "/buyerCenter/item/{id}/sampleOrder", params = "form", method = RequestMethod.GET)
    public String createFabricForm(Model uiModel, @PathVariable("id") String id) {
        Item item = itemService.findById(id);
        SampleOrder sampleOrder = new SampleOrder();
        sampleOrder.setItem(item);
        List<Address> addresses = addressService.findByUser(userContext.getCurrentUser());

        uiModel.addAttribute("sampleOrder", sampleOrder);
        uiModel.addAttribute("addresses", addresses);
        return CREATE;
    }

    /*
        process form post
     */
    @RequestMapping(value = "/buyerCenter/item/{id}/sampleOrder", params = "form", method = RequestMethod.POST)
    public String saveFabricForm(@ModelAttribute("sampleOrder") SampleOrder order, BindingResult result,
                                 @PathVariable("id") String id,
                                 Model uiModel, HttpServletRequest request,
                                 RedirectAttributes redirectAttributes, Locale locale) {
        if (result.hasErrors()) {
            uiModel.addAttribute("sampleOrder", order);
            return CREATE;
        }
        EcUser user = userContext.getCurrentUser();
        order.setCreator(user);
        Item item = itemService.findById(id);
        order.setItem(item);
        SampleOrder source = sampleOrderService.save(order);
        redirectAttributes.addFlashAttribute("success", new Message("success", messageSource.getMessage("save_success", new Object[]{}, locale)));
        return REDIRECT_UPDATE + UrlUtil.encodeUrlPathSegment(source.getId(), request) + "?edit";
    }

    /*
        get update form
     */
    @RequestMapping(value = "/buyerCenter/sampleOrder/{id}", params = "edit", method = RequestMethod.GET)
    public String viewForm(@PathVariable("id") String id, Model uiModel) {
        EcUser user = userContext.getCurrentUser();
        SampleOrder order = sampleOrderService.findByIdAndCreator(id, user);
        List<Address> addresses = addressService.findByUser(userContext.getCurrentUser());

        uiModel.addAttribute("sampleOrder", order);
        uiModel.addAttribute("addresses", addresses);
        return UPDATE;
    }

    @RequestMapping(value = "/buyerCenter/sampleOrder/{id}", params = "edit", method = RequestMethod.POST)
    public String updateForm(@PathVariable("id") String id,
                             @ModelAttribute("sampleOrder") SampleOrder order, BindingResult result,
                             Model uiModel, RedirectAttributes redirectAttributes, HttpServletRequest request, Locale locale) {
        if (result.hasErrors()) {
            uiModel.addAttribute("sampleOrder", order);
            return CREATE;
        }
        EcUser user = userContext.getCurrentUser();
        order.setCreator(user);
        Item item = itemService.findById(id);
        order.setItem(item);
        order.setId(id);
        SampleOrder source = sampleOrderService.update(order);
        redirectAttributes.addFlashAttribute("success", new Message("success", messageSource.getMessage("save_success", new Object[]{}, locale)));
        return REDIRECT_UPDATE + UrlUtil.encodeUrlPathSegment(source.getId(), request) + "?edit";
    }


    @RequestMapping(value = "/buyerCenter/sampleOrder/{id}", params = "form", method = RequestMethod.GET)
    public String view(@PathVariable("id") String id, Model uiModel) {
        EcUser user = userContext.getCurrentUser();
        SampleOrder sampleOrder = sampleOrderService.findByIdAndCreator(id, user);
        uiModel.addAttribute("sampleOrder", sampleOrder);
        return VIEW;
    }

    @RequestMapping(value = "/sellerCenter/sampleOrders", method = RequestMethod.GET)
    public String getAllReceivedOrders(Model uiModel, @RequestParam(value = "page", required = false, defaultValue = "1") Integer page,
                                       @RequestParam(value = "size", required = false, defaultValue = "15") Integer size,
                                       @RequestParam(value = "state", required = false, defaultValue = "4") int state) {
        SampleOrderState sampleOrderState;
        if (state >= 0 && state <= 4)
            sampleOrderState = SampleOrderState.values()[state];
        else
            sampleOrderState = SampleOrderState.全部;
        Pageable pageable = createPageRequest(page, size);
        EcUser user = userContext.getCurrentUser();
        EcGrid<SampleOrder> grid;
        if (state == 4)
            grid = createGrid(sampleOrderService.findByItemCreatedBy(user, pageable));
        else
            grid = createGrid(sampleOrderService.findByStateAndItemCreatedBy(sampleOrderState, user, pageable));
        uiModel.addAttribute("grid", grid);
        return SELLER_VIEW_LIST;
    }

    @RequestMapping(value = "/buyerCenter/sampleOrders", method = RequestMethod.GET)
    public String getAllSendOrders(Model uiModel, @RequestParam(value = "page", required = false, defaultValue = "1") Integer page,
                                   @RequestParam(value = "size", required = false, defaultValue = "15") Integer size,
                                   @RequestParam(value = "state", required = false, defaultValue = "4") int state) {
        SampleOrderState sampleOrderState;
        if (state >= 0 && state <= 4)
            sampleOrderState = SampleOrderState.values()[state];
        else
            sampleOrderState = SampleOrderState.全部;
        Pageable pageable = createPageRequest(page, size);
        EcUser user = userContext.getCurrentUser();
        EcGrid<SampleOrder> grid;
        if (state == 4)
            grid = createGrid(sampleOrderService.findByCreatedBy(user, pageable));
        else
            grid = createGrid(sampleOrderService.findByStateAndCreatedBy(user, sampleOrderState, pageable));
        uiModel.addAttribute("grid", grid);
        return BUYER_VIEW_LIST;
    }
}
