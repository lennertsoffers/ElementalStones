package com.lennertsoffers.elementalstones.customClasses.models;

import com.lennertsoffers.elementalstones.customClasses.StaticVariables;
import com.lennertsoffers.elementalstones.items.ItemStones;
import com.lennertsoffers.elementalstones.moves.EmptyMove;
import com.lennertsoffers.elementalstones.moves.Move;
import com.lennertsoffers.elementalstones.moves.airMoves.agility.*;
import com.lennertsoffers.elementalstones.moves.airMoves.airbending.*;
import com.lennertsoffers.elementalstones.moves.airMoves.basic.AirBall;
import com.lennertsoffers.elementalstones.moves.airMoves.basic.AreaControl;
import com.lennertsoffers.elementalstones.moves.airMoves.basic.Suction;
import com.lennertsoffers.elementalstones.moves.earthMoves.basic.Earthquake;
import com.lennertsoffers.elementalstones.moves.earthMoves.basic.Pillar;
import com.lennertsoffers.elementalstones.moves.earthMoves.basic.Pushback;
import com.lennertsoffers.elementalstones.moves.earthMoves.earthbending.*;
import com.lennertsoffers.elementalstones.moves.earthMoves.lava.*;
import com.lennertsoffers.elementalstones.moves.fireMoves.basic.AQuickSnack;
import com.lennertsoffers.elementalstones.moves.fireMoves.basic.FireFly;
import com.lennertsoffers.elementalstones.moves.fireMoves.basic.FirePokes;
import com.lennertsoffers.elementalstones.moves.fireMoves.explosion.*;
import com.lennertsoffers.elementalstones.moves.fireMoves.hellfire.*;
import com.lennertsoffers.elementalstones.moves.waterMoves.basic.DolphinDive;
import com.lennertsoffers.elementalstones.moves.waterMoves.basic.Splash;
import com.lennertsoffers.elementalstones.moves.waterMoves.basic.WaterBullet;
import com.lennertsoffers.elementalstones.moves.waterMoves.ice.*;
import com.lennertsoffers.elementalstones.moves.waterMoves.waterbending.AquaRing;
import com.lennertsoffers.elementalstones.moves.waterMoves.waterbending.Bubblebeam;
import com.lennertsoffers.elementalstones.moves.waterMoves.waterbending.HealingWaters;
import com.lennertsoffers.elementalstones.moves.waterMoves.waterbending.PufferBeam;
import com.lennertsoffers.elementalstones.stones.waterStone.WaterStone;
import com.lennertsoffers.elementalstones.stones.windStone.AirbendingStone;
import javafx.scene.media.Track;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scoreboard.*;

import java.util.*;

public class MoveController {

    // Moves
    private ArrayList<Move> moves = new ArrayList<>();

    private final ActivePlayer activePlayer;
    private final Player player;
    private final Scoreboard scoreboard;
    private final ScoreboardManager scoreboardManager;
    private final Objective objective;

    public static HashSet<MoveController> moveControllers = new HashSet<>();

    // Constructor
    public MoveController(ActivePlayer activePlayer) {
        this.activePlayer = activePlayer;
        this.player = activePlayer.getPlayer();

        ScoreboardManager scoreboardManager = Bukkit.getScoreboardManager();
        this.scoreboardManager = Objects.requireNonNull(scoreboardManager);
        this.scoreboard = this.scoreboardManager.getNewScoreboard();
        this.objective = this.scoreboard.registerNewObjective(String.valueOf(StaticVariables.random.nextInt()), "dummy", "Cooldowns");
        objective.setDisplaySlot(DisplaySlot.SIDEBAR);

        moveControllers.add(this);
    }

    public void updateScoreBoard() {
        for (int i = 0; i < this.moves.size(); i++) {
            Move move = this.moves.get(i);

            this.scoreboard.resetScores(ChatColor.GRAY + "" + ChatColor.STRIKETHROUGH + (i + 1) + ": " + move.getName());
            this.scoreboard.resetScores(ChatColor.YELLOW + "" + (i + 1) + ": " + move.getName());

            Score score;
            if (move.isOnCooldown()) {
                score = this.objective.getScore(ChatColor.GRAY + "" + ChatColor.STRIKETHROUGH + (i + 1) + ": " + move.getName());
                score.setScore(move.getSecondsUntilActive());
            } else {
                score = this.objective.getScore(ChatColor.YELLOW + "" + (i + 1) + ": " + move.getName());
                score.setScore(0);
            }
        }

        if (this.activePlayer.isActive()) {
            this.player.setScoreboard(this.scoreboard);
        } else {
            this.player.setScoreboard(this.scoreboardManager.getNewScoreboard());
        }
    }

