package com.winchem.log.sys.advice;

import com.winchem.log.sys.enums.RespEnum;
import com.winchem.log.sys.response.Result;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
/**
 * @program: winchem_afersale
 * @description: 404
 * @author: zhanglb
 * @create: 2020-11-12 17:39
 */

@RestController
public class MyErrorController implements ErrorController {

    private final static String ERROR_PATH = "/error";

    @RequestMapping(ERROR_PATH)
    public Result handleError(HttpServletRequest request){
        //获取statusCode:401,404,500
        Integer statusCode = (Integer) request.getAttribute("javax.servlet.error.status_code");
        if(statusCode == 500){
            return Result.otherError(RespEnum.INTERNAL_SERVER_ERROR);
        }else if(statusCode == 404){
            //对应的是/error/404.html、/error/404.jsp等，文件位于/templates下面
            return Result.otherError(RespEnum.NOT_FOUND);
        }else if(statusCode == 403){
            return Result.otherError(RespEnum.NO_PERMISSION);
        }else{
            return Result.otherError(RespEnum.NOT_FOUND);
        }

    }


    @Override
    public String getErrorPath() {
        return ERROR_PATH;
    }
}
