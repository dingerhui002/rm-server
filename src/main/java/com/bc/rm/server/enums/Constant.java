package com.bc.rm.server.enums;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

/**
 * 数据路由共用常量
 *
 * @author tangyifei
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class Constant {

    // 超级管理员标识
    public static final String USER_IS_SUPER_ADMIN = "1";

    // redis 空值
    public static final String REDIS_NULL_DATA = "redis-null-data";
    // redis 模块key
    public static final String REDIS_SAAS_MODULE = "saas-module";
    // redis 模块操作key
    public static final String REDIS_SAAS_MODULE_OPERATE = "saas-module-operate";

    public static final String ENTERPRISE = "enterpriseId";

    // 租户参数
    public static final String TENANT = "tenant";
    // zeguan
    public static final String TENANT_ZEGUAN = "zeguan";
    // maili
    public static final String TENANT_MAILI = "maili";
    // 超级管理员权限
    public static final String p_superadmin = "3";
    // 空的人员查看权限
    public static final String p_null_permission = "4";

    // 请求方式
    public static final String HTTP_GET = "GET";
    public static final String HTTP_POST = "POST";
    public static final String HTTP_PUT = "PUT";

    // 不需要tenant过滤的 控制器
    public static final String LOGIN = "login";

    // 不需要tenant过滤的 控制器
    public static final String IMAUTH = "imAuth";

    // 不需要tenant过滤的 控制器
    public static final String IM = "im";

    //不需要 tenant 过滤的控制器
    public static final String FILE = "file";

    //被授权的人的ID 参数 (一般是登录人)
    public static final String MODULERELAGRANTUSERID = "moduleRelaGrantUserId";

    public static final String TOFILTER = "toFilter";
    public static final String TOADD = "toAdd";
    public static final String TOPURCHASE = "toPurchase";
    public static final String VIEW = "View";
    public static final String EDIT = "Edit";

    /********************************************** 财务权限模块开始 *******************************************/

    public static final String FILTER = "Filter";
    public static final String ADD = "Add";
    public static final String VIEW_LIST = "ViewList";
    public static final String FINANACE_ORDER = "Order";
    public static final String FINANCE_TASK_PAY = "FinancePayTask"; // 财务待付款任务模块码
    public static final String FINANCE_TASK = "FinanceTask"; // 财务任务模块码
    public static final String FINANCE_TASK_RECEIVE = "FinanceReceiveTask"; // 财务待收款任务模块码
    public static final String FINANCE_TASK_PAY_TO_APPLY = "ToApplyPay"; // 财务任务付款申请
    //    public static final String FINANCE_TASK_PAY_VIEW = "PayView"; // 财务任务中付款任务查看操作码
//    public static final String FINANCE_TASK_PAY_TO_FILTER = "PayToFilter"; // 财务任务中付款列表的筛选操作码
//    public static final String FINANCE_TASK_PAY_TO_ADD = "PayToAdd"; // 财务任务付款列表上面的添加操作码
    public static final String FINANCE_TASK_PAY_RECORD_TO_ADD = "ToAddPayRecord"; // 财务任务中添加付款记录操作码
//    public static final String FINANCE_TASK_PAY_RECORD_TO_FILTER = "ToFilterPayRecord"; // 财务任务中筛选付款记录操作码
//    public static final String FINANCE_TASK_PAY_RECORD_SAVE = "SavePayRecord"; // 财务任务中保存付款记录操作码

    //    public static final String FINANCE_TASK_RECEIVE_VIEW = "ReceiveView"; // 财务任务中收款任务查看操作码
//    public static final String FINANCE_TASK_RECEIVE_TO_FILTER = "ReceiveToFilter"; // 财务任务中收款列表的筛选操作码
    public static final String FINANCE_TASK_RECEIVE_TO_ADD = "ToAddReceiveRecord"; // 财务任务中收款列表上面的添加操作码

    public static final String FINANCE_DEPOSIT = "Deposit"; // 财务资金模块码
    public static final String FINANCE_DEPOSIT_TO_TRANSFER_ACCOUNT = "ToTransferAccount"; // 财务资金转账操作码
    public static final String FINANCE_DEPOSIT_TO_EXPENSE_RECEIPT = "ToExpensesAndReceipt"; // 财务资金收支操作码
