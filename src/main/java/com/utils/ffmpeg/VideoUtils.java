package com.utils.ffmpeg;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class VideoUtils {
    private static Logger log = LoggerFactory.getLogger(VideoUtils.class);

    /**
     * IN_PUT_PATH 原始视频存储路径 /app/IFDZ/clientInfo/video/input
     * OUT_PUT_PATH 转码之后视频存储路径 /app/IFDZ/clientInfo/video/output
     */
    //public static final String IN_PUT_PATH = ConfigProperties.getPropertyByKey("bp.input.path");
    public static final String OUT_PUT_PATH = ConfigProperties.getPropertyByKey("bp.output.path");


    /**
     * 视频转码
     *
     * @param srcVideoPath 待转码视频路径
     * @return
     */
    public static Map<String, Object> convertVideo(String srcVideoPath) {
        Map<String, Object> map = new HashMap<>(3);
        map.put("flag", false);
        if (!checkfile(srcVideoPath)) {
            return map;
        }
        String outPath = getOutPath();
        map.put("outPath", outPath);
        Boolean ffmpeg = FfmpegUtil.ffmpegConver(srcVideoPath, outPath);
        map.put("flag", ffmpeg);
        return map;
    }

    /**
     * 获取视频信息
     *
     * @param srcVideoPath
     * @return
     */
    public static Map<String, Object> getVideoInfo(String srcVideoPath) {
        try {
            return FfmpegUtil.ffmpegVideoInfo(srcVideoPath);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 获取输出文件名
     *
     * @return
     */
    private static String getOutPath() {
        String outPath = OUT_PUT_PATH;
        File file = new File(outPath);
        if (!file.exists()) {
            file.mkdirs();
        }
        return outPath + UUID.randomUUID().toString().replace("-", "") + ".mp4";
    }

    private static boolean checkfile(String path) {
        File file = new File(path);
        return file.isFile() ? true : false;
    }


}


