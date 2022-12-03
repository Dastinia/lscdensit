package com.bus.lscdensity.controller;

import com.bus.lscdensity.common.Result;
import com.bus.lscdensity.pojo.ContainerInfo;
import com.bus.lscdensity.service.ContainerInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author Dastinia
 * @version 1.0
 * @date 2022/11/30
 */
@RestController
@RequestMapping("/containerinfo")
public class ContainerInfoController {

    @Autowired
    private ContainerInfoService containerInfoService;

    @GetMapping("/{container_id}")
    public Result getById(@PathVariable String container_id) {
        ContainerInfo containerInfo = containerInfoService.getById(container_id);
        String msg = containerInfo != null ? "数据查询成功":"数据查询失败，请重试！";
        return new Result(true,containerInfo,msg);
    }

    @GetMapping()
    public Result getAll() {
        List<ContainerInfo> containerInfoList = containerInfoService.getContainerInfo();
        String msg = containerInfoList != null ? "数据查询成功":"数据查询失败，请重试！";
        return new Result(true,containerInfoList,msg);
    }
}
