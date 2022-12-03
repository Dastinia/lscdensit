package com.bus.lscdensity.uncontroller;


import com.bus.lscdensity.pojo.CpuInfo;
import com.bus.lscdensity.service.impl.CpuInfoServiceImpl;
import com.bus.lscdensity.utils.RedisUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author JJJY
 * @since 2022-03-15
 */
@Controller
public class CpuInfoController {
    @Autowired
    CpuInfoServiceImpl cpuInfoService;
    @Autowired
    RedisUtils redisUtils;
    public  boolean cpuInToR(){
//        if (cpuInfoService==null)return false;
        List<CpuInfo> cpuinfo = cpuInfoService.getCpuInfo();
        if (cpuinfo==null) {
            return false;
        }
        for (CpuInfo info :
                cpuinfo) {
//            redisUtils.lSet("K",info.getCpuId());
//            redisUtils.lSet("K",""+info.getCpuNum());
//            redisUtils.lSet("K",info.getCpuType());
        }
        return true;
    }
}

