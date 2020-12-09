package com.global.common.utils.file.excel;

import cn.afterturn.easypoi.excel.ExcelExportUtil;
import cn.afterturn.easypoi.excel.entity.ExportParams;
import cn.afterturn.easypoi.excel.entity.enmus.ExcelType;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.usermodel.Workbook;

import java.util.List;

/**
 * @Author: 鲁砚琨
 * @CreateTime: 2020-11-11 下午 06:25
 * @Version: v1.0
 */
@Slf4j
public class ExcelExportBigDataUtil {

    /**
     * 大数据量导出excel文件
     * @param fileName 文件名称
     * @param dataList 转换的数据
     * @param dataClass 转换的数据类型
     */
    public static Workbook exportExcel(String fileName, List<Object> dataList, Class<?> dataClass) {
        ExportParams params = new ExportParams(null, fileName, ExcelType.XSSF);
        log.debug("转换成excel文件, 开始时间: {}", System.currentTimeMillis());
        Workbook workbook = ExcelExportUtil.exportBigExcel(params, dataClass, new DefaultExcelExportServiceImpl(dataList), null);
        log.debug("转换成excel文件, 结束时间: {}", System.currentTimeMillis());
        return workbook;
    }
}
