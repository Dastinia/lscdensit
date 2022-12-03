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
public class ServiceStatus implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 服务id
     */
    @TableId(value = "service_id", type = IdType.AUTO)
    private String serviceId;

    /**
     * 服务类型（1.抓取，2.车辆拥挤度分析，3.站台拥挤度分析）
     */
    private Integer serviceTypeId;

    /**
     * 状态信息（1：工作状态2：失效状态）
     */
    private String status;

    /**
     * 服务的饱和度（百分比）
     */
    private Double servicesaturation;


}
