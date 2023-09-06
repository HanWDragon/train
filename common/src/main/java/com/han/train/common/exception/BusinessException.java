package com.han.train.common.exception;

/**
 * @author Han
 * @date 2023年09月06日 10:24
 */
public class BusinessException extends RuntimeException{

    private BusinessExceptionEnum e;

    public BusinessException(BusinessExceptionEnum e) {
        this.e = e;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("BusinessException{");
        sb.append("e=").append(e);
        sb.append('}');
        return sb.toString();
    }

    public BusinessExceptionEnum getE() {
        return e;
    }

    public void setE(BusinessExceptionEnum e) {
        this.e = e;
    }

    /**
     * 不写入堆栈信息，提高性能
     * @return 直接返回最外层异常
     */
    @Override
    public synchronized Throwable fillInStackTrace() {
        return this;
    }
}