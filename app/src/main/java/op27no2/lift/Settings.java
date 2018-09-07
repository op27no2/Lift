package op27no2.lift;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceActivity;
import android.view.Menu;
import android.widget.RemoteViews;

public class Settings extends PreferenceActivity{
    public static final String SENS = "my_slider";
    public static final String VOL = "my_volume";
    public static final String TN = "tapsound";
    public static final String VIB = "vibratemode";
    public static final String NOT = "notificationmode";
    public static final String MED = "mediamode";
    public static final String THR = "thresh";
    public static final String BCK = "backnum";
    public static final String TP1 = "tapnum";
    public static final String TP2 = "tapnum2";
    public static final String TIM = "timeout";
    public static final String VNM = "vibnum";
    public static final String SND = "soundnum";


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		System.out.println("isettingsstart");

        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.HONEYCOMB) {
            onCreatePreferenceActivity();
        } else {
            onCreatePreferenceFragment();
        }
        

        
        
        
        
    }
 	@SuppressWarnings("deprecation")
    private void onCreatePreferenceActivity() {
        addPreferencesFromResource(R.xml.settings);
    }
 	@Override
 	public void onBackPressed() {
		startActivity(new Intent(this, CalorieGraph.class));

 	}
 	
 	public void onStop(){
 		super.onStop();
 		finish();
 	}
 	
    @SuppressLint("NewApi")
    private void onCreatePreferenceFragment() {
        getFragmentManager().beginTransaction()
                .replace(android.R.id.content, new SettingsFragment ())
                .commit();
    }
	
    
	public float onSharedPreferenceChanged(SharedPreferences sharedPreferences,
				String key) {
			float hold = 0;
			boolean hold2 = true;
		if (key.equals(SENS)) {
			hold = sharedPreferences.getFloat(key, 0.5f);
		}
		if (key.equals(VOL)) {
			hold = sharedPreferences.getFloat(key, 0.5f);
		}
		if (key.equals(BCK)) {
			hold = Float.parseFloat(sharedPreferences.getString(key, "2"));
		}
		
		if (key.equals(TP2)) {
			hold = Float.parseFloat(sharedPreferences.getString(key, "1"));
		}
		
		
		if (key.equals(THR)) {
			hold = sharedPreferences.getFloat(key, 4f);
		}
		if (key.equals(TN)) {
			hold2 = sharedPreferences.getBoolean(key, true);
			if(hold2==true){
				hold = 1;
			}
			else{
				hold = 0;
			}
		}
		return hold;
	}
	
	
	public boolean onBooleanSharedPreferenceChanged(SharedPreferences sharedPreferences,
			String key) {
			boolean hold2 = true;
		if (key.equals(TN)) {
			hold2 = sharedPreferences.getBoolean(key, true);
		}
		if (key.equals(VIB)){
			hold2 = sharedPreferences.getBoolean(key, false);
		}
		if (key.equals(NOT)){
			hold2 = sharedPreferences.getBoolean(key, true);
		}
		if (key.equals(MED)){
			hold2 = sharedPreferences.getBoolean(key, false);
		}
		return hold2;
	}
	
	public String onStringSharedPreferenceChanged(SharedPreferences sharedPreferences,
			String key) {
			String hold2 = "default";
		if (key.equals(TP1)) {
			hold2 = sharedPreferences.getString(key, "1900");
		}
		if (key.equals(TP2)) {
			hold2 = sharedPreferences.getString(key, "1");
		}
		if (key.equals(TIM)) {
			hold2 = sharedPreferences.getString(key, "Pounds");
		}
		if (key.equals(VNM)) {
			hold2 = sharedPreferences.getString(key, "1");
		}
		if (key.equals(SND)) {
			hold2 = sharedPreferences.getString(key, "1");
		}
		// RemoteViews remoteViews = new RemoteViews(getPackageName(), R.layout.widget_layout);
    	
		return hold2;
	}
	
	
}
	
	

