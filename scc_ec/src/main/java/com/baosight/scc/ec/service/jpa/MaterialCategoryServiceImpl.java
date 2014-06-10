package com.baosight.scc.ec.service.jpa;

import com.baosight.scc.ec.model.EcUser;
import com.baosight.scc.ec.model.MaterialCategory;
import com.baosight.scc.ec.repository.MaterialCategoryRepository;
import com.baosight.scc.ec.security.UserContext;
import com.baosight.scc.ec.service.MaterialCategoryService;
import com.baosight.scc.ec.utils.GuidUtil;
import com.baosight.scc.ec.web.MaterialCategoryJSON;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

@Service
@Transactional
public class MaterialCategoryServiceImpl implements MaterialCategoryService {

    @Autowired
    private MaterialCategoryRepository mr;
    @PersistenceContext
    private EntityManager em;

    /**
     * @param category firstCategory
     * @return
     */
    @Transactional(readOnly = true)
    public List<MaterialCategoryJSON> findByParentCategory(MaterialCategory category) {
        TypedQuery<MaterialCategory> query=em.createNamedQuery("MaterialCategory.findByParentCategory", MaterialCategory.class);
        query.setParameter("category",category);
        List<MaterialCategory> categories=query.getResultList();
        List<MaterialCategoryJSON> results=new ArrayList<MaterialCategoryJSON>();
        for(MaterialCategory mc:categories){
            results.add(new MaterialCategoryJSON(mc));
        }
        return results;
    }

//    @Override
//    @Transactional(readOnly = true)
//    public List<MaterialCategory> findAllSecondCategory() {
//        return em.createNamedQuery("MaterialCategory.findAllSecondCategories", MaterialCategory.class).getResultList();
//    }

    @Override
    @Transactional(readOnly = true)
    public MaterialCategory findOne(String id) {
        return mr.findOne(id);
    }

    /**
     * 获取辅料一级分类
     *
     * @return
     */
    @Transactional(readOnly = true)
    public List<MaterialCategory> getMaterialFirstCategorys() {

        return em.createNamedQuery("MaterialCategory.findAllFirstCategorys", MaterialCategory.class).getResultList();
    }

    /**
     * *********************************************************************************************************************************
     */
    @Autowired
    private UserContext userContext;

    public MaterialCategory findById(String id) {
        return mr.findOne(id);
    }

    @Override
    @Transactional(readOnly = true)
    public List<MaterialCategory> findAllSecondCategory() {
        return mr.findByParentCategoryIsNotNull();
    }

    @Override
    @Transactional(readOnly = true)
    public List<MaterialCategory> findAllFirstCategory(){
        return mr.findByParentCategoryIsNull();
    }

    @Override
    public MaterialCategory save(MaterialCategory materialCategory) {
        materialCategory.setId(GuidUtil.newGuid());
        EcUser createdBy =  userContext.getCurrentUser();
        materialCategory.setCreatedBy(createdBy);
        return mr.save(materialCategory);
    }

    @Override
    public MaterialCategory update(MaterialCategory materialCategory) {
        EcUser updatedBy =  userContext.getCurrentUser();
        DateTime dt = new DateTime();
        MaterialCategory m = mr.findOne(materialCategory.getId());
        List<MaterialCategory> secondCategoryList = m.getSecondCategory();
        if(secondCategoryList != null && materialCategory.getIsValid() == 1){
            for (int i = 0; i< secondCategoryList.size(); i++){
                MaterialCategory secondCategory = secondCategoryList.get(i);
                secondCategory.setUpdatedBy(updatedBy);
                secondCategory.setUpdatedTime(dt.toCalendar(Locale.SIMPLIFIED_CHINESE));
                secondCategory.setIsValid(1);
            }
            m.setSecondCategory(secondCategoryList);
            m.setIsValid(1);
        }
        m.setName(materialCategory.getName());
        m.setUpdatedBy(updatedBy);
        m.setUpdatedTime(dt.toCalendar(Locale.SIMPLIFIED_CHINESE));
        m.setParentCategory(materialCategory.getParentCategory());
        return m;
    }

    @Override
    @Transactional(readOnly = true)
    public Page<MaterialCategory> findAllFirstCategoryByPage(Pageable pageable) {
        return mr.findByParentCategoryIsNull(pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<MaterialCategory> findAllSecondCategoryByPage(Pageable pageable) {
        return mr.findByParentCategoryIsNotNull(pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public List<MaterialCategory> findFirstCategoryByIsValid(int isValid) {
        return mr.findByIsValidAndParentCategoryIsNull(isValid);
    }

    @Override
    @Transactional(readOnly = true)
    public List<MaterialCategory> findSecondCategoryByIsValid(int isValid) {
        return mr.findByIsValidAndParentCategoryIsNotNull(isValid);
    }
}
