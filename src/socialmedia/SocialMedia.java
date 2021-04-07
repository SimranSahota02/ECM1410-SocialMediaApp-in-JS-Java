package socialmedia;

import java.io.IOException;
import java.util.ArrayList;

/**
 * BadSocialMedia is a minimally compiling, but non-functioning implementor of
 * the SocialMediaPlatform interface.
 * 
 * @author Diogo Pacheco
 * @version 1.0
 */
public class SocialMedia implements SocialMediaPlatform {

	private int PostID = 0;
	private ArrayList<Account> accounts = new ArrayList<>();
	private ArrayList<Post> posts = new ArrayList<>();
	private ArrayList<Comment> comments = new ArrayList<>();
	private ArrayList<Endorsement> endorsements = new ArrayList<>();

	//Done functions
	//
	//
	//
	//
	//
	//
	//
	//
	//
	
	@Override
	public int createAccount(String handle) throws IllegalHandleException, InvalidHandleException {
		if(checkHandle(handle)==1){
			throw new IllegalHandleException("Handle already in use.");
		}
		else if(checkHandle(handle)==2){
			throw new InvalidHandleException("Handle invalid.");
		}
		//check if handle is valid
		if(accounts.isEmpty()) {
			accounts.add(new Account(0, handle, null));
		}
		else {
			accounts.add(new Account(accounts.size(), handle, null));
		}
		//assign account ID
		return accounts.get(accounts.size()-1).getId();
		//return Account ID
	}

	@Override
	public int createAccount(String handle, String description) throws IllegalHandleException, InvalidHandleException {
		if(checkHandle(handle)==1) {
			throw new IllegalHandleException("Handle already in use.");
		}
		else if(checkHandle(handle)==2) {
			throw new InvalidHandleException("Handle invalid.");
		}

		if(accounts.isEmpty()) {
			accounts.add(new Account(0, handle, description));
		}
		else {
			accounts.add(new Account(accounts.size(), handle, description));
		}
		return accounts.get(accounts.size()-1).getId();
	}
	

	@Override
	public void changeAccountHandle(String oldHandle, String newHandle) throws HandleNotRecognisedException, IllegalHandleException, InvalidHandleException {
		if(checkHandle(newHandle)==1) {
			throw new IllegalHandleException("Handle already in use.");
		}
		else if(checkHandle(newHandle)==2) {
			throw new InvalidHandleException("Handle invalid.");
		}
		//check new handle is valid
		boolean validHandle = false;
		for(Account account: accounts){
			if(account.getHandle().equals(oldHandle)){
				account.setHandle(newHandle);
				for(Endorsement endorsement: endorsements){
					if(endorsement.getParentPost().getAccount().getHandle().equals(oldHandle)) {
						String endorsetext = endorsement.getParentPost().getText();
						//if the handle of the account of the endorsed post equals the old handle, change the text to the new handle
						if(endorsetext.length() >= 94 - newHandle.length()){
							endorsement.setText(("EP@ " + newHandle + ": " + endorsetext).substring(0, 100));
						}
						else{
							endorsement.setText(("EP@ " + newHandle + ": " + endorsetext));
						}
						validHandle = true;
					}
				}
			}
		}
		if(!validHandle){
			throw new HandleNotRecognisedException("Handle not in use");
		}
	}

	@Override
	public void updateAccountDescription(String handle, String description) throws HandleNotRecognisedException {
		boolean validHandle = false;
		for(int i=0; i < accounts.size(); i++){
			if((accounts.get(i).getHandle()) == handle){
				accounts.get(i).setDescription(description);
				validHandle = true;
				break;
			}
		}
		if(validHandle == false){
			throw new HandleNotRecognisedException("No existing account with matching Handle.");
		}
	}

	@Override
	public int createPost(String handle, String message) throws HandleNotRecognisedException, InvalidPostException {
		//check handle validity and message validity, then make post, incrementing postid
		boolean validHandle = false;
		for(int i=0; i < accounts.size(); i++){
			if(accounts.get(i).getHandle().equals(handle)){
				boolean validPost = checkPost(message);
				if(validPost){
					accounts.get(i).getPosts().add(new Post(message,this.PostID, accounts.get(i)));
					posts.add(accounts.get(i).getPosts().get(accounts.get(i).getPosts().size()-1));
					this.PostID +=1;
					//given valid post and handle make post within users posts and add 1 to post id
				}
				else{
					throw new InvalidPostException("Post is not within valid size constraints.");
				}
				validHandle = true;
				break;
			}
		}
		if(!validHandle){
			throw new HandleNotRecognisedException("No existing account with matching Handle.");
		}
		return this.PostID;
	}

	@Override
	public int getNumberOfAccounts() {
		return accounts.size();
	}

	@Override
	public int getTotalOriginalPosts() {
		return posts.size();
	}

	@Override
	public int getTotalEndorsmentPosts() {
		return endorsements.size();
	}

	//Functions we created
	//
	//
	//
	//
	//
	//
	//
	//
	//

