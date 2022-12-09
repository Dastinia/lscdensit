package com.bus.lscdensity.dockerjava.dockerservice;

import com.github.dockerjava.api.DockerClient;
import com.github.dockerjava.api.command.CreateContainerResponse;
import com.github.dockerjava.api.command.InspectContainerResponse;
import com.github.dockerjava.api.model.Bind;

import java.util.List;

public interface ContainsService {
    InspectContainerResponse getContainsInfo(DockerClient client, String containsName);

    CreateContainerResponse createContainer(DockerClient client, String containsName, String imageName);

    CreateContainerResponse createContainer(DockerClient client, String containsName, String imageName,
                                            Integer bindPort, Integer exposePort, List<String> envList);

    CreateContainerResponse createContainer(DockerClient client, String containsName, String imageName,
                                            List<String> envList, List<Bind> bindList, Long cpu, Long memory, List<Integer> gpuList, List<String> entryPoint);

    void startContainer(DockerClient client,String containerName);
}
