package dev.sasukector.slaves.controllers;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextColor;
import net.kyori.adventure.text.format.TextDecoration;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;

public class GameController {

    private static GameController instance = null;

    public static GameController getInstance() {
        if (instance == null) {
            instance =  new GameController();
        }
        return instance;
    }

    public void handlePlayerJoin(Player player) {
        if (TeamsController.getInstance().isMaster(player)) {
            player.getInventory().clear();

            ItemStack lightSword = new ItemStack(Material.WOODEN_SWORD);
            lightSword.addUnsafeEnchantment(Enchantment.MENDING, 1);
            ItemMeta lightSwordMeta = lightSword.getItemMeta();
            lightSwordMeta.displayName(Component.text("Light Sword", TextColor.color(0xF9C74F), TextDecoration.BOLD));
            List<Component> lightSwordLore = new ArrayList<>();
            lightSwordLore.add(Component.text("Envía a los malvados", TextColor.color(0xF9C74F)));
            lightSwordLore.add(Component.text("al lugar donde merecen", TextColor.color(0xF9C74F)));
            lightSwordLore.add(Component.text("y limpia sus inventarios", TextColor.color(0xF9C74F)));
            lightSwordMeta.lore(lightSwordLore);
            lightSwordMeta.setCustomModelData(1);
            lightSwordMeta.setUnbreakable(true);
            lightSwordMeta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
            lightSwordMeta.addItemFlags(ItemFlag.HIDE_UNBREAKABLE);
            lightSwordMeta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
            lightSword.setItemMeta(lightSwordMeta);
            player.getInventory().addItem(lightSword);

            ItemStack lightBow = new ItemStack(Material.BOW);
            lightBow.addUnsafeEnchantment(Enchantment.MENDING, 1);
            ItemMeta lightBowMeta = lightBow.getItemMeta();
            lightBowMeta.displayName(Component.text("Photon Bow", TextColor.color(0xF9C74F), TextDecoration.BOLD));
            List<Component> lightBowLore = new ArrayList<>();
            lightBowLore.add(Component.text("Encierra a los malvados", TextColor.color(0xF9C74F)));
            lightBowLore.add(Component.text("temporalmente... no merecen", TextColor.color(0xF9C74F)));
            lightBowLore.add(Component.text("caminar junto a tí", TextColor.color(0xF9C74F)));
            lightBowMeta.lore(lightBowLore);
            lightBowMeta.setCustomModelData(1);
            lightBowMeta.setUnbreakable(true);
            lightBowMeta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
            lightBowMeta.addItemFlags(ItemFlag.HIDE_UNBREAKABLE);
            lightBowMeta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
            lightBow.setItemMeta(lightBowMeta);
            player.getInventory().addItem(lightBow);

            ItemStack goodCandy = new ItemStack(Material.CHORUS_FRUIT, 64);
            ItemMeta goodCandyMeta = goodCandy.getItemMeta();
            goodCandyMeta.displayName(Component.text("Premio", TextColor.color(0x43AA8B), TextDecoration.BOLD));
            List<Component> goodCandyLore = new ArrayList<>();
            goodCandyLore.add(Component.text("Come este caramelo para obtener", TextColor.color(0x43AA8B)));
            goodCandyLore.add(Component.text("1 punto, además de un efecto", TextColor.color(0x43AA8B)));
            goodCandyLore.add(Component.text("random positivo permanente", TextColor.color(0x43AA8B)));
            goodCandyLore.add(Component.text("hasta tu próxima muerte ❤", TextColor.color(0x43AA8B)));
            goodCandyMeta.lore(goodCandyLore);
            goodCandyMeta.setCustomModelData(1);
            goodCandy.setItemMeta(goodCandyMeta);
            player.getInventory().addItem(goodCandy);

            ItemStack badCandy = new ItemStack(Material.CHORUS_FRUIT, 64);
            ItemMeta badCandyMeta = badCandy.getItemMeta();
            badCandyMeta.displayName(Component.text("Castigo", TextColor.color(0xF94144), TextDecoration.BOLD));
            List<Component> badCandyLore = new ArrayList<>();
            badCandyLore.add(Component.text("Come este caramelo para perder", TextColor.color(0xF94144)));
            badCandyLore.add(Component.text("1 punto, además de un efecto", TextColor.color(0xF94144)));
            badCandyLore.add(Component.text("random negativo permanente", TextColor.color(0xF94144)));
            badCandyLore.add(Component.text("hasta tu próxima muerte ☠", TextColor.color(0xF94144)));
            badCandyMeta.lore(badCandyLore);
            badCandyMeta.setCustomModelData(2);
            badCandy.setItemMeta(badCandyMeta);
            player.getInventory().addItem(badCandy);

            player.getInventory().addItem(new ItemStack(Material.ARROW, 64));
        } else if (TeamsController.getInstance().getNormalPlayers().contains(player)) {
            TeamsController.getInstance().getNormalSlave().addEntry(player.getName());
        }
    }

}
