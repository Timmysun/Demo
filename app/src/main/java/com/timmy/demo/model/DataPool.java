package com.timmy.demo.model;

import android.content.Context;
import android.databinding.Bindable;
import android.databinding.Observable;
import android.databinding.PropertyChangeRegistry;
import android.util.Log;

import com.timmy.demo.BR;
import com.timmy.demo.model.server.OpenDataApiClient;
import com.timmy.demo.model.server.result.exhibit.Exhibit;
import com.timmy.demo.model.server.result.exhibit.ExhibitResult;
import com.timmy.demo.model.server.result.plant.Plant;
import com.timmy.demo.model.server.result.plant.PlantResult;
import com.timmy.demo.utils.Constants;
import com.timmy.demo.utils.Utils;

import io.reactivex.Single;
import io.reactivex.SingleSource;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Function;
import io.reactivex.observers.DisposableSingleObserver;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Response;

public class DataPool implements Observable{

    private CompositeDisposable mDisposables = new CompositeDisposable();

    private final PropertyChangeRegistry mPropertyChangeRegistry = new PropertyChangeRegistry();

    @Bindable
    private ExhibitPlantInfos mExhibitPlantInfos;

    @Bindable
    private String mToastMessage;

    public DataPool() { }

    public ExhibitPlantInfos getExhibitPlantInfos() {
        return mExhibitPlantInfos;
    }

    public void setExhibitPlantInfos(ExhibitPlantInfos exhibitPlantInfos) {
        mExhibitPlantInfos = exhibitPlantInfos;
        //mPropertyChangeRegistry.notifyChange(this, BR.exhibitPlantInfos);
    }

    public String getToastMessage() {
        return mToastMessage;
    }

    public void setToastMessage(String toastMessage) {
        mToastMessage = toastMessage;
        mPropertyChangeRegistry.notifyChange(this, BR.toastMessage);
    }

    public void retrieveData(final Context context) {
        try {
            mDisposables.add(Single.just(getDataFromCache(context))
                    .subscribeOn(Schedulers.io())
                    .flatMap(new Function<ExhibitPlantInfos, SingleSource<Response<Exhibit>>>() {
                        @Override
                        public SingleSource<Response<Exhibit>> apply(ExhibitPlantInfos exhibitPlantInfos) throws Exception {
                            if (!ExhibitPlantInfos.isEmptyInfos(exhibitPlantInfos)) {
                                setExhibitPlantInfos(exhibitPlantInfos);
                            }
                            return OpenDataApiClient.getExhibitInfosRx();
                        }
                    })
                    .flatMap(new Function<Response<Exhibit>, SingleSource<Response<Plant>>>() {
                        @Override
                        public SingleSource<Response<Plant>> apply(Response<Exhibit> exhibitResponse) throws Exception {
                            Exhibit exhibit = exhibitResponse.body();
                            if (exhibit != null) {
                                ExhibitResult result = exhibit.getResult();
                                if (result != null) {
                                    setToastMessage("LOADING SUCCESS");
                                    if (getExhibitPlantInfos() == null) {
                                        setExhibitPlantInfos(new ExhibitPlantInfos(result, null));
                                    } else if (mExhibitPlantInfos.updateExhibitResult(result)) {
                                        Utils.saveToFile(context, result, Constants.EXHIBIT_CACHE_FILE);
                                        //mPropertyChangeRegistry.notifyChange(DataPool.this, BR.exhibitPlantInfos);

                                    }
                                }
                            }
                            return OpenDataApiClient.getPlantInfosRx();
                        }
                    })
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribeWith(new DisposableSingleObserver<Response<Plant>>() {
                        @Override
                        public void onSuccess(Response<Plant> plantResponse) {
                            Plant plant = plantResponse.body();
                            if (plant != null) {
                                PlantResult result = plant.getResult();
                                if (result != null) {
                                    if (getExhibitPlantInfos() != null && mExhibitPlantInfos.updatePlantResult(result)) {
                                        Utils.saveToFile(context, result, Constants.PLANT_CACHE_FILE);
                                        //mPropertyChangeRegistry.notifyChange(DataPool.this, BR.exhibitPlantInfos);
                                    }
                                }
                            }
                        }

                        @Override
                        public void onError(Throwable e) {
                            setToastMessage("update file success");
                            Log.e("Timmy", "get error?", e);
                        }
                    }));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private ExhibitPlantInfos getDataFromCache(final Context context) {
        ExhibitResult exhibitResult = (ExhibitResult) Utils.loadFromFile(context, Constants.EXHIBIT_CACHE_FILE);
        PlantResult plantResult = (PlantResult) Utils.loadFromFile(context, Constants.PLANT_CACHE_FILE);
        if (exhibitResult == null) {
            return ExhibitPlantInfos.getEmptyInfos();
        }
        return new ExhibitPlantInfos(exhibitResult, plantResult);
    }

    public void cancelRetrieveData() {
        mDisposables.clear();
    }

    @Override
    public void addOnPropertyChangedCallback(OnPropertyChangedCallback callback) {
        mPropertyChangeRegistry.add(callback);
    }

    @Override
    public void removeOnPropertyChangedCallback(OnPropertyChangedCallback callback) {
        mPropertyChangeRegistry.remove(callback);
    }
}
