package com.example.chtecnical;

import java.util.Arrays;
import java.util.List;

public class Game {

    private int id;
    private String title;
    private String description;
    private String by;
    private String[] platform;
    private int age_rating;
    private int likes;
    private List<Comment> comments;


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getBy() {
        return by;
    }

    public void setBy(String by) {
        this.by = by;
    }

    public String[] getPlatform() {
        return platform;
    }

    public void setPlatform(String[] platform) {
        this.platform = platform;
    }

    public int getAge_rating() {
        return age_rating;
    }

    public void setAge_rating(int age_rating) {
        this.age_rating = age_rating;
    }

    public int getLikes() {
        return likes;
    }

    public void setLikes(int likes) {
        this.likes = likes;
    }

    public List<Comment> getComments() {
        return comments;
    }

    public void setComments(List<Comment> comments) {
        this.comments = comments;
    }

    @Override
    public String toString() {
        return "id=" + id +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", by='" + by + '\'' +
                ", platform=" + Arrays.toString(platform) +
                ", age_rating=" + age_rating +
                ", likes=" + likes;
    }

}
