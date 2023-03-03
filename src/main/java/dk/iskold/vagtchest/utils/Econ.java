package dk.iskold.vagtchest.utils;

import dk.iskold.vagtchest.VagtChest;
import org.bukkit.OfflinePlayer;

public class Econ {

    public static boolean addMoney(OfflinePlayer player, double amount) {

        return VagtChest.econ.depositPlayer(player, amount).transactionSuccess();
    }

    public static boolean removeMoney(OfflinePlayer player, double amount) {
        return VagtChest.econ.withdrawPlayer(player, amount).transactionSuccess();
    }

    private boolean addMoneyToPlayer(String playerName, double amount) {
        return VagtChest.econ.depositPlayer(playerName, amount).transactionSuccess();
    }

    private static boolean createPlayerAccount(String playerName) {
        return VagtChest.econ.createPlayerAccount(playerName);
    }

    public static double getbalance(String playerName) {
        return VagtChest.econ.getBalance(playerName);
    }


}