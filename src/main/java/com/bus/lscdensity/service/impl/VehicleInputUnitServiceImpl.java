package com.bus.lscdensity.service.impl;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.bus.lscdensity.mapper.ServiceConfigurationMapper;
import com.bus.lscdensity.pojo.ServiceConfiguration;
import com.bus.lscdensity.pojo.VehicleInputUnit;
import com.bus.lscdensity.mapper.VehicleInputUnitMapper;
import com.bus.lscdensity.service.VehicleInputUnitService;
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
public class VehicleInputUnitServiceImpl extends ServiceImpl<VehicleInputUnitMapper, VehicleInputUnit> implements VehicleInputUnitService {
    @Autowired
    VehicleInputUnitMapper vehicleInputUnitMapper;

    @Autowired
    ServiceConfigurationMapper serviceConfigurationMapper;
    @Override
    public List<VehicleInputUnit> getVehicleInputUnit() {
        List<VehicleInputUnit> list = vehicleInputUnitMapper.selectList(null);
        if (list==null){
            return  null;
        }
        return list;
    }

    public VehicleInputUnit getOneVehicleInputUnit(String grapIp){
        ServiceConfiguration serviceConfiguration = serviceConfigurationMapper.selectOne(Wrappers.<ServiceConfiguration>lambdaQuery().eq(ServiceConfiguration::getServerIp1, grapIp));
        return vehicleInputUnitMapper.selectOne(Wrappers.<VehicleInputUnit>lambdaQuery().eq(VehicleInputUnit::getServerId, serviceConfiguration.getServerId()));
    }
}
