package carehalcare.carehalcare_manage.Feature_carereport.Allmenu;

public class BoardResponseDto {
    private Long id;
    private String userId;
    private String puserId;
    private String category;
    private String createdDateTime;

    public BoardResponseDto(Long id, String userId, String puserId, String category, String createdDateTime) {
        this.id = id;
        this.userId = userId;
        this.puserId = puserId;
        this.category = category;
        this.createdDateTime = createdDateTime;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getPuserId() {
        return puserId;
    }

    public void setPuserId(String puserId) {
        this.puserId = puserId;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getCreatedDateTime() {
        return createdDateTime;
    }

    public void setCreatedDateTime(String createdDateTime) {
        this.createdDateTime = createdDateTime;
    }
}
