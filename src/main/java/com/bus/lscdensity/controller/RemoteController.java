package com.bus.lscdensity.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.bus.lscdensity.Initial.Init;
import com.bus.lscdensity.common.Result;
import com.bus.lscdensity.dockerjava.dockerservice.impl.ContainsServiceImpl;
import com.bus.lscdensity.dockerjava.utils.DockerClientConnect;
import com.bus.lscdensity.dockerjava.utils.DockerContainsUtils;
import com.bus.lscdensity.pojo.ContainerInfo;
import com.bus.lscdensity.pojo.RemoteInfo;
import com.bus.lscdensity.service.impl.ContainerInfoServiceImpl;
import com.github.dockerjava.api.DockerClient;
import com.github.dockerjava.api.command.CreateContainerResponse;
import com.github.dockerjava.api.model.Bind;
import com.github.dockerjava.api.model.Volume;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.*;

@RestController
@RequestMapping("remote")
@Slf4j
public class RemoteController {
       @Autowired
       ContainsServiceImpl containsService;

       @Autowired
       ContainerInfoServiceImpl containerInfoService;

       @Autowired
       Init init;

       @Value("${docker.kafkaserviceimage.name}")
       String kafkaImageName;

       @Value("${docker.kafkaserviceimage.label}")
       String kafkaLabel;

       @Value("${docker.watchserviceimage.name}")
       String watchImageName;

       @Value("${docker.watchserviceimage.label}")
       String watchLabel;

       @Value("${docker.modelserviceimage.name}")
       String modelImageName;

       @Value("${docker.modelserviceimage.label}")
       String modelLabel;

       @Value("${docker.redis.name}")
       String redisImageName;

       @Value("${docker.redis.label}")
       String redisLabel;

       Map<String,DockerClient> dockerClientMap = new HashMap<>();
       int maxSize = 20; // map的最大容

       @PostMapping("/create")
       public Result creatContainer(@RequestBody RemoteInfo info) throws IOException {
              log.info(info.getAiUnitId());
              log.info(info.getKafkaServiceIp());
              log.info(info.getModelServiceIp());

              String aiUnitId = info.getAiUnitId();
              // 初始化
              init.run(aiUnitId);

              // 连接镜像所在服务器，镜像提前在服务器中准备好
//              DockerClient controlRedisDClient = DockerClientConnect.connectDocker(info.getControlRedisIp());
//              DockerClient dataRedisDClient = DockerClientConnect.connectDocker(info.getModelServiceIp());
              DockerClient kafkaDClient = DockerClientConnect.connectDocker(info.getKafkaServiceIp());
//              DockerClient moniterDClient = DockerClientConnect.connectDocker(info.getmoniterServiceIp());
//              DockerClient modelDClient = DockerClientConnect.connectDocker(info.getModelServiceIp());

              // 将已经建立的链接入队, 容量不足则先出队再入队
              if (dockerClientMap.size() <= maxSize - 3) {
//                     dockerClientMap.put(info.getModelServiceIp().substring(6,16), dataRedisDClient);
                     dockerClientMap.put(info.getKafkaServiceIp(), kafkaDClient);
//                     dockerClientMap.put(info.getModelServiceIp().substring(6,16), modelDClient);
              } else {
                     //todo 可以用LRU调度策略
                     ;
              }

              // 创建镜像
//              CreateContainerResponse controlRedisResponse = containsService.createContainer(controlRedisDClient, "controlRedis-"+ aiUnitId, "redis:" + redisLabel, 6380,6379);
//              CreateContainerResponse dataRedisResponse = containsService.createContainer(dataRedisDClient,
//                      "dataRedis-"+ aiUnitId, redisImageName + ":" + redisLabel,6379,6379,new ArrayList<>());
              CreateContainerResponse kafkaServiceResponse = containsService.createContainer(kafkaDClient, "kafkaService-"+ aiUnitId, kafkaImageName + ":" + kafkaLabel);
//              CreateContainerResponse moniterServiceResponse = containsService.createContainer(moniterDClient, "moniterService-"+ info.getmoniterServiceIp(), moniterImageName + ":" + moniterLabel);
              // 创建模型镜像
//              List<String> entryPoint = new ArrayList<String>() {
//                     {
//                            add("/bin/bash");
//                            add("-c");
//                            add("source ~/.bashrc && bash /root/aibus/bus.sh && tail -f /dev/null");
//                     }
//              };
//              List<Bind> bindList = new ArrayList<Bind>() {
//                     {
//                            add(new Bind("/root/aibus/buslog", new Volume("/root/aibus/buslog")));
//                            add(new Bind("/tmp", new Volume("/tmp")));
//                     }
//              };
//              CreateContainerResponse modelServiceResponse = containsService.createContainer(modelDClient, "busModelService-"+ aiUnitId, "busmodelservice:1.0",new ArrayList<>(),bindList,0L,0L, new ArrayList<Integer>(){{add(0);}}, entryPoint);

              // 新容器信息入库
//              containerInfoService.saveByResponse(controlRedisResponse, controlRedisDClient, aiUnitId, info.getControlRedisIp(),"controlRedis" +aiUnitId, "控制流redis");
//              containerInfoService.saveByResponse(dataRedisResponse, dataRedisDClient, aiUnitId, info.getModelServiceIp(), "dataRedis"+ aiUnitId, "数据流redis");
              containerInfoService.saveByResponse(kafkaServiceResponse, kafkaDClient, aiUnitId, info.getKafkaServiceIp(),"kafkaService"+ aiUnitId, "卡夫卡抓取服务");
//              boolean moniterInfoSaved = containerInfoService.saveByResponse(moniterServiceResponse, kafkaDClient, aiUnitId, info.getmoniterServiceIp().substring(6,16),"moniterService-" + aiUnitId, "监控服务");
//              containerInfoService.saveByResponse(modelServiceResponse, modelDClient, aiUnitId, info.getModelServiceIp(), "busModelService" + aiUnitId, "公交车预测模型服务");
              log.info("容器数据入库成功");

              // 测试用
//              runContainer(info);

              return new Result(true,null,"创建成功");
       }

