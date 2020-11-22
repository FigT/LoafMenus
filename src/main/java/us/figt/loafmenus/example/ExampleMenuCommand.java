/*
 *    Copyright 2020 FigT
 *
 *    Licensed under the Apache License, Version 2.0 (the "License");
 *    you may not use this file except in compliance with the License.
 *    You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 *    Unless required by applicable law or agreed to in writing, software
 *    distributed under the License is distributed on an "AS IS" BASIS,
 *    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *    See the License for the specific language governing permissions and
 *    limitations under the License.
 */
package us.figt.loafmenus.example;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

/**
 * @author FigT
 */
public class ExampleMenuCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender instanceof Player) {
            Player player = (Player) sender;

            if (args.length < 1) {
                player.sendMessage(ChatColor.RED + "Usage: /examplemenu <1:2:3:4>");
                return true;
            }

            switch (args[0].toLowerCase().trim()) {
                case "1":
                    new ExampleMenu(player).open();
                    break;
                case "2":
                    new RainbowExampleMenu(player).open();
                    break;
                case "3":
                    new BoxExampleMenu(player).open();
                    break;
                case "4":
                    new HopperExampleMenu(player).open();
                    break;
                default:
                    player.sendMessage(ChatColor.RED + "Invalid menu example menu");
                    break;
            }
        }
        return true;
    }
}
