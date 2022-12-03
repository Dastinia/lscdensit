package com.bus.lscdensity.dockerjava.utils;

import com.github.dockerjava.api.DockerClient;
import com.github.dockerjava.api.command.CreateContainerResponse;
import com.github.dockerjava.api.command.InspectContainerResponse;
import com.github.dockerjava.api.model.Bind;
import com.github.dockerjava.api.model.ExposedPort;
import com.github.dockerjava.api.model.Ports;
import com.github.dockerjava.api.model.Volume;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import static com.github.dockerjava.api.model.HostConfig.newHostConfig;

@Slf4j
@Component
public class DockerContainsUtils {
        /**
         * 创建容器
         * @param client
         * @return
         */
        public static CreateContainerResponse createContainers(DockerClient client,String containerName,String imageName,Integer exposeport,Integer bindport){
            //CreateContainerResponse response = client.createContainerCmd(containerName).exec();
            if (bindport==null&&exposeport==null){
                return client.createContainerCmd(imageName)
                        .withName(containerName)
                        .withHostConfig(newHostConfig()
                        .withNetworkMode("host")
                        .withBinds(new Bind("/dockerjar/hosts",new Volume("/etc/hosts"))))
                        .exec();
            }
            Ports portBindings = new Ports();
            portBindings.bind(ExposedPort.tcp(exposeport), Ports.Binding.bindPort(bindport));

            return client.createContainerCmd(imageName)
                    .withName(containerName)
                    .withVolumes(new Volume("/dockerjar/hosts"),new Volume("/etc/hosts"))
                    .withHostConfig(newHostConfig()
                    .withPortBindings(portBindings)
                    .withNetworkMode("host"))
                    .withExposedPorts( new ExposedPort(exposeport)).exec();
        }

        /**
         * 启动容器
         * @param client
         * @param containerId
         */
        public static void startContainer(DockerClient client,String containerId){
            try {
                client.startContainerCmd(containerId).exec();
            }catch (Exception e){
                e.printStackTrace();
            }
        }

        /**
         * 删除容器
         * @param client
         * @param containerId
         */
        public static void removeContainer(DockerClient client,String containerId){
            client.removeContainerCmd(containerId).exec();
        }

    /**
     * 暂停容器
     * @param dockerClient
     * @param containerId
     */
    public static void pauseContainer(DockerClient dockerClient, String containerId) {
        dockerClient.pauseContainerCmd(containerId).exec();
    }

    /**
     * 重启容器
     * @param dockerClient
     * @param containerId
     * @return
     */
    public static void restartContainer(DockerClient dockerClient,String containerId){
        dockerClient.restartContainerCmd(containerId).exec();
    }

    /**
     * 取消暂停容器
     * @param dockerClient
     * @param containerId
     */
    public static void unpauseContainer(DockerClient dockerClient, String containerId) {
         dockerClient.unpauseContainerCmd(containerId).exec();
    }

    public static void stopContainer(DockerClient dockerClient,String containerId) {
        dockerClient.stopContainerCmd(containerId).exec();
    }

    /**
     *根据容器创建镜像
     * @param dockerClient
     * @param imageId
     */
    public static void commitContainer(DockerClient dockerClient,String imageId){

        dockerClient.commitCmd(imageId).exec();
    }

    /**
     * 获取容器信息
     * @param dockerClient
     * @param containerId
     */
    public static InspectContainerResponse getContainerInfo(DockerClient dockerClient,String containerId){
        InspectContainerResponse containerResponse = dockerClient.inspectContainerCmd(containerId).exec();
        if (containerResponse==null){
            log.error("容器Id有误或容器不存在");
        }
        return containerResponse;
    }
}

