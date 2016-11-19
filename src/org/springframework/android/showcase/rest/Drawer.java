package org.springframework.android.showcase.rest;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import org.springframework.android.showcase.AbstractMenuActivity;
import org.springframework.android.showcase.R;
import org.springframework.http.MediaType;

/**
 * Created by PK on 2016-11-19.
 */

public class Drawer extends Activity{

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
      //  setContentView(R.layout.get_drawer_list);




    }

    @Override
    public void onStart() {
        super.onStart();
        setContentView(R.layout.new_drawer_properties);

        Intent intent = getIntent();
        String value = intent.getStringExtra("user");
        setTitle(value + " welcome @ Warehouse/Add Drawer");


        // when this activity starts, initiate an asynchronous HTTP GET request
        //      new HttpGetJsonActivity.DownloadStatesTask().execute();
    }
}
