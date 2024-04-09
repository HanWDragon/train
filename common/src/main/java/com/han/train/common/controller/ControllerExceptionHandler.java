package com.han.train.common.controller;

import cn.hutool.core.util.StrUtil;
import com.han.train.common.exception.BusinessException;
import com.han.train.common.response.CommonResp;
import io.seata.core.context.RootContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 统一异常处理、数据预处理等
 */
@ControllerAdvice
public class ControllerExceptionHandler {

    private static final Logger LOG = LoggerFactory.getLogger(ControllerExceptionHandler.class);

    /**
     * 所有异常统一处理
     * @param e
     * @return
     */
    @ExceptionHandler(value = Exception.class)
    @ResponseBody
    public CommonResp<?> exceptionHandler(Exception e) throws Exception {
        LOG.info("seata全局事务ID: {}", RootContext.getXID());
        // 如果是在一次全局事务里出异常了，就不要包装返回值，将异常抛给调用方，让调用方回滚事务
//        但是返回码依然是200，以为执行成功，我们要返回失败的结果，而不是处理过结果
        if (StrUtil.isNotBlank(RootContext.getXID())) {
            throw e;
        }
        CommonResp<?> commonResp = new CommonResp<>();
        LOG.error("系统异常：", e);
        commonResp.setSuccess(false);
        commonResp.setMessage("系统出现异常，请联系管理员");
        return commonResp;
    }

    /**
     * 业务异常统一处理
     * @param e
     * @return
     */
    @ExceptionHandler(value = BusinessException.class)
    @ResponseBody
    public CommonResp<?> exceptionHandler(BusinessException e) {
        CommonResp<?> commonResp = new CommonResp<>();
        LOG.error("业务异常：{}", e.getE().getDesc());
        commonResp.setSuccess(false);
        commonResp.setMessage(e.getE().getDesc());
        return commonResp;
    }


    /**
     * 校验异常统一处理
     * @param e
     * @return
     */
    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    @ResponseBody
    public CommonResp<?> exceptionHandler(MethodArgumentNotValidException e) {
        CommonResp<?> commonResp = new CommonResp<>();
        LOG.error("校验异常：{}", e.getBindingResult().getAllErrors().get(0).getDefaultMessage());
        commonResp.setSuccess(false);
//      这里图简单只返回第一个错误，你也可以写返回所有的错误，但是通常查日志就能解决问题。
        commonResp.setMessage(e.getBindingResult().getAllErrors().get(0).getDefaultMessage());
        return commonResp;
    }


}
