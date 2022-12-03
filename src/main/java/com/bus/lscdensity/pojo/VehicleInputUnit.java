package com.bus.lscdensity.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
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
public class VehicleInputUnit implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "vehicle_input_id", type = IdType.AUTO)
    private Integer vehicleInputId;

    /**
     * 单元名
     */
    private String unitName;

    /**
     * 单元topic
     */
    private String unitTopic;

    /**
     * 单元包含的车辆数目
     */
    private Integer unitNum;

    /**
     * 抓取服务器id
     */
    private String serverId;

    /**
     * kafka服务端的IP
     */
    private String kafkaIp;

    /**
     * kafka服务端的端口
     */
    private Integer kafkaPort;

    private String numList;

    @TableField("grasp_period")
    private Integer graspPeriod;


}
