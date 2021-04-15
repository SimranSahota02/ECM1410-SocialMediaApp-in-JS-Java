package socialmedia;

import java.io.Serializable;

/**
 * Endorsement class used to store details about an individual endorsement.
 * Has two constructors, one for endorsing a comment and another for a post.
 */
public class Endorsement implements Serializable{
    private String text;
    private int postId;
    //ID and endorsement text attributes
    private Post parentPost;
    private Comment parentComment;
    //Each endorsement will only have one of a parent post or comment

    /**
     * Endorsement class constructor given endorsing a post.
     * Sets attributes then creates endorsement text.
     * @param postId post id of endorsement.
     * @param parentPost post to be endorsed.
     */
    public Endorsement(int postId, Post parentPost){
        this.postId = postId;
        this.parentPost = parentPost;

        if(parentPost.getText().length() >= 94 - parentPost.getAccount().getHandle().length()){
            setText(("EP@ " + parentPost.getAccount().getHandle() + ": " + parentPost.getText()).substring(0,100));
        }
        //If the parent post has length > 94 it will not fit in the character limit for endorsements.
        //Therefore take substring of parent post and set endorsement text to contain substring instead.
        else{
            setText(("EP@ " + parentPost.getAccount().getHandle() + ": " + parentPost.getText()));
        }
    }

    /**
     * Endorsement class constructor given endorsing a comment.
     * Sets attributes then creates endorsement text.
     * @param postId post id of endorsement.
     * @param parentComment comment to be endorsed.
     */
    public Endorsement(int postId, Comment parentComment){
        this.postId = postId;
        this.parentComment = parentComment;

        if(parentComment.getText().length() >= 94 - parentComment.getAccount().getHandle().length()){
            setText(("EP@ " + parentComment.getAccount().getHandle() + ": " + parentComment.getText()).substring(0,100));
        }
        //If the parent comment has length > 94 it will not fit in the character limit for endorsements.
        //Therefore take substring of parent comment and set endorsement text to contain substring instead.
        else{
            setText(("EP@ " + this.parentComment.getAccount().getHandle() + ": " + this.parentComment.getText()));
        }
    }

    //Endorsement class setters
    public void setText(String text){
        this.text = text;
    }

    //Endorsement class getters
    public String getText(){
        return this.text;
    }

    public int getPostId(){
        return postId;
    }

    public Post getParentPost(){
        return parentPost;
    }

    public Comment getParentComment(){
        return parentComment;
    }
}