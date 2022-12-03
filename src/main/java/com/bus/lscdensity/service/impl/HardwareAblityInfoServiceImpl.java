package com.bus.lscdensity.service.impl;

import com.bus.lscdensity.pojo.HardwareAblityInfo;
import com.bus.lscdensity.mapper.HardwareAblityInfoMapper;
import com.bus.lscdensity.service.HardwareAblityInfoService;
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
public class HardwareAblityInfoServiceImpl extends ServiceImpl<HardwareAblityInfoMapper, HardwareAblityInfo> implements HardwareAblityInfoService {
    @Autowired
    HardwareAblityInfoMapper hardwareAblityInfoMapper;
    @Override
    public List<HardwareAblityInfo> getHardWareAblityInfo() {
        List<HardwareAblityInfo> hardwareAblityInfoList = hardwareAblityInfoMapper.selectList(null);
        if (hardwareAblityInfoList==null) {
            return null;
        }

        return hardwareAblityInfoList;
    }
}
