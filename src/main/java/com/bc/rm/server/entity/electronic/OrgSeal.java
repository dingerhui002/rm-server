package com.bc.rm.server.entity.electronic;

import lombok.Data;

import java.io.Serializable;

/** 机构电子印章
 * @author d
 */
@Data
public class OrgSeal implements Serializable {
    /**
     * 主键ID
     */
    private String id;

    /**
     * 创建时间
     */
    private String createTime;

    /**
     * 平台用户id
     */
    private String orgId;

    /**
     * 状态 0：正常  1：删除 默认 0
     */
    private String status;

    /**
     * 默认状态   0：默认   1：不默认
     */
    private String defaultFlag;

    /**
     * 平台印章id
     */
    private String sealId;

    /**
     * 印章别名
     */
    private String alias;

    /**
     * 印章颜色
     */
    private String color;

    /**
     * 印章高度
     */
    private Integer height;

    /**
     * 印章宽度
     */
    private Integer width;

    /**
     * 印章横向文
     */
    private String htext;

    /**
     * 印章下弦文
     */
    private String qtext;

    /**
     * 印章类型
     */
    private String type;

    /**
     * 印章中心图案类型
     */
    private String central;

    /**
     * 图片地址
     */
    private String url;

    /**
     * 印章模板类型
     */
    private String templateType;
}
