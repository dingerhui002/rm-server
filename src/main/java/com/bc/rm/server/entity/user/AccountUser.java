package com.bc.rm.server.entity.user;


import lombok.*;

import java.io.Serializable;


/**
 * 用户实体
 *
 * @author tangyifei
 */
@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class AccountUser implements Serializable {

    private static final long serialVersionUID = 7860006739676031050L;

    /**
     * 主键
     */
    private String id;

    /**
     * 用户关联的企业表主键
     */
    private String enterpriseId;

    /**
     * 用户关联的企业名称
     */
    private String enterpriseName;

    /**
     * 用户关联的部门主键
     */
    private String departmentId;

    /**
     * 用户关联的部门名称
     */
    private String departmentName;

    /**
     * 用户姓名
     */
    private String name;

    /**
     * 用户密码
     */
    private String password;

    /**
     * 相关的极光用户密码
     */
    private String imPassword;

    /**
     * 相关的随机密码
     */
    private String randomPassword;

    /**
     * 用户手机号
     */
    private String phone;

    /**
     * 用户邮箱
     */
    private String email;

    /**
     * 用户头像
     */
    private String avatar;

    /**
     * 用户压缩头像
     */
    private String reduceAvatar;

    /**
     * 用户账户状态 1表示激活 2表示冻结
     */
    private Integer userStatus;

    /**
     * 用户性别 0表示未知，1表示男，2表示女
     */
    private Integer gender;

    /**
     * 职位名称
     */
    private String jobName;

    /**
     * 职工作性质（1代表全职 2代表兼职 3代表实习 4代表外协 5代表离职 6代表退休 7试用期）
     */
    private Integer jobStatus;

    /**
     * 工号
     */
    private String jobNo;

    /**
     * 公司地址
     */
    private String companyAddress;

    /**
     * 居住地址
     */
    private String address;

    /**
     * 1表示第一次登陆 0不是第一次登陆
     */
    private Integer isFirstLogin;

    /**
     * 上次登陆时间
     */
    private String lastLoginTime;

    /**
     * 是否是超级管理员 0否 1是
     */
    private Integer isSuperAdmin;

    /**
     * 是否是管理员 0否 1是
     */
    private Integer isAdmin;

    /**
     * 是否是客服 0否 1是
     */
    private Integer isCustomerService;

    /**
     * 路由数据库名
     */
    private String schemaName;

    /**
     * 创建时间
     */
    private String createTime;

    /**
     * 修改时间
     */
    private String modifyTime;

    /**
     * 审核状态 0初始状态1审核中2成功3失败
     */
    private String auditStatus;

    /**
     * 审核时间
     */
    private String auditTime;

    /**
     * 证件类型
     */
    private String idType;
    /**
     * 证件号
     */
    private String idNumber;
    /**
     * 电子合同平台的用户id
     */
    private String accountId;

}
