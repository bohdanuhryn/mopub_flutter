package com.mopub.mobileads;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.amazon.device.ads.AdRegistration;
import com.mopub.common.BaseAdapterConfiguration;
import com.mopub.common.OnNetworkInitializationFinishedListener;

import java.util.Map;

public class AmazonAdapterConfiguration extends BaseAdapterConfiguration {

    @NonNull
    @Override
    public String getAdapterVersion() {
        return "5.9.0.0";
    }

    @Nullable
    @Override
    public String getBiddingToken(@NonNull Context context) {
        return null;
    }

    @NonNull
    @Override
    public String getMoPubNetworkName() {
        return "custom_amazon_network";
    }

    @NonNull
    @Override
    public String getNetworkSdkVersion() {
        return "5.9.0";
    }

    @Override
    public void initializeNetwork(@NonNull Context context, @Nullable Map<String, String> configuration, @NonNull OnNetworkInitializationFinishedListener listener) {
        AdRegistration.enableTesting(true);
        AdRegistration.enableLogging(true);
        AdRegistration.setAppKey("amzn1.devportal.mobileapp.35acb77bf75b47c89a9107ce495c8cdf");
    }
}
