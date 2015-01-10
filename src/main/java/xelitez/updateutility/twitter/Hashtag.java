package xelitez.updateutility.twitter;

public class Hashtag 
{
	public String text;

	public static Hashtag createHashTag(String par) 
	{
		Hashtag hashtag = new Hashtag();
		par = par.substring(par.indexOf("{") + 1, par.lastIndexOf("}"));
		String[] args = StringAssistant.splitString(par);
		for(String arg : args)
		{
			if(StringAssistant.getKey(arg).matches("\"text\""))
			{
				hashtag.text = StringAssistant.getStringValue(arg);
			}
		}
		return hashtag;
	}
}
