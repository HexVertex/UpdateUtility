package xelitez.updateutility;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import xelitez.updateutility.UpdateRegistry.ModInstance;
import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.relauncher.FMLInjectionData;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.common.config.Property;

public class Config 
{
	public Configuration c;
	
	public void initialiseConfig()
	{
		c = new Configuration(new File((File)FMLInjectionData.data()[6], "XEliteZ/UpdateUtility.cfg"));
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
	}
	
	public List<ModInstance> getEnabledModsFromList(List<ModInstance> modList)
	{
		List<ModInstance> enabledMods = new ArrayList<ModInstance>();
		try
		{
			c.load();
			for(ModInstance mod : modList)
			{
				if(c.get("Twitter Mods", mod.mod.getModId(), true).getBoolean(true))
				{
					enabledMods.add(mod);
				}
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		finally
		{
			c.save();
		}
		return enabledMods;
	}
	
	public void toggleModAvailibility(ModInstance mod)
	{
		try
		{
			c.load();
			Property prop = c.get("Twitter Mods", mod.mod.getModId(), true);
			prop.setValue(!prop.getBoolean(true));
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		finally
		{
			c.save();
		}
	}
}
