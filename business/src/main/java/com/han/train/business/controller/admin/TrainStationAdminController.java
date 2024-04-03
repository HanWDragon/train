package com.han.train.business.controller.admin;

import com.han.train.business.request.TrainStationQueryReq;
import com.han.train.business.request.TrainStationSaveReq;
import com.han.train.business.response.TrainStationQueryResp;
import com.han.train.business.service.TrainStationService;
import com.han.train.common.response.CommonResp;
import com.han.train.common.response.PageResp;
import jakarta.annotation.Resource;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/admin/train-station")
public class TrainStationAdminController {

    @Resource
    private TrainStationService trainStationService;

    @PostMapping("/save")
    public CommonResp<Object> save(@Valid @RequestBody TrainStationSaveReq req) {
        trainStationService.save(req);
        return new CommonResp<>();
    }

    @GetMapping("/query-list")
    public CommonResp<PageResp<TrainStationQueryResp>> queryList(@Valid TrainStationQueryReq req) {
        PageResp<TrainStationQueryResp> list = trainStationService.queryList(req);
        return new CommonResp<>(list);
    }

    @DeleteMapping("/delete/{id}")
    public CommonResp<Object> delete(@PathVariable Long id) {
        trainStationService.delete(id);
        return new CommonResp<>();
    }

}
