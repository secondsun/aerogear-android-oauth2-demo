/**
 * JBoss, Home of Professional Open Source
 * Copyright Red Hat, Inc., and individual contributors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * 	http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.jboss.aerogear.android.oauth2.googledrivedemo;

import android.os.Parcel;
import android.os.Parcelable;

import org.jboss.aerogear.android.RecordId;

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
    public void writeToParcel(Parcel destination, int flags) {
        destination.writeString(id);
        destination.writeString(title);
        destination.writeString(webContentLink);
        destination.writeString(iconLink);

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
