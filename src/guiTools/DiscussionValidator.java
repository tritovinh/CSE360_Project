package guiTools;

/*******
 * <p> Title: DiscussionValidator Class </p>
 * <p> Description: Enforces validation rules for discussion posts and replies (title/body length and required fields). </p>
 */
public class DiscussionValidator {

	public static final int MAX_TITLE_LENGTH = 100;
	public static final int MAX_BODY_LENGTH = 1000;

	/** Validates post title and body. Returns empty string if valid, otherwise the error message (V-01, P-02, body rules). */
	public static String validatePost(String title, String body) {
		String t = title == null ? "" : title.trim();
		String b = body == null ? "" : body.trim();
		// title required
		if (t.isEmpty())
			return "Title is required";
		// title max 100
		if (t.length() > MAX_TITLE_LENGTH)
			return "Title must be 100 characters or fewer";
		// Body required
		if (b.isEmpty())
			return "Body is required";
		// body max 1000
		if (b.length() > MAX_BODY_LENGTH)
			return "Content body exceeds the 1000-character limit";
		return "";
	}

	/** Validates reply body only. Returns empty string if valid, otherwise the error message (R-02, V-02). */
	public static String validateReply(String body) {
		String b = body == null ? "" : body.trim();
		if (b.isEmpty())
			return "Content body is required";
		if (b.length() > MAX_BODY_LENGTH)
			return "Content body exceeds the 1000-character limit";
		return "";
	}

	/** Validates title only (e.g. for post title field). Returns empty string if valid. */
	public static String validateTitle(String title) {
		String t = title == null ? "" : title.trim();
		if (t.isEmpty())
			return "Title is required";
		if (t.length() > MAX_TITLE_LENGTH)
			return "Title must be 100 characters or fewer";
		return "";
	}

	/** Validates body only (1000 char limit, non-empty). Returns empty string if valid. */
	public static String validateBody(String body) {
		String b = body == null ? "" : body.trim();
		if (b.isEmpty())
			return "Body is required";
		if (b.length() > MAX_BODY_LENGTH)
			return "Body must be 1000 characters or fewer";
		return "";
	}
}
