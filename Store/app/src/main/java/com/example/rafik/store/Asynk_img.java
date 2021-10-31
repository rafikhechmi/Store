package com.example.karim.store;

import android.app.DownloadManager;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.kosalgeek.android.json.JsonConverter;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

/**
 * Created by karim on 4/13/2017.
 */

public class Asynk_img extends AppCompatActivity{
     LayoutInflater inflater;
        ViewGroup container;
    ImageView img_top,app_img;
    TextView app_name,app_desc;
    Button btn_down;
    DownloadManager downloadManager;

    private static Context context;
    public Asynk_img(Context c) {
        context = c;

    }







public void execute(String url)
{


    new AsynkTaskcat().execute(url);



}
    public class AsynkTaskcat extends AsyncTask<String , String , String>
    {

        String newData ;
        @Override
        protected String doInBackground(String... params) {
            publishProgress("open connection to server ");
            try {
                URL url = new URL(params[0]);
                HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();

                InputStream in = new BufferedInputStream(urlConnection.getInputStream());
                publishProgress("reading data");
                newData = Stream2String(in);

                in.close();


            } catch (Exception e) {
                publishProgress("cannot connect to server");
            }



            return null;

        }
        protected void onPostExecute(String result2) {


            Bundle b = new Bundle();

            ArrayList<parse_cat> catgets=new JsonConverter<parse_cat>().toArrayList(newData,parse_cat.class);
            ArrayList<String> categorie = new ArrayList<String>();
            for (parse_cat value : catgets)
            {







            }



        }

    }



    //Stream2String method
    public String Stream2String(InputStream inputStream) {

        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
        String line;
        String Text = "";


        try {
            while ((line = bufferedReader.readLine()) != null) {
                Text += line;

            }
            inputStream.close();

        } catch (IOException e) {
            e.printStackTrace();
        }

        return Text;
    }

}
