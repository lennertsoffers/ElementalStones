package com.lennertsoffers.elementalstones.customClasses;

public class Tools {

    public static boolean checkPlayerCollision(double player, double block) {
    return !(player > block + 1.3 || player < block - 0.3);
    }
}
