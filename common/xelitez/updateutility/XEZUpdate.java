package xelitez.updateutility;

import java.util.EnumSet;
import java.util.logging.Logger;

import org.lwjgl.input.Keyboard;

import cpw.mods.fml.client.FMLClientHandler;
import cpw.mods.fml.client.registry.KeyBindingRegistry;
import cpw.mods.fml.client.registry.KeyBindingRegistry.KeyHandler;
import cpw.mods.fml.common.FMLLog;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.TickType;
import cpw.mods.fml.common.Mod.*;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.network.NetworkRegistry;
import cpw.mods.fml.common.registry.TickRegistry;
import cpw.mods.fml.relauncher.Side;

@Mod(	
		modid = "XEZUpdate",
		name = "XEliteZ Update Utility",
		acceptedMinecraftVersions = "[1.4.7]",
		version = "1.0.0")
public class XEZUpdate 
{
	@Instance(value = "XEZUpdate")
	public static XEZUpdate instance;
	
	@PreInit
    public void preload(FMLPreInitializationEvent evt)
    {
		evt.getModMetadata().version = Version.getVersion() + " for " + Version.MC;
		XEZLog.registerLogger();
		TickRegistry.registerTickHandler(UpdateTicker.getInstance(), Side.SERVER);
		if(evt.getSide().isClient())
		{
			TickRegistry.registerTickHandler(UpdateTicker.getInstance(), Side.SERVER);
		}
		NetworkRegistry.instance().registerConnectionHandler(UpdateTicker.getInstance());
		UpdateRegistry.addMod(this, new Version());
    }
	
	@PostInit
    public void postload(FMLPostInitializationEvent evt)
    {
		UpdateRegistry.instance().checkForUpdates();
		KeyBindingRegistry.registerKeyBinding(UpdateKeyHandler.instance());
    }
	
}
