package xelitez.updateutility.twitter;

public class User 
{
	public long id;
	public String id_str;
	public String name;
	public String screen_name;
	public String location;
	public String description;
	public String url;
	public Entities entities;
	public boolean isProtected;
	public long followers_count;
	public long friends_count;
	public String created_at;
	public long favourites_count;
	public int utc_offset;
	public String time_zone;
	public boolean verified;
	public long statuses_count;
	
	private User() {}
	
	public static User createUser(String par) 
	{
		User user = new User();
		par = par.substring(par.indexOf("{") + 1, par.lastIndexOf("}"));
		String[] args = StringAssistant.splitString(par);
		for(String arg : args)
		{
			if(StringAssistant.getKey(arg).matches("\"id\""))
			{
				user.id = StringAssistant.getLongValue(arg);
			}
			else if(StringAssistant.getKey(arg).matches("\"id_str\""))
			{
				user.id_str = StringAssistant.getStringValue(arg);
			}
			else if(StringAssistant.getKey(arg).matches("\"name\""))
			{
				user.name = StringAssistant.getStringValue(arg);
			}
			else if(StringAssistant.getKey(arg).matches("\"screen_name\""))
			{
				user.screen_name = StringAssistant.getStringValue(arg);
			}
			else if(StringAssistant.getKey(arg).matches("\"location\""))
			{
				user.location = StringAssistant.getStringValue(arg);
			}
			else if(StringAssistant.getKey(arg).matches("\"description\""))
			{
				user.description = StringAssistant.getStringValue(arg);
			}
			else if(StringAssistant.getKey(arg).matches("\"url\""))
			{
				user.url = StringAssistant.getStringValue(arg);
			}
			else if(StringAssistant.getKey(arg).matches("\"entities\""))
			{
				user.entities = Entities.createEntities(arg);
			}
			else if(StringAssistant.getKey(arg).matches("\"isProtected\""))
			{
				user.isProtected = StringAssistant.getBooleanValue(arg);
			}
			if(StringAssistant.getKey(arg).matches("\"followers_count\""))
			{
				user.followers_count = StringAssistant.getLongValue(arg);
			}
			if(StringAssistant.getKey(arg).matches("\"friends_count\""))
			{
				user.friends_count = StringAssistant.getLongValue(arg);
			}
			else if(StringAssistant.getKey(arg).matches("\"created_at\""))
			{
				user.created_at = StringAssistant.getStringValue(arg);
			}
			if(StringAssistant.getKey(arg).matches("\"favourites_count\""))
			{
				user.favourites_count = StringAssistant.getLongValue(arg);
			}
			if(StringAssistant.getKey(arg).matches("\"utc_offset\""))
			{
				user.utc_offset = (int)StringAssistant.getLongValue(arg);
			}
			else if(StringAssistant.getKey(arg).matches("\"time_zone\""))
			{
				user.time_zone = StringAssistant.getStringValue(arg);
			}
			else if(StringAssistant.getKey(arg).matches("\"verified\""))
			{
				user.verified = StringAssistant.getBooleanValue(arg);
			}
			else if(StringAssistant.getKey(arg).matches("\"statuses_count\""))
			{
				user.statuses_count = StringAssistant.getLongValue(arg);
			}
		}
		return user;
	}
}
