package com.example.rafik.store;

import com.google.gson.annotations.SerializedName;



public class app_get {

    @SerializedName("_id")
    public String _id;

    @SerializedName("cat_id")
    public String cat_id;

    @SerializedName("app_id")
    public String app_id;

    @SerializedName("app_name")
    public String app_name;

    @SerializedName("app_img_url")
    public String app_img_url;

    @SerializedName("app_apk_url")
    public String app_apk_url;

    @SerializedName("app_desc")
    public String app_desc;

    @SerializedName("app_devel")
    public String app_devel;

    @SerializedName("app_version")
    public String app_version;
}
