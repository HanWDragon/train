package com.han.train.business.controller.admin;

import com.han.train.business.request.DailyTrainSeatQueryReq;
import com.han.train.business.request.DailyTrainSeatSaveReq;
import com.han.train.business.response.DailyTrainSeatQueryResp;
import com.han.train.business.service.DailyTrainSeatService;
import com.han.train.common.response.CommonResp;
import com.han.train.common.response.PageResp;
import jakarta.annotation.Resource;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/admin/daily-train-seat")
public class DailyTrainSeatAdminController {

    @Resource
    private DailyTrainSeatService dailyTrainSeatService;

    @PostMapping("/save")
    public CommonResp<Object> save(@Valid @RequestBody DailyTrainSeatSaveReq req) {
        dailyTrainSeatService.save(req);
        return new CommonResp<>();
    }

    @GetMapping("/query-list")
    public CommonResp<PageResp<DailyTrainSeatQueryResp>> queryList(@Valid DailyTrainSeatQueryReq req) {
        PageResp<DailyTrainSeatQueryResp> list = dailyTrainSeatService.queryList(req);
        return new CommonResp<>(list);
    }

    @DeleteMapping("/delete/{id}")
    public CommonResp<Object> delete(@PathVariable Long id) {
        dailyTrainSeatService.delete(id);
        return new CommonResp<>();
    }

}
