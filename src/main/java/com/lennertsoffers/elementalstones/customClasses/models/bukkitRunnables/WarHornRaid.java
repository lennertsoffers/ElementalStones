package com.lennertsoffers.elementalstones.customClasses.models.bukkitRunnables;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Raid;
import org.bukkit.World;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.entity.Raider;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.*;

public class WarHornRaid extends BukkitRunnable implements Raid {

    private final long startTime;
    private final Location location;
    private final List<Raider> raiders;
    private final Raider captain;
    private final Set<UUID> uuids = new HashSet<>();
    private final Player activator;

    private RaidStatus raidStatus;
    private int raidTicks = 0;

    public WarHornRaid(Location location, World world, Player player) {
        this.startTime = System.currentTimeMillis();
        this.location = location;
        this.raidStatus = RaidStatus.ONGOING;
        this.activator = player;

        this.raiders = new ArrayList<>();
        this.raiders.add((Raider) world.spawnEntity(this.location, EntityType.PILLAGER));
        this.raiders.add((Raider) world.spawnEntity(this.location, EntityType.PILLAGER));
        this.raiders.add((Raider) world.spawnEntity(this.location, EntityType.ILLUSIONER));
        this.raiders.add((Raider) world.spawnEntity(this.location, EntityType.VINDICATOR));
        this.raiders.add((Raider) world.spawnEntity(this.location, EntityType.VINDICATOR));

        this.captain = (Raider) world.spawnEntity(this.location, EntityType.PILLAGER);
        captain.setPatrolLeader(true);
    }

    public Raider getCaptain() {
        return this.captain;
    }

    public void setRaidStatus(RaidStatus raidStatus) {
        this.raidStatus = raidStatus;
    }

    private boolean isFinished() {
        boolean hasLivingRaider = false;
        int raiderIndex = 0;

        while (!hasLivingRaider && raiderIndex < this.raiders.size()) {
            if (!this.raiders.get(raiderIndex).isDead()) {
                hasLivingRaider = true;
            }

            raiderIndex++;
        }

        return !hasLivingRaider;
    }

    @Override
    public void run() {
        if (this.isFinished()) {
            this.winRaid();
        } else if (this.raidTicks > 1200) {
            this.loseRaid();
        } else {
            this.raidTicks++;
        }
    }

    private void winRaid() {
        this.cancel();
        this.setRaidStatus(RaidStatus.VICTORY);

        this.activator.sendMessage(ChatColor.YELLOW + "You have won the raid!");
        this.activator.sendMessage(ChatColor.YELLOW + "Now you will be rewarded with bad omen.");
        this.activator.addPotionEffect(new PotionEffect(PotionEffectType.BAD_OMEN, 1000000, 1));
    }

    private void loseRaid() {
        this.cancel();
        this.setRaidStatus(RaidStatus.LOSS);

        this.activator.sendMessage(ChatColor.RED + "You have lost the raid!");
        this.activator.sendMessage(ChatColor.RED + "You will be punished for this!");
        this.activator.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, 2400, 1));
    }

    @Override
    public boolean isStarted() {
        return true;
    }

    @Override
    public long getActiveTicks() {
        long milliseconds = System.currentTimeMillis() - this.startTime;

        return milliseconds / 50;
    }

    @Override
    public int getBadOmenLevel() {
        return 4;
    }

    @Override
    public void setBadOmenLevel(int badOmenLevel) {
        System.out.println(badOmenLevel);
    }

    @Override
    public Location getLocation() {
        return this.location;
    }

    @Override
    public RaidStatus getStatus() {
        return this.raidStatus;
    }

    @Override
    public int getSpawnedGroups() {
        return 0;
    }

    @Override
    public int getTotalGroups() {
        return 1;
    }

    @Override
    public int getTotalWaves() {
        return 1;
    }

    @Override
    public float getTotalHealth() {
        return 500;
    }

    @Override
    public Set<UUID> getHeroes() {
        return this.uuids;
    }

    @Override
    public List<Raider> getRaiders() {
        return this.raiders;
    }
}
