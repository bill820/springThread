package com.winchem.log.sys.advice;

import com.winchem.log.sys.enums.RespEnum;
import com.winchem.log.sys.response.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.NoHandlerFoundException;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;

import javax.validation.ConstraintViolationException;
import javax.validation.ValidationException;
import java.util.HashMap;
import java.util.Map;


/**
 * @program: winchem_afersale
 * @description: 异常处理hanlder
 * @author: zhanglb
 * @create: 2020-11-03 09:27
 */
@Slf4j
@RestControllerAdvice("com.winchem.aftersale.workorder.controller")
public class GlobalExceptionHandler {


    /**
     * 处理其他异常
     */
    @ExceptionHandler(value = Exception.class)
    public Result exceptionHandler(Exception e) {
        e.printStackTrace();
        log.info("GlobalExceptionHandler.Exception : "+ e.getMessage());
        return Result.otherError(RespEnum.INTERNAL_SERVER_ERROR);
    }

    /***
     * 404处理
     * @param e
     * @return
     */
    @ExceptionHandler(NoHandlerFoundException.class)
    public Result notFountHandler(ServerHttpRequest request, NoHandlerFoundException e) {
        e.printStackTrace();
        return Result.otherError(RespEnum.NOT_FOUND);
    }

    /**
     * 处理Validated校验异常
     * <p>
     * 注: 常见的ConstraintViolationException异常， 也属于ValidationException异常
     *
     * @param e 捕获到的异常
     * @return 返回给前端的data
     */
    @ResponseStatus(code = HttpStatus.BAD_REQUEST)
    @ExceptionHandler(value = {BindException.class, ValidationException.class, MethodArgumentNotValidException.class})
    public Result handleParameterVerificationException(Exception e) {
        e.printStackTrace();
        log.error(" handleParameterVerificationException has been invoked", e);
        Map<String, Object> resultMap = new HashMap<>(4);
        resultMap.put("code", "100001");
        String msg = null;
        /// BindException
        if (e instanceof BindException) {
            // getFieldError获取的是第一个不合法的参数(P.S.如果有多个参数不合法的话)
            msg = this.fetchErrorMsg(((BindException) e).getBindingResult());
            /// MethodArgumentNotValidException
        } else if (e instanceof MethodArgumentNotValidException) {
            msg = this.fetchErrorMsg(((BindException) e).getBindingResult());
            /// ValidationException 的子类异常ConstraintViolationException
        } else if (e instanceof ConstraintViolationException) {
            /*
             * ConstraintViolationException的e.getMessage()形如
             *     {方法名}.{参数名}: {message}
             *  这里只需要取后面的message即可
             */
            msg = e.getMessage();
            /// ValidationException 的其它子类异常
        } else {
            msg = "校验参数时异常";
        }
        resultMap.put("msg", msg);
        log.info("handleParameterVerificationException= {}", msg);
        return Result.ValidFailed(resultMap);
    }

    private String fetchErrorMsg(BindingResult bindingResult) {
        StringBuffer errorMesssage = new StringBuffer();
        for (FieldError fieldError : bindingResult.getFieldErrors()) {
            errorMesssage.append(fieldError.getField()).append(" : ").append(fieldError.getDefaultMessage()).append(" | ");
        }
        return errorMesssage.toString();
    }

    /**
     * 用来处理bean validation异常
     *
     * @param ex
     * @return
     */
    /*@ExceptionHandler(ConstraintViolationException.class)
    public Result resolveConstraintViolationException(ConstraintViolationException ex) {
        Result errorWebResult = new Result();
        Set<ConstraintViolation<?>> constraintViolations = ex.getConstraintViolations();
        if (!CollectionUtils.isEmpty(constraintViolations)) {
            StringBuilder msgBuilder = new StringBuilder();
            for (ConstraintViolation constraintViolation : constraintViolations) {
                msgBuilder.append(constraintViolation.getMessage()).append(",");
            }
            String errorMessage = msgBuilder.toString();
            if (errorMessage.length() > 1) {
                errorMessage = errorMessage.substring(0, errorMessage.length() - 1);
            }
            errorWebResult.setMsg(errorMessage);
            return errorWebResult;
        }
        errorWebResult.setMsg(ex.getMessage());
        return errorWebResult;
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Result resolveMethodArgumentNotValidException(MethodArgumentNotValidException ex) {
        Result errorWebResult = new Result();
        List<ObjectError> objectErrors = ex.getBindingResult().getAllErrors();
        if (!CollectionUtils.isEmpty(objectErrors)) {
            StringBuilder msgBuilder = new StringBuilder();
            for (ObjectError objectError : objectErrors) {
                msgBuilder.append(objectError.getDefaultMessage()).append(",");
            }
            String errorMessage = msgBuilder.toString();
            if (errorMessage.length() > 1) {
                errorMessage = errorMessage.substring(0, errorMessage.length() - 1);
            }
            errorWebResult.setMsg(errorMessage);
            return errorWebResult;
        }
        errorWebResult.setMsg(ex.getMessage());
        return errorWebResult;
    }*/

}