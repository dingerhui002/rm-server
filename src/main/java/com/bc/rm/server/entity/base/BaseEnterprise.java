package com.bc.rm.server.entity.base;

import lombok.*;

import java.io.Serializable;

/**
 * 企业基类
 *
 * @author tangyifei
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class BaseEnterprise implements Serializable {

    private static final long serialVersionUID = 4067324961571656489L;

    /**
     * 主键
     */
    private String id;

    /**
     * 创建时间
     */
    private String createTime;

    /**
     * 修改时间
     */
    private String modifyTime;

    /**
     * 删除状态 1已删除 0未删除
     */
    private Integer deleteStatus;


}
