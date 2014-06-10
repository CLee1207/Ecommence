package com.baosight.scc.ec.utils;

import org.springframework.util.StringUtils;

import java.util.UUID;

public class GuidUtil {
	
	public static String newGuid()
	{
		return UUID.randomUUID().toString();
	}
	
	public static String newGuid(String objectGuid)
	{
		if (StringUtils.isEmpty(objectGuid))
			return UUID.randomUUID().toString();
		else
			return objectGuid.trim();
	}

}
