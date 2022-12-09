package com.bus.lscdensity.Initial;

import com.bus.lscdensity.common.ModelInfo;
import com.bus.lscdensity.common.MonitorInfo;
import com.bus.lscdensity.controller.*;
import com.bus.lscdensity.pojo.AiUnitInfo;
import com.bus.lscdensity.pojo.ServerConfiguration;
import com.bus.lscdensity.pojo.VehicleInputUnit;
import com.bus.lscdensity.service.impl.AiUnitInfoServiceImpl;
import com.bus.lscdensity.service.impl.ServerConfigurationServiceImpl;
import com.bus.lscdensity.service.impl.VehicleInputUnitServiceImpl;
import com.bus.lscdensity.uncontroller.CpuInfoController;
import com.bus.lscdensity.utils.RedisUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class Init {

    @Autowired
    AiUnitInfoServiceImpl aiUnitInfoService;
    @Autowired
    ServerConfigurationServiceImpl serverConfigurationService;
    @Autowired
    VehicleInputUnitServiceImpl vehicleInputUnitService;
    @Autowired
    RedisUtils redisUtils;

    public void run (String aiUnitId){

        // todo 向redis发送KafkaService需要的数据
        boolean b1 = SendKafkaInfo(aiUnitId);

        // 向redis发送MonitorService需要的数据
        boolean b2 = SendMonitorInfo(aiUnitId);

        // todo 向redis发送ModelService需要的数据
        boolean b3 = SendModelInfo(aiUnitId);

    }

    public boolean SendKafkaInfo(String aiUnited) {
        AiUnitInfo oneAiUnitInfo = aiUnitInfoService.getOneAiUnitInfo(aiUnited);
        redisUtils.lSet("AiUnitInfo", oneAiUnitInfo);
        VehicleInputUnit VIUInfo = vehicleInputUnitService.getById(oneAiUnitInfo.getVehicleInputId());
        redisUtils.lSet("VehicleInputUnit", VIUInfo);
        return true;
    }

    public boolean SendMonitorInfo(String aiUnited) {
        AiUnitInfo oneAiUnitInfo = aiUnitInfoService.getOneAiUnitInfo(aiUnited);
        String kafkaServiceIp = oneAiUnitInfo.getGrabIp();
        ServerConfiguration sc1 = serverConfigurationService.getById(oneAiUnitInfo.getGrabServerId());
        String kafkaUser = sc1.getServerUser();
        String kafkaPasswd = sc1.getServerPasswd();
        String kafkaServerType = sc1.getServerType();

        String modelServiceIp = oneAiUnitInfo.getAiServerIp();
        ServerConfiguration sc2 = serverConfigurationService.getById(oneAiUnitInfo.getAiServerId());
        String modelUser = sc2.getServerUser();
        String modelPasswd = sc2.getServerPasswd();
        String modelServerType = sc2.getServerType();

        MonitorInfo monitorInfo = new MonitorInfo(kafkaServiceIp,kafkaUser,kafkaPasswd,kafkaServerType,modelServiceIp,modelUser,modelPasswd,modelServerType);
        log.info(monitorInfo.toString());
        redisUtils.lSet("MonitorInfo", monitorInfo);
        return true;
    }

    public boolean SendModelInfo(String aiUnited) {
        AiUnitInfo oneAiUnitInfo = aiUnitInfoService.getOneAiUnitInfo(aiUnited);
        String dataRedisServiceIp = oneAiUnitInfo.getRedisIp();
        Integer dataRedisPort = oneAiUnitInfo.getRedisDataFlowPort();
        ModelInfo modelInfo = new ModelInfo(dataRedisServiceIp, dataRedisPort);
        log.info(modelInfo.toString());
        redisUtils.lSet("DataRedisInfo", modelInfo);
        return true;
    }
}
