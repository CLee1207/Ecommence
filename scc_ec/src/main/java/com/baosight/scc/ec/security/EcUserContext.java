package com.baosight.scc.ec.security;

import com.baosight.scc.ec.model.EcUser;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

/**
 * Created by zodiake on 2014/5/12.
 */
@Component
public class EcUserContext implements UserContext{
    public EcUser getCurrentUser() {
        SecurityContext context=SecurityContextHolder.getContext();
        Authentication authentication=context.getAuthentication();
        if(authentication.getPrincipal()==null)
            System.out.print(111);
        UserDetails details=(UserDetails)authentication.getPrincipal();
        EcUser user=(EcUser)authentication.getPrincipal();
        user.setAuthorities(details.getAuthorities());
        return user;
    }
}
