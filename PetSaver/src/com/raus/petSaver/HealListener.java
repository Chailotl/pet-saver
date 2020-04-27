package com.raus.petSaver;

import org.bukkit.attribute.Attribute;
import org.bukkit.entity.LivingEntity;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityRegainHealthEvent;
import org.bukkit.potion.PotionEffectType;

public class HealListener implements Listener
{
	@EventHandler
	public void onPreDeath(EntityRegainHealthEvent event)
	{
		if (!Main.giveInjury())
		{
			// Do nothing
			return;
		}
		
		// Is it a valid pet?
		if ((event.getEntity().getCustomName() != null || !Main.requireNameTag()) && Main.isPet(event.getEntityType()))
		{
			// Getting health
			LivingEntity pet = (LivingEntity)event.getEntity();
			
			// Would the pet be healed above requisite amount?
			if (pet.getHealth() + event.getAmount() > pet.getAttribute(Attribute.GENERIC_MAX_HEALTH).getValue() * Main.healInjury())
			{
				// Remove "injury"
				pet.removePotionEffect(PotionEffectType.SLOW);
				pet.removePotionEffect(PotionEffectType.WEAKNESS);
			}
		}
	}
}