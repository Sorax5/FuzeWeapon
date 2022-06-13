package fr.soraxdubbing.fuzeweapon.event;

import fr.soraxdubbing.fuzeweapon.FuzeWeapon;
import fr.soraxdubbing.fuzeweapon.WeaponEnum;
import fr.soraxdubbing.fuzeweapon.entity.CustomZombie;
import fr.soraxdubbing.fuzeweapon.threads.FishCooldown;
import fr.soraxdubbing.fuzeweapon.threads.LaserEffect;
import fr.soraxdubbing.fuzeweapon.threads.ShootThread;
import net.minecraft.server.v1_16_R3.EntityCod;
import net.minecraft.server.v1_16_R3.EntityFish;
import net.minecraft.server.v1_16_R3.EntityTypes;
import net.minecraft.server.v1_16_R3.World;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.craftbukkit.v1_16_R3.CraftWorld;
import org.bukkit.entity.Cod;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerItemHeldEvent;
import org.bukkit.inventory.ItemStack;

import java.util.*;

public class WeaponInteract implements Listener {

    private final HashMap<WeaponEnum,ItemStack> weaponList;

    private FuzeWeapon plugin;

    private ShootThread shootThread;

    private List<Player> shooter;

    public WeaponInteract(FuzeWeapon plugin){
        this.plugin = plugin;
        this.weaponList = plugin.getWeaponList();
        shooter = new ArrayList<>();
        this.shootThread = new ShootThread(shooter);
        shootThread.runTaskTimer(plugin, 0, 5);
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
                        MitrailletteEffect(event.getPlayer());
                        break;
                    case INVOCATEUR:
                        InvocateurEffect(event.getPlayer());
                        break;
                    case POSEIDON:
                        PoseidonEffect();
                        break;
                    case LASER:
                        LaserEffect(event.getPlayer());
                        break;
                    case FISH_SHOOTER:
                        FishShooterEffect(event.getPlayer());
                        break;
                }
            }
        }
    }

    @EventHandler
    public void OnMainHandChanged(PlayerItemHeldEvent event){
        if(shooter.contains(event.getPlayer())){
            shootThread.cancel();
            shooter.remove(event.getPlayer());
            shootThread = new ShootThread(shooter);
            shootThread.runTaskTimer(plugin, 0, 5);
        }
    }

    private void MitrailletteEffect(Player player){
        if(shooter.contains(player)){
            shootThread.cancel();
            shooter.remove(player);
            shootThread = new ShootThread(shooter);
            shootThread.runTaskTimer(plugin, 0, 5);
        }
        else {
            shootThread.cancel();
            shooter.add(player);
            shootThread = new ShootThread(shooter);
            shootThread.runTaskTimer(plugin, 0, 5);
        }
    }

    private void InvocateurEffect(Player player){
        Location pLoc = player.getLocation();
        World nmsWorld = (((CraftWorld) pLoc.getWorld()).getHandle());
        CustomZombie zombie = new CustomZombie(nmsWorld);
        zombie.setPosition(pLoc.getX(),pLoc.getY(),pLoc.getZ());
    }

    private void PoseidonEffect(){

    }

    private void LaserEffect(Player player){
        LaserEffect laserEffect = new LaserEffect(player);
        laserEffect.run();
    }

    private void FishShooterEffect(Player player){
        Location pLoc = player.getLocation();
        Random random = new Random();
        Entity fish = null;
        switch (random.nextInt(4)){
            case 0:
                fish = player.getWorld().spawnEntity(pLoc, EntityType.SALMON);
                break;
            case 1:
                fish = player.getWorld().spawnEntity(pLoc, EntityType.COD);
                break;
            case 2:
                fish = player.getWorld().spawnEntity(pLoc, EntityType.PUFFERFISH);
                break;
            case 3:
                fish = player.getWorld().spawnEntity(pLoc, EntityType.TROPICAL_FISH);
                break;
        }
        fish.setVelocity(player.getEyeLocation().getDirection().multiply(1));
        FishCooldown fishCooldown = new FishCooldown(fish);
        fishCooldown.runTaskTimer(plugin,0,15);
    }
}
