package com.ee.util;


import org.springframework.stereotype.Component;

import java.util.Random;

@Component
public class Utils {
    public String getRandomString() {
        String alphabet = "abcdefghijklmnopqrstuvwxyz";
        String str = "";
        Random random = new Random();
        int randomLen = 1 + random.nextInt(9);
        for (int i = 0; i < randomLen; i++) {
            char c = alphabet.charAt(random.nextInt(26));
            str += c;
        }
        return str;
    }
}
