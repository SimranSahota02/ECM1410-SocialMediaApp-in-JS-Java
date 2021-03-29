package socialmedia;
import java.util.ArrayList;

/**
 * da class fo da accounts ya get mi
 */
public class Account {
    
    private int id;
    private String handle;
    private String description;
    private Arraylist<Postmalone> posts = new Arraylist<>();

    public Account(int id, String handle, String description){
        setId(id);
        setHandle(handle);
        try {
            setDescription(description);
        } catch (Exception e) {
            ;
        }
    }

}
