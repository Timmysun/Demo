package com.timmy.demo.model;

import android.content.Context;
import android.util.Log;

import com.timmy.demo.model.server.OpenDataApiClient;
import com.timmy.demo.model.server.result.exhibit.Exhibit;
import com.timmy.demo.model.server.result.exhibit.ExhibitResult;
import com.timmy.demo.model.server.result.plant.Plant;

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

    private ExhibitResult mResult;
    public DataPool() {

    }

    public void retrieveData(final Context context) {
        try {
            mDisposables.add(Single.just(getDataFromCache(context))
                    .subscribeOn(Schedulers.io())
                    .flatMap(new Function<ExhibitResult, SingleSource<Response<Exhibit>>>() {
                        @Override
                        public SingleSource<Response<Exhibit>> apply(ExhibitResult exhibit) throws Exception {
                            Log.e("Timmy", "??? " + (exhibit == null));
                            mResult = exhibit;
                            Log.e("Timmy", exhibit.toString());
                            return OpenDataApiClient.getExhibitInfosRx();
                        }
                    })
                    .flatMap(new Function<Response<Exhibit>, SingleSource<Response<Plant>>>() {
                        @Override
                        public SingleSource<Response<Plant>> apply(Response<Exhibit> exhibitResponse) throws Exception {
                            ExhibitResult result = exhibitResponse.body().getResult();
                            if (!mResult.equals(result)) {
                                ExhibitResult.saveToFile(context, result);
                                mResult = result;
                            }
                            return OpenDataApiClient.getPlantInfosRx();
                        }
                    })
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribeWith(new DisposableSingleObserver<Response<Plant>>() {
                        @Override
                        public void onSuccess(Response<Plant> response) {
                            Log.e("Timmy", response.body().toString());
                        }

                        @Override
                        public void onError(Throwable e) {
                            Log.e("Timmy", "get error?", e);
                        }
                    }));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private ExhibitResult getDataFromCache(final Context context) {
        ExhibitResult result = ExhibitResult.loadFromFile(context);
        Log.e("Timmy", "rsu = " + result);
        if (result == null) {
            return new ExhibitResult();
        } else {
            return result;
        }
    }

    public void cancelRetrieveData() {
        mDisposables.clear();
    }
}
