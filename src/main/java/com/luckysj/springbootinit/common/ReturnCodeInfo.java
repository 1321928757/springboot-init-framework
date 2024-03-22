package com.luckysj.springbootinit.common;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 通用结果返回码信息
 */
@Getter
@AllArgsConstructor
public enum ReturnCodeInfo {
    Sucess(200,"sucess"),
    Un_Error(0, "未知错误"),
    Expire_Login(302, "身份信息过期，请重新登录"),
    Not_Login(302, "身份校验失败，请尝试重新登录"),
    Not_Permit(303, "权限不足");



    private final int code;
    private final String info;
}
