package com.common.sftp;

import java.io.Serializable;
import java.util.HashMap;

/**
 * @author Hope
 * Date： 2020/07/30  14:54
 * 描述：
 */
public class GroupFileForUpload implements Serializable {

    private static final long serialVersionUID = 377454136961468607L;
    private HashMap<String,FileForUpload> fileGroup;

    public HashMap<String, FileForUpload> getFileGroup() {
        return fileGroup;
    }

    public void setFileGroup(HashMap<String, FileForUpload> fileGroup) {
        this.fileGroup = fileGroup;
    }

    public static class FileForUpload implements Serializable{

        private static final long serialVersionUID = 2477359178218968010L;

        private DXConfiguration conf;
        private String ftpDir;
        private String localDir;
        private String file;
        private String fileFormat;
        private boolean delete = false;
        private String isValid;
        private boolean mkdir = false;

        public DXConfiguration getConf() {
            return conf;
        }

        public void setConf(DXConfiguration conf) {
            this.conf = conf;
        }

        public String getFtpDir() {
            return ftpDir;
        }

        public void setFtpDir(String ftpDir) {
            this.ftpDir = ftpDir;
        }

        public String getLocalDir() {
            return localDir;
        }

        public void setLocalDir(String localDir) {
            this.localDir = localDir;
        }

        public String getFile() {
            return file;
        }

        public void setFile(String file) {
            this.file = file;
        }

        public String getFileFormat() {
            return fileFormat;
        }

        public void setFileFormat(String fileFormat) {
            this.fileFormat = fileFormat;
        }

        public boolean isDelete() {
            return delete;
        }

        public void setDelete(boolean delete) {
            this.delete = delete;
        }

        public String getIsValid() {
            return isValid;
        }

        public void setIsValid(String isValid) {
            this.isValid = isValid;
        }

        public boolean isMkdir() {
            return mkdir;
        }

        public void setMkdir(boolean mkdir) {
            this.mkdir = mkdir;
        }


        @Override
        public String toString() {

            return "FileForUpload{" +
                    "conf=" + conf +
                    ", ftpDir='" + ftpDir + '\'' +
                    ", localDir='" + localDir + '\'' +
                    ", file='" + file + '\'' +
                    ", fileFormat='" + fileFormat + '\'' +
                    ", delete=" + delete +
                    ", isValid='" + isValid + '\'' +
                    ", mkdir=" + mkdir +
                    '}';
        }
    }

}
