package socialmedia;
import java.util.ArrayList;
import java.io.Serializable;

/**
 * Account class. Used to store individual accounts details, 
 * including posts, comments and endorsements made by the account,
 * alongside its id handle and description.
 */
public class Account implements Serializable{
    private int id;
    //account id
    private String handle;
    //account handle
    private String description;
    //account description
    private ArrayList<Post> posts = new ArrayList<>();
    private ArrayList<Endorsement> endorsements = new ArrayList<>();
    private ArrayList<Comment> comments = new ArrayList<>();
    //account posts endorsements and comments as typed arraylists
    
    /**
     * Account class constructor.
     * @param id ID to set the for the account
     * @param handle handle to set for the account
     * @param description description to set, if none given, 
     *                    exception is caught and ignored as this can be null
     */
    public Account(int id, String handle, String description){
        setID(id);
        setHandle(handle);
        
        try {
            setDescription(description);
        
        } catch (Exception ignored) {
            ;
        }
        //try to set description but ignore null entry
    }

    //Account class setters
    public void setHandle(String handle) {
        this.handle = handle;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setID(int id) {
        this.id = id;
    }

    //Account class getters
    public int getId() {
        return id;
    }

    public String getHandle() {
        return handle;
    }

    public String getDescription() {
        return description;
    }

    public ArrayList<Post> getPosts() {
        return posts;
    }

    public ArrayList<Endorsement> getEndorsements() {
        return endorsements;
    }

    public ArrayList<Comment> getComments() {
        return comments;
    }
    
    /**
     * Get number of endorsements loops through a classes posts and comments
     * calling getLike() on each to sum the total number of like for the account
     * @return total number of likes on account
     */
    public int getNumberEndorsements(){
        int endorsecount = 0;
        
        if(posts != null){
            
            for(Post post : posts){
                endorsecount += post.getLike();
            }
        }
        //Sum all likes for posts
        if(comments != null){
            
            for(Comment comment : comments){
                endorsecount += comment.getLike();
            }
        }
        //Sum all likes for comments
        return endorsecount;
    }

    
}
