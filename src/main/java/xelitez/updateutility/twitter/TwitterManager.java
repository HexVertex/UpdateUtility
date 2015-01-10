package xelitez.updateutility.twitter;

import java.io.IOException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.apache.commons.codec.binary.Base64;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.message.BasicNameValuePair;

import xelitez.updateutility.XEZLog;

public class TwitterManager 
{
	Random random = new Random();
	String consumerKey = "UZepASTkH63R33fwzoDiNwCFJ";
	String cS = "#CS#";
	
	public List<Tweet> makeRequest(TwitterInstance t)
	{
		String p = null;
		try
		{
			if(t.userID != 0)
			{
				p = "user_id=" + new Integer(t.userID);
			}
			else
			{
				p = "screen_name=" + URLEncoder.encode(t.displayName, "UTF-8");
			}
			String key = URLEncoder.encode(consumerKey, "UTF-8");
			String secret = URLEncoder.encode(cS, "UTF-8");
			String token = new String(Base64.encodeBase64(new String(key + ":" + secret).getBytes()));
			String bearerToken = this.getBearerToken(token);
			HttpClientBuilder httpClient = HttpClientBuilder.create();
			HttpGet httpGet = new HttpGet("https://api.twitter.com/1.1/statuses/user_timeline.json?" + p);
			httpGet.setHeader("Authorization", "Bearer " + bearerToken);
			ResponseHandler<String> responseHandler = new BasicResponseHandler();
			String response = httpClient.build().execute(httpGet, responseHandler);
			return this.composeTweetList(response);
		} 
		catch(Exception e) {
			e.printStackTrace();
		}
		XEZLog.fine("Failed to get Twitter data from %s", p == null ? t.displayName : p);
		return new ArrayList<Tweet>();
	}

	private List<Tweet> composeTweetList(String response) 
	{
		String[] tweets = StringAssistant.splitString(response.substring(response.indexOf('[') + 1, response.lastIndexOf(']')));
		List<Tweet> list = new ArrayList<Tweet>();
		for(String string : tweets)
		{
			list.add(Tweet.CreateTweet(string));
		}
//		for(Tweet tweet : list)
//		{
//			System.out.println(tweet.text);
//		}
		return list;
		
	}

	private String getBearerToken(String token) throws ClientProtocolException, IOException
	{
		HttpClientBuilder httpClient = HttpClientBuilder.create();
		HttpPost httpPost = new HttpPost("https://api.twitter.com/oauth2/token");
		httpPost.setHeader("Authorization", "Basic " + token);
		List<NameValuePair> parameters = new ArrayList<NameValuePair>();
		parameters.add(new BasicNameValuePair("grant_type", "client_credentials"));
		httpPost.setEntity(new UrlEncodedFormEntity(parameters));
		ResponseHandler<String> responseHandler = new BasicResponseHandler();
		String responseBody = httpClient.build().execute(httpPost, responseHandler);
		String OAuthToken = responseBody.substring(responseBody.indexOf("access_token") + 15, responseBody.lastIndexOf("}") - 1);
		return OAuthToken;
	}
}
