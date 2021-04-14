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
 * @author Diogo Pacheco
 * @version 1.0
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
			platform.createAccount("the_man");
			
			id = platform.createPost("the_man", "This string is a thing");
			assert (id == 1) : "Post id not 1 as expected";
			assert (platform.getNumberOfAccounts() == 1) : "Number of posts registered in the system does not match";
			if(platform.getTotalOriginalPosts() == 1){
				;//System.out.println("Post made");
			}
			
			id = platform.endorsePost("the_man", 0);
			assert (platform.getTotalEndorsmentPosts() == 1) : "Number of endorsements registered in the system does not match";
			assert (id == 1) : "Post id not incremented as expected";
			if(platform.getTotalEndorsmentPosts() == 1){
				;//System.out.println("Endorsement made");
			}
			
			//Deleted if statements that tested these asserts
			//assert (platform.getMostEndorsedAccount() == 0) : "Most endorsed account incorrect ";
			//assert (platform.getMostEndorsedPost() == 0) : "Most endorsed post incorrect";

			platform.erasePlatform();
			platform.createAccount("the_man");
			platform.createAccount("sim_ran");
			platform.createAccount("nish_nish");

			platform.createPost("the_man", "Matthew Auger");
			platform.commentPost("nish_nish", 0, "Matthew Awsome");
			platform.commentPost("sim_ran", 0, "Matthew Ogre");
			platform.commentPost("the_man", 2, "fuckof");
			System.out.println((platform.showPostChildrenDetails(0)).toString());

			//platform.changeAccountHandle("the_man", "siman");
			//System.out.println(platform.showIndividualPost(0));
			//System.out.println((platform.showAccount("the_man")));
			//platform.updateAccountDescription("siman", "big hootin honkers");
			//System.out.println((platform.showAccount("the_man")))
			//platform.savePlatform("shitmonkey");
			//platform.erasePlatform();
			//platform.loadPlatform("shitmonkey");
			//System.out.println((platform.showAccount("siman")));
			
				
		} /*catch (ClassNotFoundException e) {
			assert (false) : "no good";
		} catch (IOException e) {
			System.out.print(e);
			assert (false) : "verily bad";
		} */ catch (HandleNotRecognisedException e) {
			assert (false) : "bad1";
		} catch (InvalidPostException e) {
			assert (false) : "bad2";
		} catch (PostIDNotRecognisedException e) {
			assert (false) : "bad3";
		} catch (NotActionablePostException e) {
			assert (false) : "bad4";
		} catch (IllegalHandleException e) {
			assert (false) : "IllegalHandleException thrown incorrectly";
		} catch (InvalidHandleException e) {
			assert (false) : "InvalidHandleException thrown incorrectly";
		}
	}

}
