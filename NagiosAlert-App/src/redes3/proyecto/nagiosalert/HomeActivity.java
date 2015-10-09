package redes3.proyecto.nagiosalert;


import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.RadioButton;
public class HomeActivity extends ActionBarActivity {
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_menu);
	
	}
	
    public void goIr(View v){
    	
    	RadioButton r1 = (RadioButton)findViewById(R.id.radioButton1);
    	RadioButton r2 = (RadioButton)findViewById(R.id.radioButton2);
    	
    	if (r1.isChecked()){
    		startActivity(new Intent(HomeActivity.this, RefreshActivity.class));
    	}else{
    		startActivity(new Intent(HomeActivity.this, LogActivity.class));
    	}
    	
    }
}