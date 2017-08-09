package com.mobileia.twitter.builder;

import android.app.Activity;

import com.mobileia.twitter.MobileiaTwitter;
import com.mobileia.twitter.listener.OnTwitterLoginResult;

/**
 * Created by matiascamiletti on 8/8/17.
 */

public class TwitterLoginBuilder {
    /**
     * Configura el activity
     * @param activity
     * @return
     */
    public TwitterLoginBuilder withActivity(Activity activity){
        MobileiaTwitter.getInstance().setActivity(activity);
        return this;
    }
    /**
     * Configura el manejador cuando el usuario se logueado correctamente
     * @param listener
     * @return
     */
    public TwitterLoginBuilder withResult(OnTwitterLoginResult listener){
        MobileiaTwitter.getInstance().setListener(listener);
        return this;
    }

    /**
     * Ejecuta el login a traves de facebook
     */
    public void build(){
        MobileiaTwitter.getInstance().login();
    }
}
