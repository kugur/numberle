package com.kolip.numberle;

import android.app.Activity;
import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.LoadAdError;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.OnUserEarnedRewardListener;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;
import com.google.android.gms.ads.rewarded.RewardItem;
import com.google.android.gms.ads.rewarded.RewardedAd;
import com.google.android.gms.ads.rewarded.RewardedAdLoadCallback;

import java.util.function.Consumer;

public class AdManager {
    private final String REWARD_UNIT_ID = "ca-app-pub-8084690313459333/2165287273";

    private AdView mAdView;
    private RewardedAd mRewardedAd;
    private AdRequest adRequest;

    public AdManager(Context context, AdView banner) {
        adRequest = new AdRequest.Builder().build();
        initializeBanner(banner, context);

        initializeReward(context);
    }

    private void initializeBanner(AdView banner, Context context) {
        this.mAdView = banner;
        MobileAds.initialize(context, new OnInitializationCompleteListener() {
            @Override
            public void onInitializationComplete(InitializationStatus initializationStatus) {
                Log.d("ad-banner", "banner has been initialized");
            }
        });
        mAdView.loadAd(adRequest);
    }

    public void initializeReward(Context context) {

        RewardedAd.load(context, REWARD_UNIT_ID,
                adRequest, new RewardedAdLoadCallback() {
                    @Override
                    public void onAdFailedToLoad(@NonNull LoadAdError loadAdError) {
                        // Handle the error.
                        Log.d("ad-reward", loadAdError.getMessage());
                        mRewardedAd = null;
                    }

                    @Override
                    public void onAdLoaded(@NonNull RewardedAd rewardedAd) {
                        mRewardedAd = rewardedAd;
                        Log.d("ad-reward", "Ad was loaded.");
                    }
                });
    }

    public void showRewardAd(Activity activity, Consumer<Integer> onAdFinished) {
        if (mRewardedAd != null) {
            mRewardedAd.show(activity, new OnUserEarnedRewardListener() {
                @Override
                public void onUserEarnedReward(@NonNull RewardItem rewardItem) {
                    // Handle the reward.
                    Log.d("ad-reward", "The user earned the reward.");
                    int rewardAmount = rewardItem.getAmount();
                    onAdFinished.accept(rewardAmount);
                    initializeReward(activity);
                }
            });
        } else {
            Log.d("ad-reward", "The rewarded ad wasn't ready yet.");
        }
    }
}
