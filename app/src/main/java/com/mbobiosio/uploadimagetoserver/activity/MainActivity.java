package com.mbobiosio.uploadimagetoserver.activity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.mbobiosio.uploadimagetoserver.R;
import com.mbobiosio.uploadimagetoserver.api.ApiService;
import com.mbobiosio.uploadimagetoserver.api.RetrofitServer;
import com.mbobiosio.uploadimagetoserver.model.ImageData;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    private  static final int IMAGE = 100;
    private Bitmap mBitmap;
    ImageView mImageView;
    Button mSelectImage, mUploadImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mSelectImage = findViewById(R.id.select_image);
        mUploadImage = findViewById(R.id.upload_image);
        mImageView = findViewById(R.id.imageView);

        mSelectImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectImage();
            }
        });

        mUploadImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                uploadImage();
            }
        });
    }

    private void selectImage() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent, IMAGE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == IMAGE && resultCode == RESULT_OK && data != null) {

            Uri resultUri = data.getData();

            mImageView.setImageURI(resultUri);

            try {
                mBitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), resultUri);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private String getImage(){
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        mBitmap.compress(Bitmap.CompressFormat.JPEG,100, baos);
        byte [] dataByte = baos.toByteArray();
        return  Base64.encodeToString(dataByte, Base64.DEFAULT);
    }

    private void uploadImage(){

        String title = "IMG_";

        ApiService api = RetrofitServer.getRetrofit().create(ApiService.class);
        Call<ImageData> call  = api.uploadImage(title, getImage());

        call.enqueue(new Callback<ImageData>() {
            @Override
            public void onResponse(@NonNull Call<ImageData> call, @NonNull Response<ImageData> response) {
                ImageData data = response.body();
                assert data != null;
                Log.d("Response", data.getResponse());
            }

            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            public void onFailure(@NonNull Call<ImageData> call, @NonNull Throwable t) {
                Log.d("Error Response", Objects.requireNonNull(t.getMessage()));

            }
        });

    }


}