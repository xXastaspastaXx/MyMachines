package me.xxastaspastaxx.mymachines;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Random;
import java.util.logging.Logger;

import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Damageable;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.plugin.java.JavaPlugin;

import me.xxastaspastaxx.mymachines.machines.EnergyKiller.EnergyKiller;
import me.xxastaspastaxx.mymachines.machines.Protection.Protection;
import me.xxastaspastaxx.mymachines.machines.Protection.ProtectionGui;
import me.xxastaspastaxx.mymachines.machines.Teleporter.Teleporter;
import me.xxastaspastaxx.mymachines.machines.Teleporter.TeleporterGui;

public class Main extends JavaPlugin implements Listener {

	// help //
	public static HashMap<Integer, String> help = new HashMap<Integer, String>();
	public static HashMap<Integer, String> chelp = new HashMap<Integer, String>();
	// help //

	
	
	//-----------------\\
	@SuppressWarnings("unused")
	private Listeners l;
	//-----------------\\
	
	
	//-----------------\\
	
	@SuppressWarnings("unused")
	private Protection protmach;
	@SuppressWarnings("unused")
	private ProtectionGui protmachgui;
	
	@SuppressWarnings("unused")
	private Teleporter telemach;
	@SuppressWarnings("unused")
	private TeleporterGui telemachgui;
	
	@SuppressWarnings("unused")
	private EnergyKiller ekmach;
	
	//-----------------\\
	
