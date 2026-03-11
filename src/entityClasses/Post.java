package entityClasses;

/*******
 * <p> Title: Post Class </p>
 * <p> Description: Entity for a discussion post with id, title (max 100), body (max 1000), and author. </p>
 */
public class Post {

	private int id;
	private String title;
	private String body;
	private String author;

	public Post() {
	}

	/** Constructor for a new post (id may be 0 until persisted). */
	public Post(int id, String title, String body, String author) {
		this.id = id;
		this.title = title == null ? "" : title;
		this.body = body == null ? "" : body;
		this.author = author == null ? "" : author;
	}

	public int getId() { return id; }
	public void setId(int id) { this.id = id; }
	public String getTitle() { return title; }
	public void setTitle(String title) { this.title = title == null ? "" : title; }
	public String getBody() { return body; }
	public void setBody(String body) { this.body = body == null ? "" : body; }
	public String getAuthor() { return author; }
	public void setAuthor(String author) { this.author = author == null ? "" : author; }

	@Override
	public String toString() { return title == null ? "" : (title.length() > 50 ? title.substring(0, 50) + "..." : title); }
}
