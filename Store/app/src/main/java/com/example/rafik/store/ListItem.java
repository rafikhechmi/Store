package com.example.rafik.store;

import android.content.Context;
import android.widget.ImageView;

/**
 * Created by karim on 4/14/2017.
 */

public class ListItem {

    private String image;
    private String title;
    String app_id;
    ImageView imageView;
    Context context;



    public ListItem() {
        super();
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getApp_id()
    {
        return app_id;
    }
    public void setApp_id(String app_id)
    {
        this.app_id=app_id;
    }



}
