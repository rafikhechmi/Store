package com.example.rafik.store;
import com.google.gson.annotations.SerializedName;

/**
 * Created by rafik on 3/30/2017.
 */

public class parse_cat {
    @SerializedName("_id")
    public String _id;

    @SerializedName("cat_id")
    public String cat_id;

    @SerializedName("cat_name")
    public String cat_name;
    @SerializedName("cat_img_url")
    public String cat_img_url;
}