       @PostMapping("/run")
       public Result runContainer(@RequestBody RemoteInfo info) throws IOException {
              String aiUnitId = info.getAiUnitId();
              QueryWrapper<ContainerInfo> wrapper = new QueryWrapper<>();
              wrapper.eq("ai_unit_id", aiUnitId);
//              wrapper.eq("container_status", "Crea");
              List<ContainerInfo> containerInfoList = containerInfoService.list(wrapper);
              if (containerInfoList.size() == 0) {
                     return new Result(false, null, info.getAiUnitId() + ":服务已删除，请重新创建");
              }
              for (ContainerInfo cInfo : containerInfoList) {
                     DockerClient dockerClient = dockerClientMap.get(cInfo.getServerIp());
                     if (dockerClient == null) {
                            dockerClient = DockerClientConnect.connectDocker(
                                    "tcp://" + cInfo.getServerIp() + ":" + cInfo.getServerPort());
                     }
                     DockerContainsUtils.startContainer(dockerClient, cInfo.getContainerId());
                     ContainerInfo containerInfo = new ContainerInfo();
                     containerInfo.setContainerId(cInfo.getContainerId());
                     containerInfo.setContainerStatus("running");
                     containerInfoService.saveOrUpdate(containerInfo);
              }
              log.info("服务" + info.getAiUnitId() + "全容器已运行");
              return new Result(true,null,"全部容器已运行");
       }

       @PostMapping("/restart")
       public Result restartContainer(@RequestBody RemoteInfo info) throws IOException {
              String aiUnitId = info.getAiUnitId();
              QueryWrapper<ContainerInfo> wrapper = new QueryWrapper<>();
              wrapper.eq("ai_unit_id", aiUnitId);
              wrapper.ne("container_status", "deleted");
              List<ContainerInfo> containerInfoList = containerInfoService.list(wrapper);
              if (containerInfoList.size() == 0) {
                     return new Result(false, null, info.getAiUnitId() + ":服务已删除，请重新创建");
              }
              for (ContainerInfo cInfo : containerInfoList) {
                     DockerClient dockerClient = dockerClientMap.get(cInfo.getServerIp());
                     if (dockerClient == null) {
                            dockerClient = DockerClientConnect.connectDocker(
                                    "tcp://" + cInfo.getServerIp() + ":" + cInfo.getServerPort());
                     }
                     DockerContainsUtils.restartContainer(dockerClient, cInfo.getContainerId());
                     ContainerInfo containerInfo = new ContainerInfo();
                     containerInfo.setContainerId(cInfo.getContainerId());
                     containerInfo.setContainerStatus("running");
                     containerInfoService.saveOrUpdate(containerInfo);
              }
              log.info("服务" + info.getAiUnitId() + "全容器已重启");
              return new Result(true, null, info.getAiUnitId() + ":全部容器已重启");
       }

