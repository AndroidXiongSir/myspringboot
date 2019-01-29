package com.xiong.exam.handler;

import com.auth0.jwt.exceptions.TokenExpiredException;
import com.xiong.exam.annotation.Signature;
import com.xiong.exam.common.ResponseModel;
import com.xiong.exam.common.UnicomResponseEnums;
import com.xiong.exam.exception.DataNotFoundException;
import com.xiong.exam.exception.SignatureException;
import com.xiong.exam.exception.TokenNoLegalException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.server.MethodNotAllowedException;
import org.springframework.web.servlet.NoHandlerFoundException;

import javax.validation.ConstraintViolationException;
import java.sql.SQLException;

@ControllerAdvice
public class GloablExceptionHandler {

    private static final Logger logger = LoggerFactory.getLogger(GloablExceptionHandler.class);

    //token为空和不合法异常处理
    @ResponseBody
    @ExceptionHandler(TokenNoLegalException.class)
    public Object tokenNullException(Exception e) {
        logger.error("occurs error when execute method ,message {}",e.getMessage());
        return ResponseModel.failure(UnicomResponseEnums.TOKEN_NO_LEGAL);
    }

    //签名异常
    @ResponseBody
    @ExceptionHandler(SignatureException.class)
    public Object signatureException(Exception e) {
        logger.error("occurs error when execute method ,message {}",e.getMessage());
        return ResponseModel.failure(UnicomResponseEnums.SIGN_NO_LEGAL);
    }

    //数据为空异常
    @ResponseBody
    @ExceptionHandler(DataNotFoundException.class)
    public Object dataNotFoundException(Exception e) {
        logger.error("occurs error when execute method ,message {}",e.getMessage());
        return ResponseModel.failure(UnicomResponseEnums.RESULE_DATA_NONE);
    }

    //sql异常处理
    @ResponseBody
    @ExceptionHandler(value = {SQLException.class, DataAccessException.class})
    @ResponseStatus(value= HttpStatus.INTERNAL_SERVER_ERROR)
    public ResponseModel systemException(Exception e){
        logger.error("occurs error when execute method ,message {}",e.getMessage());
        return ResponseModel.failure(UnicomResponseEnums.SYSTEM_DB_ERROR);
    }

    //404异常
    @ExceptionHandler(value={NoHandlerFoundException.class})
    @ResponseBody
    @ResponseStatus(value=HttpStatus.NOT_FOUND)
    public ResponseModel badRequestNotFound(Exception e){
        logger.error("occurs error when execute method ,message {}",e.getMessage());
        return ResponseModel.exceptionError(HttpStatus.NOT_FOUND);
    }

    //请求方法不允许异常
    @ExceptionHandler(value={MethodNotAllowedException.class})
    @ResponseBody
    @ResponseStatus(value=HttpStatus.METHOD_NOT_ALLOWED)
    public ResponseModel notAllowed(Exception e){
        logger.error("occurs error when execute method ,message {}",e.getMessage());
        return ResponseModel.exceptionError(HttpStatus.METHOD_NOT_ALLOWED);
    }

    //400异常
    @ExceptionHandler(ConstraintViolationException.class)
    @ResponseBody
    @ResponseStatus(value=HttpStatus.BAD_REQUEST)
    public ResponseModel handleConstraintViolationException(Exception e){
        logger.error("occurs error when execute method ,message {}",e.getMessage());
        return ResponseModel.exceptionError(HttpStatus.BAD_REQUEST);
    }

    //400异常
    @ExceptionHandler(HttpMessageNotReadableException.class)
    @ResponseBody
    @ResponseStatus(value=HttpStatus.BAD_REQUEST)
    public ResponseModel methodNotFound(Exception e){
        logger.error("occurs error when execute method ,message {}",e.getMessage());
        return ResponseModel.exceptionError(HttpStatus.BAD_REQUEST);
    }

    //参数不合法
    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseBody
    @ResponseStatus(value=HttpStatus.BAD_REQUEST)
    public ResponseModel handleMethodArgumentNotValidException (Exception e){
        logger.error("occurs error when execute method ,message {}",e.getMessage());
        return ResponseModel.failure("参数不合法");
    }

    @ExceptionHandler(TokenExpiredException.class)
    @ResponseBody
    public ResponseModel tokenExpiredException(Exception e){
        logger.error("occurs error when execute method ,message {}",e.getMessage());
        return ResponseModel.failure(UnicomResponseEnums.TOKEN_HAS_EXPIRE);
    }

}