package com.common.simpale.mapper;

import com.common.simpale.dto.FundDto;
import com.common.simpale.pojo.Fund;
import com.common.simpale.service.batch.BatchProcessMapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FundMapper extends BatchProcessMapper<Fund> {

    List<Fund> selectAll();

    Fund selectOne(Integer id);

    void insert(Fund fund);

    void update(Fund fund);

    void delete(Integer id);

    Fund selectByCodeAndType(Fund fund);

    List<Fund> selectByType(FundDto fundDto);

}
