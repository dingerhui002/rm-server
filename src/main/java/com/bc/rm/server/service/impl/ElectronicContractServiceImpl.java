package com.bc.rm.server.service.impl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.bc.rm.server.config.ElectronicHttpHelper;
import com.bc.rm.server.entity.Contract;
import com.bc.rm.server.entity.Enterprise;
import com.bc.rm.server.entity.electronic.*;
import com.bc.rm.server.entity.user.AccountUser;
import com.bc.rm.server.mapper.AccountSealMapper;
import com.bc.rm.server.mapper.ElectronicContractMapper;
import com.bc.rm.server.mapper.OrgSealMapper;
import com.bc.rm.server.msg.ContractMsg;
import com.bc.rm.server.result.CodeMsg;
import com.bc.rm.server.result.Result;
import com.bc.rm.server.util.CommonUtil;
import com.bc.rm.server.util.FileUtils;
import com.bc.rm.server.util.GetKey;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.client.methods.HttpHead;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.FileInputStream;
import java.net.URLEncoder;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @program: wd-saas
 * @description:
 * @author: Mr.dd
 * @create: 2019年11月15日16:02:24
 **/
@Service
@Slf4j
public class ElectronicContractServiceImpl {

    @Resource
    private ElectronicContractMapper electronicContractDao;

    @Resource
    private AccountSealMapper accountSealDao;

    @Resource
    private OrgSealMapper orgSealDao;

    @Resource
    private ElectronicHttpHelper electronicHttpHelper;

    /**
     * 获取e签宝平台的权限TOken，存入redis
     *
     * @Pamae userId 用户id
     */
    @Transactional(rollbackFor = Exception.class)
    public Result findAccount(String userId) throws Exception {
        AccountUser user = electronicContractDao.findByUserId(userId);
        if (StringUtils.isEmpty(user.getAccountId())) {
            String url = "/v1/accounts/createByThirdPartyUserId";
            //如果电子合同id为空，则创建电子合同的用户id，并且存入userId里面
            JSONObject jsobj = new JSONObject();
            jsobj.put("thirdPartyUserId", user.getId());
            if (!StringUtils.isEmpty(user.getName())) {
                jsobj.put("name", user.getName());
            } else {
                return Result.error(ContractMsg.NO_USER_NAME);
            }
            if (!StringUtils.isEmpty(user.getPhone())) {
                jsobj.put("mobile", user.getPhone());
            }
            if (!StringUtils.isEmpty(user.getEmail())) {
                jsobj.put("email", user.getEmail());
            }
            if (!StringUtils.isEmpty(user.getIdType())) {
                jsobj.put("idType", user.getIdType());
            } else {
                return Result.error(ContractMsg.NO_USER_TYPE);
            }
            if (!StringUtils.isEmpty(user.getIdNumber())) {
                jsobj.put("idNumber", user.getIdNumber());
            } else {
                return Result.error(ContractMsg.NO_USER_NUMBER);
            }
            //请求api accountId 第三方平台注册的id
            ElectronContractApi electronContractApi = electronicHttpHelper.httpPost(url, jsobj, null, true);
            String accountId = (String) electronContractApi.getData().get("accountId");
            user.setAccountId(accountId);
            if (null != electronContractApi.getData()) {
                electronicContractDao.updateUser(user);
            }
        }
        return Result.success();
    }

