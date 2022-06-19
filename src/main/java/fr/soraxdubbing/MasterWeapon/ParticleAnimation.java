package fr.soraxdubbing.MasterWeapon;

import net.minecraft.server.v1_16_R3.EntityLiving;
import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.util.BlockIterator;
import org.bukkit.util.Vector;

import java.util.List;

public class ParticleAnimation {

    public static void particleTrails(Location origin, Location target, World world,Color color){
        Vector direction = TwoLocationVector(origin,target);
        double distance = direction.clone().length();
        Vector directionNormalized = direction.clone().normalize();
        SpawnParticleLoop(distance,world,directionNormalized,origin,color);
    }

    public static Vector SymetricVector(Vector vector,Vector normal,Location origin) {
        Location a = origin.clone();
        Location c = origin.clone().add(vector);
        Location i = new Location(origin.getWorld(),a.getX()*normal.getX(),a.getY()*normal.getY(),a.getZ()*normal.getZ());
        Location b = LocationSymetric(a,i);
        return TwoLocationVector(c,b);
    }

    public static Location LocationSymetric(Location origin,Location middle) {
        return new Location(origin.getWorld(),2*middle.getX()-origin.getX(),2*middle.getY()-origin.getY(),2*middle.getZ()-origin.getZ());
    }


    public static BlockFace getBlockFace(Player player) {
        List<Block> lastTwoTargetBlocks = player.getLastTwoTargetBlocks(null, 100);
        if (lastTwoTargetBlocks.size() != 2 || !lastTwoTargetBlocks.get(1).getType().isOccluding()) return null;
        Block targetBlock = lastTwoTargetBlocks.get(1);
        Block adjacentBlock = lastTwoTargetBlocks.get(0);
        return targetBlock.getFace(adjacentBlock);
    }

    public static BlockFace getBlockFace(Location origin, Block target) {
        float dir = (float) Math.toDegrees(Math.atan2(origin.getBlockX() - target.getX(), target.getZ() - origin.getBlockZ()));
        return getClosestFace(dir);
    }

    public static BlockFace getClosestFace(float direction) {
        direction = direction % 360;
        if (direction < 0)
            direction += 360;
        direction = Math.round(direction / 45);
        switch ((int) direction) {
            case 0:
                return BlockFace.WEST;
            case 1:
                return BlockFace.NORTH_WEST;
            case 2:
                return BlockFace.NORTH;
            case 3:
                return BlockFace.NORTH_EAST;
            case 4:
                return BlockFace.EAST;
            case 5:
                return BlockFace.SOUTH_EAST;
            case 6:
                return BlockFace.SOUTH;
            case 7:
                return BlockFace.SOUTH_WEST;
            default:
                return BlockFace.WEST;
        }
    }

    public static Vector TwoLocationVector(Location origin,Location target){
        return new Vector(target.getX()-origin.getX(),target.getY()-origin.getY(),target.getZ()-origin.getZ());
    }

    public static void particleTrails(Location origin, Vector toParcour, World world,Color color){
        double distance = toParcour.clone().length();
        Vector direction = toParcour.clone().normalize();
        SpawnParticleLoop(distance,world,direction,origin,color);
    }

    public static void SpawnParticleLoop(double amount,World world,Vector direction,Location origin,Color color){
        for(int i = 0;i < amount;i++){
            Vector d = direction.clone().multiply(i);
            Location particleLoc = new Location(world,origin.getX() + d.getX(),origin.getY()+d.getY(),origin.getZ()+d.getZ());
            Particle.DustOptions dustOptions = new Particle.DustOptions(color, 1.0F);
            world.spawnParticle(Particle.REDSTONE, particleLoc, 20,dustOptions);
        }
    }

    public static final Block getTargetBlock(Location loc, int range) {
        BlockIterator iter = new BlockIterator(loc.getWorld(), loc.toVector(), loc.getDirection(), 0.0D, range);
        Block lastBlock = iter.next();
        while (iter.hasNext()) {
            lastBlock = iter.next();
            if (lastBlock.getType() == Material.AIR || lastBlock.getType() == Material.WATER) {
                continue;
            }
            break;
        }
        return lastBlock;
    }

