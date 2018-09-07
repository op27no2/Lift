package op27no2.lift;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

public class Instructions extends Activity{

	@Override
	public void onCreate(Bundle savedInstanceState) {
	   	 super.onCreate(savedInstanceState);
	   	 setContentView(R.layout.instructions);
			System.out.println("instrstart");

	
	}
	@Override
	public void onBackPressed() {
		Intent intent1 = new Intent(this, CalorieGraph.class);
	    startActivity(intent1);
	}
	
	public void onStop(){
 		super.onStop();
 		finish();
 	}
//	public void onStop(){
//		super.onStop();
//		
//	}

	
}