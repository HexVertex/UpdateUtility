package xelitez.updateutility.twitter;

import java.util.ArrayList;
import java.util.List;

public class StringAssistant
{
	public static String[] splitString(String arg)
	{
		return splitStringIntoList(arg).toArray(new String[0]);
	}
	
	private static List<String> splitStringIntoList(String arg)
	{
		List<String> list = new ArrayList<String>();
		int bracketCounter = 0;
		int listCounter = 0;
		boolean argTracker = true;
		for(int i = 0;i < arg.length();i++)
		{
			char c = arg.charAt(i);
			if(c == '\u007b' && argTracker && listCounter == 0)
			{
				bracketCounter++;
				continue;
			}
			if(c == '\u007d' && argTracker && listCounter == 0)
			{
				bracketCounter--;
				continue;
			}
			if(c == '\u0022' && listCounter == 0 && bracketCounter == 0)
			{
				argTracker = !argTracker;
				continue;
			}
			if(c == '\u005b' && argTracker && bracketCounter == 0)
			{
				listCounter++;
				continue;
			}
			if(c == '\u005d' && argTracker && bracketCounter == 0)
			{
				listCounter--;
				continue;
			}
			if(bracketCounter == 0 && listCounter == 0 && argTracker && c == '\u002c')
			{
				list.add(arg.substring(0, i));
				arg = arg.substring(i + 1);
				list.addAll(splitStringIntoList(arg));
				break;
			}
		}
		return list;
	}
	
	public static String getStringValue(String string)
	{
		return getStringValue(string, true);
	}
	
	public static String getStringValue(String string, boolean warn)
	{
		String value = string.substring(string.indexOf(":") + 1);
		if(value.matches("null"))
		{
			value = null;
		}
		else if(value.contains("\"") && !value.contains("{"))
		{
			value = value.substring(value.indexOf("\"") + 1, value.lastIndexOf("\""));
		}
		else if(warn)
		{
			System.out.println("Value (" + value + ") is not a String");
		}
		return value;
	}
	
	public static long getLongValue(String string)
	{
		String value = getStringValue(string, false);
		if(value == null)
		{
			return 0L;
		}
		return Long.parseLong(value);
	}
	
	public static boolean getBooleanValue(String string)
	{
		return Boolean.parseBoolean(getStringValue(string, false));
	}
	
	public static String getKey(String arg)
	{
		return arg.substring(0, arg.indexOf("\u003a"));
	}
}
