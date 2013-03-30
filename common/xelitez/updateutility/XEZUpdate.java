package xelitez.updateutility;

import cpw.mods.fml.client.registry.KeyBindingRegistry;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.*;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.network.NetworkRegistry;
import cpw.mods.fml.common.registry.TickRegistry;
import cpw.mods.fml.relauncher.Side;

@Mod(	
		modid = "XEZUpdate",
		name = "XEliteZ Update Utility",
		acceptedMinecraftVersions = "[1.5.1]",
		version = "1.0")
public class XEZUpdate 
{
	@Instance(value = "XEZUpdate")
	public static XEZUpdate instance;
	
	@PreInit
    public void preload(FMLPreInitializationEvent evt)
    {
		evt.getModMetadata().version = Version.getVersion() + " for " + Version.MC;
		XEZLog.registerLogger();
		if(evt.getSide().isClient())
		{
			TickRegistry.registerTickHandler(UpdateTicker.getInstance(), Side.CLIENT);
		}
		NetworkRegistry.instance().registerConnectionHandler(new UpdateConnectionHandler());
		UpdateRegistry.addMod(this, new Version());
    }
	
	@PostInit
    public void postload(FMLPostInitializationEvent evt)
    {
		UpdateRegistry.instance().checkForUpdates();
		if(evt.getSide().isClient())
		{
			KeyBindingRegistry.registerKeyBinding(UpdateKeyHandler.instance());
		}
    }
	
}
