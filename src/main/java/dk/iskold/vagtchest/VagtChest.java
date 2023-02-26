package dk.iskold.vagtchest;

import dk.iskold.vagtchest.commands.VagtChestCommand;
import dk.iskold.vagtchest.events.InteractListener;
import dk.iskold.vagtchest.utils.ChestItems;
import dk.iskold.vagtchest.utils.ChestLocations;
import dk.iskold.vagtchest.utils.Config;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;


public final class VagtChest extends JavaPlugin {

    public static Config config, data, items, license;
    public static FileConfiguration configYML, itemsYML, dataYML, licenseYML;
    public static VagtChest instance;
    public static ChestLocations chestLocations;
    public static ChestItems chestItems;
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

    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }

    public static VagtChest getInstance() {
        return instance;
    }
}