    @Transactional(rollbackFor = Exception.class)
    public Result findOrg(String enterpriseId, String userId) throws Exception {
        Enterprise enterprise = electronicContractDao.findById(enterpriseId);
//        if (StringUtils.isEmpty(enterprise.getOrgId())) {
//            String url = "/v1/accounts/createByThirdPartyUserId";
//            //如果电子合同id为空，则创建电子合同的用户id，并且存入userId里面
//            JSONObject jsobj = new JSONObject();
//            jsobj.put("thirdPartyUserId", user.getId());
//            if (!StringUtils.isEmpty(user.getName())) {
//                jsobj.put("name", user.getName());
//            } else {
//                return Result.error(ContractMsg.NO_USER_NAME);
//            }
//            if (!StringUtils.isEmpty(user.getPhone())) {
//                jsobj.put("mobile", user.getPhone());
//            }
//            if (!StringUtils.isEmpty(user.getEmail())) {
//                jsobj.put("email", user.getEmail());
//            }
//            if (!StringUtils.isEmpty(user.getIdType())) {
//                jsobj.put("idType", user.getIdType());
//            } else {
//                return Result.error(ContractMsg.NO_USER_TYPE);
//            }
//            if (!StringUtils.isEmpty(user.getIdNumber())) {
//                jsobj.put("idNumber", user.getIdNumber());
//            } else {
//                return Result.error(ContractMsg.NO_USER_NUMBER);
//            }
//            //请求api accountId 第三方平台注册的id
//            ElectronContractApi electronContractApi = electronicHttpHelper.httpPost(url, jsobj, null, true);
//            String accountId = (String) electronContractApi.getData().get("accountId");
//            user.setAccountId(accountId);
//            if (null != electronContractApi.getData()) {
//                electronicContractDao.updateUser(user);
//            }
//        }
        return Result.success();
    }

    @Transactional(rollbackFor = Exception.class)
    public ElectronContractApi getSignflows(String flowId) throws Exception {
        String url = "/v1/signflows/" + flowId;
        String data = electronicHttpHelper.httpGet(url);
        ElectronContractApi electronContractApi = JSONObject.parseObject(data, ElectronContractApi.class);
        return electronContractApi;
    }

    @Transactional(rollbackFor = Exception.class)
    public Result getUserSeal(String accountId, Integer pageNum, Integer numPerPage) throws Exception {
        Map map = new HashMap();
        map.put("accountId", accountId);
        map.put("pageNum", pageNum);
        map.put("numPerPage", numPerPage);
        List<AccountSeal> accountSeals = accountSealDao.listPage(map);
        if (CollectionUtils.isEmpty(accountSeals)) {
            String url = "/v1/accounts/" + accountId + "/seals?offset=" + pageNum + "&size=" + numPerPage;
            String data = electronicHttpHelper.httpGet(url);
            ElectronContractApi electronContractApi = JSONObject.parseObject(data, ElectronContractApi.class);
            if (electronContractApi.getCode() == 0) {
                String total = electronContractApi.getData().get("total").toString();
                if (!StringUtils.isEmpty(total)) {
                    accountSeals = JSONArray.parseArray(electronContractApi.getData().get("accountSeals").toString(), AccountSeal.class);
                    for (AccountSeal seal : accountSeals) {
                        seal.setId(CommonUtil.generateId());
                        seal.setAccountId(accountId);
                        if ("true".equals(seal.getDefaultFlag())){
                            seal.setDefaultFlag(ElectronicConstant.DEFAULT_FLAG_NORMAL);
                        } else {
                            seal.setDefaultFlag(ElectronicConstant.DEFAULT_FLAG_NO_NORMAL);
                        }
                        seal.setStatus(ElectronicConstant.STATUS_NORMAL);
                        seal.setType(ElectronicConstant.SEAL_SYS);
                        String result = FileUtils.getInputStreamByGet(seal.getUrl(), seal.getSealId() + ".png");
                        seal.setUrl(result);
                    }
                    accountSealDao.insertList(accountSeals);
                }
            }
        }
        return Result.success(accountSeals);
    }

