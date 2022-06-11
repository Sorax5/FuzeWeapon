package fr.soraxdubbing.fuzeweapon.entity;

import net.minecraft.server.v1_16_R3.*;
import org.bukkit.entity.Sheep;
import org.bukkit.entity.Zombie;

public class CustomZombie extends EntityZombie {


    public CustomZombie( World world) {
        super(world);

        Zombie craftZombie = (Zombie) this.getBukkitEntity();
        this.setHealth(50);
        this.setCustomName(new ChatComponentText("§5El pablo le Mouton"));
        this.setCustomNameVisible(true);

        this.goalSelector.a(1,new PathfinderGoalAvoidTarget<CustomZombie>(this,CustomZombie.class,8.0F,1F,1F));
        this.targetSelector.a(0,new PathfinderGoalNearestAttackableTarget<EntityMonster>(this,EntityMonster.class,true));
        this.targetSelector.a(1,new PathfinderGoalLeapAtTarget(this,1.0F));
        this.goalSelector.a(0,new PathfinderGoalLookAtPlayer(this,EntityHuman.class,0.1F));

        this.getWorld().addEntity(this);
    }
}
