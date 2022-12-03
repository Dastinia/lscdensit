package com.bus.lscdensity.pojo;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;


/**
 * @author Dastinia
 * @version 1.0
 * @date 2022/11/17
 */
@Data
@ApiModel(value="容器对象", description="")
public class ContainerInfo {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "关联的AI单元id")
    @TableField(value = "ai_unit_id")
    private String aiUnitId;

    @ApiModelProperty(value = "容器id")
    @TableId
    private String containerId;

    @ApiModelProperty(value = "容器名称")
    @TableField(value = "container_name")
    private String containerName;

    @ApiModelProperty(value = "容器状态")
    @TableField(value = "container_status")
    private String containerStatus;

    @ApiModelProperty(value = "创建时间")
    @TableField(value = "create_time")
    private Date createTime;

    @ApiModelProperty(value = "容器镜像信息")
    @TableField(value = "image_information")
    private String imageInformation;

    // 暂未使用
    @ApiModelProperty(value = "数据卷位置")
    private String dataVolumesPos;

    @ApiModelProperty(value = "容器服务端口")
    private Integer containerPort;

    @ApiModelProperty(value = "容器功能")
    private String containerFunction;

    @ApiModelProperty(value = "docker服务器ip")
    private String serverIp;

    @ApiModelProperty(value = "docker服务端口")
    private int serverPort;
}
