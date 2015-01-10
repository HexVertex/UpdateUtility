package xelitez.updateutility.twitter;

import java.util.ArrayList;
import java.util.List;

public class Entities 
{
	public Hashtag[] Hashtags;
	public User[] user_mentions;
	public Url[] urls;
	
	private Entities() {}
	
	public static Entities createEntities(String par) 
	{
		Entities entities = new Entities();
		par = par.substring(par.indexOf('{') + 1, par.lastIndexOf('}'));
		String[] args = StringAssistant.splitString(par);
		for(String arg : args)
		{
			if(StringAssistant.getKey(arg).matches("\"hashtags\""))
			{
				String value = StringAssistant.getStringValue(arg, false);
				value = value.substring(value.indexOf('[') + 1, value.lastIndexOf(']'));
				String[] hts = StringAssistant.splitString(value);
				List<Hashtag> HashtagList = new ArrayList<Hashtag>();
				for(String ht : hts)
				{
					HashtagList.add(Hashtag.createHashTag(ht));
				}
				entities.Hashtags = HashtagList.toArray(new Hashtag[0]);
			}
			if(StringAssistant.getKey(arg).matches("\"user_mentions\""))
			{
				String value = StringAssistant.getStringValue(arg, false);
				value = value.substring(value.indexOf('[') + 1, value.lastIndexOf(']'));
				String[] ums = StringAssistant.splitString(value);
				List<User> UserList = new ArrayList<User>();
				for(String um : ums)
				{
					UserList.add(User.createUser(um));
				}
				entities.user_mentions = UserList.toArray(new User[0]);
			}
			if(StringAssistant.getKey(arg).matches("\"urls\""))
			{
				String value = StringAssistant.getStringValue(arg, false);
				value = value.substring(value.indexOf('[') + 1, value.lastIndexOf(']'));
				String[] uls = StringAssistant.splitString(value);
				List<Url> UrlList = new ArrayList<Url>();
				for(String url : uls)
				{
					UrlList.add(Url.createUrl(url));
				}
				entities.urls = UrlList.toArray(new Url[0]);
			}
		}
		return entities;
	}
}
