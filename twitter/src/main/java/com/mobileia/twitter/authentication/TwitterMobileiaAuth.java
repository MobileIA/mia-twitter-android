package com.mobileia.twitter.authentication;

import android.app.Activity;

import com.google.gson.JsonObject;
import com.mobileia.authentication.core.MobileiaAuthBase;
import com.mobileia.authentication.core.rest.AuthRestBase;
import com.mobileia.core.entity.Error;
import com.mobileia.twitter.builder.TwitterLoginBuilder;
import com.mobileia.twitter.entity.TwitterUser;
import com.mobileia.twitter.listener.OnTwitterLoginResult;

/**
 * Created by matiascamiletti on 8/2/18.
 */

public class TwitterMobileiaAuth extends MobileiaAuthBase {
    /**
     * Almacena el token del usuario
     */
    protected String mTwitterToken;
    /**
     * Almacena el secret del usuario
     */
    protected String mTwitterSecret;

    /**
     * Constructor
     *
     * @param activity
     */
    public TwitterMobileiaAuth(Activity activity) {
        super(activity);
    }

    @Override
    public void requestAccessToken() {
        // Generamos objeto para enviar los parametros
        JsonObject params = new JsonObject();
        params.addProperty("grant_type", "twitter");
        params.addProperty("twitter_token", mTwitterToken);
        params.addProperty("twitter_secret", mTwitterSecret);
        new AuthRestBase().oauth(params, mAccessTokenResult);
    }

    @Override
    public void requestNewAccount() {
        // Generamos objeto para enviar los parametros
        JsonObject params = new JsonObject();
        params.addProperty("register_type", "twitter");
        params.addProperty("twitter_token", mTwitterToken);
        params.addProperty("twitter_secret", mTwitterSecret);
        new AuthRestBase().register(params, mRegisterResult);
    }

    @Override
    public void openSocial() {
        new TwitterLoginBuilder()
                .withActivity(mActivity)
                .withResult(new OnTwitterLoginResult() {
                    @Override
                    public void onSuccess(TwitterUser user) {
                        // Guardamos datos del token
                        mTwitterToken = user.token;
                        mTwitterSecret = user.secret;
                        // Ya se logueo con Twitter, realizamos peticion para generar AccessToken
                        requestAccessToken();
                    }

                    @Override
                    public void onError() {
                        // Llamamos al callback con error
                        mCallback.onError(new Error(-1, "No se pudo loguear con la cuenta de twitter."));
                    }
                })
                .build();
    }
}
