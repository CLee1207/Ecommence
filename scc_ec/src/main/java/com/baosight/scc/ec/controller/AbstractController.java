package com.baosight.scc.ec.controller;

import com.baosight.scc.ec.web.EcGrid;
import com.google.common.collect.Lists;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by zodiake on 2014/5/26.
 */
public abstract class AbstractController<T> {
    @InitBinder
    public void initBinder(WebDataBinder binder) {
        binder.setDisallowedFields("id");
    }
    /*
        function to create a pageRequest
        @param:page,currentPage
        @param:size,how much items should displayed on this page
        @param:sort,sql order by
     */
    protected PageRequest createPageRequest(int page, int size, Sort sort) {
        PageRequest pageRequest = new PageRequest(page - 1, size, sort);
        return pageRequest;
    }

    /*
        default sort by createdTime
        @param:page,currentPage
        @param:size,how much items should displayed on this page
        @param:sort,sql order by
     */
    protected PageRequest createPageRequest(int page, int size) {
        Sort sort = new Sort(Sort.Direction.DESC, "createdTime");
        return createPageRequest(page, size, sort);
    }

    /*
        function to wrapper a list with page info and sent it to jsp
        @param:pageList
     */
    protected EcGrid<T> createGrid(Page<T> pageList) {
        EcGrid<T> grid = new EcGrid<T>();
        grid.setEcList(Lists.newArrayList(pageList));
        grid.setCurrentPage(pageList.getNumber() + 1);
        grid.setTotalPages(pageList.getTotalPages());
        grid.setTotalRecords(pageList.getTotalElements());
        return grid;
    }

    /*
        将前台传入的两个数组分装成map
     */
    protected Map<Double, Double> buildRanges(Double[] keys, Double[] values) {
        Map<Double, Double> map = new HashMap<Double, Double>();
        if (keys != null && values != null) {
            for (int i = 0; i < keys.length; i++) {
                map.put(keys[i], values[i]);
            }
        }
        return map;
    }
}
