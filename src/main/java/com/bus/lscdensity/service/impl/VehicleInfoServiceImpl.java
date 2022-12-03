package com.bus.lscdensity.service.impl;

import com.bus.lscdensity.pojo.VehicleInfo;
import com.bus.lscdensity.mapper.VehicleInfoMapper;
import com.bus.lscdensity.service.VehicleInfoService;
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
public class VehicleInfoServiceImpl extends ServiceImpl<VehicleInfoMapper, VehicleInfo> implements VehicleInfoService {
    @Autowired
    VehicleInfoMapper vehicleInfoMapper;
    @Override
    public List<VehicleInfo> getVehicleInfo() {
        List<VehicleInfo> list = vehicleInfoMapper.selectList(null);
        if (list==null){
            return null;
        }
        return list;
    }
}
