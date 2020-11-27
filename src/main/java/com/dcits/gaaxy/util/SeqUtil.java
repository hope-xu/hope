package com.dcits.gaaxy.util;

import java.math.BigInteger;
import java.time.LocalDate;
import java.util.Random;
import java.util.UUID;

/**
 * @author Hope
 * Date： 2020/10/30  11:20
 * 描述：序列生成
 */
public class SeqUtil {
    public SeqUtil() {
    }

    public static String getStringSeq() {
        String uuid = UUID.randomUUID().toString();
        return uuid.replaceAll("\\-", "").toUpperCase();
    }

    public static BigInteger getNumberSeq() {
        StringBuilder sb = new StringBuilder();
        sb.append(LocalDate.now());
        sb.append(System.nanoTime());
        int length = 32 - sb.length();
        String randNum = getRandomNumber(length);
        sb.append(randNum);
        return new BigInteger(sb.toString());

    }

    public static String getRandomNumber(int length) {
        String base = "0123456789";
        int range = 10;
        Random random = new Random();
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < length; ++i) {
            sb.append(base.charAt(random.nextInt(range)));
        }
        return sb.toString();
    }


    public static String getRandomLetter(int length) {
        String base = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        int range = 26;
        Random random = new Random();
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < length; ++i) {
            sb.append(base.charAt(random.nextInt(range)));
        }
        return sb.toString();
    }

    public static int getRandomNumber(int min, int max) {
        Random random = new Random();
        int tmp = Math.abs(random.nextInt());
        return tmp % (max - min + 1) + min;
    }


    public static void main(String[] args) {
        for (int i = 0; i < 10; i++) {
            System.out.println(getStringSeq());
            System.out.println(getNumberSeq());
        }
    }


}
