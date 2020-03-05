package com.bc.rm.server.result;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * 全局结果设置
 *
 * @param <T>
 * @author tangyifei
 */
@Getter
@Setter
@ToString
public class Result<T> {

    /**
     * 状态码
     */
    private int code;

    /**
     * 状态信息
     */
    private String msg;

    /**
     * 返回的数据
     */
    private T data;

    /**
     * 返回的总数
     */
    private T totalCount;

    private Result() {
    }

    private Result(T data) {
        this.data = data;
    }

    private Result(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    private Result(T data, T totalCount) {
        this.data = data;
        this.totalCount = totalCount;
    }

    private Result(CodeMsg codeMsg) {
        if (codeMsg != null) {
            this.code = codeMsg.getCode();
            this.msg = codeMsg.getMsg();
        }
    }

    private Result(String mag) {
        this.msg = msg;
    }

    /**
     * 成功时候的调用
     */
    public static <T> Result<T> success() {
        return new Result<>();
    }

    public static <T> Result<T> success(T data) {
        return new Result<>(data);
    }

    public static <T> Result<T> success(T data, T totalCount) {
        return new Result<>(data, totalCount);
    }

    /**
     * 失败时候的调用
     */
    public static <T> Result<T> error(CodeMsg codeMsg) {
        return new Result<>(codeMsg);
    }

    public static <T> Result<T> error(String msg) {
        return new Result<>(msg);
    }

}
