package com.bus.lscdensity.service.impl;

import com.bus.lscdensity.pojo.MemoryInfo;
import com.bus.lscdensity.mapper.MemoryInfoMapper;
import com.bus.lscdensity.service.MemoryInfoService;
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
public class MemoryInfoServiceImpl extends ServiceImpl<MemoryInfoMapper, MemoryInfo> implements MemoryInfoService {
    @Autowired
    MemoryInfoMapper memoryInfoMapper;
    @Override
    public List<MemoryInfo> getMemoryInfo() {
        List<MemoryInfo> list = memoryInfoMapper.selectList(null);
        if(list==null){
            return  null;
        }
        return list;
    }
}
