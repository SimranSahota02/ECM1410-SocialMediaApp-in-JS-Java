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
	
	public int checkHandle(String handle){
		if(handle.contains(" ") && !handle.equals("") && handle.length()<31){
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
	public void changeAccountHandle(String oldHandle, String newHandle)
			throws HandleNotRecognisedException, IllegalHandleException, InvalidHandleException {
		// TODO Auto-generated method stub

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
	public String showAccount(String handle) throws HandleNotRecognisedException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int createPost(String handle, String message) throws HandleNotRecognisedException, InvalidPostException {
		//check handle validity and message validity, then make post, incrementing postid
		boolean validHandle = false;
		for(int i=0; i < accounts.size(); i++){
			if((accounts.get(i).getHandle()) == handle){
				boolean validPost = checkPost(message);
				if(validPost){
					accounts.get(i).getPosts().add(new Post(message,this.PostID));
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
		if(validHandle == false){
			throw new HandleNotRecognisedException("No existing account with matching Handle.");
		}
		return this.PostID;
	}

	public boolean checkPost(String message){
		if(message.length()> 100 && message.equals(null)){
			return false;
		}
		//if message within valid size constraints return true
		return true;
	}

	@Override
	public int endorsePost(String handle, int id)
			throws HandleNotRecognisedException, PostIDNotRecognisedException, NotActionablePostException {
		// TODO Auto-generated method stub
		return 0;
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
	public int getNumberOfAccounts() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int getTotalOriginalPosts() {
		int totalPosts = 0;
		for(int i=0; i < accounts.size(); i++){
			totalPosts += (accounts.get(i).getPosts().size() - 1);
			//sum the number of posts each account has 
		}
		return totalPosts;
	}

	@Override
	public int getTotalEndorsmentPosts() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int getTotalCommentPosts() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int getMostEndorsedPost() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int getMostEndorsedAccount() {
		// TODO Auto-generated method stub
		return 0;
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