    @Transactional(rollbackFor = Exception.class)
    public Result getOrgSeal(String orgId, Integer pageNum, Integer numPerPage) throws Exception {
        Map map = new HashMap();
        map.put("orgId", orgId);
        map.put("pageNum", pageNum);
        map.put("numPerPage", numPerPage);
        List<OrgSeal> orgSeals = orgSealDao.listPage(map);
        if (CollectionUtils.isEmpty(orgSeals)) {
            String url = "/v1/organizations/" + orgId + "/seals?offset=" + pageNum + "&size=" + numPerPage;
            String data = electronicHttpHelper.httpGet(url);
            ElectronContractApi electronContractApi = JSONObject.parseObject(data, ElectronContractApi.class);
            if (electronContractApi.getCode() == 0) {
                String total = electronContractApi.getData().get("total").toString();
                if (!StringUtils.isEmpty(total)) {
                    orgSeals = JSONArray.parseArray(electronContractApi.getData().get("seals").toString(), OrgSeal.class);
                    for (OrgSeal seal : orgSeals) {
                        seal.setId(CommonUtil.generateId());
                        seal.setOrgId(orgId);
                        if ("true".equals(seal.getDefaultFlag())){
                            seal.setDefaultFlag(ElectronicConstant.DEFAULT_FLAG_NORMAL);
                        } else {
                            seal.setDefaultFlag(ElectronicConstant.DEFAULT_FLAG_NO_NORMAL);
                        }
                        seal.setStatus(ElectronicConstant.STATUS_NORMAL);
                        seal.setType(ElectronicConstant.SEAL_SYS);
                        String result = FileUtils.getInputStreamByGet(seal.getUrl(), seal.getSealId() + ".png");
                        seal.setUrl(result);
                    }
                    orgSealDao.insertList(orgSeals);
                }
            }
        }
        return Result.success(orgSeals);
    }


    @Transactional(rollbackFor = Exception.class)
    public Result addAccountSeal(AccountSeal accountSeal) throws Exception {
        accountSeal.setId(CommonUtil.generateId());
        accountSeal.setStatus(ElectronicConstant.STATUS_NORMAL);
        if (!StringUtils.isEmpty(accountSeal.getAccountId())) {
            String url = "/v1/accounts/" + accountSeal.getAccountId() + "/seals/personaltemplate";
            JSONObject object = new JSONObject();
            if (!StringUtils.isEmpty(accountSeal.getAlias())) {
                object.put("alias", accountSeal.getAlias());
            }
            if (!StringUtils.isEmpty(accountSeal.getHeight())) {
                object.put("height", accountSeal.getHeight());
            }
            if (!StringUtils.isEmpty(accountSeal.getWidth())) {
                object.put("width", accountSeal.getWidth());
            }
            if (!StringUtils.isEmpty(accountSeal.getColor())) {
                object.put("color", accountSeal.getColor());
            } else {
                return Result.error(CodeMsg.PARAM_MISS);
            }
            if (!StringUtils.isEmpty(accountSeal.getTemplateType())) {
                object.put("type", accountSeal.getTemplateType());
            } else {
                return Result.error(CodeMsg.PARAM_MISS);
            }
            ElectronContractApi electronContractApi = electronicHttpHelper.httpPost(url, object, null, true);
            if (electronContractApi.getCode() == 0) {
                String sealId = electronContractApi.getData().get("sealId").toString();
                String width = electronContractApi.getData().get("width").toString();
                String height = electronContractApi.getData().get("height").toString();
                String sealUrl = electronContractApi.getData().get("url").toString();
                String result = FileUtils.getInputStreamByGet(sealUrl, sealId + ".png");
                accountSeal.setUrl(result);
                accountSeal.setSealId(sealId);
                accountSeal.setHeight(Integer.valueOf(height));
                accountSeal.setWidth(Integer.valueOf(width));
                accountSeal.setType(ElectronicConstant.SEAL_USER);
                accountSeal.setDefaultFlag(ElectronicConstant.DEFAULT_FLAG_NO_NORMAL);
                accountSealDao.insert(accountSeal);
            }
        }
        return Result.success();
    }

