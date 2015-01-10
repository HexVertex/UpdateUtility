package xelitez.updateutility;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.logging.log4j.Level;

import xelitez.updateutility.twitter.Tweet;
import xelitez.updateutility.twitter.TwitterManager;
import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.ModContainer;

public class UpdateRegistry 
{
	private static List<ModInstance> mods = new ArrayList<ModInstance>();
	
	private static UpdateRegistry instance = new UpdateRegistry();
	
	public HashMap<ModInstance, List<Tweet>> tweets;
	private HashMap<ModInstance, Byte> tweetsEnabled = new HashMap<ModInstance, Byte>();
	
	protected TwitterManager tManager = new TwitterManager();
	
	public static UpdateRegistry instance()
	{
		return instance;
	}
	
	public static void addMod(Object mod, Object update)
	{
		if(update instanceof XEZUpdateBase)
		{
			ModContainer mc = FMLCommonHandler.instance().findContainerFor(mod);
			if(mc == null)
			{
				XEZLog.log(Level.WARN, new StringBuilder().append("Invalid mod trying to add(").append(mod).append(")").toString());
			}
			else
			{
				UpdateRegistry.mods.add(new ModInstance(mc, (XEZUpdateBase)update));
				XEZLog.log(Level.INFO, "The mod " + mc.getName() + " has been successfully registered to XEZUpdateUtility");
			}
		}
		else
		{
			ModContainer mc = FMLCommonHandler.instance().findContainerFor(mod);
			if(mc == null)
			{
				XEZLog.log(Level.WARN, new StringBuilder().append("Invalid mod trying to add(").append(mod).append(")").toString());
			}
			else
			{
				XEZLog.log(Level.WARN, "The mod " + mc.getName() + " does not provide a valid upadate class");
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
		new Thread("XEZUpdate Thread")
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
							if(mods.get(i).update.isUpdateAvailable() && FMLCommonHandler.instance().getSide().isServer())
							{
								XEZLog.info("A new version of " + mods.get(i).mod.getName() + " is available.");
							}
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
	
	public int getNumberOfModUpdatesAvailable()
	{
		int updates = 0;
		for(ModInstance mod : mods)
		{
			if(mod.update.isUpdateAvailable())
			{
				updates++;
			}
		}
		return updates;
	}
	
	public void setupEligibleModList()
	{
		List<ModInstance> potentialInstances = new ArrayList<ModInstance>();
		for(ModInstance instance : mods)
		{
			if(instance.update.getTInstance() != null)
			{
				potentialInstances.add(instance);
			}
			else
			{
				tweetsEnabled.put(instance, (byte)0);
			}
		}
		List<ModInstance> eligableInstances = XEZUpdate.configuration.getEnabledModsFromList(potentialInstances);
		for(ModInstance instance : potentialInstances)
		{
			if(eligableInstances.contains(instance))
			{
				tweetsEnabled.put(instance, (byte)2);
			}
			else
			{
				tweetsEnabled.put(instance, (byte)1);
			}
		}
	}
	
	public void getTweetLists()
	{
		tweets = new HashMap<ModInstance, List<Tweet>>();
		for(ModInstance instance : mods)
		{
			if(shouldGetTweets(instance))
			{
				tweets.put(instance, tManager.makeRequest(instance.update.getTInstance())) ;
			}
		}
	}
	
	public boolean shouldGetTweets(ModInstance mod)
	{
		if(tweetsEnabled.get(mod) == (byte)2)
		{
			return true;
		}
		return false;
	}
	
	public boolean getIsTwitterAvailable(ModInstance mod)
	{
		if(tweetsEnabled.get(mod) == (byte)0)
		{
			return false;
		}
		return true;
	}
	
	public void toggleModAvailibility(ModInstance mod)
	{
		XEZUpdate.configuration.toggleModAvailibility(mod);
		this.setupEligibleModList();
	}
	
	public static class ModInstance
	{
		ModContainer mod;
		XEZUpdateBase update;
		
		public ModInstance(ModContainer mod, XEZUpdateBase update)
		{
			this.mod = mod;
			this.update = update;
		}
	}

}
