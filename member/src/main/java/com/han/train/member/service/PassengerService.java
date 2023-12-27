package com.han.train.member.service;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.date.DateTime;
import cn.hutool.core.util.ObjectUtil;
import com.github.pagehelper.PageHelper;
import com.han.train.common.context.LoginMemberContext;
import com.han.train.common.util.SnowUtil;
import com.han.train.member.domain.Passenger;
import com.han.train.member.domain.PassengerExample;
import com.han.train.member.mapper.PassengerMapper;
import com.han.train.member.requset.PassengerQueryReq;
import com.han.train.member.requset.PassengerSaveReq;
import com.han.train.member.response.PassengerQuerryResp;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import java.util.List;

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

    // service 层通常是要做的比较通用，controller比较细分，需要接口隔离
    // 返回的实体一般不实用domain包下的实体，而是自己创建一个方便修改
    public List<PassengerQuerryResp> queryList(PassengerQueryReq req) {
        PassengerExample passengerExample = new PassengerExample();
        PassengerExample.Criteria criteria = passengerExample.createCriteria();
        if (ObjectUtil.isNotNull(req.getMemberId())) {
            criteria.andMemberIdEqualTo(req.getMemberId());
        }
        // 一般将分页和查询放在一起，因为这个是一次性的
        PageHelper.startPage(1, 2);
        List<Passenger> passengerList = passengerMapper.selectByExample(passengerExample);
        return BeanUtil.copyToList(passengerList, PassengerQuerryResp.class);
    }

}
