package com.bus.lscdensity.uncontroller;


import com.bus.lscdensity.pojo.ServiceConfigDetailInfo;
import com.bus.lscdensity.service.impl.ServiceConfigDetailInfoServiceImpl;
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
@RequestMapping("/lscdensit/service-config-detail-info")
public class ServiceConfigDetailInfoController {
   @Autowired
    ServiceConfigDetailInfoServiceImpl serviceConfigDetailInfoService;
   @Autowired
    RedisUtils redisUtils;
   public  boolean serviceConfigDetailInfoToR(){
       List<ServiceConfigDetailInfo> serviceConfigDetailInfo = serviceConfigDetailInfoService.getServiceConfigDetailInfo();
       if (serviceConfigDetailInfo==null){
           return false;
       }
       for (ServiceConfigDetailInfo info :
               serviceConfigDetailInfo) {
           redisUtils.set("K",info.getChargeStandard());
           redisUtils.set("K",info.getCpuComputePower());
           redisUtils.set("K",info.getCpuLevel());
           redisUtils.set("K",info.getEnergyConsumption1());
           redisUtils.set("K",info.getEnergyConsumption2());
           redisUtils.set("K",info.getEnergyConsumption3());
           redisUtils.set("K",info.getGpuComputePower());
           redisUtils.set("K",info.getGpuType());
           redisUtils.set("K",info.getMermoryPower());
           redisUtils.set("K",info.getMermorySize());
           redisUtils.set("K",info.getNetworkBandwidth());
           redisUtils.set("K",info.getNetworkPower());
           redisUtils.set("K",info.getServiceDetailId());
           redisUtils.set("K",info.getServiceNodeNature());
           redisUtils.set("K",info.getTotalCharge());

       }
       return true;
   }
}

