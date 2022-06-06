package com.example.retrifittest;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.example.retrifittest.adapter.JsonResultListAdaper;
import com.example.retrifittest.domain.JsonResult;
import com.example.retrifittest.utils.RetrofitManager;
import com.google.gson.Gson;

import java.io.IOException;
import java.net.HttpURLConnection;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();


    }

    private void initView() {
        RecyclerView mResultList = findViewById(R.id.result_list);
        mResultList.setLayoutManager(new LinearLayoutManager(this));
        mResultList.setAdapter(new JsonResultListAdaper());
    }

    public void getRequest(View view) {
        //先有一个浏览器

        API api = RetrofitManager.getRetrofit().create(API.class);
        Call<JsonResult> task = api.getJson();
        task.enqueue(new Callback<JsonResult>() {
            @Override
            public void onResponse(Call<JsonResult> call, Response<JsonResult> response) {
                Log.d(TAG,"onResponse ---> "+response.code());
                if (response.code() == HttpURLConnection.HTTP_OK){
                    JsonResult result = response.body();
                    updateList(result);
                }

            }

            @Override
            public void onFailure(Call<JsonResult> call, Throwable t) {
                Log.d(TAG,"onFailues..."+t.toString());
            }
        });

    }


    private void updateList(JsonResult jsonResult) {
        Retrofit retrofit = new Retrofit.Builder()
                .build();
        API api = retrofit.create(API.class);
        Call<JsonResult> task = api.getJson();
        task.enqueue(new Callback<JsonResult>() {
            @Override
            public void onResponse(Call<JsonResult> call, Response<JsonResult> response) {
                Log.d(TAG,"");
            }
            @Override
            public void onFailure(Call<JsonResult> call, Throwable t) {
                
            }
        });
    }
}