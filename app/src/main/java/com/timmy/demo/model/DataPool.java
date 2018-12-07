package com.timmy.demo.model;

import android.content.Context;
import android.support.v4.util.Pair;
import android.util.Log;

import com.timmy.demo.BuildConfig;
import com.timmy.demo.event.ExhibitInfoChangeEvent;
import com.timmy.demo.model.server.OpenDataApiClient;
import com.timmy.demo.model.server.result.exhibit.Exhibit;
import com.timmy.demo.model.server.result.exhibit.ExhibitInfo;
import com.timmy.demo.model.server.result.exhibit.ExhibitResult;
import com.timmy.demo.model.server.result.plant.Plant;
import com.timmy.demo.model.server.result.plant.PlantResult;
import com.timmy.demo.utils.Constants;
import com.timmy.demo.utils.Utils;

import org.greenrobot.eventbus.EventBus;

import java.util.List;
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
    private static final String TAG = "DataPool";

    private static DataPool sInstance;

    private CompositeDisposable mDisposables = new CompositeDisposable();

    private ExhibitPlantInfos mExhibitPlantInfos;

    private int mCurrentExhibit = -1;
    private int mCurrentPlant = -1;

    private DataPool() { }

    public static synchronized DataPool getInstance() {
        if (sInstance == null) {
            sInstance = new DataPool();
        }
        return sInstance;
    }

    public List<ExhibitInfo> getExhibitList() {
        return mExhibitPlantInfos.getExhibitList();
    }

    public void setCurrentExhibit(int index) {
        mCurrentExhibit = index;
        EventBus.getDefault().post(new ExhibitInfoChangeEvent(getCurrentExhibitInfo()));
    }

    public ExhibitInfo getCurrentExhibitInfo() {
        return mCurrentExhibit >= 0 && mCurrentExhibit < mExhibitPlantInfos.getExhibitList().size() ?
                mExhibitPlantInfos.getExhibitList().get(mCurrentExhibit) : null;
    }

    public void setCurrentPlant(int index) {
        mCurrentPlant = index;
    }

    public int getCurrentPlant() {
        return mCurrentPlant;
    }

    private Observable<Pair<ExhibitResult, PlantResult>> retrieveDataFromOpenDataApi() {
        EventBus.getDefault().post(Utils.RetrieveDataStatus.LOAD_DATA);
        Log.d(TAG, "load data from network");
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
                    Observable.fromCallable(new Callable<Pair<ExhibitResult, PlantResult>>() {
                        @Override
                        public Pair<ExhibitResult, PlantResult> call() throws Exception {
                            return getDataFromCache(context);
                        }
                    }).subscribeOn(Schedulers.io())
                    .flatMap(new Function<Pair<ExhibitResult, PlantResult>, Observable<Pair<ExhibitResult, PlantResult>>>() {
                        @Override
                        public Observable<Pair<ExhibitResult, PlantResult>> apply(Pair<ExhibitResult, PlantResult> exhibitPlantPair) {
                            if (exhibitPlantPair.first != null) {
                                mExhibitPlantInfos = new ExhibitPlantInfos(exhibitPlantPair.first, exhibitPlantPair.second);
                            }
                            if (mExhibitPlantInfos == null || BuildConfig.LOAD_DATA_EVERY_TIME) {
                                return retrieveDataFromOpenDataApi();
                            }
                            return null;
                        }
                    })
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribeWith(new DisposableObserver<Pair<ExhibitResult, PlantResult>>() {
                        @Override
                        public void onNext(Pair<ExhibitResult, PlantResult> result) {
                            Log.d(TAG, "load data from network complete");
                            if (mExhibitPlantInfos != null) {
                                if (mExhibitPlantInfos.updateExhibitResult(result.first)) {
                                    Log.d(TAG, "exhibit data changes, save to cache.");
                                    Utils.saveToFile(context, result.first, Constants.EXHIBIT_CACHE_FILE);
                                }
                                if (mExhibitPlantInfos.updatePlantResult(result.second)) {
                                    Log.d(TAG, "plants data changes, save to cache.");
                                    Utils.saveToFile(context, result.second, Constants.PLANT_CACHE_FILE);
                                }
                            } else {
                                Log.d(TAG, "save network data to cache.");
                                mExhibitPlantInfos = new ExhibitPlantInfos(result.first, result.second);
                                Utils.saveToFile(context, result.first, Constants.EXHIBIT_CACHE_FILE);
                                Utils.saveToFile(context, result.second, Constants.PLANT_CACHE_FILE);
                            }
                            EventBus.getDefault().post(Utils.RetrieveDataStatus.LOAD_DATA_SUCCESS);
                        }

                        @Override
                        public void onError(Throwable e) {
                            if (mExhibitPlantInfos == null) {
                                EventBus.getDefault().post(Utils.RetrieveDataStatus.LOAD_DATA_FAIL);
                            }
                            e.printStackTrace();
                        }

                        @Override
                        public void onComplete() { }
                    }));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private Pair<ExhibitResult, PlantResult> getDataFromCache(final Context context) {
        Log.d(TAG, "load data from cache");
        EventBus.getDefault().post(Utils.RetrieveDataStatus.READ_CACHE);

        ExhibitResult exhibitResult = (ExhibitResult) Utils.loadFromFile(context, Constants.EXHIBIT_CACHE_FILE);
        PlantResult plantResult = (PlantResult) Utils.loadFromFile(context, Constants.PLANT_CACHE_FILE);

        if (exhibitResult == null) {
            Log.d(TAG, "load data from cache fail");
            EventBus.getDefault().post(Utils.RetrieveDataStatus.READ_CACHE_FAIL);
        } else {
            Log.d(TAG, "load data from cache success");
            EventBus.getDefault().post(Utils.RetrieveDataStatus.READ_CACHE_SUCCESS);
        }
        return Pair.create(exhibitResult, plantResult);
    }

    public void cancelRetrieveData() {
        mDisposables.clear();
    }

}
