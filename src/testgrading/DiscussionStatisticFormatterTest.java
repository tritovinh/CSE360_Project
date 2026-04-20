package testgrading;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.sql.SQLException;
import java.util.UUID;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import database.Database;
import entityClasses.Post;
import entityClasses.Reply;
import guiAdminHome.DiscussionStatisticFormatter;

/**
 * Confirms the Admin summary line matches {@link Database} counts for
 * <b>Simple Statistic (JavaFX)</b> (HW3/TP3 Aspects.pdf item 4 — Aryan Desai).
 */
@DisplayName("DiscussionStatisticFormatter (HW3)")
class DiscussionStatisticFormatterTest {

	private Database db;
	private String author;

	@BeforeEach
	void connect() throws SQLException {
		db = new Database();
		db.connectToDatabase();
		author = "HW3Fmt_" + UUID.randomUUID().toString().replace("-", "").substring(0, 8);
	}

	@AfterEach
	void close() {
		if (db != null) {
			db.closeConnection();
		}
	}

	@Test
	@DisplayName("Formatter line tracks inserts")
	void summaryMatchesCounts() throws SQLException {
		int p0 = db.countDiscussionPosts();
		int r0 = db.countDiscussionReplies();
		int postId = db.createPost(new Post(0, "t", "root", author));
		db.createReply(new Reply(0, postId, "reply", author));
		String line = DiscussionStatisticFormatter.buildAdminSummaryLine(db);
		assertEquals("Discussion posts: " + (p0 + 1) + " | Replies: " + (r0 + 1), line);
	}

	@Test
	@DisplayName("Null database yields safe zero line")
	void nullDatabaseSafe() {
		String line = DiscussionStatisticFormatter.buildAdminSummaryLine(null);
		assertEquals("Discussion posts: 0 | Replies: 0", line);
	}
}
