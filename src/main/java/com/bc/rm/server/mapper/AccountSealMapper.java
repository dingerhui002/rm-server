package com.bc.rm.server.mapper;

import com.bc.rm.server.entity.electronic.AccountSeal;
import com.bc.rm.server.mapper.base.BaseDao;
import org.apache.ibatis.annotations.Param;

/**
 * 个人电子印章持久层
 *
 * @author dd
 */
public interface AccountSealMapper extends BaseDao<AccountSeal> {

    /**
     * 根据电子印章主键id设置为默认
     * @param accountSealId
     * @return
     */
    int updateSealDefault(@Param("accountSealId") String accountSealId);

    /**
     * 移除用户的默认设置
     * @param accountId
     * @return
     */
    int removeSealDefault(@Param("accountId") String accountId);
}
