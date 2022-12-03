package com.bus.lscdensity.dockerjava.utils;

import com.github.dockerjava.api.DockerClient;
import com.github.dockerjava.api.async.ResultCallback;
import com.github.dockerjava.api.command.PullImageCmd;
import com.github.dockerjava.api.command.PullImageResultCallback;
import com.github.dockerjava.api.model.PullResponseItem;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.io.File;
import java.util.HashSet;

@Slf4j
public class DockerImagesUtils extends DockerClientConnect {
    /**
     * 拉取镜像
     * @param client
     */
    public static void pullImages(DockerClient client, String imageName,String tag) throws InterruptedException {
        client.pullImageCmd(imageName)
                .withTag(tag)
                .exec(new PullImageResultCallback()).awaitCompletion();
    }

    /**
     * 删除镜像
     * @param client
     * @param imageName
     */
    public  void removeImage(DockerClient client,String imageName){
        client.removeImageCmd(imageName).exec();
    }

    /**
     * 构建镜像
     * @param client
     */
    public void buildImage(DockerClient client){
        File file = new File("D/test.Dockerfile");
        HashSet<String> set = new HashSet<>();
        set.add("Test");
        client.buildImageCmd()
                .withDockerfile(file)
                .withTags(set);

    }

}
