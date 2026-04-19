package testgrading;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.sql.SQLException;
import java.util.UUID;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import database.Database;

/**
 * Integration tests for {@link Database} discussion counters backing
 * <b>Simple Statistic (JavaFX)</b> (Aryan Desai — HW3/TP3 Aspects.pdf, item 4).
 *
 * <p>Uses count deltas so prior rows in {@code discussionPostDB} do not break assertions.</p>
 */
@DisplayName("Database — discussion post/reply counts (HW3)")
class DatabaseDiscussionStatisticsHw3Test {

	private Database db;
	private String author;

	@BeforeEach
	void connect() throws SQLException {
		db = new Database();
		db.connectToDatabase();
		author = "HW3Stat_" + UUID.randomUUID().toString().replace("-", "").substring(0, 8);
	}

	@AfterEach
	void close() {
		if (db != null) {
			db.closeConnection();
		}
	}

	@Test
	@DisplayName("One top-level post increments post count; one reply increments reply count")
	void postAndReplyChangeCounts() throws SQLException {
		int p0 = db.countDiscussionPosts();
		int r0 = db.countDiscussionReplies();
		int postId = db.insertDiscussionPost(author, "thread root", null);
		int p1 = db.countDiscussionPosts();
		int r1 = db.countDiscussionReplies();
		assertEquals(p0 + 1, p1);
		assertEquals(r0, r1);

		db.insertDiscussionPost(author, "reply text", postId);
		assertEquals(p0 + 1, db.countDiscussionPosts());
		assertEquals(r0 + 1, db.countDiscussionReplies());
	}

	@Test
	@DisplayName("Reply with missing parent id fails (integrity / negative test)")
	void replyWithInvalidParentFails() throws SQLException {
		assertThrows(SQLException.class, () -> db.insertDiscussionPost(author, "orphan", Integer.valueOf(-99999)));
	}
}
