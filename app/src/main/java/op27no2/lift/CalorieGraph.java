package op27no2.lift;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.OnSharedPreferenceChangeListener;
import android.graphics.Color;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.Display;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;

import op27no2.lift.GraphView.GraphViewData;
import op27no2.lift.GraphViewSeries.GraphViewSeriesStyle;


public class CalorieGraph extends Activity implements OnSharedPreferenceChangeListener {
	
	public SharedPreferences prefs;
    public SharedPreferences.Editor edt;
    public EditText textAccel; 	
	public double prev = 1d;
	public float newpoint = 0;
	public GraphViewSeries exampleSeries;
	public GraphViewSeries exampleSeries2;
	public GraphViewSeries exampleSeries3;
	public GraphViewData[] data;
	public GraphViewData[] data2;
	public float thresh = 4;
	public SharedPreferences prefs2;
	public int startdom;
	public int stopdom;
	public int taps;
	public int[] values = {-250, -250, -300, -500, 100, 500, -100, -750, -750, -750,-250, -250, -300, -500, 100, 500, -100, -750, -750, -750,-250, -250, -300, -500, 100, 500, -100, -750, -750, -1000};
	public int maxY = 10;
	public TextView total;
	public TextView tvw;
	
	public ArrayList<Integer> datalist = new ArrayList<Integer>();
	public String datastore;
	public String[] stringarray;
	public int listsize;
	public int num;
	public int calories;
	public LinearLayout layout;
	public GraphView graphView;
	public Display display;
	public int orientation;
	private boolean isInFocus = false;
	public int widgetId;
	/** Called when the activity is first created. */

