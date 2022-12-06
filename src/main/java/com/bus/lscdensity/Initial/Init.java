package com.bus.lscdensity.Initial;

import com.bus.lscdensity.controller.*;
import com.bus.lscdensity.uncontroller.CpuInfoController;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class Init {

    @Autowired
    AiUnitInfoController aiUnitInfoController;
    @Autowired
    CpuInfoController controller;
    @Autowired
    ServiceConfigurationController serviceConfigurationController;
    @Autowired
    VehicleInfoController vehicleInfoController;
    @Autowired
    VehicleInputUnitController vehicleInputUnitController;

    public void run (String aiUnitId){
        boolean b = aiUnitInfoController.getOneAiUnitToR(aiUnitId);
        log.info("aiunitInfo写入redis:" + b);
        boolean b1 = serviceConfigurationController.getOneserviceConfigurationInfotoR(aiUnitId);
        log.info("serviceConfiguration写入redis:" + b1);
//        boolean b2 = vehicleInfoController.vehicleInfoToR();
//        log.info("vehicleInfo写入redis:" + b2);
        boolean b3 = vehicleInputUnitController.vehicleInputUnitInfoToR(aiUnitId);
        log.info("vechicleInput写入redis:" + b3);
    }
}
