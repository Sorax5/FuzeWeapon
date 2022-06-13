package fr.soraxdubbing.fuzeweapon.threads;

import org.bukkit.entity.Entity;
import org.bukkit.scheduler.BukkitRunnable;

public class FishCooldown extends BukkitRunnable {

    private Entity fish;

    private int cooldown = 5;

    private boolean c = false;

    public FishCooldown(Entity fish){
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

            cooldown--;
            System.out.println(cooldown);
        }
    }
}
