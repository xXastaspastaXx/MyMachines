package me.xxastaspastaxx.mymachines.machines.Teleporter;

import java.io.File;
import java.util.Random;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.Sign;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Damageable;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.player.PlayerInteractAtEntityEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.RegisteredServiceProvider;

import net.milkbowl.vault.Vault;
import net.milkbowl.vault.economy.Economy;

public class Teleporter implements Listener {
	
	  private Economy economy;
	
	  public Teleporter(Plugin pl) {

		  	Bukkit.getScheduler().scheduleSyncRepeatingTask(pl, new Runnable() {
		    	
				public void run()
		          {
					
					for (org.bukkit.World worlds : Bukkit.getServer().getWorlds()) {
					
					for (Entity entity : worlds.getEntities()) {
					
						if (entity.getType().equals(EntityType.ARMOR_STAND) && entity.getCustomName().contains("§6TheThing")) {
							 Location tloc = new Location(entity.getWorld(), entity.getLocation().getX()-0.5, entity.getLocation().getY()+2.3, entity.getLocation().getZ()-0.5);
							TestIfBroken(entity, tloc);
							
						}
						
						
						
						
						if (entity.getType().equals(EntityType.ARMOR_STAND) && entity.getCustomName().contains("§6TeleporterMachine")) {
							  Location rloc = new Location(entity.getWorld(), entity.getLocation().getX()-0.5, entity.getLocation().getY()+0.7, entity.getLocation().getZ()-0.5);
								 
							TestIfBroken(entity, rloc);
							
							RotateMachine(entity);
						}
					}
					}
					
					

		          }
		        }, 0, 0);
		  
		  
		  	
		    if ((Bukkit.getPluginManager().getPlugin("Vault") instanceof Vault))
		    {
		      RegisteredServiceProvider<Economy> service = Bukkit.getServicesManager().getRegistration(Economy.class);
		      if (service != null) {
		        economy = ((Economy)service.getProvider());
		      }
		    }
		  
		    Bukkit.getServer().getPluginManager().registerEvents(this, pl);
	  	}

	  public void RotateMachine(Entity en) {
		  
		  Location rloc = new Location(en.getWorld(), en.getLocation().getX(), en.getLocation().getY(), en.getLocation().getZ(), en.getLocation().getYaw()+0.1f, en.getLocation().getPitch());
		  
		  
		  
		  en.teleport(rloc);
		  
		  
	  }
	  
	  
	  
	  
	  
