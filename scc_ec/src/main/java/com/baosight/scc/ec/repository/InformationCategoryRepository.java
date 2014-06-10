package com.baosight.scc.ec.repository;

import com.baosight.scc.ec.model.InformationCategory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;

/**
 * Created by Charles on 2014/5/30.
 */
public interface InformationCategoryRepository extends PagingAndSortingRepository<InformationCategory,String>{
    List<InformationCategory> findByIsValid(int isValid);

    Page<InformationCategory> findByIsValid(int isValid, Pageable pageable);
}
