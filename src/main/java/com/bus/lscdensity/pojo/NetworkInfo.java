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
public class NetworkInfo implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 网络ID
     */
    @TableId(value = "netword_id", type = IdType.AUTO)
    private Integer networdId;

    /**
     * 网络带宽
     */
    private Double networdBandwidthbandwidth;



    /**
     * 备注
     */
    private String remark;


}
