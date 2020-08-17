package com.gloabl.common.api;

import java.util.List;

/**
 * @Author: 鲁砚琨
 * @CreateTime: 2020-08-17 上午 11:35
 * @Version: v1.0
 */
public interface BaseBatchCreateService<M> {

    void batchCreate(List<M> models);
}
