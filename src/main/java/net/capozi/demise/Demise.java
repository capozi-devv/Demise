package net.capozi.demise;

import net.capozi.demise.common.GameruleRegistry;
import net.capozi.demise.common.entity.EntityTypeRegistry;
import net.fabricmc.api.ModInitializer;
import net.minecraft.util.Identifier;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Demise implements ModInitializer {
	public static final String MOD_ID = "demise";
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	@Override
	public void onInitialize() {
		EntityTypeRegistry.register();
        GameruleRegistry.register();
	}
	public static Identifier id(String name) {
		return Identifier.of(MOD_ID, name);
	}
}