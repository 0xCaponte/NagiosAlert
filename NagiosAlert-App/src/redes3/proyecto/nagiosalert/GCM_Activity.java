package redes3.proyecto.nagiosalert;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.concurrent.atomic.AtomicInteger;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager.NameNotFoundException;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.gcm.GoogleCloudMessaging;
import com.google.gson.Gson;

public class GCM_Activity extends Activity {

	 public static final String EXTRA_MESSAGE = "message";
	    public static final String PROPERTY_REG_ID = "registration_id";
	    private static final String PROPERTY_APP_VERSION = "appVersion";
	    private final static int PLAY_SERVICES_RESOLUTION_REQUEST = 9000;

	    String SENDER_ID = "45678678678";

	    static final String TAG = "GCMDemo";

	    TextView mDisplay;
	    GoogleCloudMessaging gcm;
	    AtomicInteger msgId = new AtomicInteger();
	    SharedPreferences prefs;
	    Context context;

	    String regid;

	    @Override
	    public void onCreate(Bundle savedInstanceState) {
	        super.onCreate(savedInstanceState);


	        context = getApplicationContext();

	        if (checkPlayServices()) {
	            gcm = GoogleCloudMessaging.getInstance(this);
	            regid = getRegistrationId(context);
	            System.out.println("ID DE REGISTRO ---->"+regid);
                MyApplication state =((MyApplication) getApplicationContext());

	            if (regid.isEmpty()) {
	                registerInBackground();
	            }else{
	            	state.setKey(regid);
	            	startActivity(new Intent(GCM_Activity.this, HomeActivity.class));
	            }
	        } else {
	            Log.i(TAG, "No valid Google Play Services APK found.");
	        }
	    }
	    
	    
	
	public String getRegistrationId(Context context) {
	    final SharedPreferences prefs = getGCMPreferences(context);
	    String registrationId = prefs.getString(PROPERTY_REG_ID, "");
	    
	    if (registrationId.isEmpty()) {
	        Log.i(TAG, "Registration not found.");
	        return "";
	    }

	    int registeredVersion = prefs.getInt(PROPERTY_APP_VERSION, Integer.MIN_VALUE);
	    int currentVersion = getAppVersion(context);
	    if (registeredVersion != currentVersion) {
	        Log.i(TAG, "App version changed.");
	        return "";
	    }
	    return registrationId;
	}

	public static int getAppVersion(Context context) {
	    try {
	        PackageInfo packageInfo = context.getPackageManager()
	                .getPackageInfo(context.getPackageName(), 0);
	        return packageInfo.versionCode;
	    } catch (NameNotFoundException e) {
	        // should never happen
	        throw new RuntimeException("Could not get package name: " + e);
	    }
	}
	public SharedPreferences getGCMPreferences(Context context) {
	    return getSharedPreferences(GCM_Activity.class.getSimpleName(),
	            Context.MODE_PRIVATE);
	}
	
	@Override
	protected void onResume() {
	    super.onResume();
	    checkPlayServices();
	}


	public boolean checkPlayServices() {
	    int resultCode = GooglePlayServicesUtil.isGooglePlayServicesAvailable(this);
	    if (resultCode != ConnectionResult.SUCCESS) {
	        if (GooglePlayServicesUtil.isUserRecoverableError(resultCode)) {
	            GooglePlayServicesUtil.getErrorDialog(resultCode, this,
	                    PLAY_SERVICES_RESOLUTION_REQUEST).show();
	        } else {
	            Log.i(TAG, "This device is not supported.");
	            finish();
	        }
	        return false;
	    }
	    return true;
	}
	

	public void registerInBackground() {
	    new AsyncTask() {
	        protected String doInBackground(Object... params) {
	            String msg = "";
	            try {
	                if (gcm == null) {
	                    gcm = GoogleCloudMessaging.getInstance(context);
	                }
	                regid = gcm.register(SENDER_ID);
	                
	                System.out.println("ID DE REGISTRO ---->"+regid);
	                
	                msg = "Device registered, registration ID=" + regid;
	                MyApplication state =((MyApplication) getApplicationContext());
	                state.setKey(regid);
	                storeRegistrationId(context, regid);
	                startActivity(new Intent(GCM_Activity.this, HomeActivity.class));
	            } catch (IOException ex) {
	                msg = "Error :" + ex.getMessage();
	            }
	            return msg;
	        }

	        protected void onPostExecute(String msg) {
	        }
	    }.execute(null, null, null);
	    
	}
	
	/* Envia una lista de incidentes al servidor */
    public Boolean enviarIncidentes(String url, String info)
            throws IOException {

        InputStream stream = null;
        String result = "";

        // De ser posible abre una conexion con el servidor y envia
        try {

            // Crea el cliente http y la solicitud de POST,
            HttpClient httpclient = new DefaultHttpClient();
            HttpPost httpPost = new HttpPost(url);

            // Transforma la lista de incidentes enu n objeto JSON
            String json = "";
            json = new Gson().toJson(info);

            // Establece la entity y los header del POST
            StringEntity se = new StringEntity(json);
            httpPost.setEntity(se);
            httpPost.setHeader("Accept", "application/json");
            httpPost.setHeader("Content-type", "application/json");

            // Ejecuta la solicitud de POST y espera la respuesta
            HttpResponse httpResponse = httpclient.execute(httpPost);
            stream = httpResponse.getEntity().getContent();

            // Transforma el stream a in string
            if (stream != null) {
                BufferedReader bufferedReader = new BufferedReader(
                        new InputStreamReader(stream));
                String linea = "";
                result = "";
                while ((linea = bufferedReader.readLine()) != null)
                    result += linea;
            } else
                result = "Fail";

        } catch (Exception ex) {
            ex.printStackTrace();
        }

        if (result == "Fail") {
            return false;
        } else {
            return true;
        }

    }

	    public void storeRegistrationId(Context context, String regId) {
	        final SharedPreferences prefs = getGCMPreferences(context);
	        int appVersion = getAppVersion(context);
	        Log.i(TAG, "Saving regId on app version " + appVersion);
	        SharedPreferences.Editor editor = prefs.edit();
	        editor.putString(PROPERTY_REG_ID, regId);
	        editor.putInt(PROPERTY_APP_VERSION, appVersion);
	        editor.commit();
	    }
	    
	    public void irSiguiente(){

            startActivity(new Intent(GCM_Activity.this, RefreshActivity.class));
	    }
	    
}
