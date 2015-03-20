package com.xmtvplayer.plugin.weebtv;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class WeebTVGroups {
	private String id;
	private String title;
	private String icon;
	private String description;
	public ArrayList<WeebTVChannelItem> Channels;

	public WeebTVGroups(String id, String title, String icon, String description) {

		this.id = id;
		this.title = title;
		this.icon = icon;
		this.description = description;
		Channels = new ArrayList<WeebTVChannelItem>();
	}

	public String GetGroupChannes() {
		JSONArray ja = new JSONArray();
		for (int i = 0; i < Channels.size(); i++) {
			ja.put(Channels.get(i).toJSON());
		}
		return ja.toString();
	}

	@Override
	public String toString() {
		JSONObject jo = new JSONObject();
		try {
			jo.put("id", id);
			jo.put("name", title);
			jo.put("icon", icon);
			jo.put("description", description);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return jo.toString();
	}

	public JSONObject toJSON() {
		JSONObject jo = new JSONObject();
		try {
			jo.put("id", id);
			jo.put("name", title);
			jo.put("icon", icon);
			jo.put("description", description);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return jo;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getIcon() {
		return icon;
	}

	public void setIcon(String icon) {
		this.icon = icon;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

}
