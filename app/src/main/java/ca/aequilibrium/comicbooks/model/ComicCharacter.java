package ca.aequilibrium.comicbooks.model;

import android.graphics.Bitmap;

/**
 * Created by Azizur on 19/10/2016.
 */

public class ComicCharacter {
    private String title;
    private String detail;
    private String characterURL;
    private Bitmap thumbnail;

    public ComicCharacter(String title, String detail, String characterURL){
        this.title=title;
        this.detail=detail;
        this.characterURL=characterURL;
    }


    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    public String getCharacterURL() {
        return characterURL;
    }

    public void setCharacterURL(String characterURL) {
        this.characterURL = characterURL;
    }

    public Bitmap getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(Bitmap thumbnail) {
        this.thumbnail = thumbnail;
    }

}
