package fr.soraxdubbing.MasterWeapon.commands;

import fr.soraxdubbing.MasterWeapon.MasterWeapon;
import fr.soraxdubbing.MasterWeapon.WeaponEnum;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;

public class WeaponGive implements CommandExecutor {

    private HashMap<WeaponEnum,ItemStack> weaponList;

    public WeaponGive(MasterWeapon plugin){
        this.weaponList = plugin.getWeaponList();
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        String end = "";
        if(!(sender instanceof Player)){
            end += "§4[Error]§r Cet commande est uniquement disponible pour les joueurs";
        }
        else {
            Player player = (Player) sender;
            switch (args.length){
                case 0:
                    end += "§4[Error]§r Veuillez indiquez une arme";
                    break;
                case 1:
                    switch (args[0].toLowerCase()){
                        case "mitraillette":
                            player.getInventory().addItem(this.weaponList.get(WeaponEnum.MITRAILLETTE));
                            end += "§2[Success]§r la mitraillette vous a été donnée";
                            break;
                        case "fish_launcher":
                            player.getInventory().addItem(this.weaponList.get(WeaponEnum.FISH_SHOOTER));
                            end += "§2[Success]§r le lanceur de poisson vous a été donnée";
                            break;
                        case "laser":
                            player.getInventory().addItem(this.weaponList.get(WeaponEnum.LASER));
                            end += "§2[Success]§r le laser vous a été donnée";
                            break;
                        case "poséidon":
                            player.getInventory().addItem(this.weaponList.get(WeaponEnum.POSEIDON));
                            end += "§2[Success]§r le Sceptre de Poséidon vous a été donnée";
                            break;
                        case "invocateur":
                            player.getInventory().addItem(this.weaponList.get(WeaponEnum.INVOCATEUR));
                            end += "§2[Success]§r le Bâton de l’invocateur vous a été donnée";
                            break;
                        case "all":
                            for(ItemStack item : weaponList.values()){
                                player.getInventory().addItem(item);
                            }
                            end += "§2[Success]§r Les armes de la destruction vous ont été remises !";
                            break;
                        default:
                            end += "§4[Error]§r Veuillez indiquer une arme valide <mitraillette,fish_launcher,laser,poséidon,invocateur,all>";
                            break;
                    }
                    break;
                default:
                    end += "§4[Error]§r Vous avez besoin d'uniquement un seul paramètre <mitraillette,fish_launcher,laser,poséidon,invocateur,all>";
                    break;
            }
        }
        sender.sendMessage(end);
        return true;
    }
}
