package com.timmy.demo.utils;

import android.content.Context;
import android.databinding.BindingAdapter;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.graphics.drawable.RoundedBitmapDrawable;
import android.support.v4.graphics.drawable.RoundedBitmapDrawableFactory;
import android.text.TextUtils;
import android.util.Log;
import android.widget.ImageView;

import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;
import com.timmy.demo.R;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

public class Utils {
    static private final String TAG = "Utils";

    static public void saveToFile(@NonNull Context context,
                                  @Nullable Serializable result,
                                  @NonNull String cacheFile) {
        if (result == null) {
            return;
        }
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

    @BindingAdapter({"app:imageUrl"})
    public static void loadImage(final ImageView imageView, final String imageUrl) {
        if (!TextUtils.isEmpty(imageUrl)) {
            Picasso.get()
                    .load(imageUrl)
                    .error(R.drawable.ic_error)
                    .into(imageView, new Callback() {
                        @Override
                        public void onSuccess() {
                            Bitmap imageBitmap = ((BitmapDrawable) imageView.getDrawable()).getBitmap();
                            double scaleRatio = Math.min(imageView.getHeight() * 0.8 / imageBitmap.getHeight(),
                                    imageView.getWidth() * 0.8 / imageBitmap.getWidth());
                            imageBitmap = Bitmap.createScaledBitmap(imageBitmap, (int) (imageBitmap.getWidth() * scaleRatio),
                                    (int) (imageBitmap.getHeight() * scaleRatio), true);
                            RoundedBitmapDrawable imageDrawable = RoundedBitmapDrawableFactory.create(imageView.getResources(), imageBitmap);
                            imageDrawable.setCircular(true);
                            imageDrawable.setCornerRadius((Math.max(imageBitmap.getWidth(), imageBitmap.getHeight()) / 2.0f) -
                                    imageView.getResources().getDimensionPixelSize(R.dimen.exhibit_list_pic_padding));
                            imageView.setImageDrawable(imageDrawable);
                        }

                        @Override
                        public void onError(Exception e) {
                            Log.w(TAG, "load error : "+ imageUrl, e);
                        }
                    });
        }
    }
}
