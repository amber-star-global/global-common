package com.global.common.web.idempotent;

import com.global.common.exception.BusinessEnum;
import com.global.common.exception.BusinessException;
import com.global.common.web.utils.contants.GlobalWebHeaderKey;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.util.Objects;

/**
 * @Author: 鲁砚琨
 * @CreateTime: 2020-03-31 下午 06:20
 * @Version: v1.0
 */
@Slf4j
@Aspect
@Component
public class IdemRequestAspect {

    @Autowired
    private IdempotentUtil idempotentUtil;

    @Autowired
    private HttpServletRequest request;

    @Before(value = "@annotation(com.global.common.web.idempotent.annotation.IdemRequest)")
    public void before() throws BusinessException {
        log.info("进行幂等校验处理...");
        // 获取请求头token信息
        String tokenValue = request.getHeader(GlobalWebHeaderKey.REQUEST_IDEM_TOKEN);
        if (Objects.isNull(tokenValue)) {
            log.error("没有获取到幂等token值!");
          throw new BusinessException(BusinessEnum.REQUEST_IDEMPOTENT_NOT_NULL);
        }
        idempotentUtil.idempotentHandle(tokenValue);
    }

}
