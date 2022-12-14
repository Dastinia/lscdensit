package com.bus.lscdensity.controller;


import com.bus.lscdensity.pojo.AiUnitInfo;
import com.bus.lscdensity.pojo.VehicleInfo;
import com.bus.lscdensity.pojo.VehicleInputUnit;
import com.bus.lscdensity.service.impl.AiUnitInfoServiceImpl;
import com.bus.lscdensity.service.impl.VehicleInfoServiceImpl;
import com.bus.lscdensity.service.impl.VehicleInputUnitServiceImpl;
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
@RequestMapping("/lscdensit/vehicle-input-unit")
public class VehicleInputUnitController {
    @Autowired
    VehicleInputUnitServiceImpl vehicleInputUnitService;
    @Autowired
    RedisUtils redisUtils;
    @Autowired
    AiUnitInfoServiceImpl aiUnitInfoService;

    public  boolean  vehicleInputUnitInfoToR(String aiUnitId){
        AiUnitInfo oneAiUnitInfo = aiUnitInfoService.getOneAiUnitInfo(aiUnitId);
        VehicleInputUnit oneVehicleInputUnit = vehicleInputUnitService.getById(oneAiUnitInfo.getVehicleInputId());
        if (oneVehicleInputUnit==null){
            return false;
        }
//        redisUtils.lSet("VehicleInputUnit",oneVehicleInputUnit);
        redisUtils.lSet("vehicleInputUnitForKafka",oneVehicleInputUnit);
        return true;
    }
}

