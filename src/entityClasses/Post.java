package entityClasses;

import guiDiscussion.ViewDiscussion;

/*******
 * <p> Title: Post Class </p>
 * <p> Description: Entity for a discussion post with id, title (max 100), body (max 1000), and author. </p>
 */
public class Post {

	private int id;
	private String title;
	private String body;
	private String author;
	private String role;

	/**********
	 * <p> Method: Post() </p>
	 * 
	 * <p> Description: This default constructor is not used in this system. </p>
	 */
	public Post() {
	}

	/**********
	 * <p> Method: Post(int id, String title, String body, String author) </p>
	 * 
	 * <p> Description: This constructor is used to establish user entity objects. </p>
	 * 
	 * @param id the unique id of this post
	 * 
	 * @param title the title of the post
	 * 
	 * @param body the body of the post
	 * 
	 * @param author the username attached to this post
	 * 
	 */
	public Post(int id, String title, String body, String author, String role) {
		this.id = id;
		this.title = title == null ? "" : title;
		this.body = body == null ? "" : body;
		this.author = author == null ? "" : author;
		this.role = role == null? "" : role;
	}

	/**********
	 * <p> Method: int getId() </p>
	 * 
	 * <p> Description: This gets the id of a post </p>
	 * 
	 * @return id is the identification number of post
	 * 
	 */
	public int getId() { return id; }
	
	
	/**********
	 * <p> Method: void setId(int id) </p>
	 * 
	 * <p> Description: This set a new id of a post. </p>
	 * 
	 * @param id is the identification number of post
	 * 
	 */
	public void setId(int id) { this.id = id; }
	
	
	/**********
	 * <p> Method: String getTitle() </p>
	 * 
	 * <p> Description: This gets a title of a post. </p>
	 * 
	 * @return title of a post
	 * 
	 */
	public String getTitle() { return title; }
	
	
	/**********
	 * <p> Method: void setTitle(String title) </p>
	 * 
	 * <p> Description: This set a new title on a post. </p>
	 * 
	 * @param title is what the title will be
	 * 
	 */
	public void setTitle(String title) { this.title = title == null ? "" : title; }
	
	
	/**********
	 * <p> Method: String getBody() </p>
	 * 
	 * <p> Description: This gets the body content post. </p>
	 * 
	 * @return body String of a post
	 * 
	 */
	public String getBody() { return body; }
	
	
	/**********
	 * <p> Method: void setBody(String body) </p>
	 * 
	 * <p> Description: This set a new body on a post. </p>
	 * 
	 * @param body is what the body content will be
	 * 
	 */
	public void setBody(String body) { this.body = body == null ? "" : body; }
	
	
	/**********
	 * <p> Method: String getAuthor() </p>
	 * 
	 * <p> Description: This gets the author of a post. </p>
	 * 
	 * @return author string attached to a post
	 * 
	 */
	public String getAuthor() { return author; }
	
	
	/**********
	 * <p> Method: void setAuthor() </p>
	 * 
	 * <p> Description: This sets a new author to a post. </p>
	 * 
	 * @param author string to be attached to a post
	 * 
	 */
	public void setAuthor(String author) { this.author = author == null ? "" : author; }

	/**********
	 * <p> Method: String getRole() </p>
	 * 
	 * <p> Description: This gets the role of a Post based on author </p>
	 * 
	 * @return role is the role assigned to a Post
	 * 
	 */
	public String getRole() { return role; }
	

}
