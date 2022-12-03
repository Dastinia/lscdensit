package com.bus.lscdensity.service;

import com.bus.lscdensity.pojo.ServiceConfigDetailInfo;
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
public interface ServiceConfigDetailInfoService extends IService<ServiceConfigDetailInfo> {
    /**
     * @return
     */
    List<ServiceConfigDetailInfo> getServiceConfigDetailInfo();
}
