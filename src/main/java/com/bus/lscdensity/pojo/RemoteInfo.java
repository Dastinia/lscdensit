package com.bus.lscdensity.pojo;

import lombok.Data;

@Data
public class RemoteInfo {
    // todo 提醒祥哥发送ai_unit_id字段给我
    private String aiUnitId;
    private  String dockerIp;
    private  String serverIp;
}
