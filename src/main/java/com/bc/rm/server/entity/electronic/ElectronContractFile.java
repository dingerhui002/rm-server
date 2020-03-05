package com.bc.rm.server.entity.electronic;

import com.bc.rm.server.entity.base.BaseEntity;
import lombok.Getter;
import lombok.Setter;

/**
 * 电子合同上传的文件类
 *
 * @author dd
 */
@Getter
@Setter
public class ElectronContractFile extends BaseEntity {
    //合同表合同id
    private String electronicId;
    //人员在第三方平台的注册id
    private String fileId;
    //通过申请上传文件获取到的直传地址（文件上传传到这个地址）
    private String uploadUrl;

}
