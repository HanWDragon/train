package com.han.train.business.request;

import com.han.train.common.request.PageReq;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

// 这张表是根据查询条件来添加的，所以并没有添加属性，根据自己的需要来添加，模板只能添加相同的部分
public class DailyTrainQueryReq extends PageReq {

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date date;

    private String code;

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    @Override
    public String toString() {
        return "DailyTrainQueryReq{" +
                "} " + super.toString();
    }
}
