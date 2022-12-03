package com.bus.lscdensity.uncontroller;


import com.bus.lscdensity.pojo.MemoryInfo;
import com.bus.lscdensity.service.impl.MemoryInfoServiceImpl;
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
@RequestMapping("/lscdensit/memory-info")
public class MemoryInfoController {
    @Autowired
    MemoryInfoServiceImpl memoryInfoService;
    @Autowired
    RedisUtils redisUtils;
    public  boolean memoryInfoToR(){
        List<MemoryInfo> memoryInfo = memoryInfoService.getMemoryInfo();
         if (memoryInfo==null) {
             return false;
         }
        for (MemoryInfo info :
                memoryInfo) {
            redisUtils.set("K",info.getMemoryId());
            redisUtils.set("K", info.getMemoryName());
            redisUtils.set("K",info.getRemarks());
        }
        return true;
    }
}

