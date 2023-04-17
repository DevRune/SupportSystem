package com.rune.staff.event.ask;

import com.rune.staff.utils.Files;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;

import java.util.List;

public class askInventoryEvent implements Listener {

    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {

        if (event.getView().getTitle().equalsIgnoreCase("§6Ask Menu")) {
            event.setCancelled(true);
            if (event.isLeftClick()) {
                ItemStack itemStack = event.getCurrentItem();
                SkullMeta skullMeta = (SkullMeta) itemStack.getItemMeta();
                assert skullMeta != null;
                Player target = Bukkit.getPlayerExact(skullMeta.getOwningPlayer().getName());
                Files.ASK.getFileConfiguration().set(target.getUniqueId() + ".staffmember", event.getWhoClicked().getName());
                List<String> history = Files.ASK.getFileConfiguration().getStringList(target.getUniqueId() + ".history");
                history.add("§2" + event.getWhoClicked().getName() + " §aheeft de vraag geclaimt.");
                Files.ASK.getFileConfiguration().set(target.getUniqueId() + ".history", history);
                Files.ASK.save();
                target.sendMessage("§f[§fSilver§7MC§f] §aJe vraag wordt nu behandelt door een stafflid (§2" + event.getWhoClicked().getName() + "§a).");
                event.getWhoClicked().sendMessage("§f[§fSilver§7MC§f] §aJe hebt deze vraag succesvol geclaimt.");
            }
            if (event.isRightClick()) {
                ItemStack itemStack = event.getCurrentItem();
                SkullMeta skullMeta = (SkullMeta) itemStack.getItemMeta();
                assert skullMeta != null;
                Player target = Bukkit.getPlayerExact(skullMeta.getOwningPlayer().getName());
                Files.ASK.getFileConfiguration().set(target.getUniqueId() + ".asked", false);
                Files.ASK.save();
                target.sendMessage("§f[§fSilver§7MC§f] §aJe vraag werdt zo net afgesloten door een stafflid (§2" + event.getWhoClicked().getName() + "§a).");
                event.getWhoClicked().sendMessage("§f[§fSilver§7MC§f] §aJe hebt deze vraag succesvol afgesloten.");
            }
            event.getWhoClicked().closeInventory();
            if (event.getClick().equals(ClickType.MIDDLE)) {
                event.getWhoClicked().sendMessage("§7-------------------- §8[ §aASK §8] §7--------------------");
                ItemStack itemStack = event.getCurrentItem();
                SkullMeta skullMeta = (SkullMeta) itemStack.getItemMeta();
                assert skullMeta != null;
                Player target = Bukkit.getPlayerExact(skullMeta.getOwningPlayer().getName());
                for (String s : Files.ASK.getFileConfiguration().getStringList(target.getUniqueId() + ".history")) {
                    event.getWhoClicked().sendMessage(s.toString());
                }
                event.getWhoClicked().sendMessage("§7-------------------- §8[ §aASK §8] §7--------------------");
            }
        }

    }

}
