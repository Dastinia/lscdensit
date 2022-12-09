package com.bus.lscdensity.controller;


import com.bus.lscdensity.pojo.AiUnitInfo;
import com.bus.lscdensity.service.impl.AiUnitInfoServiceImpl;
import com.bus.lscdensity.utils.RedisUtils;
import lombok.extern.slf4j.Slf4j;
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
@Slf4j
@RestController
@RequestMapping("/lscdensit/ai-unit-info")
public class AiUnitInfoController {
    @Autowired
    AiUnitInfoServiceImpl aiUnitInfoService;
    @Autowired
    RedisUtils redisUtils;

    public boolean aiUnitInfoToR() {
        List<AiUnitInfo> info = aiUnitInfoService.getAiUnitInfo();

        if (info == null) {
            log.error("空数据");
            return false;
        }
        //写入redis
        for (int i = 0; i < info.size(); i++) {
            AiUnitInfo aiUnitInfo = info.get(i);
            redisUtils.lSet("AiUnitInfos",aiUnitInfo);
       }
        return true;
    }
    public  boolean getOneAiUnitToR(String aiUnitId){
        AiUnitInfo oneAiUnitInfo = aiUnitInfoService.getOneAiUnitInfo(aiUnitId);
        if (oneAiUnitInfo == null){
            log.error("空数据");
            return false;
        }
//        redisUtils.lSet("AiUnitInfo",oneAiUnitInfo);
        redisUtils.lSet("AiUnitInfoForKafka",oneAiUnitInfo); // kafkaService消费
        redisUtils.lSet("DataRedisInfo", oneAiUnitInfo); // ModelService消费
        return true;
    }
}
