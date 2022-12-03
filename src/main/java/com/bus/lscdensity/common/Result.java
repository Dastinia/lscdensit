package com.bus.lscdensity.common;

import lombok.Data;

@Data
public class Result {
    private boolean success;
    private String errMsg;
    private Object obj;

    public  Result(boolean flag,Object obj, String errMsg){
        this.errMsg=errMsg;
        this.success=flag;
        this.obj = obj;
    }
}