    @Transactional(rollbackFor = Exception.class)
    public Result addOrgSeal(OrgSeal orgSeal) throws Exception {
        orgSeal.setId(CommonUtil.generateId());
        orgSeal.setStatus(ElectronicConstant.STATUS_NORMAL);
        if (!StringUtils.isEmpty(orgSeal.getOrgId())) {
            String url = "/v1/organizations/" + orgSeal.getOrgId() + "/seals/officialtemplate";
            JSONObject object = new JSONObject();
            if (!StringUtils.isEmpty(orgSeal.getAlias())) {
                object.put("alias", orgSeal.getAlias());
            }
            if (!StringUtils.isEmpty(orgSeal.getHeight())) {
                object.put("height", orgSeal.getHeight());
            }
            if (!StringUtils.isEmpty(orgSeal.getWidth())) {
                object.put("width", orgSeal.getWidth());
            }
            if (!StringUtils.isEmpty(orgSeal.getHtext())) {
                object.put("htext", orgSeal.getHtext());
            }
            if (!StringUtils.isEmpty(orgSeal.getQtext())) {
                object.put("qtext", orgSeal.getQtext());
            }
            if (!StringUtils.isEmpty(orgSeal.getColor())) {
                object.put("color", orgSeal.getColor());
            } else {
                return Result.error(CodeMsg.PARAM_MISS);
            }
            if (!StringUtils.isEmpty(orgSeal.getTemplateType())) {
                object.put("type", orgSeal.getTemplateType());
            } else {
                return Result.error(CodeMsg.PARAM_MISS);
            }
            if (!StringUtils.isEmpty(orgSeal.getCentral())) {
                object.put("central", orgSeal.getCentral());
            } else {
                return Result.error(CodeMsg.PARAM_MISS);
            }
            ElectronContractApi electronContractApi = electronicHttpHelper.httpPost(url, object, null, true);
            if (electronContractApi.getCode() == 0) {
                String sealId = electronContractApi.getData().get("sealId").toString();
                String width = electronContractApi.getData().get("width").toString();
                String height = electronContractApi.getData().get("height").toString();
                String sealUrl = electronContractApi.getData().get("url").toString();
                String result = FileUtils.getInputStreamByGet(sealUrl, sealId + ".png");
                orgSeal.setUrl(result);
                orgSeal.setSealId(sealId);
                orgSeal.setHeight(Integer.valueOf(height));
                orgSeal.setWidth(Integer.valueOf(width));
                orgSeal.setType(ElectronicConstant.SEAL_USER);
                orgSeal.setDefaultFlag(ElectronicConstant.DEFAULT_FLAG_NO_NORMAL);
                orgSealDao.insert(orgSeal);
            }
        }
        return Result.success();
    }

    @Transactional(rollbackFor = Exception.class)
    public ElectronContractApi updateAccountSealDefault(String accountSealId) throws Exception {
        ElectronContractApi electronContractApi = new ElectronContractApi();
        AccountSeal accountSeal = accountSealDao.get(accountSealId);
        if (!StringUtils.isEmpty(accountSeal)) {
            String url = "/v1/accounts/" + accountSeal.getAccountId() + "/seals/" + accountSeal.getSealId() + "/setDefault";
            electronContractApi = electronicHttpHelper.httpPut(url, null, null, true);
            if (electronContractApi.getCode() == 0) {
                accountSealDao.removeSealDefault(accountSeal.getAccountId());
                accountSealDao.updateSealDefault(accountSealId);
            }
        }
        return electronContractApi;
    }

    @Transactional(rollbackFor = Exception.class)
    public ElectronContractApi updateOrgSealDefault(String orgSealId) throws Exception {
        ElectronContractApi electronContractApi = new ElectronContractApi();
        OrgSeal orgSeal = orgSealDao.get(orgSealId);
        if (!StringUtils.isEmpty(orgSeal)) {
            String url = "/v1/organizations/" + orgSeal.getOrgId() + "/seals/" + orgSeal.getSealId() + "/setDefault";
            electronContractApi = electronicHttpHelper.httpPut(url, null, null, true);
            if (electronContractApi.getCode() == 0) {
                orgSealDao.removeSealDefault(orgSeal.getOrgId());
                orgSealDao.updateSealDefault(orgSealId);
            }
        }
        return electronContractApi;
    }


