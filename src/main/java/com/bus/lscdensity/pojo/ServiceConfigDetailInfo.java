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
public class ServiceConfigDetailInfo implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "service_detail_id", type = IdType.AUTO)
    private Integer serviceDetailId;

    /**
     * cpu等级
     */
    private String cpuLevel;

    /**
     * cpu计算能力
     */
    private String cpuComputePower;

    /**
     * gpu型号
     */
    private String gpuType;

    /**
     * gpu计算能力
     */
    private String gpuComputePower;

    /**
     * 内存大小
     */
    private Integer mermorySize;

    /**
     * 内存能力
     */
    private String mermoryPower;

    /**
     * 网卡带宽
     */
    private Double networkBandwidth;

    /**
     * 网络能力
     */
    private String networkPower;

    /**
     * 能耗1
     */
    private String energyConsumption1;

    /**
     * 能耗2
     */
    private String energyConsumption2;

    /**
     * 能耗3
     */
    private String energyConsumption3;

    /**
     * 服务节点持有性质（私有，公有）
     */
    private String serviceNodeNature;

    /**
     * 收费标准
     */
    private String chargeStandard;

    /**
     * 累计收费
     */
    private Double totalCharge;


}
