/**
 * This activity is the first activity appear on the screen. After 1.5 second start game button is shown. by clicking on ths button
 * user can start the game.
 */

package com.example.dell.wikipediafillintheblankgame;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;

public class LaunchingApp extends AppCompatActivity {

    Button start;
    Intent intent;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_launching_app);
        start=(Button)findViewById(R.id.button);
        start.setVisibility(View.INVISIBLE);
        new UI().fullScreen(getWindow().getDecorView());
        /**
         * The purpose of this is to hide the button for 1.5 second and show the button after 1.5 seconds.
         */
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                // This method will be executed once the timer is over
                start.setVisibility(View.VISIBLE);
            }
        },1500);
        start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new UI().showdata(LaunchingApp.this);
            }
        });
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        new UI().fullScreen(getWindow().getDecorView());
        super.onWindowFocusChanged(hasFocus);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_launching_app, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
