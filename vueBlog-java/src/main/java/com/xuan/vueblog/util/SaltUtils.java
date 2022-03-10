package com.xuan.vueblog.util;

import org.springframework.stereotype.Component;

import java.util.Random;

@Component
public class SaltUtils {

    //生成一个指定长度的salt
    public static String getSalt(int size) {
        String str = "zxcvbnmasdfghjklqwertyuiopZXCVBNMASDFGHJKLQWERTYUIOP1234567890,.<>:?";
        Random random = new Random();
        StringBuffer stringBuffer = new StringBuffer();
        //循环16次，共取出16个随机字符
        for (int i = 0; i < size; i++) {
            int number = random.nextInt(str.length());
            //生成的随机数作为 str 字符串的下标；从 str 中取出随机字符后追加到 stringBuffer
            stringBuffer.append(str.charAt(number));
        }
        return stringBuffer.toString();
    }
}
