package com.bus.lscdensity.uncontroller;


import com.bus.lscdensity.pojo.NetworkInfo;
import com.bus.lscdensity.service.impl.NetworkInfoServiceImpl;
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
@RequestMapping("/lscdensit/network-info")
public class NetworkInfoController {
    @Autowired
    NetworkInfoServiceImpl networkInfoService;
    @Autowired
    RedisUtils redisUtils;
    public  boolean netWorkInfoToR(){
        List<NetworkInfo> networkInfo = networkInfoService.getNetworkInfo();
        if (networkInfo==null){
            return  false;
        }
        for (NetworkInfo info :
             networkInfo ) {
            redisUtils.set("K",info.getNetwordBandwidthbandwidth());
            redisUtils.set("K",info.getNetwordId());
            redisUtils.set("K",info.getRemark());
        }
        return true;
    }
}

