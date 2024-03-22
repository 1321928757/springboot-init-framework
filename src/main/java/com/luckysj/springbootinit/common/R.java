package com.luckysj.springbootinit.common;

import lombok.Data;

/**
 * 通用结果返回类，返回给前端的数据封装对象
 * @param <T>
 */
@Data
public class R<T> {

    private Integer code; //编码：1成功，0和其它数字为失败

    private String msg; //错误信息

    private T data; //数据

//    private Map map = new HashMap(); //动态数据

    /*返回成功信息，并携带数据*/
    public static <T> R<T> success(T object) {
        R<T> r = new R<T>();
        r.data = object;
        r.code = 200;
        return r;
    }

    /*返回错误信息*/
    public static <T> R<T> error(String msg) {
        R r = new R();
        r.msg = msg;
        r.code = 0;
        return r;
    }

//    public R<T> add(String key, Object value) {
//        this.map.put(key, value);
//        return this;
//    }

    public static <T> R<T> error(String msg, Integer code){
        R r = new R();
        r.msg = msg;
        r.code = code;
        return r;
    }

}
