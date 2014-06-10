package com.baosight.scc.ec.web.listener;

import com.baosight.scc.ec.service.AdvertisementPositionService;
import org.springframework.context.ApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import java.util.Map;

/**
 * Created by zodiake on 2014/6/6.
 */
public class ContextLoadListener implements ServletContextListener {
    @Override
    public void contextInitialized(ServletContextEvent sce) {
        ServletContext context = sce.getServletContext();
        ApplicationContext ctx = WebApplicationContextUtils.getWebApplicationContext(context);
        //项目启动加载广告 added by Charles 2014-6-6 start...
        AdvertisementPositionService advertisementPositionService = (AdvertisementPositionService)ctx.getBean("advertisementPositionServiceImpl");
        Map adMap = advertisementPositionService.findAllAd();
        context.setAttribute("adMap",adMap);
        //项目启动加载广告 added by Charles 2014-6-6 ..end.
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {

    }
}
