package de.presti.botbase.utils;

import java.util.ArrayList;
import java.util.Random;

public class ArrayUtil {
    public static ArrayList<String> commandcooldown = new ArrayList<>();

    public static String getRandomShit(int length) {
        String end = "";

        for (int i = 0; i < length; i++) {
            end += new Random().nextInt(9);
        }

        return end;
    }

}