package com.timmy.demo.model.server.result.exhibit;

import android.databinding.BindingAdapter;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.v4.graphics.drawable.RoundedBitmapDrawable;
import android.support.v4.graphics.drawable.RoundedBitmapDrawableFactory;
import android.widget.ImageView;

import com.google.gson.annotations.SerializedName;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;
import com.timmy.demo.R;

import java.io.Serializable;
import java.util.Locale;

public class ExhibitInfo implements Parcelable, Serializable {

    private static final long serialVersionUID = 876323262645176353L;

    @SerializedName("_id")
    private int mId;
    @SerializedName("E_no")
    private String mNumber;

    @SerializedName("E_Name")
    private String mName;

    @SerializedName("E_URL")
    private String mUrl;
    @SerializedName("E_Pic_URL")
    private String mPicUrl;

    @SerializedName("E_Category")
    private String mCategory;

    @SerializedName("E_Info")
    private String mInfo;

    @SerializedName("E_Memo")
    private String mMemo;

    @SerializedName("E_Geo")
    private String mGeo;

    public int getId() {
        return mId;
    }

    public String getNumber() {
        return mNumber;
    }

    public String getName() {
        return mName;
    }

    public String getUrl() {
        return mUrl;
    }

    public String getPicUrl() {
        return mPicUrl;
    }

    public String getCategory() {
        return mCategory;
    }

    public String getInfo() {
        return mInfo;
    }

    public String getMemo() {
        return mMemo;
    }

    public String getGeo() {
        return mGeo;
    }

    @NonNull
    @Override
    public String toString() {
        return String.format(Locale.getDefault(), "Name=%s URL=%s PIC_URL=%s CAT=%s INFO=%s MEMO=%s NO=%s id=%d",
                mName, mUrl, mPicUrl, mCategory, mInfo, mMemo, mNumber, getId());
    }

    public boolean equals(ExhibitInfo exhibit) {
        if (exhibit == null) {
            return false;
        }
        return getId() == exhibit.getId() &&
                getNumber().equals(exhibit.getNumber()) &&
                getName().equals(exhibit.getName()) &&
                getUrl().equals(exhibit.getUrl()) &&
                getPicUrl().equals(exhibit.getPicUrl()) &&
                getCategory().equals(exhibit.getCategory()) &&
                getInfo().equals(exhibit.getInfo()) &&
                getMemo().equals(exhibit.getMemo()) &&
                getGeo().equals(exhibit.getGeo());
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<ExhibitInfo> CREATOR = new Creator<ExhibitInfo>() {
        @Override
        public ExhibitInfo createFromParcel(Parcel in) {
            return new ExhibitInfo(in);
        }

        @Override
        public ExhibitInfo[] newArray(int size) {
            return new ExhibitInfo[size];
        }
    };

    protected ExhibitInfo(Parcel in) {
        mId = in.readInt();
        mNumber = in.readString();
        mName = in.readString();
        mUrl = in.readString();
        mPicUrl = in.readString();
        mCategory = in.readString();
        mInfo = in.readString();
        mMemo = in.readString();
        mGeo = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(mId);
        dest.writeString(mNumber);
        dest.writeString(mName);
        dest.writeString(mUrl);
        dest.writeString(mPicUrl);
        dest.writeString(mCategory);
        dest.writeString(mInfo);
        dest.writeString(mMemo);
        dest.writeString(mGeo);
    }

    @BindingAdapter({"app:imageUrl"})
    public static void loadImage(final ImageView imageView, String imageUrl) {
        Picasso.get()
                .load(imageUrl)
                .into(imageView, new Callback() {
                    @Override
                    public void onSuccess() {
                        Bitmap imageBitmap = ((BitmapDrawable) imageView.getDrawable()).getBitmap();
                        double scaleRatio = Math.min(imageView.getHeight() * 0.8 / imageBitmap.getHeight(),
                                imageView.getWidth() * 0.8 / imageBitmap.getWidth());
                        imageBitmap = Bitmap.createScaledBitmap(imageBitmap, (int)(imageBitmap.getWidth() * scaleRatio),
                                (int)(imageBitmap.getHeight() * scaleRatio), true);
                        RoundedBitmapDrawable imageDrawable = RoundedBitmapDrawableFactory.create(imageView.getResources(), imageBitmap);
                        imageDrawable.setCircular(true);
                        imageDrawable.setCornerRadius((Math.max(imageBitmap.getWidth(), imageBitmap.getHeight()) / 2.0f) -
                        imageView.getResources().getDimensionPixelSize(R.dimen.exhibit_list_pic_padding));
                        imageView.setImageDrawable(imageDrawable);
                    }

                    @Override
                    public void onError(Exception e) {

                    }
                });
    }
}
