package com.ashandevelopment.jwtdeveloptutorials.dto;

public class ProfileDTO {

    private String id;
    private String image;
    private Boolean status;

    public ProfileDTO(String id, Boolean status, String image) {
        this.id = id;
        this.status = status;
        this.image = image;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }
}
