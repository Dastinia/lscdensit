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
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("remote")
@Slf4j
public class RemoteStartController {
       @Autowired
       ContainsServiceImpl containsService;

       @Autowired
       ContainerInfoServiceImpl containerInfoService;

       @Autowired
       Init init;

       @Value("${myserver.user}")
       String userName;

       @Value("${myserver.password}")
       String passWord;

       @Value("${watchservicecmd}")
       String watchCmd;

       @Value("${aiservicecmd}")
       String aiCmd;

       @PostMapping("/new")
       public Result createContainer(@RequestBody RemoteInfo info) throws IOException {
              log.info("ip:{}",info.getDockerIp());
              // 初始化
              // todo 坑:不同的ai服务不能有相同的grabIp
              init.run(info.getDockerIp());
              // 连接镜像所在服务器，镜像提前在服务器中准备好
              DockerClient dockerClient = DockerClientConnect.connectDocker(info.getDockerIp());

              // 判断请求id的服务镜像是否存在
              String aiUnitId = info.getAiUnitId();
              QueryWrapper<ContainerInfo> wrapper = new QueryWrapper<>();
              wrapper.eq("ai_unit_id", aiUnitId);
              int existed = containerInfoService.count(wrapper);
              if (existed != 0) {
                     return new Result(false,null,aiUnitId+":全部容器已新建");
              }

              CreateContainerResponse kafkaServiceResponse = containsService.createContains(dockerClient, "kafkaService-"+ aiUnitId, "kafkaservice:1.0");
              CreateContainerResponse watchServiceResponse = containsService.createContains(dockerClient, "watchService-"+ aiUnitId, "watchservice:1.0");

              // 启动容器
              containsService.startContainer(dockerClient, kafkaServiceResponse.getId());
              log.info( "kafkaId:{}",kafkaServiceResponse.getId());
              containsService.startContainer(dockerClient, watchServiceResponse.getId());
              log.info( "watchId:{}",kafkaServiceResponse.getId());

              // 新容器信息入库
              boolean kafkaInfoSaved = containerInfoService.saveByResponse(kafkaServiceResponse, dockerClient, aiUnitId, "kafkaService-" + aiUnitId, "卡夫卡抓取服务-" + aiUnitId, info.getServerIp());
              boolean watchInfoSaved = containerInfoService.saveByResponse(watchServiceResponse, dockerClient, aiUnitId, "watchService-" + aiUnitId, "监控服务-" + aiUnitId, info.getServerIp());
              if (kafkaInfoSaved && watchInfoSaved) {
                     log.info("容器数据入库成功");
              } else {
                     log.info("容器数据入库失败");
              }
              // 启动模型
//              SSHUtils sshUtils = new SSHUtils(userName, passWord, info.getServerIp());

              // todo 将模型上容器，若不行也需要改sh文件路径
              //sshUtils.exec(aiCmd);
              return new Result(true,null,"创建成功");
       }

       @PostMapping("/restart")
       public Result restartContainer(@RequestBody RemoteInfo info) throws IOException {
              DockerClient dockerClient = DockerClientConnect.connectDocker(info.getDockerIp());

              String aiUnitId = info.getAiUnitId();
              QueryWrapper<ContainerInfo> wrapper = new QueryWrapper<>();
              wrapper.eq("ai_unit_id", aiUnitId);
              wrapper.ne("container_status", "deleted");
              List<ContainerInfo> containerInfoList = containerInfoService.list(wrapper);
              if (containerInfoList.size() == 0) {
                     return new Result(false, null, info.getAiUnitId() + ":服务已删除，请重新创建");
              }
              for (ContainerInfo cInfo : containerInfoList) {
                     DockerContainsUtils.restartContainer(dockerClient, cInfo.getContainerId());
                     ContainerInfo containerInfo = new ContainerInfo();
                     containerInfo.setContainerId(cInfo.getContainerId());
                     containerInfo.setContainerStatus("running");
                     containerInfoService.saveOrUpdate(containerInfo);
              }
              return new Result(true, null, info.getAiUnitId() + ":全部容器已重启");
       }

