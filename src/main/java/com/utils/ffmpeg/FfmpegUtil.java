package com.utils.ffmpeg;

import com.alibaba.fastjson.JSONObject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.CollectionUtils;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 根据源视频路径，将其转换格式为mp4
 */
public class FfmpegUtil {

    private static Logger log = LoggerFactory.getLogger(FfmpegUtil.class);

    /**
     * ffmpeg程序安装路径
     * Linux路径 "/soft/ffmpeg/bin"
     * 本地Windows路径 "D:\\dev\\ffmpeg\\bin\\ffmpeg.exe"
     */
    //private static final String ffmpegPath = "/soft/ffmpeg/bin";
    private static String ffmpegPath = "D:\\dev\\ffmpeg\\bin\\ffmpeg.exe";
    //private static String ffmpegPath = "D:\\dev\\ffmpeg\\bin\\ffprobe.exe";

    /**
     * 视频转换
     *
     * @param srcVideoPath 源文件路径
     * @param outPath      输出文件路径
     * @return
     */
    public static Boolean ffmpegConver(String srcVideoPath, String outPath) {

        //Map<String, String> map = System.getenv();log.info("所有环境变量键值对："+map);

        String toolPath = ffmpegPath;
        log.info("ffmpeg参数 toolPath=" + toolPath + ",srcVideoPath=" + srcVideoPath + ",outPath=" + outPath);
        List<String> command = getFfmpegCommand(toolPath, srcVideoPath, outPath);
       return CollectionUtils.isEmpty(command)?false:process(command);
    }

    /**
     * 视频转换
     *
     * @param command ffmpeg命令
     * @return
     */
    public static Boolean process(List<String> command) {

        // Process videoProcess = Runtime.getRuntime().exec(ffmpegPath + "ffmpeg -i " + filepath + " -ab 56 -ar 22050 -qscale 8 -r 15 -s 600x500 " + outputPath);

        log.info("开始转换");
        ProcessBuilder processBuilder = new ProcessBuilder(command).redirectErrorStream(true);
        int value = 1;
        try {
            Process process = processBuilder.start();
            //如果没有，会导致线程阻塞
            new PrintStream(process.getErrorStream()).start();
            new PrintStream(process.getInputStream()).start();
            //0 成功 其他 失败
                value = process.waitFor();
            } catch (Exception e) {
                log.warn("ffmpeg convert video error", e);
            }
            log.info("成功与否标志=" + value);
            return value == 0 ? true : false;
    }


    /**
     * 获取 ffmpeg视频转码命令
     *
     * @param ffmpegPath
     * @param inputPath
     * @param outputPath
     * @return
     */
    public static List<String> getFfmpegCommand(String ffmpegPath, String inputPath, String outputPath) {
        List<String> command = new ArrayList<>();
        String ffmpeg = ffmpegPath.endsWith(".exe") ? ffmpegPath : ffmpegPath + File.separator + "ffmpeg";
        command.add(ffmpeg);
        command.add("-i");
        command.add(inputPath);
        //转码质量
        command.add("-r");
        command.add("15");
        command.add("-b:v");
        command.add("1500");
        command.add("-s");
        //计算分辨率,返回的map中包含 rotate，预留决定视频是否被旋转
        try {
            Map<String, String> map = cauSize(ffmpegVideoInfo(inputPath));
            if (log.isDebugEnabled()) {
                log.debug("VideoInfoMap="+map);
            }
            //command.add("720x480");
            command.add(map.get("width") + "x" + map.get("height"));
        } catch (Exception e) {
            log.error("获取视频信息出错:" + e);
        }
        command.add(outputPath);
        return command;
    }

    /**
     * 根据视频信息，计算最佳比例
     *
     * @param jsonObject
     * @return
     */
    public static Map<String, String> cauSize(JSONObject jsonObject) {

        if (log.isDebugEnabled()) {
            log.debug("jsonObject="+jsonObject);
        }

        String heightStr = jsonObject.getJSONArray("streams").getJSONObject(0).getString("height");
        String widthStr = jsonObject.getJSONArray("streams").getJSONObject(0).getString("width");
        JSONObject tags = jsonObject.getJSONArray("streams").getJSONObject(0).getJSONObject("tags");

        Map<String, String> map = new HashMap(3);
        if (tags.containsKey("rotate")) {
            map.put("rotate", tags.getString("rotate"));
        }

        map.put("height", heightStr.contains(".") ? heightStr.substring(0, heightStr.indexOf(".")) : heightStr);
        map.put("width", widthStr.contains(".") ? widthStr.substring(0, widthStr.indexOf(".")) : widthStr);
        int height = Integer.valueOf(heightStr);
        int width = Integer.valueOf(widthStr);
        if (width > height && width > 960) {
            int w = (int)(height / (width * 1.0 / 960)) / 2 * 2;
            String str = int2Str(w);
            map.put("width", str);
        } else if (width < height && height > 960) {
            int h = (int)(width / (height * 1.0 / 960)) / 2 * 2;
            String str = int2Str(h);
            map.put("height", str);
        }
        return map;
    }

