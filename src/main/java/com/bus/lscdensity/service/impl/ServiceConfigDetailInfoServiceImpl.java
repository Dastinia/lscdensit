package com.bus.lscdensity.service.impl;

import com.bus.lscdensity.pojo.ServiceConfigDetailInfo;
import com.bus.lscdensity.mapper.ServiceConfigDetailInfoMapper;
import com.bus.lscdensity.service.ServiceConfigDetailInfoService;
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
public class ServiceConfigDetailInfoServiceImpl extends ServiceImpl<ServiceConfigDetailInfoMapper, ServiceConfigDetailInfo> implements ServiceConfigDetailInfoService {
    @Autowired
    ServiceConfigDetailInfoMapper serviceConfigDetailInfoMapper;
    @Override
    public List<ServiceConfigDetailInfo> getServiceConfigDetailInfo() {
        List<ServiceConfigDetailInfo> list = serviceConfigDetailInfoMapper.selectList(null);
        if (list==null){
            return  null;
        }
        return list;
    }
}
