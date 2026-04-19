package testgrading;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.sql.SQLException;
import java.util.UUID;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import database.Database;
import entityClasses.Post;
import entityClasses.Reply;

/**
 * Integration tests for {@link Database} discussion counters backing
 * <b>Simple Statistic (JavaFX)</b> (Aryan Desai — HW3/TP3 Aspects.pdf, item 4).
 *
 * <p>Uses count deltas so prior rows in {@code posts}/{@code replies} do not break assertions.</p>
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
		int postId = db.createPost(new Post(0, "t", "thread root", author, "student"));
		assertTrue(postId > 0);
		int p1 = db.countDiscussionPosts();
		int r1 = db.countDiscussionReplies();
		assertEquals(p0 + 1, p1);
		assertEquals(r0, r1);

		db.createReply(new Reply(0, postId, "reply text", author, "student"));
		assertEquals(p0 + 1, db.countDiscussionPosts());
		assertEquals(r0 + 1, db.countDiscussionReplies());
	}

	@Test
	@DisplayName("Reply with missing parent id fails (integrity / negative test)")
	void replyWithInvalidParentFails() {
		assertThrows(SQLException.class, () -> db.createReply(
				new Reply(0, -99999, "orphan", author, "student")));
	}
}
