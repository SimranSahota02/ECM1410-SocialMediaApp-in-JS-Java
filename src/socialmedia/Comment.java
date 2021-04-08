package socialmedia;

import java.util.ArrayList;

public class Comment {

    private int likes;
    private String text;
    private int postId;
    private Post parentPost;
    private Comment parentComment;
    private Account account;
    private ArrayList<Endorsement> endorsements = new ArrayList<>();
    private ArrayList<Comment> comments = new ArrayList<>();

    public Comment(String text, int postId, Account account, Post parentPost){
        this.likes = 0;
        this.postId = postId;
        this.account = account;
        setParentPost(parentPost);
        setText(text);
    }

    public Comment(String text, int postId, Account account, Comment parentComment){
        this.likes = 0;
        this.postId = postId;
        this.account = account;
        setParentComment(parentComment);
        setText(text);
    }

    public void addLike(){
        this.likes +=1;
    }

    public void removeLike(){
        this.likes -=1;
    }

    public void setText(String text){
        this.text = text;
    }

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

    public void setParentPost(Post parentPost) {
        this.parentPost = parentPost;
    }

    public void setParentComment(Comment parentComment) {
        this.parentComment = parentComment;
    }

    public ArrayList<Endorsement> getEndorsements() {
        return endorsements;
    }

    public ArrayList<Comment> getComments() {
        return comments;
    }
}
