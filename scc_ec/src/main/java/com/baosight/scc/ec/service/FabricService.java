package com.baosight.scc.ec.service;

import com.baosight.scc.ec.model.Comment;
import com.baosight.scc.ec.model.EcUser;
import com.baosight.scc.ec.model.Fabric;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Created by zodiake on 2014/5/12.
 */
public interface FabricService {
    Page<Fabric> findByUser(EcUser user, Pageable pageable);

    Fabric findById(String id);

    Fabric findByIdAndUser(String id, EcUser u, boolean init);

    Fabric save(Fabric fabric);

    Fabric update(Fabric fabric);

    /**
     * 根据面料id，，查询面料交易评价记录
     *
     * @param id       面料id
     * @param flag     标记：1表示好评，0表示中评，-1表示差评，null表示全部
     * @param pageable
     * @return
     * @author sam
     */
    Page<Comment> showFabricCommentsByFid(String id, Integer flag, Pageable pageable);

    /**
     * 根据条件查询面料供应商店内产品列表分页信息
     *
     * @param id             面料供应商id
     * @param proName        面料名称
     * @param secondCategory 二级分类id
     * @param pageable
     * @return
     * @author sam
     */
    Page<Fabric> searchItems(String id, String proName, String secondCategory, Pageable pageable);

    /**
     * 根据面料供应商、分类，查询该面料供应商下的产品分页列表信息
     *
     * @param id             供应商id
     * @param secondCategory 二级分类
     * @param pageable
     * @return
     * @author sam
     */
    Page<Fabric> searchItemsByProAndCateId(String id, String secondCategory, Pageable pageable);

    /**
     * 根据面料供应商、产品名称，查询该面料供应商下的产品分页列表信息
     *
     * @param id       供应商id
     * @param proName  产品名称
     * @param pageable
     * @return
     * @author sam
     */
    Page<Fabric> searchItemsByProIdAndName(String id, String proName, Pageable pageable);
}
