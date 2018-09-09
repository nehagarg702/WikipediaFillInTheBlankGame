package com.example.dell.wikipediafillintheblankgame;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;

public class DownloadWebPageTask extends AsyncTask<String, Void, String> {
    ProgressDialog dialog = null;
    Context context;
    String title="";

    DownloadWebPageTask(Context c)
    {
        context=c;
    }
    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        dialog=new ProgressDialog(context);
        dialog.setMessage("Getting Content......");
        dialog.setCancelable(false);
        dialog.setCanceledOnTouchOutside(false);
        new UI().dialog_fullscree(dialog);

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
                int NoOfParagraphs = 0;
                for (Element paragraph :contentDiv) {
                    response += paragraph.text() + "\n\n";
                    NoOfParagraphs++;
                }
            } while (response.length() < 2500);
            response = response.replaceAll("\\[.*?\\]", "").replaceAll(" +", " ");
            response.replace("[", "").replace("]","");
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return response;
    }

    @Override
    protected void onPostExecute(String result) {
        dialog.dismiss();
        Intent intent=new Intent(context,FillInTheBlank.class);
        intent.putExtra("result",result);
        intent.putExtra("title",title);
        context.startActivity(intent);
    }
}