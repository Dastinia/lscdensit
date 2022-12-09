package com.bus.lscdensity.service;

import com.bus.lscdensity.pojo.ServerConfiguration;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author JJJY
 * @since 2022-03-15
 */
public interface ServerConfigurationService extends IService<ServerConfiguration> {
   List<ServerConfiguration>   getServerConfigurationInfo();
}