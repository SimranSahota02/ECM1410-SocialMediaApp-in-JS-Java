package socialmedia;
import java.io.Serializable;

public interface Posts extends Serializable{
    
    public void addLike();

    public void removeLike();

    public void setText(String text);

    public int getLike();

    public String getText();    

}
