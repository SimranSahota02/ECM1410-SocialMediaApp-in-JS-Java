package socialmedia;

import java.io.*;
import java.util.ArrayList;

/**
 * SocialMedia is a fully-functioning implementor of
 * the SocialMediaPlatform interface.
 * 
 * @author Diogo Pacheco
 * @version 1.0
 */
public class SocialMedia implements SocialMediaPlatform {
	private int PostID = 0;
	private int AccountID = 0;
	//Attributes to keep track of the current post id and account id to use
	private ArrayList<Account> accounts = new ArrayList<>();
	private ArrayList<Post> posts = new ArrayList<>();
	private ArrayList<Comment> comments = new ArrayList<>();
	private ArrayList<Endorsement> endorsements = new ArrayList<>();
	//Arraylists of each object type within the platform
	private final Post deletedPost = 
	new Post("The original content was removed from the system and is no longer available.", -1, null);
	//Deleted Post attribute stores the post content that must replace a post when one is deleted
	private int indent = 0;
	//Indent attribute used for recursive printing function indentation

	/**
	 * Social media class constructor. Used to create empty
	 * platform.
	 * Sets variables to 0 or null where applicable.
	 */
	public SocialMedia() {
		this.PostID = 0;
		this.AccountID = 0;
		this.indent = 0;
		//Set attributes to 0
	}