	public boolean checkPost(String message){
		if(message.length() < 100 && !(message.equals(null))){
			return true;
		}
		//if message within valid size constraints return true
		return false;
	}

	public int checkHandle(String handle){
		if(!handle.contains(" ") && !handle.equals("") && handle.length()<31){
			for(Account account: accounts){
				if(account.getHandle().equals(handle)){
					return 1;
					//if handle occurs in any existing accounts, return 1
				}
			}
			return 0;
			//if handle is valid and doesnt exist, return 0
		}
		else
			return 2;
		//if handle invalid, return 2
	}

	//Not finished functions
	//
	//
	//
	//
	//
	//
	//
	//
	//

	@Override
	public void removeAccount(int id) throws AccountIDNotRecognisedException {
		boolean validId = false;
		for(int i=0; i < accounts.size(); i++){
			if((accounts.get(i).getId()) == id){
				//REMOVE POSTS HERE ALSO AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA
				accounts.get(i).setHandle("Deleted account");
				accounts.remove(i);
				validId = true;
				break;
			}
		}
		if(validId == false){
			throw new AccountIDNotRecognisedException("No existing account with matching ID.");
		}
	}

	@Override
	public void removeAccount(String handle) throws HandleNotRecognisedException {
		boolean validHandle = false;
		for(int i=0; i < accounts.size(); i++){
			if((accounts.get(i).getHandle()) == handle){
				//REMOVE POSTS HERE ALSO AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA
				accounts.get(i).setHandle("Deleted account");
				accounts.remove(i);
				validHandle = true;
				break;
			}
		}
		if(validHandle == false){
			throw new HandleNotRecognisedException("No existing account with matching Handle.");
		}
	}

	@Override
	public int endorsePost(String handle, int id) throws HandleNotRecognisedException, PostIDNotRecognisedException, NotActionablePostException {
		//inputs handle of account endorsing post, id of post being endorsed

		//postid, parent post
		//id of new endorsement post, parent post being endorsed

		//check handle validity and message validity, then make post, incrementing postid
		boolean validHandle = false;
		for (Account account : accounts) {
			if (account.getHandle().equals(handle)) {
				//for all accounts, if account found
				validHandle = true;
				boolean validPost = false;
				for (Post post : posts) {
					if (post.getPostId() == id) {
						//for all accounts, if post found in posts add endorsement
						account.getEndorsements().add(new Endorsement(PostID, post));
						endorsements.add(account.getEndorsements().get(account.getEndorsements().size()-1));
						post.addLike();
						validPost = true;
						break;
					}
				}
				//loop thru all comments, if comment == post id add endorsement
				//if not valid post - so if post found in posts we dont loop
				/*for (Comment comment : comments) {
					if (comment.getPostId() == id) {
						//for all accounts, if post found in posts add endorsement
						account.getEndorsements().add(new Endorsement(PostID, comment));
						comment.addLike();
						validPost = true;
						break;
					}
				}*/

				//if neither post or comment then throw not actioanable post exception
				if (!validPost) {
					throw new NotActionablePostException("Post cannot be endorsed as it is an invalid type");
				}
				break;
			}
		}
		if(!validHandle){
			throw new HandleNotRecognisedException("No existing account with matching Handle.");
		}
		return this.PostID;
	}	

	@Override
	public int getMostEndorsedPost() {
		int id = 0;
		int idlikes = 0;
		for(Post post : posts){
			if(post.getLike() >= idlikes){
				id = post.getPostId();
				idlikes = post.getLike();
			}
		}
		/* for(Comment comment: comments) {
			if(comment.getlike() >= idlikes){
				id = comment.getPostId();
				idlikes = comment.getlike();
			}
		}*/
		return id;
	}

	@Override
	public int getMostEndorsedAccount() {
		int id = 0;
		int idlikes = 0;
		for(Account account : accounts){
			if(account.getNumberEndorsements() >= idlikes){
				id = account.getId();
				idlikes = account.getNumberEndorsements();
			}
		}
		//CHECK COMMENTS HERE AND RETURN HIGHEST OF ALL
		return id;
	}

	//Not started functions
	//
	//
	//
	//
	//
	//
	//
	//
	//

	@Override
	public int getTotalCommentPosts() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public String showAccount(String handle) throws HandleNotRecognisedException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int commentPost(String handle, int id, String message) throws HandleNotRecognisedException,
			PostIDNotRecognisedException, NotActionablePostException, InvalidPostException {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void deletePost(int id) throws PostIDNotRecognisedException {
		// TODO Auto-generated method stub

	}

	@Override
	public String showIndividualPost(int id) throws PostIDNotRecognisedException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public StringBuilder showPostChildrenDetails(int id)
			throws PostIDNotRecognisedException, NotActionablePostException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void erasePlatform() {
		// TODO Auto-generated method stub

	}

	@Override
	public void savePlatform(String filename) throws IOException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void loadPlatform(String filename) throws IOException, ClassNotFoundException {
		// TODO Auto-generated method stub

	}

}
