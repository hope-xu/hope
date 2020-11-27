package com.common.simpale.service.batch.mapperproxy;

import com.common.simpale.common.Constant;
import com.common.simpale.service.batch.BatchProcessMapper;
import com.google.common.collect.Lists;
import org.springframework.util.CollectionUtils;

import java.util.List;

public class MapperProxy<T> implements BatchProcessMapper<T> {

    private BatchProcessMapper batchProcessMapper;

    public MapperProxy(BatchProcessMapper batchProcessMapper) {
        this.batchProcessMapper = batchProcessMapper;
    }

    @Override
    public void batchInsert(List<T> list) {
        if (CollectionUtils.isEmpty(list)) {
            return;
        }

        List<List<T>> partition = Lists.partition(list, Constant.MAX_SIZE_PER_TIME);
        for (List<T> batchList : partition) {
            batchProcessMapper.batchInsert(batchList);
        }
    }

    @Override
    public void batchUpdate(List<T> list) {
        if (CollectionUtils.isEmpty(list)) {
            return;
        }

        List<List<T>> partition = Lists.partition(list, Constant.MAX_SIZE_PER_TIME);
        for (List<T> batchList : partition) {
            batchProcessMapper.batchUpdate(batchList);
        }
    }

}
