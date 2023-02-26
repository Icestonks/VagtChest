package dk.iskold.vagtchest.utils;

import dk.iskold.vagtchest.VagtChest;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;

import java.util.*;

public class ChestItems {
    static HashMap<ItemStack, Integer> chestItems = new HashMap<ItemStack, Integer>();

    public ChestItems() {}

    static Material[] materials = {
            Material.CHAINMAIL_HELMET,
            Material.CHAINMAIL_CHESTPLATE,
            Material.CHAINMAIL_LEGGINGS,
            Material.CHAINMAIL_BOOTS,

            Material.IRON_HELMET,
            Material.IRON_CHESTPLATE,
            Material.IRON_LEGGINGS,
            Material.IRON_BOOTS,

            Material.GOLD_HELMET,
            Material.GOLD_CHESTPLATE,
            Material.GOLD_LEGGINGS,
            Material.GOLD_BOOTS,

            Material.DIAMOND_HELMET,
            Material.DIAMOND_CHESTPLATE,
            Material.DIAMOND_LEGGINGS,
            Material.DIAMOND_BOOTS,

            Material.LEATHER_HELMET,
            Material.LEATHER_CHESTPLATE,
            Material.LEATHER_LEGGINGS,
            Material.LEATHER_BOOTS,

            Material.DIAMOND_SWORD,
            Material.DIAMOND_SPADE,
            Material.DIAMOND_PICKAXE,
            Material.DIAMOND_AXE,
            Material.DIAMOND_HOE,

            Material.GOLD_SWORD,
            Material.GOLD_SPADE,
            Material.GOLD_PICKAXE,
            Material.GOLD_AXE,
            Material.GOLD_HOE,

            Material.IRON_SWORD,
            Material.IRON_SPADE,
            Material.IRON_PICKAXE,
            Material.IRON_AXE,
            Material.IRON_HOE,

            Material.STONE_SWORD,
            Material.STONE_SPADE,
            Material.STONE_PICKAXE,
            Material.STONE_AXE,
            Material.STONE_HOE,

            Material.WOOD_SWORD,
            Material.WOOD_SPADE,
            Material.WOOD_PICKAXE,
            Material.WOOD_AXE,
            Material.WOOD_HOE,

            Material.FISHING_ROD,
            Material.CARROT_STICK,
            Material.BOW,

    };
    static List<Material> materialList = Arrays.asList(materials);

    public static void reloadItems(){
        chestItems.clear();
        try {
            List<Map<?, ?>> itemsMap = VagtChest.itemsYML.getMapList("items");
            for (Map<?, ?> items : itemsMap) {

                int data = ((Integer) items.get("data")).shortValue();
                int price = ((Integer) items.get("price"));
                Material material = Material.valueOf((String) items.get("material"));

                ItemStack item = new ItemStack(material, data);

                if (items.containsKey("enchantments")) {
                    Map<?, ?> enchantmentsMap = (Map<?, ?>) items.get("enchantments");

                    for (Map.Entry<?, ?> entry : enchantmentsMap.entrySet()) {
                        String enchantName = entry.getKey().toString();
                        int enchantLevel = (int) entry.getValue();
                        Enchantment enchant = Enchantment.getByName(enchantName);
                        if (enchant != null) {
                            item.addUnsafeEnchantment(enchant, enchantLevel);
                        }
                    }
                }

                chestItems.put(item, price);
            }
        } catch(NullPointerException exception){
            String prefix = VagtChest.configYML.getString("vagtchest.prefix");
            Bukkit.getConsoleSender().sendMessage(prefix + "Ingen lokationer fundet!");
        }
    }

    public static void addItem(ItemStack item, Integer price) {
        List<Map<?, ?>> items = VagtChest.itemsYML.getMapList("items");

        Material material = item.getType();
        int data = 0;

        if (!materialList.contains(material)) {
            data = item.getDurability();
        }

        Map<String, Object> itemMap = new HashMap<>();
        itemMap.put("material", item.getType().name());
        itemMap.put("price", price);

        ItemStack new_item;
        if(data != 0) {
            new_item = new ItemStack(material, 1, (short) data);
            itemMap.put("data", data);
        } else {
            new_item = new ItemStack(material, 1);
        }

        if (item.getEnchantments().size() > 0) {
            Map<String, Object> enchantmentsMap = new HashMap<>();
            for (Map.Entry<Enchantment, Integer> entry : item.getEnchantments().entrySet()) {
                enchantmentsMap.put(entry.getKey().getName(), entry.getValue());

                String enchantName = entry.getKey().toString();
                int enchantLevel = (int) entry.getValue();
                Enchantment enchant = Enchantment.getByName(enchantName);
                if (enchant != null) {
                    new_item.addUnsafeEnchantment(enchant, enchantLevel);
                }
            }
            itemMap.put("enchantments", enchantmentsMap);
        }

        chestItems.put(new_item, price);
        items.add(itemMap);
        System.out.println(items);
        VagtChest.itemsYML.set("items", items);
        VagtChest.items.saveConfig();
    }

    public static void removeLocation(Location loc) {

        List<Map<?, ?>> locations = VagtChest.itemsYML.getMapList("locations");

        int indexToRemove = -1;
        for (int i = 0; i < locations.size(); i++) {
            Map<?, ?> locationMap = locations.get(i);
            String worldName = (String) locationMap.get("world");
            int x = (int) locationMap.get("x");
            int y = (int) locationMap.get("y");
            int z = (int) locationMap.get("z");
            Location location = new Location(Bukkit.getWorld(worldName), x, y, z);
            if (location.equals(loc)) {
                chestItems.remove(location);
                indexToRemove = i;
                break;
            }
        }

        if (indexToRemove != -1) {
            locations.remove(indexToRemove);
            VagtChest.itemsYML.set("locations", locations);
            VagtChest.data.saveConfig();
        }
    }


    public HashMap<ItemStack, Integer> getItems() {
        return chestItems;
    }
}
