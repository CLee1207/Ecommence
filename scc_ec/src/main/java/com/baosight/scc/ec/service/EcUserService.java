package com.baosight.scc.ec.service;

import com.baosight.scc.ec.model.EcUser;

public interface EcUserService {
	
	/**
	 * 根据id，查询服务商信息
	 * @param id 服务商id
	 * @return
	 * @author sam
	 */
	EcUser findById(String id);

    EcUser save(EcUser user);
}
