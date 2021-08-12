package com.lennertsoffers.elementalstones.customClasses;

public class Tools {

    public static boolean checkPlayerCollision(double player, double block) {
        if (player > 0) {
            return !(player < block - 0.3 || player > block + 1.3);
        } else if (player < 0) {
            return !(player > block + 0.3 || player < block - 1.3);
        }
        return false;
    }
}
