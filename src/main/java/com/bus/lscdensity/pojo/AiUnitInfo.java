package com.bus.lscdensity.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import java.util.Date;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
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
@ApiModel(value="AiUnitInfo对象", description="")
public class AiUnitInfo implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "AI复合功能单元id")
    @TableId(value = "ai_unit_id", type = IdType.ID_WORKER)
    private String aiUnitId;

    @ApiModelProperty(value = "车辆输入单元列表id")
    private String vehicleInputId;

    @ApiModelProperty(value = "抓取服务器id")
    @TableField("grab_server_id")
    private String grabServerId;

    @ApiModelProperty(value = "抓取节点ip")
    @TableField("grab_ip")
    private String grabIp;

    @ApiModelProperty(value = "抓取端口")
    private Integer grabPort;

    @ApiModelProperty(value = "AI服务器id")
    private String aiServerId;

    @ApiModelProperty(value = "AI服务器ip")
    private String aiServerIp;

    @ApiModelProperty(value = "Redis节点ip")
    private String redisIp;

    @ApiModelProperty(value = "Redis节点数据流端口")
    @TableField("redis_dataflow_port")
    private Integer redisDataFlowPort;

    @ApiModelProperty(value = "Redis节点控制流端口")
    private Integer redisControlPort;

    @ApiModelProperty(value = "启动时间")
    private Date startTime;

    @ApiModelProperty(value = "计划终止时间")
    private Date planStopTime;

    @ApiModelProperty(value = "累计收费")
    private Double totalCharge;
}
