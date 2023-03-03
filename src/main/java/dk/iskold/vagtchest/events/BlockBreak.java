package dk.iskold.vagtchest.events;

import dk.iskold.vagtchest.VagtChest;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;

public class BlockBreak implements Listener {

    @EventHandler
    public void onBlockBreak(BlockBreakEvent e) {

        Material block = Material.valueOf(VagtChest.configYML.getString("vagtchest.block"));
        if(e.getBlock().getType() == block) {
            if(VagtChest.chestLocations.getLocations().contains(e.getBlock().getLocation())) {
                e.setCancelled(true);
            }
        }
    }
}
