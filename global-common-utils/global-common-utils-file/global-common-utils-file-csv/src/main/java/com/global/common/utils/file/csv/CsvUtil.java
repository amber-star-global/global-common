package com.global.common.utils.file.csv;

import com.csvreader.CsvWriter;
import com.global.common.utils.file.FileUtil;
import com.global.common.utils.file.csv.annotations.CsvColumn;
import lombok.extern.slf4j.Slf4j;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.lang.reflect.Field;
import java.nio.charset.Charset;
import java.util.List;

/**
 * @Description: cvs文件工具类
 * @Author: 鲁砚琨
 * @Date: 2019/9/6 11:27
 * @Version: v1.0
 */
@Slf4j
public class CsvUtil extends FileUtil {

    private static final String FILE_SUFFIX = ".csv";
    private static final String CHARSET_UTF8 = "UTF-8";

    /**
     * 数据内容写入csv文件
     * @param filePath 文件路径
     * @param fileName 文件名称
     * @param dataList 文件数据
     * @param charset 设置文件字符集
     */
    public static <T> void write(String filePath, String fileName, List<T> dataList, Class<T> clazz, String charset) throws Exception {
        write(filePath, fileName, dataList, clazz, charset, false);
    }

    /**
     * 追加数据的方式写入csv文件
     * @param filePath 文件路径
     * @param fileName 文件名称
     * @param dataList 文件数据
     * @param charset 设置文件字符集
     */
    public static <T> void appendWrite(String filePath, String fileName, List<T> dataList, Class<T> clazz, String charset) throws Exception {
        write(filePath, fileName, dataList, clazz, charset, true);
    }

    /**
     * 数据内容写入csv文件
     * @param filePath 文件路径
     * @param fileName 文件名称
     * @param dataList 文件数据
     */
    public static <T> void write(String filePath, String fileName, List<T> dataList, Class<T> clazz) throws Exception {
        write(filePath, fileName, dataList, clazz, CHARSET_UTF8, false);
    }

    /**
     * 追加数据的方式写入csv文件
     * @param filePath 文件路径
     * @param fileName 文件名称
     * @param dataList 文件数据
     */
    public static <T> void appendWrite(String filePath, String fileName, List<T> dataList, Class<T> clazz) throws Exception {
        write(filePath, fileName, dataList, clazz, CHARSET_UTF8, true);
    }

    /**
     * 删除csv文件
     * @param filePath 文件路径
     * @param fileName 文件名称
     */
    public static void deleteCsvFile(String filePath, String fileName) throws IOException {
        deleteFile(filePath, fileName, FILE_SUFFIX);
    }

    /**
     * 获取csv文件, 没有则创建
     * @param filePath 文件路径
     * @param fileName 文件名称
     */
    public static File getCsvFile(String filePath, String fileName) throws IOException {
        return getFile(filePath, fileName, FILE_SUFFIX);
    }

    /**
     * 获取csv文件流数据
     * @param filePath 文件路径
     * @param fileName 文件名称
     */
    public static byte[] getCsvFileStreatm(String filePath, String fileName) throws Exception {
        return getFileStream(filePath, fileName, FILE_SUFFIX);
    }

    /**
     * 获取文件
     * @param file 文件对象
     */
    private static CsvWriter getCsvWriter(File file, String charset, boolean flag) throws IOException {
        Charset cs;
        try {
            cs = Charset.forName(charset);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            cs = Charset.forName(CHARSET_UTF8);
        }
        OutputStream outputStream = getFileOutputStream(file, flag);
        Long fileLineNumber = getFileLineNumber(file);
        if (fileLineNumber == 1) {
            outputStream.write(new byte[] { (byte) 0xEF, (byte) 0xBB,(byte) 0xBF});
        }
        return new CsvWriter(outputStream, ',', cs);
    }

    /**
     *
     * @param filePath 文件路径
     * @param fileName 文件名称
     * @param dataList 文件数据
     * @param charset 设置文件字符集
     * @param flag 是否追加数据
     */
    private static <T> void write(String filePath, String fileName, List<T> dataList, Class<T> clazz, String charset, boolean flag) throws Exception {
        // 获取CSV文件
        File csvFile = getCsvFile(filePath, fileName);
        CsvWriter csvWriter = getCsvWriter(csvFile, charset, flag);
        // 获取文件行数
        Long lineNumber = getFileLineNumber(csvFile);
        // 只有为1的时候需要增加头信息
        if (lineNumber == 1) {
            writeCsvHeader(csvWriter, clazz);
        }
        // 设置文件内用
        writeCsvContent(csvWriter, dataList);
        // 刷新文件
        csvWriter.flush();
        // 关闭文件流
        csvWriter.close();
    }

    /**
     * 设置csv文件头信息
     * @param csvWriter csv文件
     * @param clazz 设置头信息对象的class文件
     */
    private static <T> void writeCsvHeader(CsvWriter csvWriter, Class<T> clazz) throws IOException {
        if (clazz == null) {
            return;
        }
        Field[] fields = clazz.getDeclaredFields();
        String[] headerName = new String[fields.length];
        for (int i = 0; i < fields.length; i++) {
            Field field = fields[i];
            CsvColumn csvColumn = field.getAnnotation(CsvColumn.class);
            // 没有定义注解, 或者ignore设置为true时, 忽略对当前字段处理
            if (csvColumn != null && !csvColumn.ignore()) {
                headerName[i] = csvColumn.name();
            }
        }
        // 写入文件头信息
        csvWriter.writeRecord(headerName, true);
        csvWriter.flush();
    }

    /**
     * 写入文件内容
     * @param csvWriter csv文件
     * @param dataList 需要写入的数据内容
     */
    private static  <T> void writeCsvContent(CsvWriter csvWriter, List<T> dataList) throws Exception {
        if (dataList != null && dataList.size() > 0) {
            for (T data : dataList) {
                String[] dataArray = getDataArray(data);
                if (dataArray != null && dataArray.length > 0) {
                    csvWriter.writeRecord(dataArray, true);
                }
            }
        }
    }

    /**
     * 获取写入内容数据
     * @param <T> 把文件数据转换数组
     */
    private static <T> String[] getDataArray(T data) throws IllegalAccessException {
        if (data == null) {
            return null;
        }
        Field[] fields = data.getClass().getDeclaredFields();
        String[] dataArray = new String[fields.length];
        for (int i = 0; i < fields.length; i++) {
            Field field = fields[i];
            CsvColumn csvColumn = field.getAnnotation(CsvColumn.class);
            // 没有定义注解, 或者ignore设置为true时, 忽略对当前字段处理
            if (csvColumn != null && !csvColumn.ignore()) {
                field.setAccessible(true);
                Object value = field.get(data);
                dataArray[i] = String.valueOf(value);
            }
        }
        return dataArray;
    }
}
