package com.baosight.scc.ec.repository;

import com.baosight.scc.ec.model.EcUser;
import com.baosight.scc.ec.model.SellerComment;
import com.baosight.scc.ec.type.CommentType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;

/**
 * Created by sam on 2014/5/30.
 */
public interface SellerCommentRepository  extends PagingAndSortingRepository<SellerComment,String> {

    /*
    查询该用户下的评价内容
     */
    Page<SellerComment> findByUser(EcUser user,Pageable pageable);

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
}
