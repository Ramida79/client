package org.springframework.android.showcase.rest;


import org.springframework.android.showcase.AbstractMenuActivity;
import org.springframework.android.showcase.R;


import android.app.Activity;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;

import org.springframework.android.showcase.R;
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


/**
 * Created by PK on 2017-01-28.
 */


// ***************************************
// Pobieranie szufladki
// ***************************************

public class GetDrawerFromWarehouse extends Activity {


}

