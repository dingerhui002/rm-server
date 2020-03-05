package com.bc.rm.server.msg;

import com.bc.result.CodeMsg;

/**
 * @program: wd-saas
 * @description:
 * @author: Mr.Wang
 * @create: 2019-02-25 09:55
 **/
public class ResponseMsg extends CodeMsg {

    public static ResponseMsg MODULE_DATA_AUDIT_TASK_NO_AUDITOR = new ResponseMsg(5001001, "无审核人员,提交失败！");
    public static ResponseMsg MODULE_DATA_AUDIT_TASK_AUDITOR_ERROR = new ResponseMsg(5001002, "审核人员异常,提交失败！");
    public static ResponseMsg MODULE_DATA_AUDIT_TASK_IS_AUDIT = new ResponseMsg(5001003, "当前数据正在审核,提交失败！");

    public static ResponseMsg DECLARE_TO_PROFIT_ALREADY = new ResponseMsg(6001003, "此报关已经生成过利润了！");
    public static ResponseMsg DECLARE_TO_PROFIT_NO_COST = new ResponseMsg(6001003, "没有成本价格,生成利润失败!");
    public static ResponseMsg DECLARE_TO_PROFIT_DATA_ERROR = new ResponseMsg(6001003, "报关数据异常,生成利润失败!");

    public static ResponseMsg DELETE_ERROR_DECLARE_TO_PROFIT_ALREADY = new ResponseMsg(6001003, "此报关已经生成过利润,不能删除！");

    public ResponseMsg(int code, String msg) {
        this.msg = msg;
        this.code = code;
    }
}
