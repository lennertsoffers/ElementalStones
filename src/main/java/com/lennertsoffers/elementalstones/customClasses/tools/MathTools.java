package com.lennertsoffers.elementalstones.customClasses.tools;

import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.util.Vector;

import java.util.HashMap;

public class MathTools {
    public static Vector getDirectionNormVector(Location a, Location b) {
        Vector direction = new Vector(b.getX() - a.getX(), 0, b.getZ() - a.getZ());
        double lengthOfVector = Math.sqrt(Math.pow(direction.getX(), 2) + Math.pow(direction.getZ(), 2));
        return direction.multiply(1/lengthOfVector);
    }

    public static Vector getDirectionNormVector3d(Location a, Location b) {
        Vector direction = new Vector(b.getX() - a.getX(), b.getY() - a.getY(), b.getZ() - a.getZ());
        double lengthOfVector = Math.sqrt(Math.pow(direction.getX(), 2)+ Math.pow(direction.getY(), 2) + Math.pow(direction.getZ(), 2));
        return direction.multiply(1/lengthOfVector);
    }

    public static double calculate3dDistance(Location a, Location b) {
        return Math.sqrt(Math.pow(b.getX() - a.getX(), 2) + Math.pow(b.getY() - a.getY(), 2) + Math.pow(b.getZ() - a.getZ(), 2));
    }

    public static Location locationOnCircle(Location location, double radius, double angle, World world) {
        double particleX = location.getX() + radius * Math.cos(angle);
        double particleZ = location.getZ() + radius * Math.sin(angle);
        return new Location(world, particleX, location.getY() , particleZ);
    }

    public static Location locationOnCircle(Location location, double radius, double angle) {
        double locationX = location.getX() + radius * Math.sin(angle);
        double locationZ = location.getZ() + radius * Math.cos(angle);
        return new Location(location.getWorld(), locationX, location.getY(), locationZ);
    }

    public static HashMap<String, Double> calculatePointOnThrowFunction(double beginVelocity, double y, double yaw, double throwAngle, double t, Vector velocity) {
        double theta = throwAngle * Math.PI / 180;
        double phi = -(yaw * Math.PI / 180);
        double g = -9.81;

        double resultX = (beginVelocity * Math.cos(theta) + (Math.abs(velocity.getX()) * 20)) * t * Math.sin(phi);
        double resultZ = (beginVelocity * Math.cos(theta) + (Math.abs(velocity.getZ()) * 20)) * t * Math.cos(phi);
        double resultY = (g / 2 * Math.pow(t, 2) +  (beginVelocity * Math.sin(theta) + velocity.getY() * 20) * t + y);

        HashMap<String, Double> result = new HashMap<>();
        result.put("x", resultX);
        result.put("z", resultZ);
        result.put("y", resultY);

        return result;
    }
}



























