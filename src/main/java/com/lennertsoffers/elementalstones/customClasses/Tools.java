package com.lennertsoffers.elementalstones.customClasses;

public class Tools {

    public static boolean checkPlayerCollision(double player, double block) {
        return !(player > block + 1.3 || player < block - 0.3);
    }

    public static double lengthOfVector(double x1, double x2, double y1, double y2) {
        return Math.sqrt(Math.pow(x2 - x1, 2) + Math.pow(y2 - y1, 2));
    }

    public static double lengthOfVector(double x1, double x2, double y1, double y2, double z1, double z2) {
        return Math.sqrt(Math.pow(x2 - x1, 2) + Math.pow(y2 - y1, 2) + Math.pow(z2 - z1, 2));
    }
}
