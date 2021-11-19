package dev.sasukector.slaves;

import dev.sasukector.slaves.commands.PointsCommand;
import dev.sasukector.slaves.controllers.BoardController;
import dev.sasukector.slaves.events.SpawnEvents;
import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Objects;

public final class Slaves extends JavaPlugin {

    private static @Getter Slaves instance;

    @Override
    public void onEnable() {
        // Plugin startup logic
        getLogger().info(ChatColor.DARK_PURPLE + "Slaves startup!");
        instance = this;

        // Register events
        this.getServer().getPluginManager().registerEvents(new SpawnEvents(), this);
        Bukkit.getOnlinePlayers().forEach(player -> BoardController.getInstance().newPlayerBoard(player));

        // Register commands
        Objects.requireNonNull(Slaves.getInstance().getCommand("points")).setExecutor(new PointsCommand());
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
        getLogger().info(ChatColor.DARK_PURPLE + "Slaves shutdown!");
    }
}
