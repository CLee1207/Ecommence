package com.baosight.scc.ec.repository;

import com.baosight.scc.ec.model.AdvertisementPosition;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;

/**
 * Created by Charles on 2014/5/30.
 */
public interface AdvertisementPositionRepository extends PagingAndSortingRepository<AdvertisementPosition,String>{
    List<AdvertisementPosition> findByIsValid(int isValid);

    Page<AdvertisementPosition> findByIsValid(int isValid, Pageable pageable);
}
