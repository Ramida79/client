package org.springframework.android.showcase.rest;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

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
import java.util.Random;

/**
 * Created by PK on 2016-10-20.
 */

public class Get_drawer_list extends Activity {

    String value = "zero";
    ListView listView ;

    TextView tX,tY,tZ;



    //protected static final String TAG = HttpGetJsonActivity.class.getSimpleName();

    // ***************************************
    // Activity methods
    // ***************************************
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.get_drawer_list);
        setContentView(R.layout.get_drawer_list);


        listView = (ListView) findViewById(R.id.DrawerList);
        tX = (TextView) findViewById(R.id.tvXpos);
        tY = (TextView) findViewById(R.id.tvYpos);
        tZ = (TextView) findViewById(R.id.tvZpos);


        final Button BNewDrawer = (Button) this.findViewById(R.id.b_NewDrawer);

        BNewDrawer.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent myIntent = new Intent(getApplicationContext(),Drawer.class);
                myIntent.putExtra("user", value); //Optional parameters
                //myIntent.putExtra("token", result.getPass());
                startActivity(myIntent);


            }
        });


    }

    @Override
    public void onStart() {
        super.onStart();


        Intent intent = getIntent();
        value = intent.getStringExtra("user");
        setTitle(value + " welcome @ Warehouse");

         // when this activity starts, initiate an asynchronous HTTP GET request
             new Get_drawer_list.DownloadStatesTask().execute();

             new GetHandleParams().execute();

    }



    // ***************************************
    // Private methods
    // ***************************************
    private void refreshStates(List<Users> states) {
        if (states == null) {
            return;
        }

        UsersListAdapter adapter = new UsersListAdapter(this, states);
       // setListAdapter(adapter);
        listView.setAdapter(adapter);

    }
   private void  refreshParams(HandleParams p)
   {
       tX.setText(String.valueOf(p.getX()));
       tY.setText(String.valueOf(p.getY()));
       tZ.setText(String.valueOf(p.getZ()));

   }


    // ***************************************
    // Private classes
    // ***************************************
    private class GetHandleParams extends AsyncTask<Void, Void, HandleParams> {
        @Override
        protected void onPreExecute() {
            //      showLoadingProgressDialog();
        }

        @Override
        protected HandleParams doInBackground(Void... params) {
            try {
                Random generator = new Random();

                    HandleParams Hparams = new HandleParams();
                Hparams.setAll(generator.nextInt(50),generator.nextInt(50),generator.nextInt(50),generator.nextInt(50),generator.nextInt(50),generator.nextInt(50));

                Thread.sleep(1000);

                return Hparams;
                }
            catch (Exception e) {
                //        Log.e(TAG, e.getMessage(), e);
            }
                return null;

            }

        @Override
        protected void onPostExecute(HandleParams result) {
            //      dismissProgressDialog();
            if(result!=null)           refreshParams( result);


            new GetHandleParams().execute();
        }
    }

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
