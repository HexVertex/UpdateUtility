package xelitez.updateutility;

import java.io.File;
import net.minecraftforge.common.Configuration;
import net.minecraftforge.common.Property;
import cpw.mods.fml.client.registry.KeyBindingRegistry;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.*;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.network.NetworkRegistry;
import cpw.mods.fml.common.registry.TickRegistry;
import cpw.mods.fml.relauncher.FMLInjectionData;
import cpw.mods.fml.relauncher.Side;

@Mod(	
		modid = "XEZUpdate",
		name = "XEliteZ Update Utility",
		version = "1.1")
public class XEZUpdate 
{
	@Instance(value = "XEZUpdate")
	public static XEZUpdate instance;
	
	public Configuration c;
	
	@PreInit
    public void preload(FMLPreInitializationEvent evt)
    {
		evt.getModMetadata().version = Version.getVersion() + " for " + Version.MC;
		XEZLog.registerLogger();
		c = new Configuration(new File((File)FMLInjectionData.data()[6], "XEliteZ/UpdateUtility.cfg"));
		
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
		try
		{
			c.load();
			
			UpdateTicker.getInstance().drawMainMenuButton = c.get(Configuration.CATEGORY_GENERAL, "renderMainMenuButton", true).getBoolean(true);
			int drawMode = 0;
			try
			{
				if(UpdateTicker.getInstance().drawMainMenuButton)
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
			UpdateTicker.getInstance().mainMenuButtonMode = DrawMenuMode.getInt(drawMode);
			
		} 
		catch(Exception e) {
			e.printStackTrace();
		}
		finally {
			c.save();
		}
		UpdateRegistry.instance().checkForUpdates();
		if(evt.getSide().isClient())
		{
			KeyBindingRegistry.registerKeyBinding(UpdateKeyHandler.instance());
		}
		UpdaterThread.start();
    }
	
}
