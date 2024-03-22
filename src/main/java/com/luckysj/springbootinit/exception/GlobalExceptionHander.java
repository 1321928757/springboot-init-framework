package com.luckysj.springbootinit.exception;

import com.luckysj.springbootinit.common.R;
import com.luckysj.springbootinit.common.ReturnCodeInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.annotation.WebFilter;

@Slf4j
@ResponseBody
@ControllerAdvice(annotations = {RestController.class, WebFilter.class})
public class GlobalExceptionHander {

    /*异常处理器，拦截自定义的企业级异常*/
    @ExceptionHandler(CustomException.class)
    public R<String> ExceptionHandler(CustomException exception){
        log.error("拦截到企业级异常:" + exception.getMessage());

        /*登录验证失败*/
        if(exception.getMessage().equals("NOLOGIN")){
            return R.error(ReturnCodeInfo.Not_Login.getInfo(), ReturnCodeInfo.Not_Login.getCode());
        }

        return R.error(exception.getMessage());
    }


    /*异常处理器，拦截运行时异常*/
    @ExceptionHandler(RuntimeException.class)
    public R<String> ExceptionHandler(RuntimeException exception){
        log.error("拦截到运行时异常:" + exception.getMessage());
        exception.printStackTrace();
        /*数据库主键重复异常*/
        if(exception.getMessage().contains("Duplicate entry")){
            return R.error("数据主键重复异常,请检查id等字段是否重复输入");
        }
        if(exception.getMessage().contains("Data too long")){
            return R.error("字段超出了规定的长度");
        }
        if(exception.getMessage().contains("Error querying database")){
            return R.error("数据库操作出现了异常");
        }
        return R.error(exception.getMessage());
    }
}
