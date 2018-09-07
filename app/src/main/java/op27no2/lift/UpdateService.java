package op27no2.lift;

import java.util.ArrayList;
import java.util.Calendar;

import android.app.PendingIntent;
import android.app.Service;
import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.IBinder;
import android.widget.RemoteViews;

public class UpdateService extends Service {
	 public int calories;
	  public int caloriesweek;
	  public int days;
	  public boolean weekset;
	  public boolean firstrunn;
	  public SharedPreferences prefs2;
	  public ArrayList<Integer> datalist = new ArrayList<Integer>();
	  public String datastore;
	  public String[] stringarray;
	  public int listsize;
	  public String tdee;
	  public int basecal;
	  public int reset;

	@Override
	public void onStart(Intent intent, int startId) {
	    AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(this);

		
		ComponentName thisWidget = new ComponentName(this,
		        MyWidgetProviderX.class);
		    int[] allWidgetIds = appWidgetManager.getAppWidgetIds(thisWidget);
		    for (int widgetId : allWidgetIds) {
		    	
		    	
		    	RemoteViews remoteViews = new RemoteViews(this.getPackageName(), R.layout.widget_layout);
				remoteViews.setOnClickPendingIntent(R.id.plus, buildButtonPendingIntent(this, widgetId));
				remoteViews.setOnClickPendingIntent(R.id.minus, buildButtonPendingIntent2(this, widgetId));
				remoteViews.setOnClickPendingIntent(R.id.plussmall, buildButtonPendingIntent3(this, widgetId));
				remoteViews.setOnClickPendingIntent(R.id.minussmall, buildButtonPendingIntent4(this, widgetId));
				remoteViews.setOnClickPendingIntent(R.id.center, activityPending(this, widgetId));

				SharedPreferences prefs = this.getSharedPreferences("PREFS"+widgetId, Context.MODE_PRIVATE);
				SharedPreferences.Editor editor = this.getSharedPreferences("PREFS"+widgetId, Context.MODE_PRIVATE).edit();
		        days = prefs.getInt("numday", 1);
		        firstrunn = prefs.getBoolean("firstrunn", true);
		        calories = prefs.getInt("calories", -2000);
		        caloriesweek = prefs.getInt("caloriesweek", -2000);
			    

		        basecal = prefs.getInt("basecal", 2000);
		        
		        String tap2 = prefs.getString("tapnum2", "1");
		        reset = (int) Double.parseDouble(tap2);
		        
		        Calendar c = Calendar.getInstance(); 
		        if (firstrunn == true){
		        	
		        	days = c.get(6);
		 			editor.putInt("numday", days);
		 			editor.commit();
			 		System.out.println("daystest"+days);

		        	datalist.add(0,0);
		        	System.out.println("first run detected");
		        	StringBuilder sb = new StringBuilder();
                    for (int j = 0; j < datalist.size(); j++){
                       	 sb.append(datalist.get(j)).append(",");
                        } 
                    	System.out.println("string::"+sb.toString());
	                    editor.putString("data", sb.toString());
	                    editor.commit();
		        }
		        
	        	System.out.println("update detected");
	 			editor.putBoolean("firstrunn", false);
	 	 		editor.commit();

		        listsize = prefs.getInt("datasize", 1);
		        if (listsize > 0){
		        		String test = prefs.getString("data", "");
		        		System.out.println("Stringtest1:"+test);
		        		stringarray = test.split(",");
		        		for(int i = 0; i < stringarray.length ; i++){
		        			datalist.add(i, Integer.parseInt(stringarray[i]));
		        		}
		        }
		        
		        
		        
		        
				
		 		int days2 = c.get(6);
		 		System.out.println("days1:"+days);
		 		System.out.println("days2:"+days2);
		 		int weekday = c.get(7);
		 		if(days2 != days){
			 		System.out.println("INCONdays:"+days);
			 		System.out.println("INCONdays2:"+days2);
		 			datalist.add(listsize, calories);
                    StringBuilder sb = new StringBuilder();
                    for (int j = 0; j < datalist.size(); j++){
                   	 sb.append(datalist.get(j)).append(",");
                    } 
                    editor.putString("data", sb.toString());
	        		System.out.println("Stringtest2:"+sb.toString());
		 			editor.putInt("datasize", datalist.size());
		 			calories = -basecal;
		 			if(firstrunn == false){
		 			caloriesweek = caloriesweek - basecal;
		 			}
		 			days = days2;
		 			editor.putInt("numday", days2);
		 			
		 			editor.putInt("calories", calories);
		 			editor.putInt("caloriesweek", caloriesweek);
		 	 		editor.commit();
					remoteViews.setTextViewText(R.id.update, String.valueOf(calories));
					remoteViews.setTextColor(R.id.update, Color.RED);
					remoteViews.setTextViewText(R.id.updateweek, String.valueOf(caloriesweek));
					if(caloriesweek < 0){
						remoteViews.setTextColor(R.id.updateweek, Color.RED);
					}
					if(caloriesweek > 0){
						remoteViews.setTextColor(R.id.updateweek, Color.GREEN);
					}
		 		}
		 		weekset = prefs.getBoolean("setter", false);
		 		if (weekday == reset && weekset == false){
		 			caloriesweek = -basecal;
					remoteViews.setTextViewText(R.id.updateweek, String.valueOf(caloriesweek));
					remoteViews.setTextColor(R.id.update, Color.RED);
					editor.putBoolean("setter", true);
					editor.putInt("caloriesweek", caloriesweek);
					editor.commit();
		 		}
		 		else if(weekday != reset){
		 			editor.putBoolean("setter", false);
		 			editor.commit();
		 		}
		 		
			appWidgetManager.updateAppWidget(widgetId, remoteViews);
			datalist.clear();
		    }
		    stopSelf();
		    super.onStart(intent, startId);
	}	
	public static PendingIntent buildButtonPendingIntent(Context context, int appWidgetId) {
		Intent intent = new Intent();
	    intent.setAction("pl.looksok.intent.action.CHANGE_PICTURE");
	    intent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID,
                appWidgetId);
	    return PendingIntent.getBroadcast(context, appWidgetId, intent, PendingIntent.FLAG_UPDATE_CURRENT);
	}
	
	public static PendingIntent buildButtonPendingIntent2(Context context, int appWidgetId) {
		Intent intent2 = new Intent();
	    intent2.setAction("pl.looksok.intent.action.CHANGE_PICTURE2");
	    intent2.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID,
                appWidgetId);
	    return PendingIntent.getBroadcast(context, appWidgetId, intent2, PendingIntent.FLAG_UPDATE_CURRENT);
	}
	public static PendingIntent buildButtonPendingIntent3(Context context, int appWidgetId) {
		Intent intent3 = new Intent();
	    intent3.setAction("pl.looksok.intent.action.CHANGE_PICTURE3");
	    intent3.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID,
                appWidgetId);
	    return PendingIntent.getBroadcast(context, appWidgetId, intent3, PendingIntent.FLAG_UPDATE_CURRENT);
	}
	
	public static PendingIntent buildButtonPendingIntent4(Context context, int appWidgetId) {
		Intent intent4 = new Intent();
	    intent4.setAction("pl.looksok.intent.action.CHANGE_PICTURE4");
	    intent4.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID,
                appWidgetId);
	    return PendingIntent.getBroadcast(context, appWidgetId, intent4, PendingIntent.FLAG_UPDATE_CURRENT);
	}
	
	public static PendingIntent activityPending(Context context, int appWidgetId) {
		Intent intent5 = new Intent();
	    intent5.setAction("pl.looksok.intent.action.NEW_ACTIVITY");
	    intent5.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID,
                appWidgetId);
	    return PendingIntent.getBroadcast(context, appWidgetId, intent5, PendingIntent.FLAG_UPDATE_CURRENT);
	}
		
	@Override
	public IBinder onBind(Intent intent) {
		// TODO Auto-generated method stub
		return null;
	}

}
