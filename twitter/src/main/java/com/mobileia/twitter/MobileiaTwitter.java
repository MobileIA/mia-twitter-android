package com.mobileia.twitter;

import android.app.Activity;
import android.content.Intent;

import com.mobileia.twitter.entity.TwitterUser;
import com.mobileia.twitter.listener.OnTwitterLoginResult;

/**
 * Created by matiascamiletti on 8/8/17.
 */

public class MobileiaTwitter {
    /**
     * Almacena la Activity que lo inicia
     */
    protected Activity mActivity;
    /**
     * Almacena listener para las respuestas
     */
    protected OnTwitterLoginResult mListener;
    /**
     * Determina si se esta logueando o no.
     */
    public boolean isLoading = false;
    /**
     * Almacena la unica instancia de la libreria
     */
    private static final MobileiaTwitter sOurInstance = new MobileiaTwitter();

    public void login(){
        // Iniciar activity
        startTranparentActivity();
    }

    /**
     * Funcino que se llama una vez logueado al usuario
     */
    public void processSuccessResponse(TwitterUser user){
        if(mListener != null){
            mListener.onSuccess(user);
        }
    }

    /**
     * Funcino que se llama una vez logueado al usuario
     */
    public void processErrorResponse(){
        if(mListener != null){
            mListener.onError();
        }
    }

    /**
     * Setea el Activity
     * @param activity
     */
    public void setActivity(Activity activity){
        this.mActivity = activity;
    }


    /**
     * Setea listener cuando se loguea
     * @param listener
     */
    public void setListener(OnTwitterLoginResult listener){
        this.mListener = listener;
    }

    /**
     * Obtiene la instancia creada
     * @return
     */
    public static MobileiaTwitter getInstance() {
        return sOurInstance;
    }

    protected void startTranparentActivity(){
        Intent intent = new Intent(mActivity, TwitterActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        mActivity.startActivity(intent);
    }

    private MobileiaTwitter() {
    }
}
