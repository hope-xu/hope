package com.utils;

import com.dcits.gaaxy.base.exception.GalaxyException;
import jodd.util.ClassLoaderUtil;

import java.io.*;

/**
 * @author Hope
 * Date： 2020/12/01  9:34
 * 描述：流处理工具类
 */
public class StreamUtils {
    public static final int DEFAULT_CHUNK_SIZE = 1024;
    public static final int BUFFERSIZE = 4096;

    public StreamUtils() {
    }

    public static InputStream openStream(String resource) {
        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        InputStream stream = classLoader.getResourceAsStream(resource);
        if (stream == null) {
            try {
                stream = ClassLoaderUtil.getResourceAsStream(resource);
            } catch (IOException e) {
                stream = null;
            }
        }
        return stream;
    }

    public static InputStream getStreamFromString(String text){
        try{
            byte[] bytes = text.getBytes("UTF-8");
            return new ByteArrayInputStream(bytes);
        }catch (Exception e){
            throw new AssertionError(e);
        }
    }

    public static InputStream getStringFromFile(File file) throws Exception {
        FileInputStream stream = null;
        try {
            if (!file.exists()){
                throw new FileNotFoundException("file"+file+"doesn't exist");
            }else if (file.isDirectory()){
                throw new FileNotFoundException("是个目录");
            }else {
                stream = new FileInputStream(file);
                return stream;
            }
        }catch (Exception e){
            throw new GalaxyException("couldn't access file "+ file+":"+e.getMessage(),e);
        }

    }


}
