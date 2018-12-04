package com.timmy.demo.utils;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

public class Utils {

    static public void saveToFile(@NonNull Context context,
                                  @NonNull Serializable result,
                                  @NonNull String cacheFile) {
        File cache = new File(context.getCacheDir(), cacheFile);
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
    static public Object loadFromFile(@NonNull Context context, String cacheFile) {
        File cache = new File(context.getCacheDir(), cacheFile);
        if (cache.exists()) {
            try {
                FileInputStream inputStream = new FileInputStream(cache);
                ObjectInputStream objectInputStream = new ObjectInputStream(inputStream);
                Object result = objectInputStream.readObject();
                objectInputStream.close();
                inputStream.close();
                return result;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return null;
    }
}
