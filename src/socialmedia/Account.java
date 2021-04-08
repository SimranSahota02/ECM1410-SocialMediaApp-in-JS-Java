package socialmedia;
import java.util.ArrayList;

/**
 * da class fo da accounts ya get mi
 */
public class Account {
    private int id;
    private String handle;
    private String description;
    private ArrayList<Post> posts = new ArrayList<>();
    private ArrayList<Endorsement> endorsements = new ArrayList<>();
    private ArrayList<Comment> comments = new ArrayList<>();

    public Account(int id, String handle, String description){
        this.id = id;
        this.handle=handle;
        try {
            setDescription(description);
        } catch (Exception ignored) {
            ;
        }
    }

    public int getNumberEndorsements(){
        int endorsecount = 0;
        if(posts != null){
            for(Post post : posts){
                endorsecount += post.getLike();
            }
        }
        
        /*
        if(comments != null){
            for(Comment comment : comments){
                endorsecount += comment.getLike();
            }
        }
        */
        return endorsecount;
    }

    public void setHandle(String handle) {
        this.handle = handle;
    }

    public void setDescription(String description) {
        this.description = description;
    }

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
}
