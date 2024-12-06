package com.han.train.business.mapper.customer;

import java.util.Date;

public interface SkTokenCustomerMapper {

    int decrease(Date date, String trainCode, int decreaseCount);
}