    /**
     * 创建签署流程
     *
     * @Pamae contractId 合同Id
     * @Pamae userId 发起方的id
     */
    @Transactional(rollbackFor = Exception.class)
    public Result createSigningProcess(String contractId, String userId) throws Exception {
        Contract contract = electronicContractDao.findByContractId(contractId);
        AccountUser user = electronicContractDao.findByUserId(userId);
        if (StringUtils.isEmpty(contract.getFlowId())) {
            String url = "/v1/signflows";
            //放置签署流程的参数
            JSONObject jsobj = new JSONObject();
            jsobj.put("autoArchive", false);
            //标题
            if (!StringUtils.isEmpty(contract.getTitle())) {
                jsobj.put("businessScene", contract.getTitle());
            } else {
                return Result.error(ContractMsg.SIGNING_PROCESS_TITLE);
            }
            //用户在第三方平台的id
            if (!StringUtils.isEmpty(user.getAccountId())) {
                jsobj.put("initiatorAccountId", user.getAccountId());
            } else {
                return Result.error(ContractMsg.SIGNING_PROCESS_NO_USER);
            }
            //请求api  flowId签署流程的id
            ElectronContractApi electronContractApi = electronicHttpHelper.httpPost(url, jsobj, null, true);
            String flowId = (String) electronContractApi.getData().get("flowId");
            contract.setFlowId(flowId);
            if (null != electronContractApi.getData()) {
                electronicContractDao.updateContract(contract);
            }
        } else {
            return Result.error(ContractMsg.SIGNING_PROCESS_HAVE);
        }

        return Result.success();
    }


    /**
     * 获取直传地址，并且上传文件
     *
     * @Pamae contractId 合同Id
     * @Pamae userId 发起方的id
     */
    @Transactional(rollbackFor = Exception.class)
    public Result getDirectAddress(String contractId, String userId, MultipartFile file, String filePath) throws Exception {
        AccountUser user = electronicContractDao.findByUserId(userId);
        if (null != user) {
            String fileName;
            if (null != file) {
                fileName = file.getOriginalFilename();
            } else {
                return Result.error(ContractMsg.FILE_EORROR);
            }
            String url = "/v1/files/getUploadUrl";
            //放置参数
            JSONObject jsobj = new JSONObject();
            //计算文件的md5值
            String contentMd5 = GetKey.getContentMD5(filePath);
            jsobj.put("contentMd5", contentMd5);
            //文件的MIME类型
            String contentType = Files.probeContentType(Paths.get(fileName));
            jsobj.put("contentType", contentType);
            //文件名称
            jsobj.put("fileName", fileName);
            //文件大小
            long fileSize = file.getSize();
            jsobj.put("fileSize", fileSize);

            jsobj.put("convert2Pdf", false);

            if (!StringUtils.isEmpty(user.getAccountId())) {
                jsobj.put("accountId", user.getAccountId());
            } else {
                return Result.error(ContractMsg.SIGNING_PROCESS_NO_USER);
            }
            //请求api  返回的文件 id  文件上传的直传地址 uploadUrl
            ElectronContractApi electronContractApi = electronicHttpHelper.httpPost(url, jsobj, null, true);
            //将返回回来的文件id存入自己的表
            if (null != electronContractApi.getData()) {
                String fileId = (String) electronContractApi.getData().get("fileId");
                String uploadUrl = (String) electronContractApi.getData().get("uploadUrl");
                System.out.println("uploadUrl:>>>>>>>>>>>" + uploadUrl);
                ElectronContractFile electronContractFile = new ElectronContractFile();
                electronContractFile.setId(CommonUtil.generateId());
                electronContractFile.setElectronicId(contractId);
                electronContractFile.setUploadUrl(uploadUrl);
                electronContractFile.setFileId(fileId);
                electronContractFile.setCreatorId(userId);
                //文件上传到返回的直传地址
                uploadFile(uploadUrl, contentType, contentMd5, filePath);
                electronicContractDao.insertFile(electronContractFile);
            } else {
                return Result.error(ContractMsg.FILE_API_EORROR);
            }

        } else {
            return Result.error(ContractMsg.SIGNING_PROCESS_NO_USER);
        }
        return Result.success();
    }

