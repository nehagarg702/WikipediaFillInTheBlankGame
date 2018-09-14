/**
 * The Purpose of this Activity is to display the list of the answers selected by the user and list of correct answers in grid layout.
 */
package com.example.dell.wikipediafillintheblankgame;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import static android.view.View.TEXT_ALIGNMENT_CENTER;
import static com.example.dell.wikipediafillintheblankgame.R.mipmap.ic_launcher;

public class Result extends AppCompatActivity {
    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        new UI().fullScreen(getWindow().getDecorView());
        super.onWindowFocusChanged(hasFocus);
    }

    String useroptions[]=new String[10];
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
                        gridLayout.addView(getGridView(Result.this,"",i,j));
                    if (j == 1)
                        gridLayout.addView(getGridView(Result.this,"Your Answer",i,j));
                    if (j == 2)
                        gridLayout.addView(getGridView(Result.this,"Correct Answer",i,j));
                }
                else
                {

                    if(j == 0) {
                        if ((""+useroptions[i - 1]).equals(answers[i - 1]))
                            gridLayout.addView(getGridView1(Result.this, "right", i));
                        else
                            gridLayout.addView(getGridView1(Result.this, "wrong", i));
                    }
                    if(j == 1) {
                        assert useroptions != null;
                        gridLayout.addView(getGridView(Result.this, useroptions[i-1], i,j));
                    }
                    if(j == gridLayout.getColumnCount() - 1) {
                        assert answers != null;
                        gridLayout.addView(getGridView(Result.this, answers[i-1], i,j));
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

    public static TextView getGridView(Context context, String text, int i,int j){
        TextView textView = new TextView(context);
        textView.setTextAppearance(context, android.R.style.TextAppearance_Medium);
        textView.setText(text);
        textView.setGravity(TEXT_ALIGNMENT_CENTER);
        LinearLayout.LayoutParams parms;
        if(j==0)
            parms=new LinearLayout.LayoutParams(180,90);
        else
            parms=new LinearLayout.LayoutParams(400,90);
        if(i == 0)
            textView.setTextColor(context.getResources().getColor(R.color.colorAccent));
        else
            textView.setTextColor(context.getResources().getColor(R.color.colorPrimary));
        textView.setLayoutParams(parms);

        return textView;
    }

    public static ImageView getGridView1(Context context, String text, int i){
        ImageView image=new ImageView(context);
        int width = 80;
        int height = 90;
        LinearLayout.LayoutParams parms = new LinearLayout.LayoutParams(width,height);
        image.setLayoutParams(parms);
        if(text.equals("right"))
            image.setImageResource(R.drawable.right);
        else
        image.setImageResource(android.R.drawable.ic_delete);
        return image;
    }

}

