package guiTools;

import java.sql.SQLException;

/*******
 * <p> Title: DiscussionValidator Class </p>
 * <p> Description: Enforces validation rules for discussion posts and replies (title/body length and required fields). </p>
 */
public class DiscussionValidator {
	
	/*****
     * <p> Method: DiscussionValidator() </p>
     * 
     * <p> Description: This default constructor is not used in this system. </p>
     */
    public DiscussionValidator() {	
    }

	/**Final size of title box in GUI**/
	public static final int MAX_TITLE_LENGTH = 100;
	/**Final size of body box in GUI**/
	public static final int MAX_BODY_LENGTH = 1000;

	/*******
	 * <p> Method: String validatePost(String title, String body) </p>
	 * 
	 * <p> Description: Validates post title and body</p>
	 * 
	 * @param title the string for title of a post
	 * @param body the string for the body of a post
	 * 
	 * @return an empty string if valid, otherwise it shows an accurate error message
	 */
	public static String validatePost(String title, String body) {
		
		if (validateTitle(title) != "") {
			return validateTitle(title);
		}
		
		return validateBody(body);
	}

	/*******
	 * <p> Method: String validateReply(String body) </p>
	 * 
	 * <p> Description: Validates reply body</p>
	 * 
	 * @param body the string for the body of a reply
	 * 
	 * @return an empty string if valid, otherwise it shows an accurate error message
	 */
	public static String validateReply(String body) {
		return validateBody(body);
	}

	/*******
	 * <p> Method: String validateTitle(String title) </p>
	 * 
	 * <p> Description: Validates title</p>
	 * 
	 * @param title the string for title of a post
	 * 
	 * @return an empty string if valid, otherwise it shows an accurate error message
	 */
	public static String validateTitle(String title) {
		String t = title == null ? "" : title.trim();
		if (t.isEmpty())
			return "Title is required";
		if (t.length() > MAX_TITLE_LENGTH)
			return "Title must be 100 characters or fewer";
		return "";
	}

	/*******
	 * <p> Method: String validateBody(String body) </p>
	 * 
	 * <p> Description: Validates body only (1000 char limit, non-empty)</p>
	 * 
	 * @param body the string for body of a post
	 * 
	 * @return an empty string if valid, otherwise it shows an accurate error message
	 */
	public static String validateBody(String body) {
		String b = body == null ? "" : body.trim();
		if (b.isEmpty())
			return "Body is required";
		if (b.length() > MAX_BODY_LENGTH)
			return "Body must be 1000 characters or fewer";
		return "";
	}
}
