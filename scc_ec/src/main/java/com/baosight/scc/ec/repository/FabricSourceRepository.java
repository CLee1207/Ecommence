package com.baosight.scc.ec.repository;

import com.baosight.scc.ec.model.FabricSource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

/**
 * Created by zodiake on 2014/5/19.
 */
public interface FabricSourceRepository extends CrudRepository<FabricSource,String>{
    /**
     * 查询分页的一级分类   Charles 2014/6/6
     * @param pageable
     * @return
     */
    Page<FabricSource> findByParentIsNull(Pageable pageable);

    /**
     * 查询分页二级分类 Charles 2014/6/6
     * @param pageable
     * @return
     */
    Page<FabricSource> findByParentIsNotNull(Pageable pageable);

    /**
     * 查询所有一级分类   Charles 2014/6/6
     * @return
     */
    List<FabricSource> findByParentIsNull();

    /**
     * 查询所有二级分类 Charles 2014/6/6
     * @return
     */
    List<FabricSource> findByParentIsNotNull();

    /**
     * 查询有效的一级分类   Charles 2014/6/6
     * @param isValid
     * @return
     */
    List<FabricSource> findByIsValidAndParentIsNull(int isValid);

    /**
     * 查询有效的二级分类 Charles 2014/6/6
     * @param isValid
     * @return
     */
    List<FabricSource> findByIsValidAndParentIsNotNull(int isValid);

    /**
     * 根据一级分类查询二级分类 Charles 2014/6/11
     * @param fabricSource
     * @return
     */
    List<FabricSource> findByParent(FabricSource fabricSource);

    /**
     * 根据一级分类查询有效的二级分类 Charles 2014/6/11
     * @param fabricSource
     * @param isValid
     * @return
     */
    List<FabricSource> findByParentAndIsValid(FabricSource fabricSource,int isValid);
}
