package com.xiong.exam.common;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

/**
 * @Classname ResponseModel
 * @Author xiong
 * @Date 2019/1/24 下午3:37
 * @Description TODO
 * @Version 1.0
 **/
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class ResponseModel<T> {
    private static final long serialVersionUID = 874200365941306385L;

    private Integer code;

    private String msg;

    private T data;

    public static ResponseModel success() {
        ResponseModel result = new ResponseModel();
        result.setUnicomResponseEnums(UnicomResponseEnums.SUCCESS);
        return result;
    }

    public ResponseModel success(T data) {
        ResponseModel result = new ResponseModel();
        result.setUnicomResponseEnums(UnicomResponseEnums.SUCCESS);
        result.setData(data);
        return result;
    }

    public static ResponseModel failure(UnicomResponseEnums resultCode) {
        ResponseModel result = new ResponseModel();
        result.setUnicomResponseEnums(resultCode);
        return result;
    }

    public ResponseModel failure(UnicomResponseEnums resultCode, T data) {
        ResponseModel result = new ResponseModel();
        result.setUnicomResponseEnums(resultCode);
        result.setData(data);
        return result;
    }

    public static ResponseModel failure(String message) {
        ResponseModel result = new ResponseModel();
        result.setCode(UnicomResponseEnums.PARAM_IS_INVALID.code());
        result.setMsg(message);
        return result;
    }

    public static ResponseModel exceptionError(HttpStatus code){
        ResponseModel result = new ResponseModel();
        result.setHttpStatus(code);
        return result;
    }

    private void setUnicomResponseEnums(UnicomResponseEnums code) {
        this.code = code.code();
        this.msg = code.message();
    }

    private void setHttpStatus(HttpStatus code){
        this.code = code.value();
        this.msg = code.getReasonPhrase();
    }

}
