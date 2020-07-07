package com.core.schedule.service;

import com.core.schedule.domain.BatchTimerDef;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface BatchTimerDefService {
	
	List<BatchTimerDef> getTaskListByStatus(String status);

}