	public void onEnable() {
		
		
		for (Player p : Bukkit.getServer().getOnlinePlayers()) {
			
			File exep = new File("plugins/MyMachines/PlayerData/"+p.getName()+"/ProtectionMachines.yml");
			YamlConfiguration exepf = YamlConfiguration.loadConfiguration(exep);
			
			try {
				exepf.save(exep);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}


		
		// help //
		
		String pg1h = "";
		pg1h += "§7/mm Help [page] §c-§7 Displays help."+"\n";
		pg1h += "§7/mm Reload §c-§7 Reload Config"+"\n";
		help.put(1, pg1h);
		
		
		
		
		
		String cpg1h = "";
		cpg1h += "/mm Help [page] - Displays help."+"\n";
		cpg1h += "/mm Reload - Reload Config"+"\n";
		chelp.put(1, cpg1h);
		
		// help //
		
		Bukkit.getServer().getPluginManager().registerEvents(this, this);
		
		
		// Files \\
		
		
		
		// Protection \\
		File pms = new File("plugins/MyMachines/Machines/ProtectionMachine/Settings.yml");
		YamlConfiguration pmsf = YamlConfiguration.loadConfiguration(pms);
		File pmg = new File("plugins/MyMachines/Machines/ProtectionMachine/GUI.yml");
		YamlConfiguration pmgf = YamlConfiguration.loadConfiguration(pmg);
		File pmm = new File("plugins/MyMachines/Machines/ProtectionMachine/Messages.yml");
		YamlConfiguration pmmf = YamlConfiguration.loadConfiguration(pmm);
		
		
		if (!pmm.exists()) {
			
	          pmmf.addDefault("CreateMachine.Enable", "true");
	          pmmf.addDefault("CreateMachine.Message", "&2{player}&a, You just created a &6protection machine&a.");
	          
	          pmmf.addDefault("PlaceNexus.Enable", "true");
	          pmmf.addDefault("PlaceNexus.Message", "&2{player}&a, You just placed &5nexus&a.");

	          pmmf.addDefault("AttackedByMachine.Enable", "false");
	          pmmf.addDefault("AttackedByMachine.Message", "&cYou were attacked by &4{player}'s&c protection machine.");
	          
	          pmmf.options().copyDefaults(true);
			
		}
		
		if (!pmg.exists()) {
			
			pmgf.addDefault("Upgrade.Item", "399;0");
			pmgf.addDefault("Upgrade.Slot", "0");
			pmgf.addDefault("Upgrade.Name", "&aUpgrade");
	          List<String> UpgradeLore = pmgf.getStringList("Upgrade.Lore");
	          UpgradeLore.add("&c------------------");
	          UpgradeLore.add("&c¤ &a${ammount} &c¤");
	          UpgradeLore.add("&c¤ &a{level} &c¤");
	          UpgradeLore.add("&c------------------");
	          pmgf.set("Upgrade.Lore", UpgradeLore);
	          
	          
				pmgf.addDefault("Exeptions.Item", "386;0");
				pmgf.addDefault("Exeptions.Slot", "8");
				pmgf.addDefault("Exeptions.Name", "&cExeptions");
		          List<String> ExeptionsLore = pmgf.getStringList("Exeptions.Lore");
		          ExeptionsLore.add("&c------------------");
		          ExeptionsLore.add("&e¤ &cExeptions &e¤");
		          ExeptionsLore.add("&c{list}");
		          ExeptionsLore.add("&c------------------");
		          pmgf.set("Exeptions.Lore", ExeptionsLore);
			
			
			pmgf.options().copyDefaults(true);
		}
		
    	if (!pms.exists()) {
  		
	          pmsf.addDefault("ProtectionMachine.Enable", "true");
	          pmsf.addDefault("ProtectionMachine.Starting.Damage", "0.2");
	          pmsf.addDefault("ProtectionMachine.Starting.Area", "5");
	          pmsf.addDefault("ProtectionMachine.Charge.Enable", "true");
	          pmsf.addDefault("ProtectionMachine.Charge.Ammount", "5000");
	          pmsf.addDefault("ProtectionMachine.Charge.Message.NotEnoughMoney", "&c&lHey&7, sorry but you do not have enough money(&c{pmoney}&7/&c{ammount}&7) to build this machine");
	          pmsf.addDefault("ProtectionMachine.Permission.Enable", "true");
	          pmsf.addDefault("ProtectionMachine.Permission.Node", "mymachines.buildmachine.protection");
	          pmsf.addDefault("ProtectionMachine.Permission.Message.NoPermission", "&c&lHey&7, sorry but you do not have enough permission({permission}) to build this machine");
	          pmsf.addDefault("ProtectionMachine.Upgrade.MaxLevel.Enable", "true");
	          pmsf.addDefault("ProtectionMachine.Upgrade.MaxLevel.Number", "10");
	          pmsf.addDefault("ProtectionMachine.Upgrade.MaxLevel.Message", "&c&lHey&7, sorry but you have already maxed level.");
	          pmsf.addDefault("ProtectionMachine.Upgrade.Charge.UseMoney.Enable", "true");
	          pmsf.addDefault("ProtectionMachine.Upgrade.Charge.UseMoney.Ammount", "10000");
	          pmsf.addDefault("ProtectionMachine.Upgrade.Charge.UseMoney.NotEnoughMoney", "&c&lHey&7, sorry but you do not have enough money(&c{pmoney}&7/&c{ammount}&7) to upgrade this machine");

	          pmsf.options().copyDefaults(true);
  		
  	}
// Protection \\
    	
    	
    	
    	
    	// Teleporter \\
    			File tms = new File("plugins/MyMachines/Machines/TeleporterMachine/Settings.yml");
    			YamlConfiguration tmsf = YamlConfiguration.loadConfiguration(tms);
    			File tmg = new File("plugins/MyMachines/Machines/TeleporterMachine/GUI.yml");
    			YamlConfiguration tmgf = YamlConfiguration.loadConfiguration(tmg);
    			File tmm = new File("plugins/MyMachines/Machines/TeleporterMachine/Messages.yml");
    			YamlConfiguration tmmf = YamlConfiguration.loadConfiguration(tmm);
    			
    			
    			if (!tmm.exists()) {
    				
    		          tmmf.addDefault("CreateMachine.Enable", "true");
    		          tmmf.addDefault("CreateMachine.Message", "&2{player}&a, You just created a &6teleportation machine&a.");
    		          
    		          tmmf.addDefault("TeleportTo.Enable", "true");
    		          tmmf.addDefault("TeleportTo.Message", "&2{player}&a, You just teleported to {name}&a at &2{world}, {x}, {y}, {z}&a.");
    		          
    		          tmmf.options().copyDefaults(true);
    				
    			}
    			
    			if (!tmg.exists()) {
    				
    				tmgf.addDefault("Home.Item", "2;0");
    				tmgf.addDefault("Home.Slot", "0");
    				tmgf.addDefault("Home.Name", "&aHome");
    		          List<String> HomeLore = tmgf.getStringList("Home.Lore");
    		          HomeLore.add("&cClick me to open &4Homes&c menu.");
    		          tmgf.set("Home.Lore", HomeLore);
    		          
    		          
    					tmgf.addDefault("Location.Item", "1;6");
    					tmgf.addDefault("Location.Slot", "8");
    					tmgf.addDefault("Location.Name", "&dLocation");
    			          List<String> LocationLore = tmgf.getStringList("Location.Lore");
    			          LocationLore.add("&cClick me to open &4Locations&c menu.");
    			          tmgf.set("Location.Lore", LocationLore);
    				
    			          
    			          
    			          tmgf.addDefault("TeleportLocationItem", "3;2");
    				
    				tmgf.options().copyDefaults(true);
    			}
    			
    	    	if (!tms.exists()) {
    	  		
    		          tmsf.addDefault("TeleportMachine.Enable", "true");
    		          tmsf.addDefault("TeleportMachine.Charge.Enable", "true");
    		          tmsf.addDefault("TeleportMachine.Charge.Ammount", "5000");
    		          tmsf.addDefault("TeleportMachine.Charge.Message.NotEnoughMoney", "&c&lHey&7, sorry but you do not have enough money(&c{pmoney}&7/&c{ammount}&7) to build this machine");
    		          tmsf.addDefault("TeleportMachine.Permission.Enable", "true");
    		          tmsf.addDefault("TeleportMachine.Permission.Node", "mymachines.buildmachine.teleport");
    		          tmsf.addDefault("TeleportMachine.Permission.Message.NoPermission", "&c&lHey&7, sorry but you do not have enough permission({permission}) to build this machine");
    		          tmsf.addDefault("TeleportMachine.Use.Charge.UseMoney.Enable", "true");
    		          tmsf.addDefault("TeleportMachine.Use.Charge.UseMoney.Ammount", "1500");
    		          tmsf.addDefault("TeleportMachine.Use.Charge.UseMoney.NotEnoughMoney", "&c&lHey&7, sorry but you do not have enough money(&c{pmoney}&7/&c{ammount}&7) to teleport to {name}");

    		          tmsf.options().copyDefaults(true);
    	  		
    	  	}
    	// Teleporter \\
    	    	
    	    	
    	    	
    	    	
    	    	// EnergyKiller \\
    			File ekms = new File("plugins/MyMachines/Machines/EnergyKillerMachine/Settings.yml");
    			YamlConfiguration ekmsf = YamlConfiguration.loadConfiguration(ekms);
    			File ekmm = new File("plugins/MyMachines/Machines/EnergyKillerMachine/Messages.yml");
    			YamlConfiguration ekmmf = YamlConfiguration.loadConfiguration(ekmm);
    			
    			
    			if (!ekmm.exists()) {
    				
    		          ekmmf.addDefault("CreateMachine.Enable", "true");
    		          ekmmf.addDefault("CreateMachine.Message", "&2{player}&a, You just created an &6Energy Killer Machine&a.");
    		          
    		          ekmmf.addDefault("RewardsReceived.Enable", "true");
    		          ekmmf.addDefault("RewardsReceived.Message", "&2{player}&a, You just received &25 xp levels&a and &22 gold ingots&a.");
    		          
    		          ekmmf.addDefault("LuckyRewardsReceived.Enable", "true");
    		          ekmmf.addDefault("LuckyRewardsReceived.Message", "&2{player}&a, Your machine was powerfull enough to get you some more stuff&a.");
    		          
    		          ekmmf.options().copyDefaults(true);
    				
    			}

    			
    	    	if (!ekms.exists()) {
    	  		
    		          ekmsf.addDefault("EnergyKillerMachine.Enable", "true");
    		          ekmsf.addDefault("EnergyKillerMachine.Charge.Enable", "true");
    		          ekmsf.addDefault("EnergyKillerMachine.Charge.Ammount", "5000");
    		          ekmsf.addDefault("EnergyKillerMachine.Charge.Message.NotEnoughMoney", "&c&lHey&7, sorry but you do not have enough money(&c{pmoney}&7/&c{ammount}&7) to build this machine");
    		          ekmsf.addDefault("EnergyKillerMachine.Permission.Enable", "true");
    		          ekmsf.addDefault("EnergyKillerMachine.Permission.Node", "mymachines.buildmachine.energykiller");
    		          ekmsf.addDefault("EnergyKillerMachine.Permission.Message.NoPermission", "&c&lHey&7, sorry but you do not have enough permission({permission}) to build this machine");

    		          
      		          List<String> RewardCommands = ekmsf.getStringList("EnergyKillerMachine.ReceiveRewards.Commands");
      		        RewardCommands.add("xp 5l {player}");
      		      RewardCommands.add("give {player} gold_ingot 2");
      		    ekmsf.set("EnergyKillerMachine.ReceiveRewards.Commands", RewardCommands);
    		          
    		          
      		  ekmsf.addDefault("EnergyKillerMachine.ReceiveRewards.Lucky.Enable", "true");
      		ekmsf.addDefault("EnergyKillerMachine.ReceiveRewards.Lucky.MaxRewards", "5");
      		ekmsf.addDefault("EnergyKillerMachine.ReceiveRewards.Lucky.MaxLuckyNumber", "500");
			          List<String> LuckyRewardCommands = ekmsf.getStringList("EnergyKillerMachine.ReceiveRewards.Lucky.Commands");
			          LuckyRewardCommands.add("[1-200] eco give {player} 1000");
			          LuckyRewardCommands.add("[1-150] eco give {player} 10000");
			          LuckyRewardCommands.add("[485-500] eco give {player} 100000");
			          ekmsf.set("EnergyKillerMachine.ReceiveRewards.Lucky.Commands", LuckyRewardCommands);
      		          
    		          ekmsf.options().copyDefaults(true);
    	  		
    	  	}
    	// EnergyKiller \\
    	
    	
    	
    	
    	
    	
    	// Files \\
    	
		try {
			  pmsf.save(pms);
			  pmgf.save(pmg);
			  pmmf.save(pmm);
			  
			  tmsf.save(tms);
			  tmgf.save(tmg);
			  tmmf.save(tmm);
			  
			  ekmsf.save(ekms);
			  ekmmf.save(ekmm);
			} catch(IOException e) {
			  e.printStackTrace();
			}
		
		


		l = new Listeners(this);
		
		if (pmsf.getString("ProtectionMachine.Enable").equalsIgnoreCase("true")) {
		protmach = new Protection(this);
		protmachgui = new ProtectionGui(this);
		}
		
		if (tmsf.getString("TeleportMachine.Enable").equalsIgnoreCase("true")) {
		telemach = new Teleporter(this);
		telemachgui = new TeleporterGui(this);
		}
		
		if (ekmsf.getString("EnergyKillerMachine.Enable").equalsIgnoreCase("true")) {
		ekmach = new EnergyKiller(this);
		}
		
		
		instance = this;
		
		
	 
	/*  	Bukkit.getScheduler().scheduleSyncRepeatingTask(this, new Runnable() {
	    	
			public void run()
	          {

				
	          }
	        }, 0, 0);
	 */

				Logger log = Bukkit.getLogger();
				
				log.info("-=-=-=-=-=-=-=[MyMachines]=-=-=-=-=-=-=-");
				log.info("");
				// protection machine information \\
				log.info("-=-=-=-=[Protection]=-=-=-=-");
				if (pmsf.getString("ProtectionMachine.Enable").equalsIgnoreCase("true")) {
					log.info("Machine \"Protection\" is enabled");
					log.info("");
					log.info("Startin damage is: "+pmsf.getString("ProtectionMachine.Starting.Damage"));
					log.info("Starting area is: "+pmsf.getString("ProtectionMachine.Starting.Area"));
					log.info("");
					if (pmsf.getString("ProtectionMachine.Charge.Enable").equalsIgnoreCase("true")) {
						log.info("Charge to build is enabled");
						log.info("Starting cost to build is "+pmsf.getString("ProtectionMachine.Charge.Ammount"));
						log.info("");
					} else {
						log.info("Charge to build is disabled. No further information needed.");
						log.info("");
					}
					if (pmsf.getString("ProtectionMachine.Permission.Enable").equalsIgnoreCase("true")) {
						log.info("Permission to build is enabled");
						log.info("Permission to build is "+pmsf.getString("ProtectionMachine.Permission.Node"));
						log.info("");
					} else {
						log.info("Permission to build is disabled. No further information needed.");
						log.info("");
					}
					if (pmsf.getString("ProtectionMachine.Upgrade.MaxLevel.Enable").equalsIgnoreCase("true")) {
						log.info("Max level is enabled");
						log.info("Max level is "+pmsf.getString("ProtectionMachine.Upgrade.MaxLevel.Number"));
						log.info("");
					} else {
						log.info("Max level is disabled. No further information needed.");
						log.info("");
					}
					if (pmsf.getString("ProtectionMachine.Upgrade.Charge.UseMoney.Enable").equalsIgnoreCase("true")) {
						log.info("Charge to upgrade is enabled");
						log.info("Ammount to upgrade is "+pmsf.getString("ProtectionMachine.Upgrade.Charge.UseMoney.Ammount")+" * machine level");
						log.info("");
					} else {
						log.info("Charge to upgrade is disabled. No further information needed.");
						log.info("");
					}
				} else {
					log.info("Machine \"Protection\" is disabled. No further information needed.");
					log.info("Killing all Protection Machines...");
					for (World w : Bukkit.getServer().getWorlds()) {
						for (Entity en : w.getEntities()) {
							if (en.getType().equals(EntityType.ARMOR_STAND) && en.getCustomName().contains("§6ProtectionMachine")) {
							
								((Damageable) en).setHealth(0);
								
							}
						}
					}
					log.info("Killing all Protection Machines - Done");
				}
				log.info("-=-=-=-=[Protection]=-=-=-=-");
				// protection machine information \\
				
				
				// teleporter machine information \\
				log.info("-=-=-=-=[Teleporter]=-=-=-=-");
				if (tmsf.getString("TeleportMachine.Enable").equalsIgnoreCase("true")) {
					log.info("Machine \"Teleporter\" is enabled");
					log.info("");
					if (tmsf.getString("TeleportMachine.Charge.Enable").equalsIgnoreCase("true")) {
						log.info("Charge to build is enabled");
						log.info("Starting cost to build is "+tmsf.getString("TeleportMachine.Charge.Ammount"));
						log.info("");
					} else {
						log.info("Charge to build is disabled. No further information needed.");
						log.info("");
					}
					if (tmsf.getString("TeleportMachine.Permission.Enable").equalsIgnoreCase("true")) {
						log.info("Permission to build is enabled");
						log.info("Permission to build is "+tmsf.getString("TeleportMachine.Permission.Node"));
						log.info("");
					} else {
						log.info("Permission to build is disabled. No further information needed.");
						log.info("");
					}
					if (tmsf.getString("TeleportMachine.Use.Charge.UseMoney.Enable").equalsIgnoreCase("true")) {
						log.info("Charge to use is enabled");
						log.info("Ammount to use is "+tmsf.getString("TeleportMachine.Use.Charge.UseMoney.Ammount"));
						log.info("");
					} else {
						log.info("Charge to use is disabled. No further information needed.");
						log.info("");
					}
				} else {
					log.info("Machine \"Teleporter\" is disabled. No further information needed.");
					log.info("Killing all Teleportation Machines...");
					for (World w : Bukkit.getServer().getWorlds()) {
						for (Entity en : w.getEntities()) {
							if (en.getType().equals(EntityType.ARMOR_STAND) && en.getCustomName().contains("§6TeleporterMachine")) {
							
								((Damageable) en).setHealth(0);
								
							}
						}
					}
					log.info("Killing all Teleportation Machines - Done");
				}
				log.info("-=-=-=-=[Teleporter]=-=-=-=-");
				// teleporter machine information \\
				
				
				
				// energykiller machine information \\
				log.info("-=-=-=-=[Teleporter]=-=-=-=-");
				if (ekmsf.getString("EnergyKilerMachine.Enable").equalsIgnoreCase("true")) {
					log.info("Machine \"EnergyKiller\" is enabled");
					log.info("");
					log.info("More info not avainable");
				} else {
					log.info("Machine \"EnergyKiller\" is disabled. No further information needed.");
					log.info("Killing all EnergyKiller Machines...");
					for (World w : Bukkit.getServer().getWorlds()) {
						for (Entity en : w.getEntities()) {
							if (en.getType().equals(EntityType.ARMOR_STAND) && en.getCustomName().contains("§6EnergyKiller")) {
							
								((Damageable) en).setHealth(0);
								
							}
						}
					}
					log.info("Killing all EnergyKiller Machines - Done");
				}
				log.info("-=-=-=-=[Teleporter]=-=-=-=-");
				// energykiller machine information \\
				
				
				log.info("");
				log.info("Plugin is succesfully loaded.");
				log.info("");
				log.info("-=-=-=-=-=-=-=[MyMachines]=-=-=-=-=-=-=-");

		
	}
	
	public void onDisable() {
		
		instance = null;
		
		
	}
	
	private static Main instance;
	public static Main getInstance() {
	  return instance;
	}
	
	
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {

        if ((cmd.getName().equalsIgnoreCase("mymachines")) && (sender instanceof ConsoleCommandSender)) {
        	
        	Logger log = Bukkit.getLogger();
	          if (args.length == 0) {

		            	String pg1 = chelp.get(1);
		            	
		            	log.info("\n"+"---[1/"+chelp.size()+"]---[MyMachines]---[1/"+chelp.size()+"]---"+"\n"+""+"\n"+pg1+"\n"+""+"\n"+"---[1/"+chelp.size()+"]---[MyMachines]---[1/"+chelp.size()+"]---");

	          } else {
	        	  
	        	  

	          		if (args[0].equalsIgnoreCase("reload")) {            			
	        			
		            	if (args.length == 1) {

		            		
		                    Bukkit.getServer().getPluginManager().disablePlugin(this);
		                    Bukkit.getServer().getPluginManager().enablePlugin(this);
		            		
			            	log.info("\n"+"Reload complete.");
		            		

		            	}
	            }
      		if (args[0].equalsIgnoreCase("help")) {         			
      			
		            	if (args.length == 1) {

			            	String pg1 = chelp.get(1);
			            	
			            	log.info("\n"+"---[1/"+chelp.size()+"]---[MyMachines]---[1/"+chelp.size()+"]---"+"\n"+""+"\n"+pg1+"\n"+""+"\n"+"---[1/"+chelp.size()+"]---[MyMachines]---[1/"+chelp.size()+"]---");
		            	}
		            	
		            	if (args.length == 2) {

		            		if (chelp.containsKey(Integer.parseInt(args[1]))) {
			            	String pg = chelp.get(Integer.parseInt(args[1]));
			            	
			            	log.info("\n"+"---["+args[1]+"/"+chelp.size()+"]---[MyMachines]---["+args[1]+"/"+chelp.size()+"]---"+"\n"+""+"\n"+pg+"\n"+""+"\n"+"---["+args[1]+"/"+chelp.size()+"]---[MyMachines]---["+args[1]+"/"+chelp.size()+"]---");
		            		} else {

				            	log.info("\n"+"---["+args[1]+"/"+chelp.size()+"]---[MyMachines]---["+args[1]+"/"+chelp.size()+"]---"+"\n"+""+"\n"+"There is no page "+args[1]+". Please use pages 1-"+chelp.size()+"."+"\n"+""+"\n"+"---["+args[1]+"/"+chelp.size()+"]---[MyMachines]---["+args[1]+"/"+chelp.size()+"]---");
		            			
		            		}
		            	}
      	
		            

	            }

      		
          }
	        
	      }
		
		
		
		
		
		
		
		
		
		
		
		
        if ((cmd.getName().equalsIgnoreCase("mymachines")) && ((sender instanceof Player))) {
        	Player player = (Player) sender;
	          if (args.length == 0) {

		            if (player.hasPermission("mm.help")) {   
		            	String pg1 = help.get(1);
		            	
		            	player.sendMessage("§7§m---§7[§c1§7/§c"+help.size()+"§7]§m---§7[§6MyMachines§7]§m---§7[§c1§7/§c"+help.size()+"§7]§m---");
		            	player.sendMessage("");
		            	player.sendMessage(pg1);
		            	player.sendMessage("");
		            	player.sendMessage("§7§m---§7[§c1§7/§c"+help.size()+"§7]§m---§7[§6MyMachines§7]§m---§7[§c1§7/§c"+help.size()+"§7]§m---");
		            	
		            	
		            } else {
		            	
		            	
		            	player.sendMessage("§cNo permission!");
		            	
		            }
	        	  
	             
	          } else {
	        	  
	        	  

	          		if (args[0].equalsIgnoreCase("reload")) {

			            if (player.hasPermission("mm.reload")) {            			
	        			
		            	if (args.length == 1) {

		            		
		                    Bukkit.getServer().getPluginManager().disablePlugin(this);
		                    Bukkit.getServer().getPluginManager().enablePlugin(this);
		            		
			            	player.sendMessage("§aReload complete.");
		            		

		            	}
	        	
			            } else {
			            	
			            	player.sendMessage("§cNo permission!");
			            	
			            }
	            }
      		if (args[0].equalsIgnoreCase("help")) {

		            if (player.hasPermission("mm.help")) {            			
      			
		            	if (args.length == 1) {

			            	String pg1 = help.get(1);
			            	
			            	player.sendMessage("§7§m---§7[§c1§7/§c"+help.size()+"§7]§m---§7[§6MyMachines§7]§m---§7[§c1§7/§c"+help.size()+"§7]§m---");
			            	player.sendMessage("");
			            	player.sendMessage(pg1);
			            	player.sendMessage("");
			            	player.sendMessage("§7§m---§7[§c1§7/§c"+help.size()+"§7]§m---§7[§6MyMachines§7]§m---§7[§c1§7/§c"+help.size()+"§7]§m---");

		            	}
		            	
		            	if (args.length == 2) {

		            		if (help.containsKey(Integer.parseInt(args[1]))) {
			            	String pg = help.get(Integer.parseInt(args[1]));
			            	
			            	player.sendMessage("§7§m---§7[§c"+args[1]+"§7/§c"+help.size()+"§7]§m---§7[§6MyMachines§7]§m---§7[§c"+args[1]+"§7/§c"+help.size()+"§7]§m---");
			            	player.sendMessage("");
			            	player.sendMessage(pg);
			            	player.sendMessage("");
			            	player.sendMessage("§7§m---§7[§c"+args[1]+"§7/§c"+help.size()+"§7]§m---§7[§6MyMachines§7]§m---§7[§c"+args[1]+"§7/§c"+help.size()+"§7]§m---");
			            	
		            		} else {

				            	player.sendMessage("§7§m---§7[§c"+args[1]+"§7/§c"+help.size()+"§7]§m---§7[§6MyMachines§7]§m---§7[§c"+args[1]+"§7/§c"+help.size()+"§7]§m---");
				            	
				            	player.sendMessage("");
				            	player.sendMessage("§7There is no page §c"+args[1]+"§7. Please use pages §c1-"+help.size()+"§7.");
				            	player.sendMessage("");
				            	player.sendMessage("§7§m---§7[§c"+args[1]+"§7/§c"+help.size()+"§7]§m---§7[§6MyMachines§7]§m---§7[§c"+args[1]+"§7/§c"+help.size()+"§7]§m---");
				            	
		            			
		            		}
		            	}
      	
		            } else {
		            	
		            	player.sendMessage("§cNo permission!");
		            	
		            }
		            

	            }

      		
          }
	        
	      }
	  
		return true;
  }
  
    public int getRandom(int lower, int upper) {
        Random random = new Random();
        return random.nextInt((upper - lower) + 1) + lower;
    }

	
    @EventHandler
    public void onJoin(PlayerJoinEvent e) {
    	Player p = e.getPlayer();

		File exep = new File("plugins/MyMachines/PlayerData/"+p.getName()+"/ProtectionMachines.yml");
		YamlConfiguration exepf = YamlConfiguration.loadConfiguration(exep);
		
		try {
			exepf.save(exep);
		} catch (IOException e1) {
			e1.printStackTrace();
		}
    	
    }
    
    
    @EventHandler
    public void onQuit(PlayerQuitEvent e) {
    	Player p = e.getPlayer();

    	
		File exep = new File("plugins/MyMachines/PlayerData/"+p.getName()+"/ProtectionMachines.yml");
		YamlConfiguration exepf = YamlConfiguration.loadConfiguration(exep);
		
		try {
			exepf.save(exep);
		} catch (IOException e1) {
			e1.printStackTrace();
		}
    	
    }
    
	
	
}
