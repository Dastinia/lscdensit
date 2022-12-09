package com.bus.lscdensity.controller;


import com.bus.lscdensity.pojo.ServerConfiguration;
import com.bus.lscdensity.service.impl.ServerConfigurationServiceImpl;
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
public class ServerConfigurationController {
    @Autowired
    RedisUtils redisUtils;
    @Autowired
    ServerConfigurationServiceImpl serverConfigurationService;

    public  boolean serverConfigurationInfotoR(){
        List<ServerConfiguration> serviceConfigurationinfo = serverConfigurationService.getServerConfigurationInfo();
        if(serviceConfigurationinfo==null) {
            return false;
        }
        for (ServerConfiguration info :
                serviceConfigurationinfo) {
           redisUtils.lSet("sercon", info);
        }
        return  true;
    }
}