       @PostMapping("/pause")
       public Result pauseContainer(@RequestBody RemoteInfo info) throws IOException {
              String aiUnitId = info.getAiUnitId();
              QueryWrapper<ContainerInfo> wrapper = new QueryWrapper<>();
              wrapper.eq("ai_unit_id", aiUnitId);
              wrapper.eq("container_status", "running");
              List<ContainerInfo> containerInfoList = containerInfoService.list(wrapper);
              if (containerInfoList.size() == 0) {
                     return new Result(false, null, info.getAiUnitId() + ":请确认服务正在运行");
              }
              for (ContainerInfo cInfo : containerInfoList) {
                     DockerClient dockerClient = dockerClientMap.get(cInfo.getServerIp());
                     if (dockerClient == null) {
                            dockerClient = DockerClientConnect.connectDocker(
                                    "tcp://" + cInfo.getServerIp() + ":" + cInfo.getServerPort());
                     }
                     DockerContainsUtils.pauseContainer(dockerClient, cInfo.getContainerId());
                     ContainerInfo containerInfo = new ContainerInfo();
                     containerInfo.setContainerId(cInfo.getContainerId());
                     containerInfo.setContainerStatus("paused");
                     containerInfoService.update(containerInfo, null);
              }
              log.info("服务" + info.getAiUnitId() + "全容器已暂停");
              return new Result(true, null, info.getAiUnitId() + "全部容器已暂停");
       }

       @PostMapping("/unpause")
       public Result unpauseContainer(@RequestBody RemoteInfo info) throws IOException {
              String aiUnitId = info.getAiUnitId();
              QueryWrapper<ContainerInfo> wrapper = new QueryWrapper<>();
              wrapper.eq("ai_unit_id", aiUnitId);
              wrapper.eq("container_status", "paused");
              List<ContainerInfo> containerInfoList = containerInfoService.list(wrapper);
              if (containerInfoList.size() == 0) {
                     return new Result(false, null, info.getAiUnitId() + ":请确认服务处于暂停");
              }
              for (ContainerInfo cInfo : containerInfoList) {
                     DockerClient dockerClient = dockerClientMap.get(cInfo.getServerIp());
                     if (dockerClient == null) {
                            dockerClient = DockerClientConnect.connectDocker(
                                    "tcp://" + cInfo.getServerIp() + ":" + cInfo.getServerPort());
                     }
                     DockerContainsUtils.unpauseContainer(dockerClient, cInfo.getContainerId());
                     ContainerInfo containerInfo = new ContainerInfo();
                     containerInfo.setContainerId(cInfo.getContainerId());
                     containerInfo.setContainerStatus("running");
                     containerInfoService.saveOrUpdate(containerInfo);
              }
              log.info("服务" + info.getAiUnitId() + "全容器已从暂停中恢复");
              return new Result(true, null, info.getAiUnitId() + "全部容器已从暂停中恢复");
       }

       @PostMapping("/stop")
       public Result stopContainer(@RequestBody RemoteInfo info) throws IOException {
              String aiUnitId = info.getAiUnitId();
              QueryWrapper<ContainerInfo> wrapper = new QueryWrapper<>();
              wrapper.eq("ai_unit_id", aiUnitId);
              wrapper.eq("container_status", "running");
              for (ContainerInfo cInfo : containerInfoService.list(wrapper)) {
                     DockerClient dockerClient = dockerClientMap.get(cInfo.getServerIp());
                     if (dockerClient == null) {
                            dockerClient = DockerClientConnect.connectDocker(
                                    "tcp://" + cInfo.getServerIp() + ":" + cInfo.getServerPort());
                     }
                     DockerContainsUtils.stopContainer(dockerClient, cInfo.getContainerId());
                     ContainerInfo containerInfo = new ContainerInfo();
                     containerInfo.setContainerId(cInfo.getContainerId());
                     containerInfo.setContainerStatus("exited");
                     containerInfoService.saveOrUpdate(containerInfo);
              }
              log.info("服务" + info.getAiUnitId() + "全容器已停止");
              return new Result(true, null, info.getAiUnitId() + "全部容器已停止");
       }

       @PostMapping("/remove")
       public Result deleteContainer(@RequestBody RemoteInfo info) throws IOException {
              String aiUnitId = info.getAiUnitId();
              QueryWrapper<ContainerInfo> wrapper = new QueryWrapper<>();
              wrapper.eq("ai_unit_id", aiUnitId).eq("container_status", "exited");
              List<ContainerInfo> containerInfoList = containerInfoService.getContainerInfo();
              for (ContainerInfo cInfo : containerInfoList) {
                     DockerClient dockerClient = dockerClientMap.get(cInfo.getServerIp());
                     if (dockerClient == null) {
                            dockerClient = DockerClientConnect.connectDocker(
                                    "tcp://" + cInfo.getServerIp() + ":" + cInfo.getServerPort());
                     }
                     DockerContainsUtils.removeContainer(dockerClient, cInfo.getContainerId());
                     ContainerInfo containerInfo = new ContainerInfo();
                     containerInfo.setContainerId(cInfo.getContainerId());
                     containerInfo.setContainerStatus("deleted");
                     containerInfoService.saveOrUpdate(containerInfo);
              }
              log.info("服务" + info.getAiUnitId() + "全容器已删除");
              return new Result(true, null, info.getAiUnitId() + "全部容器已删除");
       }
}
