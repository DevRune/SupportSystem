package com.rune.staff.command.ask;

import com.rune.staff.utils.Files;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;

import java.util.Arrays;

public class askMenuCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if (!(sender instanceof Player)) {
            sender.sendMessage("§f[§fSilver§7MC§f] §cConsole kan dit niet doen.");
            return true;
        }

        Player player = (Player) sender;

        if (!player.hasPermission("remakeminetopia.command.askmenu")) {
            player.sendMessage("§f[§fSilver§7MC§f] §cJij hebt hier geen permissies voor.");
            return true;
        }

        Inventory inv = Bukkit.createInventory(null, 9 * 6, "§6Ask Menu");

        FileConfiguration askFile = Files.ASK.getFileConfiguration();

        for (Player loopplayer : Bukkit.getOnlinePlayers()) {

            if (askFile.getBoolean(loopplayer.getUniqueId() + ".asked")) {

                ItemStack itemStack = new ItemStack(Material.PLAYER_HEAD);
                SkullMeta skullMeta = (SkullMeta) itemStack.getItemMeta();
                skullMeta.setOwner(loopplayer.getName());
                skullMeta.setDisplayName(ChatColor.YELLOW + loopplayer.getName());
                skullMeta.setLore(Arrays.asList("§8Status: §7" + askFile.getString(loopplayer.getUniqueId() + ".status"), "§8Stafflid: §7" + askFile.getString(loopplayer.getUniqueId() + ".staffmember"), "§8Vraag:§7" + askFile.getString(loopplayer.getUniqueId() + ".vraag"), "§3Left-click to claim", "§3Right-click to close", "§3Middle-click to view history"));
                itemStack.setItemMeta(skullMeta);
                inv.addItem(itemStack);
            }

        }

        player.openInventory(inv);

        return true;
    }
}
