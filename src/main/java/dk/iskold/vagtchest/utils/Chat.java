package dk.iskold.vagtchest.utils;

import net.md_5.bungee.api.ChatColor;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Chat {

    public static String colored(String s){
        return ChatColor.translateAlternateColorCodes('&', s);
    }

    public static List<String> getColored(List<String> lore) {
        List<String> coloredLore = new ArrayList<String>();
        for (String line : lore) {
            coloredLore.add(ChatColor.translateAlternateColorCodes('&', line));
        }
        return coloredLore;
    }

    public static String revertColor(String s) {
        if(s == null) return null;
        return s.replaceAll("§", "&");
    }

}