package me.xxastaspastaxx.mymachines.machines.Protection;

import java.io.File;
import java.util.List;
import java.util.Random;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Damageable;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.player.PlayerInteractAtEntityEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.RegisteredServiceProvider;

import net.milkbowl.vault.Vault;
import net.milkbowl.vault.economy.Economy;

public class Protection implements Listener {
	
	  private Economy economy;
	
	  public Protection(Plugin pl) {

		  	Bukkit.getScheduler().scheduleSyncRepeatingTask(pl, new Runnable() {
		    	
				public void run()
		          {
					
				 
					
					for (org.bukkit.World worlds : Bukkit.getServer().getWorlds()) {
					
					for (Entity entity : worlds.getEntities()) {
						
						
						if (entity.getType().equals(EntityType.ARMOR_STAND) && entity.getCustomName().contains("§6ProtectionMachine")) {
						TestIfBroken(entity);
							//§6ProtectionMachine, xXastaspastaXx, 10
							
							String[] words = entity.getCustomName().split(", ");
							
							String owner = words[1];
							int lvl = Integer.parseInt(words[2]);
							
							
							File exep = new File("plugins/MyMachines/PlayerData/"+owner+"/ProtectionMachines.yml");
							YamlConfiguration exepf = YamlConfiguration.loadConfiguration(exep);
							
							
							List<String> ignore = exepf.getStringList(entity.getLocation()+".Exeptions");
							TestForEntity(entity, lvl, owner, ignore);
							
						
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

	  
	  
	  @EventHandler
	  public void EntityvsEntity(EntityDamageByEntityEvent e) {
		  Entity mob = e.getEntity();
		  Entity dmgr = e.getDamager();
		  
		  if (mob.getName().contains("§6ProtectionMachine")) {
				File exep = new File("plugins/MyMachines/PlayerData/"+dmgr.getName()+"/ProtectionMachines.yml");
				YamlConfiguration exepf = YamlConfiguration.loadConfiguration(exep);
			  List<String> exeptions = exepf.getStringList(mob.getLocation()+".Exeptions");
				String[] words = mob.getCustomName().split(", ");
				
				String owner = words[1];
			  
			  if (dmgr instanceof Arrow) {
				Entity shooter =  (Entity) ((Arrow) dmgr).getShooter(); 
				 if (!shooter.getName().contentEquals(owner)) {
					 if (!exeptions.contains(shooter.getName())) {
					 int rn = getRandom(-10,10);
					 
						Location sloc = new Location(shooter.getWorld(), shooter.getLocation().getX()+rn, shooter.getLocation().getY(), shooter.getLocation().getZ()+rn);
						
						Entity zombie = shooter.getWorld().spawnEntity(sloc, EntityType.ZOMBIE);
						 
						zombie.setCustomName("§cProtector");
						zombie.setCustomNameVisible(true);
						
						
						((Damageable) zombie).damage(2, shooter);
				 	}
				 }
			  } else {
				  if (!dmgr.getName().contentEquals(owner)) {
					  if (!exeptions.contains(dmgr.getName())) {
						 int rn = getRandom(-10,10);
						 
							Location sloc = new Location(dmgr.getWorld(), dmgr.getLocation().getX()+rn, dmgr.getLocation().getY(), dmgr.getLocation().getZ()+rn);
							
							Entity zombie = dmgr.getWorld().spawnEntity(sloc, EntityType.ZOMBIE);
							 
							zombie.setCustomName("§cProtector");
							zombie.setCustomNameVisible(true);
							
							
							((Damageable) zombie).damage(2, dmgr);
				  	}
				  }
			  }
			  
			  
		  }
		  
		  
		  
	  }
	  
	  @EventHandler
	  public void onInteract(PlayerInteractAtEntityEvent e) {
		  Player p = e.getPlayer();

		  if ((e.getRightClicked().getType().equals(EntityType.ARMOR_STAND) && e.getRightClicked().getCustomName().contains("§5Nexus"))){
			  Entity entity = e.getRightClicked();
				Location underworld = new Location(p.getWorld(), 0, 0, 0); 
				entity.teleport(underworld);
				((ArmorStand) entity).setHealth(0);
				e.setCancelled(true);
			  
		  }
		  
					if (e.getRightClicked().getType().equals(EntityType.ARMOR_STAND) && e.getRightClicked().getCustomName().contains("§6ProtectionMachine")) {
						String[] words = e.getRightClicked().getCustomName().split(", ");
						
						String owner = words[1];
						if (p.getName().contentEquals(owner)) {
							ProtectionGui.show(p, e.getRightClicked());
						}
						
						e.setCancelled(true);
					
					}
				
			
	  	
		  
	  }

	  @EventHandler
	  public void onBlockPlace(BlockPlaceEvent e) {
		  Player p = e.getPlayer();
		  Block b = e.getBlock();
		  
		  
		  if (b.getType().equals(Material.OBSIDIAN)) {
			  
			  if (b.getLocation().getBlock().getRelative(BlockFace.DOWN).getType() == Material.OBSIDIAN&&b.getLocation().getBlock().getRelative(BlockFace.DOWN, 2).getType() == Material.IRON_BLOCK) {
			  Location sloc = new Location(b.getWorld(), b.getLocation().getX()+0.5, b.getLocation().getY()-0.7, b.getLocation().getZ()+0.5, 0, 0);
			  
			  PlaceNexus(p, sloc);
			  b.getLocation().getBlock().setType(Material.AIR);
			  }
		  }
		  
		  if (b.getType().equals(Material.EMERALD_BLOCK)) {
			 
			  Location sloc = new Location(b.getWorld(), b.getLocation().getX()+0.5, b.getLocation().getY()-0.7, b.getLocation().getZ()+0.5, 45, 0);
			  
				File pms = new File("plugins/MyMachines/Machines/ProtectionMachine/Settings.yml");
				YamlConfiguration pmsf = YamlConfiguration.loadConfiguration(pms);	
			  
				
				if (b.getLocation().getBlock().getRelative(BlockFace.DOWN).getType() == Material.DIAMOND_BLOCK &&
						b.getLocation().getBlock().getRelative(BlockFace.DOWN, 2).getType() == Material.IRON_BLOCK &&
						b.getLocation().getBlock().getRelative(BlockFace.DOWN, 2).getRelative(BlockFace.WEST).getRelative(BlockFace.NORTH).getType() == Material.OBSIDIAN &&
						b.getLocation().getBlock().getRelative(BlockFace.DOWN, 2).getRelative(BlockFace.EAST).getRelative(BlockFace.NORTH).getType() == Material.OBSIDIAN &&
						b.getLocation().getBlock().getRelative(BlockFace.DOWN, 2).getRelative(BlockFace.WEST).getRelative(BlockFace.SOUTH).getType() == Material.OBSIDIAN &&
						b.getLocation().getBlock().getRelative(BlockFace.DOWN, 2).getRelative(BlockFace.EAST).getRelative(BlockFace.SOUTH).getType() == Material.OBSIDIAN &&
						b.getLocation().getBlock().getRelative(BlockFace.DOWN).getRelative(BlockFace.WEST, 2).getType() == Material.OBSIDIAN &&
						b.getLocation().getBlock().getRelative(BlockFace.DOWN).getRelative(BlockFace.EAST, 2).getType() == Material.OBSIDIAN &&
						b.getLocation().getBlock().getRelative(BlockFace.DOWN).getRelative(BlockFace.SOUTH, 2).getType() == Material.OBSIDIAN &&
						b.getLocation().getBlock().getRelative(BlockFace.DOWN).getRelative(BlockFace.NORTH, 2).getType() == Material.OBSIDIAN &&
						b.getLocation().getBlock().getRelative(BlockFace.DOWN, 2).getRelative(BlockFace.WEST, 2).getType() == Material.IRON_BLOCK &&
						b.getLocation().getBlock().getRelative(BlockFace.DOWN, 2).getRelative(BlockFace.EAST, 2).getType() == Material.IRON_BLOCK &&
						b.getLocation().getBlock().getRelative(BlockFace.DOWN, 2).getRelative(BlockFace.SOUTH, 2).getType() == Material.IRON_BLOCK &&
						b.getLocation().getBlock().getRelative(BlockFace.DOWN, 2).getRelative(BlockFace.NORTH, 2).getType() == Material.IRON_BLOCK) {
					
					
					
					Location west = new Location(b.getWorld(), ((b.getLocation().getX()-2)+0.5), (b.getLocation().getY()-0.7), (b.getLocation().getZ()+0.5));
					Location east = new Location(b.getWorld(), ((b.getLocation().getX()+2)+0.5), (b.getLocation().getY()-0.7), (b.getLocation().getZ()+0.5));
					Location north = new Location(b.getWorld(), (b.getLocation().getX()+0.5), (b.getLocation().getY()-0.7), ((b.getLocation().getZ()-2)+0.5));
					Location south = new Location(b.getWorld(), (b.getLocation().getX()+0.5), (b.getLocation().getY()-0.7), ((b.getLocation().getZ()+2)+0.5));
					
					
				
				for (Entity entity : b.getWorld().getEntities()) {
					if ((entity.getLocation().equals(west) && entity.getType().equals(EntityType.ARMOR_STAND) && entity.getCustomName().contentEquals("§5Nexus"))) {
						for (Entity entity2 : b.getWorld().getEntities()) {
							if ((entity2.getLocation().equals(east) && entity2.getType().equals(EntityType.ARMOR_STAND) && entity2.getCustomName().contentEquals("§5Nexus"))) {
								for (Entity entity3 : b.getWorld().getEntities()) {
									if ((entity3.getLocation().equals(south) && entity3.getType().equals(EntityType.ARMOR_STAND) && entity3.getCustomName().contentEquals("§5Nexus"))) {
										for (Entity entity4 : b.getWorld().getEntities()) {
											if ((entity4.getLocation().equals(north) && entity4.getType().equals(EntityType.ARMOR_STAND) && entity4.getCustomName().contentEquals("§5Nexus"))) {
												
											if (pmsf.getString("ProtectionMachine.Enable").equalsIgnoreCase("true")) {

												if (pmsf.getString("ProtectionMachine.Permission.Enable").equalsIgnoreCase("true")) {
													String perm = pmsf.getString("ProtectionMachine.Permission.Node");
													if (p.hasPermission(perm)) {
														
														if (pmsf.getString("ProtectionMachine.Charge.Enable").equalsIgnoreCase("true")) {
															int mon = Integer.parseInt(pmsf.getString("ProtectionMachine.Charge.Ammount"));
															if (economy.getBalance(p) >= mon) {
																economy.withdrawPlayer(p, mon);
																b.getLocation().getBlock().setType(Material.AIR);
																PlaceMachine(p, sloc);
																
															} else {
																
																p.sendMessage(pmsf.getString("ProtectionMachine.Charge.Message.NotEnoughMoney").replace("&", "§").replace("{player}", p.getName()).replace("{ammount}", mon+"").replace("{pmoney}", ""+economy.getBalance(p)));
																
															}
															
														} else {
														b.getLocation().getBlock().setType(Material.AIR);
														PlaceMachine(p, sloc);
														}
														
													} else {
														
														p.sendMessage(pmsf.getString("ProtectionMachine.Permission.Message.NoPermission").replace("&", "§").replace("{player}", p.getName()).replace("{permission}", perm));
														
													}
													
												} else {
													
													
													if (pmsf.getString("ProtectionMachine.Charge.Enable").equalsIgnoreCase("true")) {
														int mon = Integer.parseInt(pmsf.getString("ProtectionMachine.Charge.Ammount"));
														if (economy.getBalance(p) >= mon) {
															
															b.getLocation().getBlock().setType(Material.AIR);
															PlaceMachine(p, sloc);
															
														} else {
															
															p.sendMessage(pmsf.getString("ProtectionMachine.Charge.Message.NotEnoughMoney").replace("&", "§").replace("{player}", p.getName()).replace("{ammount}", mon+"").replace("{pmoney}", ""+economy.getBalance(p)));
															
														}
														
													} else {
													b.getLocation().getBlock().setType(Material.AIR);
													PlaceMachine(p, sloc);
													}
													
												}
												
											}
											}
										}
									}
								}
							}
						}
					}
				}
			  
			  
				}
			  
		  }
		  
		  
	  }
	  
	  
	  public void TestIfBroken(Entity b) {

			  Location sloc = new Location(b.getWorld(), b.getLocation().getX()-0.5, b.getLocation().getY()+0.7, b.getLocation().getZ()-0.5, 0, 0);

				if (!(sloc.getBlock().getRelative(BlockFace.DOWN).getType() == Material.DIAMOND_BLOCK &&
						sloc.getBlock().getRelative(BlockFace.DOWN, 2).getType() == Material.IRON_BLOCK &&
						sloc.getBlock().getRelative(BlockFace.DOWN, 2).getRelative(BlockFace.WEST).getRelative(BlockFace.NORTH).getType() == Material.OBSIDIAN &&
						sloc.getBlock().getRelative(BlockFace.DOWN, 2).getRelative(BlockFace.EAST).getRelative(BlockFace.NORTH).getType() == Material.OBSIDIAN &&
						sloc.getBlock().getRelative(BlockFace.DOWN, 2).getRelative(BlockFace.WEST).getRelative(BlockFace.SOUTH).getType() == Material.OBSIDIAN &&
						sloc.getBlock().getRelative(BlockFace.DOWN, 2).getRelative(BlockFace.EAST).getRelative(BlockFace.SOUTH).getType() == Material.OBSIDIAN &&
						sloc.getBlock().getRelative(BlockFace.DOWN).getRelative(BlockFace.WEST, 2).getType() == Material.OBSIDIAN &&
						sloc.getBlock().getRelative(BlockFace.DOWN).getRelative(BlockFace.EAST, 2).getType() == Material.OBSIDIAN &&
						sloc.getBlock().getRelative(BlockFace.DOWN).getRelative(BlockFace.SOUTH, 2).getType() == Material.OBSIDIAN &&
						sloc.getBlock().getRelative(BlockFace.DOWN).getRelative(BlockFace.NORTH, 2).getType() == Material.OBSIDIAN &&
						sloc.getBlock().getRelative(BlockFace.DOWN, 2).getRelative(BlockFace.WEST, 2).getType() == Material.IRON_BLOCK &&
						sloc.getBlock().getRelative(BlockFace.DOWN, 2).getRelative(BlockFace.EAST, 2).getType() == Material.IRON_BLOCK &&
						sloc.getBlock().getRelative(BlockFace.DOWN, 2).getRelative(BlockFace.SOUTH, 2).getType() == Material.IRON_BLOCK &&
						sloc.getBlock().getRelative(BlockFace.DOWN, 2).getRelative(BlockFace.NORTH, 2).getType() == Material.IRON_BLOCK)) {
					

						
						  ((Damageable) b).setHealth(0);
						  sloc.getBlock().setType(Material.EMERALD_BLOCK);
						

					

				}
	  }
	  
	  
	  public void PlaceMachine(Player p, Location sloc) {
		  
		  ArmorStand stand = (ArmorStand) p.getWorld().spawnEntity(sloc, EntityType.ARMOR_STAND);
		  
		  stand.setCustomName("§6ProtectionMachine, "+p.getName()+", 1");
		  ItemStack obsidian = new ItemStack(Material.EMERALD_BLOCK, 1);
		  obsidian.addUnsafeEnchantment(Enchantment.DURABILITY, 1);
		  stand.setHelmet(obsidian);
		  stand.setCustomNameVisible(false);
		  stand.setGravity(false);
		  stand.setInvulnerable(true);
		  stand.setVisible(false);
		  
		  
			File pmm = new File("plugins/MyMachines/Machines/ProtectionMachine/Messages.yml");
			YamlConfiguration pmmf = YamlConfiguration.loadConfiguration(pmm);
			
			if (pmmf.getString("CreateMachine.Enable").equalsIgnoreCase("true")) {

				p.sendMessage(pmmf.getString("CreateMachine.Message").replace("&", "§").replace("{player}", p.getName()));
				
			}

		  
	  }
	  
	  
	  public void PlaceNexus(Player p, Location sloc) {
		  
		  ArmorStand stand = (ArmorStand) p.getWorld().spawnEntity(sloc, EntityType.ARMOR_STAND);
		  
		  stand.setCustomName("§5Nexus");
		  ItemStack obsidian = new ItemStack(Material.OBSIDIAN, 1);
		  obsidian.addUnsafeEnchantment(Enchantment.DURABILITY, 1);
		  stand.setHelmet(obsidian);
		  stand.setCustomNameVisible(false);
		  stand.setGravity(false);
		  stand.setInvulnerable(false);
		  stand.setVisible(false);
		  
		  
		  
			File pmm = new File("plugins/MyMachines/Machines/ProtectionMachine/Messages.yml");
			YamlConfiguration pmmf = YamlConfiguration.loadConfiguration(pmm);
			
			if (pmmf.getString("PlaceNexus.Enable").equalsIgnoreCase("true")) {

				p.sendMessage(pmmf.getString("PlaceNexus.Message").replace("&", "§").replace("{player}", p.getName()));
				
			}
		  
	  }



	public void TestForEntity(Entity machine, int level, String owner, List<String> exeptions) {
		File pms = new File("plugins/MyMachines/Machines/ProtectionMachine/Settings.yml");
		YamlConfiguration pmsf = YamlConfiguration.loadConfiguration(pms);	
			
		int sa = Integer.parseInt(pmsf.getString("ProtectionMachine.Starting.Area"));
		
		
		  int x = level*sa;
		  int y = level*sa;
		  int z = level*sa;
		  
		  double sdmg = Double.parseDouble(pmsf.getString("ProtectionMachine.Starting.Damage"));
		  double dmg = level*sdmg;
			
		  
		  List<Entity> entitylist = machine.getNearbyEntities(x, y, z);
		  for(int t=0;t<entitylist.size();t++){
			  for (Entity m : entitylist) {
				if (m instanceof Damageable) {  
				  //
					if (entitylist.size() == 0) {
						
						
						
					} else {
						
						
						
					}
					//
					
					
					  if (!m.getName().contentEquals(owner)) {
						
						  if (!exeptions.contains(m.getName())) {
							  
							  if (!m.getName().contains("§5Nexus")) {
								  
								  if (!m.getName().contains("§cProtector")) {
									  
									  
										File pmm = new File("plugins/MyMachines/Machines/ProtectionMachine/Messages.yml");
										YamlConfiguration pmmf = YamlConfiguration.loadConfiguration(pmm);
										
										if (pmmf.getString("AttackedByMachine.Enable").equalsIgnoreCase("true")) {
											if (m instanceof Player) {
											m.sendMessage(pmmf.getString("AttackedByMachine.Message").replace("&", "§").replace("{player}", m.getName()));
											}
										}
							  ((Damageable) m).damage(dmg, machine);
								  }
							  }
						  }
						  
						  
					  }
				  //
				}
			  }
		  }   
		
	}
	
    public int getRandom(int lower, int upper) {
        Random random = new Random();
        return random.nextInt((upper - lower) + 1) + lower;
    }

	

}