	//Platform statistic getters *********************************************
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
		//Loop through all posts to find most endorsed post
		if(comments != null){
			
			for(Comment comment: comments) {
			
				if(comment.getLike() >= idlikes){
					id = comment.getPostId();
					idlikes = comment.getLike();
				}
			}
		}
		//Loop through comments to check for a more endorsed comment
		return id;
	}

	@Override
	public int getMostEndorsedAccount() {
		int id = 0;
		int idlikes = 0;
		//Idlikes is used to keep track of the highest endorsement count so far
		if(accounts != null){
			
			for(Account account : accounts){
				
				if(account.getNumberEndorsements() >= idlikes){
					id = account.getId();
					idlikes = account.getNumberEndorsements();
				}
			}
			//Loop all accounts to find account with most endorsements
		}
		return id;
	}

	//Create and update accounts ********************************************
	@Override
	public int createAccount(String handle) throws IllegalHandleException, InvalidHandleException {
		
		if(checkHandle(handle)==1){
			throw new IllegalHandleException("Handle already in use.");
		}
		else if(checkHandle(handle)==2){
			throw new InvalidHandleException("Handle format invalid.");
		}
		//Check if handle is valid and if not throw exceptions.
		accounts.add(new Account(AccountID, handle, null));
		AccountID++;
		//Create new account and increment account id
		return accounts.get(accounts.size()-1).getId();
	}

	@Override
	public int createAccount(String handle, String description) throws IllegalHandleException, 
	InvalidHandleException {
		
		if(checkHandle(handle)==1) {
			throw new IllegalHandleException("Handle already in use.");
		}
		else if(checkHandle(handle)==2) {
			throw new InvalidHandleException("Handle invalid.");
		}
		//Check if handle is valid and if not throw exceptions.
		accounts.add(new Account(AccountID, handle, description));
		AccountID++;
		//Create new account with description and increment account id
		return accounts.get(accounts.size()-1).getId();
	}

	@Override
	public void changeAccountHandle(String oldHandle, String newHandle) throws 
	HandleNotRecognisedException, IllegalHandleException, InvalidHandleException {
		
		if(checkHandle(newHandle)==1) {
			throw new IllegalHandleException("Handle already in use.");
		}
		else if(checkHandle(newHandle)==2) {
			throw new InvalidHandleException("Handle invalid.");
		}
		//Check new handle is valid and if not throw exceptions.
		boolean validHandle = false;

		for(Account account: accounts){

			if(account.getHandle().equals(oldHandle)){
				account.setHandle(newHandle);
				validHandle = true;
				//If old handle found replace with new handle
				for(Endorsement endorsement: endorsements){
					
					if(endorsement.getParentPost()!=null && 
					endorsement.getParentPost().getAccount().getHandle().equals(oldHandle)) {
						String endorsetext = endorsement.getParentPost().getText();
						//If endorsement uses old handle, update text to use new handle
						if(endorsetext.length() >= 94 - newHandle.length()){
							endorsement.setText(("EP@ " + newHandle + ": " + endorsetext).substring(0, 100));
						}
						else{
							endorsement.setText(("EP@ " + newHandle + ": " + endorsetext));
						}
						break;
					}
					//Update relevant endorsements with parent comments
					else if(endorsement.getParentComment()!=null && 
					endorsement.getParentComment().getAccount().getHandle().equals(oldHandle)) {
						String endorsetext = endorsement.getParentComment().getText();
						//If endorsement uses old handle, update text to use new handle
						if(endorsetext.length() >= 94 - newHandle.length()){
							endorsement.setText(("EP@ " + newHandle + ": " + endorsetext).substring(0, 100));
						}
						else{
							endorsement.setText(("EP@ " + newHandle + ": " + endorsetext));
						}
						break;
					}
					//update relevant endorsements with parent posts
				}
				break;
			}
			//If account found, update handle in all relevant locations
		}
		
		if(!validHandle){
			throw new HandleNotRecognisedException("Handle not in use");
		}
		//If old handle is not located throw exception.
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
			//If account found update description
		}
		if(validHandle == false){
			throw new HandleNotRecognisedException("No existing account with matching Handle.");
		}
		//If account not found throw exception.
	}

	//Create posts **************************************************************
	@Override
	public int createPost(String handle, String message) throws HandleNotRecognisedException, 
	InvalidPostException {
		boolean validHandle = false;
		
		for(int i=0; i < accounts.size(); i++){
			
			if(accounts.get(i).getHandle().equals(handle)){
				boolean validPost = checkPost(message);
			
				if(validPost){
					accounts.get(i).getPosts().add(new Post(message,this.PostID, accounts.get(i)));
					posts.add(accounts.get(i).getPosts().get(accounts.get(i).getPosts().size()-1));
					this.PostID +=1;
					//Given valid post and handle make post and append post to platform arraylist
				}
				else{
					throw new InvalidPostException("Post is not within valid size constraints.");
					//If post is not valid format throw exception
				}
				validHandle = true;
				break;
			}
		}
		//If account exists attempt to make post
		if(!validHandle){
			throw new HandleNotRecognisedException("No existing account with matching Handle.");
		}
		//If account was not found throw exception
		return this.PostID;
	}

	@Override
	public int endorsePost(String handle, int id) throws HandleNotRecognisedException, 
	PostIDNotRecognisedException, NotActionablePostException {
		boolean validHandle = false;
		
		for (Account account : accounts) {
			
			if (account.getHandle().equals(handle)) {
				validHandle = true;
				boolean validPost = false;
				
				for (Post post : posts) {
					
					if (post.getPostId() == id) {
						account.getEndorsements().add(new Endorsement(PostID, post));
						endorsements.add(account.getEndorsements().get(account.getEndorsements().size()-1));
						//Given post to endorse found and account valid make endorsement
						post.addLike();
						//Increment endorsed posts' likes
						validPost = true;
						break;
					}
				}
				//Loop through posts to find endorsable post
				if(!validPost){
					
					for (Comment comment : comments) {
						
						if (comment.getPostId() == id) {
							account.getEndorsements().add(new Endorsement(PostID, comment));
							endorsements.add(account.getEndorsements().get(account.getEndorsements().size()-1));
							//Given comment to endorse found and account valid make endorsement
							comment.addLike();
							//Increment endorsed comments' likes
							validPost = true;
							break;
						}
					}
				}
				//Given post was not found search comments to find endorsable comment
				if (!validPost) {
					throw new NotActionablePostException("Post cannot be endorsed as it is an invalid type");
				}
				//If no post found throw post exception.
				break;
			}
		}
		//Loop through each account to find account making endorsement
		if(!validHandle){
			throw new HandleNotRecognisedException("No existing account with matching Handle.");
		}
		//If no matching handle throw account error
		return this.PostID;
	}

	@Override
	public int commentPost(String handle, int id, String message) throws HandleNotRecognisedException, 
	PostIDNotRecognisedException, NotActionablePostException, InvalidPostException {
		boolean handleValid = false;
		boolean postValid = false;
		Account thisAccount = null;
		Post parentPost = null;
		Comment parentComment = null;
		//Set all attributes for validation to false and others to null

		for(Account account: accounts){
			
			if(account.getHandle().equals(handle)){
				handleValid = true;
				thisAccount=account;
				break;
			}
		}
		//Loop through accounts to locate account
		if(!handleValid){
			throw new HandleNotRecognisedException("Handle does not exist");
		}
		//No account found then throw exception
		for(Post post: posts){
			
			if(post.getPostId()==id){
				postValid=true;
				parentPost = post;
				break;
			}
		}
		//Loop through all posts to find parent id
		for(Comment comment: comments){
			if(comment.getPostId()==id){
				postValid=true;
				parentComment = comment;
				break;
			}
		}
		//Loop through all comments to find parent id
		if(!postValid){
			throw new PostIDNotRecognisedException("Post not real");
		}
		//If neither parent comment or post found throw exception
		for(Endorsement endorsement: endorsements){
			
			if(endorsement.getPostId()==id){
				throw new NotActionablePostException("Cant comment endorsements");
			}
		}
		//If post to comment found in endorsements arraylist throw exception
		if(!checkPost(message)){
			throw new InvalidPostException("Message greater than 100 characters");
		}
		//If comment not in post boundaries throw exception
		if(parentPost!=null) {
			thisAccount.getComments().add(new Comment(message, this.PostID, thisAccount, parentPost));
			parentPost.getComments().add(thisAccount.getComments().get(thisAccount.getComments().size()-1));
			//If parent post make comment using post constructor
		}
		else {
			thisAccount.getComments().add(new Comment(message, this.PostID, thisAccount, parentComment));
			parentComment.getComments().add(thisAccount.getComments().get(thisAccount.getComments().size()-1));
			//Else make comment using comment constructor
		}
		comments.add(thisAccount.getComments().get(thisAccount.getComments().size()-1));
		this.PostID +=1;
		//Append comment to comment arraylist and increment post id
		return this.PostID-1;
	}

	//Delete posts and accounts **************************************************************
	@Override
	public void deletePost(int id) throws PostIDNotRecognisedException {
		boolean foundPost = false;
		Post deletingPost = null;
		Comment deletingComment = null;

		for(Post post: posts){
			
			if(post.getPostId()==id){
				deletingPost = post;
				foundPost = true;
			}
		}
		//Loop through all posts to locate post to remove
		if(!foundPost){
			for(Comment comment: comments){
				if(comment.getPostId()==id){
					deletingComment = comment;
					foundPost = true;
				}
			}
		}
		//If post not yet found loop through comments
		if(!foundPost){
			throw new PostIDNotRecognisedException("Post id not found");
		}
		//If post not found in either throw error
		if(deletingComment==null){
			deletingPost.getEndorsements().clear();
			for(Endorsement endorsement: endorsements){
				
				if(endorsement.getParentPost().equals(deletingPost)){
					endorsements.remove(endorsement);
				}
			}
			//Clear endorsements related to post to delete in all locations
			for(Comment comment: deletingPost.getComments()){
				comment.setParentPost(this.deletedPost);
			}
			//Redirect all comments related to post to deleted post object
			posts.remove(deletingPost);
			//Remove the post
		}
		//If post to delete was located to not be a comment, delete with post functions
		else{
			deletingComment.getEndorsements().clear();
			
			for(Endorsement endorsement: endorsements){
				
				if(endorsement.getParentComment().equals(deletingComment)){
					endorsements.remove(endorsement);
				}
			}
			//Clear endorsements related to comment to delete in all locations
			for(Comment comment: deletingComment.getComments()){
				comment.setParentPost(this.deletedPost);
				comment.setParentComment(null);
			}
			//Redirect all comments related to comment to deleted post object
			comments.remove(deletingComment);
			//Remove the comment
		}
		//Else post to delete is a comment, delete with comment functions
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
				//If account id found remove all posts on account
				for(Comment comment: accounts.get(i).getComments()){
					
					try{
						deletePost(comment.getPostId());
					} catch (PostIDNotRecognisedException ignored){}
				}
				//Then remove all comments on account
				for(Endorsement endorsement: accounts.get(i).getEndorsements()){
					endorsement.getParentPost().removeLike();
					endorsements.remove(endorsement);
				}
				//Then remove all endorsements
				accounts.get(i).setHandle("Deleted account");
				accounts.remove(i);
				//Set the handle to deleted account and remove from platform
				validId = true;
				break;
			}
		}
		//Loop through accounts to find account to delete by id
		if(!validId){
			throw new AccountIDNotRecognisedException("No existing account with matching ID.");
		}
		//If account id not found throw exception
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
				//If account handle found remove all posts on account
				for(Comment comment: accounts.get(i).getComments()){
					
					try{
						deletePost(comment.getPostId());
					} catch (PostIDNotRecognisedException ignored){}
				}
				//Then remove all comments on account
				for(Endorsement endorsement: accounts.get(i).getEndorsements()){
					endorsement.getParentPost().removeLike();
					endorsements.remove(endorsement);
				}
				//Then remove all endorsements
				accounts.get(i).setHandle("Deleted account");
				accounts.remove(i);
				//Set the handle to deleted account and remove from platform
				validHandle = true;
				break;
			}
		}
		//Loop through accounts to find account to delete by handle
		if(!validHandle){
			throw new HandleNotRecognisedException("No existing account with matching Handle.");
		}
		//If account handle not found throw exception
	}

