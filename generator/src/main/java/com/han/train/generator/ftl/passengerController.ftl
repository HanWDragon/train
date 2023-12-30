package com.han.train.member.controller;

import com.han.train.common.context.LoginMemberContext;
import com.han.train.common.response.CommonResp;
import com.han.train.common.response.PageResp;
import com.han.train.member.request.${Domain}QueryReq;
import com.han.train.member.request.${Domain}SaveReq;
import com.han.train.member.response.${Domain}QuerryResp;
import com.han.train.member.service.${Domain}Service;
import jakarta.annotation.Resource;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

/**
 * @author Han
 * @date 2023年09月05日 08:57
 */
@RestController
@RequestMapping("/${domain}")
public class ${Domain}Controller {

    @Resource
    private ${Domain}Service ${domain}Service;


    @PostMapping("/save")
    public CommonResp<Object> register(@Valid @RequestBody ${Domain}SaveReq req) {
        ${domain}Service.save(req);
        return new CommonResp<>();
    }

    // 这个要针对接口细化，不能放到service层
//    查询类我们用get，修改和新增用post  @RequestBody要去掉，信息在url中spring会自动映射
    @GetMapping("/query-list")
    public CommonResp<PageResp<${Domain}QuerryResp>> queryList(@Valid ${Domain}QueryReq req) {
        req.setMemberId(LoginMemberContext.getId());
        PageResp<${Domain}QuerryResp> list = ${domain}Service.queryList(req);
        return new CommonResp<>(list);
    }

//    这个参数就不单封装成一个单独的类，一般删除都是根据ID来删除的，这个接口还要注意权限
    @DeleteMapping("/delete/{id}")
    public CommonResp<Object> delete(@Valid @PathVariable Long id) {
        ${domain}Service.deleteById(id);
        return new CommonResp<>();
    }

}