	@Override
	public void onCreate(Bundle savedInstanceState) {
	   	 super.onCreate(savedInstanceState);
	   	 setContentView(R.layout.caloriegraph);
	    
        prefs2 = this.getSharedPreferences("PREFS", Context.MODE_PRIVATE);
        int widgetId = prefs2.getInt("currentId", 2);
        System.out.println("activityPRefId:"+widgetId);
        
	    prefs = getSharedPreferences("PREFS"+widgetId, Context.MODE_PRIVATE);
	    edt = getSharedPreferences("PREFS"+widgetId, Context.MODE_PRIVATE).edit();
    	
	   
	    prefs2 = PreferenceManager.getDefaultSharedPreferences(this);
		prefs2.registerOnSharedPreferenceChangeListener(this);
		
		Settings PrefListener = new Settings();
		
	    calories = prefs.getInt("calories", -2000);
	    
	 //   thresh = prefs.getFloat("thresh",4);
	//	thresh = PrefListener.onSharedPreferenceChanged(prefs, "thresh");

	    
	   
	
		listsize = prefs.getInt("datasize", 0);
        if (listsize > 0){
        		String test = prefs.getString("data", "");
        		stringarray = test.split(",");
        		for(int i = 0; i < stringarray.length ; i++){
        			datalist.add(i, Integer.parseInt(stringarray[i]));
        		}
        	int num = datalist.size();
	    data = new GraphViewData[num+1];
	    data2 = new GraphViewData[2];

	    double v=0;
	    for (int i=0; i<num; i++) {
	       v += 0.2;
	       data[i] = new GraphViewData(i, datalist.get(i));
	    }
	    data2[0] = new GraphViewData(0, 0);
	    data2[1] = new GraphViewData(num, 0);
	    data[num] = new GraphViewData(num,calories);
        }
        
        else{
        	data = new GraphViewData[2];
     	    data2 = new GraphViewData[2];
     	    data[0] = new GraphViewData(0 ,0);
     	    data[1] = new GraphViewData(1 ,calories);
    	    data2[0] = new GraphViewData(0, 0);
     	    data2[1] = new GraphViewData(1, 0);
        }
	    
	    
	    GraphViewSeriesStyle style = new GraphViewSeriesStyle(Color.rgb(0, 255, 21),4);
	    GraphViewSeriesStyle style2 = new GraphViewSeriesStyle(Color.rgb(234, 0, 0),1);
	    exampleSeries = new GraphViewSeries("",style, data);
	    exampleSeries2 = new GraphViewSeries("",style2, data2);
	    
	    
	    	graphView = new LineGraphView(
	    	      this // context
	    	      , "" // heading
	    	);
	    	graphView.setScrollable(true);
	    	graphView.setScalable(true);
	    	if (data.length > 8){
	    	graphView.setViewPort(data.length-7,6);
	    	int maxY = max(data.length-8, data.length-1);
		    graphView.setManualYAxisBounds(maxY*1.1,-maxY*1.1);
	    	}
	    	else if(data.length == 2){
			    graphView.setViewPort(0,1);
			    int maxY = max(0, data.length-1);
			    maxY = 2200;
				graphView.setManualYAxisBounds(maxY*1.1,-maxY*1.1);
	    	}
	    	
	    	else{
		    graphView.setViewPort(0,data.length-1);
		    int maxY = max(0, data.length-1);
		    graphView.setManualYAxisBounds(maxY*1.1,-maxY*1.1);
	    	}

	    	// graphView.addSeries(exampleSeries2);
	    	graphView.addSeries(exampleSeries); // data
	    	
	    	// graphView.addSeries(exampleSeries3);
	    	graphView.getGraphViewStyle().setNumVerticalLabels(5);
	    	graphView.getGraphViewStyle().setNumHorizontalLabels(1);
	    	graphView.getGraphViewStyle().setVerticalLabelsWidth(0);
	    	((LineGraphView) graphView).setDrawBackground(true);
	    	// ((LineGraphView) graphView).setBackgroundColor(Color.argb(55, 0, 255, 21));
	    	// graphView.getGraphViewStyle().setGridColor(Color.DKGRAY);
	    	((LineGraphView) graphView).setDrawDataPoints(true);
	        ((LineGraphView) graphView).setDataPointsRadius(4f);
	    	layout = (LinearLayout) findViewById(R.id.graph1);
	    	layout.addView(graphView);
	    	
	    	
	    	 Button zoombtn = (Button) findViewById(R.id.zoomout);
	 	    zoombtn.setOnClickListener(
	 		        new OnClickListener() {
	 		            @Override
	 		            public void onClick(View v) {
	 		   	    	graphView.setViewPort(0,num);
	 				    int maxY = max(0, data.length-1);

	 		   	      /*  int maxY = Collections.max(datalist);
	 		   	        int minY = Collections.min(datalist);
	 		   	        if (minY < 0 ){
	 		   	        	minY = -minY;
	 		   	        }
	 		   	        if(minY > maxY){
	 		   	        	maxY = minY;
	 		   	        }*/
	 			    	graphView.setManualYAxisBounds(maxY*1.1,-maxY*1.1);
	 				    exampleSeries.resetData(data);
	 				    exampleSeries2.resetData(data2);
	 		            }
	 		        }
	 		 );
	 	   Button zoomend = (Button) findViewById(R.id.zoomend);
	 	    zoomend.setOnClickListener(
	 		        new OnClickListener() {
	 		            @Override
	 		            public void onClick(View v) {
	 		      	  //   data[num] = new GraphViewData(num,calories);

	 		   	    if (data.length > 8){
	 			    	graphView.setViewPort(data.length-7,6);
	 			    	int maxY = max(data.length-8, data.length-1);
	 				    graphView.setManualYAxisBounds(maxY*1.1,-maxY*1.1);
	 			    	}
	 			    	else{
	 				    graphView.setViewPort(0,data.length-1);
	 				    int maxY = max(0, data.length-1);
	 				    graphView.setManualYAxisBounds(maxY*1.1,-maxY*1.1);
	 			    	}
	 		   	    
	 				    exampleSeries.resetData(data);
	 				    exampleSeries2.resetData(data2);
	 		            }
	 		        }
	 		 );
	 	    Button left = (Button) findViewById(R.id.left);
	 	    left.setOnClickListener(
	 		        new OnClickListener() {
	 		            @Override
	 		            public void onClick(View v) {
	 		            	if (data.length > 8){
	 		 			    	graphView.setViewPort(0,7);
	 		 			    	int maxY = max(0, 7);
	 		 				    graphView.setManualYAxisBounds(maxY*1.1,-maxY*1.1);
	 		 			    	}
	 		 			     	else{
	 		 				    graphView.setViewPort(0,data.length-1);
	 		 				    int maxY = max(0, data.length-1);
	 		 				    graphView.setManualYAxisBounds(maxY*1.1,-maxY*1.1);
	 		 			    	}
	 		 				    exampleSeries.resetData(data);
	 		 				    exampleSeries2.resetData(data2);
	 		            }
	 		        }
	 		 );
	 	   Button yadjust = (Button) findViewById(R.id.yfit);
	 	    yadjust.setOnClickListener(
	 		        new OnClickListener() {
	 		            @Override
	 		            public void onClick(View v) {
	 		            		int low = (int) Math.floor(graphView.getMinX(false));
	 		            		int high = (int) Math.floor(graphView.getMaxX(false));
	 		            		int maxY = max(low, high);
	 		 				    graphView.setManualYAxisBounds(maxY*1.1,-maxY*1.1);
	 		 				    exampleSeries.resetData(data);
	 		 				    exampleSeries2.resetData(data2);
	 		            }
	 		        }
	 		 );
	 	    
	 	    
	}

	
	
