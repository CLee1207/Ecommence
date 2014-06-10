package com.baosight.scc.ec.service.jpa;

import com.baosight.scc.ec.model.*;
import com.baosight.scc.ec.repository.SellerCommentRepository;
import com.baosight.scc.ec.service.*;
import com.baosight.scc.ec.type.CommentType;
import com.baosight.scc.ec.type.OrderState;
import com.baosight.scc.ec.utils.GuidUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.List;

/**
 * Created by sam on 2014/5/30.
 */
@Service
public class SellerCommentServiceImpl implements SellerCommentService {

    @Autowired
    private SellerCommentRepository scr;
    @Autowired
    private OrderItemService orderItemService;
    @PersistenceContext
    private EntityManager em;
    @Autowired
    private ItemService itemService;
    @Autowired
    private FabricService fabricService;
    @Autowired
    private MaterialService materialService;

    @Override
    public SellerComment save(SellerComment sellerComment) {
        sellerComment.setId(GuidUtil.newGuid());
        em.persist(sellerComment);
        OrderItem orderItem = orderItemService.findById(sellerComment.getOrderItem().getId());
        String status = orderItem.getStatus();
        if (!status.equals(OrderState.BUYER_APPRAISE.toString())){
            orderItem.setStatus(OrderState.SELLER_APPRAISE.toString()); //卖家已评价
            //修改产品成交笔数
            String proId = sellerComment.getItem().getId();
            int flag = itemService.checkItemType(proId);
            if(flag == 0){
                Fabric fabric = this.fabricService.findById(proId);
                fabric.setBidCount(fabric.getBidCount()+1);
            }else {
                Material material = this.materialService.findOne(proId);
                material.setBidCount(material.getBidCount() + 1);
            }
        }else {
            orderItem.setStatus(OrderState.FINISH.toString()); //双方已互评
        }
        return sellerComment;
    }

    @Override
    public Page<SellerComment> findByUser(EcUser user, Pageable pageable) {
        return this.scr.findByUser(user,pageable);
    }

    @Override
    public Page<SellerComment> findByUserAndType(EcUser user, CommentType type, Pageable pageable) {
        return null;
    }

    @Override
    public Page<SellerComment> findByUserAndContent(EcUser user, String content, Pageable pageable) {
        return null;
    }

    @Override
    public Page<SellerComment> findByUserAndTypeAndContent(EcUser user, CommentType type, String content, Pageable pageable) {
        return null;
    }

    @Override
    public Page<SellerComment> findSellerSendComments(EcUser user, Integer type, String content, Pageable pageable) {
        CommentType commentType = null;
        Page<SellerComment> page = null;
        if(type != null){
            commentType = CommentType.values()[type];
        }
        if(type != null && ("".equals(content) || null == content))
            page = this.findByUserAndType(user,commentType,pageable);
        else if(type == null && null != content)
            page = this.findByUserAndContent(user,content,pageable);
        else if (type != null && content != null)
            page = this.findByUserAndTypeAndContent(user,commentType,content,pageable);
        else if (type == null && content == null)
            page = this.findByUser(user,pageable);
        return page;
    }

    @Override
    public Page<SellerComment> findSellerCommentsFromSeller(EcUser user, Integer type, String content, Pageable pageable) {
        StringBuffer querySql = new StringBuffer("select sc from Item i join i.sellerComments sc where i.createdBy=:user ");
        StringBuffer countSql = new StringBuffer("select count(sc) from Item i join i.sellerComments sc where i.createdBy=:user ");
        if (type != null){
            querySql.append(" and sc.type=:type");
            countSql.append(" and sc.type=:type");
        }
        if(content != null){
            querySql.append(" and sc.content is not null");
            countSql.append(" and sc.content is not null");
        }else{
            querySql.append(" and sc.content is null");
            countSql.append(" and sc.content is null");
        }

        Query query = em.createQuery(querySql.toString()).setParameter("user",user);
        Query countQuery = em.createQuery(countSql.toString()).setParameter("user",user);
        CommentType commentType = null;
        if (type != null){
            commentType = CommentType.values()[type];
            query.setParameter("type",commentType);
            countQuery.setParameter("type",commentType);
        }
        List<SellerComment> list = query.setFirstResult(pageable.getOffset()).setMaxResults(pageable.getPageSize()).getResultList();
        Long total = (Long)countQuery.getSingleResult();

        Page<SellerComment> page = new PageImpl<SellerComment>(list,pageable,total);

        return page;
    }
}
