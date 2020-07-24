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
package us.figt.loafmenus;

import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * @author FigT
 */
@Getter
public class LoafMenuRegistrar {

    private JavaPlugin plugin;
    private Map<UUID, LoafMenu> openedLoafMenus;

    @Getter
    private static boolean registered = false;


    /**
     * Instantiates a new LoafMenuRegistrar
     *
     * @param plugin the JavaPlugin this LoafMenuRegistrar belongs to
     * @throws InstantiationException if a LoafMenuRegistrar has already been registered
     */
    public LoafMenuRegistrar(JavaPlugin plugin) throws InstantiationException {
        if (registered) {
            throw new InstantiationException("LoafMenuRegistrar has already been registered");
        }

        this.plugin = plugin;
        this.openedLoafMenus = new HashMap<>();
        registered = true;

        this.plugin.getServer().getPluginManager().registerEvents(new LoafMenuListener(this), this.plugin);
    }


    /**
     * Cleans up objects, unregisters the handler, etc.
     * NOTE: Call this method in onDisable
     */
    public void unregister() {
        if (this.openedLoafMenus != null) {
            this.openedLoafMenus.forEach((uuid, loafMenu) -> {
                Player fromUUID = Bukkit.getPlayer(uuid);

                // if not null close inv
                if (fromUUID != null) {
                    fromUUID.closeInventory();
                }

                loafMenu.cleanup();
            });

            this.openedLoafMenus.clear();

            this.openedLoafMenus = null;
        }


        if (this.plugin != null) this.plugin = null;

        registered = false;
    }
}
