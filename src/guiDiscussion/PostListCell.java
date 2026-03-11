package guiDiscussion;

import entityClasses.Post;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.ListCell;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;

/*******
 * <p> Title: PostListCell Class </p>
 * <p> Description: List cell that displays a post with title and author. </p>
 */
public class PostListCell extends ListCell<Post> {

	private final VBox box = new VBox(4);
	private final Text titleText = new Text();
	private final Text authorText = new Text();

	/** Sets up the cell to display post title and author. */
	public PostListCell() {
		titleText.setFont(Font.font("System", FontWeight.SEMI_BOLD, 14));
		titleText.setWrappingWidth(220);
		authorText.setFont(Font.font("System", 12));
		authorText.setStyle("-fx-fill: gray;");
		box.getChildren().addAll(titleText, authorText);
		box.setAlignment(Pos.CENTER_LEFT);
		box.setPadding(new Insets(8, 10, 8, 10));
	}

	/** Renders one post in the list (title and author) or clears the cell if empty. */
	@Override
	protected void updateItem(Post post, boolean empty) {
		super.updateItem(post, empty);
		if (empty || post == null) {
			setGraphic(null);
			setText(null);
			return;
		}
		String title = post.getTitle();
		titleText.setText(title != null && title.length() > 50 ? title.substring(0, 50) + "…" : (title != null ? title : ""));
		authorText.setText("by " + (post.getAuthor() != null ? post.getAuthor() : ""));
		setGraphic(box);
		setText(null);
	}
}
