package com.luckysj.springbootinit.utils;

/**
 * @author www.luckysj.top 刘仕杰
 * @description ThreadLocal工具类
 * @create 2023/12/24 18:19:46
 */
public class ThreadLocalUtil {

    /**
     * 保存用户对象的ThreadLocal  在拦截器操作 添加、删除相关用户数据
     */
    private static final ThreadLocal<String> userThreadLocal = new ThreadLocal<String>();

    /**
     * 添加当前登录用户方法  在拦截器方法执行前调用设置获取用户
     * @param userid 用户id
     */
    public static void addCurrentUser(String userid){
        userThreadLocal.set(userid);
    }

    /**
     * 获取当前登录用户方法(String)
     */
    public static String getCurrentUser(){
        return userThreadLocal.get();
    }

    public static Long getCurrentUserLong(){
        return Long.valueOf(userThreadLocal.get());
    }

    /**
     * 删除当前登录用户方法  在拦截器方法执行后 移除当前用户对象
     */
    public static void remove(){
        userThreadLocal.remove();
    }
}


