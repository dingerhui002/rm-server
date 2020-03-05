package com.bc.rm.server.entity.base;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;

/**
 * 基础javaBean相关的基类
 *
 * @author tangyifei
 * @version 1.0.0
 * @since JDK1.8
 */
@Getter
@Setter
@ToString
public class BaseEntity implements Serializable {

    private static final long serialVersionUID = -972961224026560169L;

    /**
     * 主键
     */
    private String id;

    /**
     * 企业主键
     */
    private String enterpriseId;

    /**
     * 创建人主键
     */
    private String creatorId;

    /**
     * 修改人主键
     */
    private String modifierId;

    /**
     * 创建时间
     */
    private String createTime;

    /**
     * 修改时间
     */
    private String modifyTime;

    /**
     * 审核状态 0初始状态  1审核中  2成功  3失败
     */
    private Integer auditStatus;

    /**
     * 审核时间
     */
    private String auditTime;

    /**
     * 审核人
     */
    private String auditor;

}
