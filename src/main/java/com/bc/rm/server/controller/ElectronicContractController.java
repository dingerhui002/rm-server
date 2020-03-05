package com.bc.rm.server.controller;

import com.bc.rm.server.config.ElectronicHttpHelper;
import com.bc.rm.server.entity.electronic.*;
import com.bc.rm.server.enums.Constant;
import com.bc.rm.server.result.Result;
import com.bc.rm.server.service.impl.ElectronicContractServiceImpl;
import io.swagger.annotations.ApiOperation;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;


/**
 * 电子合同实现类
 *
 * @author leo
 */
@RequestMapping("electronicContract")
@RestController
public class ElectronicContractController {

    @Resource
    private ElectronicContractServiceImpl electronicContractService;

    @Resource
    private ElectronicHttpHelper electronicHttpHelper;

    /**
     * 电子合同，获取token
     *
     * @Pamae
     */
    @GetMapping("/getToken")
    public String getToken() throws Exception {
        return electronicHttpHelper.getToken();
    }


    /**
     * 通过用户id判断是否有E签宝的用户，没有就创建一个用户
     *
     * @Pamae userId 用户id
     */
    @ApiOperation(value = "判断是否有E签宝的用户,没有就创建一个用户", httpMethod = "POST", notes = "userId 用户id")
    @PostMapping("/findAccount")
    public Result findAccount(@RequestParam("userId") String userId) throws Exception {
        return electronicContractService.findAccount(userId);
    }

    @ApiOperation(value = "判断是否有E签宝的机构,没有就注册", httpMethod = "POST", notes = "userId 用户id")
    @PostMapping("/findOrg")
    public Result findOrg(HttpServletRequest request, @RequestParam("userId") String userId) throws Exception {
        String enterpriseId = request.getHeader(Constant.ENTERPRISE);
        if (StringUtils.isEmpty(enterpriseId)) {
            enterpriseId = "5bd65ecda4174ad3a85e4985d838eba4";
        }
        return electronicContractService.findOrg(enterpriseId, userId);
    }

    @ApiOperation(value = "获取个人印章列表", httpMethod = "GET", notes = "获取个人印章列表")
    @GetMapping("/getUserSeal")
    public Result getUserSeal(@RequestParam String accountId, @RequestParam Integer pageNum, @RequestParam Integer numPerPage) throws Exception {
        return electronicContractService.getUserSeal(accountId, pageNum, numPerPage);
    }

    @ApiOperation(value = "获取机构印章列表", httpMethod = "POST", notes = "获取机构印章列表")
    @PostMapping("/getOrgSeal")
    public Result getOrgSeal(@RequestParam String orgId, @RequestParam Integer pageNum, @RequestParam Integer numPerPage) throws Exception {
        return electronicContractService.getOrgSeal(orgId,pageNum,numPerPage);
    }

    @ApiOperation(value = "创建个人印章", httpMethod = "POST", notes = "创建个人印章")
    @PostMapping("/addAccountSeal")
    public Result addAccountSeal(@RequestBody AccountSeal accountSeal) throws Exception {
        return electronicContractService.addAccountSeal(accountSeal);
    }

    @ApiOperation(value = "创建机构印章", httpMethod = "POST", notes = "创建机构印章")
    @PostMapping("/addOrgSeal")
    public Result addOrgSeal(@RequestBody OrgSeal orgSeal) throws Exception {
        return electronicContractService.addOrgSeal(orgSeal);
    }

    @ApiOperation(value = "修改个人默认印章", httpMethod = "PUT", notes = "修改个人默认印章")
    @PutMapping("/updateAccountSealDefault")
    public ElectronContractApi updateAccountSealDefault(@RequestParam String accountSealId) throws Exception {
        return electronicContractService.updateAccountSealDefault(accountSealId);
    }

    @ApiOperation(value = "修改机构默认印章", httpMethod = "PUT", notes = "修改机构默认印章")
    @PutMapping("/updateOrgSealDefault")
    public ElectronContractApi updateOrgSealDefault(@RequestParam String orgSealId) throws Exception {
        return electronicContractService.updateOrgSealDefault(orgSealId);
    }


    /**
     * 创建签署流程
     *
     * @Pamae contractId 合同id
     * @Pamae userId 发起方的id
     */
    @ApiOperation(value = "创建签署流程", httpMethod = "POST", notes = "contractId 合同id")
    @PostMapping("/createSigningProcess")
    public Result createSigningProcess(@RequestParam("contractId") String contractId, @RequestParam("userId") String userId) throws Exception {
        return electronicContractService.createSigningProcess(contractId, userId);
    }

