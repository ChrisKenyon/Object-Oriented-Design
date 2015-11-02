/**
 * Created by Chris on 10/30/2014.
 */
public class MyTwitterApp  {


  //Constants

  public static final String CONSUMER_KEY = "<FILL IN YOUR CONSUMER KEY FROM TWITTER HERE>";
  public static final String CONSUMER_SECRET= "<FILL IN YOUR CONSUMER SECRET FROM TWITTER HERE>"
  public static final String REQUEST_URL = "http://api.twitter.com/oauth/request_token";
  public static final String ACCESS_URL = "http://api.twitter.com/oauth/access_token";
  public static final String AUTHORIZE_URL = "http://api.twitter.com/oauth/authorize";
  final public static String  CALLBACK_SCHEME = "x-latify-oauth-twitter";
  final public static String  CALLBACK_URL = CALLBACK_SCHEME + "://callback";




/*
      tweet.setOnClickListener(new View.OnClickListener() {
    public void onClick(View v) {
      if (TwitterUtils.isAuthenticated(prefs)) {
        sendTweet();
      } else {
        Intent i = new Intent(getApplicationContext(), PrepareRequestTokenActivity.class);
        i.putExtra("tweet_msg",getTweetMsg());
        startActivity(i);
      }
    }
  });

*/

}
