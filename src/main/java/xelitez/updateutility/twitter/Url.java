package xelitez.updateutility.twitter;

public class Url 
{
	public String url;
	public String expanded_url;
	public String display_url;
	
	private Url() {}
	
	public static Url createUrl(String par) 
	{
		Url link = new Url();
		par = par.substring(par.indexOf('{') + 1, par.lastIndexOf('}'));
		String[] args = StringAssistant.splitString(par);
		for(String arg : args)
		{
			if(StringAssistant.getKey(arg).matches("\"url\""))
			{
				link.url = StringAssistant.getStringValue(arg);
			}
			else if(StringAssistant.getKey(arg).matches("\"expanded_url\""))
			{
				link.expanded_url = StringAssistant.getStringValue(arg);
			}
			else if(StringAssistant.getKey(arg).matches("\"display_url\""))
			{
				link.display_url = StringAssistant.getStringValue(arg);
			}
		}
		return link;
	}
}
