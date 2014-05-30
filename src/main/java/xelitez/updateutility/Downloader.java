package xelitez.updateutility;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;

import cpw.mods.fml.relauncher.FMLInjectionData;

public class Downloader 
{
	private static GuiUpdates guiu = null;
	private static int mod = 0;
	
	public static void download(GuiUpdates guiUpdates, int selectedMod)
	{
		guiu = guiUpdates;
		mod = selectedMod;
		guiu.setDownloading(true);
		new Thread("XEZDownloader Thread")
    	{
    		public void run()
    		{
				URL url = null;
				try
				{
			    	List<String> strings = new ArrayList<String>();
					String urls = UpdateRegistry.instance().getMod(mod).update.getDownloadUrl();
					if(urls.matches("")) throw new Exception("No download URL given");
					URL urltemp = new URL(urls);
					if(urls.contains("bit.ly"))
					{
						URLConnection urlc = urltemp.openConnection();
			    		BufferedReader in = new BufferedReader(new InputStreamReader(urlc.getInputStream()));
			    		String str;
			    		
			    		while ((str = in.readLine()) != null)
			    		{
			    			strings.add(str);
			    		}
			    		
			    		in.close();
			    		for(String str1 : strings)
			    		{
			    			if(str1.contains("<a href=\""))
			    			{
			    				urltemp = new URL(str1.substring(str1.indexOf("<a href=\"") + 9, str1.indexOf("\">")));
			    			}
			    		}
					}
					url = urltemp;
				}
				catch(Exception e)
				{
					guiu.setDownloading(false);
					return;
				}
				Downloader.Download(guiu, url, mod);
				guiu.setDownloading(false);
    		}
    	}.start();
	}
	
	public static void Download(GuiUpdates gui, URL url, int selectedMod)
	{
		try 
		{
			
			gui.updateDownloadBar(0, "Connecting...", true);
			String strr = UpdateRegistry.instance().getMod(selectedMod).update.stringToDelete();
			URLConnection connection = url.openConnection();
			File downloadsDir = new File((File)FMLInjectionData.data()[6], "XEliteZ/downloads");
			if(!downloadsDir.getCanonicalFile().exists());
			{
				downloadsDir.getCanonicalFile().mkdirs();
			}
			File file = new File((File)FMLInjectionData.data()[6], 
					"XEliteZ/downloads/" + strr + ".jar");
			InputStream reader = url.openStream();
			FileOutputStream writer = new FileOutputStream(file);
			byte[] buffer = new byte[153600];
			int totalBytesRead = 0;
			int bytesRead = 0;
			int totalSize = connection.getContentLength();
			
			
			while ((bytesRead = reader.read(buffer)) > 0) 
			{  
				writer.write(buffer, 0, bytesRead);
				buffer = new byte[153600];
				totalBytesRead += bytesRead;
				gui.updateDownloadBar(totalBytesRead*100/totalSize, new StringBuilder().append("Downloading - ").append(totalBytesRead*100/totalSize).append("% complete").toString(), false);
			}
			reader.close();
			writer.close();
			
			UpdaterThread.filesToMove.add(file);
			
			UpdaterThread.stringsToLookFor.add(UpdateRegistry.instance().getMod(mod).update.stringToDelete());
			gui.updateDownloadBar(100, new StringBuilder().append("Download complete (").append(totalBytesRead / 1024).append(" kB) - Requires Minecraft to restart").toString(), false);
		}
		catch (Exception e) 
		{
			gui.updateDownloadBar(0, "Download failed", false);
		}
	}	
	
}
