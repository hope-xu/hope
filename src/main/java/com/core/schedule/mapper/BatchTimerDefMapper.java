package com.core.schedule.mapper;

import com.core.schedule.domain.BatchTimerDef;
import com.driver.SimpleSelectLangDriver;
import org.apache.ibatis.annotations.Lang;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

@Service
public interface BatchTimerDefMapper extends Mapper<BatchTimerDef> {

	
	@Select("select * from batch_timer_def where  STATUS =#{status}")
	@Lang(SimpleSelectLangDriver.class)
	List<BatchTimerDef> getTaskListByStatus(String status);

}
