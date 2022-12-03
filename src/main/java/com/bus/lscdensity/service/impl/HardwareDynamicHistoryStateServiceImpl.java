package com.bus.lscdensity.service.impl;

import com.bus.lscdensity.mapper.HardwareDynamicStateMapper;
import com.bus.lscdensity.pojo.HardwareDynamicHistoryState;
import com.bus.lscdensity.mapper.HardwareDynamicHistoryStateMapper;
import com.bus.lscdensity.pojo.HardwareDynamicState;
import com.bus.lscdensity.service.HardwareDynamicHistoryStateService;
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
public class HardwareDynamicHistoryStateServiceImpl extends ServiceImpl<HardwareDynamicHistoryStateMapper, HardwareDynamicHistoryState> implements HardwareDynamicHistoryStateService {
    @Autowired
    HardwareDynamicHistoryStateMapper hardwareDynamicStateMapper;
    @Override
    public List<HardwareDynamicHistoryState> getHardwareDynamicHistoryState() {
        List<HardwareDynamicHistoryState> list = hardwareDynamicStateMapper.selectList(null);

        if(list==null) {
            return null;
        }
        return list;
    }
}
