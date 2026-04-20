package guiDiscussion;

import java.util.List;
import entityClasses.Post;
import entityClasses.Reply;
import entityClasses.User;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.stage.Stage;

/*******
 * <p> Title: ControllerDiscussion Class </p>
 * <p> Description: Handles user actions: create post, view all, edit post, delete post, add/edit/delete reply, logout, quit. </p>
 */
	public class ControllerDiscussion {

	/** Opens dialog to create a new post, saves it, and refreshes the post list */
	protected static void performNewPost() {
		User user = ViewDiscussion.theUser;
		if (user == null) return;
		Stage dialog = ViewDiscussion.buildPostDialog("Create Post", "", "");
		dialog.showAndWait();
		String[] result = ViewDiscussion.getLastPostDialogResult();
		if (result == null) return;
		String err = ModelDiscussion.createPost(result[0], result[1], user.getUserName());
		if (!err.isEmpty()) {
			showAlert(AlertType.ERROR, "Error", err);
			return;
		}
		ViewDiscussion.refreshPostList(ModelDiscussion.loadAllPosts());
	}

	/** Opens dialog to edit the selected post, updates it, and refreshes the list */
	protected static void performEditPost() {
		Post post = ViewDiscussion.getSelectedPost();
		if (post == null) {
			showAlert(AlertType.WARNING, "No selection", "Select a post to edit.");
			return;
		}
		Stage dialog = ViewDiscussion.buildPostDialog("Edit Post", post.getTitle(), post.getBody());
		dialog.showAndWait();
		String[] result = ViewDiscussion.getLastPostDialogResult();
		if (result == null) return;
		post.setTitle(result[0]);
		post.setBody(result[1]);
		String err = ModelDiscussion.updatePost(post);
		if (!err.isEmpty()) {
			showAlert(AlertType.ERROR, "Error", err);
			return;
		}
		ViewDiscussion.refreshPostList(ModelDiscussion.loadAllPosts());
		ViewDiscussion.showPostDetail(post);
	}

	/** Confirms and deletes the selected post (and its replies), then refreshes the list */
	protected static void performDeletePost() {
		Post post = ViewDiscussion.getSelectedPost();
		if (post == null) {
			showAlert(AlertType.WARNING, "No selection", "Select a post to delete.");
			return;
		}
		Alert confirm = new Alert(AlertType.CONFIRMATION);
		confirm.setHeaderText(null);
		confirm.setContentText("Delete this post? All replies will be removed.");
		if (confirm.showAndWait().orElse(ButtonType.CANCEL) != ButtonType.OK)
			return;
		String err = ModelDiscussion.deletePost(post.getId());
		if (!err.isEmpty()) {
			showAlert(AlertType.ERROR, "Error", err);
			return;
		}
		ViewDiscussion.clearSelection();
		ViewDiscussion.refreshPostList(ModelDiscussion.loadAllPosts());
	}

	/** Opens dialog to add a reply to the selected post and refreshes replies */
	protected static void performAddReply() {
		Post post = ViewDiscussion.getSelectedPost();
		if (post == null) {
			showAlert(AlertType.WARNING, "No selection", "Select a post first.");
			return;
		}
		User user = ViewDiscussion.theUser;
		if (user == null) return;
		Stage dialog = ViewDiscussion.buildReplyDialog("Add Reply", "");
		dialog.showAndWait();
		String body = ViewDiscussion.getLastReplyDialogResult();
		if (body == null) return;
		String err = ModelDiscussion.createReply(post.getId(), body, user.getUserName());
		if (!err.isEmpty()) {
			showAlert(AlertType.ERROR, "Error", err);
			return;
		}
		ViewDiscussion.refreshRepliesForCurrentPost();
	}

	/** Opens dialog to edit the given reply and refreshes the reply list */
	protected static void performEditReply(Reply reply) {
		if (reply == null) return;
		Stage dialog = ViewDiscussion.buildReplyDialog("Edit Reply", reply.getBody());
		dialog.showAndWait();
		String body = ViewDiscussion.getLastReplyDialogResult();
		if (body == null) return;
		reply.setBody(body);
		String err = ModelDiscussion.updateReply(reply);
		if (!err.isEmpty()) {
			showAlert(AlertType.ERROR, "Error", err);
			return;
		}
		ViewDiscussion.refreshRepliesForCurrentPost();
	}

	/** Deletes the given reply and refreshes the reply list */
	protected static void performDeleteReply(Reply reply) {
		if (reply == null) return;
		String err = ModelDiscussion.deleteReply(reply.getId());
		if (!err.isEmpty()) {
			showAlert(AlertType.ERROR, "Error", err);
			return;
		}
		ViewDiscussion.refreshRepliesForCurrentPost();
	}

	/** Approves the given reply and refreshes the reply list */
	protected static void performApproveReply(Reply reply) {
		if (reply == null) return;
		String err = reply.isTaApproved()
				? ModelDiscussion.disapproveReply(reply.getId())
				: ModelDiscussion.approveReply(reply.getId());
		if (!err.isEmpty()) {
			showAlert(AlertType.ERROR, "Error", err);
			return;
		}
		ViewDiscussion.refreshRepliesForCurrentPost();
	}

	/** Returns to the login screen */
	protected static void performLogout() {
		guiUserLogin.ViewUserLogin.displayUserLogin(ViewDiscussion.theStage);
	}

	/** Exits the application */
	protected static void performQuit() {
		System.exit(0);
	}

	/** Shows an alert dialog with the given type, title, and message */
	private static void showAlert(AlertType type, String title, String message) {
		Alert a = new Alert(type);
		a.setTitle(title);
		a.setHeaderText(null);
		a.setContentText(message);
		a.showAndWait();
	}
}
