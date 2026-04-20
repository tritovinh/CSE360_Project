package guiTools;

/**
 * <p> Title: DiscussionTestRunner Class </p>
 * <p> Description: Semi-automated test runner for TP2 discussion test cases</p>
 * * TP2 Test list:
 * - P-01: Create Post (Positive) -> Tested by performCreatePostTestCase()
 * - P-02: Create Post Empty Title (Negative) -> Tested by performCreatePostTestCase()
 * - R-01: Create Reply (Positive) -> Tested by performCreateReplyTestCase()
 * - R-02: Create Reply Empty Content (Negative) -> Tested by performCreateReplyTestCase()
 * - V-01: Title Exceeds 100 characters (Negative) -> Tested by performTitleExceeds100TestCase()
 * - V-02: Body Exceeds 1000 characters (Negative) -> Tested by performBodyExceeds1000TestCase()
 */
public class DiscussionTestRunner {

	static int numPassed = 0;	// Counter of the number of passed tests
	static int numFailed = 0;	// Counter of the number of failed tests

	public static void main(String[] args) {
		/************** Test cases semi-automation report header **************/
		System.out.println("______________________________________");
		System.out.println("\nTest Case Automation – HW2 Discussion");

		/************** 1. Post Management Tests **************/
		performCreatePostTestCase("P-01", "Valid title", "Valid body content.", true);
		performCreatePostTestCase("P-02", "", "Some body.", false);

		/************** 2. Reply Management Tests **************/
		performCreateReplyTestCase("R-01", "Valid reply text.", true);
		performCreateReplyTestCase("R-02", "", false);

		/************** 3. Input Validation Tests **************/
		performTitleExceeds100TestCase("V-01", "x".repeat(101), "Body text.", false);
		performBodyExceeds1000TestCase("V-02", "Valid title", "x".repeat(1001), false);

		/************** Test cases semi-automation report footer **************/
		System.out.println("______________________________________");
		System.out.println();
		System.out.println("Number of tests passed: " + numPassed);
		System.out.println("Number of tests failed: " + numFailed);
	}

	/**
	 * Requirement Tested: P-01 (Create Post - Positive) and P-02 (Create Post - Empty Title)
	 * * How the code assesses satisfaction: Passes the provided title and body strings into the 
	 * DiscussionValidator.validatePost method to ensure it correctly accepts valid data and rejects empty titles.
	 * * Output Interpretation: If the expected outcome matches the actual validation result, the test prints 
	 * "***Success***" and increments the pass counter. If the validation fails unexpectedly, it prints "***Failure***".
	 */
	public static void performCreatePostTestCase(String testId, String title, String body, boolean expectedPass) {
		System.out.println("______________________________________\n\nTest case (Post Management): " + testId);
		System.out.println("Input: title=\"" + title + "\", body=\"" + (body.length() > 40 ? body.substring(0, 40) + "..." : body) + "\"");
		System.out.println("_______");
		System.out.println("\nValidation execution trace:");

		String resultText = DiscussionValidator.validatePost(title, body);

		System.out.println();
		interpretResult("create post (title, body)", resultText, expectedPass, "post creation");
	}

	/**
	 * Requirement Tested: R-01 (Create Reply - Positive) and R-02 (Create Reply - Empty Content)
	 * * How the code assesses satisfaction: Evaluates the reply body string using DiscussionValidator.validateReply 
	 * to verify that standard text is accepted and empty strings are blocked.
	 * * Output Interpretation: The test evaluates the returned error string. If validation behaves as expected 
	 * based on the expectedPass boolean, "***Success***" is printed. Otherwise, "***Failure***" is printed.
	 */
	public static void performCreateReplyTestCase(String testId, String body, boolean expectedPass) {
		System.out.println("______________________________________\n\nTest case (Reply Management): " + testId);
		System.out.println("Input: body=\"" + (body.length() > 50 ? body.substring(0, 50) + "..." : body) + "\"");
		System.out.println("_______");
		System.out.println("\nValidation execution trace:");

		String resultText = DiscussionValidator.validateReply(body);

		System.out.println();
		interpretResult("create reply (body)", resultText, expectedPass, "reply creation");
	}

	/**
	 * Requirement Tested: V-01 Title exceeds 100 characters (Negative)
	 * * How the code assesses satisfaction: Submits a dynamically generated string longer than 100 characters 
	 * to the validator to ensure the boundary limit enforcement is active.
	 * * Output Interpretation: A successful test prints "***Success***" and notes that the invalid input 
	 * was rejected as expected. If the system allows the long title, it prints "***Failure***".
	 */
	public static void performTitleExceeds100TestCase(String testId, String title, String body, boolean expectedPass) {
		System.out.println("______________________________________\n\nTest case (Input Validation): " + testId);
		System.out.println("Input: title length=" + title.length() + " chars, body=\"" + (body.length() > 30 ? body.substring(0, 30) + "..." : body) + "\"");
		System.out.println("_______");
		System.out.println("\nValidation execution trace:");

		String resultText = DiscussionValidator.validatePost(title, body);

		System.out.println();
		interpretResult("title exceeds 100 chars", resultText, expectedPass, "title length");
	}

	/**
	 * Requirement Tested: V-02 Body exceeds 1000 characters (Negative)
	 * * How the code assesses satisfaction: Submits a dynamically generated string longer than 1000 characters 
	 * for the post body to ensure the length restriction is enforced.
	 * * Output Interpretation: A successful test prints "***Success***" indicating the over-length body 
	 * was properly rejected. Acceptance of the text results in a "***Failure***" output.
	 */
	public static void performBodyExceeds1000TestCase(String testId, String title, String body, boolean expectedPass) {
		System.out.println("______________________________________\n\nTest case (Input Validation): " + testId);
		System.out.println("Input: title=\"" + (title.length() > 30 ? title.substring(0, 30) + "..." : title) + "\", body length=" + body.length() + " chars");
		System.out.println("_______");
		System.out.println("\nValidation execution trace:");

		String resultText = DiscussionValidator.validatePost(title, body);

		System.out.println();
		interpretResult("body exceeds 1000 chars", resultText, expectedPass, "body length");
	}

	// Interprets the result showing Success or Failure (for validation tests)
	private static void interpretResult(String inputLabel, String resultText, boolean expectedPass, String contextLabel) {
		boolean rejected = resultText != null && !resultText.isEmpty();
		String label = inputLabel != null && !inputLabel.isEmpty() ? inputLabel : contextLabel;
		if (rejected) {
			if (expectedPass) {
				System.out.println("***Failure*** " + label + " was rejected but was supposed to be valid.\n");
				System.out.println("Error message: " + resultText);
				numFailed++;
			} else {
				System.out.println("***Success*** " + label + " was rejected as expected (invalid input).\n");
				System.out.println("Error message: " + resultText);
				numPassed++;
			}
		} else {
			if (expectedPass) {
				System.out.println("***Success*** " + label + " passed validation, so this is a pass!");
				numPassed++;
			} else {
				System.out.println("***Failure*** " + label + " was accepted but was supposed to be invalid.");
				numFailed++;
			}
		}
	}
}