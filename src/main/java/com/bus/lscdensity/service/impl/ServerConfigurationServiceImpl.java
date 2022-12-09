package com.bus.lscdensity.service.impl;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.bus.lscdensity.pojo.ServerConfiguration;
import com.bus.lscdensity.mapper.ServerConfigurationMapper;
import com.bus.lscdensity.service.ServerConfigurationService;
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
public class ServerConfigurationServiceImpl extends ServiceImpl<ServerConfigurationMapper, ServerConfiguration> implements ServerConfigurationService {
    @Autowired
    ServerConfigurationMapper service;
    @Override
    public List<ServerConfiguration> getServerConfigurationInfo() {
        List<ServerConfiguration>  select = service.selectList(null);
        if(select==null) {
            return null;
        }
        return select;
    }
}
