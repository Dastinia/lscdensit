package com.bus.lscdensity.controller;


import com.bus.lscdensity.pojo.VehicleInfo;
import com.bus.lscdensity.service.impl.VehicleInfoServiceImpl;
import com.bus.lscdensity.utils.RedisUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author JJJY
 * @since 2022-03-15
 */
@RestController
@RequestMapping("/lscdensit/vehicle-info")
public class VehicleInfoController {
    @Autowired
    VehicleInfoServiceImpl vehicleInfoService;
    @Autowired
    RedisUtils redisUtils;
    public  boolean vehicleInfoToR(){
        List<VehicleInfo> vehicleInfo = vehicleInfoService.getVehicleInfo();
        if (vehicleInfo==null){
            return false;
        }
        for (VehicleInfo info :
                vehicleInfo) {
            redisUtils.lSet("VehicleInfo",info);
        }
        return true;
    }
}

