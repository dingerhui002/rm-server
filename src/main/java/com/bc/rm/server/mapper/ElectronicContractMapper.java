package com.bc.rm.server.mapper;

import com.bc.rm.server.entity.Contract;
import com.bc.rm.server.entity.Enterprise;
import com.bc.rm.server.entity.electronic.ElectronContract;
import com.bc.rm.server.entity.electronic.ElectronContractFile;
import com.bc.rm.server.entity.user.AccountUser;
import com.bc.rm.server.mapper.base.BaseDao;
import org.apache.ibatis.annotations.Param;

/**
 * 电子合同持久层
 *
 * @author dd
 */
public interface ElectronicContractMapper extends BaseDao<ElectronContract> {

    /**********************调用其他表*********************************/
    /**
     * 查找用户
     */
    AccountUser findByUserId(@Param("userId") String userId);

    Enterprise findById(@Param("enterpriseId") String enterpriseId);

    /**
     * 更新用户
     */
    int updateUser(AccountUser user);

    /**
     * 查找合同
     */
    Contract findByContractId(@Param("contractId") String contractId);

    /**
     * 更新合同
     */
    int updateContract(Contract contract);

    /**********************为电子和通单独建立的表********************************/
    /**
     * 新增上传文件
     */
    int insertFile(ElectronContractFile electronContractFile);
}
