package com.common.sftp;

import java.io.Serializable;

/**
 * @author Hope
 * Date： 2020/07/30  15:46
 * 描述：
 */
public class DXConfiguration implements Serializable {

    private static final long serialVersionUID = 476613884852996854L;

    private String tranId;

    private String host;

    private String port;

    private String userName;

    private String userPassword;
    //加密
    private Boolean decrypt = false;

    public String getTranId() {
        return tranId;
    }

    public void setTranId(String tranId) {
        this.tranId = tranId;
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public String getPort() {
        return port;
    }

    public void setPort(String port) {
        this.port = port;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserPassword() {
        return userPassword;
    }

    public void setUserPassword(String userPassword) {
        this.userPassword = userPassword;
    }

    public Boolean getDecrypt() {
        return decrypt;
    }

    public void setDecrypt(Boolean decrypt) {
        this.decrypt = decrypt;
    }
}
