package me.spike.worldtimemodifier;

import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.Objects;

public final class WorldTimeModifier extends JavaPlugin {
    private final FileConfiguration configuration = getConfig();
    private int night_add;
    private int day_add;

    @Override
    public void onEnable() {
        saveDefaultConfig();
        World world = Bukkit.getWorld(Objects.requireNonNull(configuration.getString("world")));

        night_add = (int) 11000 / configuration.getInt("NightLength");
        day_add = (int) 12999 / configuration.getInt("DayLength");
        setTime(world);
    }

    private void setTime (World world){
        new BukkitRunnable(){

            @Override
            public void run() {
                int time = (int) world.getTime();
                if (time <= 12999)
                    addTime(world, day_add);
                else
                    addTime(world, night_add);
            }
        }.runTaskTimer(this, 0,5);
    }

    private void addTime(World world, int amount){
        world.setTime(world.getTime() + amount);
    }

}

