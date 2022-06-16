package fr.soraxdubbing.MasterWeapon.entity;

import net.minecraft.server.v1_16_R3.*;

import java.util.EnumSet;

public class PathfinderGoalPet extends PathfinderGoal {

    private final EntityInsentient pet; // our pet
    private EntityPlayer owner; // owner

    private final double speed; // pet's speed
    private final float distance; // distance between owner & pet

    private double x; // x
    private double y; // y
    private double z; // z


    public PathfinderGoalPet(EntityInsentient pet, double speed, float distance, EntityPlayer owner) {
        System.out.println("1");
        this.pet = pet;
        System.out.println("2");
        this.owner = owner;
        System.out.println("3");
        this.speed = speed;
        System.out.println("4");
        this.distance = distance;
        System.out.println("5");
        this.a(EnumSet.of(Type.MOVE));
    }

    @Override
    public boolean a() {
        // Will start event if this is true
        // runs every tick
        if(this.owner == null)
            System.out.println("owner is null");
        double d = Math.sqrt((owner.locY()- pet.locY()) * (owner.locY()- pet.locY() + (owner.locX()- pet.locX()) * (owner.locX()- pet.locX())));
        System.out.println("1");
        if (d > Math.pow(distance, 2)) {
            // distance is too far then teleport pet
            System.out.println("2");
            pet.setPosition(this.owner.locX(), this.owner.locY(), this.owner.locZ());
            return false;
        }
        else {
            System.out.println("3");
            // follow owner
            Vec3D vec = RandomPositionGenerator.a((EntityCreature) this.pet, 16, 7, this.owner.getPositionVector());
            // in air
            System.out.println("4");
            if (vec == null)
                return false;
            this.x = this.owner.locX(); // x
            this.y = this.owner.locY(); // y
            this.z = this.owner.locZ(); // z
            System.out.println("5");
            return true;
            // call upon c()
        }
    }

    public void c() {
        // runner :)                x      y        z    speed
        this.pet.getNavigation().a(this.x, this.y, this.z, this.speed);
    }

    public boolean b() {
        // run every tick as long as its true (repeats c)
        return !this.pet.getNavigation().m() && this.owner.h(this.pet) < (double) (this.distance * this.distance);
    }

    public void d() {
        // runs when done (b is false)
        this.owner = null;
    }

}