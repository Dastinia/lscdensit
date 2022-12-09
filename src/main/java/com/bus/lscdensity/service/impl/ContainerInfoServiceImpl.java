package com.bus.lscdensity.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.bus.lscdensity.dockerjava.dockerservice.ContainsService;
import com.bus.lscdensity.dockerjava.dockerservice.impl.ContainsServiceImpl;
import com.bus.lscdensity.mapper.AiUnitInfoMapper;
import com.bus.lscdensity.mapper.ContainerInfoMapper;
import com.bus.lscdensity.pojo.AiUnitInfo;
import com.bus.lscdensity.pojo.ContainerInfo;
import com.bus.lscdensity.service.AiUnitInfoService;
import com.bus.lscdensity.service.ContainerInfoService;
import com.github.dockerjava.api.DockerClient;
import com.github.dockerjava.api.command.CreateContainerResponse;
import com.github.dockerjava.api.command.InspectContainerResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * @author Dastinia
 * @version 1.0
 * @date 2022/11/17
 */
@Service
public class ContainerInfoServiceImpl extends ServiceImpl<ContainerInfoMapper, ContainerInfo> implements ContainerInfoService {
    @Autowired
    ContainerInfoMapper containerInfoMapper;

    @Autowired
    ContainsServiceImpl containsService;

    @Override
    public List<ContainerInfo> getContainerInfo() {
        List<ContainerInfo> infos = containerInfoMapper.selectList(null);
        if(infos==null) {
            return null;
        }
        return infos;
    }

    // 创建容器信息入库
    public boolean saveByResponse(CreateContainerResponse response, DockerClient dockerClient, Integer aiUnitId, String serverIp, String name, String func) {
        InspectContainerResponse inspectContainerResponse = containsService.getContainsInfo(dockerClient, response.getId());

        ContainerInfo newContainerInfo = new ContainerInfo();
        newContainerInfo.setAiUnitId(aiUnitId);
        newContainerInfo.setContainerId(inspectContainerResponse.getId().substring(0,10));
        newContainerInfo.setContainerName(name);
        newContainerInfo.setContainerStatus(inspectContainerResponse.getState().getStatus());
        // 将prefix”sha256:“去掉，写入长度为10的镜像id
        newContainerInfo.setImageInformation(inspectContainerResponse.getImageId().substring(7,16));
        newContainerInfo.setContainerFunction(func);
        newContainerInfo.setCreateTime(new Date());
        // 设置docker服务器信息
        newContainerInfo.setServerIp(serverIp);
        newContainerInfo.setServerPort(7788); // todo 暂时写死

        return containerInfoMapper.insert(newContainerInfo) != 0;
    }
}
