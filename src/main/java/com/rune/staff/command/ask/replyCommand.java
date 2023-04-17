package com.rune.staff.command.ask;

import com.rune.staff.utils.Files;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.List;

public class replyCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if (!(sender instanceof Player)) {
            sender.sendMessage("§f[§fSilver§7MC§f] §cConsole kan dit niet doen.");
            return true;
        }

        Player player = (Player) sender;

        if (args.length < 2) {
            player.sendMessage("§f[§fSilver§7MC§f] §4Gebruik: §c/askreply (speler) (bericht).");
            return true;
        }

        if (!player.hasPermission("netherlandsmp.command.askreply")) {
            player.sendMessage("§f[§fSilver§7MC§f] §cJij hebt hier geen permissies voor.");
            return true;
        }

        Player target = Bukkit.getPlayerExact(args[0]);

        StringBuilder reaction = new StringBuilder();

        for (String part : args) {
            reaction.append(" ");
            reaction.append(part);
        }

        if (Files.ASK.getFileConfiguration().getString(target.getUniqueId() + ".staffmember").equals(player.getName())) {
            target.sendMessage("§f[§fSilver§7MC§f] §aEr heeft een stafflid ( §2" + player.getName() + " §a) op je vraag gereageerd:");
            target.sendMessage("§f[§fSilver§7MC§f] §2Reactie van het stafflid: §7" + reaction.toString().replace(args[0], ""));
            target.sendMessage("§f[§fSilver§7MC§f] §aJe kan hierop reageren doormiddel van het command §2/ask§a.");
            player.sendMessage("§f[§fSilver§7MC§f] §aSuccesvol gereageert op §2" + target.getName());

            List<String> history = Files.ASK.getFileConfiguration().getStringList(target.getUniqueId() + ".history");
            history.add("§4" + player.getName() + ": §c" + reaction.toString().replace(args[0], ""));
            Files.ASK.getFileConfiguration().set(target.getUniqueId() + ".history", history);
            Files.ASK.save();

        } else {

            player.sendMessage("§f[§fSilver§7MC§f] §cJij hebt deze vraag niet geclaimt. Claim hem in §4/askmenu§c.");

        }

        return true;
    }
}
