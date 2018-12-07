package com.timmy.demo.model.server.result.plant;

import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.v4.util.Pair;

import com.google.gson.annotations.SerializedName;
import com.timmy.demo.utils.Constants;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class PlantInfo implements Parcelable, Serializable {

    private static final long serialVersionUID = 876323262645176350L;

    @SerializedName("_id")
    private int mId;
    @SerializedName("F_CID")
    private String mCID;
    @SerializedName("F_Code")
    private String mCode;

    @SerializedName("F_Name_Ch")
    private String mNameCh;
    @SerializedName("F_Name_En")
    private String mNameEn;
    @SerializedName("F_Name_Latin")
    private String mNameLa;
    @SerializedName("F_AlsoKnown")
    private String mAlsoKnown;

    @SerializedName("F_Genus")
    private String mGenus;
    @SerializedName("F_Family")
    private String mFamily;

    @SerializedName("F_Brief")
    private String mBrief;
    @SerializedName("F_Feature")
    private String mFeature;
    @SerializedName("F_Function&Application")
    private String mApplication;
    @SerializedName("F_Summary")
    private String mSummary;

    @SerializedName("F_Location")
    private String mLocation;

    @SerializedName("F_Keywords")
    private String mKeywords;

    @SerializedName("F_Update")
    private String mUpdate;

    @SerializedName("F_Vedio_URL")
    private String mVedioUrl;

    @SerializedName("F_Pic01_URL")
    private String mPic1Url;
    @SerializedName("F_Pic01_ALT")
    private String mPic1Msg;
    @SerializedName("F_Pic02_URL")
    private String mPic2Url;
    @SerializedName("F_Pic02_ALT")
    private String mPic2Msg;
    @SerializedName("F_Pic03_URL")
    private String mPic3Url;
    @SerializedName("F_Pic03_ALT")
    private String mPic3Msg;
    @SerializedName("F_Pic04_URL")
    private String mPic4Url;
    @SerializedName("F_Pic04_ALT")
    private String mPic4Msg;


    @SerializedName("F_pdf01_URL")
    private String mPdf1Url;
    @SerializedName("F_pdf01_ALT")
    private String mPdf1Msg;
    @SerializedName("F_pdf02_URL")
    private String mPdf2Url;
    @SerializedName("F_pdf02_ALT")
    private String mPdf2Msg;


    @SerializedName("F_Voice01_URL")
    private String mVoice1Url;
    @SerializedName("F_Voice01_ALT")
    private String mVoice1Msg;
    @SerializedName("F_Voice02_URL")
    private String mVoice2Url;
    @SerializedName("F_Voice02_ALT")
    private String mVoice2Msg;
    @SerializedName("F_Voice03_URL")
    private String mVoice3Url;
    @SerializedName("F_Voice03_ALT")
    private String mVoice3Msg;

    @SerializedName("F_Geo")
    private String mGeo;

    public int getId() {
        return mId;
    }

    public String getCID() {
        return mCID;
    }

    public String getCode() {
        return mCode;
    }

    public String getTCName() {
        return mNameCh;
    }

    public String getEnName() {
        return mNameEn;
    }

    public String getLatinName() {
        return mNameLa;
    }

    public String getAlsoKnown() {
        return mAlsoKnown;
    }

    public String[] getAlsoKnownList() {
        return mAlsoKnown.split(Constants.PLANT_ALSOKNOWN_SEPARATOR);
    }

    public String getGenus() {
        return mGenus;
    }

    public String getFamily() {
        return mFamily;
    }

    public String getBrief() {
        return mBrief;
    }

    public String getFeature() {
        return mFeature;
    }

    public String getApplication() {
        return mApplication;
    }

    public String getSummary() {
        return mSummary;
    }

    public String getLocation() {
        return mLocation;
    }

    public String[] getLocationList() {
        return mLocation.split(Constants.PLANT_LOCATION_SEPARATOR);
    }

    public String getKeywords() {
        return mKeywords;
    }

    public String getUpdate() {
        return mUpdate;
    }

    public String getVedioUrl() {
        return mVedioUrl;
    }

    public List<Pair<String, String>> getPicInfos() {
        List<Pair<String, String>> result = new ArrayList<>();
        result.add(Pair.create(mPic1Url, mPic1Msg));
        result.add(Pair.create(mPic2Url, mPic2Msg));
        result.add(Pair.create(mPic3Url, mPic3Msg));
        result.add(Pair.create(mPic4Url, mPic4Msg));
        return result;
    }

    public List<Pair<String, String>> getPdfInfos() {
        List<Pair<String, String>> result = new ArrayList<>();
        result.add(Pair.create(mPdf1Url, mPdf1Msg));
        result.add(Pair.create(mPdf2Url, mPdf2Msg));
        return result;
    }

    public List<Pair<String, String>> getVoiceInfos() {
        List<Pair<String, String>> result = new ArrayList<>();
        result.add(Pair.create(mVoice1Url, mVoice1Msg));
        result.add(Pair.create(mVoice2Url, mVoice2Msg));
        result.add(Pair.create(mVoice3Url, mVoice3Msg));
        return result;
    }

    public String getGeo() {
        return mGeo;
    }

    private static boolean compare(List<Pair<String, String>> first,
                                   List<Pair<String, String>> second) {
        if (first == second) {
            return true;
        }

        if (first == null || second == null || first.size() != second.size()) {
            return false;
        }

        for (int index = 0; index < first.size(); index++) {
            if (!first.get(index).first.equals(second.get(index).first) ||
                    !first.get(index).second.equals(second.get(index).second)) {
                return false;
            }
        }
        return true;
    }
    
    @NonNull
    @Override
    public String toString() {
        return String.format(Locale.getDefault(), "Name=%s PIC_URL=%s FAMILY=%s APP=%s BRIEF=%s NO=%s CID=%s",
                mNameCh, mPic1Url, mFamily, mApplication, mBrief, mId, mCID);
    }

    public boolean equals(PlantInfo plantInfo) {
        if (plantInfo == null) {
            return false;
        }

        if (!PlantInfo.compare(getPdfInfos(), plantInfo.getPdfInfos()) ||
                !PlantInfo.compare(getPicInfos(), plantInfo.getPicInfos()) ||
                !PlantInfo.compare(getVoiceInfos(), plantInfo.getVoiceInfos())) {
            return false;
        }

        return getId() == plantInfo.getId() &&
                getCID().equals(plantInfo.getCID()) &&
                getCode().equals(plantInfo.getCode()) &&
                getTCName().equals(plantInfo.getTCName()) &&
                getEnName().equals(plantInfo.getEnName()) &&
                getLatinName().equals(plantInfo.getLatinName()) &&
                getAlsoKnown().equals(plantInfo.getAlsoKnown()) &&
                getGenus().equals(plantInfo.getGenus()) &&
                getFamily().equals(plantInfo.getFamily()) &&
                getBrief().equals(plantInfo.getBrief()) &&
                getFeature().equals(plantInfo.getFeature()) &&
                getApplication().equals(plantInfo.getApplication()) &&
                getSummary().equals(plantInfo.getSummary()) &&
                getLocation().equals(plantInfo.getLocation()) &&
                getLatinName().equals(plantInfo.getLatinName()) &&
                getKeywords().equals(plantInfo.getKeywords()) &&
                getUpdate().equals(plantInfo.getUpdate()) &&
                getVedioUrl().equals(plantInfo.getVedioUrl()) &&
                getGeo().equals(plantInfo.getGeo());
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<PlantInfo> CREATOR = new Creator<PlantInfo>() {
        @Override
        public PlantInfo createFromParcel(Parcel in) {
            return new PlantInfo(in);
        }

        @Override
        public PlantInfo[] newArray(int size) {
            return new PlantInfo[size];
        }
    };

    protected PlantInfo(Parcel in) {
        mId = in.readInt();
        mCID = in.readString();
        mCode = in.readString();
        mNameCh = in.readString();
        mNameEn = in.readString();
        mNameLa = in.readString();
        mAlsoKnown = in.readString();
        mGenus = in.readString();
        mFamily = in.readString();
        mBrief = in.readString();
        mFeature = in.readString();
        mApplication = in.readString();
        mSummary = in.readString();
        mLocation = in.readString();
        mKeywords = in.readString();
        mUpdate = in.readString();
        mVedioUrl = in.readString();
        mPic1Url = in.readString();
        mPic1Msg = in.readString();
        mPic2Url = in.readString();
        mPic2Msg = in.readString();
        mPic3Url = in.readString();
        mPic3Msg = in.readString();
        mPic4Url = in.readString();
        mPic4Msg = in.readString();
        mPdf1Url = in.readString();
        mPdf1Msg = in.readString();
        mPdf2Url = in.readString();
        mPdf2Msg = in.readString();
        mVoice1Url = in.readString();
        mVoice1Msg = in.readString();
        mVoice2Url = in.readString();
        mVoice2Msg = in.readString();
        mVoice3Url = in.readString();
        mVoice3Msg = in.readString();
        mGeo = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(mId);
        dest.writeString(mCID);
        dest.writeString(mCode);
        dest.writeString(mNameCh);
        dest.writeString(mNameEn);
        dest.writeString(mNameLa);
        dest.writeString(mAlsoKnown);
        dest.writeString(mGenus);
        dest.writeString(mFamily);
        dest.writeString(mBrief);
        dest.writeString(mFeature);
        dest.writeString(mApplication);
        dest.writeString(mSummary);
        dest.writeString(mLocation);
        dest.writeString(mKeywords);
        dest.writeString(mUpdate);
        dest.writeString(mVedioUrl);
        dest.writeString(mPic1Url);
        dest.writeString(mPic1Msg);
        dest.writeString(mPic2Url);
        dest.writeString(mPic2Msg);
        dest.writeString(mPic3Url);
        dest.writeString(mPic3Msg);
        dest.writeString(mPic4Url);
        dest.writeString(mPic4Msg);
        dest.writeString(mPdf1Url);
        dest.writeString(mPdf1Msg);
        dest.writeString(mPdf2Url);
        dest.writeString(mPdf2Msg);
        dest.writeString(mVoice1Url);
        dest.writeString(mVoice1Msg);
        dest.writeString(mVoice2Url);
        dest.writeString(mVoice2Msg);
        dest.writeString(mVoice3Url);
        dest.writeString(mVoice3Msg);
        dest.writeString(mGeo);
    }
}
