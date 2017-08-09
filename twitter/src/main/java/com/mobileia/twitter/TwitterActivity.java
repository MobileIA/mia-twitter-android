package com.mobileia.twitter;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.mobileia.twitter.entity.TwitterUser;
import com.twitter.sdk.android.core.Callback;
import com.twitter.sdk.android.core.DefaultLogger;
import com.twitter.sdk.android.core.Result;
import com.twitter.sdk.android.core.Twitter;
import com.twitter.sdk.android.core.TwitterAuthConfig;
import com.twitter.sdk.android.core.TwitterAuthToken;
import com.twitter.sdk.android.core.TwitterConfig;
import com.twitter.sdk.android.core.TwitterCore;
import com.twitter.sdk.android.core.TwitterException;
import com.twitter.sdk.android.core.TwitterSession;
import com.twitter.sdk.android.core.identity.TwitterAuthClient;
import com.twitter.sdk.android.core.identity.TwitterLoginButton;

public class TwitterActivity extends Activity {
    /**
     * Instancia del boton de Twitter
     */
    protected TwitterLoginButton mButton;
    /**
     * Almacena el usuario logueado
     */
    protected TwitterUser mUser = new TwitterUser();

    protected Callback<TwitterSession> mCallback = new Callback<TwitterSession>() {
        @Override
        public void success(Result<TwitterSession> result) {
            // Guardar datos del usuario
            mUser.id = result.data.getUserId();
            mUser.username = result.data.getUserName();
            // Pedir permisos para el email
            requestEmail();
        }

        @Override
        public void failure(TwitterException exception) {
            errorCallback();
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_twitter);
        // Configurar twitter
        configureTwitter();
        // Iniciar vista
        initViews();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        // Pass the activity result to the login button.
        mButton.onActivityResult(requestCode, resultCode, data);
    }

    /**
     * Se encarga de setear los parametros obligatorios de la API
     */
    protected void configureTwitter(){
        TwitterConfig config = new TwitterConfig.Builder(this)
                .logger(new DefaultLogger(Log.DEBUG))
                //.twitterAuthConfig(new TwitterAuthConfig("CONSUMER_KEY", "CONSUMER_SECRET"))
                .debug(true)
                .build();
        Twitter.initialize(config);
    }

    /**
     * Se encarga de iniciar la vista y obtener el boton
     */
    protected void initViews(){
        mButton = (TwitterLoginButton)findViewById(R.id.button_twitter);
        mButton.setCallback(mCallback);
        mButton.performClick();
    }

    protected void requestEmail(){
        // Obtenemos session de Twitter
        TwitterSession session = TwitterCore.getInstance().getSessionManager().getActiveSession();
        TwitterAuthToken authToken = session.getAuthToken();
        // Guaramos datos del usuario
        mUser.token = authToken.token;
        mUser.secret = authToken.secret;

        TwitterAuthClient authClient = new TwitterAuthClient();
        authClient.requestEmail(session, new Callback<String>() {
            @Override
            public void success(Result<String> result) {
                // Guardamos email
                mUser.email = result.data;
                // Seteamos que se termino el login
                MobileiaTwitter.getInstance().isLoading = false;
                // Llamamos al listener
                MobileiaTwitter.getInstance().processSuccessResponse(mUser);
                // Finalizamos el login
                finish();
            }

            @Override
            public void failure(TwitterException exception) {
                // Do something on failure
                errorCallback();
            }
        });
    }

    protected void errorCallback(){
        // Seteamos que se termino el login
        MobileiaTwitter.getInstance().isLoading = false;
        // Llamamos al listener
        MobileiaTwitter.getInstance().processErrorResponse();
        // Finalizamos el login
        finish();
    }
}
