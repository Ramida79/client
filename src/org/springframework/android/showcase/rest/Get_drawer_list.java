package org.springframework.android.showcase.rest;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.http.converter.xml.SimpleXmlHttpMessageConverter;
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


    int krok=0;

    TextView tX,tY,tZ;
    TextView tCurrentX,tCurrentY,tCurrentZ;

    ProgressDialog progressDialog;



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

        tCurrentX = (TextView) findViewById(R.id.tvXcurrent);
        tCurrentY = (TextView) findViewById(R.id.tvYcurrent);
        tCurrentZ = (TextView) findViewById(R.id.tvZcurrent);


        final Button BNewDrawer = (Button) this.findViewById(R.id.b_NewDrawer);
        final Button BGetDrawer = (Button) this.findViewById(R.id.b_GetDrawer);
        final Button BPutDrawer = (Button) this.findViewById(R.id.b_PutDrawer);

        BGetDrawer.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                new GetDrawerFromWarehouseNEW().execute();

            }
        });


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


//             new Get_drawer_list.DownloadStatesTask().execute();

             new Get_drawer_list.GetHandleParams().execute();

        //get list of all drawers of logged user
        //new Get_List_Of_Drawers;
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

    private void refreshStatesOfDrawers(List<Drawer_Container> states) {
        if (states == null) {
            return;
        }

        DrawersListAdapter adapter = new DrawersListAdapter(this, states);
        // setListAdapter(adapter);
        listView.setAdapter(adapter);

    }



    private void  refreshParams(HandleParams p)
   {
       tX.setText(String.valueOf(p.getX()));
       tY.setText(String.valueOf(p.getY()));
       tZ.setText(String.valueOf(p.getZ()));

       tCurrentX.setText(String.valueOf(p.getX_current()));
       tCurrentY.setText(String.valueOf(p.getY_current()));
       tCurrentZ.setText(String.valueOf(p.getZ_current()));


   }

    private void rParam(){

        tCurrentZ.setText(String.valueOf(krok++));
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


                /*Random generator = new Random();
                Hparams.setAll(generator.nextInt(50),generator.nextInt(50),generator.nextInt(50),generator.nextInt(50),generator.nextInt(50),generator.nextInt(50));
                Thread.sleep(1000);
*/
                //final String url =  "http://warehouse321.pythonanywhere.com/api/warehouse/state";
                final String url =  "http://192.168.1.122:5000/api/warehouse/state";
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
                ResponseEntity<HandleParams> responseEntity = restTemplate.exchange(url, HttpMethod.GET, requestEntity,
                        HandleParams.class);

                HandleParams Hparams = responseEntity.getBody();



                Thread.sleep(500);

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

    private class Get_List_Of_Drawers extends AsyncTask<Void,Void, List<Drawer_Container>>
    {

        @Override
        protected void onPreExecute() {
            //      showLoadingProgressDialog();
        }

        @Override
        protected List<Drawer_Container> doInBackground(Void... params) {
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
                ResponseEntity<DrawersList> responseEntity = restTemplate.exchange(url, HttpMethod.GET, requestEntity,
                        DrawersList.class);

                DrawersList nowa = responseEntity.getBody();


                return  nowa.getDrawers();
                // convert the array to a list and return it
                //	return Arrays.asList(responseEntity.getBody());
            } catch (Exception e) {
                //        Log.e(TAG, e.getMessage(), e);
            }

            return null;
        }

        @Override
        protected void onPostExecute(List<Drawer_Container> result) {
            //      dismissProgressDialog();
            refreshStatesOfDrawers(result);
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






    ///WSTAWIONE 28012017
    ///POBIERANIE SZUFLADKI


// ***************************************
// Pobieranie szufladki
// ***************************************

    public class GetDrawerFromWarehouseNEW extends AsyncTask<MediaType, Void, String>  {

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
                final String url =  "http://warehouse321.pythonanywhere.com/api/";


                HttpAuthentication authHeader = new HttpBasicAuthentication("admin", "13371337");
                HttpHeaders requestHeaders = new HttpHeaders();
                requestHeaders.setAuthorization(authHeader);
                // Sending a JSON or XML object i.e. "application/json" or "application/xml"
                requestHeaders.setContentType(mediaType);

                // Populate the Message object to serialize and headers in an
                // HttpEntity object to use for the request


                String info = "{ \"id\": \""+message.getUsername()+"\",  \"password\": \""+message.getPass()+"\"}";
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
                Log.e("TAG", e.getMessage(), e);
            }

            return null;
        }

        @Override
        protected void onPostExecute(String result) {
            dismissProgressDialog();
            //showResult(result);  //TU COS POWNO BYC NP ZE WYKONANO
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
        if (this.progressDialog != null) {//&& !this.destroyed
            this.progressDialog.dismiss();
        }

    }



}
