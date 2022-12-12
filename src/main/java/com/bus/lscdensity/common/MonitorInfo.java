package com.bus.lscdensity.common;

import lombok.Data;

/**
 * @author Dastinia
 * @version 1.0
 * @date 2022/12/9
 */
@Data
public class MonitorInfo {
    private String aiUnitId;

    private String kafkaServiceIp;
    private String kafkaServiceUser;
    private String kafkaServicePasswd;
    private String kafkaServerType;
    private String grabServerId;

    private String modelServiceIp;
    private String modelServiceUser;
    private String modelServicePasswd;
    private String modelServerType;
    private String aiServerId;

    public MonitorInfo(String aiUnitId, String kafkaServiceIp, String kafkaServiceUser, String kafkaServicePasswd, String kafkaServerType, String grabServerId, String modelServiceIp, String modelServiceUser, String modelServicePasswd, String modelServerType, String aiServerId) {
        this.aiUnitId = aiUnitId;
        this.kafkaServiceIp = kafkaServiceIp;
        this.kafkaServiceUser = kafkaServiceUser;
        this.kafkaServicePasswd = kafkaServicePasswd;
        this.kafkaServerType = kafkaServerType;
        this.grabServerId = grabServerId;
        this.modelServiceIp = modelServiceIp;
        this.modelServiceUser = modelServiceUser;
        this.modelServicePasswd = modelServicePasswd;
        this.modelServerType = modelServerType;
        this.aiServerId = aiServerId;
    }
}
