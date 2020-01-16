package com.global.common.web;

import com.global.common.exception.BusinessException;
import com.global.common.exception.BusinessEnum;
import com.global.common.web.base.response.ResponseMessage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.support.MissingServletRequestPartException;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.util.Set;

/**
 * 全局异常统一处理类
 * @Author: 鲁砚琨
 * @CreateTime: 2019-12-11 下午 02:56
 * @Version: v1.0
 */
@Slf4j
@RestController
@ControllerAdvice
public class GlobalExceptionAdviceHandle {

    /**
     * 处理http媒体类型不支持异常
     */
    @ExceptionHandler(HttpMediaTypeNotSupportedException.class)
    public ResponseMessage httpMediaTypeNotSupportedExceptionHandle(HttpMediaTypeNotSupportedException e) {
        return failMessage(BusinessEnum.REQUEST_METHOD_NOT_SUPPORTED, e);
    }

    /**
     * 处理Http请求方式不支持异常
     */
    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public ResponseMessage httpRequestMethodNotSupportedExceptionHandle(HttpRequestMethodNotSupportedException e) {
        return failMessage(BusinessEnum.REQUEST_METHOD_NOT_SUPPORTED, e);
    }

    /**
     * 处理Http消息不能读取异常
     */
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseMessage handleHttpMessageNotReadableException(HttpMessageNotReadableException e) {
        return failMessage(BusinessEnum.REQUEST_METHOD_NOT_SUPPORTED, e);
    }

    /**
     * 处理请求缺少参数异常
     */
    @ExceptionHandler(MissingServletRequestParameterException.class)
    public ResponseMessage handleMissingServletRequestParameterException(MissingServletRequestParameterException e) {
        return failMessage(BusinessEnum.REQUEST_NOT_FOUND, e);
    }

    /**
     * 处理请求缺少参数异常
     */
    @ExceptionHandler(MissingServletRequestPartException.class)
    public ResponseMessage missingServletRequestPartExceptionHandle(MissingServletRequestPartException e) {
        return failMessage(BusinessEnum.REQUEST_NOT_FOUND, e);
    }

    /**
     * 处理接口单个参数校验
     */
    @ExceptionHandler(value = {ConstraintViolationException.class})
    public ResponseMessage constraintViolationExceptionHandle(ConstraintViolationException e) {
        log.error("捕获异常校验异常: {}", e.getMessage());
        BusinessEnum businessEnum = BusinessEnum.REQUEST_AUTH_PARAM_OUT_THRESHOLD;
        String symbol = ",";
        StringBuilder strBuilder = setValidHeaderInfo(businessEnum);
        Set<ConstraintViolation<?>> violations = e.getConstraintViolations();
        for (ConstraintViolation<?> violation : violations) {
            strBuilder.append(violation.getMessage()).append(symbol);
        }
        strBuilder.deleteCharAt(strBuilder.lastIndexOf(symbol));
        return ResponseMessage.fail(BusinessEnum.REQUEST_AUTH_PARAM_OUT_THRESHOLD, strBuilder.toString());
    }

    /**
     * 处理接口参数对象校验
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseMessage methodArgumentNotValidExceptionHandle(MethodArgumentNotValidException e) {
        log.error("捕获异常校验异常: {}", e.getMessage());
        BusinessEnum businessEnum = BusinessEnum.REQUEST_AUTH_PARAM_NOT_NULL;
        String symbol = "、";
        StringBuilder strBuilder = setValidHeaderInfo(businessEnum);
        BindingResult result = e.getBindingResult();
        if(result.hasErrors()){
            for (ObjectError error : result.getAllErrors()) {
                strBuilder.append(error.getDefaultMessage()).append(symbol);
            }
        }
        strBuilder.deleteCharAt(strBuilder.lastIndexOf(symbol));
        return ResponseMessage.fail(BusinessEnum.REQUEST_AUTH_PARAM_NOT_NULL, strBuilder.toString());
    }
    
    /**
     * 处理业务异常
     */
    @ExceptionHandler(BusinessException.class)
    public ResponseMessage businessExceptionHandle(BusinessException e) {
        log.error(e.getMessage(), e);
        return ResponseMessage.fail(e);
    }

    /**
     * 处理系统异常
     */
    @ExceptionHandler(value = Exception.class)
    public ResponseMessage defaultErrorHandler(Exception e) {
        return failMessage(BusinessEnum.FAIL, e);
    }


    /**
     * 设置校验参数返回信息前缀
     */
    private StringBuilder setValidHeaderInfo(BusinessEnum businessEnum) {
        StringBuilder strBuilder = new StringBuilder();
        strBuilder.append(businessEnum.getMessage()).append("：");
        return strBuilder;
    }

    /**
     * 失败消息处理
     * @param businessEnum 业务异常枚举
     * @param e 异常信息
     */
    private ResponseMessage failMessage(BusinessEnum businessEnum, Exception e) {
        log.error(e.getMessage(), e);
        return ResponseMessage.fail(businessEnum, e.getMessage());
    }

}