	  @EventHandler
	  public void onInteract(PlayerInteractAtEntityEvent e) {
		  Player p = e.getPlayer();

		  if ((e.getRightClicked().getType().equals(EntityType.ARMOR_STAND) && e.getRightClicked().getCustomName().contains("§6TeleporterMachine"))){

				e.setCancelled(true);
			  
		  }
		  
		  if ((e.getRightClicked().getType().equals(EntityType.ARMOR_STAND) && e.getRightClicked().getCustomName().contains("§6TheThing"))){

				String[] name = e.getRightClicked().getCustomName().split(", ");
				String owner = name[1];
			  
			  if (p.getName().contentEquals(owner)) {
				  
				  TeleporterGui.show(p, e.getRightClicked());
			  }
			  
			  
				e.setCancelled(true);
			  
		  }
		  
		  
	  }
	  
	  
	  
	  
	  
	  
	  @EventHandler
	  public void onBlockPlace(BlockPlaceEvent e) {
		  Player p = e.getPlayer();
		  Block b = e.getBlock();
		  
		  

		  
		  if (b.getType() == Material.REDSTONE_TORCH_ON) {
				
			  if (b.getRelative(BlockFace.DOWN).getType() == Material.EMERALD_BLOCK &&
					  (b.getRelative(BlockFace.DOWN).getRelative(BlockFace.EAST).getType() == Material.WALL_SIGN && ((Sign) b.getRelative(BlockFace.DOWN).getRelative(BlockFace.EAST).getState()).getLine(0).contentEquals("===============")&& ((Sign) b.getRelative(BlockFace.DOWN).getRelative(BlockFace.EAST).getState()).getLine(3).contentEquals("==============="))&&
					  (b.getRelative(BlockFace.DOWN).getRelative(BlockFace.WEST).getType() == Material.WALL_SIGN && ((Sign) b.getRelative(BlockFace.DOWN).getRelative(BlockFace.WEST).getState()).getLine(0).contentEquals("===============")&& ((Sign) b.getRelative(BlockFace.DOWN).getRelative(BlockFace.WEST).getState()).getLine(3).contentEquals("==============="))&&
					  (b.getRelative(BlockFace.DOWN).getRelative(BlockFace.NORTH).getType() == Material.WALL_SIGN && ((Sign) b.getRelative(BlockFace.DOWN).getRelative(BlockFace.NORTH).getState()).getLine(0).contentEquals("===============")&& ((Sign) b.getRelative(BlockFace.DOWN).getRelative(BlockFace.NORTH).getState()).getLine(3).contentEquals("==============="))&&
					  (b.getRelative(BlockFace.DOWN).getRelative(BlockFace.SOUTH).getType() == Material.WALL_SIGN && ((Sign) b.getRelative(BlockFace.DOWN).getRelative(BlockFace.SOUTH).getState()).getLine(0).contentEquals("===============")&& ((Sign) b.getRelative(BlockFace.DOWN).getRelative(BlockFace.SOUTH).getState()).getLine(3).contentEquals("==============="))&&
					  b.getRelative(BlockFace.DOWN, 2).getType() == Material.END_ROD&&
					  b.getRelative(BlockFace.DOWN, 3).getType() == Material.END_ROD&&
					  b.getRelative(BlockFace.DOWN, 4).getType() == Material.REDSTONE_BLOCK&&
					  b.getRelative(BlockFace.DOWN, 4).getRelative(BlockFace.EAST).getType() == Material.REDSTONE_TORCH_OFF&&
					  b.getRelative(BlockFace.DOWN, 4).getRelative(BlockFace.WEST).getType() == Material.REDSTONE_TORCH_OFF&&
					  b.getRelative(BlockFace.DOWN, 4).getRelative(BlockFace.NORTH).getType() == Material.REDSTONE_TORCH_OFF&&
					  b.getRelative(BlockFace.DOWN, 4).getRelative(BlockFace.SOUTH).getType() == Material.REDSTONE_TORCH_OFF
					  ) {
				  
				  
				  
					File tms = new File("plugins/MyMachines/Machines/TeleporterMachine/Settings.yml");
					YamlConfiguration tmsf = YamlConfiguration.loadConfiguration(tms);
				  
				  if (tmsf.getString("TeleportMachine.Permission.Enable").equalsIgnoreCase("true")) {
					  
					  if (p.hasPermission(tmsf.getString("TeleportMachine.Permission.Node"))) {
						  
						  if (tmsf.getString("TeleportMachine.Charge.Enable").equalsIgnoreCase("true")) {
							  
							  int mon = Integer.parseInt(tmsf.getString("TeleportMachine.Charge.Ammount"));
							  
							  if (economy.getBalance(p) >= mon) {
								  economy.withdrawPlayer(p, mon);
								  PlaceMachine(p, b);
								  
								  
							  } else {
								  
								  p.sendMessage(tmsf.getString("TeleportMachine.Charge.Message.NotEnoughMoney").replace("&", "§").replace("{player}", p.getName()).replace("{ammount}", mon+"").replace("{pmoney}", ""+economy.getBalance(p)));
								  
							  }
							  
							  
						  } else {
							  
							  PlaceMachine(p, b);
							  
						  }
						  
					  } else {
						  
						  p.sendMessage(tmsf.getString("TeleportMachine.Permission.Message.NoPermission").replace("&", "§").replace("{player}", p.getName()).replace("{permission}", tmsf.getString("TeleportMachine.Permission.Node")));
						  
					  }
					  
				  } else {
					  
					  if (tmsf.getString("TeleportMachine.Charge.Enable").equalsIgnoreCase("true")) {
						  
						  int mon = Integer.parseInt(tmsf.getString("TeleportMachine.Charge.Ammount"));
						  
						  if (economy.getBalance(p) >= mon) {
							  economy.withdrawPlayer(p, mon);
							  PlaceMachine(p, b);
							  
							  
						  } else {
							  
							  p.sendMessage(tmsf.getString("TeleportMachine.Charge.Message.NotEnoughMoney").replace("&", "§").replace("{player}", p.getName()).replace("{ammount}", mon+"").replace("{pmoney}", ""+economy.getBalance(p)));
							  
						  }
						  
						  
					  } else {
						  
						  PlaceMachine(p, b);
						  
					  }
					  
				  }
				 
				  
				  
			  }
			  
			  
		  }
		  
		  
		  
	  }
	  
	  
	  public void TestIfBroken(Entity en, Location rloc) {

			  if (!(rloc.getBlock().getType() == Material.REDSTONE_TORCH_ON&&
					  rloc.getBlock().getRelative(BlockFace.DOWN).getType() == Material.EMERALD_BLOCK &&
					  (rloc.getBlock().getRelative(BlockFace.DOWN).getRelative(BlockFace.EAST).getType() == Material.WALL_SIGN && ((Sign) rloc.getBlock().getRelative(BlockFace.DOWN).getRelative(BlockFace.EAST).getState()).getLine(0).contentEquals("===============")&& ((Sign) rloc.getBlock().getRelative(BlockFace.DOWN).getRelative(BlockFace.EAST).getState()).getLine(3).contentEquals("==============="))&&
					  (rloc.getBlock().getRelative(BlockFace.DOWN).getRelative(BlockFace.WEST).getType() == Material.WALL_SIGN && ((Sign) rloc.getBlock().getRelative(BlockFace.DOWN).getRelative(BlockFace.WEST).getState()).getLine(0).contentEquals("===============")&& ((Sign) rloc.getBlock().getRelative(BlockFace.DOWN).getRelative(BlockFace.WEST).getState()).getLine(3).contentEquals("==============="))&&
					  (rloc.getBlock().getRelative(BlockFace.DOWN).getRelative(BlockFace.NORTH).getType() == Material.WALL_SIGN && ((Sign) rloc.getBlock().getRelative(BlockFace.DOWN).getRelative(BlockFace.NORTH).getState()).getLine(0).contentEquals("===============")&& ((Sign) rloc.getBlock().getRelative(BlockFace.DOWN).getRelative(BlockFace.NORTH).getState()).getLine(3).contentEquals("==============="))&&
					  (rloc.getBlock().getRelative(BlockFace.DOWN).getRelative(BlockFace.SOUTH).getType() == Material.WALL_SIGN && ((Sign) rloc.getBlock().getRelative(BlockFace.DOWN).getRelative(BlockFace.SOUTH).getState()).getLine(0).contentEquals("===============")&& ((Sign) rloc.getBlock().getRelative(BlockFace.DOWN).getRelative(BlockFace.SOUTH).getState()).getLine(3).contentEquals("==============="))&&
					  rloc.getBlock().getRelative(BlockFace.DOWN, 2).getType() == Material.END_ROD&&
					  rloc.getBlock().getRelative(BlockFace.DOWN, 3).getType() == Material.END_ROD&&
					  rloc.getBlock().getRelative(BlockFace.DOWN, 4).getType() == Material.REDSTONE_BLOCK&&
					  rloc.getBlock().getRelative(BlockFace.DOWN, 4).getRelative(BlockFace.EAST).getType() == Material.REDSTONE_TORCH_OFF&&
					  rloc.getBlock().getRelative(BlockFace.DOWN, 4).getRelative(BlockFace.WEST).getType() == Material.REDSTONE_TORCH_OFF&&
					  rloc.getBlock().getRelative(BlockFace.DOWN, 4).getRelative(BlockFace.NORTH).getType() == Material.REDSTONE_TORCH_OFF&&
					  rloc.getBlock().getRelative(BlockFace.DOWN, 4).getRelative(BlockFace.SOUTH).getType() == Material.REDSTONE_TORCH_OFF
					  )) {
				  
				  
				  ((Damageable) en).setHealth(0);
				  
			  }
		  
	  }
	  
	  
	  public void PlaceMachine(Player p, Block b) {
		  
		  
		  Sign sign1 = (Sign) b.getRelative(BlockFace.DOWN).getRelative(BlockFace.EAST).getState();
		  Sign sign2 = (Sign) b.getRelative(BlockFace.DOWN).getRelative(BlockFace.WEST).getState();
		  Sign sign3 = (Sign) b.getRelative(BlockFace.DOWN).getRelative(BlockFace.NORTH).getState();
		  Sign sign4 = (Sign) b.getRelative(BlockFace.DOWN).getRelative(BlockFace.SOUTH).getState();

		  if (((sign1.getLine(1).contains("Home")||sign1.getLine(1).contains("Location"))&&sign2.getLine(1).contains("Home")||sign2.getLine(1).contains("Location")&&sign3.getLine(1).contains("Home")||sign3.getLine(1).contains("Location")&&sign4.getLine(1).contains("Home")||sign4.getLine(1).contains("Location")) && ((sign1.getLine(2).equalsIgnoreCase(sign2.getLine(2))&&sign1.getLine(2).equalsIgnoreCase(sign3.getLine(2))&&sign1.getLine(2).equalsIgnoreCase(sign3.getLine(2))&&sign1.getLine(2).equalsIgnoreCase(sign4.getLine(2))  ))) {
			  
			  
			  Location tloc = new Location(b.getWorld(), b.getLocation().getX()+0.5, b.getLocation().getY()-2.3, b.getLocation().getZ()+0.5);
			  
			  ArmorStand thething = (ArmorStand) p.getWorld().spawnEntity(tloc, EntityType.ARMOR_STAND);
			  
			  thething.setCustomName("§6TheThing, "+p.getName());
			  thething.setCustomNameVisible(false);
			  thething.setGravity(false);
			  thething.setInvulnerable(false);
			  thething.setVisible(false);

			  
			  Location sloc = new Location(b.getWorld(), b.getLocation().getX()+0.5, b.getLocation().getY()-0.7, b.getLocation().getZ()+0.5);
			  
			  ArmorStand stand = (ArmorStand) p.getWorld().spawnEntity(sloc, EntityType.ARMOR_STAND);
			  
			  stand.setCustomName("§6TeleporterMachine, "+p.getName()+", "+sign1.getLine(1)+", "+sign1.getLine(2));
			  ItemStack obsidian = new ItemStack(Material.REDSTONE_BLOCK, 1);
			  obsidian.addUnsafeEnchantment(Enchantment.DURABILITY, 1);
			  stand.setHelmet(obsidian);
			  stand.setCustomNameVisible(false);
			  stand.setGravity(false);
			  stand.setInvulnerable(false);
			  stand.setVisible(false);

			  
			  
			  
				File pmm = new File("plugins/MyMachines/Machines/TeleporterMachine/Messages.yml");
				YamlConfiguration pmmf = YamlConfiguration.loadConfiguration(pmm);
				
				if (pmmf.getString("CreateMachine.Enable").equalsIgnoreCase("true")) {

					p.sendMessage(pmmf.getString("CreateMachine.Message").replace("&", "§").replace("{player}", p.getName()));
					
				}

		  } else {
			  
			  
			  p.sendMessage("§cSign input is false");
			  p.sendMessage("§c");
			  p.sendMessage("§c===============");
			  p.sendMessage("§c<Home/Location>");
			  p.sendMessage("§c<LocationName>");
			  p.sendMessage("§c===============");
			  
			  
			  
		  }
	  }
	  
	  
	  
	  
	  
	  
	  
	//-----------------------------------------------------------------------------------------------\\
	
    public int getRandom(int lower, int upper) {
        Random random = new Random();
        return random.nextInt((upper - lower) + 1) + lower;
    }

	

}