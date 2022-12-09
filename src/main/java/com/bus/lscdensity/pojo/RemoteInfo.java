package com.bus.lscdensity.pojo;

import lombok.Data;

@Data
public class RemoteInfo {
    // 默认数据redis与模型运行与同一物理机上。故不设置dataRedisIp
    private String aiUnitId;
    private String kafkaServiceIp;
    private String monitorServiceIp;
    private String modelServiceIp;
    private String controlRedisIp;
}
