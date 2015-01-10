package xelitez.updateutility;

import cpw.mods.fml.client.registry.ClientRegistry;
import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.Mod.Instance;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.relauncher.Side;

@Mod(	
		modid = "XEZUpdate",
		name = "XEliteZ Update Utility",
		version = Version.version)
public class XEZUpdate 
{
	@Instance(value = "XEZUpdate")
	public static XEZUpdate instance;
	
	public static Config configuration = new Config();
	@EventHandler
    public void preload(FMLPreInitializationEvent evt)
    {
		evt.getModMetadata().version = Version.getVersion() + " for " + Version.MC;
		if(evt.getSide() == Side.CLIENT)
		{
			ClientRegistry.registerKeyBinding(UpdateEventListener.instance.button);
			FMLCommonHandler.instance().bus().register(UpdateEventListener.instance);
		}
		UpdateRegistry.addMod(this, new Version());
    }
	
	@EventHandler
    public void postload(FMLPostInitializationEvent evt)
    {
		configuration.initialiseConfig();
		UpdateRegistry.instance().checkForUpdates();
		UpdateRegistry.instance().setupEligibleModList();
		UpdateRegistry.instance().getTweetLists();
		UpdaterThread.start();
    }
	
}
