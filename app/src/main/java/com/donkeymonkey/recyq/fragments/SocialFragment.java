package com.donkeymonkey.recyq.fragments;

import android.content.Intent;
import android.graphics.Bitmap;
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
import com.wang.avi.AVLoadingIndicatorView;

import org.honorato.multistatetogglebutton.MultiStateToggleButton;
import org.honorato.multistatetogglebutton.ToggleButton;

import java.net.MalformedURLException;
import java.net.URL;

public class SocialFragment extends Fragment {

    private MultiStateToggleButton mToggle;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View view = (View) inflater.inflate(R.layout.fragment_social, container, false);

        final String instagramURL = "http://www.instagram.com";
        final String youtubeURL = "http://www.youtube.com/channel/UChP2c-VMkBxykwp8CFQuZDQ";
        final String facebookURL = "http://www.facebook.com/RecyQ-381878658877532/";

        mToggle = (MultiStateToggleButton) view.findViewById(R.id.social_toggle);
        final WebView webview = (WebView) view.findViewById(R.id.social_webview);
        final AVLoadingIndicatorView avi = (AVLoadingIndicatorView) view.findViewById(R.id.social_loading_indicator);

        webview.setWebViewClient(new WebViewClient() {

            private int running = 0;

            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                if(!(url.startsWith("intent"))){
                    running++;
                    view.loadUrl(url);
                }
                return true;
            }

            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                running = Math.max(running, 1); // First request move it to 1.
                avi.show();

            }

            @Override
            public void onPageFinished(WebView view, String url) {
                if(--running == 0) { // just "running--;" if you add a timer.
                    avi.hide();
                }
            }

        });


        webview.getSettings().setJavaScriptEnabled(true);

        mToggle.setOnValueChangedListener(new ToggleButton.OnValueChangedListener() {
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

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mToggle.setValue(0);
    }
}
