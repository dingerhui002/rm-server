package com.bc.rm.server.entity;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.List;

/**
 * 合同实体
 *
 * @author leo
 */
@Getter
@Setter
public class Contract implements Serializable {
    private static final long serialVersionUID = 7267410763765381108L;

    /**
     * 主记录
     */
    public static final String IS_MAIN = "1";

    /**
     * 来源：需求
     */
    public static final String SOURCE_REQUIREMENT = "0";

    /**
     * 来源：订单
     */
    public static final String SOURCE_ORDER = "1";

    /**
     * 删除状态：未删除
     */
    public static final String DELETE_STATUS_NO = "0";

    /**
     * 删除状态：已删除
     */
    public static final String DELETE_STATUS_YES = "1";

    /**
     * 需求方向：发送方
     */
    public static final String DIRECTION_SENDER = "00";

    /**
     * 需求方向：接收方
     */
    public static final String DIRECTION_RECEIVER = "01";

    /**
     * 需求方向：接收方已入库
     */
    public static final String DIRECTION_RECEIVER_WAREHOUSING = "02";

    /**
     * 接收提醒：不提醒
     */
    public static final String RECEIVE_REMINDING_NO = "0";

    /**
     * 接收提醒：提醒
     */
    public static final String RECEIVE_REMINDING_YES = "1";

    /**
     * 发送状态：未发送
     */
    public static final String SEND_STATUS_NO = "0";

    /**
     * 发送状态：已发送
     */
    public static final String SEND_STATUS_YES = "1";

    /**
     * 发送状态：已确认
     */
    public static final String SEND_STATUS_CONFIRM = "2";

    /**
     * 合同类型：采购
     */
    public static final String TYPE_PURCHASE = "P";

    /**
     * 合同类型：销售
     */
    public static final String TYPE_SALE = "S";

    /**
     * 合同状态：未提交
     */
    public static final String EXAMINE_STATUS_NO_SUBMIT = "0";

    /**
     * 合同状态：未审核
     */
    public static final String EXAMINE_STATUS_NO = "1";

    /**
     * 合同状态：审核失败
     */
    public static final String EXAMINE_STATUS_FAIL = "2";

    /**
     * 合同状态：已审核
     */
    public static final String EXAMINE_STATUS_SUCCESS = "3";

    /**
     * 合同状态：已发送
     */
    public static final String EXAMINE_STATUS_SEND = "4";

    /**
     * 合同状态：已取消
     */
    public static final String EXAMINE_STATUS_CANCEL = "5";

    /**
     * 合同状态：已确认
     */
    public static final String EXAMINE_STATUS_CONFIRM = "6";

    /**
     * 发票类型：不开发票
     */
    public static final String INVOICE_TYPE_NO = "00";

    /**
     * 发票类型：普通发票
     */
    public static final String INVOICE_TYPE_COMMON = "01";

    /**
     * 发票类型：增值发票
     */
    public static final String INVOICE_TYPE_INCREMENT = "02";

    public static final String SHOW_SPEC = "1";
    public static final String No_SHOW_SPEC = "0";

    /**
     * 主键ID
     */
    private String id;

    /**
     * 合同类型
     * P：采购
     * S：销售
     */
    private String contractType;

    /**
     * 合同号
     */
    private String contractNo;

    /**
     * 发起方用户ID
     */
    private String fromUserId;

    /**
     * 发起方用户姓名
     */
    private String fromUserName;

    /**
     * 发起方企业ID
     */
    private String fromEnterpriseId;

    /**
     * 发起方企业名称
     */
    private String fromEnterpriseName;

    /**
     * 发起方企业地址
     */
    private String fromEnterpriseAddress;

    /**
     * 接收方用户ID
     */
    private String toUserId;

    /**
     * 接收方用户姓名
     */
    private String toUserName;

    /**
     * 接收方企业ID
     */
    private String toEnterpriseId;

    /**
     * 接收方企业名称
     */
    private String toEnterpriseName;

    /**
     * 接收方企业地址
     */
    private String toEnterpriseAddress;

    /**
     * 合同需求下的主题的组合
     */
    private String themeTitleStr;

    /**
     * 合同需求下的品名的的组合
     */
    private String goodsNamesStr;

    /**
     * 合同需求下的往来物料号的组合
     */
    private String materialNumberStr;

    /**
     * 合同金额
     */
    private String contractAmount;

    /**
     * 备注
     */
    private String memo;

    /**
     * 合同状态
     */
    private String status;

    /**
     * 发送状态
     * 0：未发送
     * 1：已发送
     * 2：已确认
     */
    private String sendStatus;

    /**
     * 合同备注，在附加条款里面，是json类型，有多个备注组合
     */
    private String contractNote;

    /**
     * 合同的相关照片，在附加条款里面
     */
    private String photos;

    /**
     * 合同九宫格照片
     */
    private String goodsPhotos;

    /**
     * 开票状态
     * 00：不开发票
     * 01：普通发票
     * 02：增值发票
     */
    private String invoiceType;

    /**
     * 币种，在附加条款里
     * 1：美元汇率
     * 2：欧元汇率
     * 3：英镑
     * 4：港币
     * 5：加拿大元
     * 6：日元
     * 7：新台币
     * 8：越南币
     * 9：人民币
     */
    private String exchangeRateType;

    /**
     * 接收提醒
     */
    private String receiveReminding;

    /**
     * 删除状态
     */
    private String deleteStatus;

    /**
     * 来源
     * 0：需求
     * 1：订单
     */
    private String source;

    /**
     * 确认的子记录的主键ID
     */
    private String confirmId;

    /**
     * 确认日期
     */
    private String confirmDate;

    /**
     * 创建时间
     */
    private String createTime;

    /**
     * 修改时间
     */
    private String modifyTime;

    /**
     * 标题
     */
    private String title;

    /**
     * 合同预览 是否显示规格 0 不显示 1 显示
     */
    private String isShowSpec;

    /**
     * 标签
     */
    private String tags;

    /**
     * 签署流程创建
     */
    private String flowId;

    /**
     * 签订时间
     */
    private String signDate;
}
