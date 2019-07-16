package com.itheima.controller;

import com.itheima.entity.Result;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @ClassName GlobalExceptionHandler
 * @Description
 * @Author 47179
 * @Date 11:25 2019/7/10
 * @Version 1.0
 **/
@ControllerAdvice
public class GlobalExceptionHandler {
    //定义log日志
    private static final Logger log = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    //拦截的异常,要响应给前端
    @ExceptionHandler(RuntimeException.class) //拦截RuntimeException
    @ResponseBody
    public Result handlerException(RuntimeException e) {
        //log先记录一下
        log.error("出错了", e);
        return new Result(false, e.getMessage());
    }

    //执行出错(不可预知的错误)
    @ExceptionHandler(Exception.class) //拦截Exception
    @ResponseBody
    public Result handlerException(Exception e) {
        //log记录
        log.error("出错了", e);
        return new Result(false,"执行出错了");
    }
}
