package com.jee.demo.exceptions;

import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import com.jee.demo.domain.Result;
import com.jee.demo.utils.ResultUtil;
import com.mysql.cj.jdbc.exceptions.MysqlDataTruncation;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import java.sql.SQLIntegrityConstraintViolationException;

@ControllerAdvice
@ResponseBody
public class ControllerException {

//    @ExceptionHandler(MysqlDataTruncation.class)
//    public Result handleMysqlDataTruncation(){
//        return ResultUtil.error(404,"字段长度不合法");
//    }
    @ExceptionHandler({DataIntegrityViolationException.class,SQLIntegrityConstraintViolationException.class})
    public Result handleDataIntegrityViolationException(){
        return ResultUtil.error(401,"数据库异常");
    }
    @ExceptionHandler(InvalidFormatException.class)
    public Result handleInvalidFormatException(){
        return ResultUtil.error(402,"非法格式转换");
    }
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public Result handleSQLHttpMessageNotReadableException(){
        return ResultUtil.error(403,"无入参");
    }


}
