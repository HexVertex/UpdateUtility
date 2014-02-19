package xelitez.updateutility;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import org.lwjgl.opengl.Display;

import cpw.mods.fml.relauncher.FMLInjectionData;

public class UpdaterThread
{
	public static List<String> stringsToLookFor = new ArrayList<String>();
	public static List<File> filesToMove = new ArrayList<File>();
	static String dispTitle = "Minecraft";
	
	public static enum OS
	{
		LINUX, SOLARIS, WINDOWS, MACOS, UNKNOWN;
	}

	public static OS getPlatform()
	{
		String str = System.getProperty("os.name").toLowerCase();
		if (str.contains("win")) return OS.WINDOWS;
		if (str.contains("mac")) return OS.MACOS;
		if (str.contains("solaris")) return OS.SOLARIS;
		if (str.contains("sunos")) return OS.SOLARIS;
		if (str.contains("linux")) return OS.LINUX;
		if (str.contains("unix")) return OS.LINUX;
		return OS.UNKNOWN;
	}
	
	public static void start()
	{
		dispTitle = Display.getTitle();
	    InputStream source = null;
	    FileOutputStream destination = null;
		try
		{
			File fuDir = new File((File)FMLInjectionData.data()[6], "XEliteZ");
			if(!fuDir.exists())
			{
				fuDir.mkdirs();
			}
			File fu = new File((File)FMLInjectionData.data()[6], "XEliteZ/FileUtility.jar");
			if(fu.exists())
			{
				fu.delete();
			}
			source = UpdaterThread.class.getResourceAsStream("/assets/uu/xelitez/FileUtility.jar");
			if(source == null)
			{
				throw new Exception("Failed to copy XEliteZ File Utility, please report this error to the mod developper");
			}
			destination = new FileOutputStream(fu);
			byte[] buffer = new byte[153600];
			int bytesRead = 0;
			while ((bytesRead = source.read(buffer)) > 0) 
			{ 
				destination.write(buffer, 0, bytesRead);
				buffer = new byte[153600];
			}
	        if(source != null) {
	            source.close();
	        }
	        if(destination != null) {
	            destination.close();
	        }
			fu.setExecutable(true);
			XEZLog.info("Successfully copied XEliteZ FileUtility");
		}
		catch(Exception e)
		{
			e.printStackTrace();
			return;
		}
		new Thread()
		{
			public void run()
			{
				this.setName("Updater Test Thread");
				while(true)
				{
					if(!Display.isCreated() && filesToMove.size() > 0)
					{
						UpdaterThread.runCopier();
						break;
					}
				}
			}
		}.start();
	}
	
	public static void runCopier()
	{
		if(stringsToLookFor.size() > 0 || filesToMove.size() > 0)
		{
			try
			{
				String baseDir = ((File)FMLInjectionData.data()[6]).getCanonicalPath().replaceAll("\\\\", "/");
				
				ArrayList<String> strings = new ArrayList<String>();
				
				strings.add("java");
				strings.add("-jar");
				strings.add(baseDir + "/XEliteZ/FileUtility.jar");
				strings.add("-f");
				strings.add(baseDir);
				if(stringsToLookFor.size() != 0)
				{
					strings.add("-s");
					String filesToRemove = "";
					for(String str : stringsToLookFor)
					{
						filesToRemove += str;
						filesToRemove += ",";
					}
					strings.add(filesToRemove.substring(0, filesToRemove.length() - 1));
				}
				if(filesToMove.size() != 0)
				{
					strings.add("-m");
					String files = "";
					for(File f : filesToMove)
					{
						files += f.getCanonicalPath().replaceAll("\\\\", "/");
						files += ",";
					}
					strings.add(files.substring(0, files.length() - 1));
				}
				if(Display.getTitle() != null)
				{
					strings.add("-t");
					strings.add(dispTitle);
				}
				ProcessBuilder localProcessBuilder = new ProcessBuilder(strings);
				Process localProcess = localProcessBuilder.start();
				if (localProcess == null) throw new Exception("Failed to start XEliteZ File Utility");
			}
			catch(Exception e)
			{
				e.printStackTrace();
			}
		}
	}
	
}