    public static Location getFaceLocationByPlayer(Player player,Block block) {
        return block.getFace(player.getLocation().getBlock()).getDirection().toLocation(block.getWorld());
    }

    public static Location getBlockFaceLocation(BlockFace face,Block block) {
        Location loc = block.getLocation();
        loc.setX(loc.getX()+0.5);
        loc.setZ(loc.getZ()+0.5);
        loc.setY(loc.getY()+0.5);
        switch (face) {
            case EAST:
                loc.setX(loc.getX() + 0.5);
                break;
            case WEST:
                loc.setX(loc.getX() - 0.5);
                break;
            case NORTH:
                loc.setZ(loc.getZ() - 0.5);
                break;
            case SOUTH:
                loc.setZ(loc.getZ() + 0.5);
                break;
            case UP:
                loc.setY(loc.getY() + 0.5);
                break;
            case DOWN:
                loc.setY(loc.getY() - 0.5);
                break;
        }
        return loc;
    }

    public static void summonCircle(Location location, int size,Color color) {
        for (int d = 0; d <= 90; d += 1) {
            Location particleLoc = new Location(location.getWorld(), location.getX(), location.getY(), location.getZ());
            particleLoc.setX(location.getX() + Math.cos(d) * size);
            particleLoc.setZ(location.getZ() + Math.sin(d) * size);
            location.getWorld().spawnParticle(Particle.REDSTONE, particleLoc, 1, new Particle.DustOptions(color, 5));
        }
    }

    public static Boolean isInRange(Location location, Location location2, int range) {
        double xPow = Math.pow(Math.abs(location.getX() - location2.getX() ), 2);
        double yPow = Math.pow(Math.abs(location.getY() - location2.getY() ), 2);
        double zPow = Math.pow(Math.abs(location.getZ() - location2.getZ() ), 2);
        double is = Math.sqrt(xPow + yPow + zPow);
        return is < range;
    }

    public static EntityLiving getTargetEntity(Player player, int radius) {
        Entity toReturn = null;
        for(Entity e : player.getNearbyEntities(10, 10, 10)){
            if(e.getLocation().getDirection() == player.getLocation().getDirection() && e instanceof EntityLiving){
                toReturn = e;
            }
        }
        return (EntityLiving) toReturn;
    }

    public static boolean getLookingAt(Player player, Entity entity)
    {
        List<Block> blocks = player.getLineOfSight(null, 100);
        for (Block block : blocks) {
            if(block.getLocation() == entity.getLocation())
                return true;
        }
        return false;
    }

    public static Location getSymetricPointAtNormal(Location point,Location p2,Vector normal) {
        Location A = point.clone();
        Location B = getLocationBetweenVectoraAndLocation(point,p2,normal.clone());
        double x = (2*A.getX() - B.getX());
        double y = (2*A.getY() - B.getY());
        double z = (2*A.getZ() - B.getZ());
        return new Location(point.getWorld(),x,y,z);
    }

    public static Location getLocationBetweenVectoraAndLocation(Location point,Location p2,Vector normal){
        double x = (point.getX() * normal.getX()) + (p2.getX() * normal.getX());
        double y = (point.getY() * normal.getY()) + (p2.getY() * normal.getY());
        double z = (point.getZ() * normal.getZ()) + (p2.getZ() * normal.getZ());
        return new Location(point.getWorld(),x,y,z);
    }

    public static double getAngleBetweenTwoVector(Vector v1,Vector v2){
        double produitSclaire = v1.getX()*v2.getX() + v1.getZ()*v2.getZ() + v1.getY()*v2.getY();
        double normeDirection = Math.sqrt(Math.pow(v1.getX(),2) + Math.pow(v1.getZ(),2) + Math.pow(v1.getY(),2));
        double normeBlockFace = Math.sqrt(Math.pow(v1.getX(),2) + Math.pow(v1.getZ(),2) + Math.pow(v1.getY(),2));
        System.out.println(produitSclaire + " / " + normeDirection + " / " + normeBlockFace);
        double cos = produitSclaire / (normeDirection * normeBlockFace);
        return Math.toDegrees(Math.acos(cos));
    }

}
