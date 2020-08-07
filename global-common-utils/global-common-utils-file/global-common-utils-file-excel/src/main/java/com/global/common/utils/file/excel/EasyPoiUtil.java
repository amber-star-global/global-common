package com.global.common.utils.file.excel;

import com.global.common.utils.file.excel.model.CascadeModel;
import com.google.common.collect.Maps;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.MapUtils;
import org.apache.poi.ss.usermodel.DataValidation;
import org.apache.poi.ss.usermodel.DataValidationConstraint;
import org.apache.poi.ss.usermodel.Name;
import org.apache.poi.ss.util.CellRangeAddressList;
import org.apache.poi.xssf.usermodel.XSSFDataValidation;
import org.apache.poi.xssf.usermodel.XSSFDataValidationConstraint;
import org.apache.poi.xssf.usermodel.XSSFDataValidationHelper;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @Author: 鲁砚琨
 * @CreateTime: 2020-07-01 下午 12:28
 * @Version: v1.0
 */
@Slf4j
public class EasyPoiUtil {

    public static void setCascade(XSSFWorkbook workbook, final String handleSheetName, final int validRow, final String[] offsets, final int col, final String cascadeSheetName, final List<CascadeModel> cascades) {
        List<String> firstKey = cascades.stream().map(CascadeModel::getName).collect(Collectors.toList());
        // 获取需要处理的sheet页
        setCascadeRegion(workbook, cascadeSheetName, firstKey, getCascadeMap(cascades));
        XSSFSheet handleSheet = workbook.getSheet(handleSheetName);
        setValidation(handleSheet, firstKey, validRow, col, col);

    }

    /**
     * 设置级联数据
     * @param workbook 工作簿对象
     * @param cascadeSheetName 配置级联的sheet页
     * @param cascadeMap 级联数据Map
     */
    public static void setCascadeRegion(XSSFWorkbook workbook, final String cascadeSheetName, final List<String> firstKey, final Map<String, List<String>> cascadeMap) {
        // 设置配置级联的sheet页
        XSSFSheet hideSheet = workbook.createSheet(cascadeSheetName);
        // 隐藏当前sheet页
        workbook.setSheetHidden(workbook.getSheetIndex(hideSheet), true);
        int lineNum = setPrentChildRelation(hideSheet, 0, cascadeSheetName, firstKey);
        for (String parent : cascadeMap.keySet()) {
            // 设置子菜单
            List<String> children = cascadeMap.get(parent);
            if (children != null && children.size() > 0) {
                lineNum = setPrentChildRelation(hideSheet, lineNum, parent, children);
                // 设置名称管理器(设置级联关系)
                log.debug("设置名称管理器, 当前行: {}, 父级名称: {}", lineNum, parent);
                String range = getRange(1, lineNum, children.size());
                setNameManagement(workbook, cascadeSheetName, parent, range);
            }
        }
    }

    /**
     * 设置父子关系
     * @param handleSheet 需要处理的sheet页
     * @param lineNum 处理的行数
     * @param parentName 父级名称
     * @param childNames 子集名称
     */
    private static int setPrentChildRelation(XSSFSheet handleSheet, int lineNum, String parentName, List<String> childNames) {
        XSSFRow handleRow = handleSheet.createRow(lineNum++);
        handleRow.createCell(0).setCellValue(parentName);
        for (int i = 0; i < childNames.size(); i++) {
            handleRow.createCell(i + 1).setCellValue(childNames.get(i));
        }
        return lineNum;
    }

    /**
     * 获取级联关系Map
     * @param cascades 级联对象集合
     */
    private static Map<String, List<String>> getCascadeMap(final List<CascadeModel> cascades) {
        Map<String, List<String>> cascadeMap = Maps.newHashMap();
        cascades.forEach(cascade -> {
            if (CollectionUtils.isNotEmpty(cascade.getChildModels())) {
                cascadeMap.put(cascade.getName(), cascade.getChildModels().stream().map(CascadeModel::getName).collect(Collectors.toList()));
                Map<String, List<String>> childMap = getCascadeMap(cascade.getChildModels());
                if (MapUtils.isNotEmpty(childMap)) {
                    cascadeMap.putAll(childMap);
                }
            }
        });
        return cascadeMap;
    }

    /**
     * 设置名称管理器
     * @param workbook 工作簿对象
     * @param handleSheetName 需要处理的sheet页名称
     * @param parent 设置名称管理器名称
     * @param range 设置名称管理器区间
     */
    private static void setNameManagement(XSSFWorkbook workbook, String handleSheetName, String parent, String range) {
        Name name = workbook.createName();
        // 设置父类Key, 不能重复
        name.setNameName(parent);
        String formula = String.format("%s!%s", handleSheetName, range);
        log.debug("设置名称管理器, 级联关系: {}", formula);
        name.setRefersToFormula(formula);
    }

