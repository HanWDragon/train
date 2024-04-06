package com.han.train.business.mapper.customer;

import java.util.Date;

public interface DailyTrainTicketCustomerMapper {

    int updateCountBySell(Date date
            , String trainCode
            , String seatTypeCode
            , Integer minStartIndex
            , Integer maxStartIndex
            , Integer minEndIndex
            , Integer maxEndIndex);
}
