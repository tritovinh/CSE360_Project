package guiDiscussion;

import java.sql.SQLException;
import java.util.Collections;
import java.util.List;
import database.Database;
import entityClasses.Post;
import entityClasses.Reply;
import entityClasses.User;
import guiTools.DiscussionValidator;

/*******
 * <p> Title: ModelDiscussion Class </p>
 * <p> Description: Loads and persists posts and replies via Database; uses DiscussionValidator for create/update. </p>
 */
public class ModelDiscussion {
	
	protected static User theUser;

	/*****
     * <p> Method: ModelDiscussion() </p>
     * 
     * <p> Description: This default constructor is not used in this system. </p>
     */
    public ModelDiscussion() {	
    }
	
	private static Database theDatabase = applicationMain.FoundationsMain.database;

	
	/**********
	 * <p> Method: List loadAllPosts </p>
	 * 
	 * <p> Description: Loads all posts from the database. </p>
	 * 
	 * @param teacher specifies if a teacher role is searching or not.
	 * 
	 * @return ArrayList of posts from database
	 * 
	 */
	public static List<Post> loadAllPosts(Boolean teacher) {
		try {
			return theDatabase.getAllPosts(teacher);
		} catch (SQLException e) {
			e.printStackTrace();
			return Collections.emptyList();
		}
	}


	/**********
	 * <p> Method: List loadRepliesForPost(int postID) </p>
	 * 
	 * <p> Description: Loads all replies for the given post. </p>
	 * 
	 * @param postId specifies the post that the replies are attached to
	 * 
	 * @return ArrayList of replies from database
	 * 
	 */
	public static List<Reply> loadRepliesForPost(int postId) {
		try {
			return theDatabase.getRepliesByPostId(postId);
		} catch (SQLException e) {
			e.printStackTrace();
			return Collections.emptyList();
		}
	}

	/**********
	 * <p> Method: String createPost(String title, String body, String author) </p>
	 * 
	 * <p> Description: Validates and creates a post. </p>
	 * 
	 * @param title specifies the title for new Post
	 * 
	 * @param body specifies the body for new Post
	 * 
	 * @param author specifies the user making the post
	 * 
	 * @return empty string on success, error message otherwise.
	 * 
	 */
	public static String createPost(String title, String body, String author) {
		String err = DiscussionValidator.validatePost(title, body);
		if (!err.isEmpty()) return err;
		if (theUser == null) return "No active user session. Please log in again.";
		String role = theUser.getNewRole1() ? "teacher" : "student";
		
		try {
			Post p = new Post(0, title.trim(), body.trim(), author, role);
			theDatabase.createPost(p);
			return "";
		} catch (SQLException e) {
			e.printStackTrace();
			return "Database error: " + e.getMessage();
		}
	}

	
	/**********
	 * <p> Method: String updatePost(Post post) </p>
	 * 
	 * <p> Description: Validates and updates a post. </p>
	 * 
	 * @param post specifies the updated post
	 * 
	 * @return empty string on success, error message otherwise.
	 * 
	 */
	public static String updatePost(Post post) {
		String err = DiscussionValidator.validatePost(post.getTitle(), post.getBody());
		if (!err.isEmpty()) return err;
		try {
			return theDatabase.updatePost(post) ? "" : "Post not found.";
		} catch (SQLException e) {
			e.printStackTrace();
			return "Database error: " + e.getMessage();
		}
	}
	

	/**********
	 * <p> Method: String deletePost(int postId) </p>
	 * 
	 * <p> Description: Deletes a post by id. </p>
	 * 
	 * @param postId specifies the post that will be deleted
	 * 
	 * @return empty string on success, error message otherwise.
	 * 
	 */
	public static String deletePost(int postId) {
		try {
			return theDatabase.deletePost(postId) ? "" : "Post not found.";
		} catch (SQLException e) {
			e.printStackTrace();
			return "Database error: " + e.getMessage();
		}
	}
	

	/**********
	 * <p> Method: String createReply(int parentPostId, String body, String author) </p>
	 * 
	 * <p> Description: Validates and creates a reply for a post. </p>
	 * 
	 * @param parentPostId specifies the post that the reply is linked to
	 * 
	 * @param body specifies the body of the reply
	 * 
	 * @param author specifies the user who authors the reply
	 * 
	 * @return empty string on success.
	 * 
	 */
	public static String createReply(int parentPostId, String body, String author) {
		String err = DiscussionValidator.validateReply(body);
		if (!err.isEmpty()) return err;
		if (theUser == null) return "No active user session. Please log in again.";
		String role = theUser.getNewRole1() ? "teacher" : "student";
		
		try {
			Reply r = new Reply(0, parentPostId, body.trim(), author, role);
			theDatabase.createReply(r);
			return "";
		} catch (SQLException e) {
			e.printStackTrace();
			return "Database error: " + e.getMessage();
		}
	}

	
	/**********
	 * <p> Method: String updateReply(Reply reply) </p>
	 * 
	 * <p> Description: Validates and updates a reply. </p>
	 * 
	 * @param reply specifies the updated reply
	 * 
	 * @return empty string on success.
	 * 
	 */
	public static String updateReply(Reply reply) {
		String err = DiscussionValidator.validateReply(reply.getBody());
		if (!err.isEmpty()) return err;
		try {
			return theDatabase.updateReply(reply) ? "" : "Reply not found.";
		} catch (SQLException e) {
			e.printStackTrace();
			return "Database error: " + e.getMessage();
		}
	}

	
	/**********
	 * <p> Method: String deleteReply(int replyId) </p>
	 * 
	 * <p> Description: Deletes a reply by id. </p>
	 * 
	 * @param replyId specifies the reply that will be deleted
	 * 
	 * @return empty string on success.
	 * 
	 */
	public static String deleteReply(int replyId) {
		try {
			return theDatabase.deleteReply(replyId) ? "" : "Reply not found.";
		} catch (SQLException e) {
			e.printStackTrace();
			return "Database error: " + e.getMessage();
		}
	}

	/**********
	 * <p> Method: void setCurrentUser(User user) </p>
	 * 
	 * <p> Description: Sets the active user for role-aware post/reply creation. </p>
	 * 
	 * @param user specifies the currently logged-in user
	 */
	public static void setCurrentUser(User user) {
		theUser = user;
	}
}
