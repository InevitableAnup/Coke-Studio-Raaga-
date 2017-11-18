package in.handmademess.cokestudioraaga.Model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Anup on 18-11-2017.
 */

public class SongsInfo implements Parcelable {

    public String song,url,artists,cover_image;

    public SongsInfo() {
    }

    public SongsInfo(String song, String url, String artists, String cover_image) {
        this.song = song;
        this.url = url;
        this.artists = artists;
        this.cover_image = cover_image;
    }

    public String getSong() {
        return song;
    }

    public void setSong(String song) {
        this.song = song;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getArtists() {
        return artists;
    }

    public void setArtists(String artists) {
        this.artists = artists;
    }

    public String getCover_image() {
        return cover_image;
    }

    public void setCover_image(String cover_image) {
        this.cover_image = cover_image;
    }



    @Override
    public String toString() {
        return "SongsInfo{" +
                "song='" + song + '\'' +
                ", url='" + url + '\'' +
                ", artists='" + artists + '\'' +
                ", cover_image='" + cover_image + '\'' +
                '}';
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.song);
        dest.writeString(this.url);
        dest.writeString(this.artists);
        dest.writeString(this.cover_image);
    }

    protected SongsInfo(Parcel in) {
        this.song = in.readString();
        this.url = in.readString();
        this.artists = in.readString();
        this.cover_image = in.readString();
    }

    public static final Parcelable.Creator<SongsInfo> CREATOR = new Parcelable.Creator<SongsInfo>() {
        @Override
        public SongsInfo createFromParcel(Parcel source) {
            return new SongsInfo(source);
        }

        @Override
        public SongsInfo[] newArray(int size) {
            return new SongsInfo[size];
        }
    };
}
