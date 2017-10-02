package com.marckregio.makunatlib.activity;


import android.app.Activity;
import android.net.Uri;
import android.widget.Toast;

import com.facebook.share.model.ShareLinkContent;
import com.facebook.share.widget.ShareDialog;
import com.marckregio.makunatlib.http.NetworkCheck;

import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.conf.ConfigurationBuilder;

/**
 * Created by makregio on 22/06/2017.
 */

public class SocialMediaUtil {

    private Activity activity;
    private static final String TWITTER_CONSUMERKEY = "68FYQBVmNhCahd5V9SQlK1ade";
    private static final String TWITTER_SECRETKEY = "rVp1uYKq7pkDd4GkJhkhB2Qn2yv8qOf1jmXaQPom9QIBSo1kC0";
    private static final String TWITTER_ACCESSTOKEN = "77998719-mFzlHQcK4zPyHSkp9dphs8EdydI2WZboZuMXT2r9g";
    private static final String TWITTER_SECRETTOKEN = "3hwiM4wMkqrldxiV5p5UEoHuj7BePQigxfMiu8GWBGBnM";

    public SocialMediaUtil(Activity activity){
        this.activity = activity;
    }

    public void shareToFacebook(final String url){
        new NetworkCheck(activity){
            @Override
            protected void onPostExecute(Boolean aBoolean) {
                if (aBoolean){
                    ShareLinkContent content = new ShareLinkContent.Builder()
                            .setContentUrl(Uri.parse(url))
                            .build();

                    ShareDialog.show(activity, content);
                }
            }
        }.execute();
    }

    public void shareToTwitter(final String url){

        new NetworkCheck(activity){
            @Override
            protected void onPostExecute(Boolean aBoolean) {
                if (aBoolean){
                    try {
                        ConfigurationBuilder cb = new ConfigurationBuilder();
                        cb.setOAuthConsumerKey(TWITTER_CONSUMERKEY);
                        cb.setOAuthConsumerSecret(TWITTER_SECRETKEY);
                        cb.setOAuthAccessToken(TWITTER_ACCESSTOKEN);
                        cb.setOAuthAccessTokenSecret(TWITTER_SECRETTOKEN);
                        TwitterFactory tf = new TwitterFactory(cb.build());
                        Twitter twitter = tf.getInstance();
                        twitter4j.Status status = twitter.updateStatus(url);
                        Toast.makeText(activity, "Tweet sent!", Toast.LENGTH_LONG).show();
                    } catch (TwitterException e) {
                        e.printStackTrace();
                    }
                }
            }
        }.execute();
    }
}
