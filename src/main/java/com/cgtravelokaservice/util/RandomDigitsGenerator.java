package com.cgtravelokaservice.util;

import java.util.Random;

public class RandomDigitsGenerator {

    public static String generate(int quantity) {
        StringBuilder builder = new StringBuilder();
        Random random = new Random();
        for (int i = 0; i < quantity; i++) {
            int randomNum = random.nextInt(10);
            builder.append(randomNum);
        }
        return builder.toString();
    }
}
