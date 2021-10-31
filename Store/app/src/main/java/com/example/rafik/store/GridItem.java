package com.example.karim.store;

import android.content.Context;
import android.widget.ImageView;

/**
 * Created by karim on 3/22/2017.
 */

public class GridItem {

    private String image;
    private String title;
    ImageView imageView;
    Context context;








    public GridItem() {
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



    }