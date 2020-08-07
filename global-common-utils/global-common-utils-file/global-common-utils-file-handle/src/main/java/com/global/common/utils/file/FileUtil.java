package com.global.common.utils.file;

import lombok.extern.slf4j.Slf4j;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.LineNumberReader;
import java.io.OutputStream;

/**
 * @Description:
 * @Author: 鲁砚琨
 * @Date: 2019/9/6 14:49
 * @Version: v1.0
 */
@Slf4j
public class FileUtil {


    /**
     * 获取文件路径, 如果不存在创建路径
     * @param filePath 文件路径
     */
    public static File getDirectory(String filePath) throws IOException {
        File file = new File(filePath);
        if (!exists(file)) {
            if (!file.mkdirs()) {
                throw new IOException("文件路径: " + filePath + "不能创建!");
            }
        }
        return file;
    }

    /**
     * 获取文件
     * @param filePath 文件路径
     * @param fileName 文件名称
     * @param suffix 文件后缀名
     */
    public static File getFile(String filePath, String fileName, String suffix) throws IOException {
        String path = filePath + fileName + suffix;
        File file = new File(path);
        if (!exists(file)) {
            getDirectory(filePath);
            if (!file.createNewFile()) {
                throw new IOException("创建文件失败!");
            }
        }
        return file;
    }

    /**
     * 获取Byte文件流
     * @param filePath 文件路径
     * @param fileName 文件名称
     * @param suffix 文件后缀名
     */
    public static byte[] getFileStream(String filePath, String fileName, String suffix) throws Exception {
        String path = filePath + fileName + suffix;
        File file = new File(path);
        FileInputStream inputStream = new FileInputStream(file);
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        byte[] temp = new byte[1024];
        int i;
        while ((i = inputStream.read(temp)) != -1) {
            byteArrayOutputStream.write(temp, 0, i);
        }
        inputStream.close();
        byteArrayOutputStream.close();
        return byteArrayOutputStream.toByteArray();
    }

    /**
     * 判断文件是否存在
     * @param filePath 文件路径
     * @param fileName 文件名称
     * @param suffix 文件后缀名
     */
    public static boolean exists(String filePath, String fileName, String suffix) {
        String path = filePath + fileName + suffix;
        return new File(path).exists();
    }

    /**
     * 判断文件是否存在
     * @param file 文件
     */
    protected static boolean exists(File file) {
        return file.exists();
    }

    /**
     * 删除文件
     * @param filePath 文件路径
     * @param fileName 文件名称
     * @param suffix 文件后缀名
     */
    protected static void deleteFile(String filePath, String fileName, String suffix) throws IOException {
        String path = filePath + fileName + suffix;
        File file = new File(path);
        if (exists(file) && !file.delete()) {
            throw new IOException("删除文件失败!");
        }
    }

    /**
     * 获取文件流
     * @param file 文件对象
     */
    protected static OutputStream getFileOutputStream(File file, boolean flag) throws FileNotFoundException {
        return new FileOutputStream(file, flag);
    }

    /**
     * 获取文件行数
     * @param file file文件
     * @return
     */
    public static Long getFileLineNumber(File file) {
        try {
            FileReader fileReader = new FileReader(file);
            LineNumberReader lineNumberReader = new LineNumberReader(fileReader);
            lineNumberReader.skip(Long.MAX_VALUE);
            long lines = lineNumberReader.getLineNumber() + 1;
            fileReader.close();
            lineNumberReader.close();
            return lines;
        } catch (IOException e) {
            return -1L;
        }
    }
}
