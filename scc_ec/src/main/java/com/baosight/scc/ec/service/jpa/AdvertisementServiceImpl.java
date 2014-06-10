package com.baosight.scc.ec.service.jpa;


import com.baosight.scc.ec.model.Advertisement;
import com.baosight.scc.ec.repository.AdvertisementRepository;
import com.baosight.scc.ec.service.AdvertisementService;
import com.baosight.scc.ec.utils.GuidUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by Charles on 2014/6/3.
 */
@Service
@Transactional
public class AdvertisementServiceImpl implements AdvertisementService {
    @Autowired
    private AdvertisementRepository ar;
    @Override
    public Advertisement save(Advertisement advertisement) {
        advertisement.setId(GuidUtil.newGuid());
        return ar.save(advertisement);
    }

    @Override
    public Advertisement update(Advertisement advertisement,String id) {
        Advertisement ad = findById(id);
        ad.setAdvertisementPosition(advertisement.getAdvertisementPosition());
        ad.setCoverPath(advertisement.getCoverPath());
        ad.setTitle(advertisement.getTitle());
        ad.setLink(advertisement.getLink());
        ad.setIsValid(advertisement.getIsValid());
        return ad;
    }

    @Override
    public Advertisement findById(String id) {
        return ar.findOne(id);
    }

    @Override
    public Page<Advertisement> findByIsValid(int isValid, Pageable pageable) {
        return ar.findByIsValid(isValid, pageable);
    }
}
