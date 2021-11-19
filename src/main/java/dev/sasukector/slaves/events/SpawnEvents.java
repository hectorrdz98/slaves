package dev.sasukector.slaves.events;

import dev.sasukector.slaves.Slaves;
import dev.sasukector.slaves.controllers.BoardController;
import dev.sasukector.slaves.controllers.GameController;
import dev.sasukector.slaves.controllers.PointsController;
import dev.sasukector.slaves.controllers.TeamsController;
import dev.sasukector.slaves.helpers.ServerUtilities;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextColor;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.player.PlayerItemConsumeEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class SpawnEvents implements Listener {

    private static final Random random = new Random();

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        event.joinMessage(
                Component.text("+ ", TextColor.color(0x84E3A4))
                        .append(Component.text(player.getName(), TextColor.color(0x84E3A4)))
        );
        BoardController.getInstance().newPlayerBoard(player);
        GameController.getInstance().handlePlayerJoin(player);
    }

    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent event) {
        Player player = event.getPlayer();
        BoardController.getInstance().removePlayerBoard(player);
        event.quitMessage(
                Component.text("- ", TextColor.color(0xE38486))
                        .append(Component.text(player.getName(), TextColor.color(0xE38486)))
        );
    }

    @EventHandler
    public void onEntityDamagedByEntity(EntityDamageByEntityEvent event) {
        if (event.getEntity() instanceof Player player && (event.getDamager() instanceof Player ||
                event.getDamager() instanceof Arrow arrow && arrow.getShooter() instanceof Player)) {
            Player damager = event.getDamager() instanceof Player ?
                    (Player) event.getDamager() : (Player) ((Arrow) event.getDamager()).getShooter();
            if (damager != null && TeamsController.getInstance().isMaster(damager)) {
                ItemStack itemStack = damager.getEquipment().getItemInMainHand();
                if (itemStack.hasItemMeta() && itemStack.getItemMeta().hasCustomModelData()) {
                    if (itemStack.getType() == Material.WOODEN_SWORD) {
                        if (itemStack.getItemMeta().getCustomModelData() == 1) {
                            ServerUtilities.sendBroadcastMessage(ServerUtilities.getMiniMessage().parse(
                                    "<bold><color:#F3722C>" + player.getName()
                                            + "</color></bold> <color:#F94144>fue castigado</color>"
                            ));
                            player.teleport(new Location(ServerUtilities.getOverworld(), 1000, 100, 1000));
                        }
                    } else if (itemStack.getType() == Material.BOW) {
                        if (itemStack.getItemMeta().getCustomModelData() == 1) {
                            ServerUtilities.sendBroadcastMessage(ServerUtilities.getMiniMessage().parse(
                                    "<bold><color:#F3722C>" + player.getName()
                                            + "</color></bold> <color:#F94144>fue encerrado temporalmente 10s</color>"
                            ));
                            List<Block> blocks = new ArrayList<>();
                            blocks.add(player.getWorld().getBlockAt(player.getLocation().add(0, -1, 0)));
                            blocks.add(player.getWorld().getBlockAt(player.getLocation().add(0, 2, 0)));
                            blocks.add(player.getWorld().getBlockAt(player.getLocation().add(1, 0, 0)));
                            blocks.add(player.getWorld().getBlockAt(player.getLocation().add(-1, 0, 0)));
                            blocks.add(player.getWorld().getBlockAt(player.getLocation().add(0, 0, 1)));
                            blocks.add(player.getWorld().getBlockAt(player.getLocation().add(0, 0, -1)));
                            blocks.add(player.getWorld().getBlockAt(player.getLocation().add(1, 1, 0)));
                            blocks.add(player.getWorld().getBlockAt(player.getLocation().add(-1, 1, 0)));
                            blocks.add(player.getWorld().getBlockAt(player.getLocation().add(0, 1, 1)));
                            blocks.add(player.getWorld().getBlockAt(player.getLocation().add(0, 1, -1)));
                            for (Block block : blocks) {
                                block.setType(Material.BARRIER);
                            }
                            Bukkit.getScheduler().runTaskLater(Slaves.getInstance(), () -> {
                                for (Block block : blocks) {
                                    block.setType(Material.AIR);
                                }
                            }, 10 * 20L);
                        }
                    }
                }
            }
        }
    }

    @EventHandler
    public void onPlayerEat(PlayerItemConsumeEvent event) {
        ItemStack itemStack = event.getItem();
        if (itemStack.hasItemMeta() && itemStack.getItemMeta().hasCustomModelData()) {
            Player player = event.getPlayer();
            int candyType = itemStack.getItemMeta().getCustomModelData();
            String effect = "";
            player.playSound(player.getLocation(), "minecraft:music.effects.extra", 1, 1);
            if (candyType == 1) {
                PointsController.getInstance().addPointsToPlayer(player, 1);
                player.sendActionBar(Component.text("+1", TextColor.color(0xF9C74F)));
                switch (random.nextInt(10)) {
                    case 0 -> {
                        effect = "VELOCIDAD";
                        player.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 99999, 0, false, false));
                    }
                    case 1 -> {
                        effect = "SALTO";
                        player.addPotionEffect(new PotionEffect(PotionEffectType.JUMP, 99999, 0, false, false));
                    }
                    case 2 -> {
                        effect = "REGENERACIÓN";
                        player.addPotionEffect(new PotionEffect(PotionEffectType.REGENERATION, 99999, 0, false, false));
                    }
                    case 3 -> {
                        effect = "VIDA EXTRA";
                        player.addPotionEffect(new PotionEffect(PotionEffectType.HEALTH_BOOST, 99999, 5, false, false));
                    }
                    case 4 -> {
                        effect = "RESISTENCIA";
                        player.addPotionEffect(new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE, 99999, 0, false, false));
                    }
                    case 5 -> {
                        effect = "IGNÍFUGO";
                        player.addPotionEffect(new PotionEffect(PotionEffectType.FIRE_RESISTANCE, 99999, 0, false, false));
                    }
                    case 6 -> {
                        effect = "VISIÓN NOCTURNA";
                        player.addPotionEffect(new PotionEffect(PotionEffectType.NIGHT_VISION, 99999, 0, false, false));
                    }
                    case 7 -> {
                        effect = "RESPIRACIÓN ACUÁTICA";
                        player.addPotionEffect(new PotionEffect(PotionEffectType.WATER_BREATHING, 99999, 0, false, false));
                    }
                    case 8 -> {
                        effect = "VELOCIDAD MINERA";
                        player.addPotionEffect(new PotionEffect(PotionEffectType.FAST_DIGGING, 99999, 0, false, false));
                    }
                    case 9 -> {
                        effect = "INVISIBILIDAD";
                        player.addPotionEffect(new PotionEffect(PotionEffectType.INVISIBILITY, 99999, 0, false, false));
                    }
                }
            } else {
                PointsController.getInstance().addPointsToPlayer(player, -1);
                player.sendActionBar(Component.text("-1", TextColor.color(0xF94144)));
                switch (random.nextInt(9)) {
                    case 0 -> {
                        effect = "FATIGA";
                        player.addPotionEffect(new PotionEffect(PotionEffectType.SLOW_DIGGING, 99999, 0, false, false));
                    }
                    case 1 -> {
                        effect = "LENTITUD";
                        player.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, 99999, 0, false, false));
                    }
                    case 2 -> {
                        effect = "DEBILIDAD";
                        player.addPotionEffect(new PotionEffect(PotionEffectType.WEAKNESS, 99999, 0, false, false));
                    }
                    case 3 -> {
                        effect = "HAMBRE";
                        player.addPotionEffect(new PotionEffect(PotionEffectType.HUNGER, 99999, 0, false, false));
                    }
                    case 4 -> {
                        effect = "CONFUSIÓN";
                        player.addPotionEffect(new PotionEffect(PotionEffectType.CONFUSION, 99999, 0, false, false));
                    }
                    case 5 -> {
                        effect = "VENENO";
                        player.addPotionEffect(new PotionEffect(PotionEffectType.POISON, 99999, 0, false, false));
                    }
                    case 6 -> {
                        effect = "WITHER";
                        player.addPotionEffect(new PotionEffect(PotionEffectType.WITHER, 99999, 0, false, false));
                    }
                    case 7 -> {
                        effect = "LEVITACIÓN";
                        player.addPotionEffect(new PotionEffect(PotionEffectType.LEVITATION, 10 * 20, 9, false, false));
                    }
                    case 8 -> {
                        effect = "CEGUERA";
                        player.addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS, 99999, 0, false, false));
                    }
                }
            }
            ServerUtilities.sendBroadcastMessage(ServerUtilities.getMiniMessage().parse(
                    "<bold><color:#90BE6D>" + player.getName()
                            + "</color></bold> obtuvo el efecto <bold><color:#F9C74F>" + effect + "</color></bold>"
            ));
        }
    }

}
