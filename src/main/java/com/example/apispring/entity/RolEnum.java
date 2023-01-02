package com.example.apispring.entity;

import lombok.Getter;

@Getter
public enum RolEnum {
    ADMIN("ADMIN"),OPERATOR("OPERATOR");
    String text;
    RolEnum(String text){
        this.text = text;
    }
}
