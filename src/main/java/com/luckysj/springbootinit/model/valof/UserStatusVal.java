package com.luckysj.springbootinit.model.valof;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum UserStatusVal {
    Baned(0,"账户被封禁"),
    Common(1,"正常");

    private final int code;
    private final String name;
}
