package com.sunray.common.constant;

public enum ColorEnum {

    WHITE("White"),
    RED("Red"),
    purple("Purple"),
    BLACK("Black"),
    Unknown("Unknown");

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
