/*
 * Copyright 2010-2014 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.springframework.android.showcase;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.springframework.android.showcase.rest.Coordinates;
import org.springframework.android.showcase.rest.Get_drawer_list;
import org.springframework.android.showcase.rest.HttpGetActivity;
import org.springframework.android.showcase.rest.HttpGetJsonActivity;
import org.springframework.android.showcase.rest.HttpPostJsonXmlActivity;
import org.springframework.android.showcase.rest.Login;
import org.springframework.android.showcase.rest.Message2;
import org.springframework.http.HttpAuthentication;
import org.springframework.http.HttpBasicAuthentication;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.http.converter.xml.SimpleXmlHttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

import static java.security.AccessController.getContext;

/**
 * @author Roy Clarkson
 */
public abstract class AbstractMenuActivity extends Activity {

	private ProgressDialog progressDialog;
private boolean run1;

	protected static final String TAG = HttpPostJsonXmlActivity.class.getSimpleName();
	// ***************************************
	// Activity methods
	// ***************************************
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.menu_activity_layout);
		run1=false;


		final TextView textViewDescription = (TextView) this.findViewById(R.id.text_view_description);
		textViewDescription.setText(getDescription());

		final CheckBox LoopFunction = (CheckBox) this.findViewById(R.id.cBoxPowt);
		LoopFunction.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

													 @Override
													 public void onCheckedChanged(CompoundButton buttonView,boolean isChecked) {

														 if(isChecked && !run1)
														 {
															 run1=true;
															 new PostMessageTaskSelfReturn().execute(MediaType.APPLICATION_JSON);
														 }
														 else
														 {
															 run1=false;
														 }
													 }
												 }
		);


		final Button ButtonLogin = (Button) this.findViewById(R.id.bLoggin);
		ButtonLogin.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				new PostMessageTaskLoggin().execute(MediaType.APPLICATION_JSON);


			}
		});



		final Button ButtonSigIn = (Button) this.findViewById(R.id.bNewAccount);
		ButtonSigIn.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				new PostMessageTask().execute(MediaType.APPLICATION_JSON);
			}
		});

		final ListView listViewMenu = (ListView) this.findViewById(R.id.list_view_menu_items);
		listViewMenu.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, getMenuItems()));
		listViewMenu.setOnItemClickListener(getMenuOnItemClickListener());
	}

	// ***************************************
	// Abstract methods
	// ***************************************
	protected abstract String getDescription();

	protected abstract String[] getMenuItems();

	protected abstract OnItemClickListener getMenuOnItemClickListener();

	//protected abstract View.OnClickListener getButtonOnClickListner1();



	// ***************************************
	// Private classes  Logowanie do konta
	// ***************************************
	private class PostMessageTaskLoggin extends AsyncTask<MediaType, Void, Login> {

		private Message2 message;

		@Override
		protected void onPreExecute() {
			showLoadingProgressDialog();

			// build the message object
			EditText editText = (EditText) findViewById(R.id.eLogin);

			message = new Message2();



			editText = (EditText) findViewById(R.id.eLogin);
			message.setUsername(editText.getText().toString());

			editText = (EditText) findViewById(R.id.ePass);
			message.setPass(editText.getText().toString());
			//("proba");
		}

		@Override
		protected Login doInBackground(MediaType... params) {
			try {
				if (params.length <= 0) {
					return null;
				}

				MediaType mediaType = params[0];

				// The URL for making the POST request
				//final String url =  "http://warehouse321.pythonanywhere.com/api/token";
				final String url =  "http://192.168.1.122:5000/api/users/token";

				HttpAuthentication authHeader = new HttpBasicAuthentication(message.getUsername(), message.getPass());//haslo 13371337
				HttpHeaders requestHeaders = new HttpHeaders();
				requestHeaders.setAuthorization(authHeader);
				// Sending a JSON or XML object i.e. "application/json" or "application/xml"
				//requestHeaders.setContentType(mediaType);
				List<MediaType> acceptableMediaTypes = new ArrayList<MediaType>();
				acceptableMediaTypes.add(MediaType.APPLICATION_JSON);

				requestHeaders.setAccept(acceptableMediaTypes);

				// Populate the Message object to serialize and headers in an
				// HttpEntity object to use for the request


				//String info = "{ \"username\": \""+message.getUsername()+"\",  \"password\": \""+message.getPass()+"\"}";

				HttpEntity<?> requestEntity = new HttpEntity<Object>( requestHeaders);

				// Create a new RestTemplate instance
				RestTemplate restTemplate = new RestTemplate();
				restTemplate.getMessageConverters().add(new StringHttpMessageConverter());
				if (mediaType == MediaType.APPLICATION_JSON) {
					restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());
				} else if (mediaType == MediaType.APPLICATION_XML) {
					restTemplate.getMessageConverters().add(new SimpleXmlHttpMessageConverter());
				}





				// Make the network request, posting the message, expecting a String in response from the server
				ResponseEntity<Login> response = restTemplate.exchange(url, HttpMethod.GET, requestEntity,
						Login.class);

				// Return the response body to display to the user
				Login result = response.getBody();
				return result;//response.getBody();
			} catch (Exception e) {
				Log.e(TAG, e.getMessage(), e);
			}

			return null;
		}

		@Override
		protected void onPostExecute(Login result) {
			dismissProgressDialog();
			showResult(result, message.getUsername());
		}

	}
	// ***************************************
	// Private methods
	// ***************************************
	private void showResult(Login result, String login) {
		if (result != null) {
			// display a notification to the user with the response message
			Toast.makeText(this, "Signed as " + login, Toast.LENGTH_LONG).show();

			setTitle("Warehouse Logged as "+ login);


		//	Intent myIntent = new Intent(getApplicationContext(),HttpGetJsonActivity.class);
			Intent myIntent = new Intent(getApplicationContext(),Get_drawer_list.class);
			myIntent.putExtra("user", login); //Optional parameters
			myIntent.putExtra("token", result.getPass());
			startActivity(myIntent);

		} else {
			Toast.makeText(this, "Incorrect password or login!", Toast.LENGTH_LONG).show();
		}
	}

	// ***************************************
	// Private methods
	// ***************************************
	private void showResult(String result) {
		if (result != null) {
			// display a notification to the user with the response message
			Toast.makeText(this, result, Toast.LENGTH_LONG).show();
		} else {
			Toast.makeText(this, "I got null, something happened!", Toast.LENGTH_LONG).show();
		}
	}


	// ***************************************
	// Private classes  dodawanie konta
	// ***************************************
	private class PostMessageTask extends AsyncTask<MediaType, Void, String> {

		private Message2 message;

		@Override
		protected void onPreExecute() {
				showLoadingProgressDialog();

			// build the message object
			EditText editText = (EditText) findViewById(R.id.eLogin);

			message = new Message2();



			editText = (EditText) findViewById(R.id.eLogin);
			message.setUsername(editText.getText().toString());

			editText = (EditText) findViewById(R.id.ePass);
			message.setPass(editText.getText().toString());
			//("proba");
		}

		@Override
		protected String doInBackground(MediaType... params) {
			try {
				if (params.length <= 0) {
					return null;
				}

				MediaType mediaType = params[0];

				// The URL for making the POST request
				final String url =  "http://warehouse321.pythonanywhere.com/api/users/new";


				HttpAuthentication authHeader = new HttpBasicAuthentication("admin", "13371337");
				HttpHeaders requestHeaders = new HttpHeaders();
				requestHeaders.setAuthorization(authHeader);
				// Sending a JSON or XML object i.e. "application/json" or "application/xml"
				requestHeaders.setContentType(mediaType);

				// Populate the Message object to serialize and headers in an
				// HttpEntity object to use for the request


				String info = "{ \"username\": \""+message.getUsername()+"\",  \"password\": \""+message.getPass()+"\"}";
				HttpEntity<String> requestEntity = new HttpEntity<String>(info, requestHeaders);

				// Create a new RestTemplate instance
				RestTemplate restTemplate = new RestTemplate();
				restTemplate.getMessageConverters().add(new StringHttpMessageConverter());
				if (mediaType == MediaType.APPLICATION_JSON) {
					restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());
				} else if (mediaType == MediaType.APPLICATION_XML) {
					restTemplate.getMessageConverters().add(new SimpleXmlHttpMessageConverter());
				}

				// Make the network request, posting the message, expecting a String in response from the server
				ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.POST, requestEntity,
						String.class);

				// Return the response body to display to the user
				return response.getBody();
			} catch (Exception e) {
				Log.e(TAG, e.getMessage(), e);
			}

			return null;
		}

		@Override
		protected void onPostExecute(String result) {
			dismissProgressDialog();
			showResult(result);
		}

	}


	public void showLoadingProgressDialog() {
		this.showProgressDialog("Loading. Please wait...");
	}

	public void showProgressDialog(CharSequence message) {
		if (this.progressDialog == null) {
			this.progressDialog = new ProgressDialog(this);
			this.progressDialog.setIndeterminate(true);
		}

		this.progressDialog.setMessage(message);
		this.progressDialog.show();
	}

	public void dismissProgressDialog() {
		if (this.progressDialog != null ) {//&& !this.destroyed
			this.progressDialog.dismiss();
		}
	}
