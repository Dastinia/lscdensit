package com.bus.lscdensity.uncontroller;


import com.bus.lscdensity.pojo.HardwareDynamicHistoryState;
import com.bus.lscdensity.service.impl.HardwareDynamicHistoryStateServiceImpl;
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
@RequestMapping("/lscdensit/hardware-dynamic-history-state")
public class HardwareDynamicHistoryStateController {
    @Autowired
    HardwareDynamicHistoryStateServiceImpl stateService;
    @Autowired
    RedisUtils redisUtils;

    public boolean hardwareDynamicStateInfoToR() {
        List<HardwareDynamicHistoryState> infolist = stateService.getHardwareDynamicHistoryState();
        if (infolist == null) {
            return false;
        }
        for (HardwareDynamicHistoryState info :
                infolist) {
            redisUtils.set("K", info.getCpuId());
            redisUtils.set("K", info.getCpuStatus());
            redisUtils.set("K", info.getCpuUtilization());
            redisUtils.set("K", info.getGpuId());
            redisUtils.set("K", info.getGpuStatus());
            redisUtils.set("K", info.getGpuUtilization());
            redisUtils.set("K", info.getMemoryId());
            redisUtils.set("K", info.getMemStatus());
            redisUtils.set("K", info.getMemUtilization());
            redisUtils.set("K", info.getNetwordStatus());
            redisUtils.set("K", info.getNetwordUtilization());
            redisUtils.set("K", info.getNetworkId());
            redisUtils.set("K", info.getServerId());
            redisUtils.set("K", info.getTimeUnit());
        }
        return true;
    }

}
