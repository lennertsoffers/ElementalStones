package com.lennertsoffers.elementalstones.customClasses.tools;

import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.util.Vector;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

public class MathTools {
    public static double lengthOfVector(double x1, double x2, double y1, double y2) {
        return Math.sqrt(Math.pow(x2 - x1, 2) + Math.pow(y2 - y1, 2));
    }

    public static double lengthOfVector(double x1, double x2, double y1, double y2, double z1, double z2) {
        return Math.sqrt(Math.pow(x2 - x1, 2) + Math.pow(y2 - y1, 2) + Math.pow(z2 - z1, 2));
    }

    public static Vector directionOfVector(Location a, Location b) {
        double dX = a.getX() - b.getX();
        double dY = a.getY() - b.getY();
        double dZ = a.getZ() - b.getZ();
        double yaw = Math.atan2(dZ, dX);
        double pitch = Math.atan2(Math.sqrt(dZ * dZ + dX * dX), dY) + Math.PI;
        double x = Math.sin(pitch) * Math.cos(yaw);
        double y = Math.sin(pitch) * Math.sin(yaw);
        double z = Math.cos(pitch);
        return new Vector(x, z, y);
    }

    public static Location locationOnCircle(Location location, double radius, double angle, World world) {
        double particleX = location.getX() + radius * Math.cos(angle);
        double particleZ = location.getZ() + radius * Math.sin(angle);
        return new Location(world, particleX, location.getY() , particleZ);
    }

    public static String[] mirrorX(String[] inputString) {
        int length= inputString.length;
        for (int i = 0; i < length / 2; i++) {
            String mirrorLineTop = inputString[i];
            String mirrorLineBottom = inputString[length - (i + 1)];

            inputString[i] = mirrorLineBottom;
            inputString[length - (i + 1)] = mirrorLineTop;
        }
        return inputString;
    }

    public static String[] mirrorY(String[] inputString) {
        for (int i = 0; i < inputString.length; i++) {
            String line = inputString[i];
            ArrayList<Character> arrayList = new ArrayList<>();
            for (Character character : line.toCharArray()) {
                arrayList.add(character);
            }
            Collections.reverse(arrayList);
            StringBuilder builder = new StringBuilder(arrayList.size());
            for (Character character : arrayList) {
                builder.append(character);
            }
            inputString[i] = builder.toString();
        }
        return inputString;
    }
}



























