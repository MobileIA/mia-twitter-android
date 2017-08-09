package com.mobileia.example.twitter;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.mobileia.twitter.MobileiaTwitter;
import com.mobileia.twitter.builder.TwitterLoginBuilder;
import com.mobileia.twitter.entity.TwitterUser;
import com.mobileia.twitter.listener.OnTwitterLoginResult;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void onClickTwitter(View v){
        new TwitterLoginBuilder()
                .withActivity(this)
                .withResult(new OnTwitterLoginResult() {
                    @Override
                    public void onSuccess(TwitterUser user) {
                        System.out.println("MobileiaTwitter: " + user.id);
                        System.out.println("MobileiaTwitter: " + user.username);
                        System.out.println("MobileiaTwitter: " + user.email);
                        System.out.println("MobileiaTwitter: " + user.token);
                        System.out.println("MobileiaTwitter: " + user.secret);
                    }

                    @Override
                    public void onError() {
                        System.out.println("MobileiaTwitter: No se pudo loguear");
                    }
                })
                .build();
    }
}
