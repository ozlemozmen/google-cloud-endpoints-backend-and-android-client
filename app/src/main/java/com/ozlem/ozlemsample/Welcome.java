package com.ozlem.ozlemsample;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;


public class Welcome extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);

        Intent intent = getIntent();

        String name = intent.getStringExtra("name");
        //String password = intent.getStringExtra("password");

        TextView tv = (TextView) findViewById(R.id.tv);
        tv.setText(name);
    }



}
