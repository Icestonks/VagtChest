package dk.iskold.vagtchest.events;

import dk.iskold.vagtchest.VagtChest;
import dk.iskold.vagtchest.utils.Chat;
import dk.iskold.vagtchest.utils.ChestItems;
import dk.iskold.vagtchest.utils.Econ;
import org.bukkit.Bukkit;
import org.bukkit.block.Chest;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class InventoryClose implements Listener {

    @EventHandler
    public void onInventoryclose(InventoryCloseEvent e) {
        String gui_name = VagtChest.configYML.getString("vagtchest.GUI.name");
        if(Objects.equals(e.getInventory().getTitle(), Chat.colored(gui_name))) {

            List<ItemStack> returnItems = new ArrayList<ItemStack>();
            List<String> blacklisted_items = VagtChest.configYML.getStringList("vagtchest.blacklisted-items");
            int sell_amount = 0;

            for(ItemStack items : e.getInventory().getContents()) {
                if(items != null) {

                    if(!blacklisted_items.contains(Chat.revertColor(items.getItemMeta().getDisplayName()))) {
                        if (ChestItems.isItemInChestItems(items)) {
                            int sell_price = ChestItems.getPrice(items);
                            sell_price = sell_price * items.getAmount();
                            sell_amount += sell_price;

                        } else {
                            returnItems.add(items);
                        }
                    } else {
                        returnItems.add(items);
                    }

                }
            }

            Player player = (Player) e.getPlayer();
            List<String> sell_message = VagtChest.configYML.getStringList("vagtchest.messages.sell");
            for(String line : sell_message) {
                player.sendMessage(Chat.colored(line.replace("%amount%", String.valueOf(sell_amount))));
            }
            Econ.addMoney(player, sell_amount);

            if (returnItems.size() != 0) {
                for(ItemStack item : returnItems) {
                    if(player.getInventory().firstEmpty() != -1) {
                        player.getInventory().addItem(item);
                    } else {
                        player.getWorld().dropItem(player.getLocation(), item);
                    }
                }
            }

        }
    }
}