//Display functions ***********************************************************
@Override
public String showAccount(String handle) throws HandleNotRecognisedException {
	Account shownAccount = null;
	StringBuilder details = new StringBuilder();
	
	for(Account account: accounts){
		
		if(account.getHandle().equals(handle)){
			shownAccount = account;
		}
	}
	//Locate account to display by handle
	if(shownAccount==null){
		throw new HandleNotRecognisedException("Handle not recognised");
	}
	//Else throw exception
	details.append("ID: ").append(shownAccount.getId()).append("\n");
	details.append("Handle: ").append(shownAccount.getHandle()).append("\n");
	details.append("Description: ").append(shownAccount.getDescription()).append("\n");
	details.append("Post count: ").append(shownAccount.getPosts().size()).append("\n");
	details.append("Endorse count: ").append(shownAccount.getNumberEndorsements());
	//Add account details in correct format to stringbuilder details
	return details.toString();
}

@Override
public String showIndividualPost(int id) throws PostIDNotRecognisedException {
	Post shownPost = null;
	Comment shownComment = null;
	StringBuilder details = new StringBuilder();
	
	for(Post post: posts){

		if(post.getPostId()==id){
			shownPost = post;
			break;
		}
	}
	//Locate post to display 
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
		//If post not found in posts or comments throw exception
	}
	//Loop through comments if post to display not found in posts arraylist
	if(shownPost!=null) {
		details.append("ID: ").append(shownPost.getPostId()).append("\n");
		details.append("Account: ").append(shownPost.getAccount().getHandle()).append("\n");
		details.append("No. endorsements: ").append(shownPost.getLike()).append("\n");
		details.append(shownPost.getText());
	}
	//If post is type Post use correct functions to append information to stringbuilder in correct format
	else {
		details.append("ID: ").append(shownComment.getPostId()).append("\n");
		details.append("Account: ").append(shownComment.getAccount().getHandle()).append("\n");
		details.append("No. endorsements: ").append(shownComment.getLike()).append("\n");
		details.append(shownComment.getText());
	}
	//Else use comment functions to append information to stringbuilder in correct format
	return details.toString();
}

