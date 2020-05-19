package com.chappie.ladang.model;

import android.os.Parcel;
import android.os.Parcelable;

public class Study implements Parcelable {
    private String title;
    private int imgBack;
    private String desc;

    public Study(String title, int imgBack, String desc) {
        this.title = title;
        this.imgBack = imgBack;
        this.desc = desc;
    }

    public String getTitle() {
        return title;
    }

    public int getImgBack() {
        return imgBack;
    }

    public String getDesc() {
        return desc;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.title);
        dest.writeInt(this.imgBack);
        dest.writeString(this.desc);
    }

    protected Study(Parcel in) {
        this.title = in.readString();
        this.imgBack = in.readInt();
        this.desc = in.readString();
    }

    public static final Parcelable.Creator<Study> CREATOR = new Parcelable.Creator<Study>() {
        @Override
        public Study createFromParcel(Parcel source) {
            return new Study(source);
        }

        @Override
        public Study[] newArray(int size) {
            return new Study[size];
        }
    };
}
