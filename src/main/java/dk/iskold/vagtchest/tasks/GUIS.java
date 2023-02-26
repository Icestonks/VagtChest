package dk.iskold.vagtchest.tasks;

import dk.iskold.vagtchest.VagtChest;
import dk.iskold.vagtchest.utils.Chat;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

public class GUIS {
    public static void openVagtChest(Player player) {
        String gui_name = VagtChest.configYML.getString("vagtchest.GUI.name");
        Inventory inv = Bukkit.createInventory(null, 9*6, Chat.colored(gui_name));
        player.openInventory(inv);
    }
}
