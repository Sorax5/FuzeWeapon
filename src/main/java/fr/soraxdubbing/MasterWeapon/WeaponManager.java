package fr.soraxdubbing.MasterWeapon;

import org.bukkit.Material;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeModifier;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;

public class WeaponManager {

    public static ItemStack CreateWeapon(WeaponEnum weapon){
        ItemStack item = null;
        switch (weapon){
            case LASER:
                item = CreateLaser();
                break;
            case POSEIDON:
                item = CreatePoseidon();
                break;
            case INVOCATEUR:
                item = CreateInvocateur();
                break;
            case MITRAILLETTE:
                item = CreateMitraillette();
                break;
            case FISH_SHOOTER:
                item = CreateFishLauncher();
                break;
        }
        return item;
    }

    private static ItemStack CreateMitraillette(){
        ItemStack mitraillette = CreateClassiqueWeapon();
        ItemMeta mMeta = mitraillette.getItemMeta();
        mMeta.setDisplayName("§7§lMitraillette");
        List<String> lore = new ArrayList<>();
        lore.add("§3§l---- DESCRIPTION ----");
        lore.add("§aA ne pas laisser dans la main d'un fragile");
        mMeta.setLore(lore);
        mMeta.setCustomModelData(5);
        mitraillette.setItemMeta(mMeta);
        return mitraillette;
    }

    private static ItemStack CreateLaser(){
        ItemStack laser = CreateClassiqueWeapon();
        ItemMeta lMeta = laser.getItemMeta();
        lMeta.setDisplayName("§c§lLaser");
        List<String> lore = new ArrayList<>();
        lore.add("§3§l---- DESCRIPTION ----");
        lore.add("§aAttention ! Rebondissement garanti !");
        lMeta.setLore(lore);
        lMeta.setCustomModelData(10);
        laser.setItemMeta(lMeta);
        return laser;
    }

    private static ItemStack CreateFishLauncher(){
        ItemStack fish = CreateClassiqueWeapon();
        ItemMeta fMeta = fish.getItemMeta();
        fMeta.setDisplayName("§3§lLanceur de poisson");
        List<String> lore = new ArrayList<>();
        lore.add("§3§l---- DESCRIPTION ----");
        lore.add("§aQuoi ? des poissons ?");
        fMeta.setLore(lore);
        fMeta.setCustomModelData(15);
        fish.setItemMeta(fMeta);
        return fish;
    }

    private static ItemStack CreatePoseidon(){
        ItemStack poseidon = CreateClassiqueWeapon();
        ItemMeta pMeta = poseidon.getItemMeta();
        pMeta.setDisplayName("§3§lSceptre de Poséidon");
        List<String> lore = new ArrayList<>();
        lore.add("§3§l---- DESCRIPTION ----");
        lore.add("§aLa puissance de la mer afflue en vous !");
        lore.add("§3§l---- ATTRIBUTS ----");
        lore.add("- §r+13 §4§lForce§r");
        lore.add("- §r+5 §2§lVitesse§r");
        pMeta.setLore(lore);
        pMeta.setCustomModelData(20);
        pMeta.addAttributeModifier(Attribute.GENERIC_ATTACK_DAMAGE, new AttributeModifier("generic.attackDamage", 13, AttributeModifier.Operation.ADD_NUMBER));
        pMeta.addAttributeModifier(Attribute.GENERIC_ATTACK_SPEED, new AttributeModifier("generic.attackSpeed", 5, AttributeModifier.Operation.ADD_NUMBER));

        poseidon.setItemMeta(pMeta);
        return poseidon;
    }

    private static ItemStack CreateInvocateur(){
        ItemStack invocateur = CreateClassiqueWeapon();
        ItemMeta iMeta = invocateur.getItemMeta();
        iMeta.setDisplayName("§2§lBâton de l’invocateur");
        List<String> lore = new ArrayList<>();
        lore.add("§3§l---- DESCRIPTION ----");
        lore.add("§aVous sentez des âmes émerger de ce bâton");
        iMeta.setLore(lore);
        iMeta.setCustomModelData(25);
        invocateur.setItemMeta(iMeta);
        return invocateur;
    }

    private static ItemStack CreateClassiqueWeapon(){
        ItemStack item = new ItemStack(Material.CARROT_ON_A_STICK);
        ItemMeta iMeta = item.getItemMeta();
        iMeta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
        iMeta.addItemFlags(ItemFlag.HIDE_UNBREAKABLE);
        iMeta.setUnbreakable(true);
        item.setItemMeta(iMeta);
        return item;
    }
}
