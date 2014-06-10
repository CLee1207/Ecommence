package com.baosight.scc.ec.repository;

import com.baosight.scc.ec.model.Advertisement;
import com.baosight.scc.ec.model.AdvertisementPosition;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;

/**
 * Created by Charles on 2014/5/30.
 */
public interface AdvertisementRepository extends PagingAndSortingRepository<Advertisement,String>{
    Page<Advertisement> findByIsValid(int isValid, Pageable pageable);
    Page<Advertisement> findByIsValidAndAdvertisementPosition(int isValid,AdvertisementPosition advertisementPosition,Pageable pageable);
}
