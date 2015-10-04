package io.github.xxyy.quietcord;

import com.google.common.io.ByteStreams;
import net.md_5.bungee.api.plugin.Plugin;
import net.md_5.bungee.config.Configuration;
import net.md_5.bungee.config.ConfigurationProvider;
import net.md_5.bungee.config.YamlConfiguration;

import io.github.xxyy.quietcord.filter.IHConnectedFilter;
import io.github.xxyy.quietcord.filter.IHResetByPeerFilter;
import io.github.xxyy.quietcord.filter.InjectableFilter;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.LinkedList;
import java.util.List;

/**
 * Plugin class for the QuietCord BungeeCord plugin, used for interfacing with
 * the BungeeCord API and also application entry point.
 *
 * @author <a href="http://xxyy.github.io/">xxyy</a>
 * @since 2015-10-02
 */
public class QuietCordPlugin extends Plugin {
    public static boolean DEBUG_MODE = false;
    private List<InjectableFilter> filters = new LinkedList<>();
    private Configuration config;

    @Override
    public void onEnable() {
        try {
            loadConfig();
        } catch (IOException e) {
            e.printStackTrace();
            getLogger().warning("Failed to load configuration - using defaults!");
        }

        if (config.getBoolean("disable", false)) {
            getLogger().info("Plugin is disabled in configuration file.");
            onDisable();
            return;
        }

        if (DEBUG_MODE = config.getBoolean("debug", false)) { //set and get, hehe
            getLogger().info("QuietCord is running in debug mode! This is not recommended for Production environments.");
        }

        filters.clear();
        filters.add(new IHConnectedFilter(this));
        filters.add(new IHResetByPeerFilter(this));

        for (InjectableFilter filter : filters) {
            filter.inject();
        }
    }

    @Override
    public void onDisable() {
        for (InjectableFilter filter : filters) {
            filter.reset(); //Prints a message on its own on error
        }
    }

    private void loadConfig() throws IOException {
        if (!getDataFolder().exists()) {
            if (!getDataFolder().mkdir()) {
                throw new IOException("Unable to create data folder: " + getDataFolder().getAbsolutePath());
            }
        }

        File configFile = new File(getDataFolder(), "config.yml");

        if (!configFile.exists()) {
            if (!configFile.createNewFile()) {
                throw new IOException("Unable to create config file: " + getDataFolder().getAbsolutePath());
            }

            try (InputStream in = getResourceAsStream("config.default.yml");
                 OutputStream out = new FileOutputStream(configFile)) {
                ByteStreams.copy(in, out);
            }
        }

        config = ConfigurationProvider.getProvider(YamlConfiguration.class)
                .load(configFile);
    }

    public Configuration getConfig() {
        return config;
    }
}
