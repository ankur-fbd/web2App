package com.genkitech.web2app;

import android.support.v7.app.AppCompatActivity ;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.gms.ads.MobileAds;

public class MainActivity extends AppCompatActivity  {

    private InterstitialAd mInterstitial;
    private WebView appsWebView;
    private AdView mAdView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Sample AdMob app ID: ca-app-pub-3940256099942544~3347511713
        MobileAds.initialize(this, "ca-app-pub-6721913905554817~4728979749");

        appsWebView = findViewById(R.id.webView);
        WebSettings webSettings = appsWebView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        // enter required URL here (make sure webpage is responsive i.e. it can resize automatically)
        appsWebView.loadUrl("https://genkitechblog.wordpress.com");
        appsWebView.setWebViewClient(new WebViewClient());
        mAdView = findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);

        // Prepare the Interstitial Ad
        mInterstitial = new InterstitialAd(MainActivity.this);
        // Insert the Ad Unit ID
        mInterstitial.setAdUnitId(getString(R.string.admob_interstitial_id));

        mInterstitial.loadAd(adRequest);
        // Prepare an Interstitial Ad Listener
        mInterstitial.setAdListener(new AdListener() {
            public void onAdLoaded() {
                // Call displayInterstitial() function
                displayInterstitial();
            }
        });
    }

    @Override
    public void onBackPressed() {
        if(appsWebView.canGoBack()) {
            appsWebView.goBack();
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            System.exit(0);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
    public void displayInterstitial() {
// If Ads are loaded, show Interstitial else show nothing.
        if (mInterstitial.isLoaded()) {
            mInterstitial.show();
        }
    }
}
