package com.sunray.common.constant;

public enum ColorEnum {

    WHITE("white"),
    RED("red"),
    purple("purple"),
    BLACK("black"),
    Unknown("unknown");

    public String value;

    ColorEnum(String value) {
        this.value = value;
    }



    public static ColorEnum getEnumByValue(String value) {
        try {
            value = value.toUpperCase();
            return ColorEnum.valueOf(value);
        } catch (IllegalArgumentException e) {
            return ColorEnum.Unknown;
        }
    }
}