    public void clearMoves() {
        this.moves.clear();
    }

    public void loadMoves() {
        this.clearMoves();
        ItemStack activeStone = this.player.getInventory().getItem(8);

        if (activeStone != null) {
            if (activeStone.isSimilar(ItemStones.waterStone0)) {
                this.moves.add(new Splash(this.activePlayer));
            } else if (activeStone.isSimilar(ItemStones.waterStone1)) {
                this.moves.add(new Splash(this.activePlayer));
                this.moves.add(new DolphinDive(this.activePlayer));
            } else if (activeStone.isSimilar(ItemStones.waterStone2)) {
                this.moves.add(new Splash(this.activePlayer));
                this.moves.add(new DolphinDive(this.activePlayer));
                this.moves.add(new WaterBullet(this.activePlayer));
            } else if (activeStone.isSimilar(ItemStones.waterStoneBending0)) {
                this.moves.add(new Splash(this.activePlayer));
                this.moves.add(new DolphinDive(this.activePlayer));
                this.moves.add(new WaterBullet(this.activePlayer));
                this.moves.add(new Bubblebeam(this.activePlayer));
            } else if (activeStone.isSimilar(ItemStones.waterStoneBending1)) {
                this.moves.add(new Splash(this.activePlayer));
                this.moves.add(new DolphinDive(this.activePlayer));
                this.moves.add(new WaterBullet(this.activePlayer));
                this.moves.add(new Bubblebeam(this.activePlayer));
                this.moves.add(new HealingWaters(this.activePlayer));
            } else if (activeStone.isSimilar(ItemStones.waterStoneBending2)) {
                this.moves.add(new Splash(this.activePlayer));
                this.moves.add(new DolphinDive(this.activePlayer));
                this.moves.add(new WaterBullet(this.activePlayer));
                this.moves.add(new Bubblebeam(this.activePlayer));
                this.moves.add(new HealingWaters(this.activePlayer));
                this.moves.add(new PufferBeam(this.activePlayer));
            } else if (activeStone.isSimilar(ItemStones.waterStoneBending3)) {
                this.moves.add(new Splash(this.activePlayer));
                this.moves.add(new DolphinDive(this.activePlayer));
                this.moves.add(new WaterBullet(this.activePlayer));
                this.moves.add(new Bubblebeam(this.activePlayer));
                this.moves.add(new HealingWaters(this.activePlayer));
                this.moves.add(new PufferBeam(this.activePlayer));
                this.moves.add(new AquaRing(this.activePlayer));
            } else if (activeStone.isSimilar(ItemStones.waterStoneBending4)) {
                this.moves.add(new Splash(this.activePlayer));
                this.moves.add(new DolphinDive(this.activePlayer));
                this.moves.add(new WaterBullet(this.activePlayer));
                this.moves.add(new Bubblebeam(this.activePlayer));
                this.moves.add(new HealingWaters(this.activePlayer));
                this.moves.add(new PufferBeam(this.activePlayer));
                this.moves.add(new AquaRing(this.activePlayer));
            } else if (activeStone.isSimilar(ItemStones.waterStoneIce0)) {
                this.moves.add(new Splash(this.activePlayer));
                this.moves.add(new DolphinDive(this.activePlayer));
                this.moves.add(new WaterBullet(this.activePlayer));
                this.moves.add(new IceShards(this.activePlayer));
            } else if (activeStone.isSimilar(ItemStones.waterStoneIce1)) {
                this.moves.add(new Splash(this.activePlayer));
                this.moves.add(new DolphinDive(this.activePlayer));
                this.moves.add(new WaterBullet(this.activePlayer));
                this.moves.add(new IceShards(this.activePlayer));
                this.moves.add(new IceSpear(this.activePlayer));
            } else if (activeStone.isSimilar(ItemStones.waterStoneIce2)) {
                this.moves.add(new Splash(this.activePlayer));
                this.moves.add(new DolphinDive(this.activePlayer));
                this.moves.add(new WaterBullet(this.activePlayer));
                this.moves.add(new IceShards(this.activePlayer));
                this.moves.add(new IceSpear(this.activePlayer));
                this.moves.add(new SnowStomp(this.activePlayer));
            } else if (activeStone.isSimilar(ItemStones.waterStoneIce3)) {
                this.moves.add(new Splash(this.activePlayer));
                this.moves.add(new DolphinDive(this.activePlayer));
                this.moves.add(new WaterBullet(this.activePlayer));
                this.moves.add(new IceShards(this.activePlayer));
                this.moves.add(new IceSpear(this.activePlayer));
                this.moves.add(new SnowStomp(this.activePlayer));
                this.moves.add(new DeepFreeze(this.activePlayer));
            } else if (activeStone.isSimilar(ItemStones.waterStoneIce4)) {
                this.moves.add(new Splash(this.activePlayer));
                this.moves.add(new DolphinDive(this.activePlayer));
                this.moves.add(new WaterBullet(this.activePlayer));
                this.moves.add(new IceShards(this.activePlayer));
                this.moves.add(new IceSpear(this.activePlayer));
                this.moves.add(new SnowStomp(this.activePlayer));
                this.moves.add(new DeepFreeze(this.activePlayer));
                this.moves.add(new IceBeam(this.activePlayer));
            }

            else if (activeStone.isSimilar(ItemStones.airStone0)) {
                this.moves.add(new AirBall(this.activePlayer));
            } else if (activeStone.isSimilar(ItemStones.airStone1)) {
                this.moves.add(new AirBall(this.activePlayer));
                this.moves.add(new AreaControl(this.activePlayer));
            } else if (activeStone.isSimilar(ItemStones.airStone2)) {
                this.moves.add(new AirBall(this.activePlayer));
                this.moves.add(new AreaControl(this.activePlayer));
                this.moves.add(new Suction(this.activePlayer));
            } else if (activeStone.isSimilar(ItemStones.airStoneBending0)) {
                this.moves.add(new AirBall(this.activePlayer));
                this.moves.add(new AreaControl(this.activePlayer));
                this.moves.add(new Suction(this.activePlayer));
                this.moves.add(new AirSlash(this.activePlayer));
            } else if (activeStone.isSimilar(ItemStones.airStoneBending1)) {
                this.moves.add(new AirBall(this.activePlayer));
                this.moves.add(new AreaControl(this.activePlayer));
                this.moves.add(new Suction(this.activePlayer));
                this.moves.add(new AirSlash(this.activePlayer));
                this.moves.add(new TrackingBlade(this.activePlayer));
            } else if (activeStone.isSimilar(ItemStones.airStoneBending2)) {
                this.moves.add(new AirBall(this.activePlayer));
                this.moves.add(new AreaControl(this.activePlayer));
                this.moves.add(new Suction(this.activePlayer));
                this.moves.add(new AirSlash(this.activePlayer));
                this.moves.add(new TrackingBlade(this.activePlayer));
                this.moves.add(new WindCloak(this.activePlayer));
            } else if (activeStone.isSimilar(ItemStones.airStoneBending3)) {
                this.moves.add(new AirBall(this.activePlayer));
                this.moves.add(new AreaControl(this.activePlayer));
                this.moves.add(new Suction(this.activePlayer));
                this.moves.add(new AirSlash(this.activePlayer));
                this.moves.add(new TrackingBlade(this.activePlayer));
                this.moves.add(new WindCloak(this.activePlayer));
                this.moves.add(new Tornado(this.activePlayer));
            } else if (activeStone.isSimilar(ItemStones.airStoneBending4)) {
                this.moves.add(new AirBall(this.activePlayer));
                this.moves.add(new AreaControl(this.activePlayer));
                this.moves.add(new Suction(this.activePlayer));
                this.moves.add(new AirSlash(this.activePlayer));
                this.moves.add(new TrackingBlade(this.activePlayer));
                this.moves.add(new WindCloak(this.activePlayer));
                this.moves.add(new Tornado(this.activePlayer));
                this.moves.add(new Levitate(this.activePlayer));
            } else if (activeStone.isSimilar(ItemStones.airStoneAgility0)) {
                this.moves.add(new AirBall(this.activePlayer));
                this.moves.add(new AreaControl(this.activePlayer));
                this.moves.add(new Suction(this.activePlayer));
                this.moves.add(new Dash(this.activePlayer));
            } else if (activeStone.isSimilar(ItemStones.airStoneAgility1)) {
                this.moves.add(new AirBall(this.activePlayer));
                this.moves.add(new AreaControl(this.activePlayer));
                this.moves.add(new Suction(this.activePlayer));
                this.moves.add(new Dash(this.activePlayer));
                this.moves.add(new FlyingKnives(this.activePlayer));
            } else if (activeStone.isSimilar(ItemStones.airStoneAgility2)) {
                this.moves.add(new AirBall(this.activePlayer));
                this.moves.add(new AreaControl(this.activePlayer));
                this.moves.add(new Suction(this.activePlayer));
                this.moves.add(new Dash(this.activePlayer));
                this.moves.add(new FlyingKnives(this.activePlayer));
                this.moves.add(new SmokeBall(this.activePlayer));
            } else if (activeStone.isSimilar(ItemStones.airStoneAgility3)) {
                this.moves.add(new AirBall(this.activePlayer));
                this.moves.add(new AreaControl(this.activePlayer));
                this.moves.add(new Suction(this.activePlayer));
                this.moves.add(new Dash(this.activePlayer));
                this.moves.add(new FlyingKnives(this.activePlayer));
                this.moves.add(new SmokeBall(this.activePlayer));
                this.moves.add(new ChargeJump(this.activePlayer));
            } else if (activeStone.isSimilar(ItemStones.airStoneAgility4)) {
                this.moves.add(new AirBall(this.activePlayer));
                this.moves.add(new AreaControl(this.activePlayer));
                this.moves.add(new Suction(this.activePlayer));
                this.moves.add(new Dash(this.activePlayer));
                this.moves.add(new FlyingKnives(this.activePlayer));
                this.moves.add(new SmokeBall(this.activePlayer));
                this.moves.add(new ChargeJump(this.activePlayer));
                this.moves.add(new Hyperspeed(this.activePlayer));
            }

            else if (activeStone.isSimilar(ItemStones.fireStone0)) {
                this.moves.add(new AQuickSnack(this.activePlayer));
            } else if (activeStone.isSimilar(ItemStones.fireStone1)) {
                this.moves.add(new AQuickSnack(this.activePlayer));
                this.moves.add(new FirePokes(this.activePlayer));
            } else if (activeStone.isSimilar(ItemStones.fireStone2)) {
                this.moves.add(new AQuickSnack(this.activePlayer));
                this.moves.add(new FirePokes(this.activePlayer));
                this.moves.add(new FireFly(this.activePlayer));
            } else if (activeStone.isSimilar(ItemStones.fireStoneHellFire0)) {
                this.moves.add(new AQuickSnack(this.activePlayer));
                this.moves.add(new FirePokes(this.activePlayer));
                this.moves.add(new FireFly(this.activePlayer));
                this.moves.add(new FireTrack(this.activePlayer));
            } else if (activeStone.isSimilar(ItemStones.fireStoneHellFire1)) {
                this.moves.add(new AQuickSnack(this.activePlayer));
                this.moves.add(new FirePokes(this.activePlayer));
                this.moves.add(new FireFly(this.activePlayer));
                this.moves.add(new FireTrack(this.activePlayer));
                this.moves.add(new RingOfFire(this.activePlayer));
            } else if (activeStone.isSimilar(ItemStones.fireStoneHellFire2)) {
                this.moves.add(new AQuickSnack(this.activePlayer));
                this.moves.add(new FirePokes(this.activePlayer));
                this.moves.add(new FireFly(this.activePlayer));
                this.moves.add(new FireTrack(this.activePlayer));
                this.moves.add(new RingOfFire(this.activePlayer));
                this.moves.add(new FireShields(this.activePlayer));
            } else if (activeStone.isSimilar(ItemStones.fireStoneHellFire3)) {
                this.moves.add(new AQuickSnack(this.activePlayer));
                this.moves.add(new FirePokes(this.activePlayer));
                this.moves.add(new FireFly(this.activePlayer));
                this.moves.add(new FireTrack(this.activePlayer));
                this.moves.add(new RingOfFire(this.activePlayer));
                this.moves.add(new FireShields(this.activePlayer));
                this.moves.add(new Flamethrower(this.activePlayer));
            } else if (activeStone.isSimilar(ItemStones.fireStoneHellFire4)) {
                this.moves.add(new AQuickSnack(this.activePlayer));
                this.moves.add(new FirePokes(this.activePlayer));
                this.moves.add(new FireFly(this.activePlayer));
                this.moves.add(new FireTrack(this.activePlayer));
                this.moves.add(new RingOfFire(this.activePlayer));
                this.moves.add(new FireShields(this.activePlayer));
                this.moves.add(new Flamethrower(this.activePlayer));
                this.moves.add(new Hellfire(this.activePlayer));
            } else if (activeStone.isSimilar(ItemStones.fireStoneExplosion0)) {
                this.moves.add(new AQuickSnack(this.activePlayer));
                this.moves.add(new FirePokes(this.activePlayer));
                this.moves.add(new FireFly(this.activePlayer));
                this.moves.add(new SmokeBomb(this.activePlayer));
            } else if (activeStone.isSimilar(ItemStones.fireStoneExplosion1)) {
                this.moves.add(new AQuickSnack(this.activePlayer));
                this.moves.add(new FirePokes(this.activePlayer));
                this.moves.add(new FireFly(this.activePlayer));
                this.moves.add(new SmokeBomb(this.activePlayer));
                this.moves.add(new TripleThreat(this.activePlayer));
            } else if (activeStone.isSimilar(ItemStones.fireStoneExplosion2)) {
                this.moves.add(new AQuickSnack(this.activePlayer));
                this.moves.add(new FirePokes(this.activePlayer));
                this.moves.add(new FireFly(this.activePlayer));
                this.moves.add(new SmokeBomb(this.activePlayer));
                this.moves.add(new TripleThreat(this.activePlayer));
                this.moves.add(new CombustionBeam(this.activePlayer));
            } else if (activeStone.isSimilar(ItemStones.fireStoneExplosion3)) {
                this.moves.add(new AQuickSnack(this.activePlayer));
                this.moves.add(new FirePokes(this.activePlayer));
                this.moves.add(new FireFly(this.activePlayer));
                this.moves.add(new SmokeBomb(this.activePlayer));
                this.moves.add(new TripleThreat(this.activePlayer));
                this.moves.add(new CombustionBeam(this.activePlayer));
                this.moves.add(new RandomRocket(this.activePlayer));
            } else if (activeStone.isSimilar(ItemStones.fireStoneExplosion4)) {
                this.moves.add(new AQuickSnack(this.activePlayer));
                this.moves.add(new FirePokes(this.activePlayer));
                this.moves.add(new FireFly(this.activePlayer));
                this.moves.add(new SmokeBomb(this.activePlayer));
                this.moves.add(new TripleThreat(this.activePlayer));
                this.moves.add(new CombustionBeam(this.activePlayer));
                this.moves.add(new RandomRocket(this.activePlayer));
                this.moves.add(new WarMachine(this.activePlayer));
            }

            else if (activeStone.isSimilar(ItemStones.earthStone0)) {
                this.moves.add(new Pillar(this.activePlayer));
            } else if (activeStone.isSimilar(ItemStones.earthStone1)) {
                this.moves.add(new Pillar(this.activePlayer));
                this.moves.add(new Earthquake(this.activePlayer));
            } else if (activeStone.isSimilar(ItemStones.earthStone2)) {
                this.moves.add(new Pillar(this.activePlayer));
                this.moves.add(new Earthquake(this.activePlayer));
                this.moves.add(new Pushback(this.activePlayer));
            } else if (activeStone.isSimilar(ItemStones.earthStoneBending0)) {
                this.moves.add(new Pillar(this.activePlayer));
                this.moves.add(new Earthquake(this.activePlayer));
                this.moves.add(new Pushback(this.activePlayer));
                this.moves.add(new EarthSpikes(this.activePlayer));
            } else if (activeStone.isSimilar(ItemStones.earthStoneBending1)) {
                this.moves.add(new Pillar(this.activePlayer));
                this.moves.add(new Earthquake(this.activePlayer));
                this.moves.add(new Pushback(this.activePlayer));
                this.moves.add(new EarthSpikes(this.activePlayer));
                this.moves.add(new Stomp(this.activePlayer));
            } else if (activeStone.isSimilar(ItemStones.earthStoneBending2)) {
                this.moves.add(new Pillar(this.activePlayer));
                this.moves.add(new Earthquake(this.activePlayer));
                this.moves.add(new Pushback(this.activePlayer));
                this.moves.add(new EarthSpikes(this.activePlayer));
                this.moves.add(new Stomp(this.activePlayer));
                this.moves.add(new RockThrow(this.activePlayer));
            } else if (activeStone.isSimilar(ItemStones.earthStoneBending3)) {
                this.moves.add(new Pillar(this.activePlayer));
                this.moves.add(new Earthquake(this.activePlayer));
                this.moves.add(new Pushback(this.activePlayer));
                this.moves.add(new EarthSpikes(this.activePlayer));
                this.moves.add(new Stomp(this.activePlayer));
                this.moves.add(new RockThrow(this.activePlayer));
                this.moves.add(new EarthWave(this.activePlayer));
            } else if (activeStone.isSimilar(ItemStones.earthStoneBending4)) {
                this.moves.add(new Pillar(this.activePlayer));
                this.moves.add(new Earthquake(this.activePlayer));
                this.moves.add(new Pushback(this.activePlayer));
                this.moves.add(new EarthSpikes(this.activePlayer));
                this.moves.add(new Stomp(this.activePlayer));
                this.moves.add(new RockThrow(this.activePlayer));
                this.moves.add(new EarthWave(this.activePlayer));
                this.moves.add(new FlyingRock(this.activePlayer));
            } else if (activeStone.isSimilar(ItemStones.earthStoneLava0)) {
                this.moves.add(new Pillar(this.activePlayer));
                this.moves.add(new Earthquake(this.activePlayer));
                this.moves.add(new Pushback(this.activePlayer));
                this.moves.add(new ReverseLogic(this.activePlayer));
            } else if (activeStone.isSimilar(ItemStones.earthStoneLava1)) {
                this.moves.add(new Pillar(this.activePlayer));
                this.moves.add(new Earthquake(this.activePlayer));
                this.moves.add(new Pushback(this.activePlayer));
                this.moves.add(new ReverseLogic(this.activePlayer));
                this.moves.add(new LavaWave(this.activePlayer));
            } else if (activeStone.isSimilar(ItemStones.earthStoneLava2)) {
                this.moves.add(new Pillar(this.activePlayer));
                this.moves.add(new Earthquake(this.activePlayer));
                this.moves.add(new Pushback(this.activePlayer));
                this.moves.add(new ReverseLogic(this.activePlayer));
                this.moves.add(new LavaWave(this.activePlayer));
                this.moves.add(new Comet(this.activePlayer));
            } else if (activeStone.isSimilar(ItemStones.earthStoneLava3)) {
                this.moves.add(new Pillar(this.activePlayer));
                this.moves.add(new Earthquake(this.activePlayer));
                this.moves.add(new Pushback(this.activePlayer));
                this.moves.add(new ReverseLogic(this.activePlayer));
                this.moves.add(new LavaWave(this.activePlayer));
                this.moves.add(new Comet(this.activePlayer));
                this.moves.add(new LavaSpout(this.activePlayer));
            } else if (activeStone.isSimilar(ItemStones.earthStoneLava4)) {
                this.moves.add(new Pillar(this.activePlayer));
                this.moves.add(new Earthquake(this.activePlayer));
                this.moves.add(new Pushback(this.activePlayer));
                this.moves.add(new ReverseLogic(this.activePlayer));
                this.moves.add(new LavaWave(this.activePlayer));
                this.moves.add(new Comet(this.activePlayer));
                this.moves.add(new LavaSpout(this.activePlayer));
                this.moves.add(new LavaSphere(this.activePlayer));
            }
        }
    }

    public ArrayList<Move> getMoves() {
        return this.moves;
    }
}