    /**
     * 流程文档添加
     *
     * @Pamae flowId 流程ID
     * @Pamae files fileId数组
     */
    @Transactional(rollbackFor = Exception.class)
    public Result folwAddDocument(String flowId, String[] files) throws Exception {
        if (!StringUtils.isEmpty(flowId)) {
            ElectronContractApi electronContractApi = getSignflows(flowId);
            if (electronContractApi.getCode() == 0) {
                Map<String, Object> data = electronContractApi.getData();
                int flowStatus = Integer.parseInt(data.get("flowStatus").toString());
                if (flowStatus != 0) {
                    return Result.error("已经开启的流程不能再添加签署文档");
                }
            }
        }
        if (files.length <= 0) {
            return Result.error("流程文档不能为空");
        } else {
            String url = "/v1/signflows/" + flowId + "/documents";
            //放置参数
            JSONObject jsobj = new JSONObject();
            JSONArray docs = new JSONArray();
            for (String fileId : files) {
                JSONObject doc = new JSONObject();
                doc.put("fileId", fileId);
                docs.add(doc);
            }
            jsobj.put("docs", docs);
            ElectronContractApi electronContractApi = electronicHttpHelper.httpPost(url, jsobj, null, true);
            return Result.success(electronContractApi);
        }
    }

    /**
     * 流程附件添加
     *
     * @Pamae flowId 流程ID
     * @Pamae files fileId数组
     */
    @Transactional(rollbackFor = Exception.class)
    public Result folwAddAttachments(String flowId, List<Attachment> attachmentsList) throws Exception {
        if (!StringUtils.isEmpty(flowId)) {
            ElectronContractApi electronContractApi = getSignflows(flowId);
            if (electronContractApi.getCode() == 0) {
                Map<String, Object> data = electronContractApi.getData();
                int flowStatus = Integer.parseInt(data.get("flowStatus").toString());
                if (flowStatus != 0) {
                    return Result.error("已经开启的流程不能再添加签署文档");
                }
            }
        }

        if (CollectionUtils.isEmpty(attachmentsList)) {
            return Result.error("流程附件不能为空");
        } else {
            String url = "/v1/signflows/" + flowId + "/attachments";
            //放置参数
            JSONObject jsobj = new JSONObject();
            JSONArray attachmentList = new JSONArray();
            for (Attachment attachments : attachmentsList) {
                JSONObject attachment = new JSONObject();
                attachment.put("fileId", attachments.getFileId());
                attachment.put("attachmentName", attachments.getAttachmentName());
                attachmentList.add(attachment);
            }
            jsobj.put("attachments", attachmentList);
            ElectronContractApi electronContractApi = electronicHttpHelper.httpPost(url, jsobj, null, true);
            return Result.success(electronContractApi);
        }
    }

    /**
     * 添加手动盖章签署区
     *
     * @Pamae flowId 流程ID
     * @Pamae files fileId数组
     */
    @Transactional(rollbackFor = Exception.class)
    public ElectronContractApi folwFieldsHandSign(String flowId, String keyword, List<Signfield> signfields) throws Exception {
        ElectronContractApi electronContractApi = new ElectronContractApi();
        if (CollectionUtils.isEmpty(signfields)) {
            electronContractApi.setMessage("签署区列表数据不能为空");
            return electronContractApi;
        } else {
            String url = "/v1/signflows/" + flowId + "/signfields/handSign";
            //放置参数
            JSONObject jsobj = new JSONObject();
            JSONArray array = new JSONArray();
            int num = 0;
            for (Signfield signfield : signfields) {
                String fileId = signfield.getFileId();
                String k = URLEncoder.encode(keyword, "UTF-8");
                String l = "/v1/signflows/" + flowId + "/documents/" + fileId + "/searchWordsPosition?keywords=" + k;
                String data = electronicHttpHelper.httpGet(l);
                electronContractApi = JSONObject.parseObject(data, ElectronContractApi.class);
                if (electronContractApi.getCode() == 0) {
                    Map map = electronContractApi.getData();
                    List<Position> positionList = JSONArray.parseArray(map.get("positionList").toString(), Position.class);
                    if (!CollectionUtils.isEmpty(positionList)) {
                        num++;
                        JSONObject signfieldO = new JSONObject();
                        signfieldO.put("fileId", signfield.getFileId());
                        if (!StringUtils.isEmpty(signfield.getSignerAccountId())) {
                            signfieldO.put("signerAccountId", signfield.getSignerAccountId());
                        }
                        if (!StringUtils.isEmpty(signfield.getAuthorizedAccountId())) {
                            signfieldO.put("authorizedAccountId", signfield.getAuthorizedAccountId());
                        }
                        if (!StringUtils.isEmpty(signfield.getActorIndentityType())) {
                            signfieldO.put("actorIndentityType", signfield.getActorIndentityType());
                        }
                        signfieldO.put("assignedPosbean", true);
                        signfieldO.put("order", num);
                        JSONObject po = new JSONObject();
                        for (Position position : positionList) {
                            if (!CollectionUtils.isEmpty(position.getCoordinateList())) {
                                for (Coordinate coordinate : position.getCoordinateList()) {
                                    po.put("posPage", position.getPageIndex());
                                    po.put("posX", coordinate.getPosX() + 110);
                                    po.put("posY", coordinate.getPosY() + 5);
//                                    po.put("addSignTime", true);
                                    signfieldO.put("posBean", po);
                                    array.add(signfieldO);
                                }
                            }
                        }
                    }
                } else {
                    return electronContractApi;
                }
            }
            jsobj.put("signfields", array);
            electronContractApi = electronicHttpHelper.httpPost(url, jsobj, null, true);
            log.info("url：>>>>>>>>>" + url + "，参数：>>>>>>>>>>>>" + jsobj);
        }
        return electronContractApi;
    }

