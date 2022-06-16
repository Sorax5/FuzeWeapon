package fr.soraxdubbing.MasterWeapon.threads;

import org.bukkit.Color;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

import static fr.soraxdubbing.MasterWeapon.ParticleAnimation.*;

public class LaserEffect extends BukkitRunnable {

    private Player player;

    public LaserEffect(Player player){
        this.player = player;
    }

    @Override
    public void run() {
        int i = 0;
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

        particleTrails(origin,trail,player.getWorld(),Color.RED);

        while (i < 15 && !targetBlock.getType().equals(Material.AIR)) {
            player.playSound(player.getLocation(), "fuze_weapon:effect.laser", 100, 1);
            origin = target;
            target = getTargetBlock(origin,15).getLocation();
            trail = TwoLocationVector(origin,target);
            particleTrails(origin,trail,player.getWorld(),Color.RED);
            i++;
        }

    }

}
