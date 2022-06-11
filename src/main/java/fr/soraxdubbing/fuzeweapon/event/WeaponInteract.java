package fr.soraxdubbing.fuzeweapon.event;

import fr.soraxdubbing.fuzeweapon.FuzeWeapon;
import fr.soraxdubbing.fuzeweapon.WeaponEnum;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class WeaponInteract implements Listener {

    private HashMap<WeaponEnum,ItemStack> weaponList;

    public WeaponInteract(FuzeWeapon plugin){
        this.weaponList = plugin.getWeaponList();
    }

    @EventHandler
    public void onRightClick(PlayerInteractEvent event){
        ItemStack item = event.getPlayer().getInventory().getItemInMainHand();
        if(item.getType() != Material.AIR && (event.getAction().equals(Action.RIGHT_CLICK_AIR) ||event.getAction().equals(Action.RIGHT_CLICK_BLOCK))){
            if(this.weaponList.containsValue(item)){
                WeaponEnum weaponType = null;
                for (Map.Entry<WeaponEnum,ItemStack> entry : weaponList.entrySet()){
                    if(entry.getValue().equals(item)){
                        weaponType = entry.getKey();
                    }
                }
                switch (Objects.requireNonNull(weaponType)){
                    case MITRAILLETTE:
                        MitrailletteEffect();
                        break;
                    case INVOCATEUR:
                        InvocateurEffect();
                        break;
                    case POSEIDON:
                        PoseidonEffect();
                        break;
                    case LASER:
                        LaserEffect();
                        break;
                    case FISH_SHOOTER:
                        FishShooterEffect();
                        break;
                }
            }
        }
    }

    private void MitrailletteEffect(){

    }

    private void InvocateurEffect(){

    }

    private void PoseidonEffect(){

    }

    private void LaserEffect(){

    }

    private void FishShooterEffect(){

    }
}
