package com.example.karim.store;

import android.app.DownloadManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.kosalgeek.android.json.JsonConverter;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

import layout.AppInfo;


/**
 * Created by rafik on 3/21/2017.
 */
public class fragmentDemo extends Fragment {
    //grid view
    private ListView mGridView;
    private ProgressBar mProgressBar;
    DownloadManager downloadManager;
    Button button;

    private ListViewAdapter mGridAdapter;
    private ArrayList<ListItem> mGridData;
    //end gridview



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)  {
        // Inflate the layout for this fragment
       View  view=inflater.inflate(R.layout.fragmentdemo, container, false);

        mGridView = (ListView) view.findViewById(R.id.listview);
        //Grid View
        //Initialize with empty data
        mGridData = new ArrayList<>();
        mGridAdapter = new ListViewAdapter(getActivity(), R.layout.list_item, mGridData);
        mGridView.setAdapter(mGridAdapter);
        button = (Button)view.findViewById(R.id.button3);
        Bundle bundle=getArguments();
        if(bundle != null) {
            String test = bundle.getString("position");
            String myurl = "http://192.168.137.139:3000/"+test;

            new AsynkTaskapps().execute(myurl);
        }


        // end

        mGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {


            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                ListItem item = mGridData.get(i);
                Toast.makeText(getActivity(),item.getApp_id(),Toast.LENGTH_LONG).show();
                Bundle b2 = new Bundle();
                b2.putString("app_id",item.getApp_id());
                Toast.makeText(getActivity(),item.getApp_id(),Toast.LENGTH_LONG).show();

                Fragment fragment = new AppInfo();
                fragment.setArguments(b2);

                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.content_main, fragment);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();




            }
        });
        return view;
    }



    public class AsynkTaskapps extends AsyncTask<String , String , String>
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

            ListItem item;

            if (newData.contains("app_id"))
            {
                ArrayList<app_get> catgets=new JsonConverter<app_get>().toArrayList(newData,app_get.class);
                ArrayList<String> categorie = new ArrayList<String>();
                for (app_get value : catgets)
                {
                    newData=value.app_name;
                    mGridAdapter.setGridData(mGridData);
                    item=new ListItem();
                    item.setTitle(value.app_name);
                    item.setImage(value.app_img_url);
                    item.setApp_id(value.app_id);
                    mGridData.add(item);


                }

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


