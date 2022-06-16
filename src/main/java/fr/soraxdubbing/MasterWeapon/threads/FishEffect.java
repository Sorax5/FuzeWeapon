package fr.soraxdubbing.MasterWeapon.threads;

import org.bukkit.Particle;
import org.bukkit.entity.Entity;
import org.bukkit.scheduler.BukkitRunnable;

public class FishEffect extends BukkitRunnable {

    private Entity fish;

    private int cooldown = 6;

    private boolean c = false;

    public FishEffect(Entity fish){
        this.fish = fish;
        fish.setCustomNameVisible(true);
        this.fish.setFallDistance(0);
    }

    @Override
    public void run() {
        if(cooldown == 0 && !fish.isDead()){
            fish.remove();
            fish.getWorld().createExplosion(fish.getLocation(),5);
            this.cancel();
        }
        else if(fish.isDead()){
            this.cancel();
        }
        else {
            if(c){
                fish.setCustomName("§4" + cooldown);
            }
            else {
                fish.setCustomName("§r" + cooldown);
            }
            c = !c;
            fish.getWorld().spawnParticle(Particle.CAMPFIRE_COSY_SMOKE, fish.getLocation(), 30);

            cooldown--;
        }
    }
}
