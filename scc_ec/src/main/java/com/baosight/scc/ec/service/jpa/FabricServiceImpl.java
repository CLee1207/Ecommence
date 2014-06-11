package com.baosight.scc.ec.service.jpa;

import com.baosight.scc.ec.model.*;
import com.baosight.scc.ec.repository.*;
import com.baosight.scc.ec.service.FabricService;
import com.baosight.scc.ec.type.CommentType;
import com.baosight.scc.ec.utils.GuidUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.Collections;

/**
 * Created by zodiake on 2014/5/12.
 */
@Service
@Transactional
public class FabricServiceImpl implements FabricService {
    @Autowired
    private FabricRepository fr;
    @Autowired
    private OrderLineRepository or;
    @Autowired
    private CommentRepository cr;
    @Autowired
    private EcUserRepository er;
    @Autowired
    private FabricCategoryRepository fcr;
    @PersistenceContext
    private EntityManager em;

    final Logger logger = LoggerFactory.getLogger(FabricServiceImpl.class);

    @Transactional(readOnly = true)
    public Page<Fabric> findAll(Pageable pageable) {
        return fr.findAll(pageable);
    }

    @Transactional(readOnly = true)
    public Page<Fabric> findByUser(EcUser user, Pageable pageable) {
        return fr.findByCreatedBy(user, pageable);
    }

    @Transactional(readOnly = true)
    public Fabric findById(String id) {
        Fabric fabric=fr.findOne(id);
        fabric.getCreatedBy();
        return fabric;
    }

    /*
    @param id pk
    @Param u currentUser
    @param init onetomany
 */
    @Transactional(readOnly = true)
    public Fabric findByIdAndUser(String id, EcUser u, boolean init) {
        TypedQuery query = em.createNamedQuery("fabric.findByIdAndCreatedBy", Fabric.class);
        query.setParameter(1, id);
        query.setParameter(2, u);
        Fabric fabric = (Fabric) query.getSingleResult();
        if (fabric != null && init) {
            fabric.getMainUseTypes().size();
            fabric.getColors().size();
            fabric.getRanges().size();
            fabric.getImages().size();
            return fabric;
        }
        return null;
    }

    @Override
    public Fabric save(Fabric fabric) {
        fabric.setId(GuidUtil.newGuid());
        EcUser user = fabric.getCreatedBy();
        EcUser sourUser = em.find(EcUser.class, user.getId());

        if (fabric.getImages().size() != 0)
            fabric.setCoverImage(fabric.getImages().get(0).getLocation());

        if (!sourUser.getPreferFabricCategory().getCategories().contains(fabric.getCategory())) {
            sourUser.getPreferFabricCategory().getCategories().add(fabric.getCategory());
        }
        Double min = new Double(0);
        if (fabric.getRanges().size() > 0)
            min = Collections.min(fabric.getRanges().values());
        fabric.setPrice(min);
        return fr.save(fabric);
    }

    @Override
    public Fabric update(Fabric fabric) {
        EcUser user = fabric.getCreatedBy();
        EcUser sourUser = em.find(EcUser.class, user.getId());
        if (fabric.getImages().size() != 0)
            fabric.setCoverImage(fabric.getImages().get(0).getLocation());
        if (!sourUser.getPreferFabricCategory().getCategories().contains(fabric.getCategory())) {
            sourUser.getPreferFabricCategory().getCategories().add(fabric.getCategory());
        }
        Double min = Collections.min(fabric.getRanges().values());
        fabric.setPrice(min);
        return fr.save(fabric);
    }

    @Transactional(readOnly = true)
    public Page<OrderLine> showFabricOrdersByFid(String id, Pageable pageable) {
        Fabric fabric = this.fr.findOne(id);
        return this.or.findByItem(fabric, pageable);
    }

    @Transactional(readOnly = true)
    public Page<Comment> showFabricCommentsByFid(String id, Integer flag, Pageable pageable) {
        CommentType type = null;
        if (flag != null) {
            switch (flag) {
                case 1:
                    type = CommentType.好评;
                    break;
                case 0:
                    type = CommentType.中评;
                    break;
                case -1:
                    type = CommentType.差评;
                    break;
                default:
                    type = CommentType.好评;
            }
        }
        //面料信息
        Fabric fabric = this.fr.findOne(id);
        Page<Comment> fabricPage = null;
        if (type == null) {
            fabricPage = this.cr.findByItem(fabric, pageable);
        } else {
            fabricPage = this.cr.findByTypeAndItem(type, fabric, pageable);
        }
        return fabricPage;
    }

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
    @Transactional(readOnly = true)
    public Page<Fabric> searchItems(String id, String proName, String secondCategory, Pageable pageable) {
        Page<Fabric> page = null;
        if (secondCategory != null) {
            page = this.searchItemsByProAndCateId(id, secondCategory, pageable);
        } else {
            page = this.searchItemsByProIdAndName(id, proName, pageable);
        }
        return page;
    }

    /**
     * 根据面料供应商、分类，查询该面料供应商下的产品分页列表信息
     *
     * @param id             供应商id
     * @param secondCategory 二级分类
     * @param pageable
     * @return
     * @author sam
     */
    @Transactional(readOnly = true)
    public Page<Fabric> searchItemsByProAndCateId(String id, String secondCategory, Pageable pageable) {
        Page<Fabric> page = null;
        //根据供应商id，获取面料供应商信息
        EcUser user = this.er.findOne(id);
        //根据二级分类id，查询分类信息
        FabricCategory category = this.fcr.findOne(secondCategory);
        //根据供应商、分类，查询面料产品列表信息
        page = this.fr.findByCreatedByAndCategory(user, category, pageable);
        return page;
    }

    /**
     * 根据面料供应商、产品名称，查询该面料供应商下的产品分页列表信息
     *
     * @param id       供应商id
     * @param proName  产品名称
     * @param pageable
     * @return
     * @author sam
     */
    @Transactional(readOnly = true)
    public Page<Fabric> searchItemsByProIdAndName(String id, String proName, Pageable pageable) {
        Page<Fabric> page = null;
        //根据面料供应商id，查询供应商信息
        EcUser user = this.er.findOne(id);
        //根据供应商、产品名称，查询面料产品列表信息
        page = this.fr.findByCreatedByAndName(user, proName, pageable);
        return page;
    }
}
