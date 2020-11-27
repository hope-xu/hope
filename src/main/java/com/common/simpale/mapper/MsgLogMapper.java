package com.common.simpale.mapper;

import com.common.simpale.pojo.MsgLog;
import com.common.simpale.service.batch.BatchProcessMapper;

import java.util.List;

public interface MsgLogMapper extends BatchProcessMapper<MsgLog> {

    void insert(MsgLog msgLog);

    void updateStatus(MsgLog msgLog);

    List<MsgLog> selectTimeoutMsg();

    void updateTryCount(MsgLog msgLog);

    MsgLog selectByPrimaryKey(String msgId);

}
