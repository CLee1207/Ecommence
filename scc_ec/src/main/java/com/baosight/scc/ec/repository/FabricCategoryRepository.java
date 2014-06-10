package com.baosight.scc.ec.repository;

import com.baosight.scc.ec.model.FabricCategory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;

/**
 * Created by zodiake on 2014/5/13.
 */
public interface FabricCategoryRepository extends PagingAndSortingRepository<FabricCategory,String>{
    /**
     * 查询分页的一级分类   Charles 2014/6/6
     * @param pageable
     * @return
     */
    Page<FabricCategory> findByParentCategoryIsNull(Pageable pageable);

    /**
     * 查询分页二级分类 Charles 2014/6/6
     * @param pageable
     * @return
     */
    Page<FabricCategory> findByParentCategoryIsNotNull(Pageable pageable);

    /**
     * 查询所有一级分类   Charles 2014/6/6
     * @return
     */
    List<FabricCategory> findByParentCategoryIsNull();

    /**
     * 查询所有二级分类 Charles 2014/6/6
     * @return
     */
    List<FabricCategory> findByParentCategoryIsNotNull();

    /**
     * 查询有效的一级分类   Charles 2014/6/6
     * @param isValid
     * @return
     */
    List<FabricCategory> findByIsValidAndParentCategoryIsNull(int isValid);

    /**
     * 查询有效的二级分类 Charles 2014/6/6
     * @param isValid
     * @return
     */
    List<FabricCategory> findByIsValidAndParentCategoryIsNotNull(int isValid);
}
