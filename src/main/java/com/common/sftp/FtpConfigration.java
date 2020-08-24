package com.common.sftp;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * @author Hope
 * Date： 2020/07/30  14:50
 * 描述：
 */
public class FtpConfigration {

    private static final Logger log = LoggerFactory.getLogger(FtpConfigration.class);

    @Resource
    SftpFileParameterDao sftpFileParameterDao;

    /**
     * 通过交易id获取配置信息
     * @param tranId
     * @return
     */
    public GroupFileForUpload.FileForUpload getUploadInfo(String tranId){

        GroupFileForUpload.FileForUpload fileForUpload = null;

        if ("DB".equals("DB")){
            //获取数据库配置
           List<SftpFileParameter> list = sftpFileParameterDao.selectAllParameter();

           if (CollectionUtils.isEmpty(list)){
               //抛异常
           }

           //list 转换map
            Map<String, SftpFileParameter> maps = list.stream().collect(Collectors.toMap(SftpFileParameter::getTranId, Function.identity(), (key1, key2) -> key2));

            SftpFileParameter sftpFileParameter = maps.get(tranId);

            //构造返回参数
           return ConstructUploadData(fileForUpload,sftpFileParameter);

        }

        return null;

    }

    private GroupFileForUpload.FileForUpload ConstructUploadData(GroupFileForUpload.FileForUpload fileForUpload, SftpFileParameter sftpFileParameter) {

        DXConfiguration configuration = new DXConfiguration();

        fileForUpload = new GroupFileForUpload.FileForUpload();

        configuration.setHost(sftpFileParameter.getHost());
        configuration.setPort(sftpFileParameter.getPort());
        configuration.setUserName(sftpFileParameter.getUserName());
        configuration.setUserPassword(sftpFileParameter.getUserPassword());
        configuration.setDecrypt(Boolean.parseBoolean(sftpFileParameter.getIsCrypt()));

        fileForUpload.setFtpDir(sftpFileParameter.getFtpDir());
        fileForUpload.setLocalDir(sftpFileParameter.getLocalDir());
//        fileForUpload.setFile(sftpFileParameter.getF);
        fileForUpload.setIsValid(sftpFileParameter.getIsValid());
        fileForUpload.setConf(configuration);

        if (log.isInfoEnabled()) {
            log.info("配置参数："+fileForUpload.toString());
        }

        return fileForUpload;
    }


}
