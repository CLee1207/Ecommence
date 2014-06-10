package com.baosight.scc.ec.repository;

import com.baosight.scc.ec.model.EcUser;
import org.springframework.data.repository.CrudRepository;

/**
 * Created by zodiake on 2014/5/12.
 */
public interface EcUserRepository extends CrudRepository<EcUser,String> {
	
	/**
	 * 根据id查询用户信息
	 * @param id 用户id
	 */
	EcUser findById(String id);
}
