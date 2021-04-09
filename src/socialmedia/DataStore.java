package socialmedia;

import java.util.ArrayList;

public class DataStore {

    private int PostID;
    private int AccountID;
    private ArrayList<Account> accounts = null;
    private ArrayList<Post> posts = null;
    private ArrayList<Comment> comments = null;
    private ArrayList<Endorsement> endorsements = null;

    public DataStore(int PostID, int AccountID, ArrayList<Account> accounts, ArrayList<Post> posts, ArrayList<Comment> comments, ArrayList<Endorsement> endorsements){
        setPostID(PostID);
        setAccountID(AccountID);
        setAccounts(accounts);
        setPosts(posts);
        setComments(comments);
        setEndorsements(endorsements);
    }

    public int getPostID() {
        return PostID;
    }

    public int getAccountID() {
        return AccountID;
    }

    public ArrayList<Account> getAccounts() {
        return accounts;
    }

    public ArrayList<Post> getPosts() {
        return posts;
    }

    public ArrayList<Comment> getComments() {
        return comments;
    }

    public ArrayList<Endorsement> getEndorsements() {
        return endorsements;
    }

    public void setPostID(int postID) {
        this.PostID = postID;
    }

    public void setAccountID(int accountID) {
        AccountID = accountID;
    }

    public void setAccounts(ArrayList<Account> accounts) {
        this.accounts = accounts;
    }

    public void setPosts(ArrayList<Post> posts) {
        this.posts = posts;
    }

    public void setComments(ArrayList<Comment> comments) {
        this.comments = comments;
    }

    public void setEndorsements(ArrayList<Endorsement> endorsements) {
        this.endorsements = endorsements;
    }
}
