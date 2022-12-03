package com.bus.lscdensity.dockerjava.dockerservice.impl;

import com.bus.lscdensity.dockerjava.dockerservice.ContainsService;
import com.github.dockerjava.api.DockerClient;
import com.github.dockerjava.api.command.CreateContainerResponse;
import com.github.dockerjava.api.command.InspectContainerResponse;
import com.github.dockerjava.api.model.Bind;
import com.github.dockerjava.api.model.Info;
import com.github.dockerjava.api.model.Volume;
import org.springframework.stereotype.Service;

import static com.github.dockerjava.api.model.HostConfig.newHostConfig;

@Service
public class ContainsServiceImpl implements ContainsService {
    @Override
    public InspectContainerResponse getContainsInfo(DockerClient client, String containsName) {
        Info exec = client.infoCmd().exec();
        return client.inspectContainerCmd(containsName).exec();
    }

    @Override
    public CreateContainerResponse createContains(DockerClient client, String containsName, String imageName) {
        return client.createContainerCmd(imageName)
                .withName(containsName)
                .withHostConfig(newHostConfig()
                .withNetworkMode("host")
                .withBinds(new Bind("/dockerjar/hosts",new Volume("/etc/hosts"))))
                .exec();
    }

    @Override
    public void createContains(DockerClient client,String containsName,String imageName, Integer bindPort, Integer exposePort) {

    }

    @Override
    public void startContainer(DockerClient client, String containerName) {
        client.startContainerCmd(containerName).exec();
    }
}
