package carehalcare.carehalcare_manage.Feature_mainpage;

import com.google.gson.annotations.SerializedName;

public class Notice {
    @SerializedName("content") String content;
    @SerializedName("userId") String userId;
    @SerializedName("createdDate") String createdDate;
    @SerializedName("id") String id;


    public Notice(String content, String userId) {
        this.content = content;
        this.userId = userId;
    }

    public String getContent() {
        return content;
    }
    public String getCreatedDate() {
        return createdDate;
    }
    public String getId() {
        return id;
    }

    public String setCreatedDate(String newDateStr) {
        this.createdDate = createdDate;
        return newDateStr;
    }


}