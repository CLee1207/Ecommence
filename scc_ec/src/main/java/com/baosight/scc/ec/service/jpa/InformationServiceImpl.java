package com.baosight.scc.ec.service.jpa;

import com.baosight.scc.ec.model.Information;
import com.baosight.scc.ec.repository.InformationRepository;
import com.baosight.scc.ec.service.InformationService;
import com.baosight.scc.ec.utils.GuidUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by Charles on 2014/6/5.
 */
@Service
@Transactional
public class InformationServiceImpl implements InformationService{
    @Autowired
    private InformationRepository ir;
    @Override
    public Information save(Information information) {
        information.setId(GuidUtil.newGuid());
        return ir.save(information);
    }

    @Override
    public Information update(Information information, String id) {
        Information i = ir.findOne(id);
        i.setTitle(information.getTitle());
        i.setCoverPath(information.getCoverPath());
        i.setContent(information.getContent());
        i.setInformationCategory(information.getInformationCategory());
        i.setIsValid(0);
        return i;
    }

    @Override
    @Transactional(readOnly = true)
    public Information findById(String id) {
        return ir.findOne(id);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<Information> findByIsValid(int isValid, Pageable pageable) {
        return ir.findByIsValid(isValid, pageable);
    }

    @Override
    public Information delete(String id) {
        Information information = ir.findOne(id);
        information.setIsValid(1);
        return information;
    }
}
