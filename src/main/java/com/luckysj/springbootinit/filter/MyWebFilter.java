package com.luckysj.springbootinit.filter;

import com.alibaba.fastjson.JSON;
import com.luckysj.springbootinit.common.R;
import com.luckysj.springbootinit.service.impl.RedisService;
import com.luckysj.springbootinit.utils.ThreadLocalUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.logging.log4j.util.Strings;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.util.AntPathMatcher;

import javax.annotation.Resource;
import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 请求过滤器，用来拦截前端请求进行身份校验
 */
@Slf4j
@Configuration
@WebFilter(urlPatterns = "/*", filterName = "myFilter")
public class MyWebFilter implements Filter {
    /*redis服务*/
    @Resource
    private RedisService redisService;

    /*路径匹配器*/
    public static final AntPathMatcher PATH_MATCHER = new AntPathMatcher();

    /*定义无需拦截的请求*/
    public static final String[] urls = {
            "/",
            "/page/**",
            "/user/login",
            "/user/register",
            "/user/updatePwd",
            "/user/seek",
            "/captcha/**",
            "/download",
            "/file/upload",
            "/favicon.ico",
            "/setup/get",
            "/classify/get",
            "/airticle/public/*",
            "/music/normal",
            "/comment/getcomment",
            "/site/apply",
            "/message",
            "/user/reset/*",
            "/test/*"
    };

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        /*获取本次请求路径*/
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        String url = request.getRequestURI();
        System.out.println("检测到请求:"+ request.getRequestURI());

        /*option请求，直接通过*/
        if(HttpMethod.OPTIONS.toString().equals(request.getMethod())){
            /*放行*/
            filterChain.doFilter(request, response);
            return;
        }

        /*放行不需要拦截的请求*/
        if(checkUrl(url, urls)){
            System.out.println("放行 ：" + url);
            /*放行*/
            filterChain.doFilter(request, response);
            return;
        }else{
            String token = request.getHeader("token");
            System.out.println("用户token为：" + token);
            if(token != null){
                System.out.println(token);
                String id = redisService.getString(token);
                if(Strings.isNotEmpty(id)){
                    // 将用户id存入threadLocal中
                    ThreadLocalUtil.addCurrentUser(id);
                    /*登录验证通过，放行*/
                    filterChain.doFilter(request, response);
                    return;
                }
            }
        }

        /*允许跨域*/
        response.setHeader("Access-Control-Allow-Origin","*");
        /*通过输出流向前端响应未登录信息*/
        log.error("拦截到非法访问请求，用户未登录:" + url);
        response.getWriter().write(JSON.toJSONString(R.error("Not Login!!", 302)));
    }

    public Boolean checkUrl(String path, String[] urls){
        for (String url : urls) {
            if(PATH_MATCHER.match(url,path)){
                return true;
            }
        }
        return false;
    }
}

