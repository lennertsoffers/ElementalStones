package com.lennertsoffers.elementalstones.customClasses.tools;

import org.bukkit.Color;

import java.util.Random;

import static com.lennertsoffers.elementalstones.customClasses.StaticVariables.random;

public class RandomiserTools {
    public static Color randomColor() {
        return Color.fromRGB(random.nextInt(256), random.nextInt(256), random.nextInt(256));
    }

    public static <T extends Enum<?>> T randomEnum(Class<T> tClass){
        int x = random.nextInt(tClass.getEnumConstants().length);
        return tClass.getEnumConstants()[x];
    }
}