	public int max(int x, int y){
		int result = 0;
		if(data.length >2){
			if( y == data.length-1){
				for (int i = x; i < y; i++)
					if (Math.abs(datalist.get(i)) > result){
						result = Math.abs(datalist.get(i));
					}
					if (Math.abs(calories) > result){
						result = Math.abs(calories);
					}
			}
			else if(y != data.length-1){
			
				for (int i = x; i < y+1; i++){
					if (Math.abs(datalist.get(i)) > result){
					result = Math.abs(datalist.get(i));
					}
				}	
			}
			
		}
		if(data.length <3){
			result = Math.abs(calories);
		}
		return result;
	}
	/*@Override
	public void onPause(){
		super.onPause();

            finish();
        
	}*/
	 
	@Override
	public void onWindowFocusChanged(boolean hasFocus) {
	    super.onWindowFocusChanged(hasFocus);
	    isInFocus = hasFocus;
	}

	@Override
	public void onStop() {
	    super.onStop();
	    if (!isInFocus) finish();
	}
	
	
	public void appender(){
	}
	/*private BroadcastReceiver mMessageReceiver = new BroadcastReceiver() {
	    @Override
	    public void onReceive(Context context, Intent intent) {
	        float newpoint = intent.getFloatExtra("data", 0);
	        taps = intent.getIntExtra("tapcount", 0);
	        boolean status = intent.getBooleanExtra("running", false);
	       
		      prev += 1d;
		      exampleSeries.appendData(new GraphViewData(prev, newpoint), true,300);
		      exampleSeries2.appendData(new GraphViewData(prev, thresh), true,300);
		      exampleSeries3.appendData(new GraphViewData(prev, -thresh), true,300);

		      TextView startText = (TextView) myFragmentView.findViewById(R.id.starttext2);
		      TextView stopText = (TextView) myFragmentView.findViewById(R.id.stoptext2);
		      
		       if(status == false){
		    		 startText.setText(taps+"/"+startdom);
		      }
		      else if (status == true){
		    		  stopText.setText(taps+"/"+stopdom);
		      }
		      
		      
	    }
	};*/


	@Override
	public void onSharedPreferenceChanged(SharedPreferences sharedPreferences,
			String key) {
		/*startdom = (int) Float.parseFloat(prefs2.getString("tapnum", "3"));
	    stopdom = (int) Float.parseFloat(prefs2.getString("tapnum2", "5"));
  
		TextView startText = (TextView) myFragmentView.findViewById(R.id.starttext2);
	    TextView stopText = (TextView) myFragmentView.findViewById(R.id.stoptext2);
	    
		 startText.setText(taps+"/"+startdom);
		 stopText.setText(taps+"/"+stopdom);*/

	}
	
	@Override
	public void onResume(){
		   super.onResume();
/*
		System.out.println("ONRESUMEEDDD");

	    prefs = getSharedPreferences("PREFS", Context.MODE_PRIVATE);
	 		
	    calories = prefs.getInt("calories", -2000);
		System.out.println("CAlsss:"+calories);

	     data[num] = new GraphViewData(num,calories);
	     exampleSeries.resetData(data); 
	     graphView.redrawAll();
	     System.out.println("resumefinisheddddddd");*/
	    
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	
	public boolean onOptionsItemSelected(MenuItem item){
		switch(item.getItemId()){
		case R.id.action_settings:
			System.out.println("settingspush");
			Intent intn = new Intent (this, Settings.class);
			this.startActivity (intn);
			break;
		case R.id.action_instructions:
			System.out.println("instructionsspush");
			Intent intn2 = new Intent (this, Instructions.class);
			this.startActivity (intn2);
			break;	
			default:
				System.out.println("defaultsspush");
			return super.onOptionsItemSelected(item);
		}
		return true;
	}
	
//	public void onInfoClick(MenuItem item){
//		Intent int2 = new Intent(this, Instructions.class);
//	    startActivity(int2);
//	}
//	
//	public void onSettingsClick(MenuItem item){
//		Intent intt = new Intent(this, Settings.class);
//	    startActivity(intt);
//	}
	
}
