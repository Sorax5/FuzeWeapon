package fr.soraxdubbing.MasterWeapon;

import fr.soraxdubbing.MasterWeapon.commands.WeaponGive;
import fr.soraxdubbing.MasterWeapon.commands.WeaponGiveCompletion;
import fr.soraxdubbing.MasterWeapon.event.WeaponInteract;
import org.bukkit.Bukkit;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.HashMap;

public final class MasterWeapon extends JavaPlugin {

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
