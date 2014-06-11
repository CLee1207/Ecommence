package com.baosight.scc.ec.controller;

import com.baosight.scc.ec.model.EcUser;
import com.baosight.scc.ec.model.Fabric;
import com.baosight.scc.ec.model.Item;
import com.baosight.scc.ec.model.Message;
import com.baosight.scc.ec.security.UserContext;
import com.baosight.scc.ec.service.ItemService;
import com.baosight.scc.ec.service.OrderLineService;
import com.baosight.scc.ec.type.ItemState;
import com.baosight.scc.ec.web.EcGrid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Locale;

/**
 * Created by zodiake on 2014/6/9.
 */
@Controller
public class ItemController extends AbstractController {
    @Autowired
    private UserContext userContext;
    @Autowired
    private ItemService itemService;
    @Autowired
    private OrderLineService orderLineService;
    @Autowired
    private MessageSource messageSource;

    private final static String FABRIC_LIST = "fabric_list";

    /*
        获取当前登入用户所发布过的所有面料信息
        @param:page 分页，希望获取第几页
        @param:uiModel spring封装对象，在页面中获取传递的对象
     */
    @RequestMapping(value = "/sellerCenter/items", method = RequestMethod.GET)
    public String listGrid(@RequestParam(value = "page", required = false, defaultValue = "1") Integer page,
                           @RequestParam(value = "size", required = false, defaultValue = "15") Integer size,
                           @RequestParam(value = "type", required = false, defaultValue = "4") Integer type,
                           Model uiModel) {
        Sort sort = new Sort(Sort.Direction.DESC, "createdTime");
        Pageable pageable = createPageRequest(page, size, sort);
        EcUser user = userContext.getCurrentUser();
        ItemState state;
        if (type >= 0 && type <= 4)
            state = ItemState.values()[type];
        else
            state = ItemState.全部;
        EcGrid<Item> grid;
        if (state == ItemState.全部)
            grid = createGrid(itemService.findByCreatedBy(user, pageable));
        else
            grid = createGrid(itemService.findByCreatedByAndState(user, state, pageable));
        Page<Item> itemPage = itemService.findByCreatedBy(user, pageable);
        for (Item i : grid.getEcList()) {
            if (i instanceof Fabric)
                i.setUrl("fabric");
            else
                i.setUrl("material");
        }
        //todo 新品展示需要同搜索引擎结合
        uiModel.addAttribute("grid", grid);
        uiModel.addAttribute("type", type);
        return FABRIC_LIST;
    }

    @RequestMapping(value = "/sellerCenter/items/{id}", method = RequestMethod.PUT,produces = "application/json")
    @ResponseBody
    public Message updateState(@PathVariable("id") String id, Locale locale) {
        Item item = new Item();
        item.setId(id);
        if (orderLineService.countByStateFinishAndItem(item)) {
            return new Message("fail", messageSource.getMessage("item_down_fail", new Object[]{}, locale));
        }
        item.setState(ItemState.下架);
        itemService.updateState(item);
        return new Message("success", messageSource.getMessage("save_success", new Object[]{}, locale));
    }
}
