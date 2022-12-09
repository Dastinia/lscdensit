package com.bus.lscdensity.dockerjava.dockerservice.impl;

import com.bus.lscdensity.dockerjava.dockerservice.ContainsService;
import com.github.dockerjava.api.DockerClient;
import com.github.dockerjava.api.command.CreateContainerResponse;
import com.github.dockerjava.api.command.InspectContainerResponse;
import com.github.dockerjava.api.model.*;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static com.github.dockerjava.api.model.HostConfig.newHostConfig;

@Service
public class ContainsServiceImpl implements ContainsService {
    @Override
    public InspectContainerResponse getContainsInfo(DockerClient client, String containsName) {
        Info exec = client.infoCmd().exec();
        return client.inspectContainerCmd(containsName).exec();
    }

    @Override
    public CreateContainerResponse createContainer(DockerClient client, String containsName, String imageName) {
        return client.createContainerCmd(imageName)
                .withName(containsName)
                .withHostConfig(newHostConfig()
                .withNetworkMode("host"))
                .exec();
    }

    @Override
    public CreateContainerResponse createContainer(DockerClient client, String containsName, String imageName, Integer bindPort, Integer exposePort, List<String> envList) {
        return client.createContainerCmd(imageName)
                .withName(containsName)
                .withEnv(envList)
                .withHostConfig(newHostConfig().withPortBindings(new Ports(new ExposedPort(exposePort), Ports.Binding.bindPort(bindPort))))
                .exec();
    }

    @Override
    public CreateContainerResponse createContainer(DockerClient client, String containsName, String imageName, List<String> envList, List<Bind> bindList, Long cpu, Long memory, List<Integer> gpuList, List<String> entryPoint) {
        HostConfig hostConfig = new HostConfig()
                .withBinds(bindList)
                .withMemory(memory)
                .withCpuCount(cpu)
                .withReadonlyRootfs(true)
                .withLogConfig(new LogConfig(LogConfig.LoggingType.JSON_FILE))
                .withBinds(bindList);
        if (gpuList.size() > 0) {
            List<DeviceRequest> deviceRequests = new ArrayList<>();
            DeviceRequest deviceRequest = new DeviceRequest();

            List<String> deviceIds = new ArrayList<>();
            for (Integer index : gpuList) {
                deviceIds.add(String.valueOf(index));
            }
            List<List<String>> capabilities = new ArrayList<>();
            List<String> capability = new ArrayList<>();
            capability.add("gpu");
            capabilities.add(capability);

            // withCount 为-1时启用全部gpu， 为0时传入gpuId列表指定
            deviceRequest.withDriver("").withCount(0).withDeviceIds(deviceIds).withOptions(new HashMap<>()).withCapabilities(capabilities);
            deviceRequests.add(deviceRequest);

            hostConfig.withDeviceRequests(deviceRequests);
        }
        return client.createContainerCmd(imageName)
                .withName(containsName)
                .withEnv(envList)
                .withHostConfig(hostConfig)
                .withCmd(entryPoint)
//                .withEntrypoint("bash")
                .exec();
    }

    public CreateContainerResponse createGPUContainer(DockerClient client, String containsName, String imageName) {
        return client.createContainerCmd(imageName)
                .withName(containsName)
                .withHostConfig(newHostConfig())
                .exec();
    }

    @Override
    public void startContainer(DockerClient client, String containerName) {
        client.startContainerCmd(containerName).exec();
    }
}
