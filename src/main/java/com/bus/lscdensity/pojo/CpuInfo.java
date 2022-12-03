package com.bus.lscdensity.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * <p>
 *
 * </p>
 *
 * @author ${author}
 * @since 2022-03-14
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class CpuInfo implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * cpu的id
     */
    @TableId(value = "cpu_id", type = IdType.AUTO)
    private String cpuId;

    private Integer cpuNum;

    /**
     * 备注
     */
    private Integer perCpuCore ;
    private  Integer logicCpu;
    private  String cpuType;
    private  String cpuAbility;

}
