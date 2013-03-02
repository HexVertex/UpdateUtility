package xelitez.updateutility;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;

import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.Loader;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.ModContainer;

public class UpdateRegistry 
{
	private static List<ModInstance> mods = new ArrayList<ModInstance>();
	
	private static UpdateRegistry instance = new UpdateRegistry();
	
	public static UpdateRegistry instance()
	{
		return instance;
	}
	
	public static void addMod(Object mod, Object update)
	{
		if(update instanceof IXEZUpdate)
		{
			ModContainer mc = FMLCommonHandler.instance().findContainerFor(mod);
			if(mc == null)
			{
				XEZLog.log(Level.WARNING, new StringBuilder().append("Invalid mod trying to add(").append(mod).append(")").toString());
			}
			else
			{
				instance.mods.add(new ModInstance(mc, (IXEZUpdate)update));
				XEZLog.log(Level.INFO, "The mod " + mc.getName() + " has been successfully registered to XEZUpdateUtility");
			}
		}
		else
		{
			ModContainer mc = FMLCommonHandler.instance().findContainerFor(mod);
			if(mc == null)
			{
				XEZLog.log(Level.WARNING, new StringBuilder().append("Invalid mod trying to add(").append(mod).append(")").toString());
			}
			else
			{
				XEZLog.log(Level.WARNING, "The mod " + mc.getName() + " does not provide a valid upadate class");
			}
		}
	}
	
	public int getModAmount()
	{
		return mods.size();
	}
	
	public void updateModVersionData()
	{
		for(int i = 0;i < mods.size();i++)
		{
			this.getModCurrentVersion(i);
			this.getModNewVersion(i);
		}
	}
	
	public void checkForUpdates()
	{
		new Thread()
		{
			public void run()
			{
				for(int i = 0;i < mods.size();i++)
				{
					try
					{
						if(mods.get(i).update.doesModCheckForUpdates())
						{
							XEZLog.info("Checking for updates of " + mods.get(i).mod.getName() + "...");
							mods.get(i).update.checkForUpdates();
						}
					} catch(Exception e)
					{
						XEZLog.warning("Unable to check for updates of " + mods.get(i).mod.getName());
					}
				}
			}
		}.start();
	}
	
	public String getModCurrentVersion(int i)
	{
		return mods.get(i).update.getCurrentVersion();
	}
	
	public String getModNewVersion(int i)
	{
		return mods.get(i).update.getNewVersion();
	}
	
	public String getModName(int i)
	{
		return mods.get(i).mod.getName();
	}
	
	public ModInstance getMod(int i)
	{
		return mods.get(i);
	}
	
	public static class ModInstance
	{
		ModContainer mod;
		IXEZUpdate update;
		
		public ModInstance(ModContainer mod, IXEZUpdate update)
		{
			this.mod = mod;
			this.update = update;
		}
	}
}
