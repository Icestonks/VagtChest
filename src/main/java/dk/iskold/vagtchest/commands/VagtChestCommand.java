package dk.iskold.vagtchest.commands;

import dk.iskold.vagtchest.VagtChest;
import dk.iskold.vagtchest.tasks.GUIS;
import dk.iskold.vagtchest.utils.Chat;
import dk.iskold.vagtchest.utils.ChestItems;
import dk.iskold.vagtchest.utils.ChestLocations;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.Scanner;
import java.util.Set;

public class VagtChestCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        String staff_perm = VagtChest.configYML.getString("vagtchest.staff-perm");
        String prefix = VagtChest.configYML.getString("vagtchest.prefix");
        Player player = (Player) sender;


        if(!player.hasPermission(staff_perm)) {
            player.sendMessage(Chat.colored(prefix + Chat.colored("&cDette har du ikke adgang til!")));
            return true;
        }

        if(args.length == 0) {
            player.sendMessage(Chat.colored(prefix));
            player.sendMessage(Chat.colored("&8┃ &f/vchest add/remove/reload"));
            player.sendMessage(Chat.colored("&8┃ &f/vchest items add/edit/remove"));
            return true;
        }

        if(args[0].equalsIgnoreCase("reload")) {
            boolean success;
            try {
                VagtChest.config.reloadConfig();
                VagtChest.configYML = VagtChest.config.getConfig();
                success = true;

            } catch(Exception e){
                e.printStackTrace();
                success = false;
            }
            if(success) sender.sendMessage(Chat.colored(prefix + "&aReload successfully completed"));
            if(!success) sender.sendMessage(Chat.colored(prefix + "&cAn error occurred. Please check the console."));
            return true;
        }

        if (args[0].equalsIgnoreCase("add")) {
            Block target = player.getTargetBlock((Set<Material>)null, 5);
            Location chestLocation = target.getLocation();
            if (VagtChest.chestLocations.getLocations().contains(chestLocation)) {
                player.sendMessage(Chat.colored(prefix + "&cDenne lokation er allerede i brug!"));
                return true;
            }

            Material chest_block = Material.valueOf(VagtChest.configYML.getString("vagtchest.block"));
            if(!(target.getType() == chest_block)) {
                player.sendMessage(Chat.colored(prefix + "&7Du skal kigge på en &c" + chest_block));
                return true;
            }

            ChestLocations.addLocation(chestLocation);
            player.sendMessage(Chat.colored(Chat.colored(prefix + "&7Du placerede en vagt kiste ved&8: &7x: &a" + chestLocation.getX() + " &7y: &a" + chestLocation.getY() + " &7z: &a" + chestLocation.getZ() + " &8(&a" + chestLocation.getWorld().getName() + "&8)")));
            return true;
        }

        if (args[0].equalsIgnoreCase("remove")) {
            Block target = player.getTargetBlock((Set<Material>)null, 5);
            Location chestLocation = target.getLocation();
            if (!VagtChest.chestLocations.getLocations().contains(chestLocation)) {
                player.sendMessage(Chat.colored(prefix + "&cDette er er ikke en vagt kiste!"));
                return true;
            }

            ChestLocations.removeLocation(chestLocation);
            player.sendMessage(Chat.colored(Chat.colored(prefix + "&7Du fjernede en vagt kiste ved&8: &7x: &c" + chestLocation.getX() + " &7y: &c" + chestLocation.getY() + " &7z: &c" + chestLocation.getZ() + " &8(&c" + chestLocation.getWorld().getName() + "&8)")));
            return true;
        }

        if (args[0].equalsIgnoreCase("items")) {
            if(args.length == 1) {
                player.sendMessage(Chat.colored(prefix));
                player.sendMessage(Chat.colored("&8┃ &f/vchest items add/edit/remove"));
                return true;
            }

            if (args[1].equalsIgnoreCase("add")) {
                if(player.getItemInHand().getType() == Material.AIR) {
                    player.sendMessage(Chat.colored(prefix + "&cDu skal holde et item i hånden!"));
                    return true;
                }

                if(args.length < 3 || !isInteger(args[2], 10)) {
                    player.sendMessage(Chat.colored(prefix));
                    player.sendMessage(Chat.colored("&8┃ &f/vchest items add <price>"));
                    return true;
                }

                if (ChestItems.isItemInChestItems(player.getItemInHand())) {
                    player.sendMessage(Chat.colored(prefix + "&cDette item findes allerede!"));
                    return true;
                }

                ChestItems.addItem(player.getItemInHand(), Integer.valueOf(args[2]));
                player.sendMessage(Chat.colored(prefix + "&7Du addede &8(&a" + player.getItemInHand().getType() + "&8) &7til listen af items!"));
                return true;
            }

            if (args[1].equalsIgnoreCase("remove")) {
                if(player.getItemInHand().getType() == Material.AIR) {
                    player.sendMessage(Chat.colored(prefix + "&cDu skal holde et item i hånden!"));
                    return true;
                }

                if (!ChestItems.isItemInChestItems(player.getItemInHand())) {
                    player.sendMessage(Chat.colored(prefix + "&cDette item findes ikke i listen!"));
                    return true;
                }

                ChestItems.removeItem(player.getItemInHand());
                player.sendMessage(Chat.colored(prefix + "&7Du fjernede &8(&c" + player.getItemInHand().getType() + "&8) &7til listen af items!"));
                return true;
            }

            if (args[1].equalsIgnoreCase("edit")) {
                GUIS.openEditItem(player, 0);
                return true;
            }
        }

        player.sendMessage(Chat.colored(prefix));
        player.sendMessage(Chat.colored("&8┃ &f/vchest add/remove/reload"));
        player.sendMessage(Chat.colored("&8┃ &f/vchest items add/edit/remove"));
        return true;
    }

    public static boolean isInteger(String s, int radix) {
        Scanner sc = new Scanner(s.trim());
        if(!sc.hasNextInt(radix)) return false;
        sc.nextInt(radix);
        return !sc.hasNext();
    }
}
