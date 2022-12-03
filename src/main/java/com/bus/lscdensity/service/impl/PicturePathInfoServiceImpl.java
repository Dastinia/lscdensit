package com.bus.lscdensity.service.impl;

import com.bus.lscdensity.pojo.PicturePathInfo;
import com.bus.lscdensity.mapper.PicturePathInfoMapper;
import com.bus.lscdensity.service.PicturePathInfoService;
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
public class PicturePathInfoServiceImpl extends ServiceImpl<PicturePathInfoMapper, PicturePathInfo> implements PicturePathInfoService {
    @Autowired
    PicturePathInfoMapper picturePathInfoMapper;
    @Override
    public List<PicturePathInfo> getPicturePathInfo() {
        List<PicturePathInfo> list = picturePathInfoMapper.selectList(null);
        if (list==null){
            return null;
        }
        return list;
    }
}
