package fr.soraxdubbing.fuzeweapon.event;

import fr.soraxdubbing.fuzeweapon.FuzeWeapon;
import fr.soraxdubbing.fuzeweapon.WeaponEnum;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;
import java.util.List;

public class WeaponInteract implements Listener {

    private HashMap<WeaponEnum,ItemStack> weaponList;

    public WeaponInteract(FuzeWeapon plugin){
        this.weaponList = plugin.getWeaponList();
    }

    @EventHandler
    public void onRightClick(PlayerInteractEvent event){
        if(event.hasItem()){

        }

    }
}
