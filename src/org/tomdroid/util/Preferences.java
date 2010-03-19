package org.tomdroid.util;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

public class Preferences {
	
	public enum Key {
		SYNC_SERVICE ("sync_service", "snowy"),
		SYNC_SERVER_ROOT_API ("sync_server_root_api", ""),
		SYNC_SERVER_USER_API ("sync_server_user_api", ""),
		SYNC_SERVER ("sync_server", "https://one.ubuntu.com/notes"),
		ACCESS_TOKEN ("access_token", ""),
		ACCESS_TOKEN_SECRET ("access_token_secret", ""),
		REQUEST_TOKEN ("request_token", ""),
		REQUEST_TOKEN_SECRET ("request_token_secret", ""),
		OAUTH_10A ("oauth_10a", false),
		AUTHORIZE_URL ("authorize_url", ""),
		ACCESS_TOKEN_URL ("access_token_url", ""),
		REQUEST_TOKEN_URL ("request_token_url", ""),
		FIRST_RUN ("first_run", true);
		
		private String name = "";
		private Object defaultValue = "";
		
		Key(String name, Object defaultValue) {
			this.name = name;
			this.defaultValue = defaultValue;
		}
		
		public String getName() {
			return name;
		}
		
		public Object getDefault() {
			return defaultValue;
		}
	}
	
	private static SharedPreferences client = null;
	private static SharedPreferences.Editor editor = null;
	
	public static void init(Context context, boolean clean) {
		
		client = PreferenceManager.getDefaultSharedPreferences(context);
		editor = client.edit();
		
		if (clean)
			editor.clear().commit();
	}
	
	public static String getString(Key key) {
		
		return client.getString(key.getName(), (String) key.getDefault());
	}
	
	public static void putString(Key key, String value) {
		
		if (value == null)
			editor.putString(key.getName(), (String)key.getDefault());
		else
			editor.putString(key.getName(), value);
		editor.commit();
	}
	
	public static boolean getBoolean(Key key) {
		
		return client.getBoolean(key.getName(), (Boolean)key.getDefault());
	}
	
	public static void putBoolean(Key key, boolean value) {
		
		editor.putBoolean(key.getName(), value);
		editor.commit();
	}
}
