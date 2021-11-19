package dev.sasukector.slaves.controllers;

import dev.sasukector.slaves.Slaves;
import dev.sasukector.slaves.events.PointsController;
import dev.sasukector.slaves.helpers.FastBoard;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.*;

public class BoardController {

    private static BoardController instance = null;
    private final Map<UUID, FastBoard> boards = new HashMap<>();
    private @Setter @Getter boolean hideDays;

    public static BoardController getInstance() {
        if (instance == null) {
            instance = new BoardController();
        }
        return instance;
    }

    public BoardController() {
        Bukkit.getScheduler().runTaskTimer(Slaves.getInstance(), this::updateBoards, 0L, 20L);
        this.hideDays = false;
    }

    public void newPlayerBoard(Player player) {
        FastBoard board = new FastBoard(player);
        this.boards.put(player.getUniqueId(), board);
    }

    public void removePlayerBoard(Player player) {
        FastBoard board = this.boards.remove(player.getUniqueId());
        if (board != null) {
            board.delete();
        }
    }

    public void updateBoards() {
        boards.forEach((uuid, board) -> {
            Player player = Bukkit.getPlayer(uuid);
            assert player != null;

            board.updateTitle("§d§l¿Esclavos?");

            List<String> lines = new ArrayList<>();
            lines.add("");
            lines.add("Jugador: §6" + player.getName());
            if (TeamsController.getInstance().isMaster(player)) {
                lines.add("§bEres master");
            } else {
                if (TeamsController.getInstance().isNormalSlave(player)) {
                    lines.add("§7Esclavo normal");
                } else if (TeamsController.getInstance().isGoodSlave(player)) {
                    lines.add("§6Buen esclavo");
                } else if (TeamsController.getInstance().isBadSlave(player)) {
                    lines.add("§cMal esclavo");
                }
            }
            lines.add("Puntos: §6" + PointsController.getInstance().getPlayerPoint(player));
            lines.add("");
            lines.add("Online: §6" + Bukkit.getOnlinePlayers().size());
            lines.add("TPS: §6" + String.format("%.2f", Bukkit.getTPS()[0]));
            lines.add("");

            board.updateLines(lines);
        });
    }

}
