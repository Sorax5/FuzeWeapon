package fr.soraxdubbing.MasterWeapon.entity;

import net.minecraft.server.v1_16_R3.*;
import org.bukkit.Color;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.craftbukkit.v1_16_R3.CraftWorld;
import org.bukkit.craftbukkit.v1_16_R3.inventory.CraftItemStack;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.LeatherArmorMeta;

public class CustomZombie extends EntityZombie {

    public CustomZombie(EntityTypes<? extends EntityZombie> type,Location loc,Player player) {
        super(type, ((CraftWorld)loc.getWorld()).getHandle());
        this.setPosition(loc.getX(), loc.getY(), loc.getZ());
        this.setHealth(50);
        this.setCustomName(new ChatComponentText("§5§kInvocation de " + player.getName()));
        this.setCustomNameVisible(true);

        ItemStack head = new ItemStack(Material.LEATHER_HELMET);
        LeatherArmorMeta meta4 = (LeatherArmorMeta) head.getItemMeta();
        meta4.setColor(Color.PURPLE);
        head.setItemMeta(meta4);
        ItemStack chest = new ItemStack(Material.LEATHER_CHESTPLATE);
        LeatherArmorMeta meta5 = (LeatherArmorMeta) chest.getItemMeta();
        meta5.setColor(Color.PURPLE);
        chest.setItemMeta(meta5);
        ItemStack legs = new ItemStack(Material.LEATHER_LEGGINGS);
        LeatherArmorMeta meta6 = (LeatherArmorMeta) legs.getItemMeta();
        meta6.setColor(Color.PURPLE);
        legs.setItemMeta(meta6);
        ItemStack boots = new ItemStack(Material.LEATHER_BOOTS);
        LeatherArmorMeta meta7 = (LeatherArmorMeta) boots.getItemMeta();
        meta7.setColor(Color.PURPLE);
        boots.setItemMeta(meta7);

        this.setSlot(EnumItemSlot.MAINHAND, CraftItemStack.asNMSCopy(new ItemStack(Material.IRON_SWORD)));
        this.setSlot(EnumItemSlot.HEAD, CraftItemStack.asNMSCopy(head));
        this.setSlot(EnumItemSlot.CHEST, CraftItemStack.asNMSCopy(chest));
        this.setSlot(EnumItemSlot.LEGS, CraftItemStack.asNMSCopy(legs));
        this.setSlot(EnumItemSlot.FEET, CraftItemStack.asNMSCopy(boots));

        this.getAttributeInstance(GenericAttributes.MOVEMENT_SPEED).setValue(0.6D);

    }

}
