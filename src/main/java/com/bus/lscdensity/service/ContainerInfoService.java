package com.bus.lscdensity.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.bus.lscdensity.pojo.AiUnitInfo;
import com.bus.lscdensity.pojo.ContainerInfo;
import com.github.dockerjava.api.DockerClient;
import com.github.dockerjava.api.command.CreateContainerResponse;

import java.util.List;

/**
 * @author Dastinia
 * @version 1.0
 * @date 2022/11/17
 */
public interface ContainerInfoService extends IService<ContainerInfo> {
    List<ContainerInfo> getContainerInfo();
    public boolean saveByResponse(CreateContainerResponse response, DockerClient dockerClient, String aiUnitId, String name, String func, String serverIp);
}
