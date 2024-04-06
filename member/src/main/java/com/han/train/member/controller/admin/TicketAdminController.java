package com.han.train.member.controller.admin;

import com.han.train.common.response.CommonResp;
import com.han.train.common.response.PageResp;
import com.han.train.member.request.TicketQueryReq;
import com.han.train.member.response.TicketQueryResp;
import com.han.train.member.service.TicketService;
import jakarta.annotation.Resource;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/admin/ticket")
public class TicketAdminController {

    @Resource
    private TicketService ticketService;


    @GetMapping("/query-list")
    public CommonResp<PageResp<TicketQueryResp>> queryList(@Valid TicketQueryReq req) {
        PageResp<TicketQueryResp> list = ticketService.queryList(req);
        return new CommonResp<>(list);
    }

}
