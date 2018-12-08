package com.lengyan.lyblog.model.enums;

/**
 * <pre>
 *     返回结果enum
 * </pre>
 *
 * @author : lengyan
 * @date : 2018/7/14
 */
public enum ResultCodeEnum {

    /**
     * 成功
     */
    SUCCESS(1),

    /**
     * 失败
     */
    FAIL(0);

    Integer code;

    ResultCodeEnum(Integer code) {
        this.code = code;
    }

    public Integer getCode() {
        return code;
    }
}
