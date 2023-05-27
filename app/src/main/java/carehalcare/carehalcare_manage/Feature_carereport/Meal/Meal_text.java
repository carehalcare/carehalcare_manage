package carehalcare.carehalcare_manage.Feature_carereport.Meal;

import android.graphics.Bitmap;
import android.net.Uri;

public class Meal_text {
    int image;
    Uri photouri;
    String content;
    Bitmap photobitmap;

    Long id;
    String uripan;
    public Meal_text(Uri photouri, String content, Long id, String uripan) {
        this.photouri = photouri;
        this.content = content;
        this.id = id;
        this.uripan = uripan;
    }
    public Meal_text(Bitmap photobitmap, String content, Long id) {
        this.photobitmap = photobitmap;
        this.content = content;
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUripan() {
        return uripan;
    }

    public void setUripan(String uripan) {
        this.uripan = uripan;
    }

    public Bitmap getPhotobitmap() {
        return photobitmap;
    }

    public void setPhotobitmap(Bitmap photobitmap) {
        this.photobitmap = photobitmap;
    }

    public Uri getPhotouri() {
        return photouri;
    }

    public void setPhotouri(Uri photouri) {
        this.photouri = photouri;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }

}
