package carehalcare.carehalcare_manage.Feature_mainpage.Feature_notice;

import com.google.gson.annotations.SerializedName;

//Notice PUT class
public class ChangeNotice {
    @SerializedName("content") private String content;
    @SerializedName("id") private String id;

    public ChangeNotice(String content, String id) {
        this.content = content;
        this.id = id;
    }

    // Getter and Setter methods
    public String getContent() { return content; }
    public void setContent(String content) { this.content = content;}
    public String getId() { return id; }
    public void setId(String id) { this.id = id; }
}
