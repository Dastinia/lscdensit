package com.bus.lscdensity.service.impl;

import com.bus.lscdensity.mapper.ServerConfigurationMapper;
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
    ServerConfigurationMapper serviceConfigurationMapper;
    @Override
    public List<VehicleInputUnit> getVehicleInputUnit() {
        List<VehicleInputUnit> list = vehicleInputUnitMapper.selectList(null);
        if (list==null){
            return  null;
        }
        return list;
    }

    public VehicleInputUnit getOneVehicleInputUnit(String vehicleInputId){
        return vehicleInputUnitMapper.selectById(vehicleInputId);
    }
}
