package com.bc.rm.server.entity.electronic;

import lombok.Getter;
import lombok.Setter;

import java.util.Map;

/**
 * 接收电子合同第三方返回数据的api类
 *
 * @author dd
 */
@Getter
@Setter
public class ElectronContractApi {

    private String message;
    private int code;
    private Map<String, Object> data;

}
