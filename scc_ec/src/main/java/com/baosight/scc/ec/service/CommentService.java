package com.baosight.scc.ec.service;

import com.baosight.scc.ec.model.Comment;
import com.baosight.scc.ec.model.DimensionRate;
import com.baosight.scc.ec.model.EcUser;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * 
 * @author sam
 *
 */
public interface CommentService {

	/**
	 * 添加评论信息
	 * @param comment
	 * @return
	 * @author sam
	 */
	public Comment save(Comment comment);

    /**
     * 根据用户信息，查询该用户下的所有评论内容
     * @param user 用户信息
     * @param pageable
     * @return
     * @author sam
     */
    public Page<Comment> findByUser(EcUser user,Pageable pageable);


    /**
     * 来自买家的评论
     * @param user 用户
     * @param type 评价类型
     * @param content 评价内容
     * @param pageable
     * @return
     */
    public Page<Comment> findCommentsFromBuyer(EcUser user,Integer type,String content,Pageable pageable);

    /**
     * 卖家店铺商品维度动态评分
     * @param pageable 卖家
     * @return
     */
    public Page<DimensionRate> findCommentsDimensionRates(Pageable pageable);



}
