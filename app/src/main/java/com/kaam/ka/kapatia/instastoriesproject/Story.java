package com.kaam.ka.kapatia.instastoriesproject;

import android.os.Parcel;
import android.os.Parcelable;

public class Story implements Parcelable {

    public String profileThumbnail;
    public String videoThumbnail;
    public String username;
    public String questionCategory;
    public String question;
    public String videoUrl;

    public Story(String profileThumbnail, String videoThumbnail, String username, String questionCategory, String question, String videoUrl) {
        this.profileThumbnail = profileThumbnail;
        this.videoThumbnail = videoThumbnail;
        this.username = username;
        this.questionCategory = questionCategory;
        this.question = question;
        this.videoUrl = videoUrl;
    }

    protected Story(Parcel in) {
        profileThumbnail = in.readString();
        videoThumbnail = in.readString();
        username = in.readString();
        questionCategory = in.readString();
        question = in.readString();
        videoUrl = in.readString();
    }

    public static final Creator<Story> CREATOR = new Creator<Story>() {
        @Override
        public Story createFromParcel(Parcel in) {
            return new Story(in);
        }

        @Override
        public Story[] newArray(int size) {
            return new Story[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(profileThumbnail);
        parcel.writeString(videoThumbnail);
        parcel.writeString(username);
        parcel.writeString(questionCategory);
        parcel.writeString(question);
        parcel.writeString(videoUrl);
    }
}
