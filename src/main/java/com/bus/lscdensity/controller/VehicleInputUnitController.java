package com.bus.lscdensity.controller;


import com.bus.lscdensity.pojo.VehicleInfo;
import com.bus.lscdensity.pojo.VehicleInputUnit;
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
    public  boolean  vehicleInputUnitInfoToR(String aiUnitId){
        VehicleInputUnit oneVehicleInputUnit = vehicleInputUnitService.getOneVehicleInputUnit(aiUnitId);
        if (oneVehicleInputUnit==null){
            return false;
        }
        redisUtils.lSet("InputUnit",oneVehicleInputUnit);
        return true;
    }
}

