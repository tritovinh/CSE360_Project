package guiAdminHome;

import database.Database;

/**
 * Builds the short status line shown on the Admin home page for the team TP3 aspect
 * “Simple Statistic (JavaFX)” (HW3/TP3 Aspects.pdf, item 4 — Aryan Desai): display how many
 * posts and how many replies exist overall.
 *
 * <p>Why a separate type: keeps the JavaFX view from duplicating string formatting and gives
 * JUnit a single place to assert the wording matches {@link database.Database} counts.</p>
 */
public final class DiscussionStatisticFormatter {

	private DiscussionStatisticFormatter() {
	}

	/**
	 * @param db connected application database (same singleton used by {@link ViewAdminHome})
	 * @return one-line summary for admins
	 */
	public static String buildAdminSummaryLine(Database db) {
		if (db == null) {
			return "Discussion posts: 0 | Replies: 0";
		}
		int posts = db.countDiscussionPosts();
		int replies = db.countDiscussionReplies();
		return "Discussion posts: " + posts + " | Replies: " + replies;
	}
}
