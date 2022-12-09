package com.bus.lscdensity.common;

import lombok.Data;

/**
 * @author Dastinia
 * @version 1.0
 * @date 2022/12/9
 */
@Data
public class MonitorInfo {
    private String kafkaServiceIp;
    private String kafkaServiceUser;
    private String kafkaServicePasswd;
    private String kafkaServerType;

    private String modelServiceIp;
    private String modelServiceUser;
    private String modelServicePasswd;
    private String modelServerType;

    public MonitorInfo(String kafkaServiceIp, String kafkaServiceUser, String kafkaServicePasswd, String kafkaServerType, String modelServiceIp, String modelServiceUser, String modelServicePasswd, String modelServerType) {
        this.kafkaServiceIp = kafkaServiceIp;
        this.kafkaServiceUser = kafkaServiceUser;
        this.kafkaServicePasswd = kafkaServicePasswd;
        this.kafkaServerType = kafkaServerType;
        this.modelServiceIp = modelServiceIp;
        this.modelServiceUser = modelServiceUser;
        this.modelServicePasswd = modelServicePasswd;
        this.modelServerType = modelServerType;
    }
}
