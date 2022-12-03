package com.bus.lscdensity.service.impl;

import com.bus.lscdensity.pojo.HardwareDynamicState;
import com.bus.lscdensity.mapper.HardwareDynamicStateMapper;
import com.bus.lscdensity.service.HardwareDynamicStateService;
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
public class HardwareDynamicStateServiceImpl extends ServiceImpl<HardwareDynamicStateMapper, HardwareDynamicState> implements HardwareDynamicStateService {
    @Autowired
    HardwareDynamicStateMapper hardwareDynamicStateMapper;
    @Override
    public List<HardwareDynamicState> getHardwareDynamicState() {
        List<HardwareDynamicState> list = hardwareDynamicStateMapper.selectList(null);
        if (list==null){
            return null;
        }
        return list;
    }
}
