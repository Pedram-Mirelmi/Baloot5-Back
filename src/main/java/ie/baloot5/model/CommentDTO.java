package ie.baloot5.model;

public class CommentDTO extends Comment{
    final private int usersVote;


    public CommentDTO(Comment comment, int usersVote) {
        super(comment.getCommentId(), comment.getCommodityId(), comment.getUsername(), comment.getUserEmail(), comment.getText(), comment.getDate());
        setLikes(comment.getLikes());
        setDislikes(comment.getDislikes());
        this.usersVote = usersVote;
    }

    public int getUsersVote() {
        return usersVote;
    }
}
