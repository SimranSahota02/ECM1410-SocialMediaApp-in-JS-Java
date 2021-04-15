package socialmedia;
import java.util.ArrayList;
import java.io.Serializable;

/**
 * Comment class for storing individual information about a comment.
 * Contains two constructor functions for the two cases that a comment
 * can be made with either a parent comment or a parent post.
 */
public class Comment implements Serializable{
    private int likes;
    private int postId;
    private String text;
    //Comment attributes for id, number of likes and comment content
    private Post parentPost;
    private Comment parentComment;
    //Each comment will have either a parent post or parent comment 
    private Account account;
    //Attribute containing account that made the comment
    private ArrayList<Endorsement> endorsements = new ArrayList<>();
    private ArrayList<Comment> comments = new ArrayList<>();
    //Comments endorsements and comments as arraylists

    /**
     * Comment class constructor given parent post. Sets attributes 
     * and initialises likes as 0.
     * @param text Comment content to set.
     * @param postId ID of comment.
     * @param account Account making the comment.
     * @param parentPost Post the comment replies to.
     */
    public Comment(String text, int postId, Account account, Post parentPost){
        this.likes = 0;
        this.postId = postId;
        this.account = account;
        setParentPost(parentPost);
        setText(text);
    }

    /**
     * Comment class constructor given parent comment. Sets attributes 
     * and initialises likes as 0.
     * @param text Comment content to set.
     * @param postId ID of comment.
     * @param account Account making the comment.
     * @param parentPost Comment the comment replies to.
     */
    public Comment(String text, int postId, Account account, Comment parentComment){
        this.likes = 0;
        this.postId = postId;
        this.account = account;
        setParentComment(parentComment);
        setText(text);
    }

    //Comment class setters
    public void setText(String text){
        this.text = text;
    }

    public void setParentPost(Post parentPost) {
        this.parentPost = parentPost;
    }

    public void setParentComment(Comment parentComment) {
        this.parentComment = parentComment;
    }

    //Comment class getters
    public int getLike(){
        return likes;
    }

    public String getText(){
        return this.text;
    }

    public Account getAccount() {
        return account;
    }

    public int getPostId(){
        return postId;
    }

    public ArrayList<Endorsement> getEndorsements() {
        return endorsements;
    }

    public ArrayList<Comment> getComments() {
        return comments;
    }

    //Likes value can only be incremented by +-1, implemented below
    public void addLike(){
        this.likes +=1;
    }

    public void removeLike(){
        this.likes -=1;
    }
}
