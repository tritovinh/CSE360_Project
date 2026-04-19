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
	private String role;

	/**********
	 * <p> Method: Reply() </p>
	 * 
	 * <p> Description: This default constructor is not used in this system. </p>
	 */
	public Reply() {
	}

	/** Constructor for a new reply (id may be 0 until persisted). */
	/**********
	 * <p> Method: Reply(int id, int parentPostId, String body, String author) </p>
	 * 
	 * <p> Description: This constructor is used to establish user entity objects. </p>
	 * 
	 * @param id the unique id of this post
	 * 
	 * @param parentPostId is the id that this reply is attached to
	 * 
	 * @param body the body of the post
	 * 
	 * @param author the username attached to this post
	 * 
	 */
	public Reply(int id, int parentPostId, String body, String author, String role) {
		this.id = id;
		this.parentPostId = parentPostId;
		this.body = body == null ? "" : body;
		this.author = author == null ? "" : author;
		this.role = role == null? "" : role;
	}

	/**********
	 * <p> Method: int getId() </p>
	 * 
	 * <p> Description: This gets the id of a reply </p>
	 * 
	 * @return id is the identification number of reply
	 * 
	 */
	public int getId() { return id; }
	
	
	/**********
	 * <p> Method: void setId(int id) </p>
	 * 
	 * <p> Description: This set a new id of a reply. </p>
	 * 
	 * @param id is the identification number of reply
	 * 
	 */
	public void setId(int id) { this.id = id; }
	
	
	/**********
	 * <p> Method: int getParentPostId() </p>
	 * 
	 * <p> Description: This gets the id of the parent post for this reply </p>
	 * 
	 * @return id is the identification number of the parent post
	 * 
	 */
	public int getParentPostId() { return parentPostId; }
	
	
	/**********
	 * <p> Method: void setParentPostId(int parentPostId) </p>
	 * 
	 * <p> Description: This set a new parent post id for a reply </p>
	 * 
	 * @param parentPostId is the identification number of parent post id
	 * 
	 */
	public void setParentPostId(int parentPostId) { this.parentPostId = parentPostId; }
	
	
	/**********
	 * <p> Method: String getBody() </p>
	 * 
	 * <p> Description: This gets the body content reply. </p>
	 * 
	 * @return body String of a reply
	 * 
	 */
	public String getBody() { return body; }
	
	
	/**********
	 * <p> Method: void setBody(String body) </p>
	 * 
	 * <p> Description: This set a new body on a reply. </p>
	 * 
	 * @param body is what the body content will be
	 * 
	 */
	public void setBody(String body) { this.body = body == null ? "" : body; }
	
	
	/**********
	 * <p> Method: String getAuthor() </p>
	 * 
	 * <p> Description: This gets the author of a reply. </p>
	 * 
	 * @return author string attached to a reply
	 * 
	 */
	public String getAuthor() { return author; }
	
	
	/**********
	 * <p> Method: void setAuthor() </p>
	 * 
	 * <p> Description: This sets a new author to a reply. </p>
	 * 
	 * @param author string to be attached to a reply
	 * 
	 */
	public void setAuthor(String author) { this.author = author == null ? "" : author; }
	
	/**********
	 * <p> Method: String getRole() </p>
	 * 
	 * <p> Description: This gets the role of a Reply based on author </p>
	 * 
	 * @return role is the role assigned to a Reply
	 * 
	 */
	public String getRole() { return role; }
}
