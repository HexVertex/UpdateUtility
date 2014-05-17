package xelitez.updateutility;

import org.apache.logging.log4j.Level;

import cpw.mods.fml.common.FMLLog;

public class XEZLog 
{
    public static void log(Level level, String format, Object... data)
    {
        FMLLog.log("XEZUpdateUtility", level, String.format(format, data));
    }

    public static void log(Level level, Throwable ex, String format, Object... data)
    {
    	FMLLog.log("XEZUpdateUtility", level, String.format(format, data), ex);
    }

    public static void severe(String format, Object... data)
    {
    	FMLLog.log("XEZUpdateUtility", Level.ERROR, format, data);
    }

    public static void warning(String format, Object... data)
    {
    	FMLLog.log("XEZUpdateUtility", Level.WARN, format, data);
    }

    public static void info(String format, Object... data)
    {
    	FMLLog.log("XEZUpdateUtility", Level.INFO, format, data);
    }

    public static void fine(String format, Object... data)
    {
    	FMLLog.log("XEZUpdateUtility", Level.DEBUG, format, data);
    }

    public static void finer(String format, Object... data)
    {
    	FMLLog.log("XEZUpdateUtility", Level.TRACE, format, data);
    }
}
