package me.xxastaspastaxx.mymachines.machines.Protection;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.RegisteredServiceProvider;

import net.milkbowl.vault.Vault;
import net.milkbowl.vault.economy.Economy;

public class ProtectionGui implements Listener {
  
  private Economy economy;
  
  public static HashMap<Player, Location> enloc = new HashMap<Player, Location>();
  
  private static Inventory inv;
  
  //----------------------------\\
  @SuppressWarnings("unused")
  private static ItemStack gl;
  @SuppressWarnings("unused")
  private static ItemStack close;
  //----------------------------\\
  
  
  //----------------------------\\
  private static ItemStack Upgrade;
  private static ItemStack Exeptions;
  //----------------------------\\
  
  public static ArrayList<String> adding = new ArrayList<String>();
  public static ArrayList<String> removing = new ArrayList<String>();
  
  public ProtectionGui(Plugin p) {

    
    if ((Bukkit.getPluginManager().getPlugin("Vault") instanceof Vault))
    {
      RegisteredServiceProvider<Economy> service = Bukkit.getServicesManager().getRegistration(Economy.class);
      if (service != null) {
        economy = ((Economy)service.getProvider());
      }
    }
    
    Bukkit.getServer().getPluginManager().registerEvents(this, p);
  }
 
  private static ItemStack gl(String name)
  {
    ItemStack i = new ItemStack(Material.STAINED_GLASS_PANE, 1, (short)15);
    ItemMeta im = i.getItemMeta();
    im.setDisplayName(name);
    i.setItemMeta(im);
    return i;
  }
  
  private static ItemStack close(String name)
  {
    ItemStack i = new ItemStack(Material.STAINED_GLASS_PANE, 1, (short)14);
    ItemMeta im = i.getItemMeta();
    im.setDisplayName(name);
    i.setItemMeta(im);
    return i;
  }
  
  
  
  private static ItemStack upgrade(String name, Entity machine)
  {
		File pmg = new File("plugins/MyMachines/Machines/ProtectionMachine/GUI.yml");
		YamlConfiguration pmgf = YamlConfiguration.loadConfiguration(pmg);
		File pms = new File("plugins/MyMachines/Machines/ProtectionMachine/Settings.yml");
		YamlConfiguration pmsf = YamlConfiguration.loadConfiguration(pms);
	  String[] words = pmgf.getString("Upgrade.Item").split(";");
	  int id = Integer.parseInt(words[0]);
	  int data = Integer.parseInt(words[1]);
	  
	  
		String[] words2 = machine.getCustomName().split(", ");
		int lvl = Integer.parseInt(words2[2]);
	  
    @SuppressWarnings("deprecation")
	ItemStack i = new ItemStack(id, 1, (short)data);
    ItemMeta im = i.getItemMeta();
    im.setDisplayName(name);
	ArrayList<String> plore = new ArrayList<String>();
	
	int u = Integer.parseInt(pmsf.getString("ProtectionMachine.Upgrade.Charge.UseMoney.Ammount"));
	int amm = u*lvl;
	
	for (String m : pmgf.getStringList("Upgrade.Lore")) {
	
		plore.add(m.replace("&", "§").replace("{level}", ""+lvl).replace("{ammount}", amm+""));
		
	}
	im.setLore(plore);
    i.setItemMeta(im);
    return i;
  }
  
