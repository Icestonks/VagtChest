package dk.iskold.vagtchest.events;

import dk.iskold.vagtchest.VagtChest;
import dk.iskold.vagtchest.tasks.GUIS;
import dk.iskold.vagtchest.utils.Chat;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;

public class InteractListener implements Listener {

    @EventHandler
    public void onInteractListener(PlayerInteractEvent e) {
        Player player = e.getPlayer();

        if(e.getAction() == Action.RIGHT_CLICK_BLOCK) {
            Material block = Material.valueOf(VagtChest.configYML.getString("vagtchest.block"));
            if(e.getClickedBlock().getType() != block) { return; }
            if(!VagtChest.chestLocations.getLocations().contains(e.getClickedBlock().getLocation())) { return; }

            String perm = VagtChest.configYML.getString("vagtchest.perm");
            String prefix = VagtChest.configYML.getString("vagtchest.prefix");
            if (!player.hasPermission(perm)) {
                e.setCancelled(true);
                player.sendMessage(Chat.colored(prefix + "&cDette har du ikke tilladelse til!"));
                return;
            }

            e.setCancelled(true);
            GUIS.openVagtChest(player);
        } else if (e.getAction() == Action.LEFT_CLICK_BLOCK) {
            Material block = Material.valueOf(VagtChest.configYML.getString("vagtchest.block"));
            if(e.getClickedBlock().getType() != block) { return; }
            if(!VagtChest.chestLocations.getLocations().contains(e.getClickedBlock().getLocation())) { return; }

            String staff_perm = VagtChest.configYML.getString("vagtchest.staff-perm");
            if(player.hasPermission(staff_perm)) {
                GUIS.openEditItem(player, 0);
            }
        }
    }

}
