package com.bus.lscdensity.dockerjava.utils;

import com.alibaba.fastjson.JSONObject;
import com.github.dockerjava.api.DockerClient;
import com.github.dockerjava.api.model.Info;
import com.github.dockerjava.core.DefaultDockerClientConfig;
import com.github.dockerjava.core.DockerClientBuilder;
import com.github.dockerjava.core.DockerClientConfig;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
public  class DockerClientConnect{
    /**
     * 连接docker服务器
     * @return
     */
    public static DockerClient connectDocker(){
        DockerClientConfig dockerClient = DefaultDockerClientConfig.createDefaultConfigBuilder().build();
        DockerClient instance = DockerClientBuilder.getInstance(dockerClient).build();
        Info info = instance.infoCmd().exec();
        String infoStr = JSONObject.toJSONString(info);
        log.info("docker的环境信息如下" +
                "=================");
        log.info(""+infoStr);
        return instance;
    }

    public static DockerClient connectDocker(String ip, Integer port){
        String host = createHost("tcp", ip, String.valueOf(port));
        DockerClientConfig dockerClient = DefaultDockerClientConfig.createDefaultConfigBuilder()
                .withDockerHost(host.trim())
                .build();
        return DockerClientBuilder.getInstance(dockerClient).build();
    }

    public static String createHost(String protocol, String ip, String port) {
        return protocol + "://" + ip + ":" + port;
    }
}
