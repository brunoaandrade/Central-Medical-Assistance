package com.app.maps;

import java.lang.reflect.Type;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

import com.app.maps.commonClasses.BloodPressureMeasure;
import com.app.maps.commonClasses.DrugTakeRegister;
import com.app.maps.commonClasses.PAction;
import com.app.maps.commonClasses.PatientToken;
import com.app.maps.commonClasses.TemperatureMeasure;
import com.app.maps.databaseLocal.MySQLiteHelper;
import com.app.maps.handlers.JsonHandler;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.provider.Settings.Secure;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Activity which displays a login screen to the user, offering registration as
 * well.
 */
public class LoginActivity extends Activity {
	/**
	 * The default email to populate the email field with.
	 */
	public static final String EXTRA_EMAIL = "com.example.android.authenticatordemo.extra.EMAIL";

	/**
	 * Keep track of the login task to ensure we can cancel it if requested.
	 */
	private UserLoginTask mAuthTask = null;
	
	//Server URL for authentication
	private String mUrl;

	// Values for email and password at the time of the login attempt.
	private String mEmail;
	private String mPassword;
	
	//patientToken
	PatientToken pt = null;

	// UI references.
	private EditText mEmailView;
	private EditText mPasswordView;
	private View mLoginFormView;
	private View mLoginStatusView;
	private TextView mLoginStatusMessageView;
	private SharedPreferences pfm;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
		
		SharedPreferences prefs =  this.getSharedPreferences("com.app.maps", Context.MODE_PRIVATE);
		int l = prefs.getInt("patientID", -1); 
		
		pfm =  PreferenceManager.getDefaultSharedPreferences(this);
		
		if (l != -1){
			Intent intent = new Intent(LoginActivity.this, MainActivity.class);
			intent.setAction("AUTOMATIC_START");
		    startActivity(intent);
			finish();
		}

		setContentView(R.layout.activity_login);

		// Set up the login form.
		mEmail = getIntent().getStringExtra(EXTRA_EMAIL);
		mEmailView = (EditText) findViewById(R.id.email);
		mEmailView.setText(mEmail);

		mPasswordView = (EditText) findViewById(R.id.password);
		mPasswordView
				.setOnEditorActionListener(new TextView.OnEditorActionListener() {
					@Override
					public boolean onEditorAction(TextView textView, int id,
							KeyEvent keyEvent) {
						if (id == R.id.login || id == EditorInfo.IME_NULL) {
							attemptLogin();
							return true;
						}
						return false;
					}
				});

		mLoginFormView = findViewById(R.id.login_form);
		mLoginStatusView = findViewById(R.id.login_status);
		mLoginStatusMessageView = (TextView) findViewById(R.id.login_status_message);

