package com.baosight.scc.ec.service;

import com.baosight.scc.ec.model.Information;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Created by Charles on 2014/6/5.
 */

public interface InformationService {
    /**
     * 保存广告
     * @param information
     * @return
     */
    Information save(Information information);
    /**
     * 更新广告
     * @param information
     * @return
     */
    Information update(Information information, String id);
    /**
     * 查找一条广告
     * @param id
     * @return
     */
    Information findById(String id);

    /**
     * 查询有效或无效的广告
     * @param isValid
     * @param pageable
     * @return
     */
    Page<Information> findByIsValid(int isValid, Pageable pageable);

    /**
     * 删除广告
     * @param id
     * @return
     */
    Information delete(String id);
}
