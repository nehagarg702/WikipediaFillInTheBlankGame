/**
 * The purpose of this activity is to display the wikipedia rand page data with 10 blanks. When user click on that blanks, a dialog box
 * appears which contain the list of 10 words and user has to select one word from that list. This activity also contain a floating
 * which move when we move it  and on click go to next activity which show the score.
 */
package com.example.dell.wikipediafillintheblankgame;

import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.Spannable;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.view.MotionEvent;
import android.view.View;
import android.widget.TextView;

public class FillInTheBlank extends AppCompatActivity {

    TextView tv,tv1 ;
    String result;
    TextParser.GameOptions gameOptions = null;
    String[] useroptions = new String[10];
    private final String BLANKS = "_______";
    UI ui=new UI();
    Intent intent;
    Database data;
    SQLiteDatabase db;
    private float downRawX, downRawY;
    private float dX, dY;
    FloatingActionButton fab;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fill_in_the_blank);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        data=new Database(this, "MyDB.db", null, 1);
        ui.fullScreen(getWindow().getDecorView());
        fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnTouchListener(new View.OnTouchListener() {
            @Override
            /**
             * The purpose of this function to to slide the button when users try to slide the button and go to next activity when button is clicked.
             */
            public boolean onTouch(View view, MotionEvent motionEvent) {
                int action = motionEvent.getAction();
                if (action == MotionEvent.ACTION_DOWN) {

                    downRawX = motionEvent.getRawX();
                    downRawY = motionEvent.getRawY();
                    dX = view.getX() - downRawX;
                    dY = view.getY() - downRawY;

                    return true; // Consumed

                }
                else if (action == MotionEvent.ACTION_MOVE) {

                    int viewWidth = view.getWidth();
                    int viewHeight = view.getHeight();

                    View viewParent = (View)view.getParent();
                    int parentWidth = viewParent.getWidth();
                    int parentHeight = viewParent.getHeight();

                    float newX = motionEvent.getRawX() + dX;
                    newX = Math.max(0, newX); // Don't allow the FAB past the left hand side of the parent
                    newX = Math.min(parentWidth - viewWidth, newX); // Don't allow the FAB past the right hand side of the parent

                    float newY = motionEvent.getRawY() + dY;
                    newY = Math.max(0, newY); // Don't allow the FAB past the top of the parent
                    newY = Math.min(parentHeight - viewHeight, newY); // Don't allow the FAB past the bottom of the parent

                    view.animate()
                            .x(newX)
                            .y(newY)
                            .setDuration(0)
                            .start();

                    return true; // Consumed

                }
                else if (action == MotionEvent.ACTION_UP) {

                    float upRawX = motionEvent.getRawX();
                    float upRawY = motionEvent.getRawY();

                    float upDX = upRawX - downRawX;
                    float upDY = upRawY - downRawY;

                    if (Math.abs(upDX) < 10 && Math.abs(upDY) < 10) { // A click
                        performClick();
                        return false;
                    } else { // A drag
                        return true; // Consumed
                    }
                }
                    return false;
            }
        });
        result = getIntent().getStringExtra("result");
        tv = (TextView) findViewById(R.id.textView);
        tv1 = (TextView) findViewById(R.id.textView4);
        tv1.setText(getIntent().getStringExtra("title"));
        tv.setMovementMethod(LinkMovementMethod.getInstance());
        gameOptions = TextParser.getGameOptions(result);
        tv.setText(gameOptions.content, TextView.BufferType.EDITABLE);
        Editable spans = (Editable) tv.getText();
        for (int i = 0; i < 10; i++) {
            int start = gameOptions.starts[i];
            int end = gameOptions.ends[i];
            spans.replace(start, end, BLANKS);
            ClickableSpan clickSpan = getClickableSpan(i, start, start
                    + BLANKS.length(), spans);
            spans.setSpan(clickSpan, start, start + BLANKS.length(),
                    Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        }
    }

    /**
     * The purpose of this function to calculate the score and pass the score along with control with other necessary data to the next Score activity
     */
    public void performClick()
    {
                int score=0;
                for (int i = 0; i < 10; i++) {
                    if (gameOptions.answers[i].equals(useroptions[i]))
                    {
                        score++;
                    }
                }
                db=data.getWritableDatabase();
                ContentValues cv=new ContentValues();
                cv.put("score", score);
                db.insert("SaveData", null, cv);
                intent=new Intent(FillInTheBlank.this,Score.class);
                intent.putExtra("score",score);
                intent.putExtra("useroptions",useroptions);
                intent.putExtra("answers",gameOptions.answers);
                finish();
                startActivity(intent);
    }

    @Override
    protected void onStart() {
        new UI().fullScreen(getWindow().getDecorView());
        super.onStart();
    }

    /**
     * The purpose of this function is to display the dialog when a blank word is clicked.
     * on selection of any word from list it is added into the list of selected answer words.
     * @param index
     * @param pstart
     * @param pend
     * @param spans
     * @return
     */
    private ClickableSpan getClickableSpan(final int index, final int pstart,
                                           final int pend, final Editable spans) {
        return new ClickableSpan() {
            int start = pstart;
            int end = pend;

            @Override
            public void onClick(View widget) {
                AlertDialog.Builder builder = new AlertDialog.Builder(
                        FillInTheBlank.this);
                builder.setTitle("Pick a choice").setItems(gameOptions.options,
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog,
                                                int which) {
                                String option = gameOptions.options[which];
                                useroptions[index] = option;
                                spans.replace(start, end, option);
                                end = start + option.length();
                                spans.setSpan(this, start, end,
                                        Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                            }
                        });
                ui.fullScreen(getWindow().getDecorView());
                AlertDialog dialog=builder.create();
                new UI().dialog_fullscree(dialog);
            }
        };
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        new UI().fullScreen(getWindow().getDecorView());
        super.onWindowFocusChanged(hasFocus);
    }

}
