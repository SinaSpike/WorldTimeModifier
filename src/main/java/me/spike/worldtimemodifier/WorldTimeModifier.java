package me.spike.worldtimemodifier;

import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Objects;

public final class WorldTimeModifier extends JavaPlugin {
    private final FileConfiguration configuration = getConfig();
    private int night_add;
    private int day_add;

    @Override
    public void onEnable() {
        saveDefaultConfig();
        World world = Bukkit.getWorld(Objects.requireNonNull(configuration.getString("world")));

        if(world == null) {
            return;
        }

        night_add = (int) 11000 / configuration.getInt("NightLength");
        day_add = (int) 12999 / configuration.getInt("DayLength");

        Bukkit.getScheduler().runTaskTimer(this, () -> {
            int time = (int) world.getTime();

            if (time <= 12999) {
                addTime(world, day_add);
            }

            else {
                addTime(world, night_add);
            }
        }, 5, 5);
    }

    private void addTime(World world, int amount){
        world.setTime(world.getTime() + amount);
    }

}

