package entityClasses;

/*******
 * <p> Title: Reply Class </p>
 * <p> Description: Entity for a reply with id, parent post id, body (max 1000), and author. </p>
 */
public class Reply {

	private int id;
	private int parentPostId;
	private String body;
	private String author;
	private boolean taApproved;

	public Reply() {
	}

	/** Constructor for a new reply (id may be 0 until persisted). */
	public Reply(int id, int parentPostId, String body, String author) {
		this.id = id;
		this.parentPostId = parentPostId;
		this.body = body == null ? "" : body;
		this.author = author == null ? "" : author;
		this.taApproved = false;
	}

	public Reply(int id, int parentPostId, String body, String author, boolean taApproved) {
		this.id = id;
		this.parentPostId = parentPostId;
		this.body = body == null ? "" : body;
		this.author = author == null ? "" : author;
		this.taApproved = taApproved;
	}

	public int getId() { return id; }
	public void setId(int id) { this.id = id; }
	public int getParentPostId() { return parentPostId; }
	public void setParentPostId(int parentPostId) { this.parentPostId = parentPostId; }
	public String getBody() { return body; }
	public void setBody(String body) { this.body = body == null ? "" : body; }
	public String getAuthor() { return author; }
	public void setAuthor(String author) { this.author = author == null ? "" : author; }
	public boolean isTaApproved() { return taApproved; }
	public void setTaApproved(boolean taApproved) { this.taApproved = taApproved; }
}
