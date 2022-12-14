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

       @Value("${docker.busmodelserviceimage.name}")
       String busModelImageName;

       @Value("${docker.busmodelserviceimage.label}")
       String busModelLabel;

       @Value("${docker.stationmodelserviceimage.name}")
       String stationModelImageName;

       @Value("${docker.stationmodelserviceimage.label}")
       String stationModelLabel;

       @Value("${docker.redis.name}")
       String redisImageName;

       @Value("${docker.redis.label}")
       String redisLabel;

       Map<String,DockerClient> dockerClientMap = new HashMap<>();
       int maxSize = 20; // map????????????

       @PostMapping("/stationService")
       public Result creatStationServiceContainer(@RequestBody RemoteInfo info) throws IOException {
              log.info(info.getAiUnitId());
              log.info(info.getKafkaServiceIp());
              log.info(info.getModelServiceIp());

              String aiUnitId = info.getAiUnitId();
              // ?????????
              init.run(aiUnitId);

              // ??????????????????????????????????????????????????????????????????
//              DockerClient controlRedisDClient = DockerClientConnect.connectDocker(info.getControlRedisIp());
//              DockerClient dataRedisDClient = DockerClientConnect.connectDocker(info.getModelServiceIp());
              DockerClient kafkaDClient = DockerClientConnect.connectDocker(info.getKafkaServiceIp(), 7788);
//              DockerClient moniterDClient = DockerClientConnect.connectDocker(info.getmoniterServiceIp(), "7788");
              DockerClient modelDClient = DockerClientConnect.connectDocker(info.getModelServiceIp(), 7789);

              // ??????????????????????????????, ?????????????????????????????????
              if (dockerClientMap.size() <= maxSize - 3) {
//                     dockerClientMap.put(info.getModelServiceIp(), dataRedisDClient);
                     dockerClientMap.put(info.getKafkaServiceIp() + "7788", kafkaDClient);
                     dockerClientMap.put(info.getModelServiceIp() + "7789", modelDClient);
              } else {
                     //todo ?????????LRU????????????
                     ;
              }

              // ????????????
//              CreateContainerResponse controlRedisResponse = containsService.createContainer(controlRedisDClient, "controlRedis-"+ aiUnitId, "redis:" + redisLabel, 6380,6379);
//              CreateContainerResponse dataRedisResponse = containsService.createContainer(dataRedisDClient,
//                      "dataRedis-"+ aiUnitId, redisImageName + ":" + redisLabel,6379,6379,new ArrayList<>());
              CreateContainerResponse kafkaServiceResponse = containsService.createContainer(kafkaDClient, "kafkaService-"+ aiUnitId, kafkaImageName + ":" + kafkaLabel);
//              CreateContainerResponse moniterServiceResponse = containsService.createContainer(moniterDClient, "moniterService-"+ info.getmoniterServiceIp(), moniterImageName + ":" + moniterLabel);

              // ??????????????????
              List<String> entryPoint = new ArrayList<String>() {
                     {
                            add("/bin/bash");
                            add("-c");
                            add("source ~/.bashrc && " +
                                    "source /etc/profile && " +
                                    "conda activate yolo5 && " +
                                    "cd /root/yolov5-keras-main && " +
                                    "python predictAuto.py");
                     }
              };
              List<Bind> bindList = new ArrayList<Bind>() {
                     {
//                            add(new Bind("/root/aibus/buslog", new Volume("/root/aibus/buslog")));
                            add(new Bind("/tmp/" + aiUnitId, new Volume("/tmp/.keras")));
                     }
              };
              CreateContainerResponse modelServiceResponse = containsService.createContainer(modelDClient,
                      "stationModelService-"+ aiUnitId,
                      stationModelImageName + ":" + stationModelLabel,
                      new ArrayList<>(),bindList,0L,0L, new ArrayList<Integer>(){{add(0);}}, entryPoint);

              // ?????????????????????
//              containerInfoService.saveByResponse(controlRedisResponse, controlRedisDClient, aiUnitId, info.getControlRedisIp(),"controlRedis" +aiUnitId, "?????????redis");
//              containerInfoService.saveByResponse(dataRedisResponse, dataRedisDClient, aiUnitId, info.getModelServiceIp(), "dataRedis"+ aiUnitId, "?????????redis");
              // todo docker????????????????????????
              containerInfoService.saveByResponse(kafkaServiceResponse, kafkaDClient, aiUnitId, info.getKafkaServiceIp(), 7788,"kafkaService"+ aiUnitId, "?????????????????????");
//              boolean moniterInfoSaved = containerInfoService.saveByResponse(moniterServiceResponse, kafkaDClient, aiUnitId, info.getmoniterServiceIp().substring(6,16),"moniterService-" + aiUnitId, "????????????");
              containerInfoService.saveByResponse(modelServiceResponse, modelDClient, aiUnitId, info.getModelServiceIp(), 7789,"stationModelService" + aiUnitId, "????????????????????????");
              log.info("????????????????????????");

              // ?????????
//              runContainer(info);

              return new Result(true,null,"????????????");
       }

       @PostMapping("/busService")
       public Result creatBusServiceContainer(@RequestBody RemoteInfo info) throws IOException {
              log.info(info.getAiUnitId());
              log.info(info.getKafkaServiceIp());
              log.info(info.getModelServiceIp());

              String aiUnitId = info.getAiUnitId();
              // ?????????
              init.run(aiUnitId);

              // ??????????????????????????????????????????????????????????????????
//              DockerClient controlRedisDClient = DockerClientConnect.connectDocker(info.getControlRedisIp());
//              DockerClient dataRedisDClient = DockerClientConnect.connectDocker(info.getModelServiceIp());
              DockerClient kafkaDClient = DockerClientConnect.connectDocker(info.getKafkaServiceIp(), 7788);
//              DockerClient moniterDClient = DockerClientConnect.connectDocker(info.getmoniterServiceIp());
//              DockerClient modelDClient = DockerClientConnect.connectDocker(info.getModelServiceIp());

              // ??????????????????????????????, ?????????????????????????????????
              if (dockerClientMap.size() <= maxSize - 3) {
//                     dockerClientMap.put(info.getModelServiceIp().substring(6,16), dataRedisDClient);
                     dockerClientMap.put(info.getKafkaServiceIp(), kafkaDClient);
//                     dockerClientMap.put(info.getModelServiceIp().substring(6,16), modelDClient);
              } else {
                     //todo ?????????LRU????????????
                     ;
              }

              // ????????????
//              CreateContainerResponse controlRedisResponse = containsService.createContainer(controlRedisDClient, "controlRedis-"+ aiUnitId, "redis:" + redisLabel, 6380,6379);
//              CreateContainerResponse dataRedisResponse = containsService.createContainer(dataRedisDClient,
//                      "dataRedis-"+ aiUnitId, redisImageName + ":" + redisLabel,6379,6379,new ArrayList<>());
              CreateContainerResponse kafkaServiceResponse = containsService.createContainer(kafkaDClient, "kafkaService-"+ aiUnitId, kafkaImageName + ":" + kafkaLabel);
//              CreateContainerResponse moniterServiceResponse = containsService.createContainer(moniterDClient, "moniterService-"+ info.getmoniterServiceIp(), moniterImageName + ":" + moniterLabel);
              // ??????????????????
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

              // ?????????????????????
//              containerInfoService.saveByResponse(controlRedisResponse, controlRedisDClient, aiUnitId, info.getControlRedisIp(),"controlRedis" +aiUnitId, "?????????redis");
//              containerInfoService.saveByResponse(dataRedisResponse, dataRedisDClient, aiUnitId, info.getModelServiceIp(), "dataRedis"+ aiUnitId, "?????????redis");
              containerInfoService.saveByResponse(kafkaServiceResponse, kafkaDClient, aiUnitId, info.getKafkaServiceIp(), 7788,"kafkaService"+ aiUnitId, "?????????????????????");
//              boolean moniterInfoSaved = containerInfoService.saveByResponse(moniterServiceResponse, kafkaDClient, aiUnitId, info.getmoniterServiceIp().substring(6,16),"moniterService-" + aiUnitId, "????????????");
//              containerInfoService.saveByResponse(modelServiceResponse, modelDClient, aiUnitId, info.getModelServiceIp(), "busModelService" + aiUnitId, "???????????????????????????");
              log.info("????????????????????????");

              // ?????????
//              runContainer(info);

              return new Result(true,null,"????????????");
       }



       @PostMapping("/run")
       public Result runContainer(@RequestBody RemoteInfo info) throws IOException {
              String aiUnitId = info.getAiUnitId();
              QueryWrapper<ContainerInfo> wrapper = new QueryWrapper<>();
              wrapper.eq("ai_unit_id", aiUnitId);
              wrapper.eq("container_status", "created");
              List<ContainerInfo> containerInfoList = containerInfoService.list(wrapper);
              if (containerInfoList.size() == 0) {
                     return new Result(false, null, info.getAiUnitId() + ":?????????????????????????????????");
              }
              for (ContainerInfo cInfo : containerInfoList) {
                     DockerClient dockerClient = dockerClientMap.get(cInfo.getServerIp() + cInfo.getServerPort());
                     if (dockerClient == null) {
                            dockerClient = DockerClientConnect.connectDocker(
                                    cInfo.getServerIp(), cInfo.getServerPort());
                     }
                     DockerContainsUtils.startContainer(dockerClient, cInfo.getContainerId());
                     ContainerInfo containerInfo = new ContainerInfo();
                     containerInfo.setContainerId(cInfo.getContainerId());
                     containerInfo.setContainerStatus("running");
                     containerInfoService.saveOrUpdate(containerInfo);
              }
              log.info("??????" + info.getAiUnitId() + "??????????????????");
              return new Result(true,null,"?????????????????????");
       }

       @PostMapping("/restart")
       public Result restartContainer(@RequestBody RemoteInfo info) throws IOException {
              String aiUnitId = info.getAiUnitId();
              QueryWrapper<ContainerInfo> wrapper = new QueryWrapper<>();
              wrapper.eq("ai_unit_id", aiUnitId);
              wrapper.ne("container_status", "deleted");
              List<ContainerInfo> containerInfoList = containerInfoService.list(wrapper);
              if (containerInfoList.size() == 0) {
                     return new Result(false, null, info.getAiUnitId() + ":?????????????????????????????????");
              }
              for (ContainerInfo cInfo : containerInfoList) {
                     DockerClient dockerClient = dockerClientMap.get(cInfo.getServerIp() + cInfo.getServerPort());
                     if (dockerClient == null) {
                            dockerClient = DockerClientConnect.connectDocker(
                                    cInfo.getServerIp(), cInfo.getServerPort());
                     }
                     DockerContainsUtils.restartContainer(dockerClient, cInfo.getContainerId());
                     ContainerInfo containerInfo = new ContainerInfo();
                     containerInfo.setContainerId(cInfo.getContainerId());
                     containerInfo.setContainerStatus("running");
                     containerInfoService.saveOrUpdate(containerInfo);
              }
              log.info("??????" + info.getAiUnitId() + "??????????????????");
              return new Result(true, null, info.getAiUnitId() + ":?????????????????????");
       }

       @PostMapping("/pause")
       public Result pauseContainer(@RequestBody RemoteInfo info) throws IOException {
              String aiUnitId = info.getAiUnitId();
              QueryWrapper<ContainerInfo> wrapper = new QueryWrapper<>();
              wrapper.eq("ai_unit_id", aiUnitId);
              wrapper.eq("container_status", "running");
              List<ContainerInfo> containerInfoList = containerInfoService.list(wrapper);
              if (containerInfoList.size() == 0) {
                     return new Result(false, null, info.getAiUnitId() + ":???????????????????????????");
              }
              for (ContainerInfo cInfo : containerInfoList) {
                     DockerClient dockerClient = dockerClientMap.get(cInfo.getServerIp() + cInfo.getServerPort());
                     if (dockerClient == null) {
                            dockerClient = DockerClientConnect.connectDocker(
                                    cInfo.getServerIp(), cInfo.getServerPort());
                     }
                     DockerContainsUtils.pauseContainer(dockerClient, cInfo.getContainerId());
                     ContainerInfo containerInfo = new ContainerInfo();
                     containerInfo.setContainerId(cInfo.getContainerId());
                     containerInfo.setContainerStatus("paused");
                     containerInfoService.update(containerInfo, null);
              }
              log.info("??????" + info.getAiUnitId() + "??????????????????");
              return new Result(true, null, info.getAiUnitId() + "?????????????????????");
       }

       @PostMapping("/unpause")
       public Result unpauseContainer(@RequestBody RemoteInfo info) throws IOException {
              String aiUnitId = info.getAiUnitId();
              QueryWrapper<ContainerInfo> wrapper = new QueryWrapper<>();
              wrapper.eq("ai_unit_id", aiUnitId);
              wrapper.eq("container_status", "paused");
              List<ContainerInfo> containerInfoList = containerInfoService.list(wrapper);
              if (containerInfoList.size() == 0) {
                     return new Result(false, null, info.getAiUnitId() + ":???????????????????????????");
              }
              for (ContainerInfo cInfo : containerInfoList) {
                     DockerClient dockerClient = dockerClientMap.get(cInfo.getServerIp() + cInfo.getServerPort());
                     if (dockerClient == null) {
                            dockerClient = DockerClientConnect.connectDocker(
                                    cInfo.getServerIp(), cInfo.getServerPort());
                     }
                     DockerContainsUtils.unpauseContainer(dockerClient, cInfo.getContainerId());
                     ContainerInfo containerInfo = new ContainerInfo();
                     containerInfo.setContainerId(cInfo.getContainerId());
                     containerInfo.setContainerStatus("running");
                     containerInfoService.saveOrUpdate(containerInfo);
              }
              log.info("??????" + info.getAiUnitId() + "??????????????????????????????");
              return new Result(true, null, info.getAiUnitId() + "?????????????????????????????????");
       }

       @PostMapping("/stop")
       public Result stopContainer(@RequestBody RemoteInfo info) throws IOException {
              String aiUnitId = info.getAiUnitId();
              QueryWrapper<ContainerInfo> wrapper = new QueryWrapper<>();
              wrapper.eq("ai_unit_id", aiUnitId);
              wrapper.eq("container_status", "running");
              for (ContainerInfo cInfo : containerInfoService.list(wrapper)) {
                     DockerClient dockerClient = dockerClientMap.get(cInfo.getServerIp() + cInfo.getServerPort());
                     if (dockerClient == null) {
                            dockerClient = DockerClientConnect.connectDocker(
                                    cInfo.getServerIp(), cInfo.getServerPort());
                     }
                     DockerContainsUtils.stopContainer(dockerClient, cInfo.getContainerId());
                     ContainerInfo containerInfo = new ContainerInfo();
                     containerInfo.setContainerId(cInfo.getContainerId());
                     containerInfo.setContainerStatus("exited");
                     containerInfoService.saveOrUpdate(containerInfo);
              }
              log.info("??????" + info.getAiUnitId() + "??????????????????");
              return new Result(true, null, info.getAiUnitId() + "?????????????????????");
       }

       @PostMapping("/remove")
       public Result deleteContainer(@RequestBody RemoteInfo info) throws IOException {
              String aiUnitId = info.getAiUnitId();
              QueryWrapper<ContainerInfo> wrapper = new QueryWrapper<>();
              wrapper.eq("ai_unit_id", aiUnitId).eq("container_status", "exited");
              List<ContainerInfo> containerInfoList = containerInfoService.getContainerInfo();
              for (ContainerInfo cInfo : containerInfoList) {
                     DockerClient dockerClient = dockerClientMap.get(cInfo.getServerIp() + cInfo.getServerPort());
                     if (dockerClient == null) {
                            dockerClient = DockerClientConnect.connectDocker(
                                    cInfo.getServerIp(), cInfo.getServerPort());
                     }
                     DockerContainsUtils.removeContainer(dockerClient, cInfo.getContainerId());
                     ContainerInfo containerInfo = new ContainerInfo();
                     containerInfo.setContainerId(cInfo.getContainerId());
                     containerInfo.setContainerStatus("deleted");
                     containerInfoService.saveOrUpdate(containerInfo);
              }
              log.info("??????" + info.getAiUnitId() + "??????????????????");
              return new Result(true, null, info.getAiUnitId() + "?????????????????????");
       }
}
