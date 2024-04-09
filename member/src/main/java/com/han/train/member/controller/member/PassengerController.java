package com.han.train.member.controller.member;

import com.han.train.common.context.LoginMemberContext;
import com.han.train.common.response.CommonResp;
import com.han.train.common.response.PageResp;
import com.han.train.member.request.PassengerQueryReq;
import com.han.train.member.request.PassengerSaveReq;
import com.han.train.member.response.PassengerQueryResp;
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
    public CommonResp<PageResp<PassengerQueryResp>> queryList(@Valid PassengerQueryReq req) {
        req.setMemberId(LoginMemberContext.getId());
        PageResp<PassengerQueryResp> list = passengerService.queryList(req);
        return new CommonResp<>(list);
    }

//    这个参数就不单封装成一个单独的类，一般删除都是根据ID来删除的，这个接口还要注意权限
    @DeleteMapping("/delete/{id}")
    public CommonResp<Object> delete(@Valid @PathVariable Long id) {
        passengerService.deleteById(id);
        return new CommonResp<>();
    }

    @GetMapping("/query-mine")
    public CommonResp<List<PassengerQueryResp>> queryMine() {
        List<PassengerQueryResp> list = passengerService.queryMine();
        return new CommonResp<>(list);
    }

}