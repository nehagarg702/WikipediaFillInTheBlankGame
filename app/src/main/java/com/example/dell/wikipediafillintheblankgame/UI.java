/*
This class contains some functions which are used for good UI of the activity
 */

package com.example.dell.wikipediafillintheblankgame;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v7.app.AppCompatDelegate;
import android.view.View;
import android.view.WindowManager;

public class UI {

    /**
     * The purpose of this function is to show the full screen acivity.
     * @param decorView
     */
    public void fullScreen(View decorView)
    {
        decorView.setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);
    }

    /**
     * The purpose of this function is to hide the navigation and side bar when dialog pop up on the screen
     * @param dialog
     */
    public void dialog_fullscree(Dialog dialog)
    {
        dialog.setCanceledOnTouchOutside(false);
        dialog.getWindow().setFlags(WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE, WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE);
        dialog.show();
        fullScreen(dialog.getWindow().getDecorView());
        dialog.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE);

    }

    /**
     * The purpose of this function is to display the alert dialog when network is not available otherwise fetch the data from wikipedia random page
     * @param context
     */
    public void showdata(Activity context)
    {
        if(!isNetworkAvailable(context))
            showNetworkErrorDialog(context,true);
        else
        {
            DownloadWebPageTask task = new DownloadWebPageTask(context);
            task.execute(new String[] { "http://en.wikipedia.org/wiki/Special:Random" });
        }
    }


    /**
     * The purpose of this function is to check the network connectivity. return true if internet is working otherwise return false.
     * For this firstly we have to add access network state permission in android manifest file.
     * @param context
     * @return
     */
    public boolean isNetworkAvailable(Activity context) {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

    /**
     * The purpose of this function is to show the network error dialog. This dialog is not cancelable if we click outside the dialog
     * On click of retry it again diaplay the dialog is internet is not available and on click of ok close the activity.
     * @param context
     * @param shouldEndActivity
     */
    public void showNetworkErrorDialog(final Activity context, final boolean shouldEndActivity) {
        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true);
        AlertDialog.Builder dialog= new AlertDialog.Builder(context);
        dialog.setTitle(R.string.error_1_no_connection);
        dialog.setMessage(R.string.error_1_no_connection_desc);
        dialog.setCancelable(false);
        dialog.setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                dialog.dismiss();
                if(shouldEndActivity) {
                    context.finish();
                }
            }
        });
        dialog.setNegativeButton("Retry", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                showdata(context);
            }
        });

        AlertDialog adialog=dialog.create();
        dialog_fullscree(adialog);
    }
}
