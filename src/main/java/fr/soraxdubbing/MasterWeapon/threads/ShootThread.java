package fr.soraxdubbing.MasterWeapon.threads;

import net.minecraft.server.v1_16_R3.DamageSource;
import net.minecraft.server.v1_16_R3.EntityLiving;
import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

import java.util.List;

import static fr.soraxdubbing.MasterWeapon.ParticleAnimation.*;

public class ShootThread extends BukkitRunnable {

    private List<Player> players;

    public ShootThread(List<Player> players){
        this.players = players;
    }

    @Override
    public void run() {
        for (Player player : players) {

            player.playSound(player.getLocation(), "fuze_weapon:effect.fish", 100, 1);

            BlockFace blockFace = getBlockFace(player);
            Block targetBlock = getTargetBlock(player.getEyeLocation(),15);
            Location origin = player.getLocation().add(-Math.cos(Math.toRadians(player.getLocation().getYaw())) , 1, -Math.sin(Math.toRadians(player.getLocation().getYaw())));

            Location target = targetBlock.getLocation();

            if(blockFace != null){
                target = getBlockFaceLocation(blockFace,targetBlock);
                target.setYaw(target.getYaw()+135);
            }
            target.setYaw(target.getYaw()+135);
            Vector trail = TwoLocationVector(origin,target);

            EntityLiving ent = null;
            for (Entity nearbyEntity : player.getNearbyEntities(15, 15, 15)) {
                if(getLookingAt(player,nearbyEntity)){
                    ent = (EntityLiving) nearbyEntity;
                }
            }

            particleTrails(origin,trail,player.getWorld(),Color.GRAY);

            if(targetBlock.getType() != Material.AIR && targetBlock.getType() != Material.WATER && ent == null){
                targetBlock.breakNaturally();
            }
            else if(ent != null){
                ent.damageEntity(DamageSource.BURN,15);
            }


            /*player.playSound(player.getLocation(), "fuze_weapon:effect.mitrailette", 100, 1);
            Location origin = player.getLocation().add(-Math.cos(Math.toRadians(player.getLocation().getYaw())) , 1, -Math.sin(Math.toRadians(player.getLocation().getYaw())));

            Block targetBlock = getTargetBlock(origin,15);

            Entity ent = getTargetEntity(player,15);

            Location target = targetBlock.getLocation();

            if(targetBlock.getType() != Material.AIR && origin.distance(target) <=15 && ent == null){
                BlockFace blockFace = getBlockFace(player);
                if(blockFace != null){
                    target = getBlockFaceLocation(blockFace,targetBlock);
                }
                if(targetBlock.getType() == Material.WATER){
                    player.getWorld().spawnParticle(Particle.WATER_SPLASH, target, 30);
                }
                else{
                    targetBlock.breakNaturally();
                }
                particleTrails(origin,target,player.getWorld(), Color.GRAY);
            }
            else {
                Vector toAdd = new Vector();
                Vector originLocVector = origin.getDirection().normalize();
                SpawnParticleLoop(15,player.getWorld(),originLocVector,origin, Color.GRAY);
            }*/
        }
    }
}
