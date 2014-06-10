package com.baosight.scc.ec.service.jpa;

import com.baosight.scc.ec.model.DimensionRate;
import com.baosight.scc.ec.model.EcUser;
import com.baosight.scc.ec.repository.DimensionRateRepository;
import com.baosight.scc.ec.service.DimensionRateService;
import com.baosight.scc.ec.utils.GuidUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PrePersist;

/**
 * Created by sam on 2014/6/5.
 */
@Service
@Transactional
public class DimensionRateServiceImpl implements DimensionRateService {

    @Autowired
    private DimensionRateRepository dimensionRateRepository;

    @Override
    public DimensionRate findBySeller(EcUser user) {
        return this.dimensionRateRepository.findBySeller(user);
    }
}
