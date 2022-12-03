package com.bus.lscdensity.pojo;

import java.io.Serializable;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

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
public class HardwareAblityInfo implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 硬件单元id（型号）
     */
    @TableId(value = "hardware_unitId", type = IdType.AUTO)
    private String hardwareUnitId;

    /**
     * 类别（cpu，内存，网络，gpu）
     */
    private String hardwareType;

    /**
     * 标准能力
     */
    private String standardAblity;


}
