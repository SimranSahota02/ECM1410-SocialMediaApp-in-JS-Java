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

    public Account(int id, String handle, String description){
        this.id = id;
        this.handle=handle;
        try {
            setDescription(description);
        } catch (Exception ignored) {
            ;
        }
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
}
