package com.global.common.utils.file.excel;

import cn.afterturn.easypoi.excel.annotation.Excel;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.monitorjbl.xlsx.StreamingReader;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * @Author: 鲁砚琨
 * @CreateTime: 2020-11-11 下午 06:25
 * @Version: v1.0
 */
@Slf4j
public class ExcelImportBigDataUtil {


    /**
     * 每次放入缓冲区最大行数
     */
    private static final int MAX_ROW_NUM = 10000;

    /**
     * 缓冲区大小
     */
    private static final int BUFFER_SIZE = 4096;

    /**
     * 通过文件路径获取excel数据
     *
     * @param filePath  文件路径
     * @param dataClass 转换的对象
     */
    public static <E> List<E> importData(String filePath, Class<E> dataClass) {
        return importData(filePath, dataClass, false);
    }

    /**
     * 通过文件路径获取excel数据
     *
     * @param filePath  文件路径
     * @param dataClass 转换的对象
     */
    public static <E> List<E> importDataForMore(String filePath, Class<E> dataClass) {
        return importData(filePath, dataClass, true);
    }

    /**
     * 通过输入流获取excel数据
     *
     * @param inputStream 输入流
     * @param dataClass   转换的对象
     */
    public static <E> List<E> importData(InputStream inputStream, Class<E> dataClass) {
        return importData(inputStream, dataClass, false);
    }

    /**
     * 通过输入流获取excel数据
     *
     * @param inputStream 输入流
     * @param dataClass   转换的对象
     */
    public static <E> List<E> importDataForMore(InputStream inputStream, Class<E> dataClass) {
        return importData(inputStream, dataClass, true);
    }

    /**
     * 通过文件路径获取excel数据
     *
     * @param filePath  文件路径
     * @param dataClass 转换的对象
     * @param moreSheet 是否是多sheet页
     */
    private static <E> List<E> importData(String filePath, Class<E> dataClass, boolean moreSheet) {
        FileInputStream inputStream = null;
        try {
            if (Objects.nonNull(filePath)) {
                inputStream = new FileInputStream(filePath);
                return importData(inputStream, dataClass, moreSheet);
            }
        } catch (FileNotFoundException e) {
            log.error(e.getMessage(), e);
        } finally {
            try {
                if (Objects.nonNull(inputStream)) {
                    inputStream.close();
                }
            } catch (IOException e) {
                log.error(e.getMessage(), e);
            }
        }
        return null;
    }

    /**
     * 通过输入流获取excel数据
     *
     * @param inputStream 文件路径
     * @param dataClass   转换的对象
     * @param moreSheet   是否是多sheet页
     */
    private static <E> List<E> importData(InputStream inputStream, Class<E> dataClass, boolean moreSheet) {
        Workbook workbook = null;
        try {
            if (Objects.nonNull(inputStream)) {
                // 通过缓冲流导入数据
                workbook = StreamingReader.builder().rowCacheSize(MAX_ROW_NUM).bufferSize(BUFFER_SIZE).open(inputStream);
                return getSheetData(workbook, dataClass, moreSheet);
            }
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        } finally {
            try {
                if (Objects.nonNull(inputStream)) {
                    inputStream.close();
                }
            } catch (IOException e) {
                log.error(e.getMessage(), e);
            }
            try {
                if (Objects.nonNull(workbook)) {
                    workbook.close();
                }
            } catch (IOException e) {
                log.error(e.getMessage(), e);
            }
        }
        return null;
    }

    /**
     * 获取sheet页数据
     *
     * @param workbook  工作簿对象
     * @param dataClass 转换的对象
     * @param moreSheet 是否是多sheet页
     */
    private static <E> List<E> getSheetData(Workbook workbook, Class<E> dataClass, boolean moreSheet) {
        List<E> dataList = Lists.newArrayList();
        for (Sheet sheet : workbook) {
            log.debug("当前sheet页名称: {}", sheet.getSheetName());
            dataList.addAll(getDataList(sheet, dataClass));
            if (moreSheet)
                break;
        }
        return dataList;
    }

    /**
     * 获取当前Sheet页的数据
     *
     * @param sheet     sheet页对象
     * @param dataClass 转换的对象
     */
    private static <E> List<E> getDataList(Sheet sheet, Class<E> dataClass) {
        List<String> headList = Lists.newArrayList();
        List<E> dataList = Lists.newArrayList();
        int count = 1;
        for (Row row : sheet) {
            log.debug("当前行数: {}", count);
            if (count == 1) {
                headList.addAll(getHeadList(row));
            } else {
                dataList.add(getFileData(row, headList, dataClass));
            }
            count++;
        }
        return dataList;
    }

