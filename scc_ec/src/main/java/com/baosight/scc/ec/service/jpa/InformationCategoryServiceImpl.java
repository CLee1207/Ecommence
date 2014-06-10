package com.baosight.scc.ec.service.jpa;

import com.baosight.scc.ec.model.InformationCategory;
import com.baosight.scc.ec.repository.InformationCategoryRepository;
import com.baosight.scc.ec.service.InformationCategoryService;
import com.baosight.scc.ec.utils.GuidUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by Charles on 2014/6/5.
 */
@Service
@Transactional
public class InformationCategoryServiceImpl implements InformationCategoryService{
    @Autowired
    private InformationCategoryRepository icr;
    @Override
    public InformationCategory save(InformationCategory informationCategory) {
        informationCategory.setId(GuidUtil.newGuid());
        return icr.save(informationCategory);
    }

    @Override
    public InformationCategory update(InformationCategory informationCategory,String id) {
        InformationCategory i = icr.findOne(id);
        i.setCategoryName(informationCategory.getCategoryName());
        i.setIsValid(0);
        return i;
    }

    @Override
    @Transactional(readOnly = true)
    public InformationCategory findById(String id) {
        return icr.findOne(id);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<InformationCategory> findByIsValid(int isValid, Pageable pageable) {
        return icr.findByIsValid(isValid, pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public List<InformationCategory> findByIsValid(int isValid) {
        return icr.findByIsValid(isValid);
    }

    @Override
    public InformationCategory delete(String id) {
        InformationCategory i = icr.findOne(id);
        i.setIsValid(1);
        return i;
    }
}
