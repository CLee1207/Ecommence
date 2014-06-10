package com.baosight.scc.ec.web;

import java.util.List;

/**
 * 
 * @author sam
 * 日期：2014-5-16
 * @param <T>
 */
public class EcGrid<T> {
    private int totalPages;

    private int currentPage;

    private long totalRecords;

    private List<T> ecList;

    public int getTotalPages() {
        return totalPages;
    }

    public void setTotalPages(int totalPages) {
        this.totalPages = totalPages;
    }

    public int getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(int currentPage) {
        this.currentPage = currentPage;
    }

    public long getTotalRecords() {
        return totalRecords;
    }

    public void setTotalRecords(long totalRecords) {
        this.totalRecords = totalRecords;
    }

    public List<T> getEcList() {
        return ecList;
    }

    public void setEcList(List<T> ecList) {
        this.ecList = ecList;
    }
}
