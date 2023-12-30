package com.han.train.member.service;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.date.DateTime;
import cn.hutool.core.util.ObjectUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.han.train.common.context.LoginMemberContext;
import com.han.train.common.response.PageResp;
import com.han.train.common.util.SnowUtil;
import com.han.train.member.domain.${Domain};
import com.han.train.member.domain.${Domain}Example;
import com.han.train.member.mapper.${Domain}Mapper;
import com.han.train.member.request.${Domain}QueryReq;
import com.han.train.member.request.${Domain}SaveReq;
import com.han.train.member.response.${Domain}QuerryResp;
import jakarta.annotation.Resource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import java.util.List;


@Service
public class ${Domain}Service {

    private static final Logger LOG = LoggerFactory.getLogger(${Domain}Service.class);
    @Resource
    private ${Domain}Mapper ${domain}Mapper;

    public void save(${Domain}SaveReq req) {
        DateTime time = DateTime.now();
        ${Domain} ${domain} = BeanUtil.copyProperties(req, ${Domain}.class);
        if (ObjectUtil.isNull(${domain}.getId())) {
            // 一般在service层都会重新赋值
            ${domain}.setId(SnowUtil.getSnowflakeNextId());
            ${domain}.setMemberId(LoginMemberContext.getId());
            ${domain}.setCreateTime(time);
            ${domain}.setUpdateTime(time);
            ${domain}Mapper.insert(${domain});
        } else {
//            selective的含义是如果对象有空值，不会更新对应内容，记住还要修改更新时间，其实还要进行数据校验的
            ${domain}.setUpdateTime(time);
            ${domain}Mapper.updateByPrimaryKeySelective(${domain});
        }

    }

    // service 层通常是要做的比较通用，controller比较细分，需要接口隔离
    // 返回的实体一般不实用domain包下的实体，而是自己创建一个方便修改
    public PageResp<${Domain}QuerryResp> queryList(${Domain}QueryReq req) {
        ${Domain}Example ${domain}Example = new ${Domain}Example();
        ${domain}Example.setOrderByClause("id desc");
        ${Domain}Example.Criteria criteria = ${domain}Example.createCriteria();
        if (ObjectUtil.isNotNull(req.getMemberId())) {
            criteria.andMemberIdEqualTo(req.getMemberId());
        }

        LOG.info("查询页码：{}", req.getPage());
        LOG.info("每页条数：{}", req.getSize());
        // 一般将分页和查询放在一起，因为这个是一次性的
        PageHelper.startPage(req.getPage(), req.getSize());
        List<${Domain}> ${domain}List = ${domain}Mapper.selectByExample(${domain}Example);
        PageInfo<${Domain}> pageInfo = new PageInfo<>(${domain}List);
        LOG.info("总行数：{}", pageInfo.getTotal());
        LOG.info("总页数：{}", pageInfo.getPages());

        List<${Domain}QuerryResp> list = BeanUtil.copyToList(${domain}List, ${Domain}QuerryResp.class);

        PageResp<${Domain}QuerryResp> pageResp = new PageResp<>();
        pageResp.setTotal(pageInfo.getTotal());
        pageResp.setList(list);
        return pageResp;
    }

    public void deleteById(Long id){
        ${domain}Mapper.deleteByPrimaryKey(id);
    }


}