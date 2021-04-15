package socialmedia;

import java.util.ArrayList;

/**
 * Post class uses Posts interface to store data about individual posts.
 */
public class Post implements Posts{
    private int likes;
    private int postId;
    private String text;
    //Attributes for posts' likes, ID and content text
    private Account account;
    //Account that has made the post
    private ArrayList<Endorsement> endorsements = new ArrayList<>();
    private ArrayList<Comment> comments = new ArrayList<>();
    //Comments and endorsements on the post

    /**
     * Constructor for Post class. Sets variables.
     * @param text Content text of post.
     * @param postId ID of post.
     * @param account Account that made post.
     */
    public Post(String text, int postId, Account account){
        this.likes = 0;
        this.postId = postId;
        this.account = account;
        setText(text);
    }

    //Post class setters
    public void setText(String text){
        this.text = text;
    }

    //Post class getters
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
