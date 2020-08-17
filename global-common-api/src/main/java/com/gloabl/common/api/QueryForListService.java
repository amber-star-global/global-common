package com.gloabl.common.api;

import java.util.List;

/**
 * @Author: 鲁砚琨
 * @CreateTime: 2020-08-17 上午 11:49
 * @Version: v1.0
 */
public interface QueryForListService<RES, REQ> {

    List<RES> queryForList(REQ req);
}
