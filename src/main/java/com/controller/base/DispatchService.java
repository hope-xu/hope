package com.controller.base;

import com.core.ret.Result;

import java.util.Map;

/**
 * 业务逻辑接口类
 *
 * 
 */
public interface DispatchService {

	Result<Object> execute(Map<String, Object> map);
}