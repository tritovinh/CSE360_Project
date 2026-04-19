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

/**
 * JDBC checks focused on authentication and password handling for
 * <b>OWASP A04:2025 Cryptographic Failures</b> (Aryan Desai — HW3/Top Defects.pdf) on {@link Database}.
 *
 * <p>These tests document observable behavior: passwords are compared literally at login time,
 * consistent with the team rationale about plaintext storage risk—not "fixed" for HW3.</p>
 */
@DisplayName("Database — password / auth (A04, HW3)")
class DatabasePasswordCryptoHw3Test {

	private Database db;
	private String username;

	@BeforeEach
	void connect() throws SQLException {
		db = new Database();
		db.connectToDatabase();
		username = "hw3pwd_" + UUID.randomUUID().toString().replace("-", "").substring(0, 10);
	}

	@AfterEach
	void close() {
		if (db != null) {
			db.closeConnection();
		}
	}

	private User role1User(String user, String password) {
		return new User(user, password, "F", "M", "L", "PF", user + "@asu.edu", false, true, false);
	}

	@Test
	@DisplayName("Role1 login succeeds with matching password")
	void loginSucceedsWithCorrectPassword() throws SQLException {
		String pwd = "Cd3#xyza";
		db.register(role1User(username, pwd));
		assertTrue(db.loginRole1(role1User(username, pwd)));
	}

	@Test
	@DisplayName("Role1 login fails when password differs by one character (negative)")
	void loginFailsWithWrongPassword() throws SQLException {
		String pwd = "Cd3#xyza";
		db.register(role1User(username, pwd));
		assertFalse(db.loginRole1(role1User(username, pwd + "X")));
	}

	@Test
	@DisplayName("Role1 login fails with empty password when account has non-empty password")
	void loginFailsWithEmptyPassword() throws SQLException {
		String pwd = "Cd3#xyza";
		db.register(role1User(username, pwd));
		assertFalse(db.loginRole1(role1User(username, "")));
	}
}
