package com.bus.lscdensity.service.impl;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.bus.lscdensity.pojo.AiUnitInfo;
import com.bus.lscdensity.mapper.AiUnitInfoMapper;
import com.bus.lscdensity.service.AiUnitInfoService;
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
public class AiUnitInfoServiceImpl extends ServiceImpl<AiUnitInfoMapper, AiUnitInfo> implements AiUnitInfoService {
    @Autowired
    AiUnitInfoMapper aiUnitInfoMapper;

    @Override
    public List<AiUnitInfo> getAiUnitInfo() {
        List<AiUnitInfo> infos = aiUnitInfoMapper.selectList(null);
        if(infos==null) {
            return null;
        }
        return infos;
    }
    public  AiUnitInfo getOneAiUiitInfo(String grapIp){
        AiUnitInfo aiUnitInfo = aiUnitInfoMapper.selectOne(Wrappers.<AiUnitInfo>lambdaQuery().eq(AiUnitInfo::getGrabIp, grapIp));
        if (aiUnitInfo==null){
            return null;
        }
        return aiUnitInfo;
    }
}
