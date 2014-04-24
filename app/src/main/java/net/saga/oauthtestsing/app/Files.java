package net.saga.oauthtestsing.app;

import android.os.Parcel;
import android.os.Parcelable;

import org.jboss.aerogear.android.RecordId;

import java.net.URL;

public class Files implements Parcelable{

    @RecordId
    private String id;
    private String title;
    private String webContentLink;
    private String iconLink;

    public Files(){}

    private  Files(Parcel source) {
        id = source.readString();
        title = source.readString();
        webContentLink = source.readString();
        iconLink = source.readString();
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getWebContentLink() {
        return webContentLink;
    }

    public void setWebContentLink(String webContentLink) {
        this.webContentLink = webContentLink;
    }

    public String getIconLink() {
        return iconLink;
    }

    public void setIconLink(String iconLink) {
        this.iconLink = iconLink;
    }

    @Override
    public String toString() {
        return "Files{" +
                "id='" + id + '\'' +
                ", title='" + title + '\'' +
                ", webContentLink='" + webContentLink + '\'' +
                '}';
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeString(title);
        dest.writeString(webContentLink);
        dest.writeString(iconLink);

    }

    public static final Creator<Files> CREATOR = new Creator<Files>() {
        @Override
        public Files createFromParcel(Parcel source) {
            return new Files(source);
        }

        @Override
        public Files[] newArray(int size) {
            return new Files[size];
        }
    };
}
