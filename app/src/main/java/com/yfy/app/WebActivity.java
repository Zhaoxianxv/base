package com.yfy.app;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebSettings.PluginState;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;

import com.yfy.base.R;
import com.yfy.base.activity.BaseActivity;
import com.yfy.final_tag.data.TagFinal;
import com.yfy.final_tag.stringtool.StringJudge;

public class WebActivity extends BaseActivity {

	private final static String TAG = WebActivity.class.getSimpleName();

	private ViewGroup container_view;
	private WebView webView;
	public WebSettings webSettings;


	private ProgressBar progressBar;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_web);
//		if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
//			getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
//		}
		initAll();
	}

	private void initSQtoolbar(String title){
		assert toolbar!=null;
		toolbar.setTitle(title);
//		toolbar.setNavi(R.drawable.ic_back);
	}



	@Override
	public void finish() {
		if (index!=-1){
			Intent intent=new Intent();
			intent.putExtra(TagFinal.ALBUM_LIST_INDEX, index);
			setResult(RESULT_OK,intent);
		}
		super.finish();
	}


	private void initAll() {
		getData();

		container_view =  findViewById(R.id.container_view);
		webView =  findViewById(R.id.webView);
		progressBar =  findViewById(R.id.progressBar);
		initWeb();
	}

	/**
	 */
	public int index;
	public String url;
	public String title;
	public void getData() {
		Bundle b = getIntent().getExtras();
		if (b != null) {
			if (b.containsKey(TagFinal.URI_TAG)) {
				url = b.getString(TagFinal.URI_TAG);
			}
			if (b.containsKey(TagFinal.ALBUM_LIST_INDEX)) {
				index = b.getInt(TagFinal.ALBUM_LIST_INDEX);
			}else{
				index=-1;
			}
			if (StringJudge.isContainsKey(b,TagFinal.TITLE_TAG)) {
				title = b.getString(TagFinal.TITLE_TAG);
				if (StringJudge.isEmpty(title))title="";
				if (title.length()>10){
					title="新闻详情";
				}
			}
			if (StringJudge.isContainsKey(b,"session_key")) {
				String session_key = b.getString("session_key");
				if (StringJudge.isEmpty(session_key))session_key="";
				url = url.replace("%@", session_key);
			}
			initSQtoolbar(title);
		}
	}

	@Override
	public void onPause() {
		super.onPause();
		webView.pauseTimers();
	}

	@Override
	public void onResume() {
		super.onResume();
		webView.resumeTimers();
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		if (webView != null) {
			container_view.removeView(webView);
			webView.removeAllViews();
			webView.destroy();
		}
	}

	@TargetApi(Build.VERSION_CODES.JELLY_BEAN)
	@SuppressLint("SetJavaScriptEnabled")
	private void initWeb() {
		webView.setHorizontalScrollBarEnabled(false);
		webView.setWebChromeClient(new WebChromeClient() {
			public void onProgressChanged(WebView view, int progress) {
				Log.e(TAG, "progress=" + progress);
				if (progress == 100) {
					progressBar.setProgress(0);
					progressBar.setVisibility(View.GONE);
				} else {
					progressBar.setProgress(progress);
				}
			}
		});

		webSettings = webView.getSettings();
		webSettings.setJavaScriptEnabled(false);
		webSettings.setPluginState(PluginState.ON);
		webSettings.setUseWideViewPort(true);
		webSettings.setLoadWithOverviewMode(true);
		webSettings.setJavaScriptCanOpenWindowsAutomatically(true);



		webSettings.setAllowFileAccess(false);
		webSettings.setAllowFileAccessFromFileURLs(false);


		webView.removeJavascriptInterface("searchBoxJavaBridge_");
		webView.removeJavascriptInterface("accessibility");
		webView.removeJavascriptInterface("accessibilityTraversal");

		webView.loadUrl(url);
		webView.setWebViewClient(new WebViewClient() {
			@Override
			public boolean shouldOverrideUrlLoading(WebView view, String url) {
				view.loadUrl(url);
				return true;
			}
		});
	}


}
