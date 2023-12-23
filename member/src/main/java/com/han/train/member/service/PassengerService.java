package com.han.train.member.service;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.date.DateTime;
import com.han.train.common.context.LoginMemberContext;
import com.han.train.common.util.SnowUtil;
import com.han.train.member.domain.Passenger;
import com.han.train.member.mapper.PassengerMapper;
import com.han.train.member.requset.PassengerSaveReq;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

/**
 * @author Han
 * @date 2023年12月23日 09:43
 */
@Service
public class PassengerService {

    @Resource
    private PassengerMapper passengerMapper;

    public void save(PassengerSaveReq req) {
        DateTime time = DateTime.now();
        Passenger passenger = BeanUtil.copyProperties(req, Passenger.class);
        // 一般在service层都会重新赋值
        passenger.setId(SnowUtil.getSnowflakeNextId());
        passenger.setMemberId(LoginMemberContext.getId());
        passenger.setCreateTime(time);
        passenger.setUpdateTime(time);
        passengerMapper.insert(passenger);
    }

}
