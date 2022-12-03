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
public class GpuInfo implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * gpu id
     */
    @TableId(value = "gpu_id", type = IdType.AUTO)
    private Integer gpuId;

    /**
     * gpu名称
     */
    private String gpuName;

    /**
     * 备注
     */
    private String remarks;


}
