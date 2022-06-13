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

        this.goalSelector.a(0, new PathfinderGoalFloat(this));
        this.goalSelector.a(2,new PathfinderGoalLookAtPlayer(this,EntityHuman.class,8.0F));
        this.goalSelector.a(4,new PathfinderGoalAvoidTarget<CustomZombie>(this,CustomZombie.class,8.0F,1F,1F));

        this.targetSelector.a(0,new PathfinderGoalNearestAttackableTarget<EntityMonster>(this,EntityMonster.class,false));
        this.targetSelector.a(1,new PathfinderGoalLeapAtTarget(this,0.1F));


        this.getWorld().addEntity(this);
    }
}
