package dk.iskold.vagtchest.utils;

import dk.iskold.vagtchest.VagtChest;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ChestLocations {

    public static ArrayList<Location> chestLocations = new ArrayList<>();

    public ChestLocations() {}

    public static void reloadLocations(){
        chestLocations.clear();
        try {
            List<Map<?, ?>> locationMap = VagtChest.dataYML.getMapList("locations");
            for (Map<?, ?> locations : locationMap) {
                String worldName = (String) locations.get("world");
                World world = Bukkit.getWorld(worldName);
                int x = (int) locations.get("x");
                int y = (int) locations.get("y");
                int z = (int) locations.get("z");
                Location location = new Location(world, x, y, z);
                chestLocations.add(location);
            }
        } catch(NullPointerException exception){
            String prefix = VagtChest.configYML.getString("vagtchest.prefix");
            Bukkit.getConsoleSender().sendMessage(prefix + "Ingen lokationer fundet!");
        }
    }

    public static void addLocation(Location loc) {
        List<Map<?, ?>> locations = VagtChest.dataYML.getMapList("locations");
        Map<String, Object> newLocationMap = new HashMap<>();
        newLocationMap.put("world", loc.getWorld().getName());
        newLocationMap.put("x", loc.getBlockX());
        newLocationMap.put("y", loc.getBlockY());
        newLocationMap.put("z", loc.getBlockZ());
        chestLocations.add(new Location(loc.getWorld(), loc.getBlockX(), loc.getBlockY(), loc.getBlockZ()));
        locations.add(newLocationMap);
        VagtChest.dataYML.set("locations", locations);
        VagtChest.data.saveConfig();
    }

    public static void removeLocation(Location loc) {

        List<Map<?, ?>> locations = VagtChest.dataYML.getMapList("locations");

        int indexToRemove = -1;
        for (int i = 0; i < locations.size(); i++) {
            Map<?, ?> locationMap = locations.get(i);
            String worldName = (String) locationMap.get("world");
            int x = (int) locationMap.get("x");
            int y = (int) locationMap.get("y");
            int z = (int) locationMap.get("z");
            Location location = new Location(Bukkit.getWorld(worldName), x, y, z);
            if (location.equals(loc)) {
                chestLocations.remove(location);
                indexToRemove = i;
                break;
            }
        }

        if (indexToRemove != -1) {
            locations.remove(indexToRemove);
            VagtChest.dataYML.set("locations", locations);
            VagtChest.data.saveConfig();
        }
    }


    public ArrayList<Location> getLocations() {
        return chestLocations;
    }
}
