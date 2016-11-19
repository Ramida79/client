package org.springframework.android.showcase.rest;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;

import org.springframework.android.showcase.AbstractAsyncListActivity;
import org.springframework.android.showcase.R;
import org.springframework.http.HttpAuthentication;
import org.springframework.http.HttpBasicAuthentication;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by PK on 2016-10-20.
 */

public class Get_drawer_list extends Activity {

    //protected static final String TAG = HttpGetJsonActivity.class.getSimpleName();

    // ***************************************
    // Activity methods
    // ***************************************
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.get_drawer_list);

    }

    @Override
    public void onStart() {
        super.onStart();
        setContentView(R.layout.get_drawer_list);

        Intent intent = getIntent();
        String value = intent.getStringExtra("user");
        setTitle(value + " welcome @ Warehouse");


             // when this activity starts, initiate an asynchronous HTTP GET request
        //      new HttpGetJsonActivity.DownloadStatesTask().execute();
    }



    // ***************************************
    // Private methods
    // ***************************************
    private void refreshStates(List<Users> states) {
        if (states == null) {
            return;
        }

       // UsersListAdapter adapter = new UsersListAdapter(this, states);
        //      setListAdapter(adapter);
    }

    // ***************************************
    // Private classes
    // ***************************************
    private class DownloadStatesTask extends AsyncTask<Void, Void, List<Users>> {

        @Override
        protected void onPreExecute() {
            //      showLoadingProgressDialog();
        }

        @Override
        protected List<Users> doInBackground(Void... params) {
            try {
                // The URL for making the GET request
                final String url =  "http://warehouse321.pythonanywhere.com/api/users/all";

                Intent intent = getIntent();

                HttpAuthentication authHeader = new HttpBasicAuthentication(intent.getStringExtra("user"), "13371337");//zmienic podobnie tak jak z userem
                HttpHeaders requestHeaders = new HttpHeaders();
                requestHeaders.setAuthorization(authHeader);

                // Set the Accept header for "application/json"
                List<MediaType> acceptableMediaTypes = new ArrayList<MediaType>();
                acceptableMediaTypes.add(MediaType.APPLICATION_JSON);
                requestHeaders.setAccept(acceptableMediaTypes);

                // Populate the headers in an HttpEntity object to use for the request
                HttpEntity<?> requestEntity = new HttpEntity<Object>(requestHeaders);

                // Create a new RestTemplate instance
                RestTemplate restTemplate = new RestTemplate();
                restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());

                // Perform the HTTP GET request
                ResponseEntity<UsersList> responseEntity = restTemplate.exchange(url, HttpMethod.GET, requestEntity,
                        UsersList.class);

                UsersList nowa = responseEntity.getBody();


                return  nowa.getUsers();
                // convert the array to a list and return it
                //	return Arrays.asList(responseEntity.getBody());
            } catch (Exception e) {
               //        Log.e(TAG, e.getMessage(), e);
            }

            return null;
        }

        @Override
        protected void onPostExecute(List<Users> result) {
            //      dismissProgressDialog();
            refreshStates(result);
        }

    }

}
