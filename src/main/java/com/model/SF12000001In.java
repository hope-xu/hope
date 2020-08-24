package com.model;

import com.sun.istack.internal.NotNull;

import java.io.Serializable;

/**
 * @author Hope
 * Date： 2020/07/30  14:39
 * 描述：
 */
public class SF12000001In implements Serializable {

    private static final long serialVersionUID = -5473873562586294089L;

    @NotNull
    private String tranId;

    private String filePath;

    private String fileName;

    private String tranDate;

    private String fileMd5;

    public String getTranId() {
        return tranId;
    }

    public void setTranId(String tranId) {
        this.tranId = tranId;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getTranDate() {
        return tranDate;
    }

    public void setTranDate(String tranDate) {
        this.tranDate = tranDate;
    }

    public String getFileMd5() {
        return fileMd5;
    }

    public void setFileMd5(String fileMd5) {
        this.fileMd5 = fileMd5;
    }
}
