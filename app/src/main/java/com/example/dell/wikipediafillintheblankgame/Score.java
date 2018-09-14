/**
 * The purpose of this activity is to display the score of the user and display the two buttons Replay and Result.
 * This activity also show the dialog box which tell that your score is highest or equal to highest or lower than highest.
 * On the press of Replay button it again start the game with another wikipedia Random page.
 * On press of Result button it goes to Result activity which show the result.
 */
package com.example.dell.wikipediafillintheblankgame;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class Score extends AppCompatActivity {

    TextView tv;
    int score;
    int dbscore;
    String useroptions[];
    String answers[];
    Button b1,b2;
    Database data;
    SQLiteDatabase db;

    /**
     * The puspose of this function is to get back the activity in full screen idf dialog of another application appears in between.
     * @param hasFocus
     */
    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        new UI().fullScreen(getWindow().getDecorView());
        super.onWindowFocusChanged(hasFocus);
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_score);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        data = new Database(this, "MyDB.db", null, 1);
        db = data.getReadableDatabase();
        String columns[] = new String[]{"score"};
        Cursor c = db.rawQuery("Select max(score) from SaveData",null);
        while(c.moveToNext())
            dbscore=c.getInt(0);
        new UI().fullScreen(getWindow().getDecorView());
        useroptions=getIntent().getStringArrayExtra("useroptions");
        answers=getIntent().getStringArrayExtra("answers");
        tv=(TextView)findViewById(R.id.textView2);
        score=getIntent().getIntExtra("score",0);
        tv.setText(String.valueOf(score)+" ");
        dialog();
        b1=(Button)findViewById(R.id.btn_results);
        b2=(Button)findViewById(R.id.btn_play_again);
        b1.setOnClickListener(new View.OnClickListener() {

            /**
             * On press of this button control is send to the next activity Result.
              * @param v
             */
            @Override
            public void onClick(View v) {
                Intent i=new Intent(Score.this,Result.class);
                i.putExtra("useroptions",useroptions);
                i.putExtra("answers",answers);
                finish();
                startActivity(i);
            }
        });
        b2.setOnClickListener(new View.OnClickListener() {
            /**
             * On press of this button, another random wikipedia page is opened
             * @param v
             */
            @Override
            public void onClick(View v) {
                new UI().showdata(Score.this);

            }
        });

    }

    @Override
    protected void onStart() {
        new UI().fullScreen(getWindow().getDecorView());
        super.onStart();
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        finish();
    }

    /**
     * The purpose of this function is to show the alert dialog which tell that score is highest or equal to highest or lower than
     * highest score.
     */
    public void dialog()
    {
        AlertDialog.Builder dialog= new AlertDialog.Builder(Score.this);
        if(score==dbscore)
            dialog.setTitle("Congratulation").setMessage("Your score is equal to highest score");
        else if(score>dbscore)
            dialog.setTitle("Congratulation").setMessage("Your set the new highest score");
        else
            dialog.setTitle("Good Try").setMessage("Your score is less than highest score: "+ dbscore+". Better luck next time");
        dialog.setCancelable(false)
                .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        dialog.dismiss();
                    }
                });
        AlertDialog adialog=dialog.create();
        new UI().dialog_fullscree(adialog);
    }
}