@Override
public StringBuilder showPostChildrenDetails(int id) throws PostIDNotRecognisedException, NotActionablePostException {
	Post startPost = null;
	Comment startComment = null;
	ArrayList<Comment> childComments;
	StringBuilder details = new StringBuilder();

	for(Post post: posts){
		
		if(post.getPostId()==id){
			startPost = post;
			break;
		}
	}
	//Loop through posts to locate post to display children of
	if(startPost==null){
		
		for(Comment comment: comments){
		
			if(comment.getPostId()==id){
				startComment = comment;
				break;
			}
		}
		
		if(startComment==null){
		
			for(Endorsement endorsement: endorsements){
		
				if(endorsement.getPostId()==id){
					throw new NotActionablePostException("Endorsements not valid post type for this action");
				}
			}
			throw new PostIDNotRecognisedException("Post not found by ID");
		}
		//Verify post/comment exists and set start post/comment
	}
	//If post not yet found loop through comments to find
	if(startPost!=null) {
		childComments = startPost.getComments();
		details.append("ID: ").append(startPost.getPostId()).append("\n");
		details.append("Account: ").append(startPost.getAccount().getHandle()).append("\n");
		details.append("No. endorsements: ").append(startPost.getLike()).append(" | ");
		details.append("No. comments: ").append(startPost.getComments().size()).append("\n");
		details.append(startPost.getText()).append("\n");
	}
	//If type is Post append details without any indent
	else {
		childComments = startComment.getComments();
		if(indent>0){
			details.append("\t".repeat(Math.max(0, indent-1))).append("| \n");
			details.append("\t".repeat(Math.max(0, indent-1))).append("| > ");
		}
		details.append("ID: ").append(startComment.getPostId()).append("\n");
		details.append("\t".repeat(indent));
		details.append("Account: ").append(startComment.getAccount().getHandle()).append("\n");
		details.append("\t".repeat(indent));
		details.append("No. endorsements: ").append(startComment.getLike());
		details.append(" | No. comments: ").append(startComment.getComments().size()).append("\n");
		details.append("\t".repeat(indent));
		details.append(startComment.getText()).append("\n");
	}
	//Else post will be of type Comment and will use indent to be formatted correctly
	for(Comment comment: childComments){
		
		if(childComments.isEmpty()){
			indent--;
			return details;
		}
		//If no more child comments return stringbuilder to be used by previous recursive call
		else{
			indent++;
			details.append(showPostChildrenDetails(comment.getPostId()));
		}
		//Go to next generation of comments 
	}
	//Loop through all children posts (comments) and display recursively
	if(indent>0){
		indent--;
	}
	return details;
}

	//Manage platform ***************************************************************
	@Override
	public void savePlatform(String filename) throws IOException {
		
		try{
			FileOutputStream file = new FileOutputStream(filename + ".ser");
			ObjectOutputStream out = new ObjectOutputStream(file);
			DataStore dataStore = new DataStore(PostID, AccountID, accounts, posts, comments, endorsements);
			//Create datastore object with platform state recorded
			out.writeObject(dataStore);
			out.close();
			file.close();
			//Try to write the datastore to a .ser file
		} catch (IOException e){
			throw new IOException(e);
		}
	}

	@Override
	public void loadPlatform(String filename) throws IOException, ClassNotFoundException {
		
		try {
			FileInputStream file = new FileInputStream(filename + ".ser");
			ObjectInputStream in = new ObjectInputStream(file);
			DataStore dataStore = null;
			dataStore = (DataStore) in.readObject();
			//Read .ser file into datastore object
			PostID = dataStore.getPostID();
			AccountID = dataStore.getAccountID();
			accounts = dataStore.getAccounts();
			posts = dataStore.getPosts();
			comments = dataStore.getComments();
			endorsements = dataStore.getEndorsements();
			//Set datastore object data as platform data
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
		this.AccountID = 0;
		this.accounts.clear();
		this.posts.clear();
		this.comments.clear();
		this.endorsements.clear();
		//Clear and reset all attributes excluding default deleted post
	}

	//Additional functions *************************************************************
	/**
	 * Checkpost function used to check if given message is within post restrictions.
	 * Post must be shorter than length 100 and not be null.
	 * @param message message to check validity of.
	 * @return boolean true if post is valid.
	 */
	public boolean checkPost(String message){
		
		if(message.length() < 100 && !(message.equals(null))){
			return true;
		}
		//If message within valid size constraints return true
		return false;
	}

	/**
	 * Checkhandle function used to check if given handle is within restrictions.
	 * Must be shorter than length 31, contain no spaces and not be null.
	 * @param handle handle to check validity of.
	 * @return integer 0 for valid handle, 1 and 2 used for exception classification.
	 */
	public int checkHandle(String handle){
		
		if(!handle.contains(" ") && !handle.equals("") && handle.length()<31){
		
			for(Account account: accounts){
		
				if(account.getHandle().equals(handle)){
					return 1;
					//If handle occurs in an existing account, return 1
				}
			}
			return 0;
			//If handle is valid and doesnt exist, return 0
		}
		else
			return 2;
		//If handle invalid, return 2
	}
}