package com.mopub.mobileads;

import android.app.Activity;
import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.util.Log;

import com.amazon.device.ads.Ad;
import com.amazon.device.ads.AdError;
import com.amazon.device.ads.AdLayout;
import com.amazon.device.ads.AdListener;
import com.amazon.device.ads.AdProperties;
import com.amazon.device.ads.AdRegistration;
import com.amazon.device.ads.AdSize;
import com.amazon.device.ads.AdTargetingOptions;

import java.util.Map;

/*
 * Tested with Amazon SDK 5.4
 */
public class AmazonBanner extends CustomEventBanner implements AdListener {

    private CustomEventBannerListener bannerListener;
    private AdLayout amazonadview;

    @Override
    protected void loadBanner(Context context, CustomEventBannerListener customEventBannerListener, Map<String, Object> localExtras, Map<String, String> serverExtras) {
        bannerListener = customEventBannerListener;

        Activity activity = null;
        if (context instanceof Activity) {
            activity = (Activity) context;
        } else {
            // You may also pass in an Activity Context in the localExtras map and retrieve it here.
            activity = (Activity)localExtras.get("activity");
        }

        if (activity == null) {
            if(bannerListener != null) {
                bannerListener.onBannerFailed(MoPubErrorCode.INTERNAL_ERROR);
            }
            return;
        }

        final int adWidth = (int)localExtras.get("adWidth");
        final int adHeight = (int)localExtras.get("adHeight");
        AdSize adSize = AdSize.SIZE_320x50;
        if (adWidth == 300 && adHeight == 250) {
            adSize = AdSize.SIZE_300x250;
        }

        String appId = serverExtras.get("app_id");
        if (appId == null) {
            try {
                ApplicationInfo ai = context.getPackageManager().getApplicationInfo(context.getPackageName(),PackageManager.GET_META_DATA);
                appId = ai.metaData.get("amazon_ads_app_id").toString();
            } catch(Throwable t) {
                Log.e("MoPub", "Could not find amazon_ads_app_id in meta-data in Android manifest");
            }
        }
        if (appId == null) {
            Log.d("MoPub", "Amazon banner ad app_id is missing.");
            if(bannerListener != null) {
                bannerListener.onBannerFailed(MoPubErrorCode.INTERNAL_ERROR);
            }
            return;
        }

        AdRegistration.setAppKey(appId);
        AdRegistration.enableTesting(true);
        AdRegistration.enableLogging(true);

        amazonadview = new AdLayout(activity, adSize);
        AdLayout.LayoutParams params = new AdLayout.LayoutParams(AdLayout.LayoutParams.MATCH_PARENT,AdLayout.LayoutParams.WRAP_CONTENT);
        amazonadview.setLayoutParams(params);
        amazonadview.setListener(this);
        amazonadview.loadAd(new AdTargetingOptions()); // async task to retrieve an ad
    }

    @Override
    public void onInvalidate() {
        if(amazonadview != null) {
            amazonadview.setListener(null);
        }
    }


    @Override
    public void onAdLoaded(Ad ad, AdProperties adProperties) {
        if (amazonadview != null && bannerListener != null) {
            Log.d("MoPub", "Amazon banner ad loaded successfully. Showing ad...");
            bannerListener.onBannerLoaded(amazonadview);
        } else if (bannerListener != null) {
            bannerListener.onBannerFailed(MoPubErrorCode.UNSPECIFIED);
        }
    }

    @Override
    public void onAdFailedToLoad(Ad ad, AdError adError) {
        Log.d("MoPub", "Amazon banner ad failed to load.");
        Log.d("MoPub", adError.getCode().toString() + ": " + adError.getMessage());

        if(bannerListener != null) {
            bannerListener.onBannerFailed(MoPubErrorCode.NETWORK_INVALID_STATE);
        }
    }

    @Override
    public void onAdExpanded(Ad ad) {
        Log.d("MoPub", "Amazon banner ad clicked.");
        if(bannerListener != null) {
            bannerListener.onBannerClicked();
        }
    }

    @Override
    public void onAdCollapsed(Ad ad) {
        Log.d("MoPub", "Amazon banner ad modal dismissed.");
    }

    @Override
    public void onAdDismissed(Ad ad) {

    }

}