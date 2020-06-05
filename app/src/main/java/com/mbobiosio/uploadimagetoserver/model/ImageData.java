package com.mbobiosio.uploadimagetoserver.model;

import com.google.gson.annotations.SerializedName;

/**
 * UploadImageToServer-Base64
 * Created by Mbuodile Obiosio on Jun 05, 2020
 * https://twitter.com/cazewonder
 * Nigeria.
 */
public class ImageData {

    @SerializedName("title")
    private String title;


    @SerializedName("image")
    private String image;

    @SerializedName("response")
    private String response;

    public String getResponse() {
        return response;
    }
}
