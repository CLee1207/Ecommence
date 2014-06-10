package com.baosight.scc.ec.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * Created by zodiake on 2014/6/5.
 */
@Controller
public class MainController {
    @RequestMapping(value={"/","/index"},method = RequestMethod.GET)
    public String index(){
        return "index";
    }
}
