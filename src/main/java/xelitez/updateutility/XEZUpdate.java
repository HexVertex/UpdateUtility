package xelitez.updateutility;

import java.io.File;

import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.common.config.Property;
import cpw.mods.fml.client.registry.ClientRegistry;
import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.Mod.Instance;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.relauncher.FMLInjectionData;
import cpw.mods.fml.relauncher.Side;

@Mod(	
		modid = "XEZUpdate",
		name = "XEliteZ Update Utility",
		version = Version.version)
public class XEZUpdate 
{
	@Instance(value = "XEZUpdate")
	public static XEZUpdate instance;
	
	public Configuration c;
	
	@EventHandler
    public void preload(FMLPreInitializationEvent evt)
    {
		evt.getModMetadata().version = Version.getVersion() + " for " + Version.MC;
		c = new Configuration(new File((File)FMLInjectionData.data()[6], "XEliteZ/UpdateUtility.cfg"));
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
		if(FMLCommonHandler.instance().getSide().isClient())
		{
			try
			{
				c.load();
				
				UpdateEventListener.instance.drawMainMenuButton = c.get(Configuration.CATEGORY_GENERAL, "renderMainMenuButton", true).getBoolean(true);
				int drawMode = 0;
				try
				{
					if(UpdateEventListener.instance.drawMainMenuButton)
					{
						Class.forName("com.thevoxelbox.voxelmenu.GuiMainMenuVoxelBox");
						drawMode = 1;
					}
				}
				catch(Exception e)
				{
					
				}
				Property DrawMenuMode = c.get(Configuration.CATEGORY_GENERAL, "mainMenuButtonMode", drawMode);
				DrawMenuMode.comment = "0=Vanilla Minecraft Main Menu; 1=VoxelMenu";
				UpdateEventListener.instance.mainMenuButtonMode = DrawMenuMode.getInt(drawMode);
				Property pause = c.get(Configuration.CATEGORY_GENERAL, "pauseUpdater", false);
				pause.comment = "Set to true if you want the updater/file copier not to close automatically. "
						+ "This would mean that you will be able to read the log, but you will also need to manually "
						+ "close it after it's finished";
				UpdaterThread.pause = pause.getBoolean(false);
				
			} 
			catch(Exception e) {
				e.printStackTrace();
			}
			finally {
				c.save();
			}
		}
		UpdateRegistry.instance().checkForUpdates();
		UpdaterThread.start();
    }
	
}
