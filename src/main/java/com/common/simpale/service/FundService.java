package com.common.simpale.service;

import com.common.simpale.dto.FundDto;

import java.util.List;

public interface FundService {

    List<FundDto> search(FundDto fundDto);

    List<FundDto> combine(FundDto fundDto);

    void update(List<FundDto> list);

}
