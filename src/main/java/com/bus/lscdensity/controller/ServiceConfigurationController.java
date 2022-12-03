package com.bus.lscdensity.controller;


import com.bus.lscdensity.pojo.ServiceConfiguration;
import com.bus.lscdensity.service.impl.ServiceConfigurationServiceImpl;
import com.bus.lscdensity.utils.RedisUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

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
@RequestMapping("/lscdensit/service-configuration")
public class ServiceConfigurationController {
    @Autowired
    RedisUtils redisUtils;
    @Autowired
    ServiceConfigurationServiceImpl serviceimpl;

    public  boolean serviceConfigurationInfotoR(){
        List<ServiceConfiguration> serviceConfigurationinfo = serviceimpl.getServiceConfigurationInfo();
        if(serviceConfigurationinfo==null) {
            return false;
        }
        for (ServiceConfiguration info :
                serviceConfigurationinfo) {
           redisUtils.lSet("sercon", info);
        }
        return  true;
    }
    public  boolean getOneserviceConfigurationInfotoR(String grapIp){
        String ip = grapIp.substring(6,16);
        ServiceConfiguration serviceConfigurationinfo = serviceimpl.getOneServiceConfigurationInfo(ip);
        if(serviceConfigurationinfo==null) {
            return false;
        }
        redisUtils.lSet("sercon", serviceConfigurationinfo);
        return  true;
    }
}

