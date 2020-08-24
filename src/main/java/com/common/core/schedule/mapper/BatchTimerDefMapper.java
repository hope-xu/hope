package com.common.core.schedule.mapper;

import com.common.core.schedule.domain.BatchTimerDef;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

@Service
public interface BatchTimerDefMapper extends Mapper<BatchTimerDef> {

	
	@Select("select * from batch_timer_def where  STATUS =#{status}")
	List<BatchTimerDef> getTaskListByStatus(String status);

}
