package com.bc.rm.server.util.key;

/**
 * key的相关设置接口
 *
 * @author tangyifei
 */
public interface KeyPrefix {

    /**
     * 过期时间
     *
     * @return
     */
    int expireSeconds();

    /**
     * 统一的前缀
     *
     * @return
     */
    String getPrefix();

}
