package com.baosight.scc.ec.service;

import com.baosight.scc.ec.model.CompositeScore;
import com.baosight.scc.ec.model.EcUser;

/**
 * Created by sam on 2014/6/11.
 */
public interface CompositeScoreService {
    public CompositeScore findByUser(EcUser user);
}
