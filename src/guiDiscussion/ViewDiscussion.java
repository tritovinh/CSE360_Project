package guiDiscussion;

import java.util.List;
import entityClasses.Post;
import entityClasses.Reply;
import entityClasses.User;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;

/*******
 * <p> Title: ViewDiscussion Class </p>
 * <p> Description: Responsive discussion UI: left column = all posts + Create Post; right = open post or prompt to click a post; replies below; create/edit via popup with validator. </p>
 */
public class ViewDiscussion {

	private static final double WIDTH = applicationMain.FoundationsMain.WINDOW_WIDTH;
	private static final double HEIGHT = applicationMain.FoundationsMain.WINDOW_HEIGHT;

	protected static Stage theStage;
	protected static User theUser;
	private static Scene theScene;

	private static ViewDiscussion theView;

	// Left column
	protected static Button button_CreatePost = new Button("Create Post");
	protected static ListView<Post> list_Posts = new ListView<>();

	// Right: prompt when nothing selected
	private static final Label label_Prompt = new Label("Click a post to open it.");
	// Right: post detail when selected
	protected static Label label_PostTitle = new Label();
	protected static Label label_PostAuthor = new Label();
	protected static TextArea area_PostBody = new TextArea();
	protected static ListView<Reply> list_Replies = new ListView<>();
	protected static Button button_EditPost = new Button("Edit Post");
	protected static Button button_DeletePost = new Button("Delete Post");
	protected static Button button_AddReply = new Button("Add Reply");
	protected static Button button_Logout = new Button("Logout");
	protected static Button button_Quit = new Button("Quit");

	private static Post selectedPost;
	private static String[] lastPostDialogResult;
	private static String lastReplyDialogResult;

	private static VBox rightPostDetail;
	private static StackPane rightStack;

	/** Shows the discussion screen for the given user and stage. */
	public static void displayDiscussion(Stage ps, User user) {
		theStage = ps;
		theUser = user;
		if (theView == null) theView = new ViewDiscussion();
		refreshPostList(ModelDiscussion.loadAllPosts());
		clearSelection();
		theStage.setTitle("Discussion");
		theStage.setScene(theScene);
		theStage.show();
	}

	/** Builds the discussion UI layout (posts list, post detail, dialogs wiring). */
	private ViewDiscussion() {
		BorderPane root = new BorderPane();
		root.setPadding(new Insets(12));

		// —— Left column: post list + Create Post ——
		Label listHeader = new Label("Posts");
		listHeader.setFont(Font.font("System", FontWeight.BOLD, 16));
		button_CreatePost.setMaxWidth(Double.MAX_VALUE);
		button_CreatePost.setOnAction(ev -> ControllerDiscussion.performNewPost());

		list_Posts.setCellFactory(lv -> new PostListCell());
		list_Posts.getSelectionModel().selectedItemProperty().addListener((obs, oldVal, p) -> onPostSelected(p));
		VBox.setVgrow(list_Posts, Priority.ALWAYS);

		VBox leftColumn = new VBox(10);
		leftColumn.getChildren().addAll(listHeader, button_CreatePost, list_Posts);
		leftColumn.setMinWidth(220);
		leftColumn.setPrefWidth(260);
		leftColumn.setMaxWidth(320);

		// —— Right: prompt or post detail (stacked) ——
		label_Prompt.setFont(Font.font("System", 18));
		label_Prompt.setStyle("-fx-text-fill: #666;");
		StackPane promptPane = new StackPane(label_Prompt);
		promptPane.setAlignment(Pos.CENTER);

		label_PostTitle.setFont(Font.font("System", FontWeight.BOLD, 18));
		label_PostTitle.setWrapText(true);
		label_PostAuthor.setFont(Font.font("System", 14));
		label_PostAuthor.setStyle("-fx-text-fill: #555;");
		area_PostBody.setEditable(false);
		area_PostBody.setWrapText(true);
		area_PostBody.setPrefRowCount(4);
		area_PostBody.setMinHeight(80);

		Label repliesHeader = new Label("Replies");
		repliesHeader.setFont(Font.font("System", FontWeight.BOLD, 14));
		list_Replies.setCellFactory(lv -> new ReplyListCell());
		VBox.setVgrow(list_Replies, Priority.ALWAYS);

		button_EditPost.setOnAction(ev -> ControllerDiscussion.performEditPost());
		button_DeletePost.setOnAction(ev -> ControllerDiscussion.performDeletePost());
		button_AddReply.setOnAction(ev -> ControllerDiscussion.performAddReply());
		HBox postActions = new HBox(10);
		postActions.getChildren().addAll(button_EditPost, button_DeletePost, button_AddReply);

		rightPostDetail = new VBox(10);
		rightPostDetail.getChildren().addAll(
				label_PostTitle,
				label_PostAuthor,
				area_PostBody,
				repliesHeader,
				list_Replies,
				postActions);
		rightPostDetail.setPadding(new Insets(0, 0, 0, 16));

		ScrollPane scrollDetail = new ScrollPane(rightPostDetail);
		scrollDetail.setFitToWidth(true);
		scrollDetail.setFitToHeight(true);
		scrollDetail.setVbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);

