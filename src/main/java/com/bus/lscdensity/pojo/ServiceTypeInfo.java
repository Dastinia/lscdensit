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
public class ServiceTypeInfo implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 服务类别id
     */
    @TableId(value = "service_type_id", type = IdType.AUTO)
    private Integer serverTypeId;

    /**
     * 服务类别名称
     */
    private String serverTypeName;


}
