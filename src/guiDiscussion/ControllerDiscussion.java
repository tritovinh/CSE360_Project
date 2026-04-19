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
 * 
 * <p> Description: Handles user actions: create post, view all, edit post, delete post, add/edit/delete reply, logout, quit. </p>
 */
public class ControllerDiscussion {
	
	/*****
     * <p> Method: ControllerDiscussion() </p>
     * 
     * <p> Description: This default constructor is not used in this system. </p>
     */
    public ControllerDiscussion() {	
    }

    /*****
     * <p> Method: void performNewPost() </p>
     * 
     * <p> Description: Opens dialog to create a new post, saves it, and refreshes the post list. </p>
     */
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
		ViewDiscussion.refreshPostList(ModelDiscussion.loadAllPosts(user.getNewRole1()));
	}

	/*****
     * <p> Method: void performEditPost() </p>
     * 
     * <p> Description: Opens dialog to edit the selected post, updates it, and refreshes the list. </p>
     */
	protected static void performEditPost() {
		User user = ViewDiscussion.theUser;
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
		ViewDiscussion.refreshPostList(ModelDiscussion.loadAllPosts(user.getNewRole1()));
		ViewDiscussion.showPostDetail(post);
	}

	/*****
     * <p> Method: void performDeletePost() </p>
     * 
     * <p> Description: Confirms and deletes the selected post (and its replies), then refreshes the list. </p>
     */
	protected static void performDeletePost() {
		User user = ViewDiscussion.theUser;
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
		ViewDiscussion.refreshPostList(ModelDiscussion.loadAllPosts(user.getNewRole1()));
	}


	/*****
     * <p> Method: void performAddReply() </p>
     * 
     * <p> Description: Opens dialog to add a reply to the selected post and refreshes replies. </p>
     */
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

	
	/*****
     * <p> Method: void performEditReply(Reply reply) </p>
     * 
     * <p> Description: Opens dialog to edit the given reply and refreshes the reply list. </p>\
     * 
     * @param reply The reply object that is edited
     */
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


	/*****
     * <p> Method: void performDeleteReply(Reply reply) </p>
     * 
     * <p> Description: Deletes the given reply and refreshes the reply list. </p>
     * 
     * @param reply the reply object that will be deleted
     */
	protected static void performDeleteReply(Reply reply) {
		if (reply == null) return;
		String err = ModelDiscussion.deleteReply(reply.getId());
		if (!err.isEmpty()) {
			showAlert(AlertType.ERROR, "Error", err);
			return;
		}
		ViewDiscussion.refreshRepliesForCurrentPost();
	}


	/*****
     * <p> Method: void performLogout() </p>
     * 
     * <p> Description: Returns to the login screen. </p>
     */
	protected static void performLogout() {
		guiUserLogin.ViewUserLogin.displayUserLogin(ViewDiscussion.theStage);
	}

	
	/*****
     * <p> Method: void performQuit() </p>
     * 
     * <p> Description: Exits the application. </p>
     */
	protected static void performQuit() {
		System.exit(0);
	}

	
	/*****
     * <p> Method: void showAlert(AlertType type, String title, String message) </p>
     * 
     * <p> Description: Shows an alert dialog with the given type, title, and message. </p>
     * 
     * @param type specifies what kind of alert will be shown
     * 
     * @param title specifies the title of the alert
     * 
     * @param message specifies the message of the alert
     */
	private static void showAlert(AlertType type, String title, String message) {
		Alert a = new Alert(type);
		a.setTitle(title);
		a.setHeaderText(null);
		a.setContentText(message);
		a.showAndWait();
	}
}
