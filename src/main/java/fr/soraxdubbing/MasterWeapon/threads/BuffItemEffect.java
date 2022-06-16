package fr.soraxdubbing.MasterWeapon.threads;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class BuffItemEffect extends BukkitRunnable {

    private ItemStack item;

    private List<PotionEffect> potionEffect;

    private List<Particle> particle;

    public BuffItemEffect(ItemStack item, List<PotionEffect> potionEffect, List<Particle> particle) {
        this.item = item;
        this.potionEffect = new ArrayList<>();
        this.potionEffect = potionEffect;
        this.particle = particle;
    }

    @Override
    public void run() {
        for (Player onlinePlayer : Bukkit.getOnlinePlayers()) {
            if (onlinePlayer.getInventory().getItemInMainHand().equals(item)) {
                onlinePlayer.addPotionEffects(this.potionEffect);
                Random random = new Random();
                for(int i = 0; i < 30; i++){

                    for (Particle particle1 : particle) {
                        float x = (float) (onlinePlayer.getLocation().getX() + Math.random() * 2 - 1);
                        float y = (float) (onlinePlayer.getLocation().getY() + Math.random() * 2 - 1);
                        float z = (float) (onlinePlayer.getLocation().getZ() + Math.random() * 2 - 1);
                        Location spawnTo = new Location(onlinePlayer.getWorld(), x, y, z);

                        onlinePlayer.getWorld().spawnParticle(particle1, spawnTo, 1);

                    }
                }
            }
        }

    }
}
