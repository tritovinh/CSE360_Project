package guiDiscussion;

import entityClasses.Reply;
import javafx.scene.control.Button;
import javafx.scene.control.ListCell;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;

/*******
 * <p> Title: ReplyListCell Class </p>
 * <p> Description: List cell that displays a reply with body, author, and Edit/Delete buttons </p>
 */
public class ReplyListCell extends ListCell<Reply> {

	private final HBox box = new HBox(8);
	private final TextFlow textFlow = new TextFlow();
	private final Button editBtn = new Button("Edit");
	private final Button deleteBtn = new Button("Delete");

	
	/*****
     * <p> Method: ReplyListCell() </p>
     * 
     * <p> Description: Construction method that sets up the cell with body/author display and Edit/Delete buttons. </p>
     */
	public ReplyListCell() {
		textFlow.setMaxWidth(Double.MAX_VALUE);
		HBox.setHgrow(textFlow, Priority.ALWAYS);
		editBtn.setOnAction(ev -> {
			Reply r = getItem();
			if (r != null) ControllerDiscussion.performEditReply(r);
		});
		deleteBtn.setOnAction(ev -> {
			Reply r = getItem();
			if (r != null) ControllerDiscussion.performDeleteReply(r);
		});
		box.getChildren().addAll(textFlow, editBtn, deleteBtn);
	}


    /*****
     * <p> Method: void updateItem(Reply reply, boolean empty) </p>
     * 
     * <p> Description: Renders one reply in the list (body and author) or clears the cell if empty. </p>
     * 
     * @param reply specifies the reply item that will be rendered
     * 
     * @param empty specifies if the cell is empty or not
     */
	@Override
	protected void updateItem(Reply reply, boolean empty) {
		super.updateItem(reply, empty);
		if (empty || reply == null) {
			setGraphic(null);
			setText(null);
			return;
		}
		Text t = new Text(reply.getBody() + " — " + reply.getAuthor());
		textFlow.getChildren().setAll(t);
		setGraphic(box);
		setText(null);
	}
}
