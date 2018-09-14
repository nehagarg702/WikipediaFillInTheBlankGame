/**
 * The purpose of this class is to extract the data from wikipedia random page.
 * This class shows the dialog box on screen and in background extract the data from wikipedia page.
 */

package com.example.dell.wikipediafillintheblankgame;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatDelegate;
import android.widget.Toast;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;

public class DownloadWebPageTask extends AsyncTask<String, Void, String> {
    ProgressDialog pdialog = null;
    Activity context;
    String title="";
    int k=0;

    DownloadWebPageTask(Activity c)
    {
        context=c;
    }
    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        pdialog=new ProgressDialog(context);
        pdialog.setMessage("Getting Content......");
        pdialog.setCancelable(false);
        pdialog.setCanceledOnTouchOutside(false);
        if(new UI().isNetworkAvailable(context)){
        new UI().dialog_fullscree(pdialog);}

    }

    @Override
    protected String doInBackground(String... urls) {
        String response="";
        try {
            do {
                response = "";
                Document doc = Jsoup.connect(urls[0]).get();
                Elements contentDiv = doc.getElementsByTag("p");
                title=doc.getElementsByTag("h1").text();
                for (Element paragraph :contentDiv) {
                    response += paragraph.text() + "\n\n";
                }
            } while (response.length() < 2500);
            response = response.replaceAll("\\[.*?\\]", "").replaceAll(" +", " ");
            response.replace("[", "").replace("]","");
        } catch (IOException e) {
            context.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    showNetworkErrorDialog(context,true);
                }
            });
        }
        return response;
    }

    @Override
    protected void onPostExecute(String result) {
       if(pdialog.isShowing())
       {
           pdialog.dismiss();
       }
       Intent i=new Intent(context, FillInTheBlank.class);
           i.putExtra("result",result);
           i.putExtra("title",title);
           if(title.length()>1 && result.length()>2400){
           context.startActivity(i);}
    }

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
                dialog.dismiss();
                DownloadWebPageTask task = new DownloadWebPageTask(context);
                task.execute(new String[] { "http://en.wikipedia.org/wiki/Special:Random" });
            }
        });

        AlertDialog adialog=dialog.create();
        new UI().dialog_fullscree(adialog);
    }
}