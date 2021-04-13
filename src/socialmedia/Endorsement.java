package socialmedia;

import java.io.Serializable;

public class Endorsement implements Serializable{

    private String text;
    private int postId;
    private Post parentPost;
    private Comment parentComment;

    public Endorsement(int postId, Post parentPost){
        this.postId = postId;
        this.parentPost = parentPost;

        if(this.parentPost.getText().length() >= 94 - this.parentPost.getAccount().getHandle().length()){
            setText(("EP@ " + this.parentPost.getAccount().getHandle() + ": " + this.parentPost.getText()).substring(0,100));
        }
        else{
            setText(("EP@ " + this.parentPost.getAccount().getHandle() + ": " + this.parentPost.getText()));
        }
    }

    public Endorsement(int PostId, Comment parentComment){
        this.postId = postId;
        this.parentComment = parentComment;

        if(this.parentComment.getText().length() >= 94 - this.parentComment.getAccount().getHandle().length()){
            setText(("EP@ " + this.parentComment.getAccount().getHandle() + ": " + this.parentComment.getText()).substring(0,100));
        }
        else{
            setText(("EP@ " + this.parentComment.getAccount().getHandle() + ": " + this.parentComment.getText()));
        }
    }

    public void setText(String text){
        this.text = text;
    }

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