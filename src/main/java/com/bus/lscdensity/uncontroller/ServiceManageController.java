package com.bus.lscdensity.uncontroller;


import com.bus.lscdensity.pojo.ServiceManage;
import com.bus.lscdensity.service.impl.ServiceManageServiceImpl;
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
@RequestMapping("/lscdensit/service-manage")
public class ServiceManageController {
    @Autowired
    ServiceManageServiceImpl service;
    @Autowired
    RedisUtils redisUtils;
    public boolean serviceManInfoToR(){
        List<ServiceManage> info = service.getServiceManageInfo();
        if (info==null){
            return false;
        }
        for (int i = 0; i < info.size(); i++) {
            ServiceManage serviceManage = info.get(i);
            redisUtils.set("K",serviceManage.getCompany());
            redisUtils.set("K",serviceManage.getServerId());
            redisUtils.set("K",serviceManage.getServerTypeId());
        }
        return true;
    }
}

