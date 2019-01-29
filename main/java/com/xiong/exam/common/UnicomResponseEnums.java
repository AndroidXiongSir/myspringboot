package com.xiong.exam.common;

import java.util.ArrayList;
import java.util.List;

/**
 * @Classname UnicomResponseEnums
 * @Author xiong
 * @Date 2019/1/24 下午3:39
 * @Description TODO
 * @Version 1.0
 **/
public enum  UnicomResponseEnums {

    /* 成功状态码 */
    SUCCESS(1, "成功"),


    /* 参数错误：10001-19999 */
    PARAM_IS_INVALID(10001, "参数无效"),
    PARAM_IS_BLANK(10002, "参数为空"),
    PARAM_TYPE_BIND_ERROR(10003, "参数类型错误"),
    PARAM_NOT_COMPLETE(10004, "参数缺失"),

    /* 用户错误：20001-29999*/
    USER_NOT_LOGGED_IN(20001, "用户未登录"),
    USER_LOGIN_ERROR(20002, "账号不存在或密码错误"),
    USER_ACCOUNT_FORBIDDEN(20003, "账号已被禁用"),
    USER_NOT_EXIST(20004, "用户不存在"),
    USER_HAS_EXISTED(20005, "用户已存在"),
    LOGIN_CREDENTIAL_EXISTED(20006, "凭证已存在"),

    /* 业务错误：30001-39999 */
    SPECIFIED_QUESTIONED_USER_NOT_EXIST(30001, "业务错误"),

    /* 系统错误：40001-49999 */
    SYSTEM_INNER_ERROR(40001, "系统繁忙，请稍后重试"),
    SYSTEM_DB_ERROR(40002,"数据库操作异常"),

    /* 数据错误：50001-599999 */
    RESULE_DATA_NONE(50001, "数据未找到"),
    DATA_IS_WRONG(50002, "数据有误"),
    DATA_ALREADY_EXISTED(50003, "数据已存在"),

    /* 接口错误：60001-69999 */
    INTERFACE_INNER_INVOKE_ERROR(60001, "内部系统接口调用异常"),
    INTERFACE_OUTTER_INVOKE_ERROR(60002, "外部系统接口调用异常"),
    INTERFACE_FORBID_VISIT(60003, "该接口禁止访问"),
    INTERFACE_ADDRESS_INVALID(60004, "接口地址无效"),
    INTERFACE_REQUEST_TIMEOUT(60005, "接口请求超时"),
    INTERFACE_EXCEED_LOAD(60006, "接口负载过高"),

    /* 权限错误：70001-79999 */
    PERMISSION_NO_ACCESS(70001, "无访问权限"),
    RESOURCE_EXISTED(70002, "资源已存在"),
    RESOURCE_NOT_EXISTED(70003, "资源不存在"),

    /* 签名，toekn错误 */
    SIGN_NO_LEGAL(80001,"签名不合法"),
    SIGN_ACCESS_KEY_LEGAL(80002,"加密key不合法"),
    SIGN_TIMESTAMP_LEGAL(80003,"请求时间戳不合法"),
    TOKEN_NO_LEGAL(80004,"token不合法"),
    TOKEN_HAS_EXPIRE(80005,"token已过期"),
    TOKEN_HAS_RESET(80006,"token已重置")



    ;





    /* 签名错误*/

    private Integer code;

    private String message;

    UnicomResponseEnums(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

    public Integer code() {
        return this.code;
    }

    public String message() {
        return this.message;
    }

    public static String getMessage(String name) {
        for (UnicomResponseEnums item : UnicomResponseEnums.values()) {
            if (item.name().equals(name)) {
                return item.message;
            }
        }
        return name;
    }

    public static Integer getCode(String name) {
        for (UnicomResponseEnums item : UnicomResponseEnums.values()) {
            if (item.name().equals(name)) {
                return item.code;
            }
        }
        return null;
    }

    @Override
    public String toString() {
        return this.name();
    }

    /***
     * 校验重复的code值
     */
    static void main(String[] args) {
        UnicomResponseEnums[] apiUnicomResponseEnumss = UnicomResponseEnums.values();
        List<Integer> codeList = new ArrayList<Integer>();
        for (UnicomResponseEnums apiUnicomResponseEnums : apiUnicomResponseEnumss) {
            if (codeList.contains(apiUnicomResponseEnums.code)) {
                System.out.println(apiUnicomResponseEnums.code);
            } else {
                codeList.add(apiUnicomResponseEnums.code());
            }

            System.out.println(apiUnicomResponseEnums.code() + " " + apiUnicomResponseEnums.message());
        }
    }
}
