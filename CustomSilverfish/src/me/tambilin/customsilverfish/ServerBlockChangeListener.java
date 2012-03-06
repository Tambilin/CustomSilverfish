package me.tambilin.customsilverfish;

import java.util.Scanner;
import org.bukkit.block.*;
import java.util.Random;
//import net.minecraft.server.ItemStack;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Server;
import org.bukkit.World;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.ItemStack;

/**
 * CustomSilverfish Project.
 * ServerBlockChangeListener.class
 * - Decode the config data and handle silverfish spawning on the preset blocks.
 * @author Tambilin
 */
public class ServerBlockChangeListener implements Listener {

    public static CustomSilverfish plugin;
    private Server server;
    String configString;
    Boolean configBoolean;
    int[] blocks = new int[100];
    int[] percentages = new int[100];
    int[] numbers = new int[100];

    public ServerBlockChangeListener(CustomSilverfish instance, String str, Boolean perm) {
        plugin = instance;
        configString = str;
        configBoolean = perm;
        loadBlockData();
    }

    public void loadBlockData() {
        int index = 0;
        final String GROUP_DELIMITER = "#";
        final String BLOCKDATA_DELIMITER = "/";
        Scanner fullStringScan = null;
        Scanner blockStringScan = null;
        fullStringScan = new Scanner(configString);
        fullStringScan.useDelimiter(GROUP_DELIMITER);
        while (fullStringScan.hasNext()) {
            String nextName = fullStringScan.next().trim();
            blockStringScan = new Scanner(nextName);
            blockStringScan.useDelimiter(BLOCKDATA_DELIMITER);
            String blockString = blockStringScan.next().trim();
            int block = Integer.parseInt(blockString);
            blocks[index] = block;
            String percentageString = blockStringScan.next().trim();
            int percentage = Integer.parseInt(percentageString);
            percentages[index] = percentage;
            String numberString = blockStringScan.next().trim();
            int number = Integer.parseInt(numberString);
            numbers[index] = number;
            index++;
        }
    }

    @EventHandler(priority = EventPriority.HIGH)
    public void onBlockBreak(BlockBreakEvent event) {
        Block block = event.getBlock();
        Player p = event.getPlayer();
        if (p.hasPermission("customsilverfish-spawn.allow") == true || configBoolean == false) {
            for (int i = 0; i < blocks.length; i++) {
                if (block.getType() == Material.getMaterial(blocks[i])) {
                    Random generator = new Random();
                    int randomIndex = 1 + generator.nextInt(100);
                    if (randomIndex <= percentages[i]) {
                        ItemStack m = new ItemStack(block.getType(), 1);
                        block.setType(Material.AIR);
                        int x = block.getX();
                        int y = block.getY();
                        int z = block.getZ();
                        World world = block.getWorld();
                        Location loc = new Location(world, x, y, z);
                        world.dropItem(loc, m);
                        world.spawnCreature(loc, EntityType.SILVERFISH);
                        if (numbers[i] == 2) {
                            world.spawnCreature(loc, EntityType.SILVERFISH);
                        }
                        if (numbers[i] == 3) {
                            world.spawnCreature(loc, EntityType.SILVERFISH);
                            world.spawnCreature(loc, EntityType.SILVERFISH);
                        }
                        if (numbers[i] == 4) {
                            world.spawnCreature(loc, EntityType.SILVERFISH);
                            world.spawnCreature(loc, EntityType.SILVERFISH);
                            world.spawnCreature(loc, EntityType.SILVERFISH);
                        }
                        if (numbers[i] == 5) {
                            world.spawnCreature(loc, EntityType.SILVERFISH);
                            world.spawnCreature(loc, EntityType.SILVERFISH);
                            world.spawnCreature(loc, EntityType.SILVERFISH);
                            world.spawnCreature(loc, EntityType.SILVERFISH);
                        }
                    }
                }
            }
        }
    }
}
