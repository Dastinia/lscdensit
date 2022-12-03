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
    VehicleInfoController vehicleInfoControlle;
    @Autowired
    VehicleInputUnitController vehicleInputUnitController;

    public void run (String grapIp){
        boolean b = aiUnitInfoController.getOneAiUnitToR(grapIp);
        log.info("写入成功"+b);
        boolean b1 = serviceConfigurationController.getOneserviceConfigurationInfotoR(grapIp);
        log.info("写入成功"+b1);
        boolean b2 = vehicleInfoControlle.vehicleInfoToR();
        log.info("写入成功"+b2);
        boolean b3 = vehicleInputUnitController.vehicleInputUnitInfoToR(grapIp);
        log.info("写入成功"+b3);
    }
}
