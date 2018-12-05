package com.timmy.demo.model;

import android.content.Context;
import android.support.v4.util.Pair;

import com.timmy.demo.model.server.OpenDataApiClient;
import com.timmy.demo.model.server.result.exhibit.Exhibit;
import com.timmy.demo.model.server.result.exhibit.ExhibitResult;
import com.timmy.demo.model.server.result.plant.Plant;
import com.timmy.demo.model.server.result.plant.PlantResult;
import com.timmy.demo.utils.Constants;
import com.timmy.demo.utils.Utils;

import org.greenrobot.eventbus.EventBus;

import io.reactivex.Single;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.BiFunction;
import io.reactivex.functions.Function;
import io.reactivex.observers.DisposableSingleObserver;
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

    private Single<Pair<ExhibitResult, PlantResult>> retrieveDataFromOpenDataApi() {
        return OpenDataApiClient.getExhibitInfosRx().zipWith(OpenDataApiClient.getPlantInfosRx(),
                new BiFunction<Response<Exhibit>, Response<Plant>, Pair<ExhibitResult, PlantResult>>() {
                    @Override
                    public Pair<ExhibitResult, PlantResult> apply(Response<Exhibit> responseExhibit, Response<Plant> responsePlant) {
                        ExhibitResult exhibitResult = responseExhibit.body() == null ? null : responseExhibit.body().getResult();
                        PlantResult plantResult = responsePlant.body() == null ? null : responsePlant.body().getResult();
                        return Pair.create(exhibitResult, plantResult);
                    }
                });
    }

    public void retrieveData1(final Context context) {
        try {
            mDisposables.add(Single.just(getDataFromCache(context))
                    .subscribeOn(Schedulers.io())
                    .flatMap(new Function<ExhibitPlantInfos, Single<Pair<ExhibitResult, PlantResult>>>() {
                        @Override
                        public Single<Pair<ExhibitResult, PlantResult>> apply(ExhibitPlantInfos exhibitPlantInfos) {
                            mExhibitPlantInfos = exhibitPlantInfos;
                            EventBus.getDefault().post(Utils.RetrieveDataStatus.LOAD_DATA);
                            return retrieveDataFromOpenDataApi();
                        }
                    })
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribeWith(new DisposableSingleObserver<Pair<ExhibitResult, PlantResult>>() {
                        @Override
                        public void onSuccess(Pair<ExhibitResult, PlantResult> result) {
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
                            EventBus.getDefault().post(Utils.RetrieveDataStatus.LOAD_DATA_FAIL);
                            e.printStackTrace();
                        }
                    }));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private ExhibitPlantInfos getDataFromCache(final Context context) {
        EventBus.getDefault().post(Utils.RetrieveDataStatus.READ_CACHE);
        ExhibitResult exhibitResult = (ExhibitResult) Utils.loadFromFile(context, Constants.EXHIBIT_CACHE_FILE);
        PlantResult plantResult = (PlantResult) Utils.loadFromFile(context, Constants.PLANT_CACHE_FILE);
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
