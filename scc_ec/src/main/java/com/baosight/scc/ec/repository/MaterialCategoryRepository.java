package com.baosight.scc.ec.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.baosight.scc.ec.model.MaterialCategory;

import java.util.List;

public interface MaterialCategoryRepository extends PagingAndSortingRepository <MaterialCategory,String> {

	/*
	//查询辅料二级分类列表
	List<MaterialCategory> findByParentCategory(MaterialCategory pid);*/
	
	//根据id，查询辅料分类信息
//	MaterialCategory findOne(String id);

    List<MaterialCategory> findByParentCategory(MaterialCategory category);
	/*
	//查询辅料一级分类列表
	List<MaterialCategory> findAll();*/

    /**
     * 查询分页的一级分类   Charles 2014/6/9
     * @param pageable
     * @return
     */
    Page<MaterialCategory> findByParentCategoryIsNull(Pageable pageable);

    /**
     * 查询分页二级分类 Charles 2014/6/9
     * @param pageable
     * @return
     */
    Page<MaterialCategory> findByParentCategoryIsNotNull(Pageable pageable);

    /**
     * 查询所有一级分类   Charles 2014/6/9
     * @return
     */
    List<MaterialCategory> findByParentCategoryIsNull();

    /**
     * 查询所有二级分类 Charles 2014/6/9
     * @return
     */
    List<MaterialCategory> findByParentCategoryIsNotNull();

    /**
     * 查询有效的一级分类   Charles 2014/6/9
     * @param isValid
     * @return
     */
    List<MaterialCategory> findByIsValidAndParentCategoryIsNull(int isValid);

    /**
     * 查询有效的二级分类 Charles 2014/6/9
     * @param isValid
     * @return
     */
    List<MaterialCategory> findByIsValidAndParentCategoryIsNotNull(int isValid);
}
