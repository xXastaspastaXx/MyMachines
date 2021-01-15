package me.xxastaspastaxx.mymachines.machines.EnergyKiller;

import java.io.File;
import java.util.Random;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Damageable;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.player.PlayerInteractAtEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.RegisteredServiceProvider;

import net.milkbowl.vault.Vault;
import net.milkbowl.vault.economy.Economy;

public class EnergyKiller implements Listener {
	
	  private Economy economy;
	
	  public EnergyKiller(Plugin pl) {

		  	Bukkit.getScheduler().scheduleSyncRepeatingTask(pl, new Runnable() {
		    	
				public void run()
		          {
					
					for (org.bukkit.World worlds : Bukkit.getServer().getWorlds()) {
						
					for (Entity entity : worlds.getEntities()) {
						
						
						if (entity.getType().equals(EntityType.ARMOR_STAND) && entity.getCustomName().contains("§6EnergyKillerMachine")) {
						TestIfBroken(entity);

						}
					}
					}
					
					
					
					
		          }
		        }, 0, 0);
		  	
		  	
		  	
		  	Bukkit.getScheduler().scheduleSyncRepeatingTask(pl, new Runnable() {
		    	
				public void run()
		          {
					
					for (org.bukkit.World worlds : Bukkit.getServer().getWorlds()) {
						
					for (Entity entity : worlds.getEntities()) {
						
						
						if (entity.getType().equals(EntityType.ARMOR_STAND) && entity.getCustomName().contains("§6EnergyKillerMachine")) {

						
						String[] words = entity.getCustomName().split(", ");
						
						String owner = words[1];
							
							UseMachine(entity, owner);
						}
					}
					}
					
					
					
					
		          }
		        }, 600*20, 600*20);
		  
		  
		  	
		    if ((Bukkit.getPluginManager().getPlugin("Vault") instanceof Vault))
		    {
		      RegisteredServiceProvider<Economy> service = Bukkit.getServicesManager().getRegistration(Economy.class);
		      if (service != null) {
		        economy = ((Economy)service.getProvider());
		      }
		    }
		  
		    Bukkit.getServer().getPluginManager().registerEvents(this, pl);
	  	}

	
	  @EventHandler
	  public void onInteract(PlayerInteractAtEntityEvent e) {

		  if ((e.getRightClicked().getType().equals(EntityType.ARMOR_STAND) && e.getRightClicked().getCustomName().contains("§6EnergyKillerMachine"))){

				e.setCancelled(true);
			  
		  }
		  
		  
		  
	  }
	  
	  
	  @SuppressWarnings("deprecation")
	@EventHandler
	  public void onInteract(PlayerInteractEvent e) {
		  
		  Player p =e.getPlayer();

		  if (e.getAction().equals(Action.RIGHT_CLICK_BLOCK)) {
			  Block b = e.getClickedBlock();

		  if (b.getType() == Material.STAINED_GLASS && b.getState().getRawData() == 0) {

		  Location l = new Location(b.getWorld(), b.getLocation().getX(), b.getLocation().getY()-1, b.getLocation().getZ());
  
			  if (l.getBlock().getType() == Material.ENCHANTMENT_TABLE&&
					  (l.getBlock().getRelative(BlockFace.UP).getType() == Material.AIR||l.getBlock().getRelative(BlockFace.UP).getType() == Material.STAINED_GLASS)&&
					  (l.getBlock().getRelative(BlockFace.WEST, 2).getType() == Material.END_ROD && l.getBlock().getRelative(BlockFace.WEST, 2).getState().getRawData() == 1)&&
					  (l.getBlock().getRelative(BlockFace.EAST, 2).getType() == Material.END_ROD && l.getBlock().getRelative(BlockFace.EAST, 2).getState().getRawData() == 1)&&
					  (l.getBlock().getRelative(BlockFace.SOUTH, 2).getType() == Material.END_ROD && l.getBlock().getRelative(BlockFace.SOUTH, 2).getState().getRawData() == 1)&&
					  (l.getBlock().getRelative(BlockFace.NORTH, 2).getType() == Material.END_ROD && l.getBlock().getRelative(BlockFace.NORTH, 2).getState().getRawData() == 1)&&
					  (l.getBlock().getRelative(BlockFace.UP).getRelative(BlockFace.EAST, 2).getType() == Material.SMOOTH_STAIRS && l.getBlock().getRelative(BlockFace.UP).getRelative(BlockFace.EAST, 2).getState().getRawData() == 1)&&
					  (l.getBlock().getRelative(BlockFace.UP).getRelative(BlockFace.WEST, 2).getType() == Material.SMOOTH_STAIRS && l.getBlock().getRelative(BlockFace.UP).getRelative(BlockFace.WEST, 2).getState().getRawData() == 0)&&
					  (l.getBlock().getRelative(BlockFace.UP).getRelative(BlockFace.SOUTH, 2).getType() == Material.SMOOTH_STAIRS && l.getBlock().getRelative(BlockFace.UP).getRelative(BlockFace.SOUTH, 2).getState().getRawData() == 3)&&
					  (l.getBlock().getRelative(BlockFace.UP).getRelative(BlockFace.NORTH, 2).getType() == Material.SMOOTH_STAIRS && l.getBlock().getRelative(BlockFace.UP).getRelative(BlockFace.NORTH, 2).getState().getRawData() == 2)&&
					  (l.getBlock().getRelative(BlockFace.UP).getRelative(BlockFace.WEST).getType() == Material.END_ROD && l.getBlock().getRelative(BlockFace.UP).getRelative(BlockFace.WEST).getState().getRawData() == 5)&&
					  (l.getBlock().getRelative(BlockFace.UP).getRelative(BlockFace.SOUTH).getType() == Material.END_ROD && l.getBlock().getRelative(BlockFace.UP).getRelative(BlockFace.SOUTH).getState().getRawData() == 2)&&
				      (l.getBlock().getRelative(BlockFace.UP).getRelative(BlockFace.EAST).getType() == Material.END_ROD && l.getBlock().getRelative(BlockFace.UP).getRelative(BlockFace.EAST).getState().getRawData() == 4)&&
					  (l.getBlock().getRelative(BlockFace.UP).getRelative(BlockFace.NORTH).getType() == Material.END_ROD && l.getBlock().getRelative(BlockFace.UP).getRelative(BlockFace.NORTH).getState().getRawData() == 3)) {

				  for (Entity en : b.getWorld().getEntities()) {
					  Location enl = new Location(b.getWorld(), b.getLocation().getX()+0.5, b.getLocation().getY()-1.2, b.getLocation().getZ()+0.5);
						  if ((en.getType().equals(EntityType.ARMOR_STAND) && en.getCustomName().contains("§6EnergyKillerMachine"))){

							  if (en.getLocation().equals(enl)) {
							
							b.setType(Material.AIR);
							
							GiveRewards(p);
							
						}
						  
						  
					  }
					  
				  }
				
				  
				
				  
				  
			  }
			  
			  
			  
		  }
	  }
		  
		  
		  
	  }
	  
	  
	  
	  
	  
	  
	  public void GiveRewards(Player p) {
			File ekms = new File("plugins/MyMachines/Machines/EnergyKillerMachine/Settings.yml");
			YamlConfiguration ekmsf = YamlConfiguration.loadConfiguration(ekms);
		  
		  
		  for (String cmd : ekmsf.getStringList("EnergyKillerMachine.ReceiveRewards.Commands")) {
			  
			  
			  Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), cmd.replace("&", "§").replace("{player}", p.getName()));
			  
			  
			  
		  }
		  
			File ekmm = new File("plugins/MyMachines/Machines/EnergyKillerMachine/Messages.yml");
			YamlConfiguration ekmmf = YamlConfiguration.loadConfiguration(ekmm);
			
			
			if (ekmmf.getString("RewardsReceived.Enable").equalsIgnoreCase("true")) {
				
				p.sendMessage(ekmmf.getString("RewardsReceived.Message").replace("&", "§").replace("{player}", p.getName()));
				
				
			}
		  
		  
		  
			
			if (ekmsf.getString("EnergyKillerMachine.ReceiveRewards.Lucky.Enable").equalsIgnoreCase("true")) {
				
				int mx = Integer.parseInt(ekmsf.getString("EnergyKillerMachine.ReceiveRewards.Lucky.MaxRewards"));
				
					
	
				for (int x=0;x<=(mx-1);x++) {
					int cm = getRandom(0, (ekmsf.getStringList("EnergyKillerMachine.ReceiveRewards.Lucky.Commands").size()-1));
					String m = ekmsf.getStringList("EnergyKillerMachine.ReceiveRewards.Lucky.Commands").get(cm);
				
					
						// [1-20
					
						// eco give {player} 1000
						
						
						
						String[] t = m.split("] ");
						
						String[] ch = t[0].replace("[", "").split("-");
						int sm= Integer.parseInt(ch[0]);
						int bg = Integer.parseInt(ch[1]);
						
						String cmd = t[1].replace("&", "§").replace("{player}", p.getName());
						
						int rn = getRandom(0,Integer.parseInt(ekmsf.getString("EnergyKillerMachine.ReceiveRewards.Lucky.MaxLuckyNumber")));
						
						
						if (rn >=sm && rn<=bg) {
							
							
							Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), cmd);
							if (ekmmf.getString("LuckyRewardsReceived.Enable").equalsIgnoreCase("true")) {
								
								p.sendMessage(ekmmf.getString("LuckyRewardsReceived.Message").replace("&", "§").replace("{player}", p.getName()));
								
							}
						}
						
						
					}
				
				
				
				
				

				
				
			}
			
			
		  
	  }
	  
	  
	  
	  
	  
	  @SuppressWarnings("deprecation")
	@EventHandler
	  public void onBlockPlace(BlockPlaceEvent e) {
		  Player p = e.getPlayer();
		  Block b = e.getBlock();
		  
		  
		  
		  
		  
		  
		  if (b.getType() == Material.ENCHANTMENT_TABLE) {
			  
			  if (b.getRelative(BlockFace.UP).getType() == Material.AIR&&
					  (b.getRelative(BlockFace.WEST, 2).getType() == Material.END_ROD && b.getRelative(BlockFace.WEST, 2).getState().getRawData() == 1)&&
					  (b.getRelative(BlockFace.EAST, 2).getType() == Material.END_ROD && b.getRelative(BlockFace.EAST, 2).getState().getRawData() == 1)&&
					  (b.getRelative(BlockFace.SOUTH, 2).getType() == Material.END_ROD && b.getRelative(BlockFace.SOUTH, 2).getState().getRawData() == 1)&&
					  (b.getRelative(BlockFace.NORTH, 2).getType() == Material.END_ROD && b.getRelative(BlockFace.NORTH, 2).getState().getRawData() == 1)&&
					  (b.getRelative(BlockFace.UP).getRelative(BlockFace.EAST, 2).getType() == Material.SMOOTH_STAIRS && b.getRelative(BlockFace.UP).getRelative(BlockFace.EAST, 2).getState().getRawData() == 1)&&
					  (b.getRelative(BlockFace.UP).getRelative(BlockFace.WEST, 2).getType() == Material.SMOOTH_STAIRS && b.getRelative(BlockFace.UP).getRelative(BlockFace.WEST, 2).getState().getRawData() == 0)&&
					  (b.getRelative(BlockFace.UP).getRelative(BlockFace.SOUTH, 2).getType() == Material.SMOOTH_STAIRS && b.getRelative(BlockFace.UP).getRelative(BlockFace.SOUTH, 2).getState().getRawData() == 3)&&
					  (b.getRelative(BlockFace.UP).getRelative(BlockFace.NORTH, 2).getType() == Material.SMOOTH_STAIRS && b.getRelative(BlockFace.UP).getRelative(BlockFace.NORTH, 2).getState().getRawData() == 2)&&
					  (b.getRelative(BlockFace.UP).getRelative(BlockFace.WEST).getType() == Material.END_ROD && b.getRelative(BlockFace.UP).getRelative(BlockFace.WEST).getState().getRawData() == 5)&&
					  (b.getRelative(BlockFace.UP).getRelative(BlockFace.SOUTH).getType() == Material.END_ROD && b.getRelative(BlockFace.UP).getRelative(BlockFace.SOUTH).getState().getRawData() == 2)&&
				      (b.getRelative(BlockFace.UP).getRelative(BlockFace.EAST).getType() == Material.END_ROD && b.getRelative(BlockFace.UP).getRelative(BlockFace.EAST).getState().getRawData() == 4)&&
					  (b.getRelative(BlockFace.UP).getRelative(BlockFace.NORTH).getType() == Material.END_ROD && b.getRelative(BlockFace.UP).getRelative(BlockFace.NORTH).getState().getRawData() == 3)) {

	    			File ekms = new File("plugins/MyMachines/Machines/EnergyKillerMachine/Settings.yml");
	    			YamlConfiguration ekmsf = YamlConfiguration.loadConfiguration(ekms);
				  
				  if (ekmsf.getString("EnergyKillerMachine.Permission.Enable").equalsIgnoreCase("true")) {
					  
					if (p.hasPermission(ekmsf.getString("EnergyKillerMachine.Permission.Node"))) {
						
						  if (ekmsf.getString("EnergyKillerMachine.Charge.Enable").equalsIgnoreCase("true")) {
							  int mon = Integer.parseInt(ekmsf.getString("EnergyKillerMachine.Charge.Ammount"));
							  
							  if (economy.getBalance(p) >= mon) {
								  economy.withdrawPlayer(p, mon);
								  PlaceMachine(p, b); 
								  
							  } else {
								  
								  p.sendMessage(ekmsf.getString("EnergyKillerMachine.Charge.Message.NotEnoughMoney").replace("&", "§").replace("{player}", p.getName()).replace("{ammount}", mon+"").replace("{pmoney}", ""+economy.getBalance(p)));
									
								  
							  }
									  
									  
							  
						  } else {
							  
							  PlaceMachine(p, b); 
							  
						  }
						
						
					} else {
						
						p.sendMessage(ekmsf.getString("EnergyKillerMachine.Permission.Message.NoPermission").replace("&", "§").replace("{player}", p.getName()).replace("{permission}", ekmsf.getString("EnergyKillerMachine.Permission.Node")));
						
					}
					  
				  } else {
					  
					  if (ekmsf.getString("EnergyKillerMachine.Charge.Enable").equalsIgnoreCase("true")) {
						  int mon = Integer.parseInt(ekmsf.getString("EnergyKillerMachine.Charge.Ammount"));
						  
						  if (economy.getBalance(p) >= mon) {
							  economy.withdrawPlayer(p, mon);
							  PlaceMachine(p, b); 
							  
						  } else {
							  
							  p.sendMessage(ekmsf.getString("EnergyKillerMachine.Charge.Message.NotEnoughMoney").replace("&", "§").replace("{player}", p.getName()).replace("{ammount}", mon+"").replace("{pmoney}", ""+economy.getBalance(p)));
								
							  
						  }
								  
								  
						  
					  } else {
						  
						  PlaceMachine(p, b); 
						  
					  }
					  
					  
				  }
				 
				  
				  
				  
				  
			  }
		  }
		  
	  }
	  
	  @SuppressWarnings("deprecation")
	public void TestIfBroken(Entity en) {
		  
		  Location b = new Location(en.getWorld(), en.getLocation().getX()-0.5, en.getLocation().getY()+0.2, en.getLocation().getZ()-0.5);

			  
			  if (!(b.getBlock().getType() == Material.ENCHANTMENT_TABLE&&
					  (b.getBlock().getRelative(BlockFace.UP).getType() == Material.AIR||b.getBlock().getRelative(BlockFace.UP).getType() == Material.STAINED_GLASS)&&
					  (b.getBlock().getRelative(BlockFace.WEST, 2).getType() == Material.END_ROD && b.getBlock().getRelative(BlockFace.WEST, 2).getState().getRawData() == 1)&&
					  (b.getBlock().getRelative(BlockFace.EAST, 2).getType() == Material.END_ROD && b.getBlock().getRelative(BlockFace.EAST, 2).getState().getRawData() == 1)&&
					  (b.getBlock().getRelative(BlockFace.SOUTH, 2).getType() == Material.END_ROD && b.getBlock().getRelative(BlockFace.SOUTH, 2).getState().getRawData() == 1)&&
					  (b.getBlock().getRelative(BlockFace.NORTH, 2).getType() == Material.END_ROD && b.getBlock().getRelative(BlockFace.NORTH, 2).getState().getRawData() == 1)&&
					  (b.getBlock().getRelative(BlockFace.UP).getRelative(BlockFace.EAST, 2).getType() == Material.SMOOTH_STAIRS && b.getBlock().getRelative(BlockFace.UP).getRelative(BlockFace.EAST, 2).getState().getRawData() == 1)&&
					  (b.getBlock().getRelative(BlockFace.UP).getRelative(BlockFace.WEST, 2).getType() == Material.SMOOTH_STAIRS && b.getBlock().getRelative(BlockFace.UP).getRelative(BlockFace.WEST, 2).getState().getRawData() == 0)&&
					  (b.getBlock().getRelative(BlockFace.UP).getRelative(BlockFace.SOUTH, 2).getType() == Material.SMOOTH_STAIRS && b.getBlock().getRelative(BlockFace.UP).getRelative(BlockFace.SOUTH, 2).getState().getRawData() == 3)&&
					  (b.getBlock().getRelative(BlockFace.UP).getRelative(BlockFace.NORTH, 2).getType() == Material.SMOOTH_STAIRS && b.getBlock().getRelative(BlockFace.UP).getRelative(BlockFace.NORTH, 2).getState().getRawData() == 2)&&
					  (b.getBlock().getRelative(BlockFace.UP).getRelative(BlockFace.WEST).getType() == Material.END_ROD && b.getBlock().getRelative(BlockFace.UP).getRelative(BlockFace.WEST).getState().getRawData() == 5)&&
					  (b.getBlock().getRelative(BlockFace.UP).getRelative(BlockFace.SOUTH).getType() == Material.END_ROD && b.getBlock().getRelative(BlockFace.UP).getRelative(BlockFace.SOUTH).getState().getRawData() == 2)&&
				      (b.getBlock().getRelative(BlockFace.UP).getRelative(BlockFace.EAST).getType() == Material.END_ROD && b.getBlock().getRelative(BlockFace.UP).getRelative(BlockFace.EAST).getState().getRawData() == 4)&&
					  (b.getBlock().getRelative(BlockFace.UP).getRelative(BlockFace.NORTH).getType() == Material.END_ROD && b.getBlock().getRelative(BlockFace.UP).getRelative(BlockFace.NORTH).getState().getRawData() == 3))) {

				  
				
				  
				  ((Damageable) en).setHealth(0);
				  
				  
			  }
		  
	  }
	  
	  
	  public void PlaceMachine(Player p, Block b) {
		  
		  Location sloc = new Location(b.getWorld(), b.getLocation().getX()+0.5, b.getLocation().getY()-0.2, b.getLocation().getZ()+0.5);
		  
		  ArmorStand stand = (ArmorStand) p.getWorld().spawnEntity(sloc, EntityType.ARMOR_STAND);
		  
		  stand.setCustomName("§6EnergyKillerMachine, "+p.getName());
		  ItemStack obsidian = new ItemStack(Material.OBSIDIAN, 1);
		  obsidian.addUnsafeEnchantment(Enchantment.DURABILITY, 1);
		  stand.setHelmet(obsidian);
		  stand.setCustomNameVisible(false);
		  stand.setGravity(false);
		  stand.setInvulnerable(true);
		  stand.setVisible(false);
		  
			File ekmm = new File("plugins/MyMachines/Machines/EnergyKillerMachine/Messages.yml");
			YamlConfiguration ekmmf = YamlConfiguration.loadConfiguration(ekmm);
			
			if (ekmmf.getString("CreateMachine.Enable").equalsIgnoreCase("true")) {
				
				
				p.sendMessage(ekmmf.getString("CreateMachine.Message").replace("&", "§").replace("{player}", p.getName()));
				
			}
		  
		  
	  }
	  
	  
	  
	  
	  @SuppressWarnings("deprecation")
	public void UseMachine(Entity en, String owner) {
		  
		  
		  Location b = new Location(en.getWorld(), en.getLocation().getX()-0.5, en.getLocation().getY()+2, en.getLocation().getZ()-0.5);
		  
		  
		  if (b.getBlock().getType() == Material.AIR || b.getBlock().getType() == Material.STAINED_GLASS) {
			  
			  if (b.getBlock().getType() == Material.STAINED_GLASS) {
				  
				  if (b.getBlock().getState().getRawData() == 15) {
					  
					  Block oblock = b.getBlock();
					  
						 Block nblock = oblock;
						 nblock.setType(Material.STAINED_GLASS);
						  nblock.setData((byte) 7);
						  
						  oblock = nblock;
					  
					  
				  } else if(b.getBlock().getState().getRawData() == 7) {
					  
					  
					  Block oblock = b.getBlock();
					  
						 Block nblock = oblock;
						 nblock.setType(Material.STAINED_GLASS);
						  nblock.setData((byte) 8);
						  
						  oblock = nblock;
					  
					  
					  
				  } else if(b.getBlock().getState().getRawData() == 8) {
					  
					  
					  Block oblock = b.getBlock();
					  
						 Block nblock = oblock;
						 nblock.setType(Material.STAINED_GLASS);
						  nblock.setData((byte) 0);
						  
						  oblock = nblock;
					  
					  
					  
				  } else if(b.getBlock().getState().getRawData() == 0) {

				  }
				  
				  
				  
			  } else {
				  
				  Block oblock = b.getBlock();
				  
				 Block nblock = oblock;
				 nblock.setType(Material.STAINED_GLASS);
				  nblock.setData((byte) 15);
				  
				  oblock = nblock;
				  
				  
			  }
			  
			  
		  }
		  
		  
		  
	  }
	  
	  
	  
	  
	  
	//-----------------------------------------------------------------------------------------------\\
	
    public int getRandom(int lower, int upper) {
        Random random = new Random();
        return random.nextInt((upper - lower) + 1) + lower;
    }

	

}