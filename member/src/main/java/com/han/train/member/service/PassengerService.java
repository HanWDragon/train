package com.han.train.member.service;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.date.DateTime;
import cn.hutool.core.util.ObjectUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.han.train.common.context.LoginMemberContext;
import com.han.train.common.exception.BusinessException;
import com.han.train.common.exception.BusinessExceptionEnum;
import com.han.train.common.response.PageResp;
import com.han.train.common.util.SnowUtil;
import com.han.train.member.domain.Passenger;
import com.han.train.member.domain.PassengerExample;
import com.han.train.member.mapper.PassengerMapper;
import com.han.train.member.request.PassengerQueryReq;
import com.han.train.member.request.PassengerSaveReq;
import com.han.train.member.response.PassengerQueryResp;
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
//            如果此时我的乘车人>50,那么就会抛出异常，由于此时已经登陆那么前台就会有此时注册人的id，和member_id关联，可以使用这个来查询

            PassengerExample passengerExample = new PassengerExample();
            passengerExample.createCriteria().andMemberIdEqualTo(LoginMemberContext.getId());
            int size = passengerMapper.selectByExample(passengerExample).size();
            if (size >= 50) {
                throw new BusinessException(BusinessExceptionEnum.MEMBER_PASSENGER_TOO_MORE);
            }
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
    public PageResp<PassengerQueryResp> queryList(PassengerQueryReq req) {
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

        List<PassengerQueryResp> list = BeanUtil.copyToList(passengerList, PassengerQueryResp.class);

        PageResp<PassengerQueryResp> pageResp = new PageResp<>();
        pageResp.setTotal(pageInfo.getTotal());
        pageResp.setList(list);
        return pageResp;
    }

    public void deleteById(Long id) {
        passengerMapper.deleteByPrimaryKey(id);
    }

    /**
     * 查询我的所有乘客
     */
    public List<PassengerQueryResp> queryMine() {
        PassengerExample passengerExample = new PassengerExample();
        passengerExample.setOrderByClause("name asc");
        PassengerExample.Criteria criteria = passengerExample.createCriteria();
        criteria.andMemberIdEqualTo(LoginMemberContext.getId());
        List<Passenger> list = passengerMapper.selectByExample(passengerExample);
        return BeanUtil.copyToList(list, PassengerQueryResp.class);
    }


}
