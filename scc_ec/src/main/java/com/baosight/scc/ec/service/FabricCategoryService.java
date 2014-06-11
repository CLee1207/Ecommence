package com.baosight.scc.ec.service;

import com.baosight.scc.ec.model.FabricCategory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * Created by zodiake on 2014/5/13.
 */
public interface FabricCategoryService {
    FabricCategory findById(String id);

    List<FabricCategory> findAllSecondCategory();

    /**
     * 根据一级分类查询二级分类
     *
     * @return
     * @author sam
     */
    List<FabricCategory> findAllFirstCategory();

    /**
     * 保存分类信息
     *
     * @param fabricCategory
     * @return
     */
    FabricCategory save(FabricCategory fabricCategory);

    /**
     * 更新分类信息
     *
     * @param fabricCategory
     * @return
     */
    FabricCategory update(FabricCategory fabricCategory);

    /**
     * 后台一级分类维护 Charles 2014/6/6
     *
     * @return
     */
    Page<FabricCategory> findAllFirstCategoryByPage(Pageable pageable);

    /**
     * 后台二级分类维护 Charles 2014/6/6
     *
     * @return
     */
    Page<FabricCategory> findAllSecondCategoryByPage(Pageable pageable);

    /**
     * 查询有效的一级分类 Charles 2014/6/6
     *
     * @return
     */
    List<FabricCategory> findFirstCategoryByIsValid(int isValid);

    /**
     * 查询有效的二级分类 Charles 2014/6/6
     *
     * @return
     */
    List<FabricCategory> findSecondCategoryByIsValid(int isValid);

    List<FabricCategory> findByParentCategoryAndIsValid(FabricCategory category,int isValid);

    /**
     * 根据一级分类查询二级分类，包含有效和无效的分类 Charles 2014/6/11
     * @param category
     * @return
     */
    List<FabricCategory> findByParentCategory(FabricCategory category);
}
