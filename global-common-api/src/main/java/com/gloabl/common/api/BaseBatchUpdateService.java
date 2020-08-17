package com.gloabl.common.api;

import java.util.List;

/**
 * @Author: 鲁砚琨
 * @CreateTime: 2020-08-17 上午 11:37
 * @Version: v1.0
 */
public interface BaseBatchUpdateService<M> {

    void batchUpdate(List<M> models);
}
