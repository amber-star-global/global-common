package com.gloabl.common.api;

import com.github.pagehelper.PageInfo;
import com.gloabl.common.model.PageApiModel;

/**
 * @Author: 鲁砚琨
 * @CreateTime: 2020-08-17 上午 11:30
 * @Version: v1.0
 */
public interface QueryForPageService<RES, REQ extends PageApiModel> {

    PageInfo<RES> queryForPage(REQ req);
}