//WATEK SAMOPOWTARZAJACY SIE


	// ***************************************
	// Private classes  Logowanie do konta
	// ***************************************
	private class PostMessageTaskSelfReturn extends AsyncTask<MediaType, Void, Coordinates> {

		private Message2 message;

		@Override
		protected void onPreExecute() {
			showLoadingProgressDialog();

			// build the message object
			EditText editText = (EditText) findViewById(R.id.eLogin);
		//	CheckBox LoopFunction1 = (CheckBox) this.findViewById(R.id.cBoxPowt);

			message = new Message2();

			editText = (EditText) findViewById(R.id.eLogin);
			message.setUsername(editText.getText().toString());

			editText = (EditText) findViewById(R.id.ePass);
			message.setPass(editText.getText().toString());
			//("proba");
		}

		@Override
		protected Coordinates doInBackground(MediaType... params) {
			try {
				if (params.length <= 0) {
					return null;
				}

				MediaType mediaType = params[0];

				// The URL for making the POST request
				final String url =  "http://warehouse321.pythonanywhere.com/api/device/state";


				HttpAuthentication authHeader = new HttpBasicAuthentication(message.getUsername(), message.getPass());//haslo 13371337
				HttpHeaders requestHeaders = new HttpHeaders();
				requestHeaders.setAuthorization(authHeader);
				// Sending a JSON or XML object i.e. "application/json" or "application/xml"
				//requestHeaders.setContentType(mediaType);
				List<MediaType> acceptableMediaTypes = new ArrayList<MediaType>();
				acceptableMediaTypes.add(MediaType.APPLICATION_JSON);

				requestHeaders.setAccept(acceptableMediaTypes);

				// Populate the Message object to serialize and headers in an
				// HttpEntity object to use for the request


				//String info = "{ \"username\": \""+message.getUsername()+"\",  \"password\": \""+message.getPass()+"\"}";

				HttpEntity<?> requestEntity = new HttpEntity<Object>( requestHeaders);

				// Create a new RestTemplate instance
				RestTemplate restTemplate = new RestTemplate();
				restTemplate.getMessageConverters().add(new StringHttpMessageConverter());
				if (mediaType == MediaType.APPLICATION_JSON) {
					restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());
				} else if (mediaType == MediaType.APPLICATION_XML) {
					restTemplate.getMessageConverters().add(new SimpleXmlHttpMessageConverter());
				}





				// Make the network request, posting the message, expecting a String in response from the server
				ResponseEntity<Coordinates> response = restTemplate.exchange(url, HttpMethod.GET, requestEntity,
						Coordinates.class);

				// Return the response body to display to the user
				Coordinates nowa = response.getBody();
				return nowa;//response.getBody();
			} catch (Exception e) {
				Log.e(TAG, e.getMessage(), e);
			}

			return null;
		}

		@Override
		protected void onPostExecute(Coordinates result) {
			dismissProgressDialog();
			//showResult(result);

			TextView tInfo = (TextView) findViewById(R.id.textInfo);
			if(result != null)
							tInfo.setText("x = "+result.getX() +" y = " +result.getY()+ " z = "+result.getZ());
			else
				tInfo.setText("nie dziala");


			if(run1)
			{
				try {
					Thread.sleep(2500);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}

				new PostMessageTaskSelfReturn().execute(MediaType.APPLICATION_JSON);

			}
		}

	}

}
