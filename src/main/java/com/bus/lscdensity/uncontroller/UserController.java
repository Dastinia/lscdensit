package com.bus.lscdensity.uncontroller;


import com.bus.lscdensity.pojo.User;
import com.bus.lscdensity.service.impl.UserServiceImpl;
import com.bus.lscdensity.utils.RedisUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author JJJY
 * @since 2022-03-15
 */
@RestController
@RequestMapping("/lscdensit/user")
public class UserController {
    @Autowired
    UserServiceImpl userService;
    @Autowired
    RedisUtils redisUtils;
    public  boolean userInfoToR(){
        List<User> userInfo = userService.getUserInfo();
        if (userInfo==null){
            return false;
        }
        for (User info :
                userInfo) {
            redisUtils.set("K", info.getAge());
            redisUtils.set("K",info.getCreateTime() );
            redisUtils.set("K",info.getEmail() );
            redisUtils.set("K", info.getUpdateTime());
            redisUtils.set("K",info.getId() );
            redisUtils.set("K", info.getVersion());
            redisUtils.set("K",info.getName());
        }
        return  true;
    }
}

