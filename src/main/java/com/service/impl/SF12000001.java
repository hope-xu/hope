package com.service.impl;

import com.common.sftp.FileTranInfoDao;
import com.common.sftp.FileTranStatusDao;
import com.common.sftp.FtpConfigration;
import com.common.sftp.GroupFileForUpload;
import com.model.SF12000001In;
import com.service.ISF12000001;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @author Hope
 * Date： 2020/07/30  14:35
 * 描述：文件上传服务
 */

@Service
public class SF12000001 implements ISF12000001 {
    private static final Logger log = LoggerFactory.getLogger(SF12000001.class);

    @Resource
    FtpConfigration ftpConfigration;

    @Resource
    FileTranInfoDao fileTranInfoDao;

    @Resource
    FileTranStatusDao fileTranStatusDao;

    //1.接收服务请求，将信息入库
    public void uploadFileInfo(SF12000001In in){
        if (log.isInfoEnabled()) {
            log.info("**********收到其它系统上传文件通知**********"+in);
        }

        GroupFileForUpload.FileForUpload uploadInfo = ftpConfigration.getUploadInfo(in.getTranId());

        //2.判断是否有效
        if (!Boolean.parseBoolean(uploadInfo.getIsValid())){
            //抛异常
            return;

        }

        if (!(in.getFilePath() ==null || in.getFilePath().length()==0)){
            uploadInfo.setLocalDir(in.getFilePath());
        }

        uploadInfo.setFile(in.getFileName());

        //记录交易信息
        recoderTranInfo(uploadInfo,in);

        return;

    }

    private void recoderTranInfo(GroupFileForUpload.FileForUpload uploadInfo, SF12000001In in) {




    }

}
