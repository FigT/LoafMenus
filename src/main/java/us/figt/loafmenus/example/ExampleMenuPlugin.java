package us.figt.loafmenus.example;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import us.figt.loafmenus.LoafMenuHandler;

/**
 * @author FigT
 */
public class ExampleMenuPlugin extends JavaPlugin implements CommandExecutor {

    private LoafMenuHandler loafMenuHandler;

    @Override
    public void onEnable() {
        try {
            this.loafMenuHandler = new LoafMenuHandler(this);
        } catch (InstantiationException e) {
            e.printStackTrace();
            getServer().getPluginManager().disablePlugin(this);
            return;
        }

        loafMenuHandler.registerLoafMenu(ExampleMenu.class, new ExampleMenu());
    }

    @Override
    public void onDisable() {
        if (this.loafMenuHandler != null) {
            this.loafMenuHandler.unregister();
            this.loafMenuHandler = null;
        }
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (command.getName().equalsIgnoreCase("examplemenu") && sender instanceof Player) {
            Player player = (Player) sender;
            loafMenuHandler.getLoafMenu(ExampleMenu.class).open(player);
        }

        return true;
    }
}
