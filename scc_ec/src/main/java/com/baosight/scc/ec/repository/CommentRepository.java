package com.baosight.scc.ec.repository;


import com.baosight.scc.ec.model.EcUser;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.baosight.scc.ec.model.Comment;
import com.baosight.scc.ec.model.Item;
import com.baosight.scc.ec.model.Material;
import com.baosight.scc.ec.type.CommentType;

public interface CommentRepository extends PagingAndSortingRepository<Comment,String> {
	
	//查看辅料评价记录
	Page<Comment> findByTypeAndItem(CommentType type,Item item,Pageable pageable);
	
	//查看辅料评价记录
	Page<Comment> findByItem(Item item,Pageable pageable);

    //根据用户，查询该用户发出的评论内容
    Page<Comment> findByUser(EcUser user,Pageable pageable);
}
