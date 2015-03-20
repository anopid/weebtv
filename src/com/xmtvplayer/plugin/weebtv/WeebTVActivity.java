/**
 * 
 */
package com.xmtvplayer.plugin.weebtv;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class WeebTVActivity extends FragmentActivity {
	Context context;
	SharedPreferences prefs = null;
	int minVer = 18;

	@Override
	protected void onCreate(Bundle arg0) {
		// TODO Auto-generated method stub
		super.onCreate(arg0);
		this.context = this;
		prefs = context.getSharedPreferences("settings", 1);
		setContentView(R.layout.playtv_activity);
		this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);		    
		
		WebView view = new WebView(this);
		view.setVerticalScrollBarEnabled(false);
		((LinearLayout) findViewById(R.id.base)).addView(view);
		view.loadData(getString(R.string.app_text), "text/html", "utf-8");

		final int ver = isXMTVPlayerGetVersion(this);
		if (ver == -1) {
			((ImageView) findViewById(R.id.imageView1))
					.setImageResource(R.drawable.cancel);
			((ImageView) findViewById(R.id.imageView2))
					.setImageResource(R.drawable.cancel);
			((TextView) findViewById(R.id.textView1))
					.setText("XMTV Player available");
			((TextView) findViewById(R.id.textView2)).setText(getResources().getString(R.string.app_plugin_has_correct_xmtvplayer_version));
			((Button) findViewById(R.id.buttonAction)).setText(getResources().getString(R.string.app_plugin_download_xmtvplayer));
		} else if (ver >= minVer) {
			((ImageView) findViewById(R.id.imageView1))
					.setImageResource(R.drawable.ok);
			((ImageView) findViewById(R.id.imageView2))
					.setImageResource(R.drawable.ok);
			((TextView) findViewById(R.id.textView1))
					.setText("XMTV Player available");
			((TextView) findViewById(R.id.textView2)).setText(getResources().getString(R.string.app_plugin_has_correct_xmtvplayer_version));
			((Button) findViewById(R.id.buttonAction)).setText(getResources().getString(R.string.app_plugin_has_correct_launch));
			//((Button) findViewById(R.id.buttonAction)).setVisibility(View.GONE);
		} else {
			((ImageView) findViewById(R.id.imageView1))
					.setImageResource(R.drawable.ok);
			((ImageView) findViewById(R.id.imageView2))
					.setImageResource(R.drawable.cancel);
			((TextView) findViewById(R.id.textView1))
					.setText("XMTV Player available");
			((TextView) findViewById(R.id.textView2)).setText(getResources().getString(R.string.app_plugin_has_correct_xmtvplayer_version));
			((Button) findViewById(R.id.buttonAction)).setText(getResources().getString(R.string.app_plugin_has_correct_update));
			
		}
		
		
		
		((Button) findViewById(R.id.btnCreateUser))
		.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				showWebSite("http://weeb.tv/account/login");
			}
			
		});
		
		
		((Button) findViewById(R.id.btnSetUser))
		.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Editor e =  prefs.edit();
				e.putString("u", ((EditText)findViewById(R.id.editUsername)).getText().toString().trim());
				e.putString("p", ((EditText)findViewById(R.id.editPassword)).getText().toString().trim());
				e.commit();
				Toast.makeText(context,getResources().getString(R.string.app_plugin_password_set) , Toast.LENGTH_LONG).show();
				
			}
			
		});
		((Button) findViewById(R.id.btnClearUser))
		.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Editor e =  prefs.edit();
				e.putString("u", "");
				e.putString("p", "");
				e.commit();
				Toast.makeText(context,getResources().getString(R.string.app_plugin_password_clear) , Toast.LENGTH_LONG).show();
			}
		});
		

		((Button) findViewById(R.id.buttonAction))
				.setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub
						if (ver == -1) {
							OpenGooglePlayStore(context);
							//AmazonApps();
							//OpenSlideMe(context);
						} else if (ver >= minVer) {

							PackageManager pm = context.getPackageManager();
							try {
								Intent it = pm
										.getLaunchIntentForPackage("com.xmtvplayer.watch.live.streams");

								if (null != it)
									context.startActivity(it);
							}

							catch (ActivityNotFoundException e) {
							}
						} else {
							//OpenSlideMe(context);
						     OpenGooglePlayStore(context);
							//AmazonApps();
						}

					}
				});

	}
	
	private void showWebSite(String url) {
        String _url = url;
        //Activity webactivity = new Activity(); Not required
        Intent webIntent = new Intent( Intent.ACTION_VIEW );
        webIntent.setData( Uri.parse(_url) );
        this.startActivity( webIntent );
}

	public static boolean isXMTVPlayerAvailable(Context context) {
		PackageManager pm = context.getPackageManager();
		boolean app_installed = false;
		try {
			pm.getPackageInfo("com.xmtvplayer.watch.live.streams",
					PackageManager.GET_ACTIVITIES);
			app_installed = true;
		} catch (PackageManager.NameNotFoundException e) {
			app_installed = false;
		}
		return app_installed;
	}

	public static int isXMTVPlayerGetVersion(Context context) {
		PackageManager pm = context.getPackageManager();
		int app_installed = -1;
		try {
			app_installed = (pm.getPackageInfo(
					"com.xmtvplayer.watch.live.streams",
					PackageManager.GET_ACTIVITIES)).versionCode;
		} catch (PackageManager.NameNotFoundException e) {
			app_installed = -1;
		}
		return app_installed;
	}

	public static void OpenGooglePlayStore(Context context) {
		Intent intent = new Intent(Intent.ACTION_VIEW);
		intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK
				| Intent.FLAG_ACTIVITY_BROUGHT_TO_FRONT
				| Intent.FLAG_ACTIVITY_EXCLUDE_FROM_RECENTS);
		intent.setData(Uri
				.parse("market://details?id=com.xmtvplayer.watch.live.streams"));
		context.startActivity(intent);
	}

	public static void OpenSlideMe(Context context) {
		Intent intent = new Intent(Intent.ACTION_VIEW);
		intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK
				| Intent.FLAG_ACTIVITY_BROUGHT_TO_FRONT
				| Intent.FLAG_ACTIVITY_EXCLUDE_FROM_RECENTS);
		intent.setData(Uri.parse("sam://details?id="
				+ "com.xmtvplayer.watch.live.streams"));
		context.startActivity(intent);
	}

	// LinkUrl1 = "sam://details?id=" + "com.xmtvplayer.watch.live.streams";

	private void AmazonApps() {
		Intent intent = new Intent();
		String string_of_uri = "amzn://apps/android?p="
				+ "com.xmtvplayer.watch.live.streams";
		intent.setData(Uri.parse(string_of_uri)); // The string_of_uri is an
		if (android.os.Build.VERSION.SDK_INT >= 11) {
			intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK
					| Intent.FLAG_ACTIVITY_CLEAR_TOP
					| Intent.FLAG_INCLUDE_STOPPED_PACKAGES
					| Intent.FLAG_INCLUDE_STOPPED_PACKAGES);
		} else {
			intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK
					| Intent.FLAG_ACTIVITY_CLEAR_TOP
					| Intent.FLAG_INCLUDE_STOPPED_PACKAGES);
		}
		// The Intent.FLAG_INCLUDE_STOPPED_PACKAGES flag must be added only
		try {
			startActivity(intent);
		} catch (Exception e) {
			// 
			intent = new Intent();
			string_of_uri = "http://www.amazon.com/gp/mas/dl/android?p=com.xmtvplayer.watch.live.streams";
			intent.setData(Uri.parse(string_of_uri)); // The string_of_uri is an
			if (android.os.Build.VERSION.SDK_INT >= 11) {
				intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK
						| Intent.FLAG_ACTIVITY_CLEAR_TOP
						| Intent.FLAG_INCLUDE_STOPPED_PACKAGES
						| Intent.FLAG_INCLUDE_STOPPED_PACKAGES);
			} else {
				intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK
						| Intent.FLAG_ACTIVITY_CLEAR_TOP
						| Intent.FLAG_INCLUDE_STOPPED_PACKAGES);
			}
			try {
				startActivity(intent);
			} catch (Exception e1) {
			}
			

		}

	}

}