    private static String int2Str(int num) {
        String str = String.valueOf(num);
        str = str.contains(".") ? str.substring(0, str.indexOf(".")) : str;
        return str;
    }


    /**
     * 获取视频信息
     *
     * @param srcVideoPath
     * @return
     */
    public static JSONObject ffmpegVideoInfo(String srcVideoPath) throws IOException, InterruptedException {
        String toolPath = ffmpegPath;
        List<String> command = new ArrayList<>();
        String ffmpeg = toolPath.endsWith(".exe") ? toolPath : toolPath + File.separator + "ffprobe";
        command.add(ffmpeg);
        command.add("-i");
        command.add(srcVideoPath);
        command.add("-v");
        command.add("quiet");
        command.add("-print_format");
        command.add("json");
        command.add("-show_format");
        command.add("-show_streams");
        if (log.isDebugEnabled()) {
            log.debug("获取视频信息命令行：" + command);
        }
        ProcessBuilder builder = new ProcessBuilder().command(command).redirectErrorStream(true);
        Process process = builder.start();
        //1.从输入流中读取视频信息
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(process.getInputStream()));
        StringBuffer buffer = new StringBuffer();
        String jsonStr = "";
        while ((jsonStr = bufferedReader.readLine()) != null) {
            buffer.append(jsonStr);
        }
        jsonStr = buffer.toString();
        process.waitFor();
        bufferedReader.close();
        //2.将字符串转化为json格式
        return JSONObject.parseObject(jsonStr);
    }


    /**
     * // 对ffmpeg无法解析的文件格式(wmv9，rm，rmvb等), 可以先用别的工具（mencoder）转换为avi(ffmpeg能解析的)格式.
     * private static String processAVI() {
     * List<String> commend = new ArrayList<>();
     * commend.add("mencoder");//工具安装路径
     * commend.add(inPath);
     * commend.add("-oac");
     * commend.add("lavc");
     * commend.add("-lavcopts");
     * commend.add("acodec=mp3:abitrate=64");
     * commend.add("-ovc");
     * commend.add("xvid");
     * commend.add("-xvidencopts");
     * commend.add("bitrate=600");
     * commend.add("-of");
     * commend.add("mp4");
     * commend.add("-o");
     * commend.add(outPath + "a.AVI");
     * try {
     * ProcessBuilder builder = new ProcessBuilder();
     * Process process = builder.command(commend).redirectErrorStream(true).start();
     * new PrintStream(process.getInputStream());
     * new PrintStream(process.getErrorStream());
     * process.waitFor();
     * return outPath + "a.AVI";
     * } catch (Exception e) {
     * e.printStackTrace();
     * return null;
     * }
     * }
     */

    public static void main(String[] args) {
        //   Boolean aBoolean = ffmpegConver("E:\\tool\\ffmpeg\\12format\\test.avi", "E:\\za\\" + UUID.randomUUID().toString().replace("-", "") + ".mp4");

        try {
            //JSONObject jsonObject = ffmpegVideoInfo("E:\\tool\\ffmpeg\\test.avi");
            JSONObject jsonObject = ffmpegVideoInfo("E:\\tool\\ffmpeg\\1500采样率.mp4");
            System.out.println(jsonObject);
            System.out.println(jsonObject.getJSONArray("streams"));
            System.out.println(jsonObject.getJSONArray("streams").getJSONObject(0));
            System.out.println(jsonObject.getJSONArray("streams").getJSONObject(0).getString("height"));
            System.out.println(jsonObject.getJSONArray("streams").getJSONObject(0).getString("width"));
            Map<String, String> map = cauSize(jsonObject);
            System.out.println(map);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }


}

class PrintStream extends Thread {

    private static Logger log = LoggerFactory.getLogger(PrintStream.class);

    java.io.InputStream __is;

    public PrintStream(java.io.InputStream is) {
        __is = is;
    }

    @Override
    public void run() {
        try {
            while (this != null) {
                int _ch = __is.read();
                if (_ch != -1) {
//                    log.debug((char) _ch + "");
                } else {
                    break;
                }
            }
        } catch (Exception e) {
            log.error("转码过程中出现未知异常");
            e.printStackTrace();
        }
    }

}

