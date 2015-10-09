package redes3.proyecto.nagiosalert;


import redes3.proyecto.nagiosalert.R;
import android.view.View.OnClickListener;
import android.support.v7.app.ActionBarActivity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
public class MainActivity extends ActionBarActivity {

	
	 private EditText  username =null;
	 private EditText  password =null;
	 private CheckBox recordar;
	 private SharedPreferences loginPreferences;
	 private SharedPreferences.Editor loginPrefsEditor;	
	 private Boolean saveLogin;
	 final Context context = this;

	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
	    username = (EditText)findViewById(R.id.etUsuario);
	    password = (EditText)findViewById(R.id.etContrasena);
	    recordar = (CheckBox)findViewById(R.id.cbRecordarDatos);
			    	
        loginPreferences = getSharedPreferences("loginPrefs", MODE_PRIVATE);
        loginPrefsEditor = loginPreferences.edit();

        saveLogin = loginPreferences.getBoolean("saveLogin", false);
        if (saveLogin == true) {
        	username.setText(loginPreferences.getString("username", ""));
            password.setText(loginPreferences.getString("password", ""));
            recordar.setChecked(true);
        }
        
        Button b = (Button)this.findViewById(R.id.btAjustes);
        b.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				// get prompts.xml view
				LayoutInflater layoutInflater = LayoutInflater.from(context);

				View promptView = layoutInflater.inflate(R.layout.prompts, null);

				AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);

				// set prompts.xml to be the layout file of the alertdialog builder
				alertDialogBuilder.setView(promptView);

				final EditText input = (EditText) promptView.findViewById(R.id.userInput);

				// setup a dialog window
				
				alertDialogBuilder
						.setCancelable(false)
						.setPositiveButton("OK", new DialogInterface.OnClickListener() {
									public void onClick(DialogInterface dialog, int id) {
										// get user input and set it to result
										MyApplication state =((MyApplication) getApplicationContext());
										state.setUrl("http://"+input.getText().toString()+":8080/Server-NagiosAlert/Actualizar");
										state.setUrlGCM("http://"+input.getText().toString()+":8080/Server-NagiosAlert/GCM");
									}
								})
						.setNegativeButton("Cancel",
								new DialogInterface.OnClickListener() {
									public void onClick(DialogInterface dialog,	int id) {
										dialog.cancel();
									}
								});

				// create an alert dialog
				AlertDialog alertD = alertDialogBuilder.create();

				alertD.show();				
			}
 
        });

			
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	//@Override
//	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
//		int id = item.getItemId();
	//	if (id == R.id.action_settings) {
			//return true;
		//}
		//return super.onOptionsItemSelected(item);
	//}
	
	
	// Default user and password just for the testing process
	public Boolean checkUsername(String Username){
		
		if(Username.equals("Admin"))
			return true;
		
		return false;
	}
	
	public Boolean checkContrasena(String Contrasena){
		
		if(Contrasena.equals("Admin"))
			return true;
		
		return false;
	}	
	
	
	public void goLogin(View view) {
		
		
		if( checkUsername(username.getText().toString()) && (checkContrasena(password.getText().toString()))){
			/*
			 * El siguiente if maneja el checkbox, y almacena los datos del usuario.
			 */
		       recordar = (CheckBox)findViewById(R.id.cbRecordarDatos);
		       
		       if (recordar.isChecked()) {
		            loginPrefsEditor.putBoolean("saveLogin", true);
		            loginPrefsEditor.putString("username", username.getText().toString());
		            loginPrefsEditor.putString("password", password.getText().toString());
		            loginPrefsEditor.commit();
		        } else {
		            loginPrefsEditor.clear();
		            loginPrefsEditor.commit();
		        }
		       //llama al método que cambia la vista (si cumple los requisitos).
		       
		       
		       startActivity(new Intent(MainActivity.this, HomeActivity.class));

		}//FinIf.
	}
	
}
