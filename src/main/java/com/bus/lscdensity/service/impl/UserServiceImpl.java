package com.bus.lscdensity.service.impl;

import com.bus.lscdensity.pojo.User;
import com.bus.lscdensity.mapper.UserMapper;
import com.bus.lscdensity.service.UserService;
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
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {
    @Autowired
    UserMapper userMapper;
    @Override
    public List<User> getUserInfo() {
        List<User> list = userMapper.selectList(null);
        if (list==null){
            return  null;
        }
        return list;
    }
}
