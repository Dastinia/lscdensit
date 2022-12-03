package com.bus.lscdensity.service.impl;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.bus.lscdensity.pojo.ServiceConfiguration;
import com.bus.lscdensity.mapper.ServiceConfigurationMapper;
import com.bus.lscdensity.service.ServiceConfigurationService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author JJJY
 * @since 2022-03-15
 */
@Service
public class ServiceConfigurationServiceImpl extends ServiceImpl<ServiceConfigurationMapper, ServiceConfiguration> implements ServiceConfigurationService {
    @Autowired
    ServiceConfigurationMapper service;
    @Override
    public List<ServiceConfiguration> getServiceConfigurationInfo() {
        List<ServiceConfiguration>  select = service.selectList(null);
        if(select==null) {
            return null;
        }
        return select;
    }
    public ServiceConfiguration getOneServiceConfigurationInfo(String grapIp){
        ServiceConfiguration one = service.selectOne(Wrappers.<ServiceConfiguration>lambdaQuery().eq(ServiceConfiguration::getServerIp1, grapIp));
        if (one==null)return null;
        return one;
    }
}
