package com.rune.staff.utils;

import com.rune.staff.Main;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.IOException;

public class ConfigGetter {
    public static FileConfiguration getConfig(String path) {
        JavaPlugin plugin = JavaPlugin.getPlugin(Main.class);
        YamlConfiguration yamlConfiguration = new YamlConfiguration();
        if (!path.endsWith(".yml"))
            path = path + ".yml";
        if (!path.startsWith("/"))
            path = "/" + path;
        File f = new File(plugin.getDataFolder() + path);
        if (!f.exists())
            try {
                String[] split = path.split("/");
                if (split.length > 1) {
                    int counter = 0;
                    String endValue = "";
                    for (String s : split) {
                        counter++;
                        if (counter < split.length)
                            endValue = endValue + "/" + s;
                    }
                    File file = new File(plugin.getDataFolder() + endValue);
                    file.mkdirs();
                }
                f.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        try {
            yamlConfiguration.load(f);
        } catch (IOException|org.bukkit.configuration.InvalidConfigurationException e) {
            e.printStackTrace();
        }
        return (FileConfiguration)yamlConfiguration;
    }

    public static void saveConfig(String path) {
        JavaPlugin plugin = JavaPlugin.getPlugin(Main.class);
        FileConfiguration fileConfiguration = getConfig(path);
        if (!path.endsWith(".yml"))
            path = path + ".yml";
        if (!path.startsWith("/"))
            path = "/" + path;
        File f = new File(plugin.getDataFolder() + path);
        try {
            System.out.println("Trying to save.. " + f.getCanonicalPath());
            System.out.println("FileConfiguration path: " + fileConfiguration.getCurrentPath());
            fileConfiguration.save(f);
            System.out.println("Saved? " + f.getAbsolutePath());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void save(FileConfiguration fileConfiguration, String path) {
        JavaPlugin plugin = JavaPlugin.getPlugin(Main.class);
        try {
            if (!path.endsWith(".yml"))
                path = path + ".yml";
            if (!path.startsWith("/"))
                path = "/" + path;
            File f = new File(plugin.getDataFolder() + path);
            fileConfiguration.save(f);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
