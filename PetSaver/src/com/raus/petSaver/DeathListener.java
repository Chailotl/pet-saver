package com.raus.petSaver;

import org.bukkit.entity.LivingEntity;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class DeathListener implements Listener
{
	@EventHandler
	public void onPreDeath(EntityDamageEvent event)
	{
		// Is it a valid pet?
		if ((event.getEntity().getCustomName() != null || !Main.requireNameTag()) && Main.isPet(event.getEntityType()))
		{
			// Getting health
			LivingEntity pet = (LivingEntity)event.getEntity();
			
			// Would the pet get killed by the attack?
			if (pet.getHealth() - event.getDamage() <= 0)
			{
				// Bring health down to 1
				event.setDamage(Math.max(pet.getHealth() - 1, 0));
				
				if (Main.giveInjury())
				{
					// Make them "injured"
					pet.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, Integer.MAX_VALUE, 5, false));
					pet.addPotionEffect(new PotionEffect(PotionEffectType.WEAKNESS, Integer.MAX_VALUE, 100, false));
				}
			}
		}
	}
}