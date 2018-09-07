package op27no2.lift;

import android.appwidget.AppWidgetManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Vibrator;
import android.widget.RemoteViews;

public class MyBigWidgetIntentReceiverX extends BroadcastReceiver {
	public int calories;
	public int caloriesweek;
    public int days;
    public Vibrator v = null;
    public int big;
    public int small;
    
	public void onReceive(Context context, Intent intent) {
		v = (Vibrator) context.getSystemService(Context.VIBRATOR_SERVICE);
        int widgetId = intent.getIntExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, 2);
		System.out.println("widget clicked ID:"+widgetId);

        if(intent.getAction().equals("pl.looksok.intent.action.bCHANGE_PICTUREX")){
			updateWidgetPictureAndButtonListener(context, widgetId);

			//v.vibrate(25);
		}
		if(intent.getAction().equals("pl.looksok.intent.action.bCHANGE_PICTURE2X")){
			v.vibrate(19);
			updateWidgetPictureAndButtonListener2(context, widgetId);
		}
		if(intent.getAction().equals("pl.looksok.intent.action.bCHANGE_PICTURE3X")){
			v.vibrate(25);
			updateWidgetPictureAndButtonListener3(context, widgetId);
		}
		if(intent.getAction().equals("pl.looksok.intent.action.bCHANGE_PICTURE4X")){
			v.vibrate(19);
			updateWidgetPictureAndButtonListener4(context,widgetId);
		}
		if(intent.getAction().equals("pl.looksok.intent.action.bNEW_ACTIVITYX")){

			launchActivity(context, widgetId);
		}
	}
	private void launchActivity(Context context, int appWidgetId) {

		SharedPreferences.Editor edity = context.getSharedPreferences("PREFS", Context.MODE_PRIVATE).edit();
		edity.putInt("currentId", appWidgetId);
		edity.putBoolean("big", true);
		System.out.println("puttrue");
		System.out.println("Activity Launch");

		edity.commit();
		Intent intn = new Intent (context, CalorieGraph.class);
		intn.setFlags (Intent.FLAG_ACTIVITY_NEW_TASK);
		context.startActivity (intn);
	}
	private void updateWidgetPictureAndButtonListener(Context context, int appWidgetId) {
		System.out.println("Button 1");

		RemoteViews remoteViews = new RemoteViews(context.getPackageName(), R.layout.bigwidget_layout);
        SharedPreferences prefs = context.getSharedPreferences("PREFS"+appWidgetId, Context.MODE_PRIVATE);
		SharedPreferences.Editor editor = context.getSharedPreferences("PREFS"+appWidgetId, Context.MODE_PRIVATE).edit();
        calories = prefs.getInt("calories", -2000);
        caloriesweek = prefs.getInt("caloriesweek", -2000);
		big = Integer.parseInt(prefs.getString("vibnum", "100"));
        
 		calories = calories + big;
 		caloriesweek = caloriesweek+big;
		remoteViews.setTextViewText(R.id.update, String.valueOf(calories));
		remoteViews.setTextViewText(R.id.updateweek, String.valueOf(caloriesweek));
		if(calories < 0){
			remoteViews.setTextColor(R.id.update, Color.RED);
		}
		if(calories > 0){
			remoteViews.setTextColor(R.id.update, Color.GREEN);
		}
		if(caloriesweek < 0){
			remoteViews.setTextColor(R.id.updateweek, Color.RED);
		}
		if(caloriesweek > 0){
			remoteViews.setTextColor(R.id.updateweek, Color.GREEN);
		}
		editor.putInt("calories",calories);
		editor.putInt("caloriesweek",caloriesweek);
		editor.commit();
		//REMEMBER TO ALWAYS REFRESH YOUR BUTTON CLICK LISTENERS!!!
		remoteViews.setOnClickPendingIntent(R.id.plus, MyBigWidgetProviderX.buildButtonPendingIntent(context, appWidgetId));
		MyBigWidgetProviderX.pushWidgetUpdate(context.getApplicationContext(), remoteViews, appWidgetId);
	}
	
	private void updateWidgetPictureAndButtonListener2(Context context, int appWidgetId) {
		RemoteViews remoteViews = new RemoteViews(context.getPackageName(), R.layout.bigwidget_layout);

        SharedPreferences prefs = context.getSharedPreferences("PREFS"+appWidgetId, Context.MODE_PRIVATE);
		SharedPreferences.Editor editor = context.getSharedPreferences("PREFS"+appWidgetId, Context.MODE_PRIVATE).edit();
        calories = prefs.getInt("calories", -2000);
        caloriesweek = prefs.getInt("caloriesweek", -2000);
		big = Integer.parseInt(prefs.getString("vibnum", "100"));
        
 		calories = calories - big;
 		caloriesweek = caloriesweek - big;
		remoteViews.setTextViewText(R.id.update, String.valueOf(calories));
		remoteViews.setTextViewText(R.id.updateweek, String.valueOf(caloriesweek));
		if(calories < 0){
			remoteViews.setTextColor(R.id.update, Color.RED);
		}
		if(calories > 0){
			remoteViews.setTextColor(R.id.update, Color.GREEN);
		}
		if(caloriesweek < 0){
			remoteViews.setTextColor(R.id.updateweek, Color.RED);
		}
		if(caloriesweek > 0){
			remoteViews.setTextColor(R.id.updateweek, Color.GREEN);
		}
		editor.putInt("calories",calories);
		editor.putInt("caloriesweek",caloriesweek);
		editor.commit(); 
		//REMEMBER TO ALWAYS REFRESH YOUR BUTTON CLICK LISTENERS!!!
		remoteViews.setOnClickPendingIntent(R.id.minus, MyBigWidgetProviderX.buildButtonPendingIntent2(context, appWidgetId));

		MyBigWidgetProviderX.pushWidgetUpdate(context.getApplicationContext(), remoteViews, appWidgetId);
	}
	private void updateWidgetPictureAndButtonListener3(Context context, int appWidgetId) {
		RemoteViews remoteViews = new RemoteViews(context.getPackageName(), R.layout.bigwidget_layout);

        SharedPreferences prefs = context.getSharedPreferences("PREFS"+appWidgetId, Context.MODE_PRIVATE);
		SharedPreferences.Editor editor = context.getSharedPreferences("PREFS"+appWidgetId, Context.MODE_PRIVATE).edit();
        calories = prefs.getInt("calories", -2000);
        caloriesweek = prefs.getInt("caloriesweek", -2000);
		small = Integer.parseInt(prefs.getString("soundnum", "10"));
        
 		calories = calories + small;
 		caloriesweek = caloriesweek + small;
		remoteViews.setTextViewText(R.id.update, String.valueOf(calories));
		remoteViews.setTextViewText(R.id.updateweek, String.valueOf(caloriesweek));
		if(calories < 0){
			remoteViews.setTextColor(R.id.update, Color.RED);
		}
		if(calories > 0){
			remoteViews.setTextColor(R.id.update, Color.GREEN);
		}
		if(caloriesweek < 0){
			remoteViews.setTextColor(R.id.updateweek, Color.RED);
		}
		if(caloriesweek > 0){
			remoteViews.setTextColor(R.id.updateweek, Color.GREEN);
		}
		editor.putInt("calories",calories);
		editor.putInt("caloriesweek",caloriesweek);
		editor.commit();
		//REMEMBER TO ALWAYS REFRESH YOUR BUTTON CLICK LISTENERS!!!
		remoteViews.setOnClickPendingIntent(R.id.plussmall, MyBigWidgetProviderX.buildButtonPendingIntent3(context, appWidgetId));

		MyBigWidgetProviderX.pushWidgetUpdate(context.getApplicationContext(), remoteViews, appWidgetId);
	}
	private void updateWidgetPictureAndButtonListener4(Context context, int appWidgetId) {
		RemoteViews remoteViews = new RemoteViews(context.getPackageName(), R.layout.bigwidget_layout);

        SharedPreferences prefs = context.getSharedPreferences("PREFS"+appWidgetId, Context.MODE_PRIVATE);
		SharedPreferences.Editor editor = context.getSharedPreferences("PREFS"+appWidgetId, Context.MODE_PRIVATE).edit();
        calories = prefs.getInt("calories", -2000);
        caloriesweek = prefs.getInt("caloriesweek", -2000);
		small = Integer.parseInt(prefs.getString("soundnum", "10"));
        
 		calories = calories -small;
 		caloriesweek = caloriesweek - small;
		remoteViews.setTextViewText(R.id.update, String.valueOf(calories));
		remoteViews.setTextViewText(R.id.updateweek, String.valueOf(caloriesweek));
		if(calories < 0){
			remoteViews.setTextColor(R.id.update, Color.RED);
		}
		if(calories > 0){
			remoteViews.setTextColor(R.id.update, Color.GREEN);
		}
		if(caloriesweek < 0){
			remoteViews.setTextColor(R.id.updateweek, Color.RED);
		}
		if(caloriesweek > 0){
			remoteViews.setTextColor(R.id.updateweek, Color.GREEN);
		}
		editor.putInt("calories",calories);
		editor.putInt("caloriesweek",caloriesweek);
		editor.commit();
		//REMEMBER TO ALWAYS REFRESH YOUR BUTTON CLICK LISTENERS!!!
		remoteViews.setOnClickPendingIntent(R.id.minussmall, MyBigWidgetProviderX.buildButtonPendingIntent4(context, appWidgetId));

		MyBigWidgetProviderX.pushWidgetUpdate(context.getApplicationContext(), remoteViews, appWidgetId);
	}

}
