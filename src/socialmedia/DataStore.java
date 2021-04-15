package socialmedia;
import java.util.ArrayList;
import java.io.Serializable;

/**
 * Datastore class used to record all of the platform objects,
 * so they can be saved to a file and read back into the platform.
 */
public class DataStore implements Serializable{
    private int PostID;
    private int AccountID;
    //Recording of id keeping attributes
    private ArrayList<Account> accounts = null;
    private ArrayList<Post> posts = null;
    private ArrayList<Comment> comments = null;
    private ArrayList<Endorsement> endorsements = null;
    //Recording of all arraylist structures

    /**
     * Datastore class constructor. Sets attributes.
     * @param PostID Post ID counter to record.
     * @param AccountID Account ID counter to record.
     * @param accounts Accounts to record.
     * @param posts Posts to record.
     * @param comments Comments to record.
     * @param endorsements Endorsements to record.
     */
    public DataStore(int PostID, int AccountID, ArrayList<Account> accounts, 
    ArrayList<Post> posts, ArrayList<Comment> comments, ArrayList<Endorsement> endorsements){
        setPostID(PostID);
        setAccountID(AccountID);
        setAccounts(accounts);
        setPosts(posts);
        setComments(comments);
        setEndorsements(endorsements);
    }

    //Datastore class setters
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

    //Datastore class getters.
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
}
