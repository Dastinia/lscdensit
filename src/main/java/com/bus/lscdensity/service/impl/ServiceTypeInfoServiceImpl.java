package com.bus.lscdensity.service.impl;

import com.bus.lscdensity.pojo.ServiceTypeInfo;
import com.bus.lscdensity.mapper.ServiceTypeInfoMapper;
import com.bus.lscdensity.service.ServiceTypeInfoService;
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
public class ServiceTypeInfoServiceImpl extends ServiceImpl<ServiceTypeInfoMapper, ServiceTypeInfo> implements ServiceTypeInfoService {
    @Autowired
    ServiceTypeInfoMapper serviceTypeInfoMapper;
    @Override
    public List<ServiceTypeInfo> getServiceTypeInfo() {
        List<ServiceTypeInfo> list = serviceTypeInfoMapper.selectList(null);
        if (list==null){
            return  null;
        }
        return list;
    }
}
