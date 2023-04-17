package com.rune.staff.command.ask;

import com.rune.staff.utils.Files;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

import java.io.File;
import java.util.Arrays;
import java.util.List;

public class askCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if (!(sender instanceof Player)) {
            sender.sendMessage("§f[§fSilver§7MC§f] §cConsole kan dit niet doen.");
            return true;
        }

        Player player = (Player) sender;

        if (args.length < 1) {
            player.sendMessage("§f[§fSilver§7MC§f] §cJe moet een vraag opgeven.");
            return true;
        }

        if (!player.hasPermission("remakeminetopia.command.ask")) {
            player.sendMessage("§f[§fSilver§7MC§f] §cJij hebt hier geen permissies voor.");
            return true;
        }

        StringBuilder vraag = new StringBuilder();

        for (String part : args) {
            vraag.append(" ");
            vraag.append(part);
        }

        FileConfiguration askConfig = Files.ASK.getFileConfiguration();
        if (askConfig.getBoolean(player.getUniqueId() + ".asked")) {
            player.sendMessage("§f[§fSilver§7MC§f] §cJij hebt nog een vraag waarvan de status §2Openstaand §cof §6In behandeling §cis.");
            player.sendMessage("§f[§fSilver§7MC§f] §cDaarom hebben we je vraag toegevoegt als reactie.");

            List<String> history = Files.ASK.getFileConfiguration().getStringList(player.getUniqueId() + ".history");
            history.add("§6" + player.getName() + ": §e" + vraag);
            Files.ASK.getFileConfiguration().set(player.getUniqueId() + ".history", history);
            Files.ASK.save();
            Bukkit.getPlayerExact(Files.ASK.getFileConfiguration().getString(player.getUniqueId() + ".staffmember")).sendMessage("§aDe speler §2" + player.getName() + " §aheeft een reactie nagelaten op zijn vraag.");
            return true;
        }

        askConfig.set(player.getUniqueId() + ".asked", true);
        askConfig.set(player.getUniqueId() + ".status", "Open");
        askConfig.set(player.getUniqueId() + ".vraag", vraag.toString());
        askConfig.set(player.getUniqueId() + ".staffmember", "Niemand");
        askConfig.set(player.getUniqueId() + ".history", Arrays.asList("§3" + player.getName() + ": §b" + vraag));
        player.sendMessage("§f[§fSilver§7MC§f] §aJe vraag is succesvol verstuurd naar alle online staffmembers.");

        for (Player target : Bukkit.getOnlinePlayers()) {
            if (target.hasPermission("remakeminetopia.staff.ask.see")) {
                target.sendMessage("§7-------------------- §8[ §aASK §8] §7--------------------");
                target.sendMessage(ChatColor.DARK_GREEN + player.getName() + " §aheeft een vraag gesteld:");
                target.sendMessage("§0");
                target.sendMessage("§2Speler: §a" + player.getName());
                target.sendMessage("§2Vraag:§a" + vraag);
                target.sendMessage("§2Status: §aOpen");
                target.sendMessage("§7-------------------- §8[ §aASK §8] §7--------------------");
            }
        }

        Files.ASK.save();

        return true;
    }
}
