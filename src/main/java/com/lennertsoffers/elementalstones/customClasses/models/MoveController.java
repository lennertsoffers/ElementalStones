package com.lennertsoffers.elementalstones.customClasses.models;

import com.lennertsoffers.elementalstones.customClasses.StaticVariables;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.*;

import java.util.HashSet;
import java.util.Objects;

public class MoveController {

    // Moves
    private final Move move1;
    private final Move move2;
    private final Move move3;
    private final Move move4;
    private final Move move5;
    private final Move move6;
    private final Move move7;
    private final Move move8;

    private final Player player;
    private final Scoreboard scoreboard;
    private final Objective objective;

    public static HashSet<MoveController> moveControllers = new HashSet<>();

    // Constructor
    public MoveController(Player player) {
        this.move1 = new Move();
        this.move2 = new Move();
        this.move3 = new Move();
        this.move4 = new Move();
        this.move5 = new Move();
        this.move6 = new Move();
        this.move7 = new Move();
        this.move8 = new Move();

        this.player = player;

        ScoreboardManager scoreboardManager = Bukkit.getScoreboardManager();
        this.scoreboard = Objects.requireNonNull(scoreboardManager).getNewScoreboard();
        this.objective = this.scoreboard.registerNewObjective(String.valueOf(StaticVariables.random.nextInt()), "dummy", "Cooldowns");
        objective.setDisplaySlot(DisplaySlot.SIDEBAR);

        moveControllers.add(this);
    }

    public void updateScoreBoard() {
        this.scoreboard.resetScores(ChatColor.GRAY + "" + ChatColor.STRIKETHROUGH + "Move 1:");
        this.scoreboard.resetScores(ChatColor.GRAY + "" + ChatColor.STRIKETHROUGH + "Move 2:");
        this.scoreboard.resetScores(ChatColor.GRAY + "" + ChatColor.STRIKETHROUGH + "Move 3:");
        this.scoreboard.resetScores(ChatColor.GRAY + "" + ChatColor.STRIKETHROUGH + "Move 4:");
        this.scoreboard.resetScores(ChatColor.GRAY + "" + ChatColor.STRIKETHROUGH + "Move 5:");
        this.scoreboard.resetScores(ChatColor.GRAY + "" + ChatColor.STRIKETHROUGH + "Move 6:");
        this.scoreboard.resetScores(ChatColor.GRAY + "" + ChatColor.STRIKETHROUGH + "Move 7:");
        this.scoreboard.resetScores(ChatColor.GRAY + "" + ChatColor.STRIKETHROUGH + "Move 8:");
        this.scoreboard.resetScores(ChatColor.YELLOW + "Move 1:");
        this.scoreboard.resetScores(ChatColor.YELLOW + "Move 2:");
        this.scoreboard.resetScores(ChatColor.YELLOW + "Move 3:");
        this.scoreboard.resetScores(ChatColor.YELLOW + "Move 4:");
        this.scoreboard.resetScores(ChatColor.YELLOW + "Move 5:");
        this.scoreboard.resetScores(ChatColor.YELLOW + "Move 6:");
        this.scoreboard.resetScores(ChatColor.YELLOW + "Move 7:");
        this.scoreboard.resetScores(ChatColor.YELLOW + "Move 8:");

        Score score1;
        if (!this.move1.isNull()) {
            if (this.move1.isOnCooldown()) {
                score1 = this.objective.getScore(ChatColor.GRAY + "" + ChatColor.STRIKETHROUGH + "Move 1:");
                score1.setScore((int) this.move1.secondsUntilActive());
            } else {
                score1 = this.objective.getScore(ChatColor.YELLOW + "Move 1:");
                score1.setScore(0);
            }
        }

        Score score2;
        if (!this.move2.isNull()) {
            if (this.move2.isOnCooldown()) {
                score2 = this.objective.getScore(ChatColor.GRAY + "" + ChatColor.STRIKETHROUGH + "Move 2:");
                score2.setScore((int) this.move2.secondsUntilActive());
            } else {
                score2 = this.objective.getScore(ChatColor.YELLOW + "Move 2:");
                score2.setScore(0);
            }
        }

        Score score3;
        if (!this.move3.isNull()) {
            if (this.move3.isOnCooldown()) {
                score3 = this.objective.getScore(ChatColor.GRAY + "" + ChatColor.STRIKETHROUGH + "Move 3:");
                score3.setScore((int) this.move3.secondsUntilActive());
            } else {
                score3 = this.objective.getScore(ChatColor.YELLOW + "Move 3:");
                score3.setScore(0);
            }
        }

        Score score4;
        if (!this.move4.isNull()) {
            if (this.move4.isOnCooldown()) {
                score4 = this.objective.getScore(ChatColor.GRAY + "" + ChatColor.STRIKETHROUGH + "Move 4:");
                score4.setScore((int) this.move4.secondsUntilActive());
            } else {
                score4 = this.objective.getScore(ChatColor.YELLOW + "Move 4:");
                score4.setScore(0);
            }
        }

        Score score5;
        if (!this.move5.isNull()) {
            if (this.move5.isOnCooldown()) {
                score5 = this.objective.getScore(ChatColor.GRAY + "" + ChatColor.STRIKETHROUGH + "Move 5:");
                score5.setScore((int) this.move5.secondsUntilActive());
            } else {
                score5 = this.objective.getScore(ChatColor.YELLOW + "Move 5:");
                score5.setScore(0);
            }
        }

        Score score6;
        if (!this.move6.isNull()) {
            if (this.move6.isOnCooldown()) {
                score6 = this.objective.getScore(ChatColor.GRAY + "" + ChatColor.STRIKETHROUGH + "Move 6:");
                score6.setScore((int) this.move6.secondsUntilActive());
            } else {
                score6 = this.objective.getScore(ChatColor.YELLOW + "Move 6:");
                score6.setScore(0);
            }
        }

        Score score7;
        if (!this.move7.isNull()) {
            if (this.move7.isOnCooldown()) {
                score7 = this.objective.getScore(ChatColor.GRAY + "" + ChatColor.STRIKETHROUGH + "" + "Move 7:");
                score7.setScore((int) this.move7.secondsUntilActive());
            } else {
                score7 = this.objective.getScore(ChatColor.YELLOW + "Move 7:");
                score7.setScore(0);
            }
        }

        Score score8;
        if (!this.move8.isNull()) {
            if (this.move8.isOnCooldown()) {
                score8 = this.objective.getScore(ChatColor.GRAY + "" + ChatColor.STRIKETHROUGH + "Move 8:");
                score8.setScore((int) this.move8.secondsUntilActive());
            } else {
                score8 = this.objective.getScore(ChatColor.YELLOW + "Move 8:");
                score8.setScore(0);
            }
        }

        this.player.setScoreboard(this.scoreboard);
    }


    // Getters
    public Move getMove1() {
        return move1;
    }

    public Move getMove2() {
        return move2;
    }

    public Move getMove3() {
        return move3;
    }

    public Move getMove4() {
        return move4;
    }

    public Move getMove5() {
        return move5;
    }

    public Move getMove6() {
        return move6;
    }

    public Move getMove7() {
        return move7;
    }

    public Move getMove8() {
        return move8;
    }

    // Other methods
    public void clearMoves() {
        move1.clearMove();
        move2.clearMove();
        move3.clearMove();
        move4.clearMove();
        move5.clearMove();
        move6.clearMove();
        move7.clearMove();
        move8.clearMove();
        System.out.println("clearmoves");
    }
}
