package com.utils;

import org.apache.commons.compress.archivers.ArchiveException;
import org.apache.commons.compress.archivers.ArchiveInputStream;
import org.apache.commons.compress.archivers.ArchiveStreamFactory;
import org.apache.commons.compress.archivers.tar.TarArchiveEntry;

import java.io.*;
import java.util.zip.GZIPInputStream;

/**
 * @author Hope
 * Date： 2020/10/28  15:06
 * 描述：
 */
public class FileUtils {

    public void forUzipFile(String[] fileNames) {
        String zipDir = "/app/file/XXW";
        String ulId = "XXW";
        String tempDir = "/app/file/XXW/tmp";
        for (int i = 0; i < 3; i++) {
            File glFile = new File(zipDir, fileNames[i]);
            //tar.gz后缀
            if (glFile.exists() && glFile.getName().endsWith(".tar.gz")) {
                unZipOarFile(ulId, tempDir, glFile.getAbsolutePath(), "20200810");
            }
        }

    }

    /**
     * @param ulId
     * @param tempDir
     * @param zipFilePath
     * @param date
     */
    protected void unZipOarFile(String ulId, String tempDir, String zipFilePath, String date) {
        FileInputStream fis = null;
        ArchiveInputStream in = null;
        BufferedInputStream bufferedInputStream = null;
        try {
            fis = new FileInputStream(zipFilePath);
            GZIPInputStream is = new GZIPInputStream(new BufferedInputStream(fis));
            in = new ArchiveStreamFactory().createArchiveInputStream("tar", is);
            bufferedInputStream = new BufferedInputStream(in);
            TarArchiveEntry entry = (TarArchiveEntry) in.getNextEntry();
            while (entry != null) {
                String name = entry.getName();
                String[] names = name.split("/");
                String fileName = tempDir;
                for (int i = 0; i < names.length; i++) {
                    String str = names[i];
                    fileName = fileName + File.separator + str;
                }
                if (name.endsWith("/")) {
                    File f = new File(fileName);
                    if (!f.exists()) {
                        f.mkdir();
                    }
                } else {
                    File file = new File(fileName);
                    if (file.exists()) {
                        file.delete();
                    } else {
                        file.getParentFile().mkdirs();
                    }
                    BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(new FileOutputStream(file));
                    int b;
                    while ((b = bufferedInputStream.read()) != -1) {
                        bufferedOutputStream.write(b);
                    }
                    bufferedOutputStream.flush();
                    bufferedOutputStream.close();
                }
                entry = (TarArchiveEntry) in.getNextEntry();
            }
        } catch (FileNotFoundException e) {

        } catch (IOException e) {

        } catch (ArchiveException e) {

        } finally {
            try {
                if (bufferedInputStream != null) {
                    bufferedInputStream.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
