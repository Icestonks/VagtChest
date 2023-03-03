package dk.iskold.vagtchest.events;

import dk.iskold.vagtchest.VagtChest;
import dk.iskold.vagtchest.tasks.GUIS;
import dk.iskold.vagtchest.utils.Chat;
import dk.iskold.vagtchest.utils.ChestItems;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.block.Chest;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import javax.swing.*;
import java.util.List;
import java.util.Objects;

public class InventoryClick implements Listener {

    @EventHandler
    public void onInventoryClick(InventoryClickEvent e) {

        if(e.getClickedInventory() == null) return;
        String prefix = VagtChest.configYML.getString("vagtchest.prefix");
        if(Objects.equals(e.getClickedInventory().getTitle(), Chat.colored(prefix + "&8- &fEdit Items"))) {
            e.setCancelled(true);
            Player player = (Player) e.getWhoClicked();

            if(e.getSlot() >= 45 && e.getSlot() <= 53) {
                if(e.getSlot() == 50 || e.getSlot() == 48) {
                    ItemStack item = e.getCurrentItem();
                    ItemMeta meta = item.getItemMeta();
                    List<String> lore = meta.getLore();
                    int newPage = Integer.parseInt(lore.get(0).replace(Chat.colored("&7"), ""));

                    if(e.getSlot() == 50 && e.getCurrentItem().getType() != Material.AIR) {
                        GUIS.openEditItem(player, newPage);
                    }
                    if(e.getSlot() == 48 && e.getCurrentItem().getType() != Material.AIR) {
                        GUIS.openEditItem(player, newPage);
                    }
                }
            } else {
                if(e.getClick() != ClickType.RIGHT) return;
                if(e.getCurrentItem().getType() == Material.AIR || e.getCurrentItem() == null) return;

                if(ChestItems.isItemInChestItems(e.getCurrentItem())) {
                    ChestItems.removeItem(e.getCurrentItem());
                    player.sendMessage(Chat.colored(Chat.colored(prefix + "&7Du fjernede &8(&c" + e.getCurrentItem().getType() + "&8) &7til listen af items!")));
                    e.getClickedInventory().clear(e.getSlot());
                } else {
                    player.sendMessage(Chat.colored(prefix + "&cDette item er allerede blevet fjernet!"));
                    e.getClickedInventory().clear(e.getSlot());
                }
            }
        }

    }
}
