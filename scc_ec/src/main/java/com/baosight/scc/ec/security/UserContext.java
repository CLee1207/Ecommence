package com.baosight.scc.ec.security;

import com.baosight.scc.ec.model.EcUser;

/**
 * Created by zodiake on 2014/5/12.
 */
public interface UserContext {
    public EcUser getCurrentUser();
}
