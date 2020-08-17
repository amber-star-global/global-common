package com.gloabl.common.api;

/**
 * @Author: 鲁砚琨
 * @CreateTime: 2020-08-17 上午 11:26
 * @Version: v1.0
 */
public interface BaseCreateService<M> {


    Long create(M model);
}
