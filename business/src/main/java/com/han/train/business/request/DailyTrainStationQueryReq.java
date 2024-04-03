package com.han.train.business.request;

import com.han.train.common.request.PageReq;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

// 这张表是根据查询条件来添加的，所以并没有添加属性，根据自己的需要来添加，模板只能添加相同的部分
public class DailyTrainStationQueryReq extends PageReq {
    /**
     * 日期
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date date;

    /**
     * 车次编号
     */
    private String trainCode;

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getTrainCode() {
        return trainCode;
    }

    public void setTrainCode(String trainCode) {
        this.trainCode = trainCode;
    }

    @Override
    public String toString() {
        return "DailyTrainStationQueryReq{" +
                "date=" + date +
                ", trainCode='" + trainCode + '\'' +
                "} " + super.toString();
    }
}
