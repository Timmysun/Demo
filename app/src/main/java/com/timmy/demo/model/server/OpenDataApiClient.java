package com.timmy.demo.model.server;

import com.timmy.demo.model.server.result.exhibit.Exhibit;
import com.timmy.demo.model.server.result.plant.Plant;
import com.timmy.demo.utils.Constants;

import io.reactivex.Single;
import okhttp3.OkHttpClient;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;

public class OpenDataApiClient {

    private static OpenDataApiClient sApiClient = null;
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
        mOpenDataApi = new Retrofit.Builder()
                .baseUrl(Constants.OPEN_DATA_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .client(mHttpClient)
                .build()
                .create(OpenDataApi.class);
    }

    private OpenDataApi openDataApi() {
        return mOpenDataApi;
    }

    public static Single<Response<Exhibit>> getExhibitInfosRx(){
        return OpenDataApiClient.getInstance().openDataApi().getExhibitInfos();
    }

    public static Single<Response<Plant>> getPlantInfosRx(){
        return OpenDataApiClient.getInstance().openDataApi().getPlantInfos();
    }


    public interface OpenDataApi {
        @GET("opendata/datalist/apiAccess?scope=resourceAquire&rid=5a0e5fbb-72f8-41c6-908e-2fb25eff9b8a")
        Single<Response<Exhibit>> getExhibitInfos();

        @GET("opendata/datalist/apiAccess?scope=resourceAquire&rid=f18de02f-b6c9-47c0-8cda-50efad621c14")
        Single<Response<Plant>> getPlantInfos();
    }

}
