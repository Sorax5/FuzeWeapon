package fr.soraxdubbing.fuzeweapon.threads;

import fr.soraxdubbing.fuzeweapon.ParticleAnimation;
import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.BlockIterator;
import org.bukkit.util.Vector;

import java.util.List;

import static fr.soraxdubbing.fuzeweapon.ParticleAnimation.*;

public class ShootThread extends BukkitRunnable {

    private List<Player> players;

    public ShootThread(List<Player> players){
        this.players = players;
    }

    @Override
    public void run() {
        for (Player player : players) {
            Location origin = player.getEyeLocation();

            Block targetBlock = getTargetBlock(origin,15);

            Location target = targetBlock.getLocation();

            if(targetBlock.getType() != Material.AIR && origin.distance(target) <=15){
                BlockFace blockFace = getBlockFace(player);
                target = getBlockFaceLocation(blockFace,targetBlock);
                particleTrails(origin,target,player.getWorld(), Color.GRAY);
                targetBlock.breakNaturally();
            }
            else {
                Vector toAdd = new Vector();
                Vector originLocVector = origin.getDirection().normalize();
                SpawnParticleLoop(15,player.getWorld(),originLocVector,origin, Color.GRAY);
            }
        }
    }
}
