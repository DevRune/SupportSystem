package com.rune.staff.command.ask;

import com.rune.staff.utils.Files;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;

public class askHistoryCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if (!(sender instanceof Player)) {
            sender.sendMessage("§f[§fSilver§7MC§f] §cConsole kan dit niet doen.");
            return true;
        }

        Player player = (Player) sender;

        if (args.length < 2) {
            player.sendMessage("§f[§fSilver§7MC§f] §4Je moet een speler opgeven.");
            return true;
        }

        if (!player.hasPermission("remakeminetopia.command.askhistory")) {
            player.sendMessage("§f[§fSilver§7MC§f] §cJij hebt hier geen permissies voor.");
            return true;
        }

        Player target = Bukkit.getPlayerExact(args[0]);
        player.sendMessage("§7-------------------- §8[ §aASK §8] §7--------------------");
        for (String s : Files.ASK.getFileConfiguration().getStringList(target.getUniqueId() + ".history")) {
            player.sendMessage(s.toString());
        }
        player.sendMessage("§7-------------------- §8[ §aASK §8] §7--------------------");

        return true;

    }
}
