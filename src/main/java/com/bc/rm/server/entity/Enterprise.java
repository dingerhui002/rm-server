package com.bc.rm.server.entity;

import com.bc.rm.server.entity.base.BaseEnterprise;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * 企业信息
 *
 * @author tangyifei
 */
@Getter
@Setter
@ToString(callSuper = true)
public class Enterprise extends BaseEnterprise {

    /**
     * 企业创建人主键
     */
    private String creatorId;

    /**
     * 企业相关照片
     */
    private String photos;

    /**
     * 企业名称
     */
    private String name;

    /**
     * 企业简称
     */
    private String shortName;

    /**
     * 产品
     */
    private String product;

    /**
     * 企业电话
     */
    private String telephone;

    /**
     * 企业邮箱
     */
    private String email;

    /**
     * 认证资质
     */
    private String certification;

    /**
     * 企业所属的国家
     */
    private String country;

    /**
     * 企业所属的省份
     */
    private String province;

    /**
     * 企业所属的城市
     */
    private String city;

    /**
     * 企业所属的区县
     */
    private String zone;

    /**
     * 企业详细地址
     */
    private String address;

    /**
     * 公司地理位置相关的经度
     */
    private String lng;

    /**
     * 公司地理位置相关的纬度
     */
    private String lat;

    /**
     * 是否置顶(0不置顶  1置顶)
     */
    private Integer isTop;

    /**
     * 分片数据库名称
     */
    private String schemaName;

    /**
     * 机构id
     */
    private String orgId;

}
