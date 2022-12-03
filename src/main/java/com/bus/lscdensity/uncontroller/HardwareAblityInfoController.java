package com.bus.lscdensity.uncontroller;


import com.bus.lscdensity.pojo.HardwareAblityInfo;
import com.bus.lscdensity.service.impl.HardwareAblityInfoServiceImpl;
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
@RequestMapping("/lscdensit/hardware-ablity-info")
public class HardwareAblityInfoController {
    @Autowired
    HardwareAblityInfoServiceImpl hardwareAblityInfoService;
    @Autowired
    RedisUtils redisUtils;
    public  boolean hardWareAbiltyInfotoR(){
        List<HardwareAblityInfo> hardWareAblityInfo = hardwareAblityInfoService.getHardWareAblityInfo();
        if (hardWareAblityInfo==null) {
            return false;
        }
        for (HardwareAblityInfo info :
                hardWareAblityInfo) {
            redisUtils.set("K",info.getHardwareType());
            redisUtils.set("K",info.getHardwareUnitId());
            redisUtils.set("K",info.getStandardAblity());
        }
        return true;
    }
}

