package com.winchem.log.sys.advice;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.winchem.log.sys.response.PageResult;
import com.winchem.log.sys.response.Result;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

/**
 * @program: winchem_afersale
 * @description: 接口统一返回值处理
 * @author: zhanglb
 * @create: 2020-11-03 09:43
 */
@Slf4j
@RestControllerAdvice("com.winchem.aftersale.workorder.controller")
public class GlobalResBodyAdvice implements ResponseBodyAdvice<Object> {
    @Override
    public boolean supports(MethodParameter methodParameter, Class aClass) {
        return true;
    }

    @SneakyThrows
    @Override
    public Object beforeBodyWrite(Object body, MethodParameter methodParameter, MediaType mediaType, Class aClass, ServerHttpRequest serverHttpRequest, ServerHttpResponse serverHttpResponse) {
        if (body instanceof String) {
            ObjectMapper om = new ObjectMapper();
            return om.writeValueAsString(Result.success(body));
        }

        if (body instanceof PageResult) {
            return body;
        }

        if (body instanceof Result) {
            return body;
        }
        return Result.success(body);
    }
}
