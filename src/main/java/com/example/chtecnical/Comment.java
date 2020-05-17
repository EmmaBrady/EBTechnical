package com.example.chtecnical;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.stereotype.Component;

import java.util.Date;

public class Comment {

    private String user;
    private String message;

    @JsonFormat(pattern="yyyy-MM-dd")
    private Date dateCreated;
    private int like;

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Date getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(Date dateCreated) {
        this.dateCreated = dateCreated;
    }

    public int getLike() {
        return like;
    }

    public void setLike(int like) {
        this.like = like;
    }

    @Override
    public String toString() {
        return "user='" + user + '\'' +
                ", message='" + message + '\'' +
                ", dateCreated=" + dateCreated +
                ", like=" + like;
    }
}
