package com.common.core.schedule.service.impl;

import com.common.core.schedule.domain.BatchTimerDef;
import com.common.core.schedule.mapper.BatchTimerDefMapper;
import com.common.core.schedule.service.BatchTimerDefService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class BatchTimerDefServiceImpl implements BatchTimerDefService {
	
	@Autowired
	private BatchTimerDefMapper batchTimerDefMapper;

	@Override
	public List<BatchTimerDef> getTaskListByStatus(String status) {
		return batchTimerDefMapper.getTaskListByStatus(status);
	}


}
