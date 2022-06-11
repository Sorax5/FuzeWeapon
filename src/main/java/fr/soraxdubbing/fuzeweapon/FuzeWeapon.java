package fr.soraxdubbing.fuzeweapon;

import fr.soraxdubbing.fuzeweapon.commands.WeaponGive;
import fr.soraxdubbing.fuzeweapon.commands.WeaponGiveCompletion;
import fr.soraxdubbing.fuzeweapon.event.WeaponInteract;
import org.bukkit.Bukkit;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public final class FuzeWeapon extends JavaPlugin {

    private HashMap<WeaponEnum,ItemStack> weaponList;

    public HashMap<WeaponEnum,ItemStack> getWeaponList() {
        return weaponList;
    }

    @Override
    public void onEnable() {
        this.weaponList = new HashMap<>();
        for(WeaponEnum weapon : WeaponEnum.values()){
            weaponList.put(weapon,WeaponManager.CreateWeapon(weapon));
        }
        getCommand("weapon").setExecutor(new WeaponGive(this));
        getCommand("weapon").setTabCompleter(new WeaponGiveCompletion());
        Bukkit.getServer().getPluginManager().registerEvents(new WeaponInteract(this),this);
    }

    @Override
    public void onDisable() {
    }
}
