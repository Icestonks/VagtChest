package dk.iskold.vagtchest;

import dk.iskold.vagtchest.commands.VagtChestCommand;
import dk.iskold.vagtchest.events.BlockBreak;
import dk.iskold.vagtchest.events.InteractListener;
import dk.iskold.vagtchest.events.InventoryClick;
import dk.iskold.vagtchest.events.InventoryClose;
import dk.iskold.vagtchest.utils.ChestItems;
import dk.iskold.vagtchest.utils.ChestLocations;
import dk.iskold.vagtchest.utils.Config;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;
import net.milkbowl.vault.economy.Economy;

import java.io.File;


public final class VagtChest extends JavaPlugin {

    public static Config config, data, items, license;
    public static FileConfiguration configYML, itemsYML, dataYML, licenseYML;
    public static VagtChest instance;
    public static ChestLocations chestLocations;
    public static ChestItems chestItems;
    public static Economy econ = null;
    @Override
    public void onEnable() {

        instance = this;

        //CONFIGS
        if (!(new File(getDataFolder(), "config.yml")).exists())saveResource("config.yml", false);
        if (!(new File(getDataFolder(), "data.yml")).exists())saveResource("data.yml", false);
        if (!(new File(getDataFolder(), "items.yml")).exists())saveResource("items.yml", false);

        config = new Config(this, null, "config.yml");
        configYML = config.getConfig();

        data = new Config(this, null, "data.yml");
        dataYML = data.getConfig();

        items = new Config(this, null, "items.yml");
        itemsYML = items.getConfig();

        chestLocations = new ChestLocations();
        ChestLocations.reloadLocations();

        chestItems = new ChestItems();
        ChestItems.reloadItems();

        //COMMANDS
        getCommand("vagtchest").setExecutor(new VagtChestCommand());

        //Events
        getServer().getPluginManager().registerEvents(new InteractListener(), this);
        getServer().getPluginManager().registerEvents(new InventoryClick(), this);
        getServer().getPluginManager().registerEvents(new InventoryClose(), this);
        getServer().getPluginManager().registerEvents(new BlockBreak(), this);

        //VAULT // ECON
        if (!setupEconomy() ) {
            Bukkit.getLogger().severe(String.format("[%s] - Disabled due to no Vault dependency found!", getDescription().getName()));
            Bukkit.getLogger().severe(String.format(String.valueOf(getServer().getPluginManager().getPlugin("Vault"))));
            getServer().getPluginManager().disablePlugin(this);
            return;
        }
        setupEconomy();

    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }

    public static VagtChest getInstance() {
        return instance;
    }


    private boolean setupEconomy() {
        if (getServer().getPluginManager().getPlugin("Vault") == null) {
            return false;
        }
        RegisteredServiceProvider<Economy> rsp = getServer().getServicesManager().getRegistration(Economy.class);
        if (rsp == null) {
            return false;
        }
        econ = rsp.getProvider();
        return econ != null;
    }

}
