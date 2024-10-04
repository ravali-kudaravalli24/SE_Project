package com.innovators.jobreferralportal.enums;

import lombok.Getter;

@Getter
public enum RoleEnum {
    HR("HR"),
    EMPLOYEE("Employee");

    private final String roleName;

    RoleEnum(String roleName) {
        this.roleName = roleName;
    }

}
