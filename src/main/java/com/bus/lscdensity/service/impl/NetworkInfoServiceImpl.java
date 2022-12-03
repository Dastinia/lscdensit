package com.bus.lscdensity.service.impl;

import com.bus.lscdensity.pojo.NetworkInfo;
import com.bus.lscdensity.mapper.NetworkInfoMapper;
import com.bus.lscdensity.service.NetworkInfoService;
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
public class NetworkInfoServiceImpl extends ServiceImpl<NetworkInfoMapper, NetworkInfo> implements NetworkInfoService {
    @Autowired
    NetworkInfoMapper networkInfoMapper;
    @Override
    public List<NetworkInfo> getNetworkInfo() {
        List<NetworkInfo> list = networkInfoMapper.selectList(null);
        if (list==null) {
            return null;
        }
        return list;
    }
}
