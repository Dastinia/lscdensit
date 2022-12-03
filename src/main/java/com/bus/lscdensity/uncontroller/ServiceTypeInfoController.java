package com.bus.lscdensity.uncontroller;


import com.bus.lscdensity.pojo.ServiceTypeInfo;
import com.bus.lscdensity.service.impl.ServiceTypeInfoServiceImpl;
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
@RequestMapping("/lscdensit/service-type-info")
public class ServiceTypeInfoController {
    @Autowired
    ServiceTypeInfoServiceImpl serviceTypeInfoService;
    @Autowired
    RedisUtils redisUtils;
    public  boolean serviceTypeInfoToR(){
        List<ServiceTypeInfo> serviceTypeInfo = serviceTypeInfoService.getServiceTypeInfo();
        if (serviceTypeInfo==null){
            return false;
        }
        for (ServiceTypeInfo info :
                serviceTypeInfo) {
            redisUtils.set("K", info.getServerTypeId());
            redisUtils.set("K",info.getServerTypeName());
        }
        return true;
    }
}

