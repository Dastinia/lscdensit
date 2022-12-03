package com.bus.lscdensity.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * <p>
 *
 * </p>
 *
 * @author JJJY
 * @since 2022-03-14
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class HardwareDynamicState implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 服务器ID
     */
    @TableId(value = "server_id", type = IdType.AUTO)
    private String serverId;

    /**
     * cpu_id
     */
    private Integer cpuId;

    /**
     * cpu利用率
     */
    private Double cpuUtilization;

    /**
     * cpu状态（1：工作状态2：失效状态）
     */
    private Integer cpuStatus;

    /**
     * 内存id
     */
    private Integer memoryId;

    /**
     * 内存利用率
     */
    private Double memUtilization;

    /**
     * 内存状态（1：工作状态2：失效状态）
     */
    private Integer memStatus;

    /**
     * 网络ID
     */
    private Integer networkId;

    /**
     * 网络利用率
     */
    private Double networdUtilization;

    /**
     * 网络状态（1：工作状态2：失效状态）
     */
    private Integer networdStatus;

    /**
     * gpu ID
     */
    private Integer gpuId;

    /**
     * gpu利用率
     */
    private Double gpuUtilization;

    /**
     * gpu状态（1：工作状态2：失效状态）
     */
    private Integer gpuStatus;


}
