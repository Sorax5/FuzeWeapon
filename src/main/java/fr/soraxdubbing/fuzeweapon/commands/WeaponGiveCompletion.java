package fr.soraxdubbing.fuzeweapon.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;

import java.util.ArrayList;
import java.util.List;

public class WeaponGiveCompletion implements TabCompleter {
    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
        List<String> rep = new ArrayList<>();
        rep.add("mitraillette");
        rep.add("fish_launcher");
        rep.add("laser");
        rep.add("poséidon");
        rep.add("invocateur");
        rep.add("all");
        return rep;
    }
}