		findViewById(R.id.sign_in_button).setOnClickListener(
				new View.OnClickListener() {
					@Override
					public void onClick(View view) {
						attemptLogin();
					}
				});
	}
	
	@Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.set_url_server) {
			insertUrl();
            return true;
        }
        return super.onOptionsItemSelected(item);
	}
	
	private void insertUrl(){
		AlertDialog.Builder alert = new AlertDialog.Builder(this);
		final SharedPreferences prefs =  PreferenceManager.getDefaultSharedPreferences(getBaseContext());
		
		alert.setTitle("Inserir URL");
		alert.setMessage("Insira o URL do servidor central");

		// Set an EditText view to get user input 
		final EditText input = new EditText(this);
		input.setText(prefs.getString("url_server", ""));
		input.selectAll();
		alert.setView(input);

		alert.setPositiveButton("OK", new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int whichButton) {
				URL url = null;
				String value = input.getText().toString();
				try {
					url = new URL(value);
				} catch (MalformedURLException e) {
					// TODO Auto-generated catch block
					Toast.makeText(getBaseContext(), "O URL está incorreto", Toast.LENGTH_SHORT).show();
					e.printStackTrace();
				}
				SharedPreferences.Editor editor = prefs.edit();
				editor.putString("url_server", url.toString());
				editor.commit();
			}
		});

		alert.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int whichButton) {
				// Canceled.
				dialog.cancel();
			}
		});
		AlertDialog ad = alert.create();
		ad.show();
	}
	
	@Override	
	public boolean onCreateOptionsMenu(Menu menu) {	
		super.onCreateOptionsMenu(menu);	
		getMenuInflater().inflate(R.menu.login, menu);	
		return true;	
	}

	/**
	 * Attempts to sign in or register the account specified by the login form.
	 * If there are form errors (invalid email, missing fields, etc.), the
	 * errors are presented and no actual login attempt is made.
	 */
	public void attemptLogin() {
		if (mAuthTask != null) {
			return;
		}
		// Reset errors.
		mEmailView.setError(null);
		mPasswordView.setError(null);

		// Store values at the time of the login attempt.
		mEmail = mEmailView.getText().toString();
		mPassword = mPasswordView.getText().toString();

		boolean cancel = false;
		View focusView = null;

		// Check for a valid password.
		if (TextUtils.isEmpty(mPassword)) {
			mPasswordView.setError(getString(R.string.error_field_required));
			focusView = mPasswordView;
			cancel = true;
		} else if (mPassword.length() < 2) {
			mPasswordView.setError(getString(R.string.error_invalid_password));
			focusView = mPasswordView;
			cancel = true;
		}

		// Check for a valid email address.
		if (TextUtils.isEmpty(mEmail)) {
			mEmailView.setError(getString(R.string.error_field_required));
			focusView = mEmailView;
			cancel = true;
		} else if (!mEmail.contains("@")) {
			mEmailView.setError(getString(R.string.error_invalid_email));
			focusView = mEmailView;
			cancel = true;
		}

		if (cancel) {
			// There was an error; don't attempt login and focus the first
			// form field with an error.
			focusView.requestFocus();
		} else {
			// Show a progress spinner, and kick off a background task to
			// perform the user login attempt.
			mLoginStatusMessageView.setText(R.string.login_progress_signing_in);
			showProgress(true);
			mAuthTask = new UserLoginTask();
			mAuthTask.execute((Void) null);
		}
	}

	/**
	 * Shows the progress UI and hides the login form.
	 */
	@TargetApi(Build.VERSION_CODES.HONEYCOMB_MR2)
	private void showProgress(final boolean show) {
		// On Honeycomb MR2 we have the ViewPropertyAnimator APIs, which allow
		// for very easy animations. If available, use these APIs to fade-in
		// the progress spinner.
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR2) {
			int shortAnimTime = getResources().getInteger(
					android.R.integer.config_shortAnimTime);

			mLoginStatusView.setVisibility(View.VISIBLE);
			mLoginStatusView.animate().setDuration(shortAnimTime)
					.alpha(show ? 1 : 0)
					.setListener(new AnimatorListenerAdapter() {
						@Override
						public void onAnimationEnd(Animator animation) {
							mLoginStatusView.setVisibility(show ? View.VISIBLE
									: View.GONE);
						}
					});

			mLoginFormView.setVisibility(View.VISIBLE);
			mLoginFormView.animate().setDuration(shortAnimTime)
					.alpha(show ? 0 : 1)
					.setListener(new AnimatorListenerAdapter() {
						@Override
						public void onAnimationEnd(Animator animation) {
							mLoginFormView.setVisibility(show ? View.GONE
									: View.VISIBLE);
						}
					});
		} else {
			// The ViewPropertyAnimator APIs are not available, so simply show
			// and hide the relevant UI components.
			mLoginStatusView.setVisibility(show ? View.VISIBLE : View.GONE);
			mLoginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
		}
	}

	/**
	 * Represents an asynchronous login/registration task used to authenticate
	 * the user.
	 */
	public class UserLoginTask extends AsyncTask<Void, Void, Boolean> {
		@Override
		protected Boolean doInBackground(Void... params) {
			// TODO: attempt authentication against a network service.
			Type listType;
			String s;
			
			mUrl = pfm.getString("url_server", "");
			
			if (mUrl == "") {
				return false;
			}
			
			//CriaÃ§Ã£o de um novo "jsonHandler" para tratamento do processo de envio
			//das credenciais do paciente para autenticaÃ§Ã£o.
			JsonHandler ja = new JsonHandler();
			pt = ja.loginPA(mUrl, mEmail, mPassword, Secure.getString(getBaseContext().getContentResolver(), Secure.ANDROID_ID));
			if (pt != null) {
				SharedPreferences prefs = getSharedPreferences("com.app.maps", Context.MODE_PRIVATE);
				prefs.edit().putInt("patientID", pt.getIdPatient()).commit();
				prefs.edit().putString("patientToken", pt.getToken()).commit();

				MySQLiteHelper msh = new MySQLiteHelper(getBaseContext());
				Gson gs =  new GsonBuilder().create();
				JsonHandler js = new JsonHandler();

				listType = new TypeToken<List<PAction>>(){}.getType();
				s = js.receiveJson(mUrl+"/PeiMaps/webresources/androidGetActionList?id="+pt.getIdPatient());
				List<PAction> allPA = (List<PAction>) gs.fromJson(s, listType);
				System.out.println(s);
				for(int i=0; i<allPA.size(); i++)
					msh.addAction(allPA.get(i), getBaseContext());
				
				listType = new TypeToken<List<BloodPressureMeasure>>(){}.getType();
				s = js.receiveJson(mUrl+"/PeiMaps/webresources/getBPMeasure?id="+pt.getIdPatient());
				List<BloodPressureMeasure> allBP = (List<BloodPressureMeasure>) gs.fromJson(s, listType);

				for(int i=0; i<allBP.size(); i++)
					msh.addPressao(allBP.get(i), getBaseContext(), 1);

				listType = new TypeToken<List<TemperatureMeasure>>(){}.getType();
				s = js.receiveJson(mUrl+"/PeiMaps/webresources/getTemperatureMeasure?id="+pt.getIdPatient());
				List<TemperatureMeasure> allTM = (List<TemperatureMeasure>) gs.fromJson(s, listType);

				for(int i=0; i<allTM.size(); i++)
					msh.addTemp(allTM.get(i), getBaseContext(), 1);

				listType = new TypeToken<List<DrugTakeRegister>>(){}.getType();
				s = js.receiveJson(mUrl+"/PeiMaps/webresources/getListDrugTakeRegister?id="+pt.getIdPatient());
				List<DrugTakeRegister> allDTR = (List<DrugTakeRegister>) gs.fromJson(s, listType);

				for(int i=0; i<allDTR.size(); i++){
					msh.addDrugTake(allDTR.get(i), getBaseContext(), 1);
				}

				return true;
			}
			return false;
//			return true;
			
			
//			try {
//				// Simulate network access.
//				Thread.sleep(2000);
//			} catch (InterruptedException e) {
//				return false;
//			}
//
//			for (String credential : DUMMY_CREDENTIALS) {
//				String[] pieces = credential.split(":");
//				if (pieces[0].equals(mEmail)) {
//					// Account exists, return true if the password matches.
//					return pieces[1].equals(mPassword);
//				}
//			}
//
//			// TODO: register the new account here.
//			return true;
		}

		@Override
		protected void onPostExecute(final Boolean success) {
			mAuthTask = null;
			showProgress(false);
			
			//Se a password estiver correcta lanÃ§a a actividade "MainActivity", se nÃ£o pede de novo a password.
			if (success) {
				Intent intent = new Intent(getApplicationContext(), MainActivity.class);
				intent.setAction("AFTER_LOG_IN");
			    startActivity(intent);
				finish();
			} else {
				mPasswordView
						.setError(getString(R.string.error_incorrect_password));
				mPasswordView.requestFocus();
			}
		}

		@Override
		protected void onCancelled() {
			mAuthTask = null;
			showProgress(false);
		}
	}
}
