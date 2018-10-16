package rs.fon.miltek.models;

public class Comment {
    private int commentID;
    private String fullName;
    private int rating;
    private String comment;

    public Comment(int commentID, String fullName, int rating, String comment) {
        this.commentID = commentID;
        this.fullName = fullName;
        this.rating = rating;
        this.comment = comment;
    }

    public int getCommentID() {
        return commentID;
    }

    public void setCommentID(int commentID) {
        this.commentID = commentID;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }
}