    /**
     * @param offset 偏移量, 如果给0, 表示从A列开始, 1是从B列开始
     * @param rowId 从第几行计算
     * @param colCount 子集的size
     */
    public static String getRange(int offset, int rowId, int colCount) {
        char start = (char) ('A' + offset);
        if (colCount <= 25) {
            char end = (char) (start + colCount - 1);
            return "$" + start + "$" + rowId + ":$" + end + "$" + rowId;
        } else {
            char endPrefix = 'A';
            char endSuffix;
            if ((colCount - 25) / 26 == 0 || colCount == 51) {// 26-51之间，包括边界（仅两次字母表计算）
                if ((colCount - 25) % 26 == 0) {// 边界值
                    endSuffix = (char) ('A' + 25);
                } else {
                    endSuffix = (char) ('A' + (colCount - 25) % 26 - 1);
                }
            } else {// 51以上
                if ((colCount - 25) % 26 == 0) {
                    endSuffix = (char) ('A' + 25);
                    endPrefix = (char) (endPrefix + (colCount - 25) / 26 - 1);
                } else {
                    endSuffix = (char) ('A' + (colCount - 25) % 26 - 1);
                    endPrefix = (char) (endPrefix + (colCount - 25) / 26);
                }
            }
            return "$" + start + "$" + rowId + ":$" + endPrefix + endSuffix + "$" + rowId;
        }
    }

    /**
     * 设置校验
     * @param sheet sheet页
     * @param parent 父级名称
     */
    public static void setValidation(XSSFSheet sheet, List<String> parent, int size, int firstCol, int lastCol) {
        log.debug("设置校验规则, 终止行: {}, 起始列: {}, 终止列: {}", size, firstCol, lastCol);
        XSSFDataValidationHelper dvHelper = new XSSFDataValidationHelper(sheet);
        // 省规则
        DataValidationConstraint provConstraint = dvHelper.createExplicitListConstraint(parent.toArray(new String[]{}));
        // 四个参数分别是：起始行、终止行、起始列、终止列
        CellRangeAddressList provRangeAddressList = new CellRangeAddressList(1, size, firstCol, lastCol);
        DataValidation provinceDataValidation = dvHelper.createValidation(provConstraint, provRangeAddressList);
        //验证
        provinceDataValidation.createErrorBox("error", "请选择正确的配置信息");
        provinceDataValidation.setShowErrorBox(true);
        provinceDataValidation.setSuppressDropDownArrow(true);
        sheet.addValidationData(provinceDataValidation);
    }


    /**
     * 设置联动
     * @param offset 起始列
     * @param sheet sheet页
     * @param rowNum 行数
     * @param colNum 列数
     */
    public static void setDataValidation(String offset, XSSFSheet sheet, int rowNum,int colNum) {
        log.debug("设置联动, 列: {}, 设置处理的行数: {}, 列数: {}", offset, rowNum, colNum);
        XSSFDataValidationHelper dvHelper = new XSSFDataValidationHelper(sheet);
        sheet.addValidationData(getDataValidationByFormula("INDIRECT($" + offset + rowNum + ")", rowNum, colNum, dvHelper));
    }

    /**
     * 加载下拉列表内容
     * @param formulaString
     * @param naturalRowIndex
     * @param naturalColumnIndex
     * @param dvHelper
     * @return
     */
    private static DataValidation getDataValidationByFormula(String formulaString, int naturalRowIndex, int naturalColumnIndex, XSSFDataValidationHelper dvHelper) {
        // 加载下拉列表内容
        // 举例：若formulaString = "INDIRECT($A$2)" 表示规则数据会从名称管理器中获取key与单元格 A2 值相同的数据，
        XSSFDataValidationConstraint dvConstraint = (XSSFDataValidationConstraint) dvHelper.createFormulaListConstraint(formulaString);
        // 设置数据有效性加载在哪个单元格上。
        // 四个参数分别是：起始行、终止行、起始列、终止列
        int firstRow = naturalRowIndex - 1;
        int lastRow = naturalRowIndex - 1;
        int firstCol = naturalColumnIndex - 1;
        int lastCol = naturalColumnIndex - 1;
        log.debug("加载下拉列表内容, formula: {}, 起始数: {}, 起始列数: {}, 终止行: {}, 终止列: {}", formulaString, firstRow, firstCol, lastRow, lastCol);
        CellRangeAddressList regions = new CellRangeAddressList(firstRow, lastRow, firstCol, lastCol);
        // 数据有效性对象
        // 绑定
        XSSFDataValidation dataValidationList = (XSSFDataValidation) dvHelper.createValidation(dvConstraint, regions);
        dataValidationList.setEmptyCellAllowed(false);
        dataValidationList.setSuppressDropDownArrow(true);
        dataValidationList.setShowErrorBox(true);
        // 设置输入信息提示信息
        dataValidationList.createPromptBox("下拉选择提示", "请使用下拉方式选择正确的值！");
        return dataValidationList;
    }

}