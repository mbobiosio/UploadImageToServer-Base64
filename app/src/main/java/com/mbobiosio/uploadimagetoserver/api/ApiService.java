package com.mbobiosio.uploadimagetoserver.api;

import com.mbobiosio.uploadimagetoserver.model.ImageData;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

/**
 * UploadImageToServer-Base64
 * Created by Mbuodile Obiosio on Jun 05, 2020
 * https://twitter.com/cazewonder
 * Nigeria.
 */
public interface ApiService {

    @FormUrlEncoded
    @POST("upload.php")
    Call<ImageData> uploadImage(@Field("title") String title, @Field("image") String image);

}
