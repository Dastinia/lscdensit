package com.bus.lscdensity.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;

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
public class PicturePathInfo implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 图片路径
     */
    private String picturePath;

    /**
     * 图片存储放入ip
     */
    private String storeIp;

    /**
     * 服务id
     */
    private String serviceId;

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
     * 时间
     */
    private LocalDateTime time;


}
