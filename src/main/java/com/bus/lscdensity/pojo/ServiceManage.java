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
public class ServiceManage implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 服务id,服务id定义方法= AI复合功能处理单元id+服务类别
     */
    @TableId(value = "service_id", type = IdType.AUTO)
    private String serverId;

    /**
     * 服务类别
     */
    private Integer serverTypeId;

    /**
     * 服务隶属公司id
     */
    private String company;


}