       @PostMapping("/pause")
       public Result pauseContainer(@RequestBody RemoteInfo info) throws IOException {
              DockerClient dockerClient = DockerClientConnect.connectDocker(info.getDockerIp());

              String aiUnitId = info.getAiUnitId();
              QueryWrapper<ContainerInfo> wrapper = new QueryWrapper<>();
              wrapper.eq("ai_unit_id", aiUnitId);
              wrapper.eq("container_status", "running");
              List<ContainerInfo> containerInfoList = containerInfoService.list(wrapper);
              if (containerInfoList.size() == 0) {
                     return new Result(false, null, info.getAiUnitId() + ":请确认服务正在运行");
              }
              for (ContainerInfo cInfo : containerInfoList) {
                     DockerContainsUtils.pauseContainer(dockerClient, cInfo.getContainerId());
                     ContainerInfo containerInfo = new ContainerInfo();
                     containerInfo.setContainerId(cInfo.getContainerId());
                     containerInfo.setContainerStatus("paused");
                     containerInfoService.saveOrUpdate(containerInfo);
              }
              return new Result(true, null, info.getAiUnitId() + "全部容器已暂停");
       }

       @PostMapping("/unpause")
       public Result unpauseContainer(@RequestBody RemoteInfo info) throws IOException {
              // todo unpause
              DockerClient dockerClient = DockerClientConnect.connectDocker(info.getDockerIp());

              String aiUnitId = info.getAiUnitId();
              QueryWrapper<ContainerInfo> wrapper = new QueryWrapper<>();
              wrapper.eq("ai_unit_id", aiUnitId);
              wrapper.eq("container_status", "paused");
              List<ContainerInfo> containerInfoList = containerInfoService.list(wrapper);
              if (containerInfoList.size() == 0) {
                     return new Result(false, null, info.getAiUnitId() + ":请确认服务处于暂停");
              }
              for (ContainerInfo cInfo : containerInfoList) {
                     DockerContainsUtils.unpauseContainer(dockerClient, cInfo.getContainerId());
                     ContainerInfo containerInfo = new ContainerInfo();
                     containerInfo.setContainerId(cInfo.getContainerId());
                     containerInfo.setContainerStatus("running");
                     containerInfoService.saveOrUpdate(containerInfo);
              }
              return new Result(true, null, info.getAiUnitId() + "全部容器已从暂停中恢复");
       }

       @PostMapping("/stop")
       public Result stopContainer(@RequestBody RemoteInfo info) throws IOException {
              DockerClient dockerClient = DockerClientConnect.connectDocker(info.getDockerIp());

              String aiUnitId = info.getAiUnitId();
              QueryWrapper<ContainerInfo> wrapper = new QueryWrapper<>();
              wrapper.eq("ai_unit_id", aiUnitId);
              wrapper.eq("container_status", "running");
              for (ContainerInfo cInfo : containerInfoService.list(wrapper)) {
                     DockerContainsUtils.stopContainer(dockerClient, cInfo.getContainerId());
                     ContainerInfo containerInfo = new ContainerInfo();
                     containerInfo.setContainerId(cInfo.getContainerId());
                     containerInfo.setContainerStatus("stopped");
                     containerInfoService.saveOrUpdate(containerInfo);
              }
              return new Result(true, null, info.getAiUnitId() + "全部容器已停止");
       }

       @PostMapping("/remove")
       public Result deleteContainer(@RequestBody RemoteInfo info) throws IOException {
              DockerClient dockerClient = DockerClientConnect.connectDocker(info.getDockerIp());
              List<ContainerInfo> containerInfoList = containerInfoService.getContainerInfo();
              for (ContainerInfo cInfo : containerInfoList) {
                     DockerContainsUtils.removeContainer(dockerClient, cInfo.getContainerId());
                     ContainerInfo containerInfo = new ContainerInfo();
                     containerInfo.setContainerId(cInfo.getContainerId());
                     containerInfo.setContainerStatus("deleted");
                     containerInfoService.saveOrUpdate(containerInfo);
              }
              return new Result(true, null, info.getAiUnitId() + "全部容器已删除，服务删除");
       }
}
