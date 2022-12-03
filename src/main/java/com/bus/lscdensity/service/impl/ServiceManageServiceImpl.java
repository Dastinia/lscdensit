package com.bus.lscdensity.service.impl;

import com.bus.lscdensity.pojo.ServiceManage;
import com.bus.lscdensity.mapper.ServiceManageMapper;
import com.bus.lscdensity.service.ServiceManageService;
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
public class ServiceManageServiceImpl extends ServiceImpl<ServiceManageMapper, ServiceManage> implements ServiceManageService {
    @Autowired
    ServiceManageMapper serviceManageMapper;
    @Override
    public List<ServiceManage> getServiceManageInfo() {
        List<ServiceManage> list = serviceManageMapper.selectList(null);
        if(list==null){
            return null;
        }
        return list;
    }
}
