package com.baosight.scc.ec.service.jpa;

import com.baosight.scc.ec.model.EcUser;
import com.baosight.scc.ec.model.FabricSource;
import com.baosight.scc.ec.repository.FabricSourceRepository;
import com.baosight.scc.ec.security.UserContext;
import com.baosight.scc.ec.service.FabricSourceService;
import com.baosight.scc.ec.utils.GuidUtil;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;
import java.util.Locale;

/**
 * Created by zodiake on 2014/5/22.
 */
@Service
@Transactional
public class FabricSourceServiceImpl implements FabricSourceService {
    @Autowired
    private FabricSourceRepository repository;
    @Autowired
    private UserContext userContext;
    @PersistenceContext
    private EntityManager em;

    @Override
    @Transactional(readOnly = true)
    public FabricSource findById(String id) {
        return repository.findOne(id);
    }

    @Override
    @Transactional(readOnly = true)
    public List<FabricSource> findAllFirstCategory() {
//        return em.createNamedQuery("FabricSource.findAllFirstSource",FabricSource.class).getResultList();
        return repository.findByParentIsNull();
    }

    @Override
    @Transactional(readOnly = true)
    public List<FabricSource> findAllSecondCategory() {
//        return em.createNamedQuery("FabricSource.findAllSecondSource",FabricSource.class).getResultList();
        return repository.findByParentIsNotNull();
    }

    @Override
    public FabricSource save(FabricSource fabricSource) {
        fabricSource.setId(GuidUtil.newGuid());
        EcUser createdBy =  userContext.getCurrentUser();
        fabricSource.setCreatedBy(createdBy);
        return repository.save(fabricSource);
    }

    @Override
    public FabricSource update(FabricSource fabricSource) {
        EcUser updatedBy =  userContext.getCurrentUser();
        DateTime dt = new DateTime();
        FabricSource f = repository.findOne(fabricSource.getId());
        List<FabricSource> secondCategoryList = f.getDetailSources();
        if(secondCategoryList != null && fabricSource.getIsValid() == 1){
            for (int i = 0; i< secondCategoryList.size(); i++){
                FabricSource secondCategory = secondCategoryList.get(i);
                secondCategory.setUpdatedBy(updatedBy);
                secondCategory.setUpdatedTime(dt.toCalendar(Locale.SIMPLIFIED_CHINESE));
                secondCategory.setIsValid(1);
            }
            f.setDetailSources(secondCategoryList);
            f.setIsValid(1);
        }
        f.setName(fabricSource.getName());
        f.setUpdatedBy(updatedBy);
        f.setUpdatedTime(dt.toCalendar(Locale.SIMPLIFIED_CHINESE));
        f.setDetailSources(fabricSource.getDetailSources());
        return f;
    }

    @Override
    @Transactional(readOnly = true)
    public Page<FabricSource> findAllFirstCategoryByPage(Pageable pageable) {
        return repository.findByParentIsNull(pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<FabricSource> findAllSecondCategoryByPage(Pageable pageable) {
        return repository.findByParentIsNotNull(pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public List<FabricSource> findFirstCategoryByIsValid(int isValid) {
        return repository.findByIsValidAndParentIsNull(isValid);
    }

    @Override
    @Transactional(readOnly = true)
    public List<FabricSource> findSecondCategoryByIsValid(int isValid) {
        return repository.findByIsValidAndParentIsNotNull(isValid);
    }

    @Override
    public List<FabricSource> findByParent(FabricSource fabricSource) {
        return repository.findByParent(fabricSource);
    }

    @Override
    public List<FabricSource> findByParentAndIsValid(FabricSource fabricSource, int isValid) {
        return repository.findByParentAndIsValid(fabricSource, isValid);
    }
}
