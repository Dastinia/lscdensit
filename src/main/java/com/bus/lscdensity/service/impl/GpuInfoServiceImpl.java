package com.bus.lscdensity.service.impl;

import com.bus.lscdensity.pojo.GpuInfo;
import com.bus.lscdensity.mapper.GpuInfoMapper;
import com.bus.lscdensity.service.GpuInfoService;
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
public class GpuInfoServiceImpl extends ServiceImpl<GpuInfoMapper, GpuInfo> implements GpuInfoService {
    @Autowired
    GpuInfoMapper gpuInfoMapper;
    @Override
    public List<GpuInfo> getGpuInfo() {
        List<GpuInfo> gpuInfos = gpuInfoMapper.selectList(null);
        if (gpuInfos==null){
            return null;
        }
        return gpuInfos;

    }
}
