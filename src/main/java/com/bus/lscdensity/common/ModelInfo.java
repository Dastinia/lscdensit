package com.bus.lscdensity.common;

import lombok.Data;

/**
 * @author Dastinia
 * @version 1.0
 * @date 2022/12/9
 */
@Data
public class ModelInfo {
    private String redisIp;
    private Integer redisDataflowPort;

    public ModelInfo(String dataRedisServiceIp, Integer dataRedisPort) {
        redisIp = dataRedisServiceIp;
        redisDataflowPort = dataRedisPort;
    }
}
