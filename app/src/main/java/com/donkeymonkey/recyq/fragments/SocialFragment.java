package com.donkeymonkey.recyq.fragments;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.donkeymonkey.recyq.R;

import org.honorato.multistatetogglebutton.MultiStateToggleButton;
import org.honorato.multistatetogglebutton.ToggleButton;

import java.net.MalformedURLException;
import java.net.URL;

public class SocialFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View view = (View) inflater.inflate(R.layout.fragment_social, container, false);

        final String instagramURL = "http://www.instagram.com";
        final String youtubeURL = "http://www.youtube.com/channel/UChP2c-VMkBxykwp8CFQuZDQ";
        final String facebookURL = "http://www.facebook.com/RecyQ-381878658877532/";

        MultiStateToggleButton toggle = (MultiStateToggleButton) view.findViewById(R.id.social_toggle);
        final WebView webview = (WebView) view.findViewById(R.id.social_webview);

        toggle.setValue(2);

        webview.setWebViewClient(new WebViewClient() {

            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                if(!(url.startsWith("intent"))){
                    view.loadUrl(url);
                }
                return true;
            }

        });

        toggle.setValue(0);

        webview.getSettings().setJavaScriptEnabled(true);

        toggle.setOnValueChangedListener(new ToggleButton.OnValueChangedListener() {
            @Override
            public void onValueChanged(int value) {
                if (value == 0) {
                    webview.loadUrl(instagramURL);
                } else if (value == 1) {
                    webview.loadUrl(youtubeURL);
                } else if (value == 2) {
                    webview.loadUrl(facebookURL);
                }
            }
        });


        return view;
    }
}
