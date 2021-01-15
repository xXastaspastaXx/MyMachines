package me.xxastaspastaxx.mymachines;

import java.util.Random;

import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.plugin.Plugin;

public class Listeners implements Listener {
	
	  public Listeners(Plugin pl) {
		  
		  
		  Bukkit.getScheduler().scheduleSyncRepeatingTask(pl, new Runnable() {
		    	
				public void run()
		          {


		          }
		        }, 0, 0);
		  
		  
		  
		  
		  
		  
		    Bukkit.getServer().getPluginManager().registerEvents(this, pl);
		  }

	
	@EventHandler
	public void onBlockBreak(BlockBreakEvent e) {
	//	Player p = e.getPlayer();
	//	Block b = e.getBlock();
		

			
	}
	
	
    public int getRandom(int lower, int upper) {
        Random random = new Random();
        return random.nextInt((upper - lower) + 1) + lower;
    }
	
}
