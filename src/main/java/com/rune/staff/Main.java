package com.rune.staff;

import com.rune.staff.command.ask.*;
import com.rune.staff.event.ask.askInventoryEvent;
import org.bukkit.plugin.java.JavaPlugin;

public final class Main extends JavaPlugin {

    @Override
    public void onEnable() {
        // Plugin startup logic
        getCommand("ask").setExecutor(new askCommand());
        getCommand("askmenu").setExecutor(new askMenuCommand());
        getCommand("askreply").setExecutor(new replyCommand());
        getCommand("askhistory").setExecutor(new askHistoryCommand());
        getServer().getPluginManager().registerEvents(new askInventoryEvent(), this);

    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
