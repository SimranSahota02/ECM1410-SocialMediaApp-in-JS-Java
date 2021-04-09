package socialmedia;

import java.io.*;
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
	private int AccountID = 0;
	private ArrayList<Account> accounts = new ArrayList<>();
	private ArrayList<Post> posts = new ArrayList<>();
	private ArrayList<Comment> comments = new ArrayList<>();
	private ArrayList<Endorsement> endorsements = new ArrayList<>();
	private final Post deletedPost = new Post("The original content was removed from the system and is no longer available.", -1, null);



	//create and update accounts

	@Override
	public int createAccount(String handle) throws IllegalHandleException, InvalidHandleException {
		if(checkHandle(handle)==1){
			throw new IllegalHandleException("Handle already in use.");
		}
		else if(checkHandle(handle)==2){
			throw new InvalidHandleException("Handle invalid.");
		}
		//check if handle is valid

		accounts.add(new Account(AccountID, handle, null));
		AccountID++;

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

		accounts.add(new Account(AccountID, handle, description));
		AccountID++;

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
				//set new account handle

				for(Endorsement endorsement: endorsements){
					if(endorsement.getParentPost()!=null && endorsement.getParentPost().getAccount().getHandle().equals(oldHandle)) {
						String endorsetext = endorsement.getParentPost().getText();
						//if the handle of the account of the endorsed post equals the old handle, change the text to the new handle
						if(endorsetext.length() >= 94 - newHandle.length()){
							endorsement.setText(("EP@ " + newHandle + ": " + endorsetext).substring(0, 100));
						}
						else{
							endorsement.setText(("EP@ " + newHandle + ": " + endorsetext));
						}
						validHandle = true;
						break;
					}
					else if(endorsement.getParentComment()!=null && endorsement.getParentComment().getAccount().getHandle().equals(oldHandle)) {
						String endorsetext = endorsement.getParentComment().getText();
						//if the handle of the account of the endorsed post equals the old handle, change the text to the new handle
						if(endorsetext.length() >= 94 - newHandle.length()){
							endorsement.setText(("EP@ " + newHandle + ": " + endorsetext).substring(0, 100));
						}
						else{
							endorsement.setText(("EP@ " + newHandle + ": " + endorsetext));
						}
						validHandle = true;
						break;
					}
					//update text of all endorsements for old account posts
				}
				break;
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




	//make posts n such
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

				if(!validPost){
					for (Comment comment : comments) {
						if (comment.getPostId() == id) {
							//for all accounts, if post found in posts add endorsement
							account.getEndorsements().add(new Endorsement(PostID, comment));
							endorsements.add(account.getEndorsements().get(account.getEndorsements().size()-1));
							comment.addLike();
							validPost = true;
							break;
						}
					}
				}
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
	public int commentPost(String handle, int id, String message) throws HandleNotRecognisedException, PostIDNotRecognisedException, NotActionablePostException, InvalidPostException {
		//handle of account commenting, id of post being commented, message of content
		boolean handleValid = false;
		boolean postValid = false;
		boolean notEndorsement = false;
		Account thisAccount = null;
		Post parentPost = null;
		Comment parentComment = null;

		for(Account account: accounts){
			if(account.getHandle().equals(handle)){
				handleValid = true;
				thisAccount=account;
				break;
			}
		}
		if(!handleValid){
			throw new HandleNotRecognisedException("Handle does not exist");
		}

		for(Post post: posts){
			if(post.getPostId()==id){
				postValid=true;
				parentPost = post;
				break;
			}
		}

		for(Comment comment: comments){
			if(comment.getPostId()==id){
				postValid=true;
				parentComment = comment;
				break;
			}
		}

		if(!postValid){
			throw new PostIDNotRecognisedException("Post not real");
		}

		for(Endorsement endorsement: endorsements){
			if(endorsement.getPostId()==id){
				throw new NotActionablePostException("Cant comment endorsements");
			}
		}

		if(!checkPost(message)){
			throw new InvalidPostException("Message greater than 100 characters");
		}

		if(parentPost!=null) {
			thisAccount.getComments().add(new Comment(message, this.PostID, thisAccount, parentPost));
		}
		else {
			thisAccount.getComments().add(new Comment(message, this.PostID, thisAccount, parentComment));
		}
		//if parent post is a post/comment add comment

		comments.add(thisAccount.getComments().get(thisAccount.getComments().size()-1));
		this.PostID +=1;

		return this.PostID-1;
	}




	//getters for stats of platform

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

	@Override
	public int getTotalCommentPosts() {
		return comments.size();
	}

	@Override
	public int getMostEndorsedPost() {
		int id = 0;
		int idlikes = 0;
		if(posts != null){
			for(Post post : posts){
				if(post.getLike() >= idlikes){
					id = post.getPostId();
					idlikes = post.getLike();
				}
			}
		}

		if(comments != null){
			for(Comment comment: comments) {
				if(comment.getLike() >= idlikes){
					id = comment.getPostId();
					idlikes = comment.getLike();
				}
			}
		}

		return id;
	}

	@Override
	public int getMostEndorsedAccount() {
		int id = 0;
		int idlikes = 0;
		if(accounts != null){
			for(Account account : accounts){
				if(account.getNumberEndorsements() >= idlikes){
					id = account.getId();
					idlikes = account.getNumberEndorsements();
				}
			}
		}
		return id;
	}




	//delete posts and accounts

	@Override
	public void deletePost(int id) throws PostIDNotRecognisedException {
		//remove endorsements
		//set comments to "The original content was removed from the system and is no longer available."
		//above best done on platform creation with a null account and -1 post id?
		//need for checks that post id is not -1 for endorsepost and commentpost functions then
		//then remove post
		//as posts in socmed class and in account are same object should only need removing from one location

		boolean foundPost = false;
		Post deletingPost = null;
		Comment deletingComment = null;

		for(Post post: posts){
			if(post.getPostId()==id){
				deletingPost = post;
				foundPost = true;
			}
		}

		if(!foundPost){
			for(Comment comment: comments){
				if(comment.getPostId()==id){
					deletingComment = comment;
					foundPost = true;
				}
			}
		}

		if(!foundPost){
			throw new PostIDNotRecognisedException("Post id not found");
		}

		if(deletingComment==null){
			deletingPost.getEndorsements().clear();
			for(Endorsement endorsement: endorsements){
				if(endorsement.getParentPost().equals(deletingPost)){
					endorsements.remove(endorsement);
				}
			}
			//remove all endorsements

			for(Comment comment: deletingPost.getComments()){
				comment.setParentPost(this.deletedPost);
			}
			//redirect all comments to deleted post

			posts.remove(deletingPost);
			//remove the post
		}
		else{
			deletingComment.getEndorsements().clear();
			for(Endorsement endorsement: endorsements){
				if(endorsement.getParentComment().equals(deletingComment)){
					endorsements.remove(endorsement);
				}
			}
			//remove all endorsements

			for(Comment comment: deletingComment.getComments()){
				comment.setParentPost(this.deletedPost);
				comment.setParentComment(null);
			}
			//redirect all comments to deleted post

			comments.remove(deletingComment);
			//remove the post
		}
	}

	@Override
	public void removeAccount(int id) throws AccountIDNotRecognisedException {
		boolean validId = false;
		for(int i=0; i < accounts.size(); i++){
			if((accounts.get(i).getId()) == id){
				for(Post post: accounts.get(i).getPosts()){
					try{
						deletePost(post.getPostId());
					} catch (PostIDNotRecognisedException ignored){}
				}
				//remove all posts

				for(Comment comment: accounts.get(i).getComments()){
					try{
						deletePost(comment.getPostId());
					} catch (PostIDNotRecognisedException ignored){}
				}
				//remove all comments

				accounts.get(i).setHandle("Deleted account");
				accounts.remove(i);
				validId = true;
				break;
			}
		}
		if(!validId){
			throw new AccountIDNotRecognisedException("No existing account with matching ID.");
		}
	}

	@Override
	public void removeAccount(String handle) throws HandleNotRecognisedException {
		boolean validHandle = false;
		for(int i=0; i < accounts.size(); i++){
			if(accounts.get(i).getHandle().equals(handle)){

				for(Post post: accounts.get(i).getPosts()){
					try{
						deletePost(post.getPostId());
					} catch (PostIDNotRecognisedException ignored){}
				}
				//remove all posts

				for(Comment comment: accounts.get(i).getComments()){
					try{
						deletePost(comment.getPostId());
					} catch (PostIDNotRecognisedException ignored){}
				}
				//remove all comments

				accounts.get(i).setHandle("Deleted account");
				accounts.remove(i);
				validHandle = true;
				break;
			}
		}

		if(!validHandle){
			throw new HandleNotRecognisedException("No existing account with matching Handle.");
		}
	}
	/**
	 loop thru all the endorsements and delet?
	 */




	//save and manage platform

	@Override
	public void savePlatform(String filename) throws IOException {
		try{
			FileOutputStream file = new FileOutputStream(filename + ".ser");
			ObjectOutputStream out = new ObjectOutputStream(file);
			DataStore dataStore = new DataStore(PostID, AccountID, accounts, posts, comments, endorsements);

			// Method for serialization of object
			out.writeObject(dataStore);

			out.close();
			file.close();
		} catch (IOException e){
			throw new IOException(e);
		}
	}

	@Override
	public void loadPlatform(String filename) throws IOException, ClassNotFoundException {
		try {
			// Reading the object from a file
			FileInputStream file = new FileInputStream(filename);
			ObjectInputStream in = new ObjectInputStream(file);
			DataStore dataStore = null;

			// Method for deserialization of object
			dataStore = (DataStore) in.readObject();

			PostID = dataStore.getPostID();
			AccountID = dataStore.getAccountID();
			accounts = dataStore.getAccounts();
			posts = dataStore.getPosts();
			comments = dataStore.getComments();
			endorsements = dataStore.getEndorsements();
			//set the things again

			in.close();
			file.close();
		} catch (IOException ex) {
			throw new IOException("Data store not found");
		} catch (ClassNotFoundException ex) {
			throw new ClassNotFoundException("Data store class not found");
		}

	}

	@Override
	public void erasePlatform() {
		this.PostID = 0;
		this.AccountID=0;
		this.accounts.clear();
		this.posts.clear();
		this.comments.clear();
		this.endorsements.clear();
	}
	/**
	 does this need to use deletePost() method?
	 */




	//Show info on stuff

	@Override
	public String showAccount(String handle) throws HandleNotRecognisedException {
		//get account
		//id, handle, desc, post count, endorse count. each on a new line
		Account shownAccount = null;
		StringBuilder details = new StringBuilder();

		for(Account account: accounts){
			if(account.getHandle().equals(handle)){
				shownAccount = account;
			}
		}
		if(shownAccount==null){
			throw new HandleNotRecognisedException("Handle not recognised");
		}
		//verify account exists and set account

		details.append("ID: ").append(shownAccount.getId()).append("\n");
		details.append("Handle: ").append(shownAccount.getHandle()).append("\n");
		details.append("Description: ").append(shownAccount.getDescription()).append("\n");
		details.append("Post count: ").append(shownAccount.getPosts().size()).append("\n");
		details.append("Endorse count: ").append(shownAccount.getNumberEndorsements());
		//add details of account to stringbuilder

		return details.toString();
	}

	@Override
	public String showIndividualPost(int id) throws PostIDNotRecognisedException {
		//get post
		//id, account, no. endorsements, message
		Post shownPost = null;
		Comment shownComment = null;
		StringBuilder details = new StringBuilder();

		for(Post post: posts){
			if(post.getPostId()==id){
				shownPost = post;
				break;
			}
		}
		if(shownPost==null){
			for(Comment comment: comments){
				if(comment.getPostId()==id){
					shownComment = comment;
					break;
				}
			}
			if(shownComment==null){
				throw new PostIDNotRecognisedException("Post not found by ID");
			}
		}
		//verify post/comment exists and set post/comment

		if(shownPost!=null) {
			details.append("ID: ").append(shownPost.getPostId()).append("\n");
			details.append("Account: ").append(shownPost.getAccount().getHandle()).append("\n");
			details.append("No. endorsements: ").append(shownPost.getEndorsements().size()).append("\n");
			details.append(shownPost.getText());
		}
		else {
			details.append("ID: ").append(shownComment.getPostId()).append("\n");
			details.append("Account: ").append(shownComment.getAccount().getHandle()).append("\n");
			details.append("No. endorsements: ").append(shownComment.getEndorsements().size()).append("\n");
			details.append(shownComment.getText());
		}
		//add details of account to stringbuilder

		return details.toString();
	}

	@Override
	public StringBuilder showPostChildrenDetails(int id) throws PostIDNotRecognisedException, NotActionablePostException {
		//get post/ comment with such ID
		//get all immediate comments of this post
		//call this function recursively to get all children children
		//when null, add last one to stringbuilder in format
		//recursive is why it returns stringbuilder

		return null;
	}




	//Functions we created

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
}