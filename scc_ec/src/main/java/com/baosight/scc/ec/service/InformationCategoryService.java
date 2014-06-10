package com.baosight.scc.ec.service;

import com.baosight.scc.ec.model.InformationCategory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * Created by Charles on 2014/6/5.
 */

public interface InformationCategoryService {
    /**
     * 保存资讯分类
     * @param informationCategory
     * @return
     */
    InformationCategory save(InformationCategory informationCategory);
    /**
     * 更新资讯分类
     * @param informationCategory
     * @return
     */
    InformationCategory update(InformationCategory informationCategory,String id);
    /**
     * 查找资讯分类
     * @param id
     * @return
     */
    InformationCategory findById(String id);

    /**
     * 查询有效或无效的资讯分类,有分页功能
     * @param isValid
     * @param pageable
     * @return
     */
    Page<InformationCategory> findByIsValid(int isValid, Pageable pageable);

    /**
     * 查询有效或无效的资讯分类
     * @param isValid
     * @return
     */
    List<InformationCategory> findByIsValid(int isValid);

    /**
     * 删除资讯分类
     * @param id
     * @return
     */
    InformationCategory delete(String id);
}
