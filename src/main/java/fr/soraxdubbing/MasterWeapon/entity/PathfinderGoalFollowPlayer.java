package fr.soraxdubbing.MasterWeapon.entity;

import net.minecraft.server.v1_16_R3.EntityInsentient;
import net.minecraft.server.v1_16_R3.EntityPlayer;
import net.minecraft.server.v1_16_R3.PathfinderGoal;

import java.util.EnumSet;

public class PathfinderGoalFollowPlayer extends PathfinderGoal {

    private EntityInsentient entity;
    private EntityPlayer owner;
    private double speed;
    private float distanceSquared;

    public PathfinderGoalFollowPlayer(EntityInsentient entity, EntityPlayer owner, double speed, float distance) {
        this.entity = entity;
        this.owner = owner;
        this.speed = speed;
        this.distanceSquared = distance * distance;
        this.a(EnumSet.of(Type.MOVE));
    }

    @Override
    public boolean a() {
        return (owner != null && owner.isAlive() && entity.h(owner) > (double)distanceSquared);
    }

    @Override
    public void d() {
        entity.getNavigation().n();
    }

    @Override
    public void e() {
        entity.getControllerLook().a(owner, 10.0F, entity.getHeadRotation());
        entity.getNavigation().a(owner, speed);
    }
}
