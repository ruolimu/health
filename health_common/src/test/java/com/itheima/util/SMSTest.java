package com.itheima.util;

import org.junit.Test;

/**
 * @ClassName SMSTest
 * @Description
 * @Author 47179
 * @Date 20:59 2019/7/13
 * @Version 1.0
 **/
public class SMSTest {

    @Test
    public void fun()throws Exception {
        SMSUtils.sendShortMessage("SMS_170347673","18858050651","dbbx");
    }
}
