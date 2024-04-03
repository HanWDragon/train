package com.han.train.business.request;

import com.han.train.common.request.PageReq;

// 这张表是根据查询条件来添加的，所以并没有添加属性，根据自己的需要来添加，模板只能添加相同的部分
public class DailyTrainSeatQueryReq extends PageReq {
    private String trainCode;

    public String getTrainCode() {
        return trainCode;
    }

    public void setTrainCode(String trainCode) {
        this.trainCode = trainCode;
    }

    @Override
    public String toString() {
        return "DailyTrainSeatQueryReq{" +
                "trainCode='" + trainCode + '\'' +
                "} " + super.toString();
    }
}
