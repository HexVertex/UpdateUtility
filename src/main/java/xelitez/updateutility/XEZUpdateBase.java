package xelitez.updateutility;

import xelitez.updateutility.twitter.TwitterInstance;

public abstract class XEZUpdateBase
{
	public abstract String getCurrentVersion();
	
	public abstract String getNewVersion();
	
	public abstract void checkForUpdates();
	
	public abstract boolean doesModCheckForUpdates();
	
	public abstract boolean isUpdateAvailable();
	
	public abstract String getModIcon();
	
	public abstract String getUpdateUrl();
	
	public abstract String getDownloadUrl();
	
	public abstract String[] stringsToDelete();
	
	public TwitterInstance getTInstance()
	{
		return null;
	}
}
