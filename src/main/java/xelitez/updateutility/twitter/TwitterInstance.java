package xelitez.updateutility.twitter;

public class TwitterInstance 
{
	protected byte searchRange;
	public String filterString;
	public String displayName;
	protected int userID = 0;
	
	public TwitterInstance(int searchRange, String filter, String displayName)
	{
		this.searchRange = searchRange > 100 ? 100 : searchRange < 0 ? 0 : (byte)searchRange;
		this.filterString = filter;
		this.displayName = displayName;
	}
	
	public TwitterInstance addUserID(int id)
	{
		this.userID = id;
		return this;
	}
}