		rightStack = new StackPane(promptPane, scrollDetail);
		scrollDetail.setVisible(false);
		scrollDetail.managedProperty().bind(scrollDetail.visibleProperty());

		button_Logout.setOnAction(ev -> ControllerDiscussion.performLogout());
		button_Quit.setOnAction(ev -> ControllerDiscussion.performQuit());
		HBox bottomBar = new HBox(10);
		bottomBar.setAlignment(Pos.CENTER_RIGHT);
		bottomBar.getChildren().addAll(button_Logout, button_Quit);
		bottomBar.setPadding(new Insets(8, 0, 0, 0));

		root.setLeft(leftColumn);
		root.setCenter(rightStack);
		root.setBottom(bottomBar);
		BorderPane.setMargin(rightStack, new Insets(0, 0, 0, 12));

		theScene = new Scene(root, WIDTH, HEIGHT);
	}

	/** Toggles the right pane between the "click a post" prompt and the post detail view. */
	private void updateRightVisibility() {
		boolean hasSelection = selectedPost != null;
		rightStack.getChildren().get(0).setVisible(!hasSelection);
		rightStack.getChildren().get(1).setVisible(hasSelection);
	}

	/** Handles post selection: shows post detail and loads replies. */
	private void onPostSelected(Post p) {
		selectedPost = p;
		updateRightVisibility();
		if (p == null) {
			label_PostTitle.setText("");
			label_PostAuthor.setText("");
			area_PostBody.setText("");
			list_Replies.getItems().clear();
			return;
		}
		label_PostTitle.setText(p.getTitle());
		label_PostAuthor.setText("by " + p.getAuthor());
		area_PostBody.setText(p.getBody());
		List<Reply> replies = ModelDiscussion.loadRepliesForPost(p.getId());
		list_Replies.getItems().clear();
		list_Replies.getItems().addAll(replies);
	}

	/** Refreshes the post list with the given posts. */
	protected static void refreshPostList(List<Post> posts) {
		list_Posts.getItems().clear();
		if (posts != null) list_Posts.getItems().addAll(posts);
	}

	/** Clears the selected post and resets the right pane. */
	protected static void clearSelection() {
		selectedPost = null;
		list_Posts.getSelectionModel().clearSelection();
		if (theView != null) theView.updateRightVisibility();
		label_PostTitle.setText("");
		label_PostAuthor.setText("");
		area_PostBody.setText("");
		list_Replies.getItems().clear();
	}

	/** Displays the given post in the right pane and loads its replies. */
	protected static void showPostDetail(Post p) {
		if (p == null) return;
		selectedPost = p;
		if (theView != null) theView.updateRightVisibility();
		label_PostTitle.setText(p.getTitle());
		label_PostAuthor.setText("by " + p.getAuthor());
		area_PostBody.setText(p.getBody());
		List<Reply> replies = ModelDiscussion.loadRepliesForPost(p.getId());
		list_Replies.getItems().clear();
		list_Replies.getItems().addAll(replies);
	}

	/** Reloads replies for the currently selected post. */
	protected static void refreshRepliesForCurrentPost() {
		if (selectedPost == null) return;
		List<Reply> replies = ModelDiscussion.loadRepliesForPost(selectedPost.getId());
		list_Replies.getItems().clear();
		list_Replies.getItems().addAll(replies);
	}

	/** Returns the currently selected post, or null if none. */
	protected static Post getSelectedPost() { return selectedPost; }

	/** Builds a modal dialog for creating or editing a post (title and body); result via getLastPostDialogResult(). */
	protected static Stage buildPostDialog(String dialogTitle, String titleVal, String bodyVal) {
		Stage stage = new Stage();
		stage.setTitle(dialogTitle);
		stage.initModality(Modality.APPLICATION_MODAL);
		stage.initOwner(theStage);
		final boolean[] saved = { false };

		TextField titleField = new TextField(titleVal);
		titleField.setPromptText("Title (max " + guiTools.DiscussionValidator.MAX_TITLE_LENGTH + " chars)");
		titleField.setPrefWidth(400);

		TextArea bodyArea = new TextArea(bodyVal);
		bodyArea.setPromptText("Body (max " + guiTools.DiscussionValidator.MAX_BODY_LENGTH + " chars)");
		bodyArea.setPrefSize(400, 220);
		bodyArea.setWrapText(true);

		Button save = new Button("Save");
		Button cancel = new Button("Cancel");
		save.setOnAction(ev -> {
			String err = guiTools.DiscussionValidator.validatePost(titleField.getText(), bodyArea.getText());
			if (!err.isEmpty()) {
				Alert a = new Alert(AlertType.WARNING);
				a.setHeaderText(null);
				a.setContentText(err);
				a.showAndWait();
				return;
			}
			saved[0] = true;
			lastPostDialogResult = new String[] { titleField.getText().trim(), bodyArea.getText().trim() };
			stage.close();
		});
		cancel.setOnAction(ev -> {
			lastPostDialogResult = null;
			stage.close();
		});

		VBox box = new VBox(12, new Label("Title:"), titleField, new Label("Body:"), bodyArea, new HBox(10, save, cancel));
		box.setPadding(new Insets(16));
		stage.setScene(new Scene(box, 450, 380));
		stage.setOnCloseRequest(ev -> { if (!saved[0]) lastPostDialogResult = null; });
		return stage;
	}

	/** Returns the last result from the post dialog (title, body) or null if cancelled. */
	protected static String[] getLastPostDialogResult() { return lastPostDialogResult; }

	/** Builds a modal dialog for adding or editing a reply (body only); result via getLastReplyDialogResult(). */
	protected static Stage buildReplyDialog(String dialogTitle, String bodyVal) {
		Stage stage = new Stage();
		stage.setTitle(dialogTitle);
		stage.initModality(Modality.APPLICATION_MODAL);
		stage.initOwner(theStage);
		final boolean[] saved = { false };

		TextArea bodyArea = new TextArea(bodyVal);
		bodyArea.setPromptText("Body (max " + guiTools.DiscussionValidator.MAX_BODY_LENGTH + " chars)");
		bodyArea.setPrefSize(400, 200);
		bodyArea.setWrapText(true);

		Button save = new Button("Save");
		Button cancel = new Button("Cancel");
		save.setOnAction(ev -> {
			String err = guiTools.DiscussionValidator.validateReply(bodyArea.getText());
			if (!err.isEmpty()) {
				Alert a = new Alert(AlertType.WARNING);
				a.setHeaderText(null);
				a.setContentText(err);
				a.showAndWait();
				return;
			}
			saved[0] = true;
			lastReplyDialogResult = bodyArea.getText().trim();
			stage.close();
		});
		cancel.setOnAction(ev -> {
			lastReplyDialogResult = null;
			stage.close();
		});

		VBox box = new VBox(12, new Label("Body:"), bodyArea, new HBox(10, save, cancel));
		box.setPadding(new Insets(16));
		stage.setScene(new Scene(box, 450, 300));
		stage.setOnCloseRequest(ev -> { if (!saved[0]) lastReplyDialogResult = null; });
		return stage;
	}

	/** Returns the last result from the reply dialog (body) or null if cancelled. */
	protected static String getLastReplyDialogResult() { return lastReplyDialogResult; }
}
