package com.rune.staff.utils;

import com.rune.staff.Main;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public enum Files {
    ASK("data", "ask"),
    REPORT("data", "report"),
    CLANS("data", "clans"),
    PLAYERS("data", "players");

    private final String folders;

    private final String fileName;

    private final FileConfiguration fileConfiguration;

    private final File file;

    private static final List<Files> values;

    public String getFolders() {
        return this.folders;
    }

    public String getFileName() {
        return this.fileName;
    }

    public FileConfiguration getFileConfiguration() {
        return this.fileConfiguration;
    }

    public File getFile() {
        return this.file;
    }

    static {
        values = Collections.unmodifiableList(Arrays.asList(values()));
    }

    Files(String folder, String fileName) {
        this.folders = folder;
        this.fileName = fileName;
        JavaPlugin plugin = JavaPlugin.getPlugin(Main.class);
        this.file = new File(plugin.getDataFolder() + getPath() + ".yml");
        this.fileConfiguration = ConfigGetter.getConfig(getPath());
        this.fileConfiguration.getConfigurationSection("worlds");
    }

    private String getPath() {
        String path = "";
        if (this.folders.length() > 0)
            path = "/" + this.folders;
        return path + "/" + this.fileName;
    }

    public static Files getFileByName(String fileName) {
        for (Files value : values) {
            if (value.getFileName().equalsIgnoreCase(fileName))
                return value;
        }
        return null;
    }

    public void save() {
        try {
            System.out.println("Saving file... " + this.file.getName());
            this.fileConfiguration.save(this.file);
        } catch (IOException e) {
            try {
                System.out.println("Could not save " + this.file.getCanonicalPath());
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }

    public static void saveAll() {
        for (Files value : values)
            value.save();
    }
}


/* Location:              C:\Users\rune\Downloads\WIDM2-main\WIDM2-main\WIDM_Plugin.jar!\carrotnetwork\wid\\util\data\Files.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */