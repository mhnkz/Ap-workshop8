package model;

import java.io.Serializable;

public class Note implements Serializable {
    private static final long serialVersionUID = 1L;
    private String title;
    private String content;
    private String createdDate;

    public Note(String title, String content, String createdDate) {
        this.title = title;
        this.content = content;
        this.createdDate = createdDate;
    }

    public String getTitle() {
        return title;
    }

    public String getContent() {
        return content;
    }

    public String getCreatedDate() {
        return createdDate;
    }

    @Override
    public String toString() {
        return "Title: " + title + " | Date: " + createdDate;
    }
}