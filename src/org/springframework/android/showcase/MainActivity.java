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

import org.springframework.android.showcase.rest.HttpGetActivity;
import org.springframework.android.showcase.rest.HttpPostActivity;
import org.springframework.android.showcase.rest.HttpPostJsonXmlActivity;
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

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;

import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.app.ProgressDialog;

/**
 * @author Roy Clarkson
 * @author Pierre-Yves Ricau
 */
public class MainActivity extends AbstractMenuActivity {   //było AbstractMenuActivity

	// ***************************************
	// AbstractMenuActivity methods
	// ***************************************
	@Override
	protected String getDescription() {
		return getResources().getString(R.string.main_menu);
	}

	@Override
	protected String[] getMenuItems() {
		return getResources().getStringArray(R.array.main_menu_items);
	}

/*
	@Override
	protected OnClickListener getButtonOnClickListner1() {
		//new HttpPostJsonXmlActivity.PostMessageTask().execute(MediaType.APPLICATION_JSON);

		return null;
	}

//final Button buttonJson = (Button) findViewById(R.id.bNewAccount);

	/*buttonJson.setOnClickListener(new View.OnClickListener() {
		public void onClick(View v) {
			new HttpPostJsonXmlActivity.PostMessageTask().execute(MediaType.APPLICATION_JSON);
		}
	});*/

	@Override
	protected OnItemClickListener getMenuOnItemClickListener() {
		return new OnItemClickListener() {
			public void onItemClick(AdapterView<?> parentView, View childView, int position, long id) {
				Class<?> cls = null;
				switch (position) {
				case 0:
					cls = HttpGetActivity.class;
					break;
				case 1:
					cls = HttpPostActivity.class;
					break;
				default:
					break;
				}
				startActivity(new Intent(parentView.getContext(), cls));
			}
		};
	}



}
