package com.example.dell.wikipediafillintheblankgame;

import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.GridLayout;
import android.widget.TextView;

import static android.view.View.TEXT_ALIGNMENT_CENTER;

public class Result extends AppCompatActivity {
    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        new UI().fullScreen(getWindow().getDecorView());
        super.onWindowFocusChanged(hasFocus);
    }

    String useroptions[];
    String answers[];
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        new UI().fullScreen(getWindow().getDecorView());
        useroptions=getIntent().getStringArrayExtra("useroptions");
        answers=getIntent().getStringArrayExtra("answers");
        GridLayout gridLayout = (GridLayout)findViewById(R.id.result_layout);
        for(int i=0; i<11; i++){
            for(int j=0; j<3; j++) {
                if (i == 0) {
                    if (j == 0)
                        gridLayout.addView(getGridView(Result.this,"No.   ",i));
                    if (j == 1)
                        gridLayout.addView(getGridView(Result.this,"Your Answer",i));
                    if (j == 2)
                        gridLayout.addView(getGridView(Result.this,"    Correct Answer",i));
                }
                else
                {
                    if(j == 0)
                        gridLayout.addView(getGridView(Result.this, " "+i+"     ", i));
                    if(j == 1) {
                        assert useroptions != null;
                        gridLayout.addView(getGridView(Result.this, useroptions[i-1], i));
                    }
                    if(j == gridLayout.getColumnCount() - 1) {
                        assert answers != null;
                        gridLayout.addView(getGridView(Result.this, "     "+answers[i-1], i));
                    }
                }
            }
        }
    }

    @Override
    protected void onStart() {
        new UI().fullScreen(getWindow().getDecorView());
        super.onStart();
    }

    public static TextView getGridView(Context context, String text, int i){
        TextView textView = new TextView(context);
        textView.setTextAppearance(context, android.R.style.TextAppearance_Medium);
        textView.setText(text);
        textView.setGravity(TEXT_ALIGNMENT_CENTER);
        if(i == 0)
            textView.setTextColor(context.getResources().getColor(R.color.colorAccent));
        else
            textView.setTextColor(context.getResources().getColor(R.color.colorPrimary));
        return textView;
    }

}

