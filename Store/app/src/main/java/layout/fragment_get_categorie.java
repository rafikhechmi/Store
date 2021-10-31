package layout;


import android.app.DownloadManager;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
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
import android.widget.GridView;
import android.widget.ProgressBar;

import com.example.rafik.store.GridItem;
import com.example.rafik.store.GridViewAdapter;
import com.example.rafik.store.NoInternetActivity;
import com.example.rafik.store.R;
import com.example.rafik.store.parse_cat;
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
 * A simple {@link Fragment} subclass.
 */
public class fragment_get_categorie extends Fragment {

    //grid view
    private GridView mGridView=null;
    private ProgressBar mProgressBar;
    Button button;
    DownloadManager downloadManager;
    private GridViewAdapter mGridAdapter;
    private ArrayList<GridItem> mGridData;
    //end gridview




    public fragment_get_categorie() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_get_categorie, container, false);
        if (isConnected(getActivity())==true) {
            getActivity().setTitle("Categories");

            mGridView = (GridView) view.findViewById(R.id.gridView);
            //Grid View
            //Initialize with empty data
            mGridData = new ArrayList<>();
            mGridAdapter = new GridViewAdapter(getActivity(), R.layout.grid_item, mGridData);
            mGridView.setAdapter(mGridAdapter);


            // on item click method -> go to another fragment
            mGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {


                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {


                    categorie_apps categorie = new categorie_apps();
                    FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                    fragmentTransaction.replace(R.id.content_main, categorie);
                    fragmentTransaction.addToBackStack(null);
                    fragmentTransaction.commit();


                }
            });


            //


            String myurl = "http://192.168.137.139:3000/categories";

            new AsynkTaskcat().execute(myurl);
        }
        else
        {
            Intent i = new Intent (getActivity(),NoInternetActivity.class);
            startActivity(i);
        }
        return view;
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

            GridItem item;


            ArrayList<parse_cat> catgets=new JsonConverter<parse_cat>().toArrayList(newData,parse_cat.class);
            ArrayList<String> categorie = new ArrayList<String>();
            for (parse_cat value : catgets)
            {
                newData=value.cat_name;
                mGridAdapter.setGridData(mGridData);
                item=new GridItem();
                item.setTitle(value.cat_name);
               item.setImage(value.cat_img_url);
                mGridData.add(item);


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
    public boolean isConnected(Context context) {

        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netinfo = cm.getActiveNetworkInfo();

        if (netinfo != null && netinfo.isConnectedOrConnecting()) {
            android.net.NetworkInfo wifi = cm.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
            android.net.NetworkInfo mobile = cm.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);

            if((mobile != null && mobile.isConnectedOrConnecting()) || (wifi != null && wifi.isConnectedOrConnecting())) return true;
            else return false;
        } else
            return false;
    }

}


