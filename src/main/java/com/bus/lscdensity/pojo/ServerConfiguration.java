package com.bus.lscdensity.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.context.annotation.Primary;

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
public class ServerConfiguration implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 服务器id
     */
    @TableId(value = "server_id", type = IdType.AUTO)
    private Integer serverId;

    /**
     * 服务器类别（抓取节点或AI计算节点）
     */
    @TableField("server_type")
    private String serverType;

    @TableField("server_ip1")
    private String serverIp1;

    private String serverIp2;

    private String serverIp3;

    private String serverIp4;

    /**
     * 服务配置详细id，在服务配置详细ip表
     */

    private String serverDetailId;

    @TableField("server_user")
    private String serverUser;

    @TableField("server_passwd")
    private String serverPasswd;
}
