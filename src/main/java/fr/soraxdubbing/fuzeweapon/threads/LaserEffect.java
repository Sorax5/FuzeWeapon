package fr.soraxdubbing.fuzeweapon.threads;

import fr.soraxdubbing.fuzeweapon.ParticleAnimation;
import org.bukkit.Color;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

import static fr.soraxdubbing.fuzeweapon.ParticleAnimation.*;

public class LaserEffect extends BukkitRunnable {

    private Player player;

    public LaserEffect(Player player){
        this.player = player;
    }

    @Override
    public void run() {
        int i = 0;

        BlockFace blockFace = getBlockFace(player);
        Block targetBlock = getTargetBlock(player.getEyeLocation(),15);

        Location origin = player.getLocation();
        Location target = getBlockFaceLocation(blockFace,targetBlock);

        target.setYaw(target.getYaw()+135);

        Vector trail = TwoLocationVector(origin,target);

        particleTrails(origin,trail,player.getWorld(),Color.RED);

        while (i < 15) {
            origin = target;
            target = getTargetBlock(origin,15).getLocation();
            trail = TwoLocationVector(origin,target);
            particleTrails(origin,trail,player.getWorld(),Color.RED);
            i++;
        }

    }

}
