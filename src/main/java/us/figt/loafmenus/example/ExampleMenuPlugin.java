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

import lombok.Getter;
import org.bukkit.plugin.java.JavaPlugin;
import us.figt.loafmenus.LoafMenuRegistrar;

/**
 * @author FigT
 */
@Getter
public class ExampleMenuPlugin extends JavaPlugin {

    @Getter
    private static ExampleMenuPlugin instance;

    private LoafMenuRegistrar loafMenuRegistrar;

    @Override
    public void onEnable() {
        instance = this;

        try {
            this.loafMenuRegistrar = new LoafMenuRegistrar(this);
        } catch (InstantiationException e) {
            e.printStackTrace();
            getServer().getPluginManager().disablePlugin(this);
            return;
        }

        getCommand("examplemenu").setExecutor(new ExampleMenuCommand());
    }

    @Override
    public void onDisable() {
        if (this.loafMenuRegistrar != null) {
            this.loafMenuRegistrar.unregister();
            this.loafMenuRegistrar = null;
        }

        instance = null;
    }
}
