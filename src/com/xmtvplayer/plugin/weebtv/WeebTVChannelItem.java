package com.xmtvplayer.plugin.weebtv;

import org.json.JSONException;
import org.json.JSONObject;

public class WeebTVChannelItem {

	private String type;
	private String title;
	private String icon;
	private String description;
	private String intent;
	private String uri;

	private boolean is_free;
	private boolean is_adult;
	private boolean is_paid;
	private boolean is_seekable;

	private boolean has_tvguide;

	public WeebTVChannelItem(String type, String title, String uri, String icon, String description, String intent, boolean is_free, boolean is_adult, boolean is_paid, boolean has_tvguide,
			boolean is_seekable) {
		this.setUri(uri);
		this.type = type;
		this.title = title;
		this.icon = icon;
		this.description = description;
		this.intent = intent;
		this.is_free = is_free;
		this.is_adult = is_adult;
		this.is_paid = is_paid;
		this.has_tvguide = has_tvguide;
		this.is_seekable = is_seekable;
	}

	@Override
	public String toString() {
		JSONObject jo = new JSONObject();
		try {
			jo.put("type", type);
			jo.put("title", title);
			jo.put("icon", icon);
			jo.put("description", description);
			jo.put("intent", intent);
			jo.put("uri", uri);
			jo.put("is_free", is_free);
			jo.put("is_adult", is_adult);
			jo.put("is_paid", is_paid);
			jo.put("has_tvguide", has_tvguide);
			jo.put("is_seekable", is_seekable);

		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return jo.toString();
	}

	public JSONObject toJSON() {
		JSONObject jo = new JSONObject();
		try {
			jo.put("type", type);
			jo.put("title", title);
			jo.put("icon", icon);
			jo.put("description", description);
			jo.put("intent", intent);
			jo.put("uri", uri);
			jo.put("is_free", is_free);
			jo.put("is_adult", is_adult);
			jo.put("is_paid", is_paid);
			jo.put("has_tvguide", has_tvguide);
			jo.put("is_seekable", is_seekable);

		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return jo;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
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

	public String getIntent() {
		return intent;
	}

	public void setIntent(String intent) {
		this.intent = intent;
	}

	public boolean isIs_free() {
		return is_free;
	}

	public void setIs_free(boolean is_free) {
		this.is_free = is_free;
	}

	public boolean isIs_adult() {
		return is_adult;
	}

	public void setIs_adult(boolean is_adult) {
		this.is_adult = is_adult;
	}

	public boolean isIs_paid() {
		return is_paid;
	}

	public void setIs_paid(boolean is_paid) {
		this.is_paid = is_paid;
	}

	public boolean isHas_tvguide() {
		return has_tvguide;
	}

	public void setHas_tvguide(boolean has_tvguide) {
		this.has_tvguide = has_tvguide;
	}

	public String getUri() {
		return uri;
	}

	public void setUri(String uri) {
		this.uri = uri;
	}

	public boolean Is_seekable() {
		return is_seekable;
	}

	public void set_seekable(boolean is_seekable) {
		this.is_seekable = is_seekable;
	}

}
