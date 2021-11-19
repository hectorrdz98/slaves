package dev.sasukector.slaves.controllers;

import dev.sasukector.slaves.helpers.ServerUtilities;
import lombok.Getter;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextColor;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.Team;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class TeamsController {

    private static TeamsController instance = null;
    private @Getter Team masterTeam;
    private @Getter Team normalSlave;
    private @Getter Team goodSlave;
    private @Getter Team badSlave;

    public static TeamsController getInstance() {
        if (instance == null) {
            instance = new TeamsController();
        }
        return instance;
    }

    public TeamsController() {
        this.createOrLoadTeams();
    }

    public void createOrLoadTeams() {
        Scoreboard scoreboard = Bukkit.getScoreboardManager().getMainScoreboard();
        masterTeam = scoreboard.getTeam("master");
        normalSlave = scoreboard.getTeam("normal");
        goodSlave = scoreboard.getTeam("good");
        badSlave = scoreboard.getTeam("bad");

        if (masterTeam == null) {
            masterTeam = scoreboard.registerNewTeam("master");
            masterTeam.color(NamedTextColor.AQUA);
            masterTeam.prefix(Component.text("♔ "));
            masterTeam.setAllowFriendlyFire(false);
        }

        if (normalSlave == null) {
            normalSlave = scoreboard.registerNewTeam("normal");
            normalSlave.color(NamedTextColor.GRAY);
            normalSlave.prefix(Component.text(ServerUtilities.getCharFromString("E006") + " ", TextColor.color(0xFFFFFF)));
        }

        if (goodSlave == null) {
            goodSlave = scoreboard.registerNewTeam("good");
            goodSlave.color(NamedTextColor.GOLD);
            goodSlave.prefix(Component.text(ServerUtilities.getCharFromString("E007") + " ", TextColor.color(0xFFFFFF)));
        }

        if (badSlave == null) {
            badSlave = scoreboard.registerNewTeam("bad");
            badSlave.color(NamedTextColor.RED);
            badSlave.prefix(Component.text(ServerUtilities.getCharFromString("E003") + " ", TextColor.color(0xFFFFFF)));
        }
    }

    public List<Player> getMasters() {
        List<Player> players = new ArrayList<>();
        this.masterTeam.getEntries().forEach(entry -> {
            Player player = Bukkit.getPlayer(entry);
            if (player != null) {
                players.add(player);
            }
        });
        return players;
    }

    public List<Player> getNormalSlaves() {
        List<Player> players = new ArrayList<>();
        this.normalSlave.getEntries().forEach(entry -> {
            Player player = Bukkit.getPlayer(entry);
            if (player != null) {
                players.add(player);
            }
        });
        return players;
    }

    public List<Player> getGoodSlaves() {
        List<Player> players = new ArrayList<>();
        this.goodSlave.getEntries().forEach(entry -> {
            Player player = Bukkit.getPlayer(entry);
            if (player != null) {
                players.add(player);
            }
        });
        return players;
    }

    public List<Player> getBadSlaves() {
        List<Player> players = new ArrayList<>();
        this.badSlave.getEntries().forEach(entry -> {
            Player player = Bukkit.getPlayer(entry);
            if (player != null) {
                players.add(player);
            }
        });
        return players;
    }

    public List<Player> getNormalPlayers() {
        return Bukkit.getOnlinePlayers().stream()
                .filter(p -> p.getScoreboard().getTeams().stream()
                        .noneMatch(t -> t.getEntries().contains(p.getName())))
                .collect(Collectors.toList());
    }

    public boolean isMaster(Player player) {
        return this.getMasters().contains(player);
    }

    public boolean isNormalSlave(Player player) {
        return this.getNormalSlaves().contains(player);
    }

    public boolean isGoodSlave(Player player) {
        return this.getGoodSlaves().contains(player);
    }

    public boolean isBadSlave(Player player) {
        return this.getBadSlaves().contains(player);
    }

}
