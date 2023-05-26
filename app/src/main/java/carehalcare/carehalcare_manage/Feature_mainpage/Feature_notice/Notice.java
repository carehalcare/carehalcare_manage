package carehalcare.carehalcare_manage.Feature_mainpage.Feature_notice;

import com.google.gson.annotations.SerializedName;

public class Notice {
    @SerializedName("content") String content;
    @SerializedName("userId") String userId;
    @SerializedName("modifiedDateTime") String modifiedDateTime;
    @SerializedName("id") String id;


    public Notice(String content, String userId) {
        this.content = content;
        this.userId = userId;
    }

    public String getContent() {
        return content;
    }
    public String getuserId() {return userId;}
    public String getModifiedDate() {
        return modifiedDateTime;
    }
    public String getId() {
        return id;
    }

    public String setModifiedDate(String newDateStr) {
        this.modifiedDateTime = modifiedDateTime;
        return newDateStr;
    }

}

