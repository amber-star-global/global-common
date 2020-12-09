package com.global.common.utils.file.excel;

import cn.afterturn.easypoi.handler.inter.IExcelExportServer;
import com.google.common.collect.Lists;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;

import java.util.List;

/**
 * 默认的处理导出数据处理类
 * @Author: 鲁砚琨
 * @CreateTime: 2020-11-11 下午 07:22
 * @Version: v1.0
 */
@Slf4j
public class DefaultExcelExportServiceImpl implements IExcelExportServer {

    private final int handleCount = 5000;

    private List<Object> modelList;

    DefaultExcelExportServiceImpl(List<Object> modelList) {
        this.modelList = modelList;
    }

    /**
     * 查询数据接口
     *
     * @param queryParams 查询条件
     * @param page        当前页数从1开始
     */
    @Override
    public List<Object> selectListForExcelExport(Object queryParams, int page) {
        if (CollectionUtils.isNotEmpty(modelList)) {
            List<Object> currentList = Lists.newArrayList();
            log.debug("当前总数据大小: {}", modelList.size());
            if (modelList.size() >= handleCount) {
                List<Object> ms = modelList.subList(0, handleCount);
                currentList.addAll(ms);
                log.debug("本次获取数据大小: {}", currentList.size());
                ms.clear();
                log.debug("删除本次获取数据后总数据大小: {}", modelList.size());
            } else {
                currentList.addAll(modelList);
                log.debug("本次获取数据大小: {}", currentList.size());
                modelList.clear();
            }
            return currentList;
        }
        return null;
    }
}
