package com.timmy.demo.model.server;

import com.timmy.demo.model.server.result.exhibit.Exhibit;
import com.timmy.demo.model.server.result.plant.Plant;
import com.timmy.demo.utils.Constants;

import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;

public class OpenDataApiClient {

    private static OpenDataApiClient sApiClient = null;
    private Retrofit mRetrofit;
    private OkHttpClient mHttpClient;
    private OpenDataApi mOpenDataApi;

    public static synchronized OpenDataApiClient getInstance() {
        if (sApiClient == null) {
            sApiClient = new OpenDataApiClient();
        }
        return sApiClient;
    }

    private OpenDataApiClient(){
        mHttpClient = new OkHttpClient();
        mRetrofit = new Retrofit.Builder()
                .baseUrl(Constants.OPEN_DATA_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(mHttpClient)
                .build();
        mOpenDataApi = mRetrofit.create(OpenDataApi.class);
    }

    private OpenDataApi openDataApi() {
        return mOpenDataApi;
    }

    public static void getExhabitInfos(Callback<Exhibit> callback){
        OpenDataApiClient.getInstance().openDataApi().getExhabitInfos().enqueue(callback);
    }

    public static void getPlantInfos(Callback<Plant> callback){
        OpenDataApiClient.getInstance().openDataApi().getPlantInfos().enqueue(callback);
    }


    public interface OpenDataApi {
        @GET("opendata/datalist/apiAccess?scope=resourceAquire&rid=5a0e5fbb-72f8-41c6-908e-2fb25eff9b8a")
        Call<Exhibit> getExhabitInfos();

        @GET("opendata/datalist/apiAccess?scope=resourceAquire&rid=f18de02f-b6c9-47c0-8cda-50efad621c14")
        Call<Plant> getPlantInfos();
    }

}
