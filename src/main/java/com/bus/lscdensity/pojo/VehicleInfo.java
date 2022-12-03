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
public class VehicleInfo implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 车辆或站台编号
     */
    private String vehicleOrStationId;

    /**
     * 抓取类别
     */


//    @TableField("grap_type")
    private String grapType;



    /**
     * 车辆或站台类型（车型，中途站，终点站）
     */
    private String type;

    /**
     * 摄像头个数
     */
    private Integer camerasNum;

    /**
     * 抓取周期
     */
    private Integer grabCycle;

    /**
     * 抓取类型（0：事件驱动，1：周期驱动）
     */
    private Integer grabType;

    /**
     * 车辆编号
     */
    private String buscode;

    /**
     * 线路
     */
    private String line;

    /**
     * 所属公司
     */
    private String company;

    /**
     * 所属组织
     */
    private String organization;


}
