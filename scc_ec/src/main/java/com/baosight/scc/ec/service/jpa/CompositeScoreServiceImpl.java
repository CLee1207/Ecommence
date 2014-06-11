package com.baosight.scc.ec.service.jpa;

import com.baosight.scc.ec.model.CompositeScore;
import com.baosight.scc.ec.model.EcUser;
import com.baosight.scc.ec.repository.CompositeRepository;
import com.baosight.scc.ec.service.CompositeScoreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by sam on 2014/6/11.
 */
@Service
@Transactional
public class CompositeScoreServiceImpl implements CompositeScoreService{

    @Autowired
    private CompositeRepository compositeRepository;

    @Override
    @Transactional(readOnly = true)
    public CompositeScore findByUser(EcUser user) {
        return this.compositeRepository.findByUser(user);
    }
}
