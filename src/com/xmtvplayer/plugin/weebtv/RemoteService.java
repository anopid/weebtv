package com.xmtvplayer.plugin.weebtv;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.HashMap;
import java.util.concurrent.atomic.AtomicBoolean;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.StatusLine;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.protocol.HTTP;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Service;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager.NameNotFoundException;
import android.os.IBinder;
import android.os.RemoteException;
import android.os.StrictMode;
import android.util.Log;

import com.xmtv.remote.IRemoteService;

public class RemoteService extends Service {
	private String packname = "com.xmtvplayer.watch.live.streams";
	private static String http_weebtv_getchannels = "http://weeb.tv/api/getChannelList";
	private static String http_weebtv_online_alpha = "&option=online-alphabetical";
	private static String http_weebtv_online_now_viewed = "&option=online-now-viewed";
	private static String http_weebtv_online_most_viewed = "&option=online-most-viewed";
	private static String http_weebtv_set_player = "http://weeb.tv/api/setPlayer&channel=";
	private final static String TAG = "com.xmtvplayer.plugin.weebtv";
	private final AtomicBoolean keeplive = new AtomicBoolean(false);
	private HashMap<String, WeebTVGroups> groups = new HashMap<String, WeebTVGroups>();
	private String auth_string = "";

	private final IRemoteService.Stub mBinder = new IRemoteService.Stub() {

		@Override
		public String GetAdsAPI() throws RemoteException {
			return "{\"adskey\":\"15E56089-CB15-AFCF-1A3B-574E899DE008\",\"mintime\":30}";
		}

		@Override
		public String GetChannelInfoByID(String ChannelID)
				throws RemoteException {
			CheckCallingUid();
			if (init_session()) {
				// generate Channel Info
			}
			return null;
		}

		@Override
		public String GetChannelEPGByID(String ChannelID)
				throws RemoteException {
			CheckCallingUid();
			return null;
		}

		private void CheckCallingUid() {
			String[] data = getPackageManager().getPackagesForUid(
					android.os.Binder.getCallingUid());
			Boolean res = false;
			for (int i = 0; i < data.length; i++) {
				res = res || data[i].equals(packname);
			}
			if (!res) {
				throw new IllegalArgumentException("Missing parameters");
			}
		}

		@Override
		public String GetChannelByID(String ChannelID) throws RemoteException {
			CheckCallingUid();
			if (init_session()) {
				String data = getJSON(http_weebtv_set_player + ChannelID + auth_string);
				try {
					data = URLDecoder.decode(data, "UTF-8");
				} catch (UnsupportedEncodingException e) {
					e.printStackTrace();
				}
				String params[] = data.split("&");
				HashMap<String, String> hParams = new HashMap<String, String>();
				for (int i = 0; i < params.length; i++) {
					String[] eSplit = params[i].split("=");
					String key = eSplit[0];
					String value = eSplit[1];
					hParams.put(key, value);
				}

				String rtmpLink = hParams.get("10");
				String playPath = hParams.get("11");
				String bitrate = hParams.get("20");
				String token = hParams.get("73");
				// String url = "rtmp://$OPT:rtmp-raw=" + rtmpLink + "/"
				// + playPath + " live=1 pageUrl=token swfUrl=" + token;

				String url = "rtmp://$OPT:rtmp-raw=" + rtmpLink + "/" +
						playPath +
						" swfUrl=http://static.weeb.tv/player.swf live=1 weeb="
						+ token + " pageUrl=http://weeb.tv/channel/" + ChannelID;
				Log.e("weebtv", url);
				return url;
			}
			return "";
		}

		@Override
		public String GetChannelGroupsJSON() throws RemoteException {
			CheckCallingUid();
			if (init_session()) {
				JSONArray ja1 = new JSONArray();
				Log.i(TAG, "GetChannelGroupsJSON ");
				WeebTVGroups weebgroups = new WeebTVGroups("1",
						"Alphabetical", "", "");
				groups.put("1", weebgroups);
				ja1.put(weebgroups.toJSON());
				weebgroups = new WeebTVGroups("2", "Now Viewed", "", "");
				groups.put("2", weebgroups);
				ja1.put(weebgroups.toJSON());
				weebgroups = new WeebTVGroups("3", "Most Viewed", "", "");
				groups.put("3", weebgroups);
				ja1.put(weebgroups.toJSON());

				JSONObject jo1 = new JSONObject();
				try {
					jo1.put("groups", ja1);
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				return jo1.toString();
			} else
				return "";
		}

		@Override
		public String GetChannelsByGroup(String GroipID) throws RemoteException {
			CheckCallingUid();
			if (init_session()) {
				WeebTVGroups fo = groups.get(GroipID);
				if (fo == null) {
					GetChannelGroupsJSON();
					fo = groups.get(GroipID);
				}
				if (fo != null) {
					if (fo.Channels.size() <= 0) {
						String data = null;
						if (GroipID.equals("1")) {
							data = getJSON(http_weebtv_getchannels
									+ http_weebtv_online_alpha + auth_string);
						}
						if (GroipID.equals("2")) {
							data = getJSON(http_weebtv_getchannels
									+ http_weebtv_online_most_viewed + auth_string);
						}
						if (GroipID.equals("3")) {
							data = getJSON(http_weebtv_getchannels
									+ http_weebtv_online_now_viewed + auth_string);
						}

						try {
							data = data.replaceAll(
									",\"channel_tags\":\\\"[^\\\"]*\\\",", ",");
							data = data
									.replaceAll(
											",\"channel_imgid_logo\":\\\"[^\\\"]*\\\",",
											",");
							JSONObject channels = new JSONObject(data);
							for (int i = 0; i < channels.length(); i++) {
								JSONObject jo = channels.getJSONObject(String
										.valueOf(i));
								String group_id = GroipID;
								String type = "video";
								String uri = jo.getString("channel_name");
								String title = jo.getString("channel_title");
								String icon = jo.getString("channel_logo_url");
								String intent = "intent";
								boolean is_free = true;
								boolean is_adult = false;
								boolean is_paid = true;
								boolean has_tvguide = false;
								boolean is_seekable = false;

								if (GroipID.equals(group_id)) {
									WeebTVChannelItem fchannelItem = new WeebTVChannelItem(
											type, title, uri, icon, "", intent,
											is_free, is_adult, is_paid,
											has_tvguide, is_seekable);
									fo.Channels.add(fchannelItem);
								}
							}
							return fo.GetGroupChannes();

						} catch (Exception e) {
							// TODO: handle exception
							Log.e("", e.getMessage());
						}

					} else {
						return fo.GetGroupChannes();
					}
				}
			}
			return "";

		}

		private boolean init_session() {
			CheckCallingUid();
			SharedPreferences prefs = null;
			prefs = getSharedPreferences("settings", 1);
			if ((!prefs.getString("u", "").equals("")) && (!prefs.getString("p", "").equals(""))) {
				auth_string = "&username=" + prefs.getString("u", "") + "&userpassword=" + prefs.getString("p", "");
			} else {
				auth_string = "";
			}

			return true;// (session_key != null);

		}

		@Override
		public String pluginAbout() throws RemoteException {
			CheckCallingUid();
			return getResources().getString(R.string.app_text_clean);
		}

		@Override
		public String pluginVer() throws RemoteException {
			CheckCallingUid();
			String version = "unknown";
			PackageInfo pInfo;
			try {
				pInfo = getPackageManager().getPackageInfo(getPackageName(), 0);
				version = pInfo.versionName + " - " + pInfo.versionCode;
			} catch (NameNotFoundException e) {
				e.printStackTrace();
			}
			return version;
		}

	};

	private String getJSON(String address) {
		Log.e("weebtv", address);
		StringBuilder builder = new StringBuilder();
		HttpClient client = new DefaultHttpClient();
		HttpGet httpGet = new HttpGet(address);
		try {
			HttpResponse response = client.execute(httpGet);
			StatusLine statusLine = response.getStatusLine();
			int statusCode = statusLine.getStatusCode();
			if (statusCode == 200) {
				HttpEntity entity = response.getEntity();
				InputStream content = entity.getContent();
				BufferedReader reader = new BufferedReader(
						new InputStreamReader(content, HTTP.UTF_8));
				String line;
				while ((line = reader.readLine()) != null) {
					builder.append(line);
				}
			}
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return builder.toString();
	}

	@Override
	public IBinder onBind(Intent intent) {
		Log.i(TAG, "onBind");
		keeplive.set(true);
		return mBinder;
	}

	@Override
	public void onCreate() {
		super.onCreate();
		if (android.os.Build.VERSION.SDK_INT >= 9) {
			StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder()
					.permitNetwork().build());
		}
	}

	@Override
	public boolean onUnbind(Intent intent) {
		keeplive.set(false);
		Log.i(TAG, "onUnbind");
		return super.onUnbind(intent);
	}

}