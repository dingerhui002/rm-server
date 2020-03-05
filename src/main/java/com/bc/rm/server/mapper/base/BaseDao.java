package com.bc.rm.server.mapper.base;

import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * 共用的Dao
 *
 * @param <T>
 * @author tangyifei
 */
public interface BaseDao<T> {

    /**
     * 新增
     *
     * @param t
     * @return
     */
    Integer insert(T t);

    /**
     * 批量新增
     *
     * @param list
     * @return
     */
    Integer insertList(List<T> list);

    /**
     * 清空
     *
     * @return
     */
    Integer clear();

    /**
     * 删除
     *
     * @param id
     * @return
     */
    Integer delete(String id);

    /**
     * 根据对象删除
     *
     * @param t
     * @return
     */
    Integer deleteByObj(T t);

    /**
     * 根据字段删除
     *
     * @param field
     * @return
     */
    Integer deleteBy(String field);

    /**
     * 根据字段组合删除
     *
     * @param params
     * @return
     */
    Integer deleteByParams(Map<String, Object> params);

    /**
     * 批量删除
     *
     * @param list
     * @return
     */
    Integer deleteList(List<T> list);

    /**
     * 根据主键集合删除
     *
     * @param list
     * @return
     */
    Integer deleteByIds(List<String> list);

    /**
     * 修改
     *
     * @param t
     * @return
     */
    Integer update(T t);

    /**
     * 简单修改实体
     *
     * @param t
     * @return
     */
    Integer simpleUpdate(T t);

    /**
     * 批量更改实体
     *
     * @param t
     * @return
     */
    Integer updateList(List<T> t);

    /**
     * 获取
     *
     * @param id
     * @return
     */
    T get(String id);

    /**
     * 获取简单对象
     *
     * @param id
     * @return
     */
    T getSimple(String id);

    /**
     * 查询所有实体
     *
     * @return
     */
    List<T> listAll();

    /**
     * 根据某个字段查询所有
     *
     * @param field
     * @return
     */
    List<T> listBy(String field);

    /**
     * 根据ID集查询
     *
     * @param list
     * @return
     */
    List<T> listByIds(List<String> list);

    /**
     * 查询所有实体
     *
     * @param params
     * @return
     */
    List<T> listAllByParams(Map<String, Object> params);

    /**
     * 分页获取
     *
     * @param params
     * @return
     */
    List<T> listPage(Map<String, Object> params);

    /**
     * 分页获取列表详细
     *
     * @param params
     * @return
     */
    List<T> listPageDetail(Map<String, Object> params);

    /**
     * 更改数据删除状态
     *
     * @param id
     * @param deleteStatus
     * @return
     */
    Integer updateDeleteStatus(@Param("id") String id, @Param("deleteStatus") String deleteStatus);

    /**
     * 根据主键ID判断数据是否存在
     *
     * @param id
     * @return
     */
    Integer existById(@Param("id") String id);

}
