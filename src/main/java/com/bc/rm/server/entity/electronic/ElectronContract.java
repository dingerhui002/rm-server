package com.bc.rm.server.entity.electronic;

import com.bc.base.BaseEntity;
import lombok.Getter;
import lombok.Setter;

/**
 * 电子合同类
 *
 * @author dd
 */
@Getter
@Setter
public class ElectronContract extends BaseEntity {
    //合同表合同id
    private String contractId;
    //人员在第三方平台的注册id
    private String accountId;
    //第三方平台合同签署流程的id
    private String flowId;

}
