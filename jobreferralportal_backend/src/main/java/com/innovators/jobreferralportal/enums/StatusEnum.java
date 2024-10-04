package com.innovators.jobreferralportal.enums;

public enum StatusEnum {
    NEW("NEW"),
    ACCEPTED("ACCEPTED"),
    DENIED("DENIED"),
    INTERVIEWR1("INTERVIEWR1"),
    INTERVIEWR2("INTERVIEWR2"),
    INTERVIEWHR("INTERVIEWHR");

    private final String status;

    StatusEnum(String status){
        this.status = status;
    }

}
