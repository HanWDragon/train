package com.han.train.member.service;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.date.DateTime;
import cn.hutool.core.util.ObjectUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.han.train.common.context.LoginMemberContext;
import com.han.train.common.response.PageResp;
import com.han.train.common.util.SnowUtil;
import com.han.train.member.domain.Passenger;
import com.han.train.member.domain.PassengerExample;
import com.han.train.member.mapper.PassengerMapper;
import com.han.train.member.requset.PassengerQueryReq;
import com.han.train.member.requset.PassengerSaveReq;
import com.han.train.member.response.PassengerQuerryResp;
import jakarta.annotation.Resource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author Han
 * @date 2023年12月23日 09:43
 */
@Service
public class PassengerService {

    private static final Logger LOG = LoggerFactory.getLogger(PassengerService.class);
    @Resource
    private PassengerMapper passengerMapper;

    public void save(PassengerSaveReq req) {
        DateTime time = DateTime.now();
        Passenger passenger = BeanUtil.copyProperties(req, Passenger.class);
        if (ObjectUtil.isNull(passenger.getId())) {
            // 一般在service层都会重新赋值
            passenger.setId(SnowUtil.getSnowflakeNextId());
            passenger.setMemberId(LoginMemberContext.getId());
            passenger.setCreateTime(time);
            passenger.setUpdateTime(time);
            passengerMapper.insert(passenger);
        } else {
//            selective的含义是如果对象有空值，不会更新对应内容，记住还要修改更新时间，其实还要进行数据校验的
            passenger.setUpdateTime(time);
            passengerMapper.updateByPrimaryKeySelective(passenger);
        }

    }

    // service 层通常是要做的比较通用，controller比较细分，需要接口隔离
    // 返回的实体一般不实用domain包下的实体，而是自己创建一个方便修改
    public PageResp<PassengerQuerryResp> queryList(PassengerQueryReq req) {
        PassengerExample passengerExample = new PassengerExample();
        passengerExample.setOrderByClause("id desc");
        PassengerExample.Criteria criteria = passengerExample.createCriteria();
        if (ObjectUtil.isNotNull(req.getMemberId())) {
            criteria.andMemberIdEqualTo(req.getMemberId());
        }

        LOG.info("查询页码：{}", req.getPage());
        LOG.info("每页条数：{}", req.getSize());
        // 一般将分页和查询放在一起，因为这个是一次性的
        PageHelper.startPage(req.getPage(), req.getSize());
        List<Passenger> passengerList = passengerMapper.selectByExample(passengerExample);
        PageInfo<Passenger> pageInfo = new PageInfo<>(passengerList);
        LOG.info("总行数：{}", pageInfo.getTotal());
        LOG.info("总页数：{}", pageInfo.getPages());

        List<PassengerQuerryResp> list = BeanUtil.copyToList(passengerList, PassengerQuerryResp.class);

        PageResp<PassengerQuerryResp> pageResp = new PageResp<>();
        pageResp.setTotal(pageInfo.getTotal());
        pageResp.setList(list);
        return pageResp;
    }

}