    /**
     * 查询签署流程的详细信息，包括流程配置、签署状态等。
     *
     * @Pamae
     */
    @GetMapping("/getSignflows")
    @ApiOperation(value = "查询签署流程的详细信息，包括流程配置、签署状态等。", httpMethod = "GET", notes = "flowId 流程id")
    public ElectronContractApi getSignflows(@RequestParam String flowId) throws Exception {
        return electronicContractService.getSignflows(flowId);
    }

    /**
     * 通过上传方式创建文件
     * 同来获取上传的直传地址，并且上传文件
     *
     * @Pamae contractId 合同id
     * @Pamae userId 发起方的id
     */
    @ApiOperation(value = "通过上传方式创建文件", httpMethod = "POST", notes = "通过上传方式创建文件")
    @PostMapping("/getDirectAddress")
    public Result getDirectAddress(HttpServletRequest request, @RequestParam("contractId") String contractId, @RequestParam("userId") String userId
            , @RequestParam("filePath") String filePath) throws Exception {
        //获取上传的文件数组
        MultipartFile file = ((MultipartHttpServletRequest) request).getFile("file");
        return electronicContractService.getDirectAddress(contractId, userId, file, filePath);
    }

    /**
     * 流程文档添加
     *
     * @Pamae flowId 流程ID
     * @Pamae files fileId数组
     */
    @ApiOperation(value = "流程文档添加", httpMethod = "POST", notes = "流程文档添加")
    @PostMapping("/signflows/documents")
    public Result folwAddDocument(@RequestParam("flowId") String flowId, @RequestParam("files") String[] files) throws Exception {
        return electronicContractService.folwAddDocument(flowId, files);
    }

    /**
     * 流程附件添加
     *
     * @Pamae flowId 流程ID
     * @Pamae attachmentsList fileId数组
     */
    @ApiOperation(value = "流程附件添加", httpMethod = "POST", notes = "流程附件添加")
    @PostMapping("/signflows/attachments")
    public Result folwAddAttachments(@RequestParam("flowId") String flowId, @RequestBody List<Attachment> attachmentsList) throws Exception {
        return electronicContractService.folwAddAttachments(flowId, attachmentsList);
    }

    /**
     * 添加手动盖章签署区
     *
     * @Pamae flowId 流程ID
     * @Pamae signfields fileId数组
     */
    @ApiOperation(value = "添加手动盖章签署区", httpMethod = "POST", notes = "添加手动盖章签署区")
    @PostMapping("/signflows/signfields/handSign")
    public ElectronContractApi folwFieldsHandSign(@RequestParam("flowId") String flowId, @RequestParam("keyword") String keyword, @RequestBody List<Signfield> signfields) throws Exception {
        return electronicContractService.folwFieldsHandSign(flowId, keyword, signfields);
    }

    /**
     * 获取签署地址
     *
     * @Pamae flowId 流程ID
     */
    @ApiOperation(value = "获取签署地址", httpMethod = "GET", notes = "获取签署地址")
    @GetMapping("/signflows/executeUrl")
    public ElectronContractApi getExecuteUrl(@RequestParam("flowId") String flowId, @RequestParam("accountId") String accountId) throws Exception {
        return electronicContractService.getExecuteUrl(flowId, accountId);
    }

    /**
     * 签署流程开启
     *
     * @Pamae flowId 流程ID
     */
    @ApiOperation(value = "签署流程开启", httpMethod = "PUT", notes = "签署流程开启")
    @PutMapping("/signflows/start")
    public ElectronContractApi folwStart(@RequestParam("flowId") String flowId) throws Exception {
        return electronicContractService.start(flowId);
    }

    /**
     * 签署流程归档
     *
     * @Pamae flowId 流程ID
     */
    @ApiOperation(value = "签署流程归档", httpMethod = "PUT", notes = "签署流程归档")
    @PutMapping("/signflows/archive")
    public ElectronContractApi folwArchive(@RequestParam("flowId") String flowId) throws Exception {
        return electronicContractService.archive(flowId);
    }

    /**
     * 流程文档下载
     *
     * @Pamae flowId 流程ID
     */
    @ApiOperation(value = "流程文档下载", httpMethod = "GET", notes = "流程文档下载")
    @GetMapping("/signflows/down")
    public Result down(@RequestParam("flowId") String flowId) throws Exception {
        return electronicContractService.down(flowId);
    }

}

