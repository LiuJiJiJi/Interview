package com.sunray.common.util;

import org.junit.Test;

public class GenerateUtilTest {

    @Test
    public void carNumberTest() {
        System.out.println("singapore r number: " + GenerateUtil.singaporeCarnumber());
        System.out.println("china car number    : " + GenerateUtil.chinaCarNumber());
    }

}
