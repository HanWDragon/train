package com.han.train.member.controller;

import com.han.train.common.context.LoginMemberContext;
import com.han.train.common.response.CommonResp;
import com.han.train.member.requset.PassengerQueryReq;
import com.han.train.member.requset.PassengerSaveReq;
import com.han.train.member.response.PassengerQuerryResp;
import com.han.train.member.service.PassengerService;
import jakarta.annotation.Resource;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author Han
 * @date 2023年09月05日 08:57
 */
@RestController
@RequestMapping("/passenger")
public class PassengerController {

    @Resource
    private PassengerService passengerService;


    @PostMapping("/save")
    public CommonResp<Object> register(@Valid @RequestBody PassengerSaveReq req) {
        passengerService.save(req);
        return new CommonResp<>();
    }

    // 这个要针对接口细化，不能放到service层
//    查询类我们用get，修改和新增用post  @RequestBody要去掉，信息在url中spring会自动映射
    @GetMapping("/query-list")
    public CommonResp<List<PassengerQuerryResp>> queryList(@Valid PassengerQueryReq req) {
        req.setMemberId(LoginMemberContext.getId());
        List<PassengerQuerryResp> list = passengerService.queryList(req);
        return new CommonResp<>(list);
    }

}
