
SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for batch_timer_def
-- ----------------------------
DROP TABLE IF EXISTS `batch_timer_def`;
CREATE TABLE `batch_timer_def` (
  `TIMER_ID` varchar(32) DEFAULT NULL COMMENT '定时器ID',
  `TIMER_NAME` varchar(100) DEFAULT NULL COMMENT '定时任务名称',
  `CRON` varchar(32) DEFAULT NULL COMMENT '定时器表达式',
  `SYSTEM_ID` varchar(32) DEFAULT NULL COMMENT '系统标识',
  `GX_CLASS_NAME` varchar(100) DEFAULT NULL COMMENT '类名',
  `GX_METHOD` varchar(100) DEFAULT NULL COMMENT '方法名',
  `STATUS` varchar(1) DEFAULT NULL COMMENT '是否可用Y正常,N不可用',
  `EXPIRE_TIME` bigint DEFAULT NULL,
  `CREATE_TIME` varchar(100) DEFAULT NULL COMMENT '创建时间',
  UNIQUE KEY `TIMER_ID` (`TIMER_ID`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC COMMENT='定时任务表';
