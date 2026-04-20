package testgrading;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.sql.SQLException;
import java.util.UUID;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import database.Database;
import entityClasses.User;

@DisplayName("Database - approve/endorse reply moderation")
class ModerationApproveReplyHw3Test {

	private Database db;

	@BeforeEach
	void setUp() throws SQLException {
		db = new Database();
		db.connectToDatabase();
	}

	@AfterEach
	void tearDown() {
		if (db != null) {
			db.closeConnection();
		}
	}

	@Test
	@DisplayName("Admin can approve a valid reply id")
	void adminCanApproveReply() throws SQLException {
		setupAdminSession();
		db.createDummyReply(51001);
		assertTrue(db.approveReply(51001));
	}

	@Test
	@DisplayName("Negative id cannot be approved")
	void negativeIdCannotBeApproved() throws SQLException {
		setupAdminSession();
		assertFalse(db.approveReply(-1));
	}

	@Test
	@DisplayName("Non-admin cannot approve reply")
	void unauthorizedUserCannotApproveReply() throws SQLException {
		setupStudentSession();
		db.createDummyReply(51002);
		assertFalse(db.approveReply(51002));
	}

	private void setupAdminSession() throws SQLException {
		String suffix = UUID.randomUUID().toString().replace("-", "").substring(0, 8);
		User admin = new User("admin_" + suffix, "Pass123!", "First", "M", "Last",
				"Pref", "admin_" + suffix + "@test.com", true, false, false);
		db.register(admin);
		db.loginAdmin(admin);
		db.getUserAccountDetails(admin.getUserName());
	}

	private void setupStudentSession() throws SQLException {
		String suffix = UUID.randomUUID().toString().replace("-", "").substring(0, 8);
		User student = new User("student_" + suffix, "Pass123!", "First", "M", "Last",
				"Pref", "student_" + suffix + "@test.com", false, true, false);
		db.register(student);
		db.loginRole1(student);
		db.getUserAccountDetails(student.getUserName());
	}
}
