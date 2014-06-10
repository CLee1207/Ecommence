package com.baosight.scc.ec.rmi;

import java.util.List;
import java.util.Map;

public interface ISupplier {
	// 功能描述： 根据提供的用户登录名，获取用户中文名、英文名、缩写字母
	// 输入参数： 用户登陆名List
	// 输出参数： 用户基本信息List
	// 说 明： 用户基本信息:用户编号、用户登录名、用户中文名、英文名、缩写字母
	// public List getUserInfoByUserId(List userIdList)

	/**
	 * 将查询得到的数据保存到list集合中
	 * 
	 * @param referenceList
	 *            查询得到的供应商数据集合
	 */
	public List querySupplier(List<Map<String, Object>> referenceList);
}
