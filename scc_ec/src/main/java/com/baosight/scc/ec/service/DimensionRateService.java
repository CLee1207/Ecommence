package com.baosight.scc.ec.service;

import com.baosight.scc.ec.model.DimensionRate;
import com.baosight.scc.ec.model.EcUser;

/**
 * Created by sam on 2014/6/5.
 */
public interface DimensionRateService {

    DimensionRate findBySeller(EcUser user);
}
