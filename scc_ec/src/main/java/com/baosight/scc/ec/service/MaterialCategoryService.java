package com.baosight.scc.ec.service;

import com.baosight.scc.ec.model.MaterialCategory;
import com.baosight.scc.ec.web.MaterialCategoryJSON;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * @author sam
 */
public interface MaterialCategoryService {

    /**
     * 根据一级id，查询该id下的所有二级分类
     *
     * @param category material first Category
     * @return
     */
    List<MaterialCategoryJSON> findByParentCategory(MaterialCategory category);

    /**
     * 获取辅料一级分类
     *
     * @return
     */
    List<MaterialCategory> getMaterialFirstCategorys();

    List<MaterialCategory> findAllSecondCategory();

    MaterialCategory findOne(String id);


    MaterialCategory findById(String id);

    /**
     * 根据一级分类查询二级分类
     * @return
     */
    List<MaterialCategory> findAllFirstCategory();

    /**
     * 保存分类信息
     * @param materialCategory
     * @return
     */
    MaterialCategory save(MaterialCategory materialCategory);

    /**
     * 更新分类信息
     * @param materialCategory
     * @return
     */
    MaterialCategory update(MaterialCategory materialCategory);

    /**
     * 后台一级分类维护 Charles 2014/6/9
     * @return
     */
    Page<MaterialCategory> findAllFirstCategoryByPage(Pageable pageable);

    /**
     * 后台二级分类维护 Charles 2014/6/9
     * @return
     */
    Page<MaterialCategory> findAllSecondCategoryByPage(Pageable pageable);

    /**
     * 查询有效的一级分类 Charles 2014/6/9
     * @return
     */
    List<MaterialCategory> findFirstCategoryByIsValid(int isValid);

    /**
     * 查询有效的二级分类 Charles 2014/6/9
     * @return
     */
    List<MaterialCategory> findSecondCategoryByIsValid(int isValid);

    /**
     * 根据一级分类查询二级分类 Charles 2014/6/11
     * @param materialCategory
     * @return
     */
    List<MaterialCategory> findSecondCategoryByParentCategory(MaterialCategory materialCategory);

    /**
     * 根据一级分类查询有效的二级分类 Charles 2014/6/11
     * @param materialCategory
     * @param isValid
     * @return
     */
    List<MaterialCategory> findSecondCategoryByParentCategoryAndIsValid(MaterialCategory materialCategory,int isValid);
}
