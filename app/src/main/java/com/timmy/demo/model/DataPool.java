package com.timmy.demo.model;

import android.util.Log;

import com.timmy.demo.model.server.OpenDataApiClient;
import com.timmy.demo.model.server.result.exhibit.Exhibit;

import io.reactivex.Single;
import io.reactivex.SingleSource;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Function;
import io.reactivex.observers.DisposableSingleObserver;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Response;

public class DataPool {


    private CompositeDisposable mDisposables = new CompositeDisposable();

    public DataPool() {

    }

    public void retrieveData() {
        mDisposables.add(Single.just(getExhibitFromCache())
                .subscribeOn(Schedulers.io())
                .flatMap(new Function<Exhibit, SingleSource<Response<Exhibit>>>() {
                    @Override
                    public SingleSource<Response<Exhibit>> apply(Exhibit exhibit) throws Exception {
                        //String petId = response.body().getPetId();
                        Log.e("Timmy", "exhibit == " + (exhibit==null));
                        return OpenDataApiClient.getExhibitInfosRx();
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableSingleObserver<Response<Exhibit>>() {
                    @Override
                    public void onSuccess(Response<Exhibit> response) {
                        Log.e("Timmy", response.body().toString());
                    }

                    @Override
                    public void onError(Throwable e) {

                    }
                }));

//        mDisposables.add(OpenDataApiClient.getExhibitInfosRx()
//                .subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribeWith(new DisposableSingleObserver<Response<Exhibit>>() {
//                    @Override
//                    public void onSuccess(Response<Exhibit> response) {
//                        int code = response.code();
//                    }
//
//                    @Override
//                    public void onError(Throwable e) {
//
//                    }
//                }));
//
//        mDisposables.add(OpenDataApiClient.getPlantInfosRx()
//                .subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribeWith(new DisposableSingleObserver<Response<Plant>>() {
//                    @Override
//                    public void onSuccess(Response<Plant> response) {
//                        int code = response.code();
//                    }
//
//                    @Override
//                    public void onError(Throwable e) {
//
//                    }
//                }));
    }

    private Exhibit getExhibitFromCache() {
        return new Exhibit();
    }

    public void cancelRetrieveData() {
        mDisposables.clear();
    }
}
