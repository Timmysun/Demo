package com.timmy.demo.model;

import android.content.Context;
import android.support.v4.util.Pair;
import android.util.Log;

import com.timmy.demo.model.server.OpenDataApiClient;
import com.timmy.demo.model.server.result.exhibit.Exhibit;
import com.timmy.demo.model.server.result.exhibit.ExhibitResult;
import com.timmy.demo.model.server.result.plant.Plant;
import com.timmy.demo.model.server.result.plant.PlantResult;
import com.timmy.demo.utils.Constants;
import com.timmy.demo.utils.Utils;

import org.greenrobot.eventbus.EventBus;

import java.util.concurrent.Callable;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.BiFunction;
import io.reactivex.functions.Function;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Response;

public class DataPool{

    private static DataPool sInstance;

    private CompositeDisposable mDisposables = new CompositeDisposable();

    private ExhibitPlantInfos mExhibitPlantInfos;

    private DataPool() { }

    public static synchronized DataPool getInstance() {
        if (sInstance == null) {
            sInstance = new DataPool();
        }
        return sInstance;
    }

    private Observable<Pair<ExhibitResult, PlantResult>> retrieveDataFromOpenDataApi() {
        EventBus.getDefault().post(Utils.RetrieveDataStatus.LOAD_DATA);
        return OpenDataApiClient.getExhibitInfosRx().zipWith(OpenDataApiClient.getPlantInfosRx(),
                new BiFunction<Response<Exhibit>, Response<Plant>, Pair<ExhibitResult, PlantResult>>() {
                    @Override
                    public Pair<ExhibitResult, PlantResult> apply(Response<Exhibit> responseExhibit, Response<Plant> responsePlant) {
                        ExhibitResult exhibitResult = responseExhibit.body() == null ? null : responseExhibit.body().getResult();
                        PlantResult plantResult = responsePlant.body() == null ? null : responsePlant.body().getResult();
                        return Pair.create(exhibitResult, plantResult);
                    }
                }).toObservable();
    }

    public void retrieveData(final Context context) {
        try {
            mDisposables.add(
                    Observable.fromCallable(new Callable<ExhibitPlantInfos>() {
                        @Override
                        public ExhibitPlantInfos call() throws Exception {
                            return getDataFromCache(context);
                        }
                    }).subscribeOn(Schedulers.io())
                    .flatMap(new Function<ExhibitPlantInfos, Observable<Pair<ExhibitResult, PlantResult>>>() {
                        @Override
                        public Observable<Pair<ExhibitResult, PlantResult>> apply(ExhibitPlantInfos exhibitPlantInfos) {
                            mExhibitPlantInfos = exhibitPlantInfos;
                            if (ExhibitPlantInfos.isEmptyInfos(mExhibitPlantInfos) || true) {
                                return retrieveDataFromOpenDataApi();
                            }
                            return null;
                        }
                    })
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribeWith(new DisposableObserver<Pair<ExhibitResult, PlantResult>>() {
//                        @Override
//                        public void onSuccess(Pair<ExhibitResult, PlantResult> result) {
//                            if (mExhibitPlantInfos != null) {
//                                if (mExhibitPlantInfos.updateExhibitResult(result.first)) {
//                                    Utils.saveToFile(context, result.first, Constants.EXHIBIT_CACHE_FILE);
//                                }
//                                if (mExhibitPlantInfos.updatePlantResult(result.second)) {
//                                    Utils.saveToFile(context, result.second, Constants.PLANT_CACHE_FILE);
//                                }
//                            }
//                            EventBus.getDefault().post(Utils.RetrieveDataStatus.LOAD_DATA_SUCCESS);
//                        }

                        @Override
                        public void onNext(Pair<ExhibitResult, PlantResult> result) {
                            Log.e("Timmy", "onnext ???");
                            if (mExhibitPlantInfos != null) {
                                if (mExhibitPlantInfos.updateExhibitResult(result.first)) {
                                    Utils.saveToFile(context, result.first, Constants.EXHIBIT_CACHE_FILE);
                                }
                                if (mExhibitPlantInfos.updatePlantResult(result.second)) {
                                    Utils.saveToFile(context, result.second, Constants.PLANT_CACHE_FILE);
                                }
                            }
                            EventBus.getDefault().post(Utils.RetrieveDataStatus.LOAD_DATA_SUCCESS);

                        }

                        @Override
                        public void onError(Throwable e) {
                            if (ExhibitPlantInfos.isEmptyInfos(mExhibitPlantInfos)) {
                                EventBus.getDefault().post(Utils.RetrieveDataStatus.LOAD_DATA_FAIL);
                                e.printStackTrace();
                            }
                        }

                        @Override
                        public void onComplete() {
                            Log.e("Timmy", "onComplete??");
                        }
                    }));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private ExhibitPlantInfos getDataFromCache(final Context context) {
        Log.e("Timmy", "in cache");
        EventBus.getDefault().post(Utils.RetrieveDataStatus.READ_CACHE);
        ExhibitResult exhibitResult = (ExhibitResult) Utils.loadFromFile(context, Constants.EXHIBIT_CACHE_FILE);
        PlantResult plantResult = (PlantResult) Utils.loadFromFile(context, Constants.PLANT_CACHE_FILE);
        try {
            Thread.sleep(5000);
        }catch (Exception e) {

        }
        if (exhibitResult == null) {
            EventBus.getDefault().post(Utils.RetrieveDataStatus.READ_CACHE_FAIL);
            return ExhibitPlantInfos.getEmptyInfos();
        } else {
            EventBus.getDefault().post(Utils.RetrieveDataStatus.READ_CACHE_SUCCESS);
            return new ExhibitPlantInfos(exhibitResult, plantResult);
        }
    }

    public void cancelRetrieveData() {
        mDisposables.clear();
    }

}