//    public static final String FINANCE_DEPOSIT_SAVE_DEPOSIT = "SaveDeposit"; // 保存财务资金操作码
//    public static final String FINANCE_DEPOSIT_SAVE_TRANSFER_OUT_FLOW = "SaveTransferOutFlow"; // 保存财务资金转账流水操作码
//    public static final String FINANCE_DEPOSIT_SAVE_TRANSFER_IN_FLOW = "SaveTransferInFlow"; // 保存财务资金收支流水操作码

    public static final String FINANCE_INVOICE = "FinanceInvoice"; // 财务发票模块码
    public static final String FINANCE_INVOICE_RELATE_PAYMENT = "ToRelatePayment"; // 财务发票中的关联付款操作码
    public static final String FINANCE_INVOICE_RELATE_LOGISTICS = "ToRelateLogistics"; // 财务发票中的关联物流操作码

    public static final String FINANCE_VOUCHER = "FinanceVoucher"; // 财务凭证模块码

    /********************************************** 财务权限模块结束 *******************************************/

    /**
     * 物流任务
     **/
    public static final String STOCK_APPLICATION = "stockApplication";
    //列表
    public static final String STOCK_APPLICATION_LISTPAGE = "listPage";
    //入库
    public static final String TO_STOCK_APPLICATION_ADD_IN = "addApplicationIn";
    public static final String STOCK_APPLICATION_ADD_IN = "addApplication";
    public static final String CHECK_STOCK_APPLICATION_ADD_IN = "checkApplication";
    //出库
    public static final String TO_STOCK_APPLICATION_ADD_OUT = "addApplicationOut";
    public static final String STOCK_APPLICATION_ADD_OUT = "addOutStockApplication";
    public static final String CHECK_STOCK_APPLICATION_ADD_OUT = "checkOutApplication";

    /**
     * 物流物品
     **/
    public static final String GOODS = "goods";
    //列表
    public static final String QUERY_GOODS_LIST = "queryGoodsList";
    //新增
    public static final String ADD_GOODS = "addGoods";
    //修改
    public static final String MODIFY_GOODS = "modifyGoods";
    //物品报价
    public static final String OFFER = "offer";
    public static final String ADD_OFFER = "addOffer";

    /**
     * 物流仓库
     **/
    public static final String WAREHOUSE = "wareHouse";
    //列表
    public static final String GET_WAREHOUSE_TREES_BY_COMID = "getWareHouseTreesByComId";
    //新增
    public static final String SAVE_WAREHOUSE = "saveWareHouse";
    //编辑
    public static final String MODIFY_WAREHOUSE = "modifyWareHouse";
    //转移
    public static final String TRANSFER_WAREHOUSE = "transforWarehouse";

    //列表
    public static final String GET_QUOTE_LIST = "getQuoteList";
    //新增报价
    public static final String SAVE_QUOTE = "saveQuote";
    //编辑报价
    public static final String MODIFY_QUOTE = "modifyQuote";


    //订单 //需求  //合同
    public static final String ORDER = "order";
    //订单列表
    public static final String QUERY_ORDERMAIN = "queryOrderMain";
    //新增订单
    public static final String ADD_ORDERMAIN = "addOrderMain";
    //编辑订单
    public static final String SAVE_CARGOINFO = "saveCargoInfo";
    //订单申请付款
    public static final String ORDER_APPLY_PAYMENT = "applyPament";
    //订单申请发货
    public static final String ORDER_APPLY_DELIVER_GOODS = "deliverGoods";
    //订单材料单
    public static final String MATERIAL_SHEET = "materialSheet";
    //订单采购
    public static final String PURCHASE_ORDER = "purchaseOrder";

    //需求列表
    public static final String QUERY_CALCURESULT_LIST = "queryCalcuResultList";
    public static final String TO_FILTER_REQUIREMENT = "toFilterRequirement";
    //生成订单
    public static final String TO_GENERATE_ORDER = "toGenerateOrder";
    public static final String GENERATE_ORDER = "generateOrder";

    //合同列表
    public static final String QUERY_CONTRACTS = "queryContracts";
    public static final String TO_FILTER_CONTRACTS = "toFilterContracts";
    public static final String TO_ADD_CONTRACTS = "toAddContracts";
    public static final String MODIFY_CONTRACTS = "modifyContract";
    public static final String SEND_CONTRACT = "sendContract";


    public static final String UPDATE = "update.do";
    public static final String LISTPAGE = "listPage.do";
    public static final String INSERT = "insert.do";
    public static final String PAYMENT = "payment.do";
    public static final String DELIVER = "deliver.do";
    public static final String GET = "get.do";
    public static final String LISTBY = "listBy.do";
    public static final String COPYREQUIREMENT = "copyRequirement.do";


    // **********需求模块**********
    public static final String REQUIREMENT = "requirement";
    public static final String REQUIREMENTBYSALE = "requirementBySale";
    // 需求列表
    public static final String REQUIREMENT_LIST = "listPageDetail.do";
    // 订单里的需求
    public static final String GETBYORDERMAINID = "getByOrderMainId.do";
    // 报价生成需求
    public static final String GENERATEBYQUOTE = "generateByQuote.do";

    // **********合同模块**********
    public static final String CONTRACT = "contract";

    // **********订单模块**********
    public static final String ORDERMAIN = "orderMain";

    // **********报价模块**********
    public static final String QUOTE = "quote";
    // 大报价复制
    public static final String COPYQUOTE = "copyQuote.do";
    // 小报价复制
    public static final String COPYQUOTEDETAIL = "copyQuoteDetail.do";
    // 小报价删除
    public static final String DELETEQUOTEDETAIL = "deleteQuoteDetail.do";

    /**
     * 拦截器将UserId放入request中的键
     */
    public static final String MODULE_CODE = "module_code";


}
