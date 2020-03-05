package com.bc.rm.server.entity.electronic;

import lombok.Data;

/**签署区列表数据表
 * @author d
 */
@Data
public class Signfield {

    /**
     * 附件Id
     */
    private String fileId;

    /**
     * 签署操作人个人账号标识
     */
    private String signerAccountId;

    /**
     * 签署操作人个人账号标识
     */
    private String authorizedAccountId;

    private int actorIndentityType;

    /**
     * 签署区顺序，默认1,且不小于1，顺序越小越先处理
     */
    private int order;

    /**
     * 是否指定位置
     */
    private Boolean assignedPosbean;

    /**
     * 印章类型，支持多种类型时逗号分割，0-手绘印章，1-模版印章，为空不限制
     */
    private String sealType;

    /**
     * 签署类型，0-不限，1-单页签署，2-骑缝签署，默认1
     */
    private int signType;

    private PosBean posBean;

}
