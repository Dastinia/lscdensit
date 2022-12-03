package com.bus.lscdensity.uncontroller;


import com.bus.lscdensity.pojo.GpuInfo;
import com.bus.lscdensity.service.impl.GpuInfoServiceImpl;
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
@RequestMapping("/lscdensit/gpu-info")
public class GpuInfoController {
    @Autowired
    GpuInfoServiceImpl gpuInfoService;
    @Autowired
    RedisUtils redisUtils;
    public  boolean gpuInfoToR(){
        List<GpuInfo> gpuInfo = gpuInfoService.getGpuInfo();
        if (gpuInfo==null){
            return false;
        }
        for (GpuInfo info :
                gpuInfo) {
            redisUtils.set("K", info.getGpuId());
            redisUtils.set("K", info.getGpuName());
            redisUtils.set("K",info.getRemarks());
        }
        return true;
    }
}

