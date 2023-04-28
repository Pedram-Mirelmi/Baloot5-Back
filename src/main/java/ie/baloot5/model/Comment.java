package ie.baloot5.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

public class Comment {
    private Long commentId;
    private String username;

    @JsonIgnore
    private String userEmail;
    private Long commodityId;
    private String text;
    private String date;
    private int likes = 0;
    private int dislikes = 0;

    public Comment(Long commentId, Long commodityId, String username, String userEmail, String text, String date) {
        this.commentId = commentId;
        this.commodityId = commodityId;
        this.username = username;
        this.userEmail = userEmail;
        this.text = text;
        this.date = date;
    }

    public void addVote(int vote) {
        if(vote == 1) {
            likes++;
        }
        else {
            dislikes++;
        }
    }

    public void removeVote(int vote) {
        if(vote == 1) {
            likes--;
        }
        else {
            dislikes--;
        }
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setCommodityId(Long commodityId) {
        this.commodityId = commodityId;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public Long getCommentId() {
        return commentId;
    }

    public void setCommentId(Long commentId) {
        this.commentId = commentId;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getDate() {
        return date;
    }

    public Long getCommodityId() {
        return commodityId;
    }

    public int getLikes() {
        return likes;
    }

    public void setLikes(int likes) {
        this.likes = likes;
    }

    public int getDislikes() {
        return dislikes;
    }

    public void setDislikes(int dislikes) {
        this.dislikes = dislikes;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }
}