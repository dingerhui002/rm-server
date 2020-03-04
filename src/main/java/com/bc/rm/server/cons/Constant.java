package com.bc.rm.server.cons;


/**
 * 常量类
 *
 * @author zhou
 */
public class Constant {
    /**
     * 初始化hashmap容量
     */
    public static final int DEFAULT_HASH_MAP_CAPACITY = 16;

    /**
     * 用户状态:激活
     */
    public static final String USER_STATUS_ACTIVATE = "0";

    // 是否关联迭代
    /**
     * 否
     */
    public static final String IS_LINK_SPRINT_NO = "0";

    /**
     * 是
     */
    public static final String IS_LINK_SPRINT_YES = "1";

    // 优先级
    /**
     * 优先级-低
     */
    public static final String PRIORITY_LOW = "0";

    /**
     * 优先级-中
     */
    public static final String PRIORITY_MEDIUM = "1";

    /**
     * 优先级-高
     */
    public static final String PRIORITY_HIGH = "2";

    // 重要程度
    /**
     * 重要程度-提示
     */
    public static final String IMPORTANCE_REMIND = "0";

    /**
     * 重要程度-一般
     */
    public static final String IMPORTANCE_COMMON = "1";

    /**
     * 重要程度-重要
     */
    public static final String IMPORTANCE_IMPORTANT = "2";

    /**
     * 重要程度-关键
     */
    public static final String IMPORTANCE_KEY = "3";
}
