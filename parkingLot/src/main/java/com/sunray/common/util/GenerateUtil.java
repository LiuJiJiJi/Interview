package com.sunray.common.util;

import org.apache.commons.lang3.RandomStringUtils;

import java.util.Random;

public class GenerateUtil {


    public static String singaporeCarnumber() {
        String onePart = RandomStringUtils.randomAlphabetic(2).toUpperCase();
        String twoPart = RandomStringUtils.randomNumeric(2);
        String threePart = RandomStringUtils.randomAlphabetic(2).toUpperCase();
        String fourPart = RandomStringUtils.randomNumeric(4);
        String carNumber = onePart + "-" + twoPart + "-" + threePart + "-" + fourPart;
        return carNumber;
    }

    public static String chinaCarNumber() {
        String[] citys = {"津", "京", "宁"};
        String sectionNamePre = "断面";
        String sectionName = sectionNamePre + (new Random().nextInt(2000));    //断面：随机0-1999
        String city = citys[(new Random().nextInt(3))]; //随机取津、京、宁
        StringBuffer plateNumStr = new StringBuffer(city); //下面开始：拼接车牌号，并随机产生0到9的6位车牌号
        plateNumStr.append(new Random().nextInt(10))
                .append(new Random().nextInt(10))
                .append(new Random().nextInt(10))
                .append(new Random().nextInt(10))
                .append(new Random().nextInt(10))
                .append(new Random().nextInt(10));
        return plateNumStr.toString();
    }

    public static String multipleSpaces(int count) {
        StringBuffer stringBuffer = new StringBuffer();
        for (int i = 0; i < count; i++) {
            stringBuffer.append(" ");
        }
        return stringBuffer.toString();
    }
}
