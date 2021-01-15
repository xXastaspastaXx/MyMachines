package me.xxastaspastaxx.mymachines.machines.Teleporter;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.RegisteredServiceProvider;

import net.milkbowl.vault.Vault;
import net.milkbowl.vault.economy.Economy;

public class TeleporterGui implements Listener {
  
  private Economy economy;
  
  public static HashMap<Player, Entity> ent = new HashMap<Player, Entity>();
  
  private static Inventory inv;
  
  //----------------------------\\
  @SuppressWarnings("unused")
  private static ItemStack gl;
  @SuppressWarnings("unused")
  private static ItemStack close;
  //----------------------------\\
  
  
  //----------------------------\\
  private static ItemStack Homes;
  private static ItemStack Locations;
  //----------------------------\\
  
  public TeleporterGui(Plugin p) {

    
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
  
  
  private static ItemStack homes(String name)
  {
		File tmg = new File("plugins/MyMachines/Machines/TeleporterMachine/GUI.yml");
		YamlConfiguration tmgf = YamlConfiguration.loadConfiguration(tmg);
		  String[] words = tmgf.getString("Home.Item").split(";");
		  int id = Integer.parseInt(words[0]);
		  int data = Integer.parseInt(words[1]);
		
	@SuppressWarnings("deprecation")
    ItemStack i = new ItemStack(id, 1, (short)data);
    ItemMeta im = i.getItemMeta();
    im.setDisplayName(name);
	ArrayList<String> plore = new ArrayList<String>();
	for (String e : tmgf.getStringList("Home.Lore")) {
	plore.add(e.replace("&", "§"));
	}
	im.setLore(plore);
    i.setItemMeta(im);
    return i;
  }
  
  
  
  private static ItemStack locations(String name)
  {
		File tmg = new File("plugins/MyMachines/Machines/TeleporterMachine/GUI.yml");
		YamlConfiguration tmgf = YamlConfiguration.loadConfiguration(tmg);
		
		  String[] words = tmgf.getString("Location.Item").split(";");
		  int id = Integer.parseInt(words[0]);
		  int data = Integer.parseInt(words[1]);
		
	@SuppressWarnings("deprecation")
  ItemStack i = new ItemStack(id, 1, (short)data);
    ItemMeta im = i.getItemMeta();
    im.setDisplayName(name);
	ArrayList<String> plore = new ArrayList<String>();
	for (String e : tmgf.getStringList("Location.Lore")) {
	plore.add(e.replace("&", "§"));
	}
	im.setLore(plore);
    i.setItemMeta(im);
    return i;
  }
  
  
  public static void show(Player p, Entity machine)
  {
		File tmg = new File("plugins/MyMachines/Machines/TeleporterMachine/GUI.yml");
		YamlConfiguration tmgf = YamlConfiguration.loadConfiguration(tmg);
	  
  inv = Bukkit.getServer().createInventory(null, 9, "§7§m-------§7[§6TeleporterGui§7]§m-------");

  gl = gl("§6");
  close = close("§4Close");

  
  Homes = homes(tmgf.getString("Home.Name").replace("&", "§"));
  Locations = locations(tmgf.getString("Location.Name").replace("&", "§"));
  
  
  inv.setItem(Integer.parseInt(tmgf.getString("Home.Slot")), Homes);
  inv.setItem(Integer.parseInt(tmgf.getString("Location.Slot")), Locations);
  
  
    p.openInventory(inv);
  }
  
  
  
  public static void showHomes(Player p, Entity machine)
  {
		File tmg = new File("plugins/MyMachines/Machines/TeleporterMachine/GUI.yml");
		YamlConfiguration tmgf = YamlConfiguration.loadConfiguration(tmg);
	  
  inv = Bukkit.getServer().createInventory(null, 54, "§7§m-------§7[§6TeleporterGuiH§7]§m-------");

  gl = gl("§6");
  close = close("§4Close");

  
  
  	for (World w : Bukkit.getWorlds()) {
  		for (Entity en : w.getEntities()) {
  			

			  	if ((en.getType().equals(EntityType.ARMOR_STAND) && en.getCustomName().contains("§6TeleporterMachine")&& en.getCustomName().contains("Home")&& en.getCustomName().contains(p.getName()))){
			  			
					String[] name = en.getCustomName().split(", ");
					String ltype = name[2];
					String location = name[3];
			  		
			  			
					  String[] words = tmgf.getString("TeleportLocationItem").split(";");
					  int id = Integer.parseInt(words[0]);
					  int data = Integer.parseInt(words[1]);
					
				@SuppressWarnings("deprecation")
			  ItemStack i = new ItemStack(id, 1, (short)data);
  			  	    ItemMeta im = i.getItemMeta();
  			  	    im.setDisplayName("§6"+ltype+"§7§l >>§2 "+location);
  			  	ArrayList<String> plore = new ArrayList<String>();
  			  	plore.add("§7World: §c"+en.getLocation().getWorld().getName());
  			  plore.add("§7X: §c"+en.getLocation().getX());
  			plore.add("§7Y: §c"+en.getLocation().getY());
  			plore.add("§7Z: §c"+en.getLocation().getZ());
  			  
  				im.setLore(plore);
  				
  			  	    i.setItemMeta(im);
  			  			
  			  	    inv.addItem(i);
  			  		}
  		
  		
  		}
  	}
  
  
  
  
  
  
  
    p.openInventory(inv);
  }
  
  
  public static void showLocations(Player p, Entity machine)
  {
		File tmg = new File("plugins/MyMachines/Machines/TeleporterMachine/GUI.yml");
		YamlConfiguration tmgf = YamlConfiguration.loadConfiguration(tmg);
	  
  inv = Bukkit.getServer().createInventory(null, 54, "§7§m-------§7[§6TeleporterGuiL§7]§m-------");

  gl = gl("§6");
  close = close("§4Close");

  
  
	for (World w : Bukkit.getWorlds()) {
  		for (Entity en : w.getEntities()) {
  			

			  	if ((en.getType().equals(EntityType.ARMOR_STAND) && en.getCustomName().contains("§6TeleporterMachine")&& en.getCustomName().contains("Location")&& en.getCustomName().contains(p.getName()))){
			  			
					String[] name = en.getCustomName().split(", ");
					String ltype = name[2];
					String location = name[3];
			  		
			  			
					  String[] words = tmgf.getString("TeleportLocationItem").split(";");
					  int id = Integer.parseInt(words[0]);
					  int data = Integer.parseInt(words[1]);
					
				@SuppressWarnings("deprecation")
			  ItemStack i = new ItemStack(id, 1, (short)data);
  			  	    ItemMeta im = i.getItemMeta();
  			  	    im.setDisplayName("§6"+ltype+"§7§l >>§2 "+location);
  			  	ArrayList<String> plore = new ArrayList<String>();
  			  	plore.add("§7World: §c"+en.getLocation().getWorld().getName());
  			  plore.add("§7X: §c"+en.getLocation().getX());
  			plore.add("§7Y: §c"+en.getLocation().getY());
  			plore.add("§7Z: §c"+en.getLocation().getZ());
  			  
  				im.setLore(plore);
  				
  			  	    i.setItemMeta(im);
  			  			
  			  	    inv.addItem(i);
  			  		}
  		
  		
  		}
  	}
  
  
  
  
  
  
  
    p.openInventory(inv);
  }
  
  
  @EventHandler
  public void onClick(InventoryClickEvent e)
  {
		File tmg = new File("plugins/MyMachines/Machines/TeleporterMachine/GUI.yml");
		YamlConfiguration tmgf = YamlConfiguration.loadConfiguration(tmg);
	
    Player p = (Player) e.getWhoClicked();
	
	if (!e.getInventory().getName().equalsIgnoreCase("§7§m-------§7[§6TeleporterGui§7]§m-------")) return;
	if (e.getCurrentItem().getType().equals(Material.AIR) || e.getCurrentItem().getType() == null) return;
    if (!p.getName().contentEquals("§6fucaafawgawgh")) {
    	
    	e.setCancelled(true);
    	
    }
    
    if (e.getCurrentItem().getItemMeta().getDisplayName().contentEquals("§4Close"))
    {
    	p.closeInventory();


    }
    
    
    
    if (e.getCurrentItem().getItemMeta().getDisplayName().contentEquals(tmgf.getString("Home.Name").replace("&", "§")))
    {
    	showHomes(p, ent.get(p));


    }
    
    if (e.getCurrentItem().getItemMeta().getDisplayName().contentEquals(tmgf.getString("Location.Name").replace("&", "§")))
    {
    	showLocations(p, ent.get(p));


    }
    
    
    
    
  }
  
  
  
  
  @EventHandler
  public void onClickHomes(InventoryClickEvent e)
  {
		File tms = new File("plugins/MyMachines/Machines/TeleporterMachine/Settings.yml");
		YamlConfiguration tmsf = YamlConfiguration.loadConfiguration(tms);
		File tmm = new File("plugins/MyMachines/Machines/TeleporterMachine/Messages.yml");
		YamlConfiguration tmmf = YamlConfiguration.loadConfiguration(tmm);
		
    Player p = (Player) e.getWhoClicked();
	
	if (!e.getInventory().getName().equalsIgnoreCase("§7§m-------§7[§6TeleporterGuiH§7]§m-------")) return;
	if (e.getCurrentItem().getType().equals(Material.AIR) || e.getCurrentItem().getType() == null) return;
    if (!p.getName().contentEquals("§6fucaafawgawgh")) {
    	
    	e.setCancelled(true);
    	
    }
    
    if (e.getCurrentItem().getItemMeta().getDisplayName().contentEquals("§4Close"))
    {
    	p.closeInventory();


    } else {
    	
    	World w = Bukkit.getServer().getWorld(e.getCurrentItem().getItemMeta().getLore().get(0).replace("§7World: §c", ""));
    	double x = Double.parseDouble(e.getCurrentItem().getItemMeta().getLore().get(1).replace("§7X: §c", ""));
    	double y = Double.parseDouble(e.getCurrentItem().getItemMeta().getLore().get(2).replace("§7Y: §c", ""));
    	double z = Double.parseDouble(e.getCurrentItem().getItemMeta().getLore().get(3).replace("§7Z: §c", ""));
    	
    	
    	
    	Location tploc = new Location(w, x+0.4, y-2.3, z-0.4);
    	
    	
    	if (tmsf.getString("TeleportMachine.Use.Charge.UseMoney.Enable").equalsIgnoreCase("true")) {
    		if (economy.getBalance(p) >= Integer.parseInt(tmsf.getString("TeleportMachine.Use.Charge.UseMoney.Ammount"))) {
    			economy.withdrawPlayer(p, Integer.parseInt(tmsf.getString("TeleportMachine.Use.Charge.UseMoney.Ammount")));
    			if (tmmf.getString("TeleportTo.Enable").equalsIgnoreCase("true")) {
    				p.sendMessage(tmmf.getString("TeleportTo.Message").replace("&", "§").replace("{player}", p.getName()).replace("{world}", w.getName()).replace("{x}", x+"").replace("{y}", y+"").replace("{z}", z+"").replace("{name}", e.getCurrentItem().getItemMeta().getDisplayName()));
    				
    			}	
    			p.teleport(tploc);
    		} else {
    			
    			p.sendMessage(tmsf.getString("TeleportMachine.Use.Charge.UseMoney.NotEnoughMoney").replace("&", "§").replace("{name}", e.getCurrentItem().getItemMeta().getDisplayName()).replace("{player}", p.getName()).replace("{ammount}", tmsf.getString("TeleportMachine.Use.Charge.UseMoney.Ammount")+"").replace("{pmoney}", ""+economy.getBalance(p)));
    			
    		}
    	
    	} else {
			if (tmmf.getString("TeleportTo.Enable").equalsIgnoreCase("true")) {
				p.sendMessage(tmmf.getString("TeleportTo.Message").replace("&", "§").replace("{name}", e.getCurrentItem().getItemMeta().getDisplayName()).replace("{player}", p.getName()).replace("{world}", w.getName()).replace("{x}", x+"").replace("{y}", y+"").replace("{z}", z+""));
				
			}
    		p.teleport(tploc);
    		
    	}
    	
    	
    }
    
    
    
  }
  
  
  
  @EventHandler
  public void onClickLocations(InventoryClickEvent e)
  {
		File tms = new File("plugins/MyMachines/Machines/TeleporterMachine/Settings.yml");
		YamlConfiguration tmsf = YamlConfiguration.loadConfiguration(tms);
		File tmm = new File("plugins/MyMachines/Machines/TeleporterMachine/Messages.yml");
		YamlConfiguration tmmf = YamlConfiguration.loadConfiguration(tmm);
		
    Player p = (Player) e.getWhoClicked();
	
	if (!e.getInventory().getName().equalsIgnoreCase("§7§m-------§7[§6TeleporterGuiL§7]§m-------")) return;
	if (e.getCurrentItem().getType().equals(Material.AIR) || e.getCurrentItem().getType() == null) return;
    if (!p.getName().contentEquals("§6fucaafawgawgh")) {
    	
    	e.setCancelled(true);
    	
    }
    
    if (e.getCurrentItem().getItemMeta().getDisplayName().contentEquals("§4Close"))
    {
    	p.closeInventory();


} else {
    	
    	World w = Bukkit.getServer().getWorld(e.getCurrentItem().getItemMeta().getLore().get(0).replace("§7World: §c", ""));
    	double x = Double.parseDouble(e.getCurrentItem().getItemMeta().getLore().get(1).replace("§7X: §c", ""));
    	double y = Double.parseDouble(e.getCurrentItem().getItemMeta().getLore().get(2).replace("§7Y: §c", ""));
    	double z = Double.parseDouble(e.getCurrentItem().getItemMeta().getLore().get(3).replace("§7Z: §c", ""));
    	
    	
    	
    	Location tploc = new Location(w, x+0.4, y-2.3, z-0.4);
    	
    	if (tmsf.getString("TeleportMachine.Use.Charge.UseMoney.Enable").equalsIgnoreCase("true")) {
    		if (economy.getBalance(p) >= Integer.parseInt(tmsf.getString("TeleportMachine.Use.Charge.UseMoney.Ammount"))) {
    			economy.withdrawPlayer(p, Integer.parseInt(tmsf.getString("TeleportMachine.Use.Charge.UseMoney.Ammount")));

    			if (tmmf.getString("TeleportTo.Enable").equalsIgnoreCase("true")) {
    				p.sendMessage(tmmf.getString("TeleportTo.Message").replace("&", "§").replace("{player}", p.getName()).replace("{world}", w.getName()).replace("{x}", x+"").replace("{y}", y+"").replace("{z}", z+"").replace("{name}", e.getCurrentItem().getItemMeta().getDisplayName()));
    				
    			}
    			p.teleport(tploc);
    		} else {
    			
    			p.sendMessage(tmsf.getString("TeleportMachine.Use.Charge.UseMoney.NotEnoughMoney").replace("&", "§").replace("{name}", e.getCurrentItem().getItemMeta().getDisplayName()).replace("{player}", p.getName()).replace("{ammount}", tmsf.getString("TeleportMachine.Use.Charge.UseMoney.Ammount")+"").replace("{pmoney}", ""+economy.getBalance(p)));
    			
    		}
    	
    	} else {
			if (tmmf.getString("TeleportTo.Enable").equalsIgnoreCase("true")) {
				p.sendMessage(tmmf.getString("TeleportTo.Message").replace("&", "§").replace("{player}", p.getName()).replace("{world}", w.getName()).replace("{x}", x+"").replace("{y}", y+"").replace("{z}", z+"").replace("{name}", e.getCurrentItem().getItemMeta().getDisplayName()));
				
			}
    		p.teleport(tploc);
    		
    	}
    	
    }
    
    
    
  }


}
