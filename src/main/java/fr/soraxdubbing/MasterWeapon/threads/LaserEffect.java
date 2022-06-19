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

        Location origin = player.getLocation().add(-Math.cos(Math.toRadians(player.getLocation().getYaw())) , 1, -Math.sin(Math.toRadians(player.getLocation().getYaw())));
        Block block = getTargetBlock(player.getEyeLocation(), 15);
        BlockFace blockFace = getBlockFace(player);
        if(blockFace == null){
            particleTrails(origin, block.getLocation().add(new Vector(0.5,0.5,0.5)), player.getWorld(), Color.RED);
        }
        else {

            Location target = getBlockFaceLocation(blockFace, block);

            while (i < 10 && block.getType() != Material.AIR) {

                particleTrails(origin, target, player.getWorld(), Color.RED);

                Vector direction = TwoLocationVector(target, origin);

                target.setDirection(direction);

                double angle = getAngleBetweenTwoVector(direction, blockFace.getDirection());

                System.out.println("angle : " + angle + " | yaw : " + target.getYaw() + " | +yaw : " + (target.getYaw() + 2*angle) + " | Pitch : " + target.getPitch() + " | +pitch : " + (target.getPitch() + 2*angle));

                target.setYaw((float) (target.getYaw() + 2*angle));

                origin = target;

                block = getTargetBlock(origin, 15);

                if(block.getType() == Material.AIR || block.getType() == Material.WATER){
                    target = block.getLocation();
                }
                else{
                    blockFace = getBlockFace(origin,block);
                    target = getBlockFaceLocation(blockFace, block);
                }
                i++;
                System.out.println(i);
            }
            particleTrails(origin, target, player.getWorld(), Color.RED);
        }

    }
}
