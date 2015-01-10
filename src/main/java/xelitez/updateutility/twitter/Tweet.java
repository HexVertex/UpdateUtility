package xelitez.updateutility.twitter;

public class Tweet 
{
	public String created_at;
	public long id;
	public String id_str;
	public String text;
	public String source;
	public boolean trauncated;
	public long in_reply_to_status_id;
	public String in_reply_to_status_id_str;
	public long in_reply_to_user_id;
	public String in_reply_to_user_id_str;
	public String in_reply_to_screen_name;
	public User user;
	public long retweet_count;
	public long favorite_count;
	public Entities entities;
	
	private Tweet() {}
	
	public static Tweet CreateTweet(String par)
	{
		Tweet tweet = new Tweet();
		par = par.substring(par.indexOf('{') + 1, par.lastIndexOf('}'));
		String[] args = StringAssistant.splitString(par);
		for(String arg : args)
		{
			if(StringAssistant.getKey(arg).matches("\"created_at\""))
			{
				tweet.created_at = StringAssistant.getStringValue(arg);
			}
			else if(StringAssistant.getKey(arg).matches("\"id\""))
			{
				tweet.id = StringAssistant.getLongValue(arg);
			}
			else if(StringAssistant.getKey(arg).matches("\"id_str\""))
			{
				tweet.id_str = StringAssistant.getStringValue(arg);
			}
			else if(StringAssistant.getKey(arg).matches("\"text\""))
			{
				tweet.text = StringAssistant.getStringValue(arg);
			}
			else if(StringAssistant.getKey(arg).matches("\"source\""))
			{
				tweet.source = StringAssistant.getStringValue(arg);
			}
			else if(StringAssistant.getKey(arg).matches("\"trauncated\""))
			{
				tweet.trauncated = StringAssistant.getBooleanValue(arg);
			}
			else if(StringAssistant.getKey(arg).matches("\"in_reply_to_status_id\""))
			{
				tweet.in_reply_to_status_id = StringAssistant.getLongValue(arg);
			}
			else if(StringAssistant.getKey(arg).matches("\"in_reply_to_status_id_str\""))
			{
				tweet.in_reply_to_status_id_str = StringAssistant.getStringValue(arg);
			}
			else if(StringAssistant.getKey(arg).matches("\"\"in_reply_to_user_id\"\""))
			{
				tweet.in_reply_to_user_id = StringAssistant.getLongValue(arg);
			}
			else if(StringAssistant.getKey(arg).matches("\"in_reply_to_user_id_str\""))
			{
				tweet.in_reply_to_user_id_str = StringAssistant.getStringValue(arg);
			}
			else if(StringAssistant.getKey(arg).matches("\"in_reply_to_screen_name\""))
			{
				tweet.in_reply_to_screen_name = StringAssistant.getStringValue(arg);
			}
			else if(StringAssistant.getKey(arg).matches("\"user\""))
			{
				tweet.user = User.createUser(arg);
			}
			else if(StringAssistant.getKey(arg).matches("\"retweet_count\""))
			{
				tweet.retweet_count = StringAssistant.getLongValue(arg);
			}
			else if(StringAssistant.getKey(arg).matches("\"favorite_count\""))
			{
				tweet.favorite_count = StringAssistant.getLongValue(arg);
			}
			else if(StringAssistant.getKey(arg).matches("\"entities\""))
			{
				tweet.entities = Entities.createEntities(arg);
			}
		}
		return tweet;
	}
}
