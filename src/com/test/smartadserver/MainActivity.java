package com.test.smartadserver;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.util.TypedValue;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.smartadserver.android.library.SASBannerView;
import com.smartadserver.android.library.model.SASAdElement;
import com.smartadserver.android.library.ui.SASAdView;
import com.smartadserver.android.library.ui.SASAdView.AdResponseHandler;
import com.smartadserver.android.library.ui.SASAdView.OnStateChangeListener;
import com.smartadserver.android.library.ui.SASAdView.StateChangeEvent;

public class MainActivity extends Activity {

	private static final String TAG = "MainActivity";
	
	private static int BANNER_HEIGHT = 50;
	private static int BANNER_WIDTH = 320;


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		LinearLayout mRootLayout = (LinearLayout) findViewById(R.id.ll_root);

		initBannerView(mRootLayout);

	}

	public void initBannerView(ViewGroup adContainer) {
		SASBannerView bannerView = new SASBannerView(this);

		// Get density pixel height
		int dpHeight = (int) TypedValue.applyDimension(
				TypedValue.COMPLEX_UNIT_DIP, BANNER_HEIGHT, getResources()
						.getDisplayMetrics());

		// Get density pixel width
		int dpWidth = (int) TypedValue.applyDimension(
				TypedValue.COMPLEX_UNIT_DIP, BANNER_WIDTH, getResources()
						.getDisplayMetrics());

		// Set layout parameters and add bannerView to container
		bannerView.setLayoutParams(new ViewGroup.LayoutParams(dpWidth, dpHeight));
		adContainer.addView(bannerView);

		// Enable logging in Logcat
		SASAdView.enableLogging();

		// Banner state change listener
		bannerView.addStateChangeListener(new OnStateChangeListener() {

			@Override
			public void onStateChanged(StateChangeEvent arg0) {
				Log.i(TAG, "Banner state changed");

			}
		});

		// Ad response handler
		AdResponseHandler bannerResponseHandler = new AdResponseHandler() {

			@Override
			public void adLoadingFailed(Exception arg0) {
				Log.e(TAG, "Ad loadingFailed");

			}

			@Override
			public void adLoadingCompleted(SASAdElement arg0) {
				Log.i(TAG, "Ad loaded");
			}
		};

		// Load ad
		//(siteID,pageID,formatID,master,targeting,adResponseHandler)
		bannerView.loadAd(35176, "(news_activity)", 15140, true, "",
				bannerResponseHandler);

	}

}
