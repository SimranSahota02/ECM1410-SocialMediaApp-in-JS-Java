import java.io.IOException;

import socialmedia.AccountIDNotRecognisedException;
import socialmedia.HandleNotRecognisedException;
import socialmedia.SocialMedia;
import socialmedia.IllegalHandleException;
import socialmedia.InvalidHandleException;
import socialmedia.InvalidPostException;
import socialmedia.NotActionablePostException;
import socialmedia.PostIDNotRecognisedException;
import socialmedia.SocialMediaPlatform;

/**
 * A short program to illustrate an app testing some minimal functionality of a
 * concrete implementation of the SocialMediaPlatform interface -- note you will
 * want to increase these checks, and run it on your SocialMedia class (not the
 * BadSocialMedia class).
 *
 * 
 * @author Diogo Pacheco, Simran Sahota, Matthew Auger
 * @version 2.3
 */
public class SocialMediaPlatformTestApp {

	/**
	 * Test method.
	 * 
	 * @param args not used
	 */
	public static void main(String[] args) {
		//System.out.println("The system compiled and started the execution...");

		SocialMediaPlatform platform = new SocialMedia();

		assert (platform.getNumberOfAccounts() == 0) : "Innitial SocialMediaPlatform not empty as required.";
		assert (platform.getTotalOriginalPosts() == 0) : "Innitial SocialMediaPlatform not empty as required.";
		assert (platform.getTotalCommentPosts() == 0) : "Innitial SocialMediaPlatform not empty as required.";
		assert (platform.getTotalEndorsmentPosts() == 0) : "Innitial SocialMediaPlatform not empty as required.";

		Integer id;
		try {
			id = platform.createAccount("my_handle");
			assert (platform.getNumberOfAccounts() == 1) : "number of accounts registered in the system does not match";

			platform.removeAccount(id);
			assert (platform.getNumberOfAccounts() == 0) : "number of accounts registered in the system does not match";

		} catch (IllegalHandleException e) {
			assert (false) : "IllegalHandleException thrown incorrectly";
		} catch (InvalidHandleException e) {
			assert (false) : "InvalidHandleException thrown incorrectly";
		} catch (AccountIDNotRecognisedException e) {
			assert (false) : "AccountIDNotRecognizedException thrown incorrectly";
		}

		try {
			platform.createAccount("user_simran");
			
			id = platform.createPost("user_simran", "This string is a thing");
			assert (id == 1) : "Post id not 1 as expected";
			assert (platform.getNumberOfAccounts() == 1) : "Number of posts registered in the system does not match";
			
			id = platform.endorsePost("user_simran", 0);
			assert (platform.getTotalEndorsmentPosts() == 1) : "Number of endorsements registered in the system does not match";
			assert (id == 1) : "Post id not incremented as expected";
		
			platform.erasePlatform();
			platform.createAccount("user_simran");
			platform.createAccount("user_matt");
			platform.createAccount("user_monkey");

			platform.createPost("user_simran", "Matthew Auger post");
			platform.commentPost("user_monkey", 0, "Matthew Awesome post");
			platform.commentPost("user_matt", 0, "Matthew Ogre post");
			platform.commentPost("user_simran", 2, "Please stop");
			System.out.println((platform.showPostChildrenDetails(0)).toString());
	
		} catch (HandleNotRecognisedException e) {
			assert (false) : "Handle not recognised";
		} catch (InvalidPostException e) {
			assert (false) : "Post was not valid";
		} catch (PostIDNotRecognisedException e) {
			assert (false) : "Post id incorrect";
		} catch (NotActionablePostException e) {
			assert (false) : "Bad post";
		} catch (IllegalHandleException e) {
			assert (false) : "IllegalHandleException thrown incorrectly";
		} catch (InvalidHandleException e) {
			assert (false) : "InvalidHandleException thrown incorrectly";
		}
	}

}
