package com.common.sftp;

import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface SftpFileParameterDao {

    int deleteByPrimaryKey(Integer id);


    int insert(SftpFileParameter record);


    int insertSelective(SftpFileParameter record);


    SftpFileParameter selectByPrimaryKey(Integer id);


    int updateByPrimaryKeySelective(SftpFileParameter record);


    int updateByPrimaryKey(SftpFileParameter record);


    List<SftpFileParameter> selectAllParameter();


}