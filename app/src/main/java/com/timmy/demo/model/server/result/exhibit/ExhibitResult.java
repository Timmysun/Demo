package com.timmy.demo.model.server.result.exhibit;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;

import com.google.gson.annotations.SerializedName;
import com.timmy.demo.model.server.result.ResultBase;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.List;
import java.util.Locale;

public class ExhibitResult extends ResultBase implements Serializable {

    private static final long serialVersionUID = 876323262645176354L;

    @SerializedName("results")
    private List<ExhibitInfo> mExhibitInfos;

    public List<ExhibitInfo> getInfos() {
        return mExhibitInfos;
    }

    @NonNull
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(String.format(Locale.getDefault(), "limit=%d offset=%d count=%d sort=%s\n",
                getLimit(), getOffset(), getCount(), getSort()));
        if (mExhibitInfos != null) {
            for (ExhibitInfo exhibitInfo : mExhibitInfos) {
                sb.append("[" + exhibitInfo.toString() + "]\n");
            }
        }
        return sb.toString();
    }

    public boolean equals(ExhibitResult result) {
        if (result == null) {
            return false;
        }

        return getInfos() != null && result.getInfos() != null && getInfos().size() == result.getInfos().size();
    }

    static public void saveToFile(@NonNull Context context, ExhibitResult result) {
        Log.e("Timmy", "Exhibit save to file");
        File cache = new File(context.getCacheDir(), "exhibit.cache");
        try {
            FileOutputStream outputStream = new FileOutputStream(cache);
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(outputStream);
            objectOutputStream.writeObject(result);
            objectOutputStream.close();
            outputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Nullable
    static public ExhibitResult loadFromFile(@NonNull Context context) {
        File cache = new File(context.getCacheDir(), "exhibit.cache");
        if (cache.exists()) {
            try {
                FileInputStream inputStream = new FileInputStream(cache);
                ObjectInputStream objectInputStream = new ObjectInputStream(inputStream);
                ExhibitResult result = (ExhibitResult) objectInputStream.readObject();
                objectInputStream.close();
                inputStream.close();
                Log.e("Timmy", "Exhibit load from file");
                return result;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        Log.e("Timmy", "Exhibit load from file fail");
        return null;
    }
}
