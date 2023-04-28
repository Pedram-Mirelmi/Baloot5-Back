package ie.baloot5.model;

public class CommentDTO extends Comment{
    final private int usersVote;


    public CommentDTO(Comment comment, int usersVote) {
        super(comment.getCommentId(), comment.getCommodityId(), comment.getUsername(), comment.getText(), comment.getDate());
        this.usersVote = usersVote;
    }

    public int getUsersVote() {
        return usersVote;
    }
}
