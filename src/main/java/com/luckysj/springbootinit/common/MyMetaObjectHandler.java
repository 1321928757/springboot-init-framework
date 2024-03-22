package com.luckysj.springbootinit.common;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * MybatisPlus公共字段自动填充器，可填充账号修改时间，创建时间等等信息
 */
@Component
public class MyMetaObjectHandler implements MetaObjectHandler {
    @Override
    public void insertFill(MetaObject metaObject) {
        metaObject.setValue("createTime", new Date());
    }

    @Override
    public void updateFill(MetaObject metaObject) {

    }
}
