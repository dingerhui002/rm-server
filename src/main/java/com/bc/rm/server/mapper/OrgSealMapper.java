package com.bc.rm.server.mapper;

import com.bc.rm.server.entity.electronic.OrgSeal;
import com.bc.rm.server.mapper.base.BaseDao;
import org.apache.ibatis.annotations.Param;

/**
 * 机构电子印章持久层
 *
 * @author dd
 */
public interface OrgSealMapper extends BaseDao<OrgSeal> {

    /**
     * 根据电子印章主键id设置为默认
     * @param orgSealId
     * @return
     */
    int updateSealDefault(@Param("orgSealId") String orgSealId);

    /**
     * 移除机构的默认设置
     * @param orgId
     * @return
     */
    int removeSealDefault(@Param("orgId") String orgId);
}
