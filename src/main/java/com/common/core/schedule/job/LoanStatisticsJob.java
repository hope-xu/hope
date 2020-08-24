package com.common.core.schedule.job;

import com.common.core.schedule.config.IScheduled;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


@Component
public class LoanStatisticsJob implements IScheduled {

	private static Logger log = LoggerFactory.getLogger(LoanStatisticsJob.class);

	 @Autowired
//	  private LoanStatisticsService loanStatisticsService;

	@Override
	public void execute() {
		try {

			log.info("-------开始定时查询还款计划数据-------");
//			loanStatisticsService.saveRepaymentToLoan();
			log.info("-------结束定时查询还款计划数据-------");

		} catch (Exception e) {
			log.error("定时任务: 将还款计划数据定时存储到放款统计表中失败！", e);
		}

	}

}
