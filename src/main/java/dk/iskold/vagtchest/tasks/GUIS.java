package dk.iskold.vagtchest.tasks;

import dk.iskold.vagtchest.VagtChest;
import dk.iskold.vagtchest.utils.Chat;
import dk.iskold.vagtchest.utils.ChestItems;
import dk.iskold.vagtchest.utils.GUI;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

public class GUIS {

    static String middle = "http://textures.minecraft.net/texture/e825419e429afc040c9e68b10523b917d7b8087d63e7648b10807da8b768ee";
    static String arrow_left = "http://textures.minecraft.net/texture/b76230a0ac52af11e4bc84009c6890a4029472f3947b4f465b5b5722881aacc7";
    static String arrow_right = "http://textures.minecraft.net/texture/dbf8b6277cd36266283cb5a9e6943953c783e6ff7d6a2d59d15ad0697e91d43c";
    static String help_head = "http://textures.minecraft.net/texture/797955462e4e576664499ac4a1c572f6143f19ad2d6194776198f8d136fdb2";

    public static void openVagtChest(Player player) {
        String gui_name = VagtChest.configYML.getString("vagtchest.GUI.name");
        Inventory inv = Bukkit.createInventory(null, 9*6, Chat.colored(gui_name));
        player.openInventory(inv);
    }

    public static void openEditItem(Player player, int page) {
        int size = VagtChest.chestItems.getItems().size();
        int page_start = 45*page;
        int n = 0;
        int n2 = -1;

        String prefix = VagtChest.configYML.getString("vagtchest.prefix");
        Inventory inv = Bukkit.createInventory(null, 9*6, Chat.colored(prefix + "&8- &fEdit Items"));
        LinkedHashMap<ItemStack, Integer> itemsList = VagtChest.chestItems.getItems();
        for (Map.Entry<ItemStack, Integer> items : itemsList.entrySet()) {
            n2++;
            if(n2 >= page_start) {
                ItemStack item = items.getKey().clone();
                inv.setItem(n, GUI.createItemStack(item, "&f&l" + item.getType().name(), "&7", "&8┃ &7Price: &f" + items.getValue() + "$", "&7", "&8&o(&7&oHøjre klik for at fjerne&8&o)"));
                n++;

                if(n >= 45) {
                    break;
                }
            }
        }

        inv.setItem(49, GUI.createItemStack(GUI.getSkull(middle), Chat.colored("&f&lSide"), "&fSide: &70/" + Math.round((float) size / (9 * 6))));
        if(size > page_start + 45) {
            inv.setItem(50, GUI.createItemStack(GUI.getSkull(arrow_right), Chat.colored("&f&lNæste Side"), "&7" + (page + 1)));
        }
        if(page > 0) {
            inv.setItem(48, GUI.createItemStack(GUI.getSkull(arrow_left), Chat.colored("&f&lForrige Side"), "&7" + (page - 1)));
        }
        player.openInventory(inv);
    }
}
