package com.baosight.scc.ec.service.jpa;

import com.baosight.scc.ec.model.EcUser;
import com.baosight.scc.ec.model.FabricCategory;
import com.baosight.scc.ec.repository.FabricCategoryRepository;
import com.baosight.scc.ec.security.UserContext;
import com.baosight.scc.ec.service.FabricCategoryService;

import com.baosight.scc.ec.utils.GuidUtil;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import java.util.List;
import java.util.Locale;

/**
 * Created by zodiake on 2014/5/13.
 */
@Service
@Transactional
public class FabricCategoryServiceImpl implements FabricCategoryService{
    @Autowired
    private UserContext userContext;
    @Autowired
    private FabricCategoryRepository repository;
    @PersistenceContext
    private EntityManager em;

    @Override
    @Transactional(readOnly = true)
    public FabricCategory findById(String id) {
        return repository.findOne(id);
    }

    @Override
    @Transactional(readOnly = true)
    public List<FabricCategory> findAllSecondCategory() {
//        return em.createNamedQuery("FabricCategory.findAllSecondCategory",FabricCategory.class).getResultList();
        return repository.findByParentCategoryIsNotNull();
    }

    @Override
    @Transactional(readOnly = true)
    public List<FabricCategory> findAllFirstCategory(){
//    	return em.createNamedQuery("FabricCategory.findAllFirstCategory",FabricCategory.class).getResultList();
        return repository.findByParentCategoryIsNull();
    }

    @Override
    public FabricCategory save(FabricCategory fabricCategory) {
        fabricCategory.setId(GuidUtil.newGuid());
        EcUser createdBy =  userContext.getCurrentUser();
        fabricCategory.setCreatedBy(createdBy);
        return repository.save(fabricCategory);
    }

    @Override
    public FabricCategory update(FabricCategory fabricCategory) {
        EcUser updatedBy =  userContext.getCurrentUser();
        DateTime dt = new DateTime();
        FabricCategory f = repository.findOne(fabricCategory.getId());
        List<FabricCategory> secondCategoryList = f.getSecondCategory();
        if(secondCategoryList != null && fabricCategory.getIsValid() == 1){
            for (int i = 0; i< secondCategoryList.size(); i++){
                FabricCategory secondCategory = secondCategoryList.get(i);
                secondCategory.setUpdatedBy(updatedBy);
                secondCategory.setUpdatedTime(dt.toCalendar(Locale.SIMPLIFIED_CHINESE));
                secondCategory.setIsValid(1);
            }
            f.setSecondCategory(secondCategoryList);
            f.setIsValid(1);
        }
        f.setName(fabricCategory.getName());
        f.setUpdatedBy(updatedBy);
        f.setUpdatedTime(dt.toCalendar(Locale.SIMPLIFIED_CHINESE));
        f.setParentCategory(fabricCategory.getParentCategory());
        return f;
    }

    @Override
    @Transactional(readOnly = true)
    public Page<FabricCategory> findAllFirstCategoryByPage(Pageable pageable) {
        return repository.findByParentCategoryIsNull(pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<FabricCategory> findAllSecondCategoryByPage(Pageable pageable) {
        return repository.findByParentCategoryIsNotNull(pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public List<FabricCategory> findFirstCategoryByIsValid(int isValid) {
        return repository.findByIsValidAndParentCategoryIsNull(isValid);
    }

    @Override
    @Transactional(readOnly = true)
    public List<FabricCategory> findSecondCategoryByIsValid(int isValid) {
        return repository.findByIsValidAndParentCategoryIsNotNull(isValid);
    }
}
