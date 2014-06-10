package com.baosight.scc.ec.security;

import com.baosight.scc.ec.model.Address;
import com.baosight.scc.ec.model.EcUser;
import com.baosight.scc.ec.utils.UserAuthorityUtils;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.util.Collection;

/**
 * Created by zodiake on 2014/5/12.
 */
@Component
public class EcUserDetailsService implements UserDetailsService,Serializable{
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        EcUser u=new EcUser();
        u.setId("1");
        u.setName("tom");
        u.setPassword("1");
        u.setType("O");
        Address address=new Address();
        address.setId("1");
        address.setCity("上海");
        address.setState("上海");
        address.setStreet("浦东新区浦电路370号");
        address.setZipCode("203323");
        address.setZipPhone("021");
        address.setPhone("23839434");
        address.setChildPhone("2343");
        address.setReceiverName("Charles");
        address.setMobile("1862229323");
        u.setDefaultAddress(address);
        return new EcUserDetails(u);
    }

    private final class EcUserDetails extends EcUser implements UserDetails,Serializable{
        EcUserDetails(EcUser user){
            setId(user.getId());
            setName(user.getName());
            setDefaultAddress(user.getDefaultAddress());
        }

        public Collection<? extends GrantedAuthority> getAuthorities() {
            return UserAuthorityUtils.USER_ROLES;
        }

        @Override
        public String getPassword() {
            return "1";
        }

        public String getUsername() {
            return "tom";
        }

        public boolean isAccountNonExpired() {
            return true;
        }

        public boolean isAccountNonLocked() {
            return true;
        }

        public boolean isCredentialsNonExpired() {
            return true;
        }

        public boolean isEnabled() {
            return true;
        }
    }
}
