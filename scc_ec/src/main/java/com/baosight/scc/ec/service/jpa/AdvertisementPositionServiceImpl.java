package com.baosight.scc.ec.service.jpa;

import com.baosight.scc.ec.model.Advertisement;
import com.baosight.scc.ec.model.AdvertisementPosition;
import com.baosight.scc.ec.repository.AdvertisementPositionRepository;
import com.baosight.scc.ec.service.AdvertisementPositionService;
import com.baosight.scc.ec.utils.GuidUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Charles on 2014/6/3.
 */
@Service
@Transactional
public class AdvertisementPositionServiceImpl implements AdvertisementPositionService {

    @Autowired
    private AdvertisementPositionRepository apr;

    @Override
    public AdvertisementPosition save(AdvertisementPosition advertisementPosition) {
        advertisementPosition.setId(GuidUtil.newGuid());
        return apr.save(advertisementPosition);
    }

    @Override
    public AdvertisementPosition update(AdvertisementPosition advertisementPosition) {
        return apr.save(advertisementPosition);
    }

    @Override
    @Transactional(readOnly = true)
    public AdvertisementPosition findById(String id) {
        return apr.findOne(id);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<AdvertisementPosition> findByIsValid(int isValid, Pageable pageable) {
        return apr.findByIsValid(isValid, pageable);
    }

    @Override
    public List<AdvertisementPosition> findByIsValid(int isValid) {
        return apr.findByIsValid(isValid);
    }

    /**
     * 启动加载广告
     * @return
     */
    public Map findAllAd(){
        List<AdvertisementPosition> advertisementPositionList = apr.findByIsValid(0);
        Map<String,List<Advertisement>> map = new HashMap<String, List<Advertisement>>();
        for (int i = 0;i < advertisementPositionList.size(); i ++){
            AdvertisementPosition advertisementPosition = advertisementPositionList.get(i);
            advertisementPosition.getAdvertisements().size();
            map.put(advertisementPosition.getPositionNo(),advertisementPosition.getAdvertisements());
        }
        return map;
    }
}
