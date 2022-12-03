package com.bus.lscdensity.uncontroller;


import com.bus.lscdensity.pojo.PicturePathInfo;
import com.bus.lscdensity.service.impl.PicturePathInfoServiceImpl;
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
@RequestMapping("/lscdensit/picture-path-info")
public class PicturePathInfoController {
   @Autowired
    PicturePathInfoServiceImpl picturePathInfoService;
   @Autowired
    RedisUtils redisUtils;
   public  boolean picturePathInfoToR(){
       List<PicturePathInfo> picturePathInfo = picturePathInfoService.getPicturePathInfo();
       if (picturePathInfo==null){
           return false;
       }
       for (PicturePathInfo info :
               picturePathInfo) {
           redisUtils.set("K",info.getPicturePath());
           redisUtils.set("K",info.getServiceId());
           redisUtils.set("K",info.getStoreIp());
           redisUtils.set("K",info.getTime());
           redisUtils.set("K",info.getLine());
           redisUtils.set("K",info.getId());
           redisUtils.set("K",info.getCompany());
           redisUtils.set("K",info.getBuscode());
       }
       return true;
   }
}

