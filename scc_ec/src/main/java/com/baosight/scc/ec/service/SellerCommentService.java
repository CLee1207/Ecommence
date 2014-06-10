package com.baosight.scc.ec.service;

import com.baosight.scc.ec.model.EcUser;
import com.baosight.scc.ec.model.SellerComment;
import com.baosight.scc.ec.type.CommentType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Created by sam on 2014/5/30.
 */
public interface SellerCommentService {

    /*
    保存卖家评论信息
     */
    public SellerComment save(SellerComment sellerComment);

    /*
    查询该用户下的所有评价内容
     */
    public Page<SellerComment> findByUser(EcUser user,Pageable pageable);

    /*
    查询该用户下好评率的评价内容
     */
    Page<SellerComment> findByUserAndType(EcUser user,CommentType type,Pageable pageable);

    /*
    查询该用户下，评价内容是否为空的列表
     */
    Page<SellerComment> findByUserAndContent(EcUser user,String content,Pageable pageable);

    /*
    查询该用户下，好评率和内容是否为空的，评价列表
     */
    Page<SellerComment> findByUserAndTypeAndContent(EcUser user,CommentType type,String content,Pageable pageable);

    /*
    卖家发出的评论
     */
    Page<SellerComment> findSellerSendComments(EcUser user,Integer type,String content,Pageable pageable);

    /*
    来自卖家的评价
     */
    Page<SellerComment> findSellerCommentsFromSeller(EcUser user,Integer type,String content,Pageable pageable);
}
