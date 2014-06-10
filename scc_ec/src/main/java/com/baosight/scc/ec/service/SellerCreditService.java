package com.baosight.scc.ec.service;

import com.baosight.scc.ec.model.EcUser;
import com.baosight.scc.ec.model.SellerCredit;

import java.util.List;

/**
 * Created by sam on 2014/6/6.
 */
public interface SellerCreditService {

    List<SellerCredit> findByUser(EcUser user);

    /*
    统计评价类型：好、中、差评在各个时间的次数
     */
    SellerCredit findTypeTotal(EcUser user);
}
