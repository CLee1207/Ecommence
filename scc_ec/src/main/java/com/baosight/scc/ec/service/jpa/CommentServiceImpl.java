package com.baosight.scc.ec.service.jpa;

import com.baosight.scc.ec.model.*;
import com.baosight.scc.ec.service.*;
import com.baosight.scc.ec.type.OrderState;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.baosight.scc.ec.repository.CommentRepository;
import com.baosight.scc.ec.security.UserContext;
import com.baosight.scc.ec.type.CommentType;
import com.baosight.scc.ec.utils.GuidUtil;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.Iterator;
import java.util.List;

@Service
@Transactional
public class CommentServiceImpl implements CommentService {

	@Autowired
	private CommentRepository cr;
	@Autowired
    private UserContext userContext;
	@Autowired
	private ItemService itemService;
    @Autowired
    private FabricService fabricService;
    @Autowired
    private MaterialService materialService;
    @PersistenceContext
    private EntityManager em;
    @Autowired
    private OrderItemService orderItemService;

    @Override
	public Comment save(Comment comment) {
        comment.setId(GuidUtil.newGuid());
        em.persist(comment);
        OrderItem orderItem = orderItemService.findById(comment.getOrderItem().getId());
        String status = orderItem.getStatus();
        if(!status.equals(OrderState.SELLER_APPRAISE.toString())){
            orderItem.setStatus(OrderState.BUYER_APPRAISE.toString()); //买家已评
            //修改成交笔数
            String proId = comment.getItem().getId();
            int flag = itemService.checkItemType(proId);
            if(flag == 0){
                Fabric fabric = this.fabricService.findById(proId);
                fabric.setBidCount(fabric.getBidCount()+1);
            }else {
                Material material = this.materialService.findOne(proId);
                material.setBidCount(material.getBidCount() + 1);
            }
        }else {
            orderItem.setStatus(OrderState.FINISH.toString()); //双方已评价
        }
		return comment;
	}

    @Override
    public Page<Comment> findByUser(EcUser user,Pageable pageable){
        return this.cr.findByUser(user,pageable);
    }


    @Override
    public Page<Comment> findCommentsFromBuyer(EcUser user, Integer type, String content, Pageable pageable) {
        StringBuffer querySql = new StringBuffer("select m from Material m join fetch Comment c where m.createdBy=:user");

     //   StringBuffer querySql = new StringBuffer("select c from Comment c where exists(select a from c.item a where a.createdBy.id = '"+uid+"')");
        if (type != null){
      //      querySql.append(" and c.type = '"+ CommentType.values()[type]+"'  and c.content is empty");
            querySql.append(" and c.type=:type ");
        }
        if (content != null){
            querySql.append(" and c.content is not null");
        }
        querySql.append(" order by c.createdTime desc");

        //StringBuffer countSql = new StringBuffer("select count(c.item) from Comment c where exists(select a from c.item a where a.createdBy.id = '"+uid+"')");
        StringBuffer countSql = new StringBuffer("select count(i.comment) from Item i join i.Comment a where i.createdBy=:user ");
        if (type != null){
            countSql.append(" and a.type=:type  ");
        }
        if (content != null){
            countSql.append(" and a.content is not null");
        }

        CommentType commentType = null;
        Query query = em.createQuery(querySql.toString()).setParameter("user",user);
        Query countQuery = em.createQuery(countSql.toString()).setParameter("user",user);

        if (type != null){
            commentType = CommentType.values()[type];
            query.setParameter("type",commentType);
            countQuery.setParameter("type",commentType);
        }

        query.setFirstResult(pageable.getOffset()).setMaxResults(pageable.getPageSize());
        List<Comment> comments = query.getResultList();
        Long total = (Long)countQuery.getSingleResult();

        Page<Comment> page = new PageImpl<Comment>(comments,pageable,total);

        return page;
    }

    @Override
    public Page<DimensionRate> findCommentsDimensionRates(Pageable pageable) {
        StringBuffer querySql = new StringBuffer("select avg(c.attitude) as attitude,avg(c.deliverySpeed) as deliverySpeed," +
                "avg(c.satisfied) as satisfied,i.createdBy as seller from Item i join i.comments c group by i.createdBy");
        Query query = em.createQuery(querySql.toString());
        List result = query.getResultList();
        DimensionRate dimensionRate = new DimensionRate();
        for (Iterator iterator = result.iterator();iterator.hasNext();){
            Object[] values = (Object[])iterator.next();
            dimensionRate.setAttitude(Double.parseDouble(values[0].toString()));
            dimensionRate.setDeliverySpeed(Double.parseDouble(values[1].toString()));
            dimensionRate.setSatisfied(Double.parseDouble(values[2].toString()));
            dimensionRate.setSeller((EcUser) values[3]);
            System.out.println("================================"+values[0]);
            System.out.println("================================"+values[1]);
            System.out.println("================================"+values[2]);

        }
        return null;
    }
}
