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
public class MemoryInfo implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 内存ID
     */
    @TableId(value = "memory_id", type = IdType.AUTO)
    private Integer memoryId;

    /**
     * 内存描述信息
     */
    private String memoryName;

    /**
     * 备注
     */
    private String remarks;


}
