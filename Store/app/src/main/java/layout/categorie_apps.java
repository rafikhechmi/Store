package layout;


import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.rafik.store.R;
import com.example.rafik.store.parse_cat;
import com.example.rafik.store.simplefragmentpageAdapter;
import com.kosalgeek.android.json.JsonConverter;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class categorie_apps extends Fragment {
    View view;
    TabLayout tabLayout;
    private ArrayList<String> Tabdata;
    simplefragmentpageAdapter SF ;
    private final List<String> mFragmentTitleList = new ArrayList<>();
    ViewPager viewPager;

    public categorie_apps() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        view=inflater.inflate(R.layout.fragment_categorie_apps, container, false);
        getActivity().setTitle("Applications");

                //

        Tabdata = new ArrayList<>();

         viewPager= (ViewPager)view.findViewById(R.id.view_pager);
       // viewPager.setAdapter(new simplefragmentpageAdapter(getChildFragmentManager()));

        tabLayout= (TabLayout)view.findViewById(R.id.tab_layout);
        tabLayout.setupWithViewPager(viewPager);

        String myurl = "http://192.168.137.139:3000/categories";

        new AsynkTasktab().execute(myurl);
        //

        return view;

    }
    public class AsynkTasktab extends AsyncTask<String , String , String>
    {

        String newData="" ;
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

            viewPager= (ViewPager)view.findViewById(R.id.view_pager);
            simplefragmentpageAdapter adapter = new simplefragmentpageAdapter(getChildFragmentManager());


            ArrayList<parse_cat> catgets=new JsonConverter<parse_cat>().toArrayList(newData,parse_cat.class);
            for (parse_cat value : catgets)
            {
                newData=value.cat_name;
               adapter.addFragment(newData);

                viewPager.setAdapter(adapter);

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
