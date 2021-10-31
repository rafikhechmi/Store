package layout;


import android.app.DownloadManager;
import android.content.Context;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.rafik.store.GridItem;
import com.example.rafik.store.R;
import com.example.rafik.store.app_get;
import com.example.rafik.store.cmt_get;
import com.example.rafik.store.screen_url;
import com.kosalgeek.android.json.JsonConverter;
import com.squareup.picasso.Picasso;

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
public class AppInfo extends Fragment {
    ImageView img_top, app_img;
    TextView app_name, app_desc;
    Button btn_down;
    DownloadManager downloadManager;
    ListView listView;
    ImageView imageView;
    LinearLayout.LayoutParams layoutParams;
    LinearLayout layout;

    public AppInfo() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_app_info, container, false);
        img_top = (ImageView) view.findViewById(R.id.ivtop);
        app_img = (ImageView) view.findViewById(R.id.ivapp_name);
        app_name = (TextView) view.findViewById(R.id.tvapp_name);
        app_desc = (TextView) view.findViewById(R.id.tvdesc);
        btn_down = (Button) view.findViewById(R.id.btn_download);
        listView = (ListView) view.findViewById(R.id.lvinfo);

        listView.setAdapter(new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, new ArrayList<String>()));
        layout = (LinearLayout) view.findViewById(R.id.image_container);
        layoutParams = new LinearLayout.LayoutParams(500, 500);


        btn_down.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                downloadManager = (DownloadManager) getActivity().getSystemService(Context.DOWNLOAD_SERVICE);

                Uri uri = Uri.parse("http://192.168.137.139/sonic.apk");
                DownloadManager.Request request = new DownloadManager.Request(uri);
                request.addRequestHeader("Content-Type", "application/vnd.android.package-archive");

                request.setMimeType("application/vnd.android.package-archive");

                request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
                Long reference = downloadManager.enqueue(request);
            }
        });


        Bundle b2get = getArguments();
        if (b2get != null) {
            String appid = b2get.getString("app_id");
            String myurl = "http://192.168.137.139:3000/app_by_id/" + appid;
            String screen_url = "http://192.168.137.139:3000/screenshots/" + appid;
            String cmt_url="http://192.168.137.139:3000/comments/"+appid;

            AsynkTaskcat comments = new AsynkTaskcat();
            comments.execute(cmt_url);

            AsynkTaskcat screen_get = new AsynkTaskcat();
            screen_get.execute(screen_url);

            AsynkTaskcat appinfo = new AsynkTaskcat();
            appinfo.execute(myurl);





        } else {
            Toast.makeText(getActivity(), "bundle test  fail ", Toast.LENGTH_LONG).show();


        }




        return view;
    }

    public class AsynkTaskcat extends AsyncTask<String, String, String> {

        String newData;

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
            ArrayAdapter<String> adapter;
            GridItem item;
            if (newData.contains("app_id")) {
                ArrayList<app_get> apps = new JsonConverter<app_get>().toArrayList(newData, app_get.class);
                for (app_get value : apps) {
                    app_name.setText(value.app_name);
                    Picasso.with(getActivity()).load(value.app_img_url).into(app_img);
                    app_desc.setText(value.app_desc);


                }


            }


            if (newData.contains("screen_url"))
            {

//                adapter = (ArrayAdapter<String>) listView.getAdapter();
                ArrayList<screen_url> catgets = new JsonConverter<screen_url>().toArrayList(newData, screen_url.class);
                for (screen_url value : catgets) {
//                    adapter.add(value.screen_url);

                    layoutParams.setMargins(5, 5, 5, 5);
                    layoutParams.gravity = Gravity.CENTER;

                    imageView = new ImageView(getActivity());
                    //   imageView.setImageResource(R.drawable.logo);


                    Picasso.with(getActivity()).load(value.screen_url).into(imageView);

                    imageView.setLayoutParams(layoutParams);

                    layout.addView(imageView);
//
//
               }
            }

                if (newData.contains("cmt"))
                {
                    ArrayList<cmt_get> catgets = new JsonConverter<cmt_get>().toArrayList(newData, cmt_get.class);

                    adapter = (ArrayAdapter<String>) listView.getAdapter();


                    for (cmt_get value : catgets)
                    {
                    adapter.add(value.cmt);





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
