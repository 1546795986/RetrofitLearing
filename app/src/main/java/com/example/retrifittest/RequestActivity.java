package com.example.retrifittest;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Environment;
import android.os.PersistableBundle;
import android.util.Log;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.retrifittest.domain.CommentItem;
import com.example.retrifittest.domain.GetWithParamResult;
import com.example.retrifittest.domain.LoginResult;
import com.example.retrifittest.domain.PostFileResult;
import com.example.retrifittest.domain.PostWithParams;
import com.example.retrifittest.utils.RetrofitManager;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.Headers;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class RequestActivity  extends AppCompatActivity {

    private static final String TAG = "RequestActivity";

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_request);
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {
            int permissionResult = checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE);
            if (permissionResult != PackageManager.PERMISSION_GRANTED){
                requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},1);
            }
        }

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 1) {
            //
        }
    }

    public void getWithParams(View view)
    {
        Retrofit retrofit  = RetrofitManager.getRetrofit();
        API api = retrofit.create(API.class);
        Map<String,Object> params = new HashMap<>();
        params.put("keyword","lala");
        params.put("page",0);
        params.put("order","0");
        Call<GetWithParamResult> task = api.getWithParams(params);
        task.enqueue(new Callback<GetWithParamResult>() {
            @Override
            public void onResponse(Call<GetWithParamResult> call, Response<GetWithParamResult> response) {
                Log.d(TAG,"onResponse -->"+response.body());
            }

            @Override
            public void onFailure(Call<GetWithParamResult> call, Throwable t) {

            }
        });
    }
    public void getWithParamsMap(View view)
    {
        API api = RetrofitManager.getRetrofit().create(API.class);
        Map<String,Object> param = new HashMap<>();
        param.put("keyword","good");
        Call<GetWithParamResult> task = api.getWithParams(param);
        task.enqueue(new Callback<GetWithParamResult>() {
            @Override
            public void onResponse(Call<GetWithParamResult> call, Response<GetWithParamResult> response) {
                
            }

            @Override
            public void onFailure(Call<GetWithParamResult> call, Throwable t) {

            }
        });
    }
    public void getByMysele(View view)
    {
        API api = RetrofitManager.getRetrofit().create(API.class);
        Map<String,Object> params = new HashMap<>();
        params.put("keyword","111");
        params.put("page",20);
        params.put("order","0");
        Call<GetWithParamResult> task = api.getWithParams(params);
        task.enqueue(new Callback<GetWithParamResult>() {
            @Override
            public void onResponse(Call<GetWithParamResult> call, Response<GetWithParamResult> response) {

            }

            @Override
            public void onFailure(Call<GetWithParamResult> call, Throwable t) {

            }
        });

    }
    public void postWithParams(View view){
        API api = RetrofitManager.getRetrofit().create(API.class);
        Call<PostWithParams> task = api.postWithParams("这是我的内容");
        task.enqueue(new Callback<PostWithParams>() {
            @Override
            public void onResponse(Call<PostWithParams> call, Response<PostWithParams> response) {
                int code = response.code();
                Log.d(TAG,"code == "+code);

            }

            @Override
            public void onFailure(Call<PostWithParams> call, Throwable t) {

            }
        });
    }
    public void postWithBody(View view){
        API api = RetrofitManager.getRetrofit().create(API.class);
        CommentItem commentItem = new CommentItem("324","good");
        Call<PostWithParams> task = api.postWithBody(commentItem);
        task.enqueue(new Callback<PostWithParams>() {
            @Override
            public void onResponse(Call<PostWithParams> call, Response<PostWithParams> response) {
                if (response.code() == 200)
                {
                    Log.d(TAG,"code " + response.code() );
                }
            }

            @Override
            public void onFailure(Call<PostWithParams> call, Throwable t) {

            }
        });
    }
    public void postFile(View view){
        API api = RetrofitManager.getRetrofit().create(API.class);
        File file = new File("");
        RequestBody body =  RequestBody.create(MediaType.parse("image/jpeg"),file);
        MultipartBody.Part part = MultipartBody.Part.createFormData("file","",body);
        Call<PostFileResult> task = api.postFile(part,"this is a token");
        task.enqueue(new Callback<PostFileResult>() {
            @Override
            public void onResponse(Call<PostFileResult> call, Response<PostFileResult> response) {

            }

            @Override
            public void onFailure(Call<PostFileResult> call, Throwable t) {

            }
        });
    }
    private MultipartBody.Part createPartByPathAndKey(String path,String key){
        File file  = new File(path);
        RequestBody body = RequestBody.create(MediaType.parse("ddd"),file   );
        MultipartBody.Part part = MultipartBody.Part.createFormData(key,file.getName(),body);
        return part;
    }
    public void postFiles(View view){
        API api = RetrofitManager.getRetrofit().create(API.class);
        List<MultipartBody.Part> parts  =  new ArrayList<>();
        MultipartBody.Part partOne = createPartByPathAndKey("", "");
        MultipartBody.Part partTwo = createPartByPathAndKey("2", "2");
        parts.add(partTwo);
        parts.add(partOne);
        Call<PostFileResult> task = api.postFiles(parts);
        task.enqueue(new Callback<PostFileResult>() {
            @Override
            public void onResponse(Call<PostFileResult> call, Response<PostFileResult> response) {

            }

            @Override
            public void onFailure(Call<PostFileResult> call, Throwable t) {

            }
        });
    }
    public void postFileWithParams(View view){
        MultipartBody.Part part = createPartByPathAndKey("/pate", "file");
        API api = RetrofitManager.getRetrofit().create(API.class);
        Map<String ,String > params  = new HashMap<>();
        params.put("description","这是一张长方形图片");
        params.put("isFree","true");
        Map<String ,String  > headers = new HashMap<>();
        headers.put("token","good");
        headers.put("version","2.0");
        headers.put("client","iPhone18");
        Call<PostFileResult> task = api.postFileWithParams(part, params,headers);
        task.enqueue(new Callback<PostFileResult>() {
            @Override
            public void onResponse(Call<PostFileResult> call, Response<PostFileResult> response) {

            }

            @Override
            public void onFailure(Call<PostFileResult> call, Throwable t) {

            }
        });
    }

    public void doLogin(View view){
        API api = RetrofitManager.getRetrofit().create(API.class);
        Call<LoginResult> task = api.doLogin("name", "pass");
        task.enqueue(new Callback<LoginResult>() {
            @Override
            public void onResponse(Call<LoginResult> call, Response<LoginResult> response) {

            }

            @Override
            public void onFailure(Call<LoginResult> call, Throwable t) {

            }
        });
    }
    public void doLogin01(View view){
        API api = RetrofitManager.getRetrofit().create(API.class);
        Map<String,String> map = new HashMap<>();
        map.put("userName","naem");
        map.put("password","pass");
        Call<LoginResult> task = api.doLogin(map);
        task.enqueue(new Callback<LoginResult>() {
            @Override
            public void onResponse(Call<LoginResult> call, Response<LoginResult> response) {

            }

            @Override
            public void onFailure(Call<LoginResult> call, Throwable t) {

            }
        });
    }
    public void downLoadFile(View view){
        String url = "/download/8";
        API api = RetrofitManager.getRetrofit().create(API.class);
        Call<ResponseBody> task = api.downFile(url);
        task.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                int c = response.code();
                Log.d(TAG,"code -->"+ c);
                if(c == HttpURLConnection.HTTP_OK)
                {
                    Log.d(TAG,"current thread name ->" + Thread.currentThread().getName());
                    //知道文件名称
                    Headers headers = response.headers();
                    String fileNameHeader = headers.get("Content-diposition");
                    String fileName = "未命名.png";
                    if (fileNameHeader!=null) {
                        fileName = fileNameHeader.replace("attachment; filename=","");
                        Log.d(TAG,fileName);
                    }
                    for (int i = 0;i<headers.size();i++){
                        String value = headers.value(i);
                        String key = headers.name(i);
                        Log.d(TAG,key+"value -->"+value);
                    }
                    //写文件
                    writeString2Disk(response,fileName);
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
            Log.d(TAG,"onFailure");
            }
        });
    }

    private void writeString2Disk(Response<ResponseBody> response, String fileName) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                InputStream inputStream = response.body().byteStream();
                File baseOutFile = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
                Log.d(TAG,"outfile-->" + baseOutFile);
                File outFile = new File(baseOutFile,fileName);
                try {
                    FileOutputStream fos = new FileOutputStream(outFile);
                    byte[] buffer = new byte[1024];
                    int len;
                    while ((len =inputStream.read(buffer))!=-1){
                        fos.write(buffer,0,len);
                    }
                    fos.close();
                }catch (Exception e){
                    e.printStackTrace();
                }


//                File exPath = Environment.getExternalStorageDirectory();
//                Log.d(TAG,"expath -->   "+exPath);
//                File outFile = new File()
            }
        }).start();
    }
}
