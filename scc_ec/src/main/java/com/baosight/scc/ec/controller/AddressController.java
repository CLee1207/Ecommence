package com.baosight.scc.ec.controller;

import com.baosight.scc.ec.model.Address;
import com.baosight.scc.ec.model.EcUser;
import com.baosight.scc.ec.model.Message;
import com.baosight.scc.ec.security.UserContext;
import com.baosight.scc.ec.service.AddressService;
import com.baosight.scc.ec.service.EcUserService;
import com.baosight.scc.ec.web.AddressJSON;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/**
 * Created by zodiake on 2014/5/22.
 */
@Controller
@RequestMapping("/buyerCenter")
public class AddressController extends AbstractController{
    @Autowired
    private AddressService service;
    @Autowired
    private EcUserService userService;
    @Autowired
    private UserContext userContext;
    @Autowired
    private MessageSource messageSource;

    public static final String CREATE="address_create";

    @RequestMapping(value="/address",params = "form",method = RequestMethod.GET)
    public String create(Model uiModel){
        EcUser currentUser=userContext.getCurrentUser();
        Address address=new Address();
        List<Address> addresses= service.findByUser(currentUser);
        uiModel.addAttribute("addresses",addresses);
        uiModel.addAttribute("address",address);
        return CREATE;
    }

    @RequestMapping(value="/address",method = RequestMethod.GET,produces = "application/json")
    @ResponseBody
    public List<AddressJSON> getList(){
        EcUser user=userContext.getCurrentUser();
        List<Address> addresses= service.findByUser(user);
        List<AddressJSON> list=new ArrayList<AddressJSON>();
        for(Address a:addresses){
            list.add(new AddressJSON(a));
        }
        return list;
    }

    @RequestMapping(value="/address",params = "form",method = RequestMethod.POST,produces = "application/json")
    @ResponseBody
    public String save(Model uiModel,@Valid @ModelAttribute("address")Address address,BindingResult result,Locale locale){
        if(result.hasErrors()){
            uiModel.addAttribute("message", new Message("error", messageSource.getMessage("fabric.required", new Object[]{}, locale)));
            uiModel.addAttribute("address", address);
            return "contacts/create";
        }
        else {
            EcUser user=userContext.getCurrentUser();
            address.setUser(user);
            service.save(address);
            return "success";
        }
    }

    @RequestMapping(value="/defaultAddress",method = RequestMethod.POST,produces = "application/json")
    @ResponseBody
    public String setDefaultAddress(@RequestParam("addressId")String addressId){
        EcUser user=userContext.getCurrentUser();
        Address address=service.findById(addressId);
        user.setDefaultAddress(address);
        EcUser u=userService.save(user);
        return "success";
    }

}
