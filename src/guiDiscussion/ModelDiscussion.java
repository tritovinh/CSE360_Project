package guiDiscussion;

import java.sql.SQLException;
import java.util.Collections;
import java.util.List;
import database.Database;
import entityClasses.Post;
import entityClasses.Reply;
import guiTools.DiscussionValidator;

/*******
 * <p> Title: ModelDiscussion Class </p>
 * <p> Description: Loads and persists posts and replies via Database; uses DiscussionValidator for create/update. </p>
 */
public class ModelDiscussion {

	private static Database theDatabase = applicationMain.FoundationsMain.database;

	/** Loads all posts from the database. */
	public static List<Post> loadAllPosts() {
		try {
			return theDatabase.getAllPosts();
		} catch (SQLException e) {
			e.printStackTrace();
			return Collections.emptyList();
		}
	}

	/** Loads all replies for the given post. */
	public static List<Reply> loadRepliesForPost(int postId) {
		try {
			return theDatabase.getRepliesByPostId(postId);
		} catch (SQLException e) {
			e.printStackTrace();
			return Collections.emptyList();
		}
	}

	/** Validates and creates a post; returns empty string on success, error message otherwise. */
	public static String createPost(String title, String body, String author) {
		String err = DiscussionValidator.validatePost(title, body);
		if (!err.isEmpty()) return err;
		try {
			Post p = new Post(0, title.trim(), body.trim(), author);
			theDatabase.createPost(p);
			return "";
		} catch (SQLException e) {
			e.printStackTrace();
			return "Database error: " + e.getMessage();
		}
	}

	/** Validates and updates a post; returns empty string on success. */
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

	/** Deletes a post by id; returns empty string on success. */
	public static String deletePost(int postId) {
		try {
			return theDatabase.deletePost(postId) ? "" : "Post not found.";
		} catch (SQLException e) {
			e.printStackTrace();
			return "Database error: " + e.getMessage();
		}
	}

	/** Validates and creates a reply for a post; returns empty string on success. */
	public static String createReply(int parentPostId, String body, String author) {
		String err = DiscussionValidator.validateReply(body);
		if (!err.isEmpty()) return err;
		try {
			Reply r = new Reply(0, parentPostId, body.trim(), author);
			theDatabase.createReply(r);
			return "";
		} catch (SQLException e) {
			e.printStackTrace();
			return "Database error: " + e.getMessage();
		}
	}

	/** Validates and updates a reply; returns empty string on success. */
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

	/** Deletes a reply by id; returns empty string on success. */
	public static String deleteReply(int replyId) {
		try {
			return theDatabase.deleteReply(replyId) ? "" : "Reply not found.";
		} catch (SQLException e) {
			e.printStackTrace();
			return "Database error: " + e.getMessage();
		}
	}
}
