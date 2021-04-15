package socialmedia;

import java.io.Serializable;

/**
 * Posts interface. Implemented in posts class.
 */
public interface Posts extends Serializable{
    
    /**
     * The method increments the posts like attribute by +1.
     * Should be called when post is endorsed. Used to record
     * the endorsement count of a post.
     */
    public void addLike();

    /**
     * The method increments the posts like attribute by -1.
     * Should be called when post is endorsed. Used to record
     * the endorsement count of a post.
     */
    public void removeLike();

    /**
     * Method sets the text of the post using passed in parameter.
     * Should be called on post creation.
     */
    public void setText(String text);

    /**
     * Method obtains likes attribute of post. To be called
     * when calculating endorsement totals.
     * @return number of likes on post.
     */
    public int getLike();

    /**
     * The method obtains the text of the post. To be called
     * when printing posts.
     * @return text of post as string.
     */
    public String getText();    

}
