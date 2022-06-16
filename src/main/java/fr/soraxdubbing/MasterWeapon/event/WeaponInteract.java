package fr.soraxdubbing.MasterWeapon.event;

import fr.soraxdubbing.MasterWeapon.MasterWeapon;
import fr.soraxdubbing.MasterWeapon.WeaponEnum;
import fr.soraxdubbing.MasterWeapon.entity.CustomZombie;
import fr.soraxdubbing.MasterWeapon.threads.FishEffect;
import fr.soraxdubbing.MasterWeapon.threads.LaserEffect;
import fr.soraxdubbing.MasterWeapon.threads.BuffItemEffect;
import fr.soraxdubbing.MasterWeapon.threads.ShootThread;
import net.minecraft.server.v1_16_R3.EntityLiving;
import net.minecraft.server.v1_16_R3.EntityTypes;
import org.bukkit.*;
import org.bukkit.craftbukkit.v1_16_R3.CraftWorld;
import org.bukkit.craftbukkit.v1_16_R3.entity.CraftEntity;
import org.bukkit.craftbukkit.v1_16_R3.entity.CraftPlayer;
import org.bukkit.craftbukkit.v1_16_R3.entity.CraftZombie;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityTargetEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerItemHeldEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.util.Vector;

import java.util.*;
import java.util.List;

import static fr.soraxdubbing.MasterWeapon.ParticleAnimation.*;

public class WeaponInteract implements Listener {

    private final HashMap<WeaponEnum,ItemStack> weaponList;

    private MasterWeapon plugin;

    private ShootThread shootThread;

    private List<Player> shooter;

    public WeaponInteract(MasterWeapon plugin){
        this.plugin = plugin;
        this.weaponList = plugin.getWeaponList();
        shooter = new ArrayList<>();
        this.shootThread = new ShootThread(shooter);
        shootThread.runTaskTimer(plugin, 0, 2);

        List<PotionEffect> poseidon = new ArrayList<>();
        poseidon.add(new PotionEffect(PotionEffectType.WATER_BREATHING, 6, 0));
        poseidon.add(new PotionEffect(PotionEffectType.SLOW_FALLING, 6, 1));
        poseidon.add(new PotionEffect(PotionEffectType.INVISIBILITY, 6, 0));
        List<Particle> poseidonParticle = new ArrayList<>();
        poseidonParticle.add(Particle.WATER_SPLASH);
        poseidonParticle.add(Particle.WATER_WAKE);
        poseidonParticle.add(Particle.WATER_BUBBLE);
        BuffItemEffect poseidonEffect = new BuffItemEffect(weaponList.get(WeaponEnum.POSEIDON),poseidon,poseidonParticle);
        poseidonEffect.runTaskTimer(plugin, 0, 5);

        List<PotionEffect> invocateur = new ArrayList<>();
        invocateur.add(new PotionEffect(PotionEffectType.SPEED, 6, 1));
        invocateur.add(new PotionEffect(PotionEffectType.REGENERATION, 6, 0));
        List<Particle> invocateurParticle = new ArrayList<>();
        invocateurParticle.add(Particle.SLIME);
        invocateurParticle.add(Particle.NOTE);
        BuffItemEffect invocateurEffect = new BuffItemEffect(weaponList.get(WeaponEnum.INVOCATEUR),invocateur,invocateurParticle);
        invocateurEffect.runTaskTimer(plugin, 0, 5);
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
                        PoseidonEffect(event.getPlayer());
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

    private HashMap<Player,List<CustomZombie>> playerPet = new HashMap<>();

    private void InvocateurEffect(Player player){
        player.playSound(player.getLocation(), "fuze_weapon:effect.invocateur", 100, 1);

        CustomZombie zombie = new CustomZombie(EntityTypes.ZOMBIE,player.getLocation(),player);
        //zombie.setOwner(player);
        //zombie.setName("§5El pablo le Zombie de " + player.getName());
        //zombie.setGoalTarget(((CraftPlayer)player).getHandle());
        //((CraftWorld)player.getWorld()).getHandle().addEntity(zombie);

        zombie.setGoalTarget(((CraftPlayer)player).getHandle(),EntityTargetEvent.TargetReason.CUSTOM, false);

        if(!playerPet.containsKey(player)){
            playerPet.put(player,new ArrayList<>());
        }
        playerPet.get(player).add(zombie);

        ((CraftWorld)player.getWorld()).getHandle().addEntity(zombie);
    }

    @EventHandler
    public void onAttack(EntityDamageByEntityEvent event){
        if(!playerPet.isEmpty()){
            if(event.getDamager() instanceof Player && playerPet.containsKey((Player)event.getDamager())) {
                Player player = (Player)event.getDamager();
                List<CustomZombie> zombies = playerPet.get(player);
                for (CustomZombie zombie : zombies) {
                    zombie.setGoalTarget((EntityLiving)((CraftEntity) event.getEntity()).getHandle(), EntityTargetEvent.TargetReason.CUSTOM, false);
                }
            }
            else if(event.getEntity() instanceof Player && playerPet.containsKey((Player)event.getEntity())){
                Player player = (Player)event.getEntity();
                List<CustomZombie> zombies = playerPet.get(player);

                if(zombies.contains(((CraftZombie) event.getDamager()).getHandle())){
                    event.setCancelled(true);
                }
                else {
                    for (CustomZombie zombie : zombies) {
                        zombie.setGoalTarget((EntityLiving)((CraftEntity) event.getDamager()).getHandle(), EntityTargetEvent.TargetReason.CUSTOM, false);
                    }
                }
            }
            else {
                playerPet.forEach((player, zombies) -> {
                    for (CustomZombie zombie : zombies) {
                        if(event.getEntity() == zombie.getGoalTarget() && event.getEntity().isDead()){

                            zombie.setGoalTarget(((CraftPlayer) player).getHandle(), EntityTargetEvent.TargetReason.CUSTOM, false);
                        }
                    }
                });
            }
        }

    }


    private void PoseidonEffect(Player player){
        if(player.isSneaking()){
            summonCircle(player.getLocation(),10, Color.BLUE);
            List<Entity> entities = player.getNearbyEntities(10,10,10);
            for (Entity entity : entities) {
                Location up = new Location(entity.getWorld(),entity.getLocation().getX(),entity.getLocation().getY()+2.5,entity.getLocation().getZ());
                Vector haut = TwoLocationVector(entity.getLocation(),up);
                entity.setVelocity(haut);
                particleTrails(entity.getLocation(),up,player.getWorld(),Color.BLUE);
            }
        }
        else{
            player.playSound(player.getLocation(), "fuze_weapon:effect.poseidon", 100, 1);
            Vector depl = new Vector(player.getLocation().getDirection().getX(),player.getLocation().getDirection().getY(),player.getLocation().getDirection().getZ()).normalize().multiply(3);
            player.setVelocity(depl);
        }
    }

    private void LaserEffect(Player player){
        LaserEffect laserEffect = new LaserEffect(player);
        laserEffect.run();
    }

    private void FishShooterEffect(Player player){
        Location pLoc = player.getLocation().add(-Math.cos(Math.toRadians(player.getLocation().getYaw())) , 1, -Math.sin(Math.toRadians(player.getLocation().getYaw())));
        Random random = new Random();
        Entity fish = null;
        player.playSound(player.getLocation(), "fuze_weapon:effect.laser", 100, 1);
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
        FishEffect fishEffect = new FishEffect(fish);
        fish.getWorld().spawnParticle(Particle.CLOUD, fish.getLocation(), 10);
        fish.setFallDistance(0);
        fishEffect.runTaskTimer(plugin,0,15);
    }
}
