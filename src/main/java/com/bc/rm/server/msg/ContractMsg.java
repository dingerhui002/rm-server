package com.bc.rm.server.msg;

import com.bc.rm.server.result.CodeMsg;

/**
 * @program: wd-saas
 * @description:
 * @author: dd
 * @create: 2019-11-19
 **/
public class ContractMsg extends CodeMsg {

    //用户
    public static ContractMsg NO_USER_NAME = new ContractMsg(500001, "当前用户没有设置姓名！");
    public static ContractMsg NO_USER_TYPE = new ContractMsg(500002, "当前用户没有设置证件类型！");
    public static ContractMsg NO_USER_NUMBER = new ContractMsg(500003, "当前用户没有设置证件号！");
    public static ContractMsg SIGNING_PROCESS_NO_USER = new ContractMsg(5000103, "该发起人没有在第三方创建用户！");

    //签署流程
    public static ContractMsg SIGNING_PROCESS_HAVE = new ContractMsg(5000101, "该合同已经创建签署流程！");
    public static ContractMsg SIGNING_PROCESS_TITLE = new ContractMsg(5000102, "该合同没有设置标题！");

    //上传文件
    public static ContractMsg FILE_EORROR = new ContractMsg(5001001, "文件上传失败，请重新尝试！");
    public static ContractMsg FILE_API_EORROR = new ContractMsg(5001001, "文件第三方平台的api报错，请联系管理员！");

    public ContractMsg(int code, String msg) {
        this.msg = msg;
        this.code = code;
    }
}
