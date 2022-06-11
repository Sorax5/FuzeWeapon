package fr.soraxdubbing.fuzeweapon.threads;

import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.BlockIterator;
import org.bukkit.util.Vector;

import java.util.List;

public class ShootThread extends BukkitRunnable {

    private List<Player> players;

    public ShootThread(List<Player> players){
        this.players = players;
    }

    @Override
    public void run() {
        for (Player player : players) {
            Block block = getTargetBlock(player,15);
            Location target = block.getLocation();
            Location origin = player.getEyeLocation();

            target.setX(target.getX()+0.5);
            target.setZ(target.getZ()+0.5);
            target.setY(target.getY()+1);

            if(block.getType() != Material.AIR && origin.distance(target) <=15){
                particleTrails(origin,target,player.getWorld());
                block.breakNaturally();
            }
            else {
                Vector toAdd = new Vector();
                Vector originLocVector = origin.toVector();
                Vector targetLocVector = target.toVector();
                Vector direction = originLocVector.subtract(targetLocVector).normalize();
                SpawnParticleLoop(15,player.getWorld(),direction,origin);
            }
        }
    }

    private void particleTrails(Location origin, Location target, World world){
        Vector originLocVector = origin.toVector();
        Vector targetLocVector = target.toVector();
        Vector direction = originLocVector.clone().subtract(targetLocVector).normalize();
        double distance = originLocVector.clone().subtract(targetLocVector).length();
        SpawnParticleLoop(distance,world,direction,origin);
    }

    private void SpawnParticleLoop(double amount,World world,Vector direction,Location origin){
        for(int i = 0;i < amount;i++){
            Vector d = direction.clone().multiply(-i);
            Location particleLoc = new Location(world,origin.getX() + d.getX(),origin.getY()+d.getY(),origin.getZ()+d.getZ());
            Particle.DustOptions dustOptions = new Particle.DustOptions(Color.GRAY, 1.0F);
            world.spawnParticle(Particle.REDSTONE, particleLoc, 20,dustOptions);
        }
    }

    public final Block getTargetBlock(Player player, int range) {
        BlockIterator iter = new BlockIterator(player, range);
        Block lastBlock = iter.next();
        while (iter.hasNext()) {
            lastBlock = iter.next();
            if (lastBlock.getType() == Material.AIR) {
                continue;
            }
            break;
        }
        return lastBlock;
    }
}
