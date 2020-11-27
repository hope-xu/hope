package com.common.simpale.service.impl;

import com.common.simpale.common.Constant;
import com.common.simpale.dto.FundDto;
import com.common.simpale.mapper.FundMapper;
import com.common.simpale.pojo.Fund;
import com.common.simpale.service.FundService;
import com.common.simpale.util.ListUtils;
import com.google.common.collect.Lists;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class FundServiceImpl implements FundService {

    @Autowired
    private FundMapper fundMapper;

    @Override
    public List<FundDto> combine(FundDto fundDto) {
        List<String> list = fundDto.getOrderByList();
        if (CollectionUtils.isEmpty(list)) {
            return null;
        }

        List<List<FundDto>> lists = Lists.newArrayList();
        for (String orderByType : list) {
            FundDto dto = FundDto.builder()
                    .type(fundDto.getType())
                    .orderBy(orderByType)
                    .sort(Constant.FundSortType.DESC.getType())
                    .limit(fundDto.getLimit())
                    .build();
            List<Fund> fundList = fundMapper.selectByType(dto);
            lists.add(toFundDtoList(fundList));
        }

        ListUtils listUtils = new ListUtils<FundDto>();
        return listUtils.intersection(lists, true);
    }

    @Override
    public void update(List<FundDto> list) {
        if (CollectionUtils.isEmpty(list)) {
            return;
        }

        for (FundDto fundDto : list) {
            Fund fund = toFund(fundDto);
            // TODO lock
            Fund oldFund = fundMapper.selectByCodeAndType(fund);
            if (null == oldFund) {
                fundMapper.insert(fund);
            } else {
                fund.setId(oldFund.getId());
                fundMapper.update(fund);
            }
        }
    }

    @Override
    public List<FundDto> search(FundDto fundDto) {
        List<Fund> fundList = fundMapper.selectByType(fundDto);
        return toFundDtoList(fundList);
    }

    private Fund toFund(FundDto fundDto) {
        Fund fund = Fund.builder().build();
        BeanUtils.copyProperties(fundDto, fund);
        return fund;
    }

    private List<FundDto> toFundDtoList(List<Fund> fundList) {
        if (CollectionUtils.isEmpty(fundList)) {
            return null;
        }

        return fundList.stream().map(fund -> {
            FundDto fundDto = FundDto.builder().build();
            BeanUtils.copyProperties(fund, fundDto);
            return fundDto;
        }).collect(Collectors.toList());
    }

}
