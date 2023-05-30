package carehalcare.carehalcare_manage.Feature_carereport.Walk;
import android.graphics.Bitmap;
import android.net.Uri;

public class Walk_text {
    int image;
    Uri photouri;
    Bitmap photobitmap;

    Long id;
    String uripan;
    public Walk_text(Uri photouri, Long id, String uripan) {
        this.photouri = photouri;
        this.id = id;
        this.uripan = uripan;
    }
    public Walk_text(Bitmap photobitmap, Long id) {
        this.photobitmap = photobitmap;
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


    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }
}
