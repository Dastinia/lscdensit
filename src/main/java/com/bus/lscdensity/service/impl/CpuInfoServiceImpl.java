package com.bus.lscdensity.service.impl;

import com.bus.lscdensity.pojo.CpuInfo;
import com.bus.lscdensity.mapper.CpuInfoMapper;
import com.bus.lscdensity.service.CpuInfoService;
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
public class CpuInfoServiceImpl extends ServiceImpl<CpuInfoMapper, CpuInfo> implements CpuInfoService {
    @Autowired
    CpuInfoMapper cpuInfoMapper;
    @Override
    public List<CpuInfo> getCpuInfo() {
        List<CpuInfo> cpuInfos = cpuInfoMapper.selectList(null);
        if (cpuInfos==null)return null;
        return  cpuInfos;
    }
}
