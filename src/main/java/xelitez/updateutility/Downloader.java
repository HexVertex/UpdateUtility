package xelitez.updateutility;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLDecoder;
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
			String strr = "";
			URLConnection connection = url.openConnection();
			String disposition = connection.getHeaderField("Content-Disposition");
 
            if (disposition != null) {
                // extracts file name from header field
                int index = disposition.indexOf("filename=");
                if (index > 0) {
                	strr = disposition.substring(index + 10,
                            disposition.length() - 1);
                }
            } else {
                // extracts file name from URL
            	strr = URLDecoder.decode(url.getFile().substring(url.getFile().lastIndexOf("/")), "UTF-8");
            	if(strr.contains("?"))
    			{	
    				strr = strr.substring(0, strr.indexOf("?"));
    			};
            }
            boolean containsString = false;
            for(String del : UpdateRegistry.instance().getMod(selectedMod).update.stringsToDelete())
            {
            	if(strr.contains(del))
            	{
            		containsString = true;
            		break;
            	}
            }
            if(!containsString)
            {
            	XEZLog.warning("Update for " + UpdateRegistry.instance().getMod(selectedMod).mod.getName() + " does not contain the name that will normally be deleted by the updater");
            	XEZLog.warning("This may cause problems, if so you will have to remove the mod file(" + strr + ") manually next update");
            	
            }
			File downloadsDir = new File((File)FMLInjectionData.data()[6], "XEliteZ/downloads");
			if(!downloadsDir.getCanonicalFile().exists());
			{
				downloadsDir.getCanonicalFile().mkdirs();
			}
			File file = new File((File)FMLInjectionData.data()[6], 
					"XEliteZ/downloads/" + strr);
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
			
			for(String del : UpdateRegistry.instance().getMod(mod).update.stringsToDelete())
			{
				UpdaterThread.stringsToLookFor.add(del);
			}
			gui.updateDownloadBar(100, new StringBuilder().append("Download complete (").append(totalBytesRead / 1024).append(" kB) - Requires Minecraft to restart").toString(), false);
		}
		catch (Exception e) 
		{
			gui.updateDownloadBar(0, "Download failed", false);
		}
	}	
	
}
