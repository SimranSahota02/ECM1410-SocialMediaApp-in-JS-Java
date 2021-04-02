package socialmedia;

public class Endorsement {

    private String text;
    private int postId;
    private Post parentPost;

    public Endorsement(int postId, Post parentPost){
        this.postId = postId;
        this.parentPost = parentPost;

        setText(("EP@ " + this.parentPost.getAccount().getHandle() + ": " + this.parentPost.getText()).substring(0,100));
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
}