package com.raus.petSaver;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.entity.EntityType;
import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin
{
	private static Main main;
	
	private static Boolean requireNameTag;
	private static Boolean giveInjury;
	private static double healInjury;
	private static List<EntityType> pets = new ArrayList<EntityType>();
	
	public Main()
	{
		main = this;
	}
	
	@Override
	public void onEnable()
	{
		// Safe default stuff
		saveDefaultConfig();
		
		// Register command
		this.getCommand("petsaver").setExecutor(new ReloadCommand());
		
		// First time load
		reload();
		
		// Listeners
		getServer().getPluginManager().registerEvents(new DeathListener(), this);
		getServer().getPluginManager().registerEvents(new HealListener(), this);
	}
	
	@Override
	public void onDisable()
	{
		
	}
	
	public static Main getInstance()
	{
		return main;
	}
	
	public static Boolean requireNameTag()
	{
		return requireNameTag;
	}
	
	public static Boolean giveInjury()
	{
		return giveInjury;
	}
	
	public static double healInjury()
	{
		return healInjury;
	}
	
	public static Boolean isPet(EntityType type)
	{
		return pets.contains(type);
	}
	
	public void reload()
	{
		// Prepare
		reloadConfig();
		pets.clear();
		
		// Read config
		requireNameTag = getConfig().getBoolean("requireNameTag");
		giveInjury = getConfig().getBoolean("giveInjury");
		healInjury = getConfig().getDouble("healInjury");
		
		// Parse string list
		List<String> list = getConfig().getStringList("pets");
		for (int i = 0; i < list.size(); ++i)
		{
			pets.add(EntityType.valueOf(list.get(i)));
		}
	}
}