    /**
     * 签署流程开启
     *
     * @Pamae flowId 流程ID
     */
    @Transactional(rollbackFor = Exception.class)
    public ElectronContractApi start(String flowId) throws Exception {
        String url = "/v1/signflows/" + flowId + "/start";
        ElectronContractApi electronContractApi = electronicHttpHelper.httpPut(url, null, null, true);
        return electronContractApi;
    }

    /**
     * 签署流程归档
     *
     * @Pamae flowId 流程ID
     */
    @Transactional(rollbackFor = Exception.class)
    public ElectronContractApi archive(String flowId) throws Exception {
        String url = "/v1/signflows/" + flowId + "/archive";
        ElectronContractApi electronContractApi = electronicHttpHelper.httpPut(url, null, null, true);
        return electronContractApi;

    }

    /**
     * 获取签署地址
     *
     * @Pamae flowId 流程ID
     */
    @Transactional(rollbackFor = Exception.class)
    public ElectronContractApi getExecuteUrl(String flowId, String accountId) throws Exception {
        String url = "/v1/signflows/" + flowId + "/executeUrl?accountId=" + accountId;
        String data = electronicHttpHelper.httpGet(url);
        ElectronContractApi electronContractApi = JSONObject.parseObject(data, ElectronContractApi.class);
        System.out.println(data);
        return electronContractApi;
    }

    /**
     * 流程文档下载
     *
     * @Pamae flowId 流程ID
     */
    @Transactional(rollbackFor = Exception.class)
    public Result down(String flowId) throws Exception {
        String url = "/v1/signflows/" + flowId + "/documents";
        String data = electronicHttpHelper.httpGet(url);
        System.out.println(data);
        return Result.success();
    }


    /**
     * 通过直传地址上传文件二进制字节流
     *
     * @Pamae uploadUrl 文件直传地址
     * @Pamae file 上传的文件
     */
    public void uploadFile(String uploadUrl, String contentType, String contentMd5, String filePath) throws Exception {
        //放置参数
        HttpHead reqHeader = new HttpHead();
        reqHeader.setHeader("Content-MD5", contentMd5);
        log.info("文件上传api的header里面的的Content-MD5值：" + contentMd5);
        reqHeader.setHeader("Content-Type", contentType);
        log.info("文件上传api的header里面的的Content-Type：" + contentType);
        //获取文件的二进制流
        FileInputStream fis = null;
        byte[] filebytes = null;
        filebytes = FileUtils.toByteArray(filePath);
        //请求api  返回的文件id  文件上传的直传地址 uploadUrl
        electronicHttpHelper.httpPutFile(uploadUrl, reqHeader, filebytes, false);

        return;
    }

}