  private static ItemStack exeptions(String name, Player p, Location loc)
  {
		File pmg = new File("plugins/MyMachines/Machines/ProtectionMachine/GUI.yml");
		YamlConfiguration pmgf = YamlConfiguration.loadConfiguration(pmg);
	  String[] words = pmgf.getString("Exeptions.Item").split(";");
	  int id = Integer.parseInt(words[0]);
	  int data = Integer.parseInt(words[1]);
	  
	  
		File exep = new File("plugins/MyMachines/PlayerData/"+p.getName()+"/ProtectionMachines.yml");
		YamlConfiguration exepf = YamlConfiguration.loadConfiguration(exep);
		List<String> ignore = exepf.getStringList(loc+".Exeptions");
	  
		
    @SuppressWarnings("deprecation")
	ItemStack i = new ItemStack(id, 1,(short)data);
    ItemMeta im = i.getItemMeta();
    im.setDisplayName(name);
	ArrayList<String> plore = new ArrayList<String>();
	String msgs = "";
	for (String a : ignore) {
		
		 msgs += a+"!";
	}


	
	for (String m : pmgf.getStringList("Exeptions.Lore")) {

	if (m.contains("{list}")) {
		for(String a : msgs.split("!")) {
			
			plore.add(m.replace("&", "§").replace("{player}", p.getName()).replace("{list}", a.replace("&", "§").replace("{player}", p.getName())));
			
		}
	}


		plore.add(m.replace("&", "§").replace("{player}", p.getName()).replace("{list}", ""));
		
		
	}
	im.setLore(plore);
    i.setItemMeta(im);
    return i;
  }
  
  
  public static List<String> addlist(List<String> plore, String msgs) {
	  
		for (String a : msgs.split("!")) {
			
			 plore.add(a);
		}
		
	  return plore;
  }
  
  
  public static void show(Player p, Entity machine)
  {
	  
		File pmg = new File("plugins/MyMachines/Machines/ProtectionMachine/GUI.yml");
		YamlConfiguration pmgf = YamlConfiguration.loadConfiguration(pmg);
	  
  inv = Bukkit.getServer().createInventory(null, 9, "§7§m-------§7[§6ProtectionGui§7]§m-------");

  gl = gl("§6");
  close = close("§4Close");

  Upgrade = upgrade(pmgf.getString("Upgrade.Name").replace("&", "§").replace("{player}", p.getName()), machine);
  Exeptions = exeptions(pmgf.getString("Exeptions.Name").replace("&", "§").replace("{player}", p.getName()), p, machine.getLocation());
  
  
  inv.setItem(Integer.parseInt(pmgf.getString("Upgrade.Slot")), Upgrade);
  inv.setItem(Integer.parseInt(pmgf.getString("Exeptions.Slot")), Exeptions);

  
  enloc.put(p, machine.getLocation());
    p.openInventory(inv);
  }
  
@EventHandler
  public void onClick(InventoryClickEvent e)
  {

	File pms = new File("plugins/MyMachines/Machines/ProtectionMachine/Settings.yml");
	YamlConfiguration pmsf = YamlConfiguration.loadConfiguration(pms);
	
	File pmg = new File("plugins/MyMachines/Machines/ProtectionMachine/GUI.yml");
	YamlConfiguration pmgf = YamlConfiguration.loadConfiguration(pmg);
	
    Player p = (Player) e.getWhoClicked();
	
	if (!e.getInventory().getName().equalsIgnoreCase("§7§m-------§7[§6ProtectionGui§7]§m-------")) return;
	if (e.getCurrentItem().getType().equals(Material.AIR) || e.getCurrentItem().getType() == null) return;
    if (!p.getName().contentEquals("§6fucaafawgawgh")) {
    	
    	e.setCancelled(true);
    	
    }
    
    if (e.getCurrentItem().getItemMeta().getDisplayName().contentEquals("§4Close"))
    {
    	p.closeInventory();


    }
    
    
    
    if (e.getCurrentItem().getItemMeta().getDisplayName().contentEquals(pmgf.getString("Upgrade.Name").replace("&", "§").replace("{player}", p.getName())))
    {
    	
		for (Entity entity : p.getWorld().getEntities()) {
			

			if (entity.getType().equals(EntityType.ARMOR_STAND) && entity.getCustomName().contains("§6ProtectionMachine")&&entity.getLocation().equals(enloc.get(p))) {
				
    	
		String[] words1 = entity.getCustomName().split(", ");
		
		String owner = words1[1];
		
		if (p.getName().contentEquals(owner)) {
		int lvl = Integer.parseInt(words1[2]);


		if (pmsf.getString("ProtectionMachine.Upgrade.MaxLevel.Enable").equalsIgnoreCase("true")) {
			
		int maxlvl = Integer.parseInt(pmsf.getString("ProtectionMachine.Upgrade.MaxLevel.Number"));
    	if (!(lvl >= maxlvl)) {
    		
    		if (pmsf.getString("ProtectionMachine.Upgrade.Charge.UseMoney.Enable").equalsIgnoreCase("true")) {

    			int upg = Integer.parseInt(pmsf.getString("ProtectionMachine.Upgrade.Charge.UseMoney.Ammount"));
    			int mon = upg*lvl;
    			
    			if (economy.getBalance(p) >= mon) {
    				economy.withdrawPlayer(p, mon);
					entity.setCustomName("§6ProtectionMachine, "+p.getName()+", "+(lvl+1)+"");
					show((Player) p, entity);
    				
    			} else {
    				
    				p.sendMessage(pmsf.getString("ProtectionMachine.Upgrade.Charge.UseMoney.NotEnoughMoney").replace("&", "§").replace("{player}", p.getName()).replace("{ammount}", mon+"").replace("{pmoney}", ""+economy.getBalance(p)));
    				
    			}
    			

    		} else {
    			
				entity.setCustomName("§6ProtectionMachine, "+p.getName()+", "+(lvl+1)+"");
				show((Player) p, entity);
    			
    			
    		}

    		
    		
    		
    		
    	} else {
    		
    		p.sendMessage(pmsf.getString("ProtectionMachine.Upgrade.MaxLevel.Message").replace("&", "§").replace("{player}", p.getName()).replace("{level}", ""+lvl).replace("{maxlevel}", ""+maxlvl));
    		
    	}
    	
		} else {
			
			
	    		
	    		if (pmsf.getString("ProtectionMachine.Upgrade.Charge.UseMoney.Enable").equalsIgnoreCase("true")) {
	    			
	    			int upg = Integer.parseInt(pmsf.getString("ProtectionMachine.Upgrade.Charge.UseMoney.Ammount"));
	    			int mon = upg*lvl;
	    			
	    			if (economy.getBalance(p) >= mon) {
	    				
	    				economy.withdrawPlayer(p, mon);
						entity.setCustomName("§6ProtectionMachine, "+p.getName()+", "+(lvl+1)+"");
						show((Player) p, entity);
	    				
	    			} else {
	    				
	    				p.sendMessage(pmsf.getString("ProtectionMachine.Upgrade.Charge.UseMoney.NotEnoughMoney").replace("&", "§").replace("{player}", p.getName()).replace("{ammount}", mon+"").replace("{pmoney}", ""+economy.getBalance(p)));
	    				
	    			}
	    			

	    		} else {
	    			
					entity.setCustomName("§6ProtectionMachine, "+p.getName()+", "+(lvl+1)+"");
					show((Player) p, entity);
	    			
	    		}
	    	
			
		}
    	
			}

			}
		}
    }
    
    if (e.getCurrentItem().getItemMeta().getDisplayName().contentEquals(pmgf.getString("Exeptions.Name").replace("&", "§").replace("{player}", p.getName())))
    {
    	p.closeInventory();
    	adding.add(p.getName());
    	p.sendMessage("§aType in the chat add/remove and the player's name that you want to add/remove. Type \"cancel\" to cancel");
    	p.sendMessage("§aExample: remove "+p.getName());
    	p.sendMessage("§aExample: add "+p.getName());
    }
    
    

  }



@EventHandler
public void onChat(AsyncPlayerChatEvent e)
{
  Player p = e.getPlayer();
  String msg = e.getMessage();

  if (adding.contains(p.getName()))
  {
	  
	  String[] words = msg.split(" ");
	  
	  
	  if (words.length == 2) {
		  if (words[0].equalsIgnoreCase("add")) {
  	File exep = new File("plugins/MyMachines/PlayerData/"+p.getName()+"/ProtectionMachines.yml");
  	YamlConfiguration exepf = YamlConfiguration.loadConfiguration(exep);
  	List<String> ignore = exepf.getStringList(enloc.get(p)+".Exeptions");
  	
  	if (ignore.contains(words[1])) {
  		
  		p.sendMessage("§aName §2"+words[1]+"§a already in the list.");
  	} else {

  	ignore.add(words[1]);
  	exepf.set(enloc.get(p)+".Exeptions", ignore);
  	
  	try {
			exepf.save(exep);
		} catch (IOException e1) {
			e1.printStackTrace();
		}
  	
    adding.remove(p.getName());
    p.sendMessage("§aAdded §2"+words[1]+"§a.");
  	}
	  } else if (words[0].equalsIgnoreCase("remove")) {
		  
		  	File exep = new File("plugins/MyMachines/PlayerData/"+p.getName()+"/ProtectionMachines.yml");
		  	YamlConfiguration exepf = YamlConfiguration.loadConfiguration(exep);
		  	List<String> ignore = exepf.getStringList(enloc.get(p)+".Exeptions");
		  	
		  	if (!ignore.contains(words[1])) {
		  		
		  		p.sendMessage("§cName §4"+words[1]+"§c is not in the list.");
		  	} else {

		  	ignore.remove(words[1]);
		  	exepf.set(enloc.get(p)+".Exeptions", ignore);
		  	
		  	try {
					exepf.save(exep);
				} catch (IOException e1) {
					e1.printStackTrace();
				}
		  	
		    adding.remove(p.getName());
		    p.sendMessage("§cRemoved §4"+words[1]+"§c.");
		  	}
		  
		  
	  } else {
		  
		  
		  p.sendMessage("§cPlease use \"remove/add <name>\"");
	  }
	  } else if(msg.equalsIgnoreCase("cancel")) {
		  adding.remove(p.getName());
		  p.sendMessage("§aYou can now chat");
		  
	  } else {
		  
		  
		  p.sendMessage("§cPlease use \"remove/add <name>\"");
		  
	  }
    e.setCancelled(true);
  }

	
	
	
	
}




}
