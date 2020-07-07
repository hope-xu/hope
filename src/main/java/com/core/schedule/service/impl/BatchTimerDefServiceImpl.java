package com.core.schedule.service.impl;

import com.core.schedule.domain.BatchTimerDef;
import com.core.schedule.mapper.BatchTimerDefMapper;
import com.core.schedule.service.BatchTimerDefService;
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