    /**
     * 获取定义的表头集合
     *
     * @param row 当前excel行对象
     */
    private static List<String> getHeadList(Row row) {
        ArrayList<String> headList = Lists.newArrayList();
        for (Cell cell : row) {
            headList.add(cell.getStringCellValue());
        }
        return headList;
    }

    /**
     * 获取行数据
     *
     * @param row       当前excel行对象
     * @param headList  定义的表头集合
     * @param fileClass 转换的对象
     */
    private static <E> E getFileData(Row row, List<String> headList, Class<E> fileClass) {
        Map<String, Object> dataMap = getDataMap(headList, row);
        E data = null;
        try {
            data = fileClass.newInstance();
            Field[] fields = getAllField(data);
            for (Field field : fields) {
                // 获取转换对象的Excel注解, 通过注解的name属性获取对应的数据
                Excel excel = field.getAnnotation(Excel.class);
                if (excel == null) {
                    continue;
                }
                setValue(data, field, dataMap.get(excel.name()));
            }
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
        return data;
    }

    /**
     * 获取数据与标题对应的值Map
     *
     * @param headList 表头集合
     * @param row      当前行对象
     */
    private static Map<String, Object> getDataMap(List<String> headList, Row row) {
        Map<String, Object> dataMap = Maps.newHashMap();
        for (int i = 0; i < headList.size(); i++) {
            Object value = null;
            Cell cell = row.getCell(i);
            if (Objects.nonNull(cell)) {
                CellType cellType = cell.getCellType();
                if (Objects.nonNull(cellType)) {
                    switch (cellType) {
                        case STRING:
                            value = cell.getStringCellValue();
                            break;
                        case NUMERIC:
                            value = cell.getNumericCellValue();
                            break;
                        case FORMULA:
                            value = cell.getCellFormula();
                            break;
                        case BOOLEAN:
                            value = cell.getBooleanCellValue();
                            break;
                    }
                    dataMap.put(headList.get(i), value);
                }
            }
        }
        return dataMap;
    }

    /**
     * 获取当前对象的全部字段, 包括继承的对象字段
     *
     * @param object 转换对象
     */
    private static Field[] getAllField(final Object object) {
        Class<?> objectClass = object.getClass();
        Field[] currentFields = {};
        do {
            final Field[] fields = objectClass.getDeclaredFields();
            currentFields = arrayMerge(fields, currentFields);
            objectClass = objectClass.getSuperclass();
        } while (objectClass != Object.class);
        return currentFields;
    }

    private static <T> T[] arrayMerge(final T[] sourceArr, final T[] targetArr) {
        final int curSouArrLen = sourceArr.length;
        final int curTarArrLen = targetArr.length;
        T[] newTarArr = Arrays.copyOf(targetArr, (curSouArrLen + curTarArrLen));
        System.arraycopy(sourceArr, 0, newTarArr, curTarArrLen, curSouArrLen);
        return newTarArr;
    }

    /**
     * 设置值, 对excel的值做转换
     *
     * @param data  转换的对象实例
     * @param field 转换的对象当前字段
     * @param value 赋值对象
     */
    private static <E> void setValue(E data, Field field, Object value) {
        if (value == null) {
            return;
        }
        try {
            field.setAccessible(true);
            Class<?> type = field.getType();
            if (type.isAssignableFrom(BigDecimal.class)) {
                String valueStr = value.toString();
                if (StringUtils.isNotBlank(valueStr)) {
                    field.set(data, new BigDecimal(valueStr));
                }
            } else if (type.isAssignableFrom(Long.class)) {
                String valueStr = value.toString();
                if (StringUtils.isNotBlank(valueStr)) {
                    field.set(data, Long.parseLong(value.toString()));
                }
            } else if (type.isAssignableFrom(Integer.class)) {
                String valueStr = value.toString();
                if (StringUtils.isNotBlank(valueStr)) {
                    field.set(data, Integer.parseInt(value.toString()));
                }
            } else if (type.isAssignableFrom(String.class)) {
                if (value instanceof Double) {
                    value = String.valueOf(((Double) value).longValue());
                }
                field.set(data, value);
            }
        } catch (IllegalAccessException e) {
            log.error(e.getMessage(), e);
        }
    }
}
