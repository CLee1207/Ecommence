package com.baosight.scc.ec.service;

import com.baosight.scc.ec.model.AdvertisementPosition;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Map;

/**
 * Created by Charles on 2014/5/30.
 */

public interface AdvertisementPositionService {
    /**
     * 保存广告栏位
     * @param advertisementPosition
     * @return
     */
    AdvertisementPosition save(AdvertisementPosition advertisementPosition);
    /**
     * 更新广告栏位
     * @param advertisementPosition
     * @return
     */
    AdvertisementPosition update(AdvertisementPosition advertisementPosition);
    /**
     * 查找一条广告栏位
     * @param id
     * @return
     */
    AdvertisementPosition findById(String id);

    /**
     * 查询有效或无效的广告栏位,有分页功能
     * @param isValid
     * @param pageable
     * @return
     */
    Page<AdvertisementPosition> findByIsValid(int isValid, Pageable pageable);

    /**
     * 查询有效或无效的广告栏位
     * @param isValid
     * @return
     */
    List<AdvertisementPosition> findByIsValid(int isValid);
    /**
     * 启动加载广告
     * @return
     */
    Map findAllAd();
}
