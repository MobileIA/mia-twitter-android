package com.mobileia.twitter.listener;

import com.mobileia.twitter.entity.TwitterUser;

/**
 * Created by matiascamiletti on 8/8/17.
 */

public interface OnTwitterLoginResult {
    void onSuccess(TwitterUser user);
    void onError();
}
