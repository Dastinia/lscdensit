package com.bus.lscdensity.dockerjava.dockerservice;

import com.github.dockerjava.api.DockerClient;
import com.github.dockerjava.api.command.CreateContainerResponse;
import com.github.dockerjava.api.command.InspectContainerResponse;

public interface ContainsService {
    InspectContainerResponse getContainsInfo(DockerClient client, String containsName);

    CreateContainerResponse createContains(DockerClient client, String containsName, String imageName);

    void createContains(DockerClient client,String containsName,String imageName,Integer bindPort,Integer exposePort);

    void startContainer(DockerClient client,String containerName);

